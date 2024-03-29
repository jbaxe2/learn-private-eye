<%@ page import="
  java.util.List,
  java.util.ArrayList,
  blackboard.data.course.Course,
  blackboard.persist.course.CourseDbLoader,
  blackboard.persist.user.UserDbLoader,
  blackboard.persist.Id,
  _auxilliary.CourseSessionComparator,
  _context.PrivateEyeUserContext,
  _persistence.PersistenceManager,
  _persistence.query.UserSessionQuery,
  course.SimpleCourse,
  session.SimpleCourseUserSessionCount,
  user.SimpleUser" %>

<%@ taglib prefix="bbNG" uri="/bbNG" %>

<bbNG:includedPage authentication="Y" entitlement="system.plugin.MODIFY">

<%
  String username = request.getParameter ("username");
  String userId = request.getParameter ("user_id");

  UserDbLoader userLoader = null;
  CourseDbLoader courseLoader = null;

  PrivateEyeUserContext context = null;
  PersistenceManager persistenceManager = null;
  UserSessionQuery userQuery = null;
  UserSessionsCollection loginSessions = null;
  SimpleUser user = null;

  List<SimpleCourseUserSessionCount> sessionCountList = new ArrayList<>();

  CourseSessionComparator comparator = new CourseSessionComparator();

  int successfulLogins = 0;

  try {
    persistenceManager = new PersistenceManager();
    userLoader = (UserDbLoader)persistenceManager.retrieveLoader (UserDbLoader.TYPE);
    courseLoader = (CourseDbLoader)persistenceManager.retrieveLoader (CourseDbLoader.TYPE);

    if (!((null == username) || username.isEmpty())) {
      context = new PrivateEyeUserContext (null);
      context.loadContextUserByUsername (userLoader, username);
    } else if (!((null == userId) || userId.isEmpty())) {
      context = new PrivateEyeUserContext (Id.toId (User.DATA_TYPE, userId));
      context.loadContextUserById (userLoader);
    } else {
      throw new Exception ("A specific user was not provided.");
    }

    user = context.getUser();

    userQuery = new UserSessionQuery (
      context.getContextId(),
      persistenceManager.getConnection(),
      persistenceManager.getStatsConnection()
    );

    loginSessions = userQuery.retrieveSuccessfulLogins();
    sessionCountList = userQuery.retrieveNumberOfSessions();
    successfulLogins += loginSessions.getUserSessions().size();

    loginSessions = userQuery.retrieveStatsSuccessfulLogins();
    sessionCountList.addAll (userQuery.retrieveStatsNumberOfSessions());
    successfulLogins += loginSessions.getUserSessions().size();
  } catch (Exception e) {
    %><bbNG:error exception="<%= e %>" /><%
  }

  if (null == user) {
    %><p>No matching user could be found to provide session information.</p><%
  } else {
%>

    <p style="background-color: lightgrey; margin-bottom: 10px; padding: 8px; font-size: medium;">
      Number of successful login attempts: &nbsp;
      <strong><a href="index.jsp?context=user&contextualize=logins&user_id=<%= user.getPk1()
        %>&startIndex=0"><%= successfulLogins
      %></a></strong>
    </p><br>

    <p style="margin-bottom: 8px; font-size: medium; font-weight: 600; text-decoration: underline;">
      Tracked session count (by course) for
      <%= user.getFirstName() %>&nbsp;<%= user.getLastName() %>:
    </p>

    <bbNG:pagedList
        collection="<%= sessionCountList %>"
        className="session.SimpleCourseUserSessionCount"
        objectVar="sessionCount"
        recordCount="<%= sessionCountList.size() %>"
        initialSortCol="courseId">
    <%
      Id currentCourseId = null;
      SimpleCourse currentCourse = null;

      try {
        if (null != sessionCount.getCoursePk1()) {
          currentCourseId = Id.toId (Course.DATA_TYPE, sessionCount.getCoursePk1());
          currentCourse = context.loadCourseForContext (courseLoader, currentCourseId);
        }
      } catch (Exception e) {
        %><bbNG:error exception="<%= e %>" /><%
      }
    %>
      <bbNG:listElement
          name="courseId"
          label="Course ID"
          isRowHeader="true"
          comparator="<%= comparator %>">
        <%
          if (null == currentCourse) {
            %><a href="index.jsp?context=user&contextualize=system&user_id=<%= user.getPk1()
                %>&startIndex=0">
              (not course-based tracking)
            </a><%
          } else {
            %><a href="index.jsp?context=course&course_id=<%= currentCourseId.getExternalString()
                %>&user_id=<%= user.getPk1() %>&startIndex=0">
              <%= currentCourse.getCourseId() %>
            </a><%
          }
        %>
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

      <bbNG:listElement name="stats" label="Contains Antiquated Tracking">
        <%
          if (sessionCount.getForStats()) {
            %>Yes<%
          } else {
            %>No<%
          }
        %>
      </bbNG:listElement>
    </bbNG:pagedList>

<%
  }

  if (null != persistenceManager) {
    persistenceManager.releaseConnections();
  }
%>

</bbNG:includedPage>
