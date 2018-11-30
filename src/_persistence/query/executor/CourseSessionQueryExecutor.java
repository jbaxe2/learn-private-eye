package _persistence.query.executor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import blackboard.persist.Id;

import _error.SessionException;
import activity.ActivityEvent;
import session.SimpleCourseUserSessionCount;
import session.SingleUserCourseSessionsCollection;

/**
 * The [CourseSessionQueryExecutor] class...
 */
public class CourseSessionQueryExecutor implements QueryExecutor {
  private final Id courseId;

  private final PreparedStatement preparedStatement;

  /**
   * The [CourseSessionQueryExecutor] constructor...
   */
  CourseSessionQueryExecutor (Id courseId, PreparedStatement preparedStatement) {
    this.courseId = courseId;
    this.preparedStatement = preparedStatement;
  }

  /**
   * The [retrieveNumberSessionsAllUsers] method...
   */
  public List<SimpleCourseUserSessionCount> retrieveNumberSessionsAllUsers() throws SQLException {
    ResultSet countResult = preparedStatement.executeQuery();
    List<SimpleCourseUserSessionCount> sessionCountList = new ArrayList<SimpleCourseUserSessionCount>();

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

  /**
   * The [retrieveSessionsForUser] method...
   */
  public SingleUserCourseSessionsCollection retrieveSessionsForUser() throws SQLException, SessionException {
    ResultSet sessionsResult = preparedStatement.executeQuery();

    SingleUserCourseSessionsCollection sessionsCollection =
      SingleUserCourseSessionsCollection.getInstance();

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
