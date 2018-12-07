<%@ page import="
  java.util.List,
  java.util.ArrayList,
  blackboard.data.user.User,
  blackboard.persist.user.UserDbLoader,
  blackboard.persist.Id,
  blackboard.platform.plugin.PlugInUtil,
  _context.PrivateEyeCourseContext,
  _persistence.PersistenceManager,
  _persistence.query.builder.CourseSessionQueryBuilder,
  _persistence.query.executor.CourseSessionQueryExecutor,
  session.SimpleCourseUserSessionCount,
  user.SimpleUser" %>

<%@ taglib prefix="bbNG" uri="/bbNG" %>

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
    persistenceManager = new PersistenceManager();
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
    %><bbNG:error exception="<%= e %>" /><%
  }
%>

  <p style="margin-bottom: 8px; font-size: medium; font-weight: 600; text-decoration: underline;">
    Tracked list of users for this course (<%= courseId.getExternalString() %>):
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
  </bbNG:pagedList>

<%
  if (null != persistenceManager) {
    persistenceManager.releaseConnection();
  }
%>

</bbNG:includedPage>
