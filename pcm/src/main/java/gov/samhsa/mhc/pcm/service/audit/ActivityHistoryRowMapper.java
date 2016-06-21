package gov.samhsa.mhc.pcm.service.audit;

import gov.samhsa.mhc.pcm.service.audit.domain.ActivityHistory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
        activityHistory.setChangedBy(getFullName(rs.getString("last_name"), rs.getString("first_name")));
        activityHistory.setRecType(rs.getString("rec_type"));
        activityHistory.setType(convertRevClassNameToType(rs.getString("type")));

        return activityHistory;
    }

    private String convertTimestampToDateTime(Long revtstmp) {
        Assert.isTrue(revtstmp > 0, "The value must be greater than zero");
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(revtstmp));
    }

    private String getFullName(String lastName, String firstName) {
        return lastName.concat(", ").concat(firstName);
    }

    private String convertRevClassNameToType(String revClassName) {
        String type = revClassName
                .substring(revClassName.lastIndexOf('.') + 1).trim()
                .replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2");

        Set<String> providerType = new HashSet<>();
        providerType.add("Individual Provider");
        providerType.add("Organizational Provider");

        if (providerType.contains(type)) {
            return "Add provider";
        } else {
            return type;
        }
    }
}
