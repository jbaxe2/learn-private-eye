<%@ taglib prefix="bbNG" uri="/bbNG" %>

<bbNG:includedPage authentication="Y" entitlement="course.control_panel.VIEW">

<%
  String bcUserId = request.getParameter ("user_id");
  String bcCourseId = request.getParameter ("course_id");
  //String bcContextualize = request.getParameter ("contextualize");
  //String bcSessionId = request.getParameter ("lpe_sid");

  String href = "index.jsp?context=" + contextQuery;

  try {
    if (!((null == bcCourseId)) || bcCourseId.isEmpty()) {
      href += "&course_id=" + bcCourseId;
    }

    if (!((null == bcUserId)) || bcUserId.isEmpty()) {
      href += "&user_id=" + bcCourseId;
    }
  } catch (Exception e) {
    ; // Do nothing.
  }

  String bcEnvironment = "course".equals (contextQuery) ? "COURSE" : "SYS_ADMIN";
%>

  <bbNG:breadcrumbBar environment="<%= bcEnvironment %>">
    <bbNG:breadcrumb href="<%= href %>">Learn PrivateEye</bbNG:breadcrumb>

    <%
      if ("course".equals (contextQuery)) {
        %><bbNG:breadcrumb href="<%= href %>">Course Sessions</bbNG:breadcrumb><%

        if (!((null == bcUserId) || bcUserId.isEmpty())) {
          ;
        }
      } else if ("user".equals (contextQuery)) {
        href += "&contextualize=sessions";

        %><bbNG:breadcrumb href="<%= href %>">System Sessions</bbNG:breadcrumb><%
      }
    %>

  </bbNG:breadcrumbBar>

</bbNG:includedPage>
