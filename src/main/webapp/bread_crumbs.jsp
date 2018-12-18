<%@ taglib prefix="bbNG" uri="/bbNG" %>

<bbNG:includedPage authentication="Y" entitlement="course.control_panel.VIEW">

<%
  String bcUserId = request.getParameter ("user_id");
  String bcCourseId = request.getParameter ("course_id");
  //String bcContextualize = request.getParameter ("contextualize");
  //String bcSessionId = request.getParameter ("lpe_sid");

  //String baseHref = "index.jsp?context=" + contextQuery;
  String href = "index.jsp?context=" + contextQuery;

  String bcEnvironment = "course".equals (contextQuery) ? "COURSE" : "SYS_ADMIN";
%>

  <bbNG:breadcrumbBar environment="<%= bcEnvironment %>">
    <bbNG:breadcrumb href="<%= href %>">Learn PrivateEye</bbNG:breadcrumb>

    <%
      if ("course".equals (contextQuery)) {
        href += "&course_id=" + bcCourseId;

        %><bbNG:breadcrumb href="<%= href %>">Course Sessions</bbNG:breadcrumb><%

        if (!bcUserId.isEmpty()) {
          ;
        }
      } else if ("user".equals (contextQuery)) {
        href += "&contextualize=sessions&user_id=" + bcUserId;

        %><bbNG:breadcrumb href="<%= href %>">System Sessions</bbNG:breadcrumb><%
      }
    %>

  </bbNG:breadcrumbBar>

</bbNG:includedPage>
