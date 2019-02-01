<%@ page import="
  java.util.List,
  java.util.ArrayList,
  blackboard.data.user.User,
  blackboard.persist.user.UserDbLoader,
  blackboard.persist.Id,
  blackboard.platform.plugin.PlugInUtil,
  _context.PrivateEyeCourseContext,
  _persistence.query.CourseSessionQuery,
  _persistence.PersistenceManager,
  session.SimpleCourseUserSessionCount,
  user.SimpleUser" %>

<%@ taglib prefix="bbNG" uri="/bbNG" %>

<bbNG:includedPage authentication="Y" entitlement="course.control_panel.VIEW">

<%
  Id courseId = PlugInUtil.getCourseId();
  UserDbLoader loader = null;

  PrivateEyeCourseContext context = new PrivateEyeCourseContext (courseId);
  PersistenceManager persistenceManager = null;
  CourseSessionQuery courseQuery = null;

  List<SimpleCourseUserSessionCount> sessionCountList = new ArrayList<>();

  try {
    persistenceManager = new PersistenceManager();
    loader = (UserDbLoader)persistenceManager.retrieveLoader (UserDbLoader.TYPE);
    context.loadCourseUsers (loader);

    courseQuery = new CourseSessionQuery (
      courseId, persistenceManager.getConnection(), persistenceManager.getStatsConnection()
    );

    sessionCountList = courseQuery.retrieveNumberSessionsAllUsers();
    sessionCountList.addAll (courseQuery.retrieveStatsNumberSessionsAllUsers());
  } catch (Exception e) {
    %><bbNG:error exception="<%= e %>" /><%
  }

  if (sessionCountList.isEmpty()) {
    %><p>
      Attempting to pull the list of session counts for the enrollments did not
      produce any results, or resulted in an error.
    </p><%
  } else {
%>

    <p style="margin-bottom: 8px; font-size: medium; font-weight: 600; text-decoration: underline;">
      Tracked list of users for this course (<%= courseId.getExternalString() %>):
    </p>

    <bbNG:pagedList
        collection="<%= sessionCountList %>"
        className="session.SimpleCourseUserSessionCount"
        objectVar="sessionCount"
        recordCount="<%= sessionCountList.size() %>"
        initialSortCol="username">
    <%
      SimpleUser currentUser = null;

      try {
        currentUser = context.getUser (
          Id.toId (User.DATA_TYPE, sessionCount.getUserPk1())
        );
      } catch (Exception e) {
        %><bbNG:error exception="<%= e %>" /><%
      }

      if (null == currentUser) {
        %><p>No current user to provide session count details.</p><%
      } else {
    %>
        <bbNG:listElement name="username" label="Username" isRowHeader="true">
          <a href="index.jsp?context=course&course_id=<%= courseId.getExternalString()
              %>&user_id=<%= currentUser.getPk1() %>&startIndex=0">
            <%= currentUser.getUserId() %>
          </a>
        </bbNG:listElement>

        <bbNG:listElement name="firstName" label="First Name">
          <%= currentUser.getFirstName() %>
        </bbNG:listElement>

        <bbNG:listElement name="lastName" label="Last Name">
          <%= currentUser.getLastName() %>
        </bbNG:listElement>

        <bbNG:listElement name="session" label="Number of Sessions">
          <%= sessionCount.getSessionCount() %>
        </bbNG:listElement>

        <bbNG:listElement name="stats" label="Contains Antiquated Tracking">
          <%
            if (sessionCount.getForStats()) {
              %>Yes<%
            } else {
              %>No<%
            }
          %>
        </bbNG:listElement>
    <%
      }
    %>

    </bbNG:pagedList>

<%
  }

  if (null != persistenceManager) {
    persistenceManager.releaseConnections();
  }
%>

</bbNG:includedPage>
