package context;

import java.util.ArrayList;
import java.util.List;

import blackboard.data.course.Course;
import blackboard.data.course.CourseMembership;

import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.Id;
import blackboard.persist.PersistenceException;

import course.SimpleCourse;
import membership.SimpleMembership;
import user.SimpleUser;

/**
 * The [PrivateEyeCourseContext] class...
 */
public class PrivateEyeCourseContext implements PrivateEyeContext {
  private final Id courseId;

  private SimpleCourse course;

  private List<SimpleUser> users;

  private List<SimpleMembership> memberships;

  /**
   * The [PrivateEyeCourseContext] constructor...
   */
  public PrivateEyeCourseContext (Id courseId) {
    this.courseId = courseId;

    users = new ArrayList<SimpleUser>();
    memberships = new ArrayList<SimpleMembership>();
  }

  /**
   * The [loadCourse] method...
   */
  public void loadCourse (CourseDbLoader loader) throws PersistenceException {
    Course bbCourse = loader.loadById (courseId);

    course = new SimpleCourse (
      bbCourse.getId().getExternalString(), bbCourse.getCourseId(), bbCourse.getBatchUid(),
      bbCourse.getTitle(), bbCourse.getIsAvailable()
    );
  }

  /**
   * The [loadMemberships] method...
   */
  public void loadMemberships (CourseMembershipDbLoader loader) throws PersistenceException {
    List<CourseMembership> memberships = loader.loadByCourseId (courseId);

    for (CourseMembership membership : memberships) {
      SimpleMembership simpleMembership = new SimpleMembership (
        membership.getId().getExternalString(), membership.getCourseId().getExternalString(),
        membership.getUserId().getExternalString(), membership.getRoleAsString(),
        membership.getIsAvailable(), membership.getEnrollmentDate().getTime(),
        membership.getLastAccessDate().getTime()
      );

      this.memberships.add (simpleMembership);
    }
  }

  public Id getContextId() {
    return courseId;
  }

  public SimpleCourse getCourse() {
    return course;
  }

  public List<SimpleUser> getUsers() {
    return users;
  }

  public List<SimpleMembership> getMemberships() {
    return memberships;
  }
}
