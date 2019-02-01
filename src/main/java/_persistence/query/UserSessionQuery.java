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
  public UserSessionQuery (
    Id userId, Connection connection, Connection statsConnection
  ) {
    this.userId = userId;

    builder = new UserSessionQueryBuilder (userId, connection, statsConnection);
  }

  /**
   * The [retrieveSuccessfulLogins] method...
   */
  public UserSessionsCollection retrieveSuccessfulLogins()
      throws SQLException, SessionException {
    PreparedStatement preparedStatement = builder.retrieveSuccessfulLogins();
    executor = new UserSessionQueryExecutor (userId, preparedStatement, false);

    return executor.retrieveSuccessfulLogins();
  }

  /**
   * The [retrieveStatsSuccessfulLogins] method...
   */
  public UserSessionsCollection retrieveStatsSuccessfulLogins()
      throws SQLException, SessionException {
    PreparedStatement preparedStatement = builder.retrieveStatsSuccessfulLogins();
    executor = new UserSessionQueryExecutor (userId, preparedStatement, true);

    return executor.retrieveSuccessfulLogins();
  }

  /**
   * The [retrieveNumberOfSessions] method...
   */
  public List<SimpleCourseUserSessionCount> retrieveNumberOfSessions()
      throws SQLException {
    PreparedStatement preparedStatement = builder.retrieveNumberOfSessions();
    executor = new UserSessionQueryExecutor (userId, preparedStatement, false);

    return executor.retrieveNumberOfSessions();
  }

  /**
   * The [retrieveStatsNumberOfSessions] method...
   */
  public List<SimpleCourseUserSessionCount> retrieveStatsNumberOfSessions()
      throws SQLException {
    PreparedStatement preparedStatement = builder.retrieveStatsNumberOfSessions();
    executor = new UserSessionQueryExecutor (userId, preparedStatement, true);

    return executor.retrieveNumberOfSessions();
  }

  /**
   * The [retrieveSystemSessions] method...
   */
  public UserSessionsCollection retrieveSystemSessions()
      throws SQLException, SessionException {
    PreparedStatement preparedStatement = builder.retrieveSystemSessions();
    executor = new UserSessionQueryExecutor (userId, preparedStatement, false);

    return executor.retrieveSystemSessions();
  }

  /**
   * The [retrieveStatsSystemSessions] method...
   */
  public UserSessionsCollection retrieveStatsSystemSessions()
      throws SQLException, SessionException {
    PreparedStatement preparedStatement = builder.retrieveStatsSystemSessions();
    executor = new UserSessionQueryExecutor (userId, preparedStatement, true);

    return executor.retrieveSystemSessions();
  }

  /**
   * The [retrieveSystemSession] method...
   */
  public SingleUserSession retrieveSystemSession (String sessionId)
      throws SQLException, SessionException {
    PreparedStatement preparedStatement =
      builder.retrieveSystemSession (sessionId);

    executor = new UserSessionQueryExecutor (userId, preparedStatement, false);

    return executor.retrieveSystemSession (sessionId);
  }

  /**
   * The [retrieveStatsSystemSession] method...
   */
  public SingleUserSession retrieveStatsSystemSession (String sessionId)
      throws SQLException, SessionException {
    PreparedStatement preparedStatement =
      builder.retrieveStatsSystemSession (sessionId);

    executor = new UserSessionQueryExecutor (userId, preparedStatement, true);

    return executor.retrieveSystemSession (sessionId);
  }
}
