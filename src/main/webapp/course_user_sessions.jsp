<%@ page import="
  java.text.SimpleDateFormat,
  java.util.HashMap,
  java.util.Map,
  blackboard.data.user.User,
  blackboard.persist.user.UserDbLoader,
  blackboard.persist.Id,
  blackboard.platform.plugin.PlugInUtil,
  _context.PrivateEyeCourseContext,
  _persistence.PersistenceManager,
  _persistence.query.builder.CourseSessionQueryBuilder,
  _persistence.query.executor.CourseSessionQueryExecutor,
  session.CourseUserSessionsCollection,
  session.SingleCourseUserSession,
  user.SimpleUser" %>

<%@ taglib prefix="bbNG" uri="/bbNG" %>

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

  Map<String, SingleCourseUserSession> sessionsMap = new HashMap<>();

  try {
    sessionsMap = courseUserSessions.getCourseSessions();
  } catch (NullPointerException e) {
    %><bbNG:error exception="<%= e %>" /><%
  }
%>

  <p style="margin-bottom: 8px; font-size: medium; font-weight: 600; text-decoration: underline;">
    Tracked list of sessions for <%= user.getFirstName() %>&nbsp;<%= user.getLastName() %>:
  </p>

  <bbNG:pagedList
      collection="<%= sessionsMap.values() %>"
      className="session.SingleCourseUserSession"
      objectVar="courseUserSession"
      recordCount="<%= sessionsMap.values().size() %>"
      initialSortCol="sessionId"
      initialSortBy="DESCENDING">
    <%
      String sessionId = courseUserSession.getSessionId();
    %>
    <bbNG:listElement name="sessionId" label="Session ID" isRowHeader="true">
      <a href="index.jsp?context=course&course_id=<%= courseId.getExternalString()
          %>&user_id=<%= user.getPk1() %>&lpe_sid=<%= sessionId %>&startIndex=0">
        <%= sessionId %>
      </a>
    </bbNG:listElement>

    <bbNG:listElement name="timestamp" label="Date &amp; Timestamp">
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
