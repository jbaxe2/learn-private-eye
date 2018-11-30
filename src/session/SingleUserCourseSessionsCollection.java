package session;

import java.util.HashMap;
import java.util.Map;

import blackboard.data.course.Course;
import blackboard.data.user.User;
import blackboard.persist.Id;

import _error.SessionException;
import activity.ActivityEvent;

/**
 * The [SingleUserCourseSessionsCollection] class...
 */
public class SingleUserCourseSessionsCollection {
  private Map<String, SingleUserCourseSession> courseSessions;

  private static SingleUserCourseSessionsCollection _instance;

  /**
   * The [getInstance] static method...
   */
  public static SingleUserCourseSessionsCollection getInstance() {
    if (null == _instance) {
      _instance = new SingleUserCourseSessionsCollection();
    }

    return _instance;
  }

  /**
   * The [SingleUserCourseSessionsCollection] constructor...
   */
  private SingleUserCourseSessionsCollection() {
    courseSessions = new HashMap<String, SingleUserCourseSession>();
  }

  /**
   * The [pushSessionEventToCollection] method...
   */
  public void pushSessionEventToCollection (ActivityEvent sessionEvent) throws SessionException {
    String sessionId = sessionEvent.getSessionId();

    if (!courseSessions.containsKey (sessionId)) {
      Id courseId = Id.toId (Course.DATA_TYPE, sessionEvent.getCoursePk1());
      Id userId = Id.toId (User.DATA_TYPE, sessionEvent.getUserPk1());

      courseSessions.put (sessionId, new SingleUserCourseSession (courseId, userId, sessionId));
    }

    courseSessions.get (sessionId).addSessionActivity (sessionEvent);
  }

  /**
   * The [clearCourseSessions] method...
   */
  public void clearCourseSessions() {
    courseSessions.clear();
  }

  public Map<String, SingleUserCourseSession> getCourseSessions() {
    return new HashMap<String, SingleUserCourseSession>(courseSessions);
  }
}
