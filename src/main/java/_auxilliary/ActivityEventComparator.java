package _auxilliary;

import java.util.Comparator;

import activity.ActivityEvent;

/**
 * The [ActivityEventComparator] class...
 */
public class ActivityEventComparator implements Comparator<ActivityEvent> {
  /**
   * The [compare] method...
   */
  @Override
  public int compare (ActivityEvent event1, ActivityEvent event2) {
    return event1.compareTo (event2);
  }
}
