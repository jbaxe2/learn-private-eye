<%@ taglib prefix="bbNG" uri="/bbNG" %>

<%@ page import="
  java.util.List,
  blackboard.persist.course.CourseDbLoader,
  blackboard.persist.user.UserDbLoader,
  _context.PrivateEyeUserContext,
  _persistence.PersistenceManager,
  _persistence.query.builder.UserSessionQueryBuilder,
  _persistence.query.executor.UserSessionQueryExecutor,
  course.SimpleCourse,
  session.SimpleCourseUserSessionCount" %>
<%@ page import="blackboard.data.course.Course" %>

<bbNG:includedPage authentication="Y" entitlement="system.plugin.MODIFY">

<%
  String username = request.getParameter ("username");

  UserDbLoader loader = null;
  CourseDbLoader courseLoader = null;

  PrivateEyeUserContext context = new PrivateEyeUserContext (null);
  PersistenceManager persistenceManager = null;

  UserSessionQueryBuilder builder = null;
  UserSessionQueryExecutor executor = null;

  SimpleUser user = null;

  List<SimpleCourseUserSessionCount> sessionCountList = new ArrayList<>();

  try {
    persistenceManager = new PersistenceManager();
    loader = (UserDbLoader)persistenceManager.retrieveLoader (UserDbLoader.TYPE);
    courseLoader = (CourseDbLoader)persistenceManager.retrieveLoader (CourseDbLoader.TYPE);

    context.loadContextUserByUsername (loader, username);

    user = context.getUser();

    builder = new UserSessionQueryBuilder (
      context.getContextId(), persistenceManager.getConnection()
    );

    executor = new UserSessionQueryExecutor (
      context.getContextId(), builder.retrieveNumberOfSessions()
    );
  } catch (Exception e) {
    %><bbNG:error exception="<%= e %>" /><%
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
      Id courseId = Id.toId (Course.DATA_TYPE, sessionCount.getCoursePk1());
      SimpleCourse currentCourse = null;

      try {
        currentCourse = context.loadCourseForContext (courseLoader, courseId);
      } catch (Exception e) {
        %><bbNG:error exception="<%= e %>" /><%
      }
  %>
    <bbNG:listElement name="courseId" label="Course ID" isRowHeader="true">
      <%= currentCourse.getBatchUid() %>
    </bbNG:listElement>

    <bbNG:listElement name="username" label="Username">
      <%= user.getUserId() %>
    </bbNG:listElement>

    <bbNG:listElement name="firstName" label="First Name">
      <%= user.getFirstName() %>
    </bbNG:listElement>

    <bbNG:listElement name="lastName" label="Last Name">
      <%= user.getLastName() %>
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
