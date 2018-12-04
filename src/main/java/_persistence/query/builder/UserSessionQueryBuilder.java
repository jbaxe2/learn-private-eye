package _persistence.query.builder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
   * The [retrieveNumberOfSessions] method...
   */
  public PreparedStatement retrieveNumberOfSessions() throws SQLException {
    String statement = "SELECT user_pk1, course_pk1, count(distinct session_id) " +
      "FROM activity_accumulator WHERE user_pk1 = ? GROUP BY user_pk1, course_pk1 " +
      "ORDER BY course_pk1";

    PreparedStatement preparedStatement = connection.prepareStatement (statement);
    preparedStatement.setString (1, userId.getExternalString());

    return preparedStatement;
  }
}
