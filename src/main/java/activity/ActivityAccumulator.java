package activity;

import java.util.ArrayList;
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

  public List<ActivityEvent> getActivityEvents() {
    return new ArrayList<>(activityEvents);
  }
}
