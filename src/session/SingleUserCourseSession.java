package session;

import blackboard.persist.Id;

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
}
