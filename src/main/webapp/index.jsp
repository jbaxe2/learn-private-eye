<!DOCTYPE html>

<%@ taglib prefix="bbNG" uri="/bbNG" %>

<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<bbNG:learningSystemPage title="Learn PrivateEye" authentication="Y">
  <bbNG:pageHeader>
    <bbNG:pageTitleBar title="Learn PrivateEye" />

    <bbNG:breadcrumbBar>
      <bbNG:breadcrumb>Learn PrivateEye</bbNG:breadcrumb>
    </bbNG:breadcrumbBar>
  </bbNG:pageHeader>

  <%
    String contextQuery = request.getParameter ("context");

    if (null == contextQuery || contextQuery.isEmpty ()) {
      contextQuery = "course";
    }

    try {
      if (contextQuery.equals ("course")) {
        %><%@ include file="course_user_sessions_count.jsp"%><%
      } else if (contextQuery.equals ("user")) {
        %><%@ include file="user_contextualizer.jsp"%><%
      }
    } catch (Exception e) {
      %><bbNG:error exception="<%= e %>" /><%
    }
  %>
</bbNG:learningSystemPage>
