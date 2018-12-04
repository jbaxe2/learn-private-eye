<%@ taglib prefix="bbNG" uri="/bbNG" %>

<%@ page import="
  java.util.List,
  java.util.ArrayList,
  blackboard.data.user.User,
  blackboard.persist.user.UserDbLoader,
  blackboard.persist.Id,
  blackboard.platform.plugin.PlugInUtil,
  _persistence.PersistenceManager,
  _persistence.query.builder.CourseSessionQueryBuilder,
  _persistence.query.executor.CourseSessionQueryExecutor,
  context.PrivateEyeCourseContext,
  session.SimpleCourseUserSessionCount,
  user.SimpleUser" %>

<bbNG:includedPage authentication="Y" entitlement="course.control_panel.VIEW">

<%
  Id courseId = PlugInUtil.getCourseId();
  UserDbLoader loader = null;

  PrivateEyeCourseContext context = new PrivateEyeCourseContext (courseId);
  PersistenceManager persistenceManager = null;

  CourseSessionQueryBuilder builder = null;
  CourseSessionQueryExecutor executor = null;

  List<SimpleCourseUserSessionCount> sessionCountList = new ArrayList<>();

  try {
    persistenceManager = PersistenceManager.getInstance();
    loader = (UserDbLoader)persistenceManager.retrieveLoader (UserDbLoader.TYPE);

    context.loadCourseUsers (loader);

    builder = new CourseSessionQueryBuilder (
      courseId, persistenceManager.getConnection()
    );

    executor = new CourseSessionQueryExecutor (
      courseId, builder.retrieveNumberSessionsAllUsers()
    );

    sessionCountList = executor.retrieveNumberSessionsAllUsers();
  } catch (Exception e) {
    %><p>Error with Learn PrivateEye:<br><%= e.getMessage() %></p><%
  }
%>

  <p style="margin-bottom: 8px; font-size: medium; font-weight: 600; text-decoration: underline;">
    Tracked list of users for this course:
  </p>

  <bbNG:pagedList
      collection="<%= sessionCountList %>"
      className="session.SimpleCourseUserSessionCount"
      objectVar="sessionCount"
      recordCount="<%= sessionCountList.size() %>">
  <%
    SimpleUser currentUser = context.getUser (
      Id.toId (User.DATA_TYPE, sessionCount.getUserPk1())
    );
  %>
    <bbNG:listElement name="username" label="Username" isRowHeader="true">
      <a href="course_user_sessions.jsp?context=course&user_id=<%= currentUser.getPk1() %>&startIndex=0">
        <%= currentUser.getUserId() %>
      </a>
    </bbNG:listElement>

    <bbNG:listElement name="firstName" label="First Name">
      <%= currentUser.getFirstName() %>
    </bbNG:listElement>

      <bbNG:listElement name="lastName" label="last Name">
        <%= currentUser.getLastName() %>
      </bbNG:listElement>

      <bbNG:listElement name="session" label="Number of Sessions">
        <%= sessionCount.getSessionCount() %>
      </bbNG:listElement>
  </bbNG:pagedList>

<%
  if (null != persistenceManager) {
    persistenceManager.releaseConnection();
  }
%>

</bbNG:includedPage>
