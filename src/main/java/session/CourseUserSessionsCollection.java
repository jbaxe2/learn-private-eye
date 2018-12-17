package session;

import java.util.HashMap;
import java.util.Map;

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
    if (null == _instance) {
      _instance = new CourseUserSessionsCollection();
    }

    return (CourseUserSessionsCollection)_instance;
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
        sessionId, new SingleCourseUserSession (courseId, userId, sessionId)
      );
    }

    userSessions.get (sessionId).addSessionActivity (sessionEvent);
  }

  /**
   * The [getCourseSessions] method...
   */
  public Map<String, SingleCourseUserSession> getCourseSessions() {
    Map<String, SingleCourseUserSession> courseSessions = new HashMap<>();
    Map<String, SingleUserSession> userSessions = getUserSessions();

    for (Object sessionId : userSessions.keySet()) {
      courseSessions.put (
        (String)sessionId, (SingleCourseUserSession)(userSessions.get (sessionId))
      );
    }

    return courseSessions;
  }
}
