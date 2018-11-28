package context;

import java.util.ArrayList;
import java.util.List;

import com.sun.istack.internal.NotNull;

import blackboard.data.course.CourseMembership;
import blackboard.data.course.Course;
import blackboard.data.user.User;
import blackboard.persist.Id;

import blackboard.persist.PersistenceException;
import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.user.UserDbLoader;

import course.SimpleCourse;
import membership.SimpleMembership;
import user.SimpleUser;

/**
 * The [PrivateEyeContext] class...
 */
public class PrivateEyeContext {
  private Id courseId;

  private SimpleCourse course;

  private SimpleUser contextUser;

  private List<SimpleUser> users;

  private List<SimpleMembership> memberships;

  /**
   * The [PrivateEyeContext] constructor...
   */
  public PrivateEyeContext (Id courseId) {
    this.courseId = courseId;

    users = new ArrayList<SimpleUser>();
    memberships = new ArrayList<SimpleMembership>();
  }

  /**
   * The [loadUser] method...
   */
  public void loadContextUserById (
    UserDbLoader loader, Id userId
  ) throws PersistenceException {
    User user = loader.loadById (userId);

    _createContextUser (user);
  }

  /**
   * The [loadContextUserByBatchUid] method...
   */
  public void loadContextUserByBatchUid (
    UserDbLoader loader, String batchUid
  ) throws PersistenceException {
    User user = loader.loadByBatchUid (batchUid);

    _createContextUser (user);
  }

  /**
   * The [loadContextUserByUsername] method...
   */
  public void loadContextUserByUsername (
    UserDbLoader loader, String username
  ) throws PersistenceException {
    User user = loader.loadByUserName (username);

    _createContextUser (user);
  }

  /**
   * The [loadUsersFromMemberships] method...
   */
  public void loadUsersFromMemberships (UserDbLoader loader) throws PersistenceException {
    ;
  }

  public SimpleCourse getCourse() {
    return course;
  }

  public SimpleUser getContextUser() {
    return contextUser;
  }

  public List<SimpleUser> getUsers() {
    return users;
  }

  public List<SimpleMembership> getMemberships() {
    return memberships;
  }

  /**
   * The [_createContextUser] method...
   */
  private void _createContextUser (@NotNull User user) {
    contextUser = new SimpleUser (
        user.getId().getExternalString(), user.getUserName(), user.getBatchUid(),
        user.getStudentId(), user.getSystemRoleIdentifier(), user.getGivenName(),
        user.getFamilyName(), user.getEmailAddress(), user.getIsAvailable()
    );
  }
}
