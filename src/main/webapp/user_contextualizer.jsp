<%@ taglib prefix="bbNG" uri="/bbNG" %>

<bbNG:includedPage authentication="Y" entitlement="system.plugin.MODIFY">

<%
  String contextualize = request.getParameter ("contextualize");

  if (null == contextualize || contextualize.isEmpty()) {
    %><bbNG:form
        id="userContextualizer"
        name="userContextualizer"
        method="POST"
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
    if ("sessions".equals (contextualize)) {
      %><%@include file="user_sessions_count.jsp"%><%
    }
  }
%>

</bbNG:includedPage>
