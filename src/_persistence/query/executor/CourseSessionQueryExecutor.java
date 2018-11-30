package _persistence.query.executor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import blackboard.data.user.User;
import blackboard.persist.Id;

import _error.SessionException;
import activity.ActivityEvent;
import session.SingleUserCourseSession;
import session.SingleUserCourseSessionsCollection;

/**
 * The [CourseSessionQueryExecutor] class...
 */
public class CourseSessionQueryExecutor implements QueryExecutor {
  private final Id courseId;

  private final PreparedStatement preparedStatement;

  private SingleUserCourseSessionsCollection sessionsCollection;

  /**
   * The [CourseSessionQueryExecutor] constructor...
   */
  CourseSessionQueryExecutor (Id courseId, PreparedStatement preparedStatement) {
    this.courseId = courseId;
    this.preparedStatement = preparedStatement;

    sessionsCollection = SingleUserCourseSessionsCollection.getInstance();
  }

  /**
   * The [retrieveSessionsForUser] method...
   */
  public void retrieveSessionsForUser() throws SQLException, SessionException {
    ResultSet sessionsResult = preparedStatement.executeQuery();
    SingleUserCourseSession courseSession = null;

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

      Id userId = Id.toId (User.DATA_TYPE, sessionEvent.getUserPk1());

      if (null == courseSession) {
        courseSession = new SingleUserCourseSession (
          courseId, userId, sessionEvent.getSessionId ()
        );
      }

      courseSession.addSessionActivity (sessionEvent);
    }
  }
}
