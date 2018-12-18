package activity;

import java.util.Date;

/**
 * The [ActivityEvent] class...
 */
public class ActivityEvent implements Comparable {
  private final String pk1;

  private final String userPk1;

  private final String coursePk1;

  private final String groupPk1;

  private final String forumPk1;

  private final String contentPk1;

  private final String eventType;

  private final String internalHandle;

  private final String data;

  private final Date timestamp;

  private final String sessionId;

  /**
   * The [ActivityEvent] constructor...
   */
  public ActivityEvent (
    String pk1, String userPk, String coursePk1, String groupPk1,
    String forumPk1, String contentPk1, String eventType,
    String internalHandle, String data, Date timestamp, String sessionId
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

  /**
   * The [compareTo] method...
   */
  public int compareTo (Object other) {
    return timestamp.compareTo (((ActivityEvent)other).getTimestamp());
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
