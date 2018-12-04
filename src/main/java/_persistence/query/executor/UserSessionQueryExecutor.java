package _persistence.query.executor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.List;

import blackboard.persist.Id;
import session.SimpleCourseUserSessionCount;

/**
 * The [UserSessionQueryExecutor] class...
 */
public class UserSessionQueryExecutor extends SessionQueryExecutor implements QueryExecutor {
  private final Id userId;

  private final PreparedStatement preparedStatement;

  public UserSessionQueryExecutor (Id userId, PreparedStatement preparedStatement) {
    this.userId = userId;
    this.preparedStatement = preparedStatement;
  }

  /**
   * The [retrieveNumberOfSessions] method...
   */
  public List<SimpleCourseUserSessionCount> retrieveNumberOfSessions() throws SQLException {
    return super.retrieveNumberOfSessions (preparedStatement);
  }
}
