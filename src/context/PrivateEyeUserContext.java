package context;

import blackboard.persist.Id;

import user.SimpleUser;

/**
 * The [PrivateEyeUserContext] class...
 */
public class PrivateEyeUserContext {
  private final Id userId;

  private SimpleUser user;

  /**
   * The [PrivateEyeUserContext] constructor...
   */
  public PrivateEyeUserContext (Id userId) {
    this.userId = userId;
  }
}
