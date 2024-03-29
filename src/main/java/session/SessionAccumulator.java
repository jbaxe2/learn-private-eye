package session;

import java.util.List;

import _error.SessionException;

import activity.ActivityAccumulator;
import activity.ActivityEvent;

/**
 * The [SessionAccumulator] class...
 */
public class SessionAccumulator {
  protected final String sessionId;

  private ActivityAccumulator activityAccumulator;

  /**
   * The [SessionAccumulator] constructor...
   */
  protected SessionAccumulator (String sessionId) {
    this.sessionId = sessionId;

    activityAccumulator = new ActivityAccumulator();
  }

  /**
   * The [addSessionActivity] method...
   */
  protected void addSessionActivity (ActivityEvent sessionEvent) throws SessionException {
    if (!sessionId.equals (sessionEvent.getSessionId())) {
      throw new SessionException (
        "Unable to add a session activity event that does not belong to the session."
      );
    }

    activityAccumulator.addActivityEvent (sessionEvent);
  }

  /**
   * The [getSessionActivities] method...
   */
  List<ActivityEvent> getSessionActivities() {
    return activityAccumulator.getActivityEvents();
  }

  /**
   * The [getSessionSize] method...
   */
  int getSessionSize() {
    return activityAccumulator.getEventsCount();
  }

  public String getSessionId() {
    return sessionId;
  }
}
