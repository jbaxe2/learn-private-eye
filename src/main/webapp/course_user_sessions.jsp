<%@ taglib prefix="bbNG" uri="/bbNG" %>

<%@ page import="
  java.text.SimpleDateFormat,
  java.util.Map,
  blackboard.data.user.User,
  blackboard.persist.user.UserDbLoader,
  blackboard.persist.Id,
  blackboard.platform.plugin.PlugInUtil,
  _persistence.PersistenceManager,
  _persistence.query.builder.CourseSessionQueryBuilder,
  _persistence.query.executor.CourseSessionQueryExecutor,
  context.PrivateEyeCourseContext,
  session.CourseUserSessionsCollection,
  session.SingleCourseUserSession,
  user.SimpleUser" %>

<bbNG:includedPage authentication="Y" entitlement="course.control_panel.VIEW">

<%
  Id courseId = PlugInUtil.getCourseId();
  Id userId = Id.toId (User.DATA_TYPE, request.getParameter ("user_id"));
  UserDbLoader loader = null;

  PrivateEyeCourseContext context = new PrivateEyeCourseContext (courseId);
  PersistenceManager persistenceManager = null;

  CourseSessionQueryBuilder builder = null;
  CourseSessionQueryExecutor executor = null;

  SimpleUser user = null;

  CourseUserSessionsCollection courseUserSessions = null;

  SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss a z");

  try {
    persistenceManager = new PersistenceManager();
    loader = (UserDbLoader)persistenceManager.retrieveLoader (UserDbLoader.TYPE);
    user = new SimpleUser (loader.loadById (userId));

    builder = new CourseSessionQueryBuilder (
      courseId, persistenceManager.getConnection()
    );

    executor = new CourseSessionQueryExecutor (
      courseId, builder.retrieveSessionsForUser (userId)
    );

    courseUserSessions = executor.retrieveSessionsForUser();
  } catch (Exception e) {
    %><bbNG:error exception="<%= e %>" /><%
  }

  Map<String, SingleCourseUserSession> sessionsMap =
    courseUserSessions.getCourseSessions();
%>

  <p style="margin-bottom: 8px; font-size: medium; font-weight: 600; text-decoration: underline;">
    Tracked list of sessions for <%= user.getFirstName() %>&nbsp;<%= user.getLastName() %>:
  </p>

  <bbNG:pagedList
      collection="<%= sessionsMap.values() %>"
      className="session.SingleCourseUserSession"
      objectVar="courseUserSession"
      recordCount="<%= sessionsMap.values().size() %>">
    <bbNG:listElement name="sessionId" label="Session ID" isRowHeader="true">
      <%= courseUserSession.getSessionId() %>
    </bbNG:listElement>

    <bbNG:listElement name="timestamp" label="Date & Timestamp (Session Start)">
      <%=
        dateFormatter.format (
          courseUserSession.getSessionActivities().get (0).getTimestamp()
        )
      %>
    </bbNG:listElement>
  </bbNG:pagedList>

<%
  if (null != persistenceManager) {
    persistenceManager.releaseConnection();
  }
%>

</bbNG:includedPage>
