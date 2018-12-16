package session;

import blackboard.data.course.Course;
import blackboard.data.user.User;
import blackboard.persist.Id;

import _error.SessionException;
import activity.ActivityEvent;

/**
 * The [CourseUserSessionsCollection] class...
 */
public class CourseUserSessionsCollection extends UserSessionsCollection {
  /**
   * The [CourseUserSessionsCollection] constructor...
   */
  private CourseUserSessionsCollection() {
    super();
  }

  /**
   * The [getInstance] static method...
   */
  public static CourseUserSessionsCollection getInstance() {
    return (CourseUserSessionsCollection)UserSessionsCollection.getInstance();
  }

  /**
   * The [pushSessionEventToCollection] method...
   */
  @Override
  public void pushSessionEventToCollection (
    ActivityEvent sessionEvent
  ) throws SessionException {
    String sessionId = sessionEvent.getSessionId();

    if (!userSessions.containsKey (sessionId)) {
      Id courseId = Id.toId (Course.DATA_TYPE, sessionEvent.getCoursePk1());
      Id userId = Id.toId (User.DATA_TYPE, sessionEvent.getUserPk1());

      userSessions.put (
        sessionId,
        new SingleCourseUserSession (courseId, userId, sessionId)
      );
    }

    userSessions.get (sessionId).addSessionActivity (sessionEvent);
  }
}
