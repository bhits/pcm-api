package gov.samhsa.mhc.pcm.infrastructure.pagination;

import org.springframework.jdbc.core.RowMapper;

/**
 * Created by Jiahao.Li on 6/19/2016.
 */
public interface SetQueryInfoMapping {
    public abstract RowMapper getRowMapper();

    public abstract String getTableName();

    public abstract String getIdColumn();
}
