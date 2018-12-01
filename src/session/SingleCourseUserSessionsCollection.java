package session;

import java.util.HashMap;
import java.util.Map;

import blackboard.data.course.Course;
import blackboard.data.user.User;
import blackboard.persist.Id;

import _error.SessionException;
import activity.ActivityEvent;

/**
 * The [SingleCourseUserSessionsCollection] class...
 */
public class SingleCourseUserSessionsCollection {
  private Map<String, SingleCourseUserSession> courseSessions;

  private static SingleCourseUserSessionsCollection _instance;

  /**
   * The [getInstance] static method...
   */
  public static SingleCourseUserSessionsCollection getInstance() {
    if (null == _instance) {
      _instance = new SingleCourseUserSessionsCollection ();
    }

    return _instance;
  }

  /**
   * The [SingleCourseUserSessionsCollection] constructor...
   */
  private SingleCourseUserSessionsCollection () {
    courseSessions = new HashMap<String, SingleCourseUserSession>();
  }

  /**
   * The [pushSessionEventToCollection] method...
   */
  public void pushSessionEventToCollection (ActivityEvent sessionEvent) throws SessionException {
    String sessionId = sessionEvent.getSessionId();

    if (!courseSessions.containsKey (sessionId)) {
      Id courseId = Id.toId (Course.DATA_TYPE, sessionEvent.getCoursePk1());
      Id userId = Id.toId (User.DATA_TYPE, sessionEvent.getUserPk1());

      courseSessions.put (sessionId, new SingleCourseUserSession (courseId, userId, sessionId));
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
    return new HashMap<String, SingleCourseUserSession>(courseSessions);
  }
}
