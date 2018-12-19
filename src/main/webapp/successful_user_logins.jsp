<%@ page import="
  java.text.SimpleDateFormat,
  java.util.HashMap,
  java.util.Map,
  blackboard.persist.user.UserDbLoader,
  blackboard.persist.Id,
  _persistence.PersistenceManager,
  _persistence.query.UserSessionQuery,
  session.UserSessionsCollection,
  user.SimpleUser" %>

<%@ taglib prefix="bbNG" uri="/bbNG" %>

<bbNG:includedPage authentication="Y" entitlement="system.plugin.MODIFY">

<%
  Id userId = Id.toId (User.DATA_TYPE, request.getParameter ("user_id"));
  UserDbLoader loader = null;

  PersistenceManager persistenceManager = null;
  UserSessionQuery userQuery = null;
  UserSessionsCollection loginSessions = null;
  SimpleUser user = null;

  SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss a z");
  Map<String, SingleUserSession> loginAttempts = new HashMap<>();

  try {
    persistenceManager = new PersistenceManager();
    loader = (UserDbLoader)persistenceManager.retrieveLoader (UserDbLoader.TYPE);
    user = new SimpleUser (loader.loadById (userId));

    userQuery = new UserSessionQuery (userId, persistenceManager.getConnection());

    loginSessions = userQuery.retrieveSuccessfulLogins();
    loginAttempts = loginSessions.getUserSessions();
  } catch (Exception e) {
    %><bbNG:error exception="<%= e %>" /><%
  }
%>

  <p style="margin-bottom: 8px; font-size: medium; font-weight: 600; text-decoration: underline;">
    Tracked successful login attempts for
    <%= user.getFirstName() %>&nbsp;<%= user.getLastName() %>:
  </p>

  <bbNG:pagedList
      collection="<%= loginAttempts.values() %>"
      className="session.SingleUserSession"
      objectVar="loginAttempt"
      recordCount="<%= loginAttempts.values().size() %>">
    <%
      String sessionId = loginAttempt.getSessionId();
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
          loginAttempt.getSessionActivities().get (
            loginAttempt.getSessionSize() - 1
          ).getTimestamp()
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
