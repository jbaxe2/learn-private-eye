package _persistence.query.executor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import blackboard.persist.Id;

import _error.SessionException;

import session.SimpleCourseUserSessionCount;
import session.SingleUserSession;
import session.UserSessionsCollection;

/**
 * The [UserSessionQueryExecutor] class...
 */
public class UserSessionQueryExecutor
    extends SessionQueryExecutor implements QueryExecutor {
  private final Id userId;

  private final PreparedStatement preparedStatement;

  /**
   * The [UserSessionQueryExecutor] constructor...
   */
  public UserSessionQueryExecutor (
    Id userId, PreparedStatement preparedStatement
  ) {
    this.userId = userId;
    this.preparedStatement = preparedStatement;
  }

  /**
   * The [retrieveSuccessfulLogins] method...
   */
  public UserSessionsCollection retrieveSuccessfulLogins()
      throws SQLException, SessionException {
    return _buildSessionsCollection();
  }

  /**
   * The [retrieveNumberOfSessions] method...
   */
  public List<SimpleCourseUserSessionCount> retrieveNumberOfSessions()
      throws SQLException {
    return super.retrieveNumberOfSessions (preparedStatement, null);
  }

  /**
   * The [retrieveSystemSessions] method...
   */
  public UserSessionsCollection retrieveSystemSessions()
      throws SQLException, SessionException {
    return _buildSessionsCollection();
  }

  /**
   * The [retrieveSystemSession] method...
   */
  public SingleUserSession retrieveSystemSession (String sessionId)
      throws SQLException, SessionException {
    ResultSet sessionResult = preparedStatement.executeQuery();

    SingleUserSession userSession = new SingleUserSession (userId, sessionId);

    while (sessionResult.next()) {
      userSession.addSessionActivity (_createActivityEvent (sessionResult));
    }

    return userSession;
  }

  /**
   * The [_buildSessionsCollection] method...
   */
  private UserSessionsCollection _buildSessionsCollection()
      throws SQLException, SessionException {
    UserSessionsCollection sessionsCollection = new UserSessionsCollection();

    ResultSet sessionsResult = preparedStatement.executeQuery();

    while (sessionsResult.next()) {
      sessionsCollection.pushSessionEventToCollection (
        _createActivityEvent (sessionsResult)
      );
    }

    return sessionsCollection;
  }
}
