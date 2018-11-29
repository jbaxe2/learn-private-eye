package _persistence.query.executor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import blackboard.persist.DataType;
import blackboard.persist.Id;
import blackboard.persist.PersistenceException;
import blackboard.persist.course.CourseDbLoader;

import activity.ActivityEvent;
import blackboard.persist.user.UserDbLoader;

/**
 * The [CourseSessionQueryExecutor] class...
 */
public class CourseSessionQueryExecutor implements QueryExecutor {
  private final PreparedStatement preparedStatement;

  /**
   * The [CourseSessionQueryExecutor] constructor...
   */
  CourseSessionQueryExecutor (PreparedStatement preparedStatement) {
    this.preparedStatement = preparedStatement;
  }

  /**
   * The [retrieveSessionsForUser] method...
   */
  public void retrieveSessionsForUser() throws SQLException, PersistenceException {
    ResultSet sessionsResult = preparedStatement.executeQuery();

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

      Id courseId = Id.generateId (
        new DataType (CourseDbLoader.TYPE), sessionEvent.getCoursePk1()
      );

      Id userId = Id.generateId (
        new DataType (UserDbLoader.TYPE), sessionEvent.getUserPk1()
      );


    }
  }
}
