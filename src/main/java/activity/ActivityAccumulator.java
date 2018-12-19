package activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The [ActivityAccumulator] class...
 */
public class ActivityAccumulator {
  private List<ActivityEvent> activityEvents;

  /**
   * The [ActivityAccumulator] constructor...
   */
  public ActivityAccumulator() {
    activityEvents = new ArrayList<>();
  }

  /**
   * The [addActivityEvent] method...
   */
  public void addActivityEvent (ActivityEvent event) {
    if (!activityEvents.contains (event)) {
      activityEvents.add (event);
    }
  }

  /**
   * The [getEventsCount] method...
   */
  public int getEventsCount() {
    return activityEvents.size();
  }

  public List<ActivityEvent> getActivityEvents() {
    sortActivities();

    return new ArrayList<>(activityEvents);
  }

  /**
   * The [sortActivities] method...
   */
  private void sortActivities() {
    activityEvents.sort (Collections.reverseOrder());
  }
}
