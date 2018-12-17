<%@ page import="
  session.SingleUserSession,
  session.UserSessionsCollection" %>

<%@ taglib prefix="bbNG" uri="/bbNG" %>

<bbNG:includedPage authentication="Y" entitlement="system.plugin.MODIFY">

<%
  Id userId = Id.toId (User.DATA_TYPE, request.getParameter ("user_id"));
  UserDbLoader loader = null;

  PrivateEyeUserContext context = new PrivateEyeUserContext (userId);
  PersistenceManager persistenceManager = null;
  UserSessionQuery userQuery = null;
  UserSessionsCollection userSessions = null;
  SimpleUser user = null;

  SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss a z");
  Map<String, SingleUserSession> sessionsMap = new HashMap<>();

  try {
    persistenceManager = new PersistenceManager();
    loader = (UserDbLoader)persistenceManager.retrieveLoader (UserDbLoader.TYPE);
    user = new SimpleUser (loader.loadById (userId));

    userQuery = new UserSessionQuery (userId, persistenceManager.getConnection());

    userSessions = userQuery.retrieveSystemSessions();
    sessionsMap = userSessions.getUserSessions();
  } catch (Exception e) {
    %><bbNG:error exception="<%= e %>" /><%
  }
%>

  <p style="margin-bottom: 8px; font-size: medium; font-weight: 600; text-decoration: underline;">
    Tracked list of non-course sessions for <%= user.getFirstName() %>&nbsp;<%= user.getLastName() %>:
  </p>

  <bbNG:pagedList
      collection="<%= sessionsMap.values() %>"
      className="session.SingleUserSession"
      objectVar="userSession"
      recordCount="<%= sessionsMap.values().size() %>"
      initialSortCol="sessionId"
      initialSortBy="DESCENDING">
    <%
      String sessionId = userSession.getSessionId();
    %>
    <bbNG:listElement name="sessionId" label="Session ID" isRowHeader="true">
      <a href="index.jsp?context=user&contextualize=system&user_id=<%=
          user.getPk1() %>&lpe_sid=<%= sessionId %>&startIndex=0">
        <%= sessionId %>
      </a>
    </bbNG:listElement>

    <bbNG:listElement name="timestamp" label="Date &amp; Timestamp">
      <%=
        dateFormatter.format (
          userSession.getSessionActivities().get (0).getTimestamp()
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
