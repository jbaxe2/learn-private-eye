package _persistence;

import java.sql.Connection;

import blackboard.admin.persist.course.impl.EnrollmentDbLoader;

import blackboard.db.BbDatabase;
import blackboard.db.ConnectionManager;
import blackboard.db.ConnectionNotAvailableException;

import blackboard.persist.course.CourseDbLoader;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.user.UserDbLoader;
import blackboard.persist.BbPersistenceManager;
import blackboard.persist.Loader;
import blackboard.persist.PersistenceException;

import blackboard.platform.persistence.PersistenceServiceFactory;

/**
 * The [PersistenceManager] class...
 */
public class PersistenceManager {
  private ConnectionManager connectionManager;

  private BbPersistenceManager bbPersistenceManager;

  private Connection connection;

  /**
   * The [PersistenceManager] constructor...
   */
  public PersistenceManager() throws ConnectionNotAvailableException {
    _establishDatabase();
    _establishLoaderManager();
  }

  /**
   * The [retrieveLoader] method...
   */
  public Loader retrieveLoader (String type) throws PersistenceException {
    Loader loader = null;

    switch (type) {
      case CourseDbLoader.TYPE:
        loader = bbPersistenceManager.getLoader (CourseDbLoader.TYPE);
        break;
      case UserDbLoader.TYPE:
        loader = bbPersistenceManager.getLoader (UserDbLoader.TYPE);
        break;
      case CourseMembershipDbLoader.TYPE:
        loader = bbPersistenceManager.getLoader (EnrollmentDbLoader.TYPE);
        break;
      default:
        throw new PersistenceException (
            "The loader type (" + type + ") is not supported."
        );
    }

    return loader;
  }

  /**
   * The [releaseConnection] method...
   */
  public void releaseConnection () {
    if (null != connectionManager) {
      connectionManager.releaseConnection (connection);
    }
  }

  public Connection getConnection() {
    return connection;
  }

  /**
   * The [_establishDatabase] method...
   */
  private void _establishDatabase() throws ConnectionNotAvailableException {
    connectionManager = BbDatabase.getDefaultInstance().getConnectionManager();
    connection = connectionManager.getConnection();
  }

  /**
   * The [_establishLoaderManager] method...
   */
  private void _establishLoaderManager() {
    bbPersistenceManager =
      PersistenceServiceFactory.getInstance().getDbPersistenceManager();
  }
}
