package session;

import blackboard.persist.Id;

import _error.SessionException;
import activity.ActivityEvent;

import java.util.List;

/**
 * The [SingleCourseUserSession] class...
 */
public class SingleCourseUserSession {
  private final Id courseId;

  private final Id userId;

  private final String sessionId;

  private SessionAccumulator sessionAccumulator;

  /**
   * The [SingleCourseUserSession] constructor...
   */
  public SingleCourseUserSession (Id courseId, Id userId, String sessionId) {
    this.courseId = courseId;
    this.userId = userId;
    this.sessionId = sessionId;

    sessionAccumulator = new SessionAccumulator (this.sessionId);
  }

  /**
   * The [addSessionActivity] method...
   */
  public void addSessionActivity (ActivityEvent sessionActivity) throws SessionException {
    String normalizedUserId = userId.getExternalString().split ("_")[1];
    String normalizedCourseId = courseId.getExternalString().split ("_")[1];

    if (!(sessionActivity.getUserPk1().equals (normalizedUserId) &&
          sessionActivity.getCoursePk1().equals (normalizedCourseId) &&
          sessionActivity.getSessionId().equals (sessionId))) {
      throw new SessionException (
        "The session event does not correspond to the correct user and course session."
      );
    }

    sessionAccumulator.addSessionActivity (sessionActivity);
  }

  /**
   * The [getSessionActivities] method...
   */
  public List<ActivityEvent> getSessionActivities() {
    return sessionAccumulator.getSessionActivities();
  }

  public Id getCourseId() {
    return courseId;
  }

  public Id getUserId() {
    return userId;
  }

  public String getSessionId() {
    return sessionId;
  }
}
