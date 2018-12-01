<!DOCTYPE html>

<%@ taglib prefix="bbNG" uri="/bbNG" %>

<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%@ page import="
  blackboard.db.ConnectionNotAvailableException,
  blackboard.persist.Id,
  blackboard.platform.plugin.PlugInUtil,
  _persistence.PersistenceManager,
  context.PrivateEyeContext,
  context.PrivateEyeCourseContext"
%>

<bbNG:learningSystemPage title="Learn PrivateEye" authentication="Y" entitlement="course.control_panel.VIEW">
<%
  Id courseId = PlugInUtil.getCourseId();

  PrivateEyeContext context = new PrivateEyeCourseContext (courseId);
  PersistenceManager persistenceManager = null;

  try {
    persistenceManager = new PersistenceManager();
  } catch (ConnectionNotAvailableException e) {
    %><p>Error with Learn PrivateEye:<br><%= e.getMessage() %></p><%
  }

  if (null != persistenceManager) {
    persistenceManager.closeConnection();
  }
%>

  <bbNG:pageHeader>
    <bbNG:pageTitleBar title="Learn PrivateEye" />

    <bbNG:breadcrumbBar environment="course">
      <bbNG:breadcrumb>Learn PrivateEye</bbNG:breadcrumb>
    </bbNG:breadcrumbBar>
  </bbNG:pageHeader>

  <p>Loaded course context and persistence manager successfully.</p>

<%
  if (null != persistenceManager) {
    persistenceManager.closeConnection();
  }
%>
</bbNG:learningSystemPage>
