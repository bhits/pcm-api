package gov.samhsa.c2s.pcm.infrastructure.pagination;

import org.springframework.jdbc.core.RowMapper;

public interface SetQueryInfoMapping {
    public abstract RowMapper getRowMapper();

    public abstract String getTableName();

    public abstract String getIdColumn();
}
