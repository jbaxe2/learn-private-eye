package membership;

import java.util.Date;

/**
 * The [SimpleMembership] class...
 */
public class SimpleMembership {
  private final String pk1;

  private final String coursePk1;

  private final String userPk1;

  private final String role;

  private final boolean availableInd;

  private final Date enrollmentDate;

  private final Date lastAccess;
  /**
   * The [SimpleMembership] constructor...
   */
  public SimpleMembership (
    String pk1, String coursePk1, String userPk1, String role, boolean availableInd,
    Date enrollmentDate, Date lastAccess
  ) {
    this.pk1 = pk1;
    this.coursePk1 = coursePk1;
    this.userPk1 = userPk1;
    this.role = role;
    this.availableInd = availableInd;
    this.enrollmentDate = enrollmentDate;
    this.lastAccess = lastAccess;
  }

  public String getPk1() {
    return pk1;
  }

  public String getCoursePk1() {
    return coursePk1;
  }

  public String getUserPk1() {
    return userPk1;
  }

  public String getRole() {
    return role;
  }

  public boolean isAvailableInd() {
    return availableInd;
  }

  public Date getEnrollmentDate() {
    return enrollmentDate;
  }

  public Date getLastAccess() {
    return lastAccess;
  }
}
