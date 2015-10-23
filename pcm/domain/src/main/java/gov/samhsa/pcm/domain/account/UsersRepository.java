package gov.samhsa.pcm.domain.account;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

public interface UsersRepository {

	public abstract void createUser(Users user);

	public abstract void updateUser(Users user);

	public abstract void deleteUser(String username);

	public abstract boolean userExists(String username);

	public abstract List<String> findAllGroups();

	public abstract List<String> findUsersInGroup(String groupName);

	public abstract void createGroup(String groupName,
			List<GrantedAuthority> authorities);

	public abstract void deleteGroup(String groupName);

	public abstract void renameGroup(String oldName, String newName);

	public abstract void addUserToGroup(String username, String groupName);

	public abstract void removeUserFromGroup(String username, String groupName);

	public abstract void removeGroupAuthority(String groupName,
			GrantedAuthority authority);

	public abstract void addGroupAuthority(String groupName,
			GrantedAuthority authority);

	public abstract void setAuthenticationManager(
			AuthenticationManager authenticationManager);

	public abstract void setCreateUserSql(String createUserSql);

	public abstract void setDeleteUserSql(String deleteUserSql);

	public abstract void setUpdateUserSql(String updateUserSql);

	public abstract void setCreateAuthoritySql(String createAuthoritySql);

	public abstract void setDeleteUserAuthoritiesSql(
			String deleteUserAuthoritiesSql);

	public abstract void setUserExistsSql(String userExistsSql);

	public abstract void setChangePasswordSql(String changePasswordSql);

	public abstract void setFindAllGroupsSql(String findAllGroupsSql);

	public abstract void setFindUsersInGroupSql(String findUsersInGroupSql);

	public abstract void setInsertGroupSql(String insertGroupSql);

	public abstract void setFindGroupIdSql(String findGroupIdSql);

	public abstract void setInsertGroupAuthoritySql(
			String insertGroupAuthoritySql);

	public abstract void setDeleteGroupSql(String deleteGroupSql);

	public abstract void setDeleteGroupAuthoritiesSql(
			String deleteGroupAuthoritiesSql);

	public abstract void setDeleteGroupMembersSql(String deleteGroupMembersSql);

	public abstract void setRenameGroupSql(String renameGroupSql);

	public abstract void setInsertGroupMemberSql(String insertGroupMemberSql);

	public abstract void setDeleteGroupMemberSql(String deleteGroupMemberSql);

	public abstract void setGroupAuthoritiesSql(String groupAuthoritiesSql);

	public abstract void setDeleteGroupAuthoritySql(
			String deleteGroupAuthoritySql);

	public abstract void changePassword(String oldPassword, String newPassword)
			throws AuthenticationException;

	/**
	 * Executes the SQL <tt>usersByUsernameQuery</tt> and returns a list of UserDetails objects.
	 * There should normally only be one matching user.
	 */
	public abstract Users loadUserByUsername(String username);

}