<%@ taglib prefix="bbNG" uri="/bbNG" %>

<%@ page import="blackboard.platform.plugin.PlugInUtil"%>

<bbNG:includedPage>
  <p>
    Learn PrivateEye's vision has been obstructed!<br><br>
    Please allow us a moment to reinitialize.
  </p>

  <%
    String redirectUrl = "5,url=index.jsp?context=course&course_id=" +
      PlugInUtil.getCourseId().getExternalString();

    response.setHeader ("Refresh", redirectUrl);
  %>
</bbNG:includedPage>
