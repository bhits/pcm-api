package gov.samhsa.c2s.pcm.service.audit;

import gov.samhsa.c2s.pcm.infrastructure.pagination.SetQueryInfoMapping;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component("SetQueryInfoMapping")
public class ActivityHistoryQueryInfo implements SetQueryInfoMapping {
    @Override
    public RowMapper getRowMapper() {
        return new ActivityHistoryRowMapper();
    }

    @Override
    public String getTableName() {
        return "revinfo";
    }

    @Override
    public String getIdColumn() {
        return "r.username";
    }
}
