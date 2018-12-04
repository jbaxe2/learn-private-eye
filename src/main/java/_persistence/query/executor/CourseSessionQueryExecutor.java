package _persistence.query.executor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import blackboard.persist.Id;

import _error.SessionException;
import activity.ActivityEvent;
import session.CourseUserSessionsCollection;
import session.SimpleCourseUserSessionCount;

/**
 * The [CourseSessionQueryExecutor] class...
 */
public class CourseSessionQueryExecutor extends SessionQueryExecutor implements QueryExecutor {
  private final Id courseId;

  private final PreparedStatement preparedStatement;

  /**
   * The [CourseSessionQueryExecutor] constructor...
   */
  public CourseSessionQueryExecutor (Id courseId, PreparedStatement preparedStatement) {
    this.courseId = courseId;
    this.preparedStatement = preparedStatement;
  }

  /**
   * The [retrieveNumberSessionsAllUsers] method...
   */
  public List<SimpleCourseUserSessionCount> retrieveNumberSessionsAllUsers() throws SQLException {
    return super.retrieveNumberOfSessions (preparedStatement);
  }

  /**
   * The [retrieveSessionsForUser] method...
   */
  public CourseUserSessionsCollection retrieveSessionsForUser() throws SQLException, SessionException {
    ResultSet sessionsResult = preparedStatement.executeQuery();

    CourseUserSessionsCollection sessionsCollection =
      CourseUserSessionsCollection.getInstance();

    while (sessionsResult.next()) {
      ActivityEvent sessionEvent = new ActivityEvent (
        sessionsResult.getString ("pk1"),
        sessionsResult.getString ("user_pk1"),
        sessionsResult.getString ("course_pk1"),
        sessionsResult.getString ("group_pk1"),
        sessionsResult.getString ("forum_pk1"),
        sessionsResult.getString ("content_pk1"),
        sessionsResult.getString ("event_type"),
        sessionsResult.getString ("internal_handle"),
        sessionsResult.getString ("data"),
        sessionsResult.getDate ("timestamp"),
        sessionsResult.getString ("session_id")
      );

      sessionsCollection.pushSessionEventToCollection (sessionEvent);
    }

    return sessionsCollection;
  }
}
