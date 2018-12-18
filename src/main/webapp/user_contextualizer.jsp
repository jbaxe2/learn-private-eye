<%@ taglib prefix="bbNG" uri="/bbNG" %>

<bbNG:includedPage authentication="Y" entitlement="system.plugin.MODIFY">

<%
  String contextualize = request.getParameter ("contextualize");

  if (null == contextualize || contextualize.isEmpty()) {
    %><bbNG:form
        id="userContextualizer"
        name="userContextualizer"
        method="post"
        action="index.jsp?context=user&contextualize=sessions&startIndex=0">
      <p style="margin-bottom: 8px; font-size: medium; font-weight: 600;">
        Please enter the username for the user you would like to track:
      </p>

      <bbNG:textElement name="username" isRequired="true"/><br><br>

      <bbNG:button
          formSubmit="true"
          label="Initiate Tracking"
          url="index.jsp?context=user&contextualize=sessions&startIndex=0" />
    </bbNG:form><%
  } else {
    switch (contextualize) {
      case "sessions":
        %><%@include file="user_sessions_count.jsp"%><%

        break;
      case "system":
        String sessionIdQuery = request.getParameter ("lpe_sid");

        if (null == sessionIdQuery) {
          %><%@include file="system_user_sessions.jsp"%><%
        } else {
          %><%@include file="system_user_session.jsp"%><%
        }

        break;
      case "logins":
        %><%@include file="successful_user_logins.jsp"%><%

        break;
      default:
        %><%@ include file="error.jsp"%><%
    }
  }
%>

</bbNG:includedPage>
