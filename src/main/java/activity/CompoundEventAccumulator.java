package activity;

import java.util.Date;

import _error.SessionException;
import session.SessionAccumulator;

/**
 * The [CompoundEventAccumulator] class...
 */
public class CompoundEventAccumulator extends SessionAccumulator {
  private final Date timestamp;

  /**
   * The [CompoundEventAccumulator] constructor...
   */
  CompoundEventAccumulator (String sessionId, Date timestamp) {
    super (sessionId);

    this.timestamp = timestamp;
  }

  /**
   * The [addSessionActivity] method...
   */
  @Override
  public void addSessionActivity (ActivityEvent compoundEvent) throws SessionException {
    if (!timestamp.equals (compoundEvent.getTimestamp())) {
      throw new SessionException (
        "Unable to add a compound event that does not contain the required timestamp."
      );
    }

    super.addSessionActivity (compoundEvent);
  }
}
