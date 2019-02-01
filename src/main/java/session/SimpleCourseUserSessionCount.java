package session;

/**
 * The [SimpleCourseUserSessionCount] class...
 */
public class SimpleCourseUserSessionCount {
  private final String coursePk1;

  private final String userPk1;

  private final int sessionCount;

  private final boolean forStats;

  /**
   * The [SimpleCourseUserSessionCount] constructor...
   */
  public SimpleCourseUserSessionCount (
    String coursePk1, String userPk1, int sessionCount, boolean forStats
  ) {
    this.coursePk1 = coursePk1;
    this.userPk1 = userPk1;
    this.sessionCount = sessionCount;
    this.forStats = forStats;
  }

  public String getCoursePk1() {
    return coursePk1;
  }

  public String getUserPk1() {
    return userPk1;
  }

  public int getSessionCount() {
    return sessionCount;
  }

  public boolean getForStats() {
    return forStats;
  }
}
