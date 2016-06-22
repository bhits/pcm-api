package gov.samhsa.mhc.pcm.infrastructure.pagination;

import gov.samhsa.mhc.pcm.infrastructure.SqlScriptProvider;
import gov.samhsa.mhc.pcm.infrastructure.pagination.sql.SqlFromClause;
import gov.samhsa.mhc.pcm.infrastructure.pagination.sql.SqlRebuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jiahao.Li on 6/19/2016.
 */
@Service
public class JdbcPagingRepositoryImpl implements JdbcPagingRepository {

    private static final String FROM_PATTERN = "FROM";
    private static final String SELECT_PATTERN = "SELECT";

    /**
     * The jdbc operations.
     */
    @Autowired
    private JdbcOperations jdbcOperations;

    /**
     * The sql script provider.
     */
    @Autowired
    private SqlScriptProvider sqlScriptProvider;

    @Autowired
    private SetQueryInfoMapping queryInfoMapping;

    @Override
    public <T> Page<T> findAll(Pageable pageable) {
        Page<T> pages = null;
        try {
            SqlRebuilder sqlRebuilder = getSqlRebuilder();
            SqlFromClause sqlFromClause = getSqlFromClause();
            String query = sqlRebuilder.selectAll(sqlFromClause, pageable);
            pages = new PageImpl<>(jdbcOperations.query(query, queryInfoMapping.getRowMapper()), pageable, count(sqlRebuilder, sqlFromClause));
        } catch (Exception e) {
            throw new JdbcPagingException(e);
        }
        return pages;
    }

    @Override
    public <T> Page<T> findAllByArgs(Pageable pageable, Object... args) {
        Page<T> pages = null;
        try {
            SqlRebuilder sqlRebuilder = getSqlRebuilder();
            SqlFromClause sqlFromClause = getSqlFromClause();
            String query = sqlRebuilder.selectByIdPageable(sqlFromClause, pageable);
            pages = new PageImpl<>(jdbcOperations.query(query, queryInfoMapping.getRowMapper(), args), pageable, countByArgs(sqlRebuilder, sqlFromClause, args[0]));
        } catch (Exception e) {
            throw new JdbcPagingException(e);
        }
        return pages;
    }

    private long count(SqlRebuilder sqlRebuilder, SqlFromClause sqlFromClause) {
        return jdbcOperations.queryForObject(sqlRebuilder.count(sqlFromClause), Long.class);
    }

    private long countByArgs(SqlRebuilder sqlRebuilder, SqlFromClause sqlFromClause, Object arg) {
        return jdbcOperations.queryForObject(sqlRebuilder.countByArgs(sqlFromClause, arg), Long.class);
    }

    private SqlRebuilder getSqlRebuilder() {
        String sqlScript = sqlScriptProvider.getSqlScript();
        Pattern pattern = Pattern.compile(FROM_PATTERN);
        Matcher matcher = pattern.matcher(sqlScript);
        String selectClause = "*";
        while (matcher.find()) {
            selectClause = sqlScript.substring(SELECT_PATTERN.length(), matcher.start());
        }
        return new SqlRebuilder(selectClause);
    }

    private SqlFromClause getSqlFromClause() {
        String sqlScript = sqlScriptProvider.getSqlScript();
        Pattern pattern = Pattern.compile(FROM_PATTERN);
        Matcher matcher = pattern.matcher(sqlScript);
        String fromClause = null;
        if (matcher.find()) {
            fromClause = sqlScript.substring(matcher.end());
        }
        return new SqlFromClause(queryInfoMapping.getTableName(), fromClause, queryInfoMapping.getIdColumn());
    }
}
