package session;

import java.util.List;

import blackboard.persist.Id;

import _error.SessionException;
import activity.ActivityEvent;

/**
 * The [SingleUserSession] class...
 */
public class SingleUserSession implements Comparable {
  protected final Id userId;

  protected final String sessionId;

  SessionAccumulator sessionAccumulator;

  /**
   * The [SingleUserSession] constructor...
   */
  public SingleUserSession (Id userId, String sessionId) {
    this.userId = userId;
    this.sessionId = sessionId;

    sessionAccumulator = new SessionAccumulator (this.sessionId);
  }

  /**
   * The [addSessionActivity] method...
   */
  public void addSessionActivity (ActivityEvent sessionActivity)
      throws SessionException {
    String normalizedUserId = userId.getExternalString().split ("_")[1];

    if (!(sessionActivity.getUserPk1().equals (normalizedUserId) &&
          sessionActivity.getSessionId().equals (sessionId))) {
      throw new SessionException (
        "The session event does not correspond to the correct user session."
      );
    }

    sessionAccumulator.addSessionActivity (sessionActivity);
  }

  /**
   * The [compareTo] method...
   */
  @Override
  public int compareTo (Object other) {
    return sessionId.compareTo (((SingleUserSession) other).getSessionId());
  }

  /**
   * The [getSessionActivities] method...
   */
  public List<ActivityEvent> getSessionActivities() {
    return sessionAccumulator.getSessionActivities();
  }

  /**
   * The [getSessionSize] method...
   */
  public int getSessionSize() {
    return sessionAccumulator.getSessionSize();
  }

  public Id getUserId() {
    return userId;
  }

  public String getSessionId() {
    return sessionId;
  }
}
