package _persistence.query.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import activity.EventType;
import blackboard.persist.Id;

/**
 * The [UserSessionQueryBuilder] class...
 */
public class UserSessionQueryBuilder extends ConnectionQueryBuilder {
  private final Id userId;

  /**
   * The [UserSessionQueryBuilder] constructor...
   */
  public UserSessionQueryBuilder (
    Id userId, Connection connection, Connection statsConnection
  ) {
    super (connection, statsConnection);

    this.userId = userId;
  }

  /**
   * The [retrieveSuccessfulLogins] method...
   */
  public PreparedStatement retrieveSuccessfulLogins() throws SQLException {
    return _createUserSessionStatement (_buildSuccessfulLoginsQuery(), false);
  }

  /**
   * The [retrieveStatsSuccessfulLogins] method...
   */
  public PreparedStatement retrieveStatsSuccessfulLogins() throws SQLException {
    return _createUserSessionStatement (_buildSuccessfulLoginsQuery(), true);
  }

  /**
   * The [retrieveNumberOfSessions] method...
   */
  public PreparedStatement retrieveNumberOfSessions() throws SQLException {
    return _createUserSessionStatement (_buildNumberOfSessionsQuery(), false);
  }

  /**
   * The [retrieveStatsNumberOfSessions] method...
   */
  public PreparedStatement retrieveStatsNumberOfSessions() throws SQLException {
    return _createUserSessionStatement (_buildNumberOfSessionsQuery(), true);
  }

  /**
   * The [retrieveSystemSessions] method...
   */
  public PreparedStatement retrieveSystemSessions() throws SQLException {
    return _createUserSessionStatement (_buildSystemSessionsQuery(), false);
  }

  /**
   * The [retrieveStatsSystemSessions] method...
   */
  public PreparedStatement retrieveStatsSystemSessions() throws SQLException {
    return _createUserSessionStatement (_buildSystemSessionsQuery(), true);
  }

  /**
   * The [retrieveSystemSession] method...
   */
  public PreparedStatement retrieveSystemSession (String sessionId)
      throws SQLException {
    PreparedStatement preparedStatement =
      _createUserSessionStatement (_buildSystemSessionQuery(), false);

    preparedStatement.setString (2, sessionId);

    return preparedStatement;
  }

  /**
   * The [retrieveStatsSystemSession] method...
   */
  public PreparedStatement retrieveStatsSystemSession (String sessionId)
      throws SQLException {
    PreparedStatement preparedStatement =
      _createUserSessionStatement (_buildSystemSessionQuery(), true);

    preparedStatement.setString (2, sessionId);

    return preparedStatement;
  }

  /**
   * The [_createUserSessionStatement] private method...
   */
  private PreparedStatement _createUserSessionStatement (String statement, boolean forStats)
      throws SQLException {
    Connection theConnection = whichConnection (forStats);

    PreparedStatement preparedStatement = theConnection.prepareStatement (statement);
    preparedStatement.setString (1, userId.getExternalString().split ("_")[1]);

    return preparedStatement;
  }

  /**
   * The [_buildSuccessfulLoginsQuery] method...
   */
  private String _buildSuccessfulLoginsQuery() {
    return "SELECT * FROM activity_accumulator WHERE user_pk1 = ? " +
      "AND event_type = '" + EventType.LOGIN_ATTEMPT + "' AND " +
      "data = 'Login succeeded.' ORDER BY timestamp DESC";
  }

  /**
   * The [_buildNumberOfSessionsQuery] method...
   */
  private String _buildNumberOfSessionsQuery() {
    return "SELECT user_pk1, course_pk1, count(distinct session_id) " +
      "AS session_count FROM activity_accumulator WHERE user_pk1 = ? " +
      "GROUP BY user_pk1, course_pk1 ORDER BY course_pk1";
  }

  /**
   * The [_buildSystemSessionsQuery] method...
   */
  private String _buildSystemSessionsQuery() {
    return "SELECT * FROM activity_accumulator WHERE user_pk1 = ? " +
      "AND course_pk1 IS NULL ORDER BY timestamp DESC";
  }

  /**
   * The [_buildSystemSessionQuery] method...
   */
  private String _buildSystemSessionQuery() {
    return "SELECT * FROM activity_accumulator WHERE user_pk1 = ? " +
      "AND session_id = ? AND course_pk1 IS NULL ORDER BY timestamp DESC";
  }
}
