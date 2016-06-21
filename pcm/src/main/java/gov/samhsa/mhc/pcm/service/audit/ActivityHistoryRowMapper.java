package gov.samhsa.mhc.pcm.service.audit;

import gov.samhsa.mhc.pcm.service.audit.domain.ActivityHistory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jiahao.Li on 6/17/2016.
 */
public class ActivityHistoryRowMapper implements RowMapper<ActivityHistory> {

    @Override
    public ActivityHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
        ActivityHistory activityHistory = new ActivityHistory();

        activityHistory.setId(rs.getLong("activity_id"));
        activityHistory.setRevisionid(rs.getLong("revision_id"));
        activityHistory.setChangedDateTime(convertTimestampToDateTime(rs.getLong("timestamp")));
        activityHistory.setChangedBy(rs.getString("username"));
        activityHistory.setRecType(rs.getString("rec_type"));
        activityHistory.setType(rs.getString("type"));

        return activityHistory;
    }

    private String convertTimestampToDateTime(Long revtstmp) {
        Assert.isTrue(revtstmp > 0, "The value must be greater than zero");
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(revtstmp));
    }
}
