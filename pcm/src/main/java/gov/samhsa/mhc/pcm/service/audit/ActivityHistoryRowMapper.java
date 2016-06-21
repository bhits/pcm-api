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

    private static final String ACTIVITY_ID = "activity_id";
    private static final String REVISION_ID = "revision_id";
    private static final String TIMESTAMP = "timestamp";
    private static final String USERNAME = "username";
    private static final String REC_TYPE = "rec_type";
    private static final String TYPE = "type";

    @Override
    public ActivityHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
        ActivityHistory activityHistory = new ActivityHistory();

        activityHistory.setId(rs.getLong(ACTIVITY_ID));
        activityHistory.setRevisionid(rs.getLong(REVISION_ID));
        activityHistory.setChangedDateTime(convertTimestampToDateTime(rs.getLong(TIMESTAMP)));
        activityHistory.setChangedBy(rs.getString(USERNAME));
        activityHistory.setRecType(rs.getString(REC_TYPE));
        activityHistory.setType(rs.getString(TYPE));

        return activityHistory;
    }

    private String convertTimestampToDateTime(Long revtstmp) {
        Assert.isTrue(revtstmp > 0, "The value must be greater than zero");
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(revtstmp));
    }
}
