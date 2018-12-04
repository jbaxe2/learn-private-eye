package _persistence.query.executor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import session.SimpleCourseUserSessionCount;

/**
 * The [SessionQueryExecutor] abstract class...
 */
abstract class SessionQueryExecutor {
  /**
   * The [retrieveNumberOfSessions] method...
   */
  List<SimpleCourseUserSessionCount> retrieveNumberOfSessions (
    PreparedStatement preparedStatement
  ) throws SQLException {
    ResultSet countResult = preparedStatement.executeQuery();
    List<SimpleCourseUserSessionCount> sessionCountList = new ArrayList<>();

    while (countResult.next()) {
      SimpleCourseUserSessionCount sessionCount = new SimpleCourseUserSessionCount (
        countResult.getString ("course_pk1"),
        countResult.getString ("user_pk1"),
        countResult.getInt ("session_count")
      );

      sessionCountList.add (sessionCount);
    }

    return sessionCountList;
  }
}
