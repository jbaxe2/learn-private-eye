package _persistence.query.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import activity.EventType;
import blackboard.persist.Id;

/**
 * The [UserSessionQueryBuilder] class...
 */
public class UserSessionQueryBuilder implements QueryBuilder {
  private final Id userId;

  private final Connection connection;

  /**
   * The [UserSessionQueryBuilder] constructor...
   */
  public UserSessionQueryBuilder (Id userId, Connection connection) {
    this.userId = userId;
    this.connection = connection;
  }

  /**
   * The [retrieveSuccessfulLogins] method...
   */
  public PreparedStatement retrieveSuccessfulLogins() throws SQLException {
    String statement = "SELECT * FROM activity_accumulator WHERE user_pk1 = ? " +
      "AND event_type = '" + EventType.LOGIN_ATTEMPT + "' AND data = 'Login succeeded.'";

    return _createUserSessionStatement (statement);
  }
  /**
   * The [retrieveNumberOfSessions] method...
   */
  public PreparedStatement retrieveNumberOfSessions() throws SQLException {
    String statement = "SELECT user_pk1, course_pk1, count(distinct session_id) " +
      "AS session_count FROM activity_accumulator WHERE user_pk1 = ? " +
      "GROUP BY user_pk1, course_pk1 ORDER BY course_pk1";

    return _createUserSessionStatement (statement);
  }

  /**
   * The [retrieveSystemSessions] method...
   */
  public PreparedStatement retrieveSystemSessions() throws SQLException {
    String statement = "SELECT * FROM activity_accumulator WHERE user_pk1 = ? " +
      "AND course_pk1 IS NULL ORDER BY timestamp";

    return _createUserSessionStatement (statement);
  }

  /**
   * The [retrieveSystemSession] method...
   */
  public PreparedStatement retrieveSystemSession (String sessionId)
      throws SQLException {
    String statement = "SELECT * FROM activity_accumulator WHERE user_pk1 = ? " +
      "AND session_id = ? AND course_pk1 IS NULL ORDER BY timestamp";

    PreparedStatement preparedStatement = _createUserSessionStatement (statement);
    preparedStatement.setString (2, sessionId);

    return preparedStatement;
  }

  /**
   * The [_createUserSessionStatement] private method...
   */
  private PreparedStatement _createUserSessionStatement (String statement)
      throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement (statement);
    preparedStatement.setString (1, userId.getExternalString().split ("_")[1]);

    return preparedStatement;
  }
}
