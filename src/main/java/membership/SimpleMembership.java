package membership;

import java.util.Date;

import blackboard.data.course.CourseMembership;

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
    String pk1, String coursePk1, String userPk1, String role,
    boolean availableInd, Date enrollmentDate, Date lastAccess
  ) {
    this.pk1 = pk1;
    this.coursePk1 = coursePk1;
    this.userPk1 = userPk1;
    this.role = role;
    this.availableInd = availableInd;
    this.enrollmentDate = enrollmentDate;
    this.lastAccess = lastAccess;
  }

  /**
   * The [SimpleMembership] constructor...
   */
  public SimpleMembership (CourseMembership membership) {
    this.pk1 = membership.getId().getExternalString();
    this.coursePk1 = membership.getCourseId().getExternalString();
    this.userPk1 = membership.getUserId().getExternalString();
    this.role = membership.getRoleAsString();
    this.availableInd = membership.getIsAvailable();
    this.enrollmentDate = membership.getEnrollmentDate().getTime();
    this.lastAccess = membership.getLastAccessDate().getTime();
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
