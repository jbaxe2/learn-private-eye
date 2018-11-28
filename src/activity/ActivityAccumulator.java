package activity;

import java.util.Date;

/**
 * The [ActivityAccumulator] class...
 */
public class ActivityAccumulator {
  private String pk1;

  private String userPk1;

  private String coursePk1;

  private String groupPk1;

  private String forumPk1;

  private String contentPk1;

  private String eventType;

  private String internalHandle;

  private String data;

  private Date timestamp;

  private String sessionId;

  /**
   * The [ActivityAccumulator] constructor...
   */
  public ActivityAccumulator (
    String pk1, String userPk, String coursePk1, String groupPk1, String forumPk1,
    String contentPk1, String eventType, String internalHandle, String data,
    Date timestamp, String sessionId
  ) {
    this.pk1 = pk1;
    this.userPk1 = userPk;
    this.coursePk1 = coursePk1;
    this.groupPk1 = groupPk1;
    this.forumPk1 = forumPk1;
    this.contentPk1 = contentPk1;
    this.eventType = eventType;
    this.internalHandle = internalHandle;
    this.data = data;
    this.timestamp = timestamp;
    this.sessionId = sessionId;
  }

  public String getPk1() {
    return pk1;
  }

  public String getUserPk1() {
    return userPk1;
  }

  public String getCoursePk1() {
    return coursePk1;
  }

  public String getGroupPk1() {
    return groupPk1;
  }

  public String getForumPk1() {
    return forumPk1;
  }

  public String getContentPk1() {
    return contentPk1;
  }

  public String getEventType() {
    return eventType;
  }

  public String getInternalHandle() {
    return internalHandle;
  }

  public String getData() {
    return data;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getSessionId() {
    return sessionId;
  }
}
