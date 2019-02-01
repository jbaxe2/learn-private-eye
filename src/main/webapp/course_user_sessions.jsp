<%@ page import="
  java.text.SimpleDateFormat,
  java.util.HashMap,
  java.util.Map,
  blackboard.data.user.User,
  blackboard.persist.user.UserDbLoader,
  blackboard.persist.Id,
  blackboard.platform.plugin.PlugInUtil,
  _auxilliary.SingleUserSessionComparator,
  _context.PrivateEyeCourseContext,
  _persistence.PersistenceManager,
  _persistence.query.CourseSessionQuery,
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
  CourseSessionQuery courseQuery = null;
  CourseUserSessionsCollection courseUserSessions = null;
  SimpleUser user = null;

  SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss a z");
  SingleUserSessionComparator comparator = new SingleUserSessionComparator();
  Map<String, SingleCourseUserSession> sessionsMap = new HashMap<>();

  try {
    persistenceManager = new PersistenceManager();
    loader = (UserDbLoader)persistenceManager.retrieveLoader (UserDbLoader.TYPE);
    user = new SimpleUser (loader.loadById (userId));

    courseQuery = new CourseSessionQuery (
      courseId, persistenceManager.getConnection(), persistenceManager.getStatsConnection()
    );

    courseUserSessions = courseQuery.retrieveSessionsForUser (userId);
    sessionsMap = courseUserSessions.getCourseSessions();

    courseUserSessions = courseQuery.retrieveStatsSessionsForUser (userId);
    sessionsMap.putAll (courseUserSessions.getCourseSessions());
  } catch (Exception e) {
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
      initialSortCol="timestamp"
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

    <bbNG:listElement
        name="timestamp"
        label="Date &amp; Timestamp"
        comparator="<%= comparator %>">
      <%=
        dateFormatter.format (
          courseUserSession.getSessionActivities().get (
            courseUserSession.getSessionSize() - 1
          ).getTimestamp()
        )
      %>
    </bbNG:listElement>

    <bbNG:listElement name="stats" label="Contains Antiquated Tracking">
      <%
        if (courseUserSession.containsStatsTracking()) {
          %>Yes<%
        } else {
          %>No<%
        }
      %>
    </bbNG:listElement>
  </bbNG:pagedList>

<%
  if (null != persistenceManager) {
    persistenceManager.releaseConnections();
  }
%>

</bbNG:includedPage>
