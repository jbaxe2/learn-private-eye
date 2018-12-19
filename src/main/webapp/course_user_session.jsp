<%@ page import="
  java.text.SimpleDateFormat,
  blackboard.data.user.User,
  blackboard.persist.user.UserDbLoader,
  blackboard.persist.Id,
  blackboard.platform.plugin.PlugInUtil,
  _persistence.PersistenceManager,
  _persistence.query.CourseSessionQuery,
  activity.ActivityEvent,
  session.SingleCourseUserSession,
  user.SimpleUser" %>

<%@ taglib prefix="bbNG" uri="/bbNG" %>

<bbNG:includedPage authentication="Y" entitlement="course.control_panel.VIEW">

<%
  Id courseId = PlugInUtil.getCourseId();
  Id userId = Id.toId (User.DATA_TYPE, request.getParameter ("user_id"));
  String sessionId = request.getParameter ("lpe_sid");
  UserDbLoader loader = null;

  PersistenceManager persistenceManager = null;
  CourseSessionQuery courseQuery = null;
  SingleCourseUserSession singleSession = null;
  SimpleUser user = null;

  SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss a z");
  List<ActivityEvent> sessionEvents = new ArrayList<>();

  try {
    persistenceManager = new PersistenceManager();
    loader = (UserDbLoader)persistenceManager.retrieveLoader (UserDbLoader.TYPE);
    user = new SimpleUser (loader.loadById (userId));

    courseQuery = new CourseSessionQuery (
      courseId, persistenceManager.getConnection()
    );

    singleSession = courseQuery.retrieveSessionForUser (courseId, userId, sessionId);
    sessionEvents = singleSession.getSessionActivities();
  } catch (Exception e) {
    %><bbNG:error exception="<%= e %>" /><%
  }
%>

  <p style="margin-bottom: 8px; font-size: medium; font-weight: 600; text-decoration: underline;">
    Tracked session (<%= singleSession.getSessionId() %>) for
    <%= user.getFirstName() %>&nbsp;<%= user.getLastName() %>:
  </p>

  <bbNG:pagedList
      collection="<%= sessionEvents %>"
      className="activity.ActivityEvent"
      objectVar="sessionEvent"
      recordCount="<%= sessionEvents.size() %>">
    <bbNG:listElement name="timestamp" label="Date &amp; Timestamp" isRowHeader="true">
      <%= dateFormatter.format (sessionEvent.getTimestamp()) %>
    </bbNG:listElement>

    <bbNG:listElement name="eventType" label="Event Type">
      <%= sessionEvent.getEventType() %>
    </bbNG:listElement>

    <bbNG:listElement name="internalHandle" label="Internal Handle">
      <%= sessionEvent.getInternalHandle() %>
    </bbNG:listElement>

    <bbNG:listElement name="extraData" label="Relevant or Extra Information">
      <%= sessionEvent.getData() %>
    </bbNG:listElement>
  </bbNG:pagedList>

<%
  if (null != persistenceManager) {
    persistenceManager.releaseConnection();
  }
%>

</bbNG:includedPage>
