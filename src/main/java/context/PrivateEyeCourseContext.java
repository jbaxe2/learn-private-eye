package context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import blackboard.data.course.Course;
import blackboard.data.course.CourseMembership;
import blackboard.data.user.User;

import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.user.UserDbLoader;
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

  private Map<Id, SimpleUser> users;

  private Map<Id, SimpleMembership> memberships;

  /**
   * The [PrivateEyeCourseContext] constructor...
   */
  public PrivateEyeCourseContext (Id courseId) {
    this.courseId = courseId;

    users = new HashMap<>();
    memberships = new HashMap<>();
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
   * The [loadCourseUsers] method...
   */
  public void loadCourseUsers (UserDbLoader loader) throws PersistenceException {
    List<User> users = loader.loadByCourseId (courseId);

    for (User user : users) {
      this.users.put (user.getId(), new SimpleUser (user));
    }
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

      this.memberships.put (membership.getId(), simpleMembership);
    }
  }

  /**
   * The [getUser] method...
   */
  public SimpleUser getUser (Id userId) {
    return users.get (userId);
  }

  public Id getContextId() {
    return courseId;
  }

  public SimpleCourse getCourse() {
    return course;
  }

  public Map<Id, SimpleUser> getUsers() {
    return users;
  }

  public Map<Id, SimpleMembership> getMemberships() {
    return memberships;
  }
}
