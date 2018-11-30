package session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The [SingleUserCourseSessionsCollection] class...
 */
public class SingleUserCourseSessionsCollection {
  private Map<String, List<SingleUserCourseSession>> courseSessions;

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
    courseSessions = new HashMap<String, List<SingleUserCourseSession>>();
  }

  /**
   * The [pushSingleUserCourseSession] method...
   */
  public void pushSingleUserCourseSession (String sessionId, SingleUserCourseSession sessionEvent) {
    if (courseSessions.containsKey (sessionId)) {
      courseSessions.get (sessionId).add (sessionEvent);
    } else {
      List<SingleUserCourseSession> sessionList = new ArrayList<SingleUserCourseSession>();
      sessionList.add (sessionEvent);

      courseSessions.put (sessionId, sessionList);
    }
  }

  public Map<String, List<SingleUserCourseSession>> getCourseSessions() {
    return new HashMap<String, List<SingleUserCourseSession>>(courseSessions);
  }
}
