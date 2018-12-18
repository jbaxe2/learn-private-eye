package _persistence.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.List;

import blackboard.persist.Id;

import _error.SessionException;

import _persistence.query.builder.UserSessionQueryBuilder;
import _persistence.query.executor.UserSessionQueryExecutor;

import session.SimpleCourseUserSessionCount;
import session.SingleUserSession;
import session.UserSessionsCollection;

/**
 * The [UserSessionQuery] class...
 */
public class UserSessionQuery {
  private final Id userId;

  private UserSessionQueryBuilder builder;

  private UserSessionQueryExecutor executor;

  /**
   * The [UserSessionQuery] constructor...
   */
  public UserSessionQuery (Id userId, Connection connection) {
    this.userId = userId;

    builder = new UserSessionQueryBuilder (userId, connection);
  }

  /**
   * The [retrieveSuccessfulLogins] method...
   */
  public UserSessionsCollection retrieveSuccessfulLogins()
      throws SQLException, SessionException {
    PreparedStatement preparedStatement = builder.retrieveSuccessfulLogins();
    executor = new UserSessionQueryExecutor (userId, preparedStatement);

    return executor.retrieveSuccessfulLogins();
  }

  /**
   * The [retrieveNumberOfSessions] method...
   */
  public List<SimpleCourseUserSessionCount> retrieveNumberOfSessions()
      throws SQLException {
    PreparedStatement preparedStatement = builder.retrieveNumberOfSessions();
    executor = new UserSessionQueryExecutor (userId, preparedStatement);

    return executor.retrieveNumberOfSessions();
  }

  /**
   * The [retrieveSystemSessions] method...
   */
  public UserSessionsCollection retrieveSystemSessions()
      throws SQLException, SessionException {
    PreparedStatement preparedStatement = builder.retrieveSystemSessions();
    executor = new UserSessionQueryExecutor (userId, preparedStatement);

    return executor.retrieveSystemSessions();
  }

  /**
   * The [retrieveSystemSession] method...
   */
  public SingleUserSession retrieveSystemSession (String sessionId)
      throws SQLException, SessionException {
    PreparedStatement preparedStatement =
      builder.retrieveSystemSession (sessionId);

    executor = new UserSessionQueryExecutor (userId, preparedStatement);

    return executor.retrieveSystemSession (sessionId);
  }
}
