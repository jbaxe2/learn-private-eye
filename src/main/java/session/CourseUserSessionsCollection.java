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
public class CourseUserSessionsCollection {
  private Map<String, SingleCourseUserSession> courseSessions;

  private static CourseUserSessionsCollection _instance;

  /**
   * The [getInstance] static method...
   */
  public static CourseUserSessionsCollection getInstance() {
    if (null == _instance) {
      _instance = new CourseUserSessionsCollection();
    }

    return _instance;
  }

  /**
   * The [CourseUserSessionsCollection] constructor...
   */
  private CourseUserSessionsCollection() {
    courseSessions = new HashMap<>();
  }

  /**
   * The [pushSessionEventToCollection] method...
   */
  public void pushSessionEventToCollection (
    ActivityEvent sessionEvent
  ) throws SessionException {
    String sessionId = sessionEvent.getSessionId();

    if (!courseSessions.containsKey (sessionId)) {
      Id courseId = Id.toId (Course.DATA_TYPE, sessionEvent.getCoursePk1());
      Id userId = Id.toId (User.DATA_TYPE, sessionEvent.getUserPk1());

      courseSessions.put (
        sessionId,
        new SingleCourseUserSession (courseId, userId, sessionId)
      );
    }

    courseSessions.get (sessionId).addSessionActivity (sessionEvent);
  }

  /**
   * The [clearCourseSessions] method...
   */
  public void clearCourseSessions() {
    courseSessions.clear();
  }

  public Map<String, SingleCourseUserSession> getCourseSessions() {
    return new HashMap<>(courseSessions);
  }
}
