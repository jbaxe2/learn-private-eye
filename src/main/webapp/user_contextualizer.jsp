<%@ taglib prefix="bbNG" uri="/bbNG" %>

<%@ page import="blackboard.platform.security.NonceUtil" %>

<bbNG:includedPage authentication="Y" entitlement="system.plugin.MODIFY">

<%
  String contextualize = request.getParameter ("contextualize");

  if (null == contextualize || contextualize.isEmpty()) {
    %><p style="margin-bottom: 8px; font-size: medium; font-weight: 600;">
      Please enter the username for the user you would like to track:
    </p>

    <bbNG:form isSecure="true">
      <label for="username">Username:</label>
      <input id="username" name="username" type="text"><br>

      <bbNG:actionButton
          title="Initiate Tracking"
          url="index.jsp?_context=user&contextualize=sessions" />
    </bbNG:form><%
  } else {
    String nonceId = request.getParameter ("nonceId");

    if (!NonceUtil.validate (request, nonceId)) {
      %><bbNG:error
        exception="Failed to validate nonce check to contextualize user." />
      <%
    }

    if (contextualize.equals ("sessions")) {
      %><%@include file="user_sessions_count.jsp"%><%
    }
  }
%>

</bbNG:includedPage>
