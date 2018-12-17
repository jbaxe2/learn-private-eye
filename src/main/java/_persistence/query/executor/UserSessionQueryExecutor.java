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
   * The [retrieveNumberOfSessions] method...
   */
  public List<SimpleCourseUserSessionCount> retrieveNumberOfSessions()
      throws SQLException {
    return super.retrieveNumberOfSessions (preparedStatement);
  }

  /**
   * The [retrieveSystemSessions] method...
   */
  public UserSessionsCollection retrieveSystemSessions()
      throws SQLException, SessionException {
    UserSessionsCollection sessionsCollection =
      UserSessionsCollection.getInstance();

    ResultSet sessionsResult = preparedStatement.executeQuery();

    while (sessionsResult.next()) {
      sessionsCollection.pushSessionEventToCollection (
        _createActivityEvent (sessionsResult)
      );
    }

    return sessionsCollection;
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
}
