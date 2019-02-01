package _auxilliary;

import session.SingleUserSession;

import java.util.Comparator;
import java.util.Date;

/**
 * The [SingleUserSessionComparator] class...
 */
public class SingleUserSessionComparator implements Comparator<SingleUserSession> {
  /**
   * The [compare] method...
   */
  @Override
  public int compare (SingleUserSession session1, SingleUserSession session2) {
    Date date1 = session1.getSessionActivities().get (0).getTimestamp();
    Date date2 = session2.getSessionActivities().get (0).getTimestamp();

    return date1.compareTo (date2);
  }
}
