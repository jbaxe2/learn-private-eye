package session;

import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import blackboard.data.user.User;
import blackboard.persist.Id;

import _error.SessionException;
import activity.ActivityEvent;

/**
 * The [UserSessionsCollection] class...
 */
public class UserSessionsCollection {
  Map<String, SingleUserSession> userSessions;

  /**
   * The [UserSessionsCollection] private constructor...
   */
  public UserSessionsCollection() {
    userSessions = new TreeMap<>(Collections.reverseOrder());
  }

  /**
   * The [pushSessionEventToCollection] method...
   */
  public void pushSessionEventToCollection (ActivityEvent sessionEvent)
      throws SessionException {
    String sessionId = sessionEvent.getSessionId();

    if (!userSessions.containsKey (sessionId)) {
      Id userId = Id.toId (User.DATA_TYPE, sessionEvent.getUserPk1());

      userSessions.put (
        sessionId,
        new SingleUserSession (userId, sessionId)
      );
    }

    userSessions.get (sessionId).addSessionActivity (sessionEvent);
  }

  /**
   * The [clearSessions] method...
   */
  public void clearSessions() {
    userSessions.clear();
  }

  public Map<String, SingleUserSession> getUserSessions() {
    return new TreeMap<>((SortedMap<String, SingleUserSession>)userSessions);
  }
}
