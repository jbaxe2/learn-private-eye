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
import session.SingleCourseUserSession;

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
      sessionsCollection.pushSessionEventToCollection (
        _createActivityEvent (sessionsResult)
      );
    }

    return sessionsCollection;
  }

  /**
   * The [retrieveSessionForUser] method...
   */
  public SingleCourseUserSession retrieveSessionForUser (
    Id userId, String sessionId
  ) throws SQLException, SessionException {
    ResultSet sessionResult = preparedStatement.executeQuery();

    SingleCourseUserSession session = new SingleCourseUserSession (courseId, userId, sessionId);

    while (sessionResult.next()) {
      session.addSessionActivity (_createActivityEvent (sessionResult));
    }

    return session;
  }

  /**
   * The [_createActivityEvent] method...
   */
  private ActivityEvent _createActivityEvent (ResultSet sessionResult) throws SQLException {
    return new ActivityEvent (
      sessionResult.getString ("pk1"),
      sessionResult.getString ("user_pk1"),
      sessionResult.getString ("course_pk1"),
      sessionResult.getString ("group_pk1"),
      sessionResult.getString ("forum_pk1"),
      sessionResult.getString ("content_pk1"),
      sessionResult.getString ("event_type"),
      sessionResult.getString ("internal_handle"),
      sessionResult.getString ("data"),
      sessionResult.getDate ("timestamp"),
      sessionResult.getString ("session_id")
    );
  }
}
