package _persistence.query.executor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import blackboard.persist.Id;

import _error.SessionException;

import session.CourseUserSessionsCollection;
import session.SimpleCourseUserSessionCount;
import session.SingleCourseUserSession;

/**
 * The [CourseSessionQueryExecutor] class...
 */
public class CourseSessionQueryExecutor
    extends SessionQueryExecutor implements QueryExecutor {
  private final PreparedStatement preparedStatement;

  private final boolean forStats;

  /**
   * The [CourseSessionQueryExecutor] constructor...
   */
  public CourseSessionQueryExecutor (
    PreparedStatement preparedStatement, boolean forStats
  ) {
    this.preparedStatement = preparedStatement;
    this.forStats = forStats;
  }

  /**
   * The [retrieveNumberSessionsAllUsers] method...
   */
  public List<SimpleCourseUserSessionCount> retrieveNumberSessionsAllUsers (
    Id courseId
  ) throws SQLException {
    return super.retrieveNumberOfSessions (preparedStatement, courseId, forStats);
  }

  /**
   * The [retrieveSessionsForUser] method...
   */
  public CourseUserSessionsCollection retrieveSessionsForUser()
      throws SQLException, SessionException {
    ResultSet sessionsResult = preparedStatement.executeQuery();

    CourseUserSessionsCollection sessionsCollection =
      new CourseUserSessionsCollection();

    while (sessionsResult.next()) {
      sessionsCollection.pushSessionEventToCollection (
        _createActivityEvent (sessionsResult, forStats)
      );
    }

    return sessionsCollection;
  }

  /**
   * The [retrieveSessionForUser] method...
   */
  public SingleCourseUserSession retrieveSessionForUser (
    Id courseId, Id userId, String sessionId
  ) throws SQLException, SessionException {
    ResultSet sessionResult = preparedStatement.executeQuery();

    SingleCourseUserSession session =
      new SingleCourseUserSession (courseId, userId, sessionId);

    while (sessionResult.next()) {
      session.addSessionActivity (
        _createActivityEvent (sessionResult, forStats)
      );
    }

    return session;
  }
}
