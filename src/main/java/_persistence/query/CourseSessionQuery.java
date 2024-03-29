package _persistence.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.List;

import blackboard.persist.Id;

import _error.SessionException;

import _persistence.query.builder.CourseSessionQueryBuilder;
import _persistence.query.executor.CourseSessionQueryExecutor;

import session.CourseUserSessionsCollection;
import session.SimpleCourseUserSessionCount;
import session.SingleCourseUserSession;

/**
 * The [CourseSessionQuery] class...
 */
public class CourseSessionQuery {
  private Id courseId;

  private CourseSessionQueryBuilder builder;

  private CourseSessionQueryExecutor executor;

  /**
   * The [CourseSessionQuery] constructor...
   */
  public CourseSessionQuery (
    Id courseId, Connection connection, Connection statsConnection
  ) {
    builder =
      new CourseSessionQueryBuilder (courseId, connection, statsConnection);

    this.courseId = courseId;
  }

  /**
   * The [retrieveNumberSessionsAllUsers] method...
   */
  public List<SimpleCourseUserSessionCount> retrieveNumberSessionsAllUsers()
      throws SQLException {
    PreparedStatement preparedStatement = builder.retrieveNumberSessionsAllUsers();
    executor = new CourseSessionQueryExecutor (preparedStatement, false);

    return executor.retrieveNumberSessionsAllUsers (courseId);
  }

  /**
   * The [retrieveStatsNumberSessionsAllUsers] method...
   */
  public List<SimpleCourseUserSessionCount> retrieveStatsNumberSessionsAllUsers()
      throws SQLException {
    PreparedStatement preparedStatement = builder.retrieveStatsNumberSessionsAllUsers();
    executor = new CourseSessionQueryExecutor (preparedStatement, true);

    return executor.retrieveNumberSessionsAllUsers (courseId);
  }

  /**
   * The [retrieveSessionsForUser] method...
   */
  public CourseUserSessionsCollection retrieveSessionsForUser (Id userId)
      throws SQLException, SessionException {
    PreparedStatement preparedStatement = builder.retrieveSessionsForUser (userId);
    executor = new CourseSessionQueryExecutor (preparedStatement, false);

    return executor.retrieveSessionsForUser();
  }

  /**
   * The [retrieveStatsSessionsForUser] method...
   */
  public CourseUserSessionsCollection retrieveStatsSessionsForUser (Id userId)
      throws SQLException, SessionException {
    PreparedStatement preparedStatement =
      builder.retrieveStatsSessionsForUser (userId);

    executor = new CourseSessionQueryExecutor (preparedStatement, true);

    return executor.retrieveSessionsForUser();
  }

  /**
   * The [retrieveSessionForUser] method...
   */
  public SingleCourseUserSession retrieveSessionForUser (
    Id courseId, Id userId, String sessionId
  ) throws SQLException, SessionException {
    PreparedStatement preparedStatement =
      builder.retrieveSessionForUser (userId, sessionId);

    executor = new CourseSessionQueryExecutor (preparedStatement, false);

    return executor.retrieveSessionForUser (courseId, userId, sessionId);
  }

  /**
   * The [retrieveStatsSessionForUser] method...
   */
  public SingleCourseUserSession retrieveStatsSessionForUser (
    Id courseId, Id userId, String sessionId
  ) throws SQLException, SessionException {
    PreparedStatement preparedStatement =
      builder.retrieveStatsSessionForUser (userId, sessionId);

    executor = new CourseSessionQueryExecutor (preparedStatement, true);

    return executor.retrieveSessionForUser (courseId, userId, sessionId);
  }
}
