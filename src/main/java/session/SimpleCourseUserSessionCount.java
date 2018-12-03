package session;

/**
 * The [SimpleCourseUserSessionCount] class...
 */
public class SimpleCourseUserSessionCount {
  private final String coursePk1;

  private final String userPk1;

  private final int sessionCount;

  /**
   * The [SimpleCourseUserSessionCount] constructor...
   */
  public SimpleCourseUserSessionCount (String coursePk1, String userPk1, int sessionCount) {
    this.coursePk1 = coursePk1;
    this.userPk1 = userPk1;
    this.sessionCount = sessionCount;
  }
}
