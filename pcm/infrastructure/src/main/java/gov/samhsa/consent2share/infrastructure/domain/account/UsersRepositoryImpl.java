package gov.samhsa.consent2share.infrastructure.domain.account;

import gov.samhsa.consent2share.domain.account.Users;
import gov.samhsa.consent2share.domain.account.UsersRepository;
import gov.samhsa.consent2share.infrastructure.security.UsersAuthorityUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * Jdbc user management service, based on the same table structure as its parent class, <tt>JdbcDaoImpl</tt>.
 * <p>
 * Provides CRUD operations for both users and groups. Note that if the {@link #setEnableAuthorities(boolean)
 * enableAuthorities} property is set to false, calls to createUser, updateUser and deleteUser will not store the
 * authorities from the <tt>UserDetails</tt> or delete authorities for the user. Since this class cannot differentiate
 * between authorities which were loaded for an individual or for a group of which the individual is a member,
 * it's important that you take this into account when using this implementation for managing your users.
 *
 * @author Luke Taylor
 * @since 2.0
 */
public class UsersRepositoryImpl implements UsersRepository{
   

    //~ Instance fields ================================================================================================

    protected final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    // UserDetailsManager SQL
    public static final String DEF_CREATE_USER_SQL =
            "insert into users (username, password, enabled,failed_attempts) values (?,?,?,?)";
    public static final String DEF_DELETE_USER_SQL =
            "delete from users where username = ?";
    public static final String DEF_UPDATE_USER_SQL =
            "update users set password = ?, enabled = ?,failed_attempts=?, lockout_time=? where username = ?";
    public static final String DEF_INSERT_AUTHORITY_SQL =
            "insert into authorities (username, authority) values (?,?)";
    public static final String DEF_DELETE_USER_AUTHORITIES_SQL =
            "delete from authorities where username = ?";
    public static final String DEF_USER_EXISTS_SQL =
            "select username from users where username = ?";
    public static final String DEF_CHANGE_PASSWORD_SQL =
            "update users set password = ? where username = ?";
    
    //~ Static fields/initializers =====================================================================================
    public static final String DEF_USERS_BY_USERNAME_QUERY = "SELECT u.USERNAME,u.password,u.enabled," +
    		"u.failed_attempts,u.lockout_time,r.authority FROM users u, authorities r WHERE u.username = r.username AND " +
    		"u.USERNAME=?";
    public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY =
            "select username,authority " +
            "from authorities " +
            "where username = ?";
    public static final String DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY =
            "select g.id, g.group_name, ga.authority " +
            "from groups g, group_members gm, group_authorities ga " +
            "where gm.username = ? " +
            "and g.id = ga.group_id " +
            "and g.id = gm.group_id";

    // GroupManager SQL
    public static final String DEF_FIND_GROUPS_SQL =
            "select group_name from groups";
    public static final String DEF_FIND_USERS_IN_GROUP_SQL =
            "select username from group_members gm, groups g " +
            "where gm.group_id = g.id" +
            " and g.group_name = ?";
    public static final String DEF_INSERT_GROUP_SQL =
            "insert into groups (group_name) values (?)";
    public static final String DEF_FIND_GROUP_ID_SQL =
            "select id from groups where group_name = ?";
    public static final String DEF_INSERT_GROUP_AUTHORITY_SQL =
            "insert into group_authorities (group_id, authority) values (?,?)";
    public static final String DEF_DELETE_GROUP_SQL =
            "delete from groups where id = ?";
    public static final String DEF_DELETE_GROUP_AUTHORITIES_SQL =
            "delete from group_authorities where group_id = ?";
    public static final String DEF_DELETE_GROUP_MEMBERS_SQL =
            "delete from group_members where group_id = ?";
    public static final String DEF_RENAME_GROUP_SQL =
            "update groups set group_name = ? where group_name = ?";
    public static final String DEF_INSERT_GROUP_MEMBER_SQL =
            "insert into group_members (group_id, username) values (?,?)";
    public static final String DEF_DELETE_GROUP_MEMBER_SQL =
            "delete from group_members where group_id = ? and username = ?";
    public static final String DEF_GROUP_AUTHORITIES_QUERY_SQL =
            "select g.id, g.group_name, ga.authority " +
            "from groups g, group_authorities ga " +
            "where g.group_name = ? " +
            "and g.id = ga.group_id ";
    public static final String DEF_DELETE_GROUP_AUTHORITY_SQL =
            "delete from group_authorities where group_id = ? and authority = ?";


    //~ Instance fields ================================================================================================

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private String createUserSql = DEF_CREATE_USER_SQL;
    private String deleteUserSql = DEF_DELETE_USER_SQL;
    private String updateUserSql = DEF_UPDATE_USER_SQL;
    private String createAuthoritySql = DEF_INSERT_AUTHORITY_SQL;
    private String deleteUserAuthoritiesSql = DEF_DELETE_USER_AUTHORITIES_SQL;
    private String userExistsSql = DEF_USER_EXISTS_SQL;
    private String changePasswordSql = DEF_CHANGE_PASSWORD_SQL;

    private String findAllGroupsSql = DEF_FIND_GROUPS_SQL;
    private String findUsersInGroupSql = DEF_FIND_USERS_IN_GROUP_SQL;
    private String insertGroupSql = DEF_INSERT_GROUP_SQL;
    private String findGroupIdSql = DEF_FIND_GROUP_ID_SQL;
    private String insertGroupAuthoritySql = DEF_INSERT_GROUP_AUTHORITY_SQL;
    private String deleteGroupSql = DEF_DELETE_GROUP_SQL;
    private String deleteGroupAuthoritiesSql = DEF_DELETE_GROUP_AUTHORITIES_SQL;
    private String deleteGroupMembersSql = DEF_DELETE_GROUP_MEMBERS_SQL;
    private String renameGroupSql = DEF_RENAME_GROUP_SQL;
    private String insertGroupMemberSql = DEF_INSERT_GROUP_MEMBER_SQL;
    private String deleteGroupMemberSql = DEF_DELETE_GROUP_MEMBER_SQL;
    private String groupAuthoritiesSql = DEF_GROUP_AUTHORITIES_QUERY_SQL;
    private String deleteGroupAuthoritySql = DEF_DELETE_GROUP_AUTHORITY_SQL;
    
    private String authoritiesByUsernameQuery = DEF_AUTHORITIES_BY_USERNAME_QUERY;
    private String groupAuthoritiesByUsernameQuery = DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY;
    private String usersByUsernameQuery = DEF_USERS_BY_USERNAME_QUERY;

    private AuthenticationManager authenticationManager;

	private DataSource dataSource;    
    
    public UsersRepositoryImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	//~ UserDetailsManager implementation ==============================================================================

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#createUser(gov.samhsa.consent2share.domain.account.Users)
	 */
    @Override
	public void createUser(final Users user) {
    	validateUser(user);
        getJdbcTemplate().update(createUserSql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setBoolean(3, user.isEnabled());
                ps.setInt(4, user.getFailedLoginAttempts());
            }

        });
            insertUserAuthorities(user);
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#updateUser(gov.samhsa.consent2share.domain.account.Users)
	 */
    @Override
	public void updateUser(final Users user) {
    	validateUser(user);
    	
        getJdbcTemplate().update(updateUserSql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, user.getPassword());
                ps.setBoolean(2, user.isEnabled());
                ps.setInt(3, user.getFailedLoginAttempts());
                String lockoutTime=(user.getLockoutTime()==null)?
            			"NULL":new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(user.getLockoutTime().getTime());
                ps.setString(4, lockoutTime);
                ps.setString(5, user.getUsername());
                
            }
        });

            deleteUserAuthorities(user.getUsername());
            insertUserAuthorities(user);
    }

    private void insertUserAuthorities(Users user) {
        for (GrantedAuthority auth : user.getAuthorities()) {
            getJdbcTemplate().update(createAuthoritySql, user.getUsername(), auth.getAuthority());
        }
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#deleteUser(java.lang.String)
	 */
    @Override
	public void deleteUser(String username) {
        deleteUserAuthorities(username);
        getJdbcTemplate().update(deleteUserSql, username);
    }

    private void deleteUserAuthorities(String username) {
        getJdbcTemplate().update(deleteUserAuthoritiesSql, username);
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#userExists(java.lang.String)
	 */
    @Override
	public boolean userExists(String username) {
        List<String> users = getJdbcTemplate().queryForList(userExistsSql, new String[] {username}, String.class);

        if (users.size() > 1) {
            throw new IncorrectResultSizeDataAccessException("More than one user found with name '" + username + "'", 1);
        }

        return users.size() == 1;
    }

    //~ GroupManager implementation ====================================================================================

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#findAllGroups()
	 */
    @Override
	public List<String> findAllGroups() {
        return getJdbcTemplate().queryForList(findAllGroupsSql, String.class);
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#findUsersInGroup(java.lang.String)
	 */
    @Override
	public List<String> findUsersInGroup(String groupName) {
        Assert.hasText(groupName);
        return getJdbcTemplate().queryForList(findUsersInGroupSql, new String[] {groupName}, String.class);
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#createGroup(java.lang.String, java.util.List)
	 */
    @Override
	public void createGroup(final String groupName, final List<GrantedAuthority> authorities) {
        Assert.hasText(groupName);
        Assert.notNull(authorities);

        logger.debug("Creating new group '" + groupName + "' with authorities " +
                AuthorityUtils.authorityListToSet(authorities));

        getJdbcTemplate().update(insertGroupSql, groupName);

        final int groupId = findGroupId(groupName);

        for (GrantedAuthority a : authorities) {
            final String authority = a.getAuthority();
            getJdbcTemplate().update(insertGroupAuthoritySql, new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, groupId);
                    ps.setString(2, authority);
                }
            });
        }
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#deleteGroup(java.lang.String)
	 */
    @Override
	public void deleteGroup(String groupName) {
        logger.debug("Deleting group '" + groupName + "'");
        Assert.hasText(groupName);

        final int id = findGroupId(groupName);
        PreparedStatementSetter groupIdPSS = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
            }
        };
        getJdbcTemplate().update(deleteGroupMembersSql, groupIdPSS);
        getJdbcTemplate().update(deleteGroupAuthoritiesSql, groupIdPSS);
        getJdbcTemplate().update(deleteGroupSql, groupIdPSS);
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#renameGroup(java.lang.String, java.lang.String)
	 */
    @Override
	public void renameGroup(String oldName, String newName) {
        logger.debug("Changing group name from '" + oldName + "' to '" + newName + "'");
        Assert.hasText(oldName);
        Assert.hasText(newName);

        getJdbcTemplate().update(renameGroupSql, newName, oldName);
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#addUserToGroup(java.lang.String, java.lang.String)
	 */
    @Override
	public void addUserToGroup(final String username, final String groupName) {
        logger.debug("Adding user '" + username + "' to group '" + groupName + "'");
        Assert.hasText(username);
        Assert.hasText(groupName);

        final int id = findGroupId(groupName);
        getJdbcTemplate().update(insertGroupMemberSql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
                ps.setString(2, username);
            }
        });
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#removeUserFromGroup(java.lang.String, java.lang.String)
	 */
    @Override
	public void removeUserFromGroup(final String username, final String groupName) {
        logger.debug("Removing user '" + username + "' to group '" + groupName + "'");
        Assert.hasText(username);
        Assert.hasText(groupName);

        final int id = findGroupId(groupName);

        getJdbcTemplate().update(deleteGroupMemberSql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
                ps.setString(2, username);
            }
        });
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#removeGroupAuthority(java.lang.String, org.springframework.security.core.GrantedAuthority)
	 */
    @Override
	public void removeGroupAuthority(String groupName, final GrantedAuthority authority) {
        logger.debug("Removing authority '" + authority + "' from group '" + groupName + "'");
        Assert.hasText(groupName);
        Assert.notNull(authority);

        final int id = findGroupId(groupName);

        getJdbcTemplate().update(deleteGroupAuthoritySql, new PreparedStatementSetter() {

            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
                ps.setString(2, authority.getAuthority());
            }
        });
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#addGroupAuthority(java.lang.String, org.springframework.security.core.GrantedAuthority)
	 */
    @Override
	public void addGroupAuthority(final String groupName, final GrantedAuthority authority) {
        logger.debug("Adding authority '" + authority + "' to group '" + groupName + "'");
        Assert.hasText(groupName);
        Assert.notNull(authority);

        final int id = findGroupId(groupName);
        getJdbcTemplate().update(insertGroupAuthoritySql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
                ps.setString(2, authority.getAuthority());
            }
        });
    }

    private int findGroupId(String group) {
        return getJdbcTemplate().queryForObject(findGroupIdSql,Integer.class, group);
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setAuthenticationManager(org.springframework.security.authentication.AuthenticationManager)
	 */
    @Override
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setCreateUserSql(java.lang.String)
	 */
    @Override
	public void setCreateUserSql(String createUserSql) {
        Assert.hasText(createUserSql);
        this.createUserSql = createUserSql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setDeleteUserSql(java.lang.String)
	 */
    @Override
	public void setDeleteUserSql(String deleteUserSql) {
        Assert.hasText(deleteUserSql);
        this.deleteUserSql = deleteUserSql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setUpdateUserSql(java.lang.String)
	 */
    @Override
	public void setUpdateUserSql(String updateUserSql) {
        Assert.hasText(updateUserSql);
        this.updateUserSql = updateUserSql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setCreateAuthoritySql(java.lang.String)
	 */
    @Override
	public void setCreateAuthoritySql(String createAuthoritySql) {
        Assert.hasText(createAuthoritySql);
        this.createAuthoritySql = createAuthoritySql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setDeleteUserAuthoritiesSql(java.lang.String)
	 */
    @Override
	public void setDeleteUserAuthoritiesSql(String deleteUserAuthoritiesSql) {
        Assert.hasText(deleteUserAuthoritiesSql);
        this.deleteUserAuthoritiesSql = deleteUserAuthoritiesSql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setUserExistsSql(java.lang.String)
	 */
    @Override
	public void setUserExistsSql(String userExistsSql) {
        Assert.hasText(userExistsSql);
        this.userExistsSql = userExistsSql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setChangePasswordSql(java.lang.String)
	 */
    @Override
	public void setChangePasswordSql(String changePasswordSql) {
        Assert.hasText(changePasswordSql);
        this.changePasswordSql = changePasswordSql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setFindAllGroupsSql(java.lang.String)
	 */
    @Override
	public void setFindAllGroupsSql(String findAllGroupsSql) {
        Assert.hasText(findAllGroupsSql);
        this.findAllGroupsSql = findAllGroupsSql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setFindUsersInGroupSql(java.lang.String)
	 */
    @Override
	public void setFindUsersInGroupSql(String findUsersInGroupSql) {
        Assert.hasText(findUsersInGroupSql);
        this.findUsersInGroupSql = findUsersInGroupSql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setInsertGroupSql(java.lang.String)
	 */
    @Override
	public void setInsertGroupSql(String insertGroupSql) {
        Assert.hasText(insertGroupSql);
        this.insertGroupSql = insertGroupSql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setFindGroupIdSql(java.lang.String)
	 */
    @Override
	public void setFindGroupIdSql(String findGroupIdSql) {
        Assert.hasText(findGroupIdSql);
        this.findGroupIdSql = findGroupIdSql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setInsertGroupAuthoritySql(java.lang.String)
	 */
    @Override
	public void setInsertGroupAuthoritySql(String insertGroupAuthoritySql) {
        Assert.hasText(insertGroupAuthoritySql);
        this.insertGroupAuthoritySql = insertGroupAuthoritySql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setDeleteGroupSql(java.lang.String)
	 */
    @Override
	public void setDeleteGroupSql(String deleteGroupSql) {
        Assert.hasText(deleteGroupSql);
        this.deleteGroupSql = deleteGroupSql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setDeleteGroupAuthoritiesSql(java.lang.String)
	 */
    @Override
	public void setDeleteGroupAuthoritiesSql(String deleteGroupAuthoritiesSql) {
        Assert.hasText(deleteGroupAuthoritiesSql);
        this.deleteGroupAuthoritiesSql = deleteGroupAuthoritiesSql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setDeleteGroupMembersSql(java.lang.String)
	 */
    @Override
	public void setDeleteGroupMembersSql(String deleteGroupMembersSql) {
        Assert.hasText(deleteGroupMembersSql);
        this.deleteGroupMembersSql = deleteGroupMembersSql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setRenameGroupSql(java.lang.String)
	 */
    @Override
	public void setRenameGroupSql(String renameGroupSql) {
        Assert.hasText(renameGroupSql);
        this.renameGroupSql = renameGroupSql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setInsertGroupMemberSql(java.lang.String)
	 */
    @Override
	public void setInsertGroupMemberSql(String insertGroupMemberSql) {
        Assert.hasText(insertGroupMemberSql);
        this.insertGroupMemberSql = insertGroupMemberSql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setDeleteGroupMemberSql(java.lang.String)
	 */
    @Override
	public void setDeleteGroupMemberSql(String deleteGroupMemberSql) {
        Assert.hasText(deleteGroupMemberSql);
        this.deleteGroupMemberSql = deleteGroupMemberSql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setGroupAuthoritiesSql(java.lang.String)
	 */
    @Override
	public void setGroupAuthoritiesSql(String groupAuthoritiesSql) {
        Assert.hasText(groupAuthoritiesSql);
        this.groupAuthoritiesSql = groupAuthoritiesSql;
    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#setDeleteGroupAuthoritySql(java.lang.String)
	 */
    @Override
	public void setDeleteGroupAuthoritySql(String deleteGroupAuthoritySql) {
        Assert.hasText(deleteGroupAuthoritySql);
        this.deleteGroupAuthoritySql = deleteGroupAuthoritySql;
    }

    /**
     * Optionally sets the UserCache if one is in use in the application.
     * This allows the user to be removed from the cache after updates have taken place to avoid stale data.
     *
     * @param userCache the cache used by the AuthenticationManager.
     */

    private void validateUser(Users user) {
        Assert.hasText(user.getUsername(), "Username may not be empty or null");
        validateAuthorities(user.getAuthorities());
    }

    private void validateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Authorities list must not be null");

        for (GrantedAuthority authority : authorities) {
            Assert.notNull(authority, "Authorities list contains a null entry");
            Assert.hasText(authority.getAuthority(), "getAuthority() method must return a non-empty string");
        }
    }
    
    private JdbcTemplate getJdbcTemplate(){
    	return new JdbcTemplate(dataSource);
    }
    
    protected Authentication createNewAuthentication(Authentication currentAuth, String newPassword) {
        UserDetails user = loadUserByUsername(currentAuth.getName());

        UsernamePasswordAuthenticationToken newAuthentication =
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        newAuthentication.setDetails(currentAuth.getDetails());

        return newAuthentication;
    }
    
    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#changePassword(java.lang.String, java.lang.String)
	 */
    @Override
	public void changePassword(String oldPassword, String newPassword) throws AuthenticationException {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();

        if (currentUser == null) {
            // This would indicate bad coding somewhere
            throw new AccessDeniedException("Can't change password as no Authentication object found in context " +
                    "for current user.");
        }

        String username = currentUser.getName();

        // If an authentication manager has been set, re-authenticate the user with the supplied password.
        if (authenticationManager != null) {
            logger.debug("Reauthenticating user '"+ username + "' for password change request.");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        } else {
            logger.debug("No authentication manager set. Password won't be re-checked.");
        }

        logger.debug("Changing password for user '"+ username + "'");

        getJdbcTemplate().update(changePasswordSql, newPassword, username);

        SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(currentUser, newPassword));

    }

    /* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.domain.account.UsersRepository#loadUserByUsername(java.lang.String)
	 */
	@Override
	public Users loadUserByUsername(String username) {
		List<Users> users=getJdbcTemplate().query(usersByUsernameQuery, new String[] {username}, new RowMapper<Users>() {
            public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString(1);
                String password = rs.getString(2);
                boolean enabled = rs.getBoolean(3);
                int failed_attempts=rs.getInt(4);
                String lockout_time=rs.getString(5);
                String authority=rs.getString(6);
                Calendar cal=Calendar.getInstance();
                if (lockout_time!=null && !lockout_time.equals("NULL")){
                	try {
						cal.setTime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(lockout_time));
					} catch (ParseException e) {
						logger.warn("Error when parsing lockout time");
						return new Users(failed_attempts,username, password, enabled, true, true, UsersAuthorityUtils.createAuthoritySet(authority),Calendar.getInstance());
					}
                	return new Users(failed_attempts,username, password, enabled, true, true, UsersAuthorityUtils.createAuthoritySet(authority),cal);
                }
                else{
                	return new Users(failed_attempts,username, password, enabled, true, true, UsersAuthorityUtils.createAuthoritySet(authority));
                }
                
            }
        });
		
		
		if (users.size()!=0){
			Set<GrantedAuthority> authorities=new HashSet<GrantedAuthority>();
			for (Users user:users){
				authorities.addAll(user.getAuthorities());
			}
			Users userTemp=users.get(0);
			return new Users(userTemp.getFailedLoginAttempts(),
					userTemp.getUsername(),
					userTemp.getPassword(),
					userTemp.isEnabled(),
					userTemp.isAccountNonExpired(),
					userTemp.isCredentialsNonExpired(),
					authorities,userTemp.getLockoutTime());
		}
		else
			return null;
    }

}

