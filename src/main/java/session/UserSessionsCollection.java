package session;

import java.util.HashMap;
import java.util.Map;

import blackboard.data.user.User;
import blackboard.persist.Id;

import _error.SessionException;
import activity.ActivityEvent;
/**
 * The [UserSessionsCollection] class...
 */
public class UserSessionsCollection {
  Map<String, SingleUserSession> userSessions;

  private static UserSessionsCollection _instance;

  /**
   * The [UserSessionsCollection] private constructor...
   */
  UserSessionsCollection() {
    userSessions = new HashMap<>();
  }

  /**
   * The [getInstance] method...
   */
  public static UserSessionsCollection getInstance() {
    if (null == _instance) {
      _instance = new UserSessionsCollection();
    }

    return _instance;
  }

  /**
   * The [pushSessionEventToCollection] method...
   */
  public void pushSessionEventToCollection (
    ActivityEvent sessionEvent
  ) throws SessionException {
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
    return new HashMap<>(userSessions);
  }
}
