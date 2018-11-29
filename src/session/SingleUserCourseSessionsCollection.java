package session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The [SingleUserCourseSessionsCollection] class...
 */
public class SingleUserCourseSessionsCollection {
  Map<String, List<SingleUserCourseSession>> courseSessions;

  /**
   * The [SingleUserCourseSessionsCollection] constructor...
   */
  public SingleUserCourseSessionsCollection() {
    courseSessions = new HashMap<String, List<SingleUserCourseSession>>();
  }

  /**
   * The [pushSingleUserCourseSession] method...
   */
  public void pushSingleUserCourseSession (String sessionId, SingleUserCourseSession sessionEvent) {
    if (courseSessions.containsKey (sessionId)) {
      courseSessions.get (sessionId).add (sessionEvent);
    } else {
      List sessionList = new ArrayList<SingleUserCourseSession>();
      sessionList.add (sessionEvent);

      courseSessions.putIfAbsent (sessionId, sessionList);
    }
  }

  public Map<String, List<SingleUserCourseSession>> getCourseSessions() {
    return new HashMap<String, List<SingleUserCourseSession>>(courseSessions);
  }
}
