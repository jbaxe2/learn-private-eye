<!DOCTYPE html>

<%@ taglib prefix="bbNG" uri="/bbNG" %>

<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<bbNG:learningSystemPage title="Learn PrivateEye" authentication="Y">
  <bbNG:pageHeader>
    <bbNG:pageTitleBar title="Learn PrivateEye" />
  </bbNG:pageHeader>

  <%
    String contextQuery = request.getParameter ("_context");

    if (null == contextQuery || contextQuery.isEmpty ()) {
      contextQuery = "course";
    }

    try {
      if (contextQuery.equals ("course")) {
        String userIdQuery = request.getParameter ("user_id");

        if (null == userIdQuery || userIdQuery.isEmpty ()) {
          %><%@ include file="course_user_sessions_count.jsp"%><%
        } else {
          String sessionIdQuery = request.getParameter ("session_id");

          if (null == sessionIdQuery || sessionIdQuery.isEmpty()) {
            %><%@ include file="course_user_sessions.jsp"%><%
          } else {
            %><%@ include file="course_user_session.jsp"%><%
          }
        }
      } else if (contextQuery.equals ("user")) {
        %><%@ include file="user_contextualizer.jsp"%><%
      } else {
        %><p>Context is: <%= contextQuery %></p><%
      }
    } catch (Exception e) {
      %><bbNG:error exception="<%= e %>" /><%
    }
  %>
</bbNG:learningSystemPage>
