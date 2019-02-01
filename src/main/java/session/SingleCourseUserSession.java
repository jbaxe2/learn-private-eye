package session;

import blackboard.persist.Id;

import _error.SessionException;
import activity.ActivityEvent;

/**
 * The [SingleCourseUserSession] class...
 */
public class SingleCourseUserSession extends SingleUserSession {
  private final Id courseId;

  /**
   * The [SingleCourseUserSession] constructor...
   */
  public SingleCourseUserSession (Id courseId, Id userId, String sessionId) {
    super (userId, sessionId);

    this.courseId = courseId;
  }

  /**
   * The [addSessionActivity] method...
   */
  @Override
  public void addSessionActivity (ActivityEvent sessionActivity)
      throws SessionException {
    String normalizedUserId =
      userId.getExternalString().split ("_")[1];

    String normalizedCourseId =
      courseId.getExternalString().split ("_")[1];

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
   * The [containsStatsTracking] method...
   */
  public boolean containsStatsTracking() {
    boolean contains = false;

    for (ActivityEvent event : sessionAccumulator.getSessionActivities()) {
      if (event.getForStats()) {
        contains = true;

        break;
      }
    }

    return contains;
  }

  public Id getCourseId() {
    return courseId;
  }
}
