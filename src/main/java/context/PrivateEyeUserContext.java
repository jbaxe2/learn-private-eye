package context;

import blackboard.data.user.User;

import blackboard.persist.user.UserDbLoader;
import blackboard.persist.Id;
import blackboard.persist.PersistenceException;

import user.SimpleUser;

/**
 * The [PrivateEyeUserContext] class...
 */
public class PrivateEyeUserContext implements PrivateEyeContext {
  private final Id userId;

  private SimpleUser user;

  /**
   * The [PrivateEyeUserContext] constructor...
   */
  public PrivateEyeUserContext (Id userId) {
    this.userId = userId;
  }

  /**
   * The [loadUser] method...
   */
  public void loadContextUserById (UserDbLoader loader) throws PersistenceException {
    User bbUser = loader.loadById (userId);

    _createContextUser (bbUser);
  }

  /**
   * The [loadContextUserByBatchUid] method...
   */
  public void loadContextUserByBatchUid (
    UserDbLoader loader, String batchUid
  ) throws PersistenceException {
    User bbUser = loader.loadByBatchUid (batchUid);

    _createContextUser (bbUser);
  }

  /**
   * The [loadContextUserByUsername] method...
   */
  public void loadContextUserByUsername (
    UserDbLoader loader, String username
  ) throws PersistenceException {
    User bbUser = loader.loadByUserName (username);

    _createContextUser (bbUser);
  }

  public Id getContextId() {
    return userId;
  }

  public SimpleUser getUser() {
    return user;
  }

  /**
   * The [_createContextUser] method...
   */
  private void _createContextUser (User bbUser) {
    user = new SimpleUser (
      bbUser.getId().getExternalString(), bbUser.getUserName(), bbUser.getBatchUid(),
      bbUser.getStudentId(), bbUser.getSystemRoleIdentifier(), bbUser.getGivenName(),
      bbUser.getFamilyName(), bbUser.getEmailAddress(), bbUser.getIsAvailable()
    );
  }
}
