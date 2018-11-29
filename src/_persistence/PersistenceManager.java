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

  private BbPersistenceManager persistenceManager;

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

    if (type.equals (CourseDbLoader.TYPE)) {
      loader = persistenceManager.getLoader (CourseDbLoader.TYPE);
    } else if (type.equals (UserDbLoader.TYPE)) {
      loader = persistenceManager.getLoader (UserDbLoader.TYPE);
    } else if (type.equals (CourseMembershipDbLoader.TYPE)) {
      loader = persistenceManager.getLoader (EnrollmentDbLoader.TYPE);
    } else {
      throw new PersistenceException (
        "The loader type (" + type +") is not supported."
      );
    }

    return loader;
  }

  /**
   * The [closeConnection] method...
   */
  public void closeConnection() {
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
    persistenceManager = PersistenceServiceFactory.getInstance().getDbPersistenceManager();
  }
}
