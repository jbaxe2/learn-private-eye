package session;

import blackboard.persist.Id;

import _error.SessionException;
import activity.ActivityEvent;

/**
 * The [SingleUserCourseSession] class...
 */
public class SingleUserCourseSession {
  private final Id courseId;

  private final Id userId;

  private final String sessionId;

  private SessionAccumulator sessionAccumulator;

  /**
   * The [SingleUserCourseSession] constructor...
   */
  public SingleUserCourseSession (Id courseId, Id userId, String sessionId) {
    this.courseId = courseId;
    this.userId = userId;
    this.sessionId = sessionId;

    sessionAccumulator = new SessionAccumulator (this.sessionId);
  }

  /**
   * The [addSessionActivity] method...
   */
  public void addSessionActivity (ActivityEvent sessionActivity) throws SessionException {
    if (!(sessionActivity.getUserPk1().equals (userId.getExternalString()) &&
          sessionActivity.getCoursePk1().equals (courseId.getExternalString()) &&
          sessionActivity.getSessionId().equals (sessionId))) {
      throw new SessionException (
        "The session event does not correspond to the correct user and course session."
      );
    }

    sessionAccumulator.addSessionActivity (sessionActivity);
  }
}
