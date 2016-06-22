package gov.samhsa.mhc.pcm.service.audit;

import gov.samhsa.mhc.pcm.infrastructure.pagination.SetQueryInfoMapping;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * Created by Jiahao.Li on 6/19/2016.
 */
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
