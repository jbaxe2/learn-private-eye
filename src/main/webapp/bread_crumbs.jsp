<%@ taglib prefix="bbNG" uri="/bbNG" %>

<bbNG:includedPage authentication="Y" entitlement="course.control_panel.VIEW">

<%
  String bcUserId = request.getParameter ("user_id");
  String bcCourseId = request.getParameter ("course_id");
  //String bcContextualize = request.getParameter ("contextualize");
  String bcSessionId = request.getParameter ("lpe_sid");

  String baseHref = "index.jsp?context=" + contextQuery + "&startIndex=0";
  String href = baseHref;

  try {
    if (!((null == bcCourseId)) || bcCourseId.isEmpty()) {
      href = baseHref + "&course_id=" + bcCourseId;
    }
  } catch (Exception e) {
    ; // Do nothing.
  }

  String bcEnvironment = "course".equals (contextQuery) ? "COURSE" : "SYS_ADMIN";
%>

  <bbNG:breadcrumbBar environment="<%= bcEnvironment %>">
    <bbNG:breadcrumb href="<%= href %>">
      <%
        if ("course".equals (contextQuery)) {
          %>Enrollments' Session Counts<%
        } else if ("user".equals (contextQuery)) {
          %>Username Injector<%
        }
      %>
    </bbNG:breadcrumb>

    <%
      if ("course".equals (contextQuery)) {
        if (!((null == bcUserId) || bcUserId.isEmpty())) {
          href = href + "&user_id=" + bcUserId;

          %><bbNG:breadcrumb href="<%= href %>">
            Enrollment Sessions
          </bbNG:breadcrumb><%

          if (!((null == bcSessionId) || bcSessionId.isEmpty())) {
            href = baseHref + "&course_id=" + bcCourseId +
              "&user_id=" + bcUserId;

            %><bbNG:breadcrumb href="<%= href %>">
              Session #<%= bcSessionId %>
            </bbNG:breadcrumb><%
          }
        }
      } else if ("user".equals (contextQuery)) {
        href = baseHref + "&contextualize=sessions";

        %><bbNG:breadcrumb href="<%= href %>">User Session Counts</bbNG:breadcrumb><%
      }
    %>

  </bbNG:breadcrumbBar>

</bbNG:includedPage>
