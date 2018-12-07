<!DOCTYPE html>

<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%@ taglib prefix="bbNG" uri="/bbNG" %>

<bbNG:learningSystemPage title="Learn PrivateEye" authentication="Y">
  <bbNG:pageHeader>
    <bbNG:pageTitleBar title="Learn PrivateEye" />
  </bbNG:pageHeader>

  <%
    String contextQuery = request.getParameter ("context");

    if (null == contextQuery || contextQuery.isEmpty ()) {
      contextQuery = "course";
    }

    try {
      if ("course".equals (contextQuery)) {
        String userIdQuery = request.getParameter ("user_id");

        if (null == userIdQuery || userIdQuery.isEmpty ()) {
          %><%@ include file="course_user_sessions_count.jsp"%><%
        } else {
          String sessionIdQuery = request.getParameter ("lpe_sid");

          if (null == sessionIdQuery || sessionIdQuery.isEmpty()) {
            %><%@ include file="course_user_sessions.jsp"%><%
          } else {
            %><%@ include file="course_user_session.jsp"%><%
          }
        }
      } else if ("user".equals (contextQuery)) {
        %><%@ include file="user_contextualizer.jsp"%><%
      } else {
        %><%@ include file="error.jsp"%><%
      }
    } catch (Exception e) {
      %><bbNG:error exception="<%= e %>" /><%
    }
  %>
</bbNG:learningSystemPage>
