<%@ taglib prefix="bbNG" uri="/bbNG" %>

<%@ page import="
  java.text.SimpleDateFormat,
  blackboard.data.user.User,
  blackboard.persist.user.UserDbLoader,
  blackboard.persist.Id,
  blackboard.platform.plugin.PlugInUtil,
  _persistence.PersistenceManager,
  _persistence.query.builder.CourseSessionQueryBuilder,
  _persistence.query.executor.CourseSessionQueryExecutor,
  context.PrivateEyeCourseContext,
  session.SingleCourseUserSession,
  user.SimpleUser" %>
<%@ page import="activity.ActivityEvent" %>

<bbNG:includedPage authentication="Y" entitlement="course.control_panel.VIEW">

<%
  Id courseId = PlugInUtil.getCourseId();
  Id userId = Id.toId (User.DATA_TYPE, request.getParameter ("user_id"));

  String sessionId = request.getParameter ("session_id");

  SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss a z");

  UserDbLoader loader = null;

  PrivateEyeCourseContext context = new PrivateEyeCourseContext (courseId);
  PersistenceManager persistenceManager = null;

  CourseSessionQueryBuilder builder = null;
  CourseSessionQueryExecutor executor = null;

  SimpleUser user = null;

  SingleCourseUserSession singleSession = null;

  List<ActivityEvent> sessionEvents = new ArrayList<>();

  try {
    persistenceManager = new PersistenceManager();
    loader = (UserDbLoader)persistenceManager.retrieveLoader (UserDbLoader.TYPE);
    user = new SimpleUser (loader.loadById (userId));

    builder = new CourseSessionQueryBuilder (
      courseId, persistenceManager.getConnection()
    );

    executor = new CourseSessionQueryExecutor (
      courseId, builder.retrieveSessionForUser (userId, sessionId)
    );

    singleSession = executor.retrieveSessionForUser (userId, sessionId);
  } catch (Exception e) {
    %><bbNG:error exception="<%= e %>" /><%
  }
%>

  <p style="margin-bottom: 8px; font-size: medium; font-weight: 600; text-decoration: underline;">
    Tracked list of sessions for <%= user.getFirstName() %>&nbsp;<%= user.getLastName() %>:
  </p>

  <bbNG:pagedList
      collection="<%= sessionEvents %>"
      className="activity.ActivityEvent"
      objectVar="sessionEvent"
      recordCount="<%= sessionEvents.size() %>">
        <bbNG:listElement name="timestamp" label="Date & Timestamp (Session Start)">
          <%= dateFormatter.format (sessionEvent.getTimestamp()) %>
        </bbNG:listElement>
  </bbNG:pagedList>

<%
  if (null != persistenceManager) {
    persistenceManager.releaseConnection();
  }
%>

</bbNG:includedPage>
