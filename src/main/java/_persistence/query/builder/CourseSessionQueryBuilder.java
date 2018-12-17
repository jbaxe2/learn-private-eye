package _persistence.query.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import blackboard.persist.Id;

/**
 * The [CourseSessionQueryBuilder] class...
 */
public class CourseSessionQueryBuilder implements QueryBuilder {
  private final Id courseId;

  private final Connection connection;

  /**
   * The [CourseSessionQueryBuilder] constructor...
   */
  public CourseSessionQueryBuilder (Id courseId, Connection connection) {
    this.courseId = courseId;
    this.connection = connection;
  }

  /**
   * The [retrieveNumberSessionsAllUsers] method...
   */
  public PreparedStatement retrieveNumberSessionsAllUsers() throws SQLException {
    String statement = "SELECT user_pk1, course_pk1, count(distinct session_id) " +
      "AS session_count FROM activity_accumulator WHERE course_pk1 = ? " +
      "GROUP BY course_pk1, user_pk1";

    PreparedStatement preparedStatement = connection.prepareStatement (statement);
    preparedStatement.setString (1, courseId.getExternalString().split ("_")[1]);

    return preparedStatement;
  }

  /**
   * The [retrieveSessionsForUser] method...
   */
  public PreparedStatement retrieveSessionsForUser (Id userId)
      throws SQLException {
    String statement = "SELECT * FROM activity_accumulator WHERE course_pk1 = ? " +
      "AND user_pk1 = ? ORDER BY timestamp DESC";

    return _createUserSessionStatement (statement, userId);
  }

  /**
   * The [retrieveSessionForUser] method...
   */
  public PreparedStatement retrieveSessionForUser (Id userId, String sessionId)
      throws SQLException {
    String statement = "SELECT * FROM activity_accumulator WHERE course_pk1 = ? " +
      "AND user_pk1 = ? AND session_id = ? ORDER BY timestamp DESC";

    PreparedStatement preparedStatement = _createUserSessionStatement (statement, userId);
    preparedStatement.setString (3, sessionId);

    return preparedStatement;
  }

  /**
   * The [_createUserSessionStatement] private method...
   */
  private PreparedStatement _createUserSessionStatement (
    String statement, Id userId
  ) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement (statement);

    preparedStatement.setString (1, courseId.getExternalString().split ("_")[1]);
    preparedStatement.setString (2, userId.getExternalString().split ("_")[1]);

    return preparedStatement;
  }
}
