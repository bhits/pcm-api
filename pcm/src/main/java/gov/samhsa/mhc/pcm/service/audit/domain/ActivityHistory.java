package gov.samhsa.mhc.pcm.service.audit.domain;

import groovy.transform.EqualsAndHashCode;
import groovy.transform.ToString;
import org.springframework.data.domain.Persistable;

/**
 * Created by Jiahao.Li on 6/16/2016.
 */
@ToString
@EqualsAndHashCode
public class ActivityHistory implements Persistable<Long> {

    /**
     * The activityId.
     */
    private Long id;

    /**
     * The revisionid.
     */
    private Long revisionid;

    /**
     * The timestamp.
     */
    private String changedDateTime;

    /**
     * The changed by.
     */
    private String changedBy;

    /**
     * The patient first name
     */
    private String userName;

    /**
     * The rec type.
     */
    private String recType;

    /**
     * The type.
     */
    private String type;

    public ActivityHistory() {
    }

    public ActivityHistory(Long id, String changedDateTime, String changedBy, String type, String recType, Long revisionid) {
        this.id = id;
        this.changedDateTime = changedDateTime;
        this.changedBy = changedBy;
        this.type = type;
        this.recType = recType;
        this.revisionid = revisionid;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRevisionid() {
        return revisionid;
    }

    public void setRevisionid(Long revisionid) {
        this.revisionid = revisionid;
    }

    public String getChangedDateTime() {
        return changedDateTime;
    }

    public void setChangedDateTime(String changedDateTime) {
        this.changedDateTime = changedDateTime;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRecType() {
        return recType;
    }

    public void setRecType(String recType) {
        this.recType = recType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
