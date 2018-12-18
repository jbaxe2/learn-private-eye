<%@ page import="
  java.text.SimpleDateFormat,
  blackboard.data.user.User,
  blackboard.persist.user.UserDbLoader,
  blackboard.persist.Id,
  _context.PrivateEyeUserContext,
  _persistence.PersistenceManager,
  _persistence.query.UserSessionQuery,
  activity.ActivityEvent,
  session.SingleUserSession,
  user.SimpleUser" %>

<%@ taglib prefix="bbNG" uri="/bbNG" %>

<bbNG:includedPage authentication="Y" entitlement="system.plugin.MODIFY">

<%
  Id userId = Id.toId (User.DATA_TYPE, request.getParameter ("user_id"));
  String sessionId = request.getParameter ("lpe_sid");
  UserDbLoader loader = null;

  PrivateEyeUserContext context = new PrivateEyeUserContext (userId);
  PersistenceManager persistenceManager = null;
  UserSessionQuery userQuery = null;
  SingleUserSession userSession = null;
  SimpleUser user = null;

  SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss a z");
  List<ActivityEvent> sessionEvents = new ArrayList<>();

  try {
    persistenceManager = new PersistenceManager();
    loader = (UserDbLoader)persistenceManager.retrieveLoader (UserDbLoader.TYPE);
    user = new SimpleUser (loader.loadById (userId));

    userQuery = new UserSessionQuery (userId, persistenceManager.getConnection());

    userSession = userQuery.retrieveSystemSession (sessionId);
    sessionEvents = userSession.getSessionActivities();
  } catch (Exception e) {
    %><bbNG:error exception="<%= e %>" /><%
  }
%>

  <p style="margin-bottom: 8px; font-size: medium; font-weight: 600; text-decoration: underline;">
    Tracked non-course session (<%= userSession.getSessionId() %>) for
    <%= user.getFirstName() %>&nbsp;<%= user.getLastName() %>:
  </p>

  <bbNG:pagedList
      collection="<%= sessionEvents %>"
      className="activity.ActivityEvent"
      objectVar="sessionEvent"
      recordCount="<%= sessionEvents.size() %>"
      initialSortCol="timestamp"
      initialSortBy="DESCENDING">
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
