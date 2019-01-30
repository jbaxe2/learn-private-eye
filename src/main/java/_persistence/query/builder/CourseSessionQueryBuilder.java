package _persistence.query.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import blackboard.persist.Id;

/**
 * The [CourseSessionQueryBuilder] class...
 */
public class CourseSessionQueryBuilder extends ConnectionQueryBuilder {
  private final Id courseId;

  /**
   * The [CourseSessionQueryBuilder] constructor...
   */
  public CourseSessionQueryBuilder (
    Id courseId, Connection connection, Connection statsConnection
  ) {
    super (connection, statsConnection);

    this.courseId = courseId;
  }

  /**
   * The [retrieveNumberSessionsAllUsers] method...
   */
  public PreparedStatement retrieveNumberSessionsAllUsers() throws SQLException {
    PreparedStatement preparedStatement =
      connection.prepareStatement (_buildNumberSessionsAllUsersQuery());

    preparedStatement.setString (1, courseId.getExternalString().split ("_")[1]);

    return preparedStatement;
  }

  /**
   * The [retrieveStatsNumberSessionsAllUsers] method...
   */
  public PreparedStatement retrieveStatsNumberSessionsAllUsers() throws SQLException {
    PreparedStatement preparedStatement =
      statsConnection.prepareStatement (_buildNumberSessionsAllUsersQuery());

    preparedStatement.setString (1, courseId.getExternalString().split ("_")[1]);

    return preparedStatement;
  }

  /**
   * The [retrieveSessionsForUser] method...
   */
  public PreparedStatement retrieveSessionsForUser (Id userId)
      throws SQLException {
    return _createUserSessionStatement (_buildSessionsForUserQuery(), userId, false);
  }

  /**
   * The [retrieveStatsSessionsForUser] method...
   */
  public PreparedStatement retrieveStatsSessionsForUser (Id userId)
      throws SQLException {
    return _createUserSessionStatement (_buildSessionsForUserQuery(), userId, true);
  }

  /**
   * The [retrieveSessionForUser] method...
   */
  public PreparedStatement retrieveSessionForUser (Id userId, String sessionId)
      throws SQLException {
    PreparedStatement preparedStatement =
      _createUserSessionStatement (_buildSessionForUserQuery(), userId, false);

    preparedStatement.setString (3, sessionId);

    return preparedStatement;
  }

  /**
   * The [retrieveStatsSessionForUser] method...
   */
  public PreparedStatement retrieveStatsSessionForUser (Id userId, String sessionId)
      throws SQLException {
    PreparedStatement preparedStatement =
      _createUserSessionStatement (_buildSessionForUserQuery(), userId, true);

    preparedStatement.setString (3, sessionId);

    return preparedStatement;
  }

  /**
   * The [_createUserSessionStatement] private method...
   */
  private PreparedStatement _createUserSessionStatement (
    String statement, Id userId, boolean forStats
  ) throws SQLException {
    Connection theConnection = whichConnection (forStats);

    PreparedStatement preparedStatement = theConnection.prepareStatement (statement);
    preparedStatement.setString (1, courseId.getExternalString().split ("_")[1]);
    preparedStatement.setString (2, userId.getExternalString().split ("_")[1]);

    return preparedStatement;
  }

  /**
   * The [_buildNumberSessionsAllUsersQuery] method...
   */
  private String _buildNumberSessionsAllUsersQuery() {
    return "SELECT user_pk1, COUNT(DISTINCT session_id) " +
      "AS session_count FROM activity_accumulator WHERE course_pk1 = ? " +
      "GROUP BY user_pk1";
  }

  /**
   * The [_buildSessionsForUserQuery] method...
   */
  private String _buildSessionsForUserQuery() {
    return "SELECT * FROM activity_accumulator WHERE course_pk1 = ? " +
      "AND user_pk1 = ? ORDER BY timestamp DESC";
  }

  /**
   * The [_buildSessionForUserQuery] method...
   */
  private String _buildSessionForUserQuery() {
    return "SELECT * FROM activity_accumulator WHERE course_pk1 = ? " +
      "AND user_pk1 = ? AND session_id = ? ORDER BY timestamp DESC";
  }
}
