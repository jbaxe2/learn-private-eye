package _context;

import java.util.HashMap;
import java.util.Map;

import blackboard.data.user.User;

import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.user.UserDbLoader;
import blackboard.persist.Id;
import blackboard.persist.PersistenceException;

import course.SimpleCourse;
import user.SimpleUser;

/**
 * The [PrivateEyeUserContext] class...
 */
public class PrivateEyeUserContext implements PrivateEyeContext {
  private Id userId;

  private SimpleUser user;

  private Map<Id, SimpleCourse> loadedCourses;

  /**
   * The [PrivateEyeUserContext] constructor...
   */
  public PrivateEyeUserContext (Id userId) {
    this.userId = userId;

    this.loadedCourses = new HashMap<>();
  }

  /**
   * The [loadUser] method...
   */
  public void loadContextUserById (UserDbLoader loader) throws PersistenceException {
    user = new SimpleUser (loader.loadById (userId));
  }

  /**
   * The [loadContextUserByBatchUid] method...
   */
  public void loadContextUserByBatchUid (
    UserDbLoader loader, String batchUid
  ) throws PersistenceException {
    user = new SimpleUser (loader.loadByBatchUid (batchUid));

    _setUserId();
  }

  /**
   * The [loadContextUserByUsername] method...
   */
  public void loadContextUserByUsername (
    UserDbLoader loader, String username
  ) throws PersistenceException {
    user = new SimpleUser (loader.loadByUserName (username));

    _setUserId();
  }

  /**
   * The [loadCourseForContext] method...
   */
  public SimpleCourse loadCourseForContext (
    CourseDbLoader loader, Id courseId
  ) throws PersistenceException {
    if (!loadedCourses.containsKey (courseId)) {
      SimpleCourse course = new SimpleCourse (loader.loadById (courseId));

      loadedCourses.put (courseId, course);
    }

    return loadedCourses.get (courseId);
  }

  public Id getContextId() {
    return userId;
  }

  public SimpleUser getUser() {
    return user;
  }

  /**
   * The [_setUserId] method...
   */
  private void _setUserId () {
    userId = Id.toId (User.DATA_TYPE, user.getPk1());
  }
}
