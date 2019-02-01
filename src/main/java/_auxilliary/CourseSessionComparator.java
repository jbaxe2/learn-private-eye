package _auxilliary;

import java.util.Comparator;

import session.SimpleCourseUserSessionCount;

/**
 * The [CourseSessionComparator] class...
 */
public class CourseSessionComparator
    implements Comparator<SimpleCourseUserSessionCount> {
  /**
   * The [compare] method...
   */
  public int compare (
    SimpleCourseUserSessionCount count1, SimpleCourseUserSessionCount count2
  ) {
    if ((null != count1.getCoursePk1()) && (null != count2.getCoursePk1())) {
      return count1.getCoursePk1().compareTo (count2.getCoursePk1());
    }

    return count1.getUserPk1().compareTo (count2.getUserPk1());
  }
}
