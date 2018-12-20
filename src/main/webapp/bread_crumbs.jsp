<%@ taglib prefix="bbNG" uri="/bbNG" %>

<bbNG:includedPage authentication="Y" entitlement="course.control_panel.VIEW">

<%
  String bcUserId = request.getParameter ("user_id");
  String bcCourseId = request.getParameter ("course_id");
  String bcContextualize = request.getParameter ("contextualize");
  String bcSessionId = request.getParameter ("lpe_sid");
  String bcForContextualize = request.getParameter ("forContextualize");

  String baseHref = "index.jsp?context=" + contextQuery + "&startIndex=0";
  String href = baseHref;

  try {
    if (!((null == bcCourseId)) || bcCourseId.isEmpty()) {
      href = baseHref + "&course_id=" + bcCourseId;
    }
  } catch (Exception e) {
    ; // Do nothing; we may be in user context.
  }

  //String bcEnvironment = "course".equals (contextQuery) ? "CTRL_PANEL" : "PORTAL";
%>

  <bbNG:breadcrumbBar>
    <bbNG:breadcrumb href="<%= href %>">
      <%
        if ("course".equals (contextQuery)) {
          %>Enrollments' Sessions Counts<%
        } else if ("user".equals (contextQuery)) {
          %>Username Injector<%
        }
      %>
    </bbNG:breadcrumb>

    <%
      if ("course".equals (contextQuery)) {
        if (!((null == bcUserId) || bcUserId.isEmpty())) {
          href = href + "&user_id=" + bcUserId;

          %><bbNG:breadcrumb href="<%= href %>">Enrollment Sessions</bbNG:breadcrumb><%

          if (!((null == bcSessionId) || bcSessionId.isEmpty())) {
            href = baseHref + "&course_id=" + bcCourseId +
              "&user_id=" + bcUserId;

            %><bbNG:breadcrumb href="<%= href %>">
              Session #<%= bcSessionId %>
            </bbNG:breadcrumb><%
          }
        }
      } else if ("user".equals (contextQuery)) {
        if (!((null == bcContextualize) || bcContextualize.isEmpty())) {
          href = baseHref + "&contextualize=sessions&user_id=" + bcUserId;

          %><bbNG:breadcrumb href="<%= href %>">User Session Counts</bbNG:breadcrumb><%

          if (!((null == bcSessionId) || bcSessionId.isEmpty())) {
            if ("system".equals (bcContextualize) ||
                "logins".equals (bcContextualize)) {
              String breadCrumbText = "User Non-Course Sessions";

              if (!((null == bcForContextualize) || bcForContextualize.isEmpty()) &&
                   "logins".equals (bcForContextualize)) {
                href = baseHref + "&contextualize=logins&user_id=" + bcUserId;
                breadCrumbText = "User Login Attempts";
              } else {
                href = baseHref + "&contextualize=system&user_id=" + bcUserId;
              }

              %><bbNG:breadcrumb href="<%= href %>">
                <%= breadCrumbText %>
              </bbNG:breadcrumb><%

              if ("system".equals (bcContextualize)) {
                %><bbNG:breadcrumb>Session #<%= bcSessionId %></bbNG:breadcrumb><%
              }
            }
          } else {
            if ("system".equals (bcContextualize) ||
                "logins".equals (bcContextualize)) {
              href = baseHref + "&contextualize=logins&user_id=" + bcUserId;

              %><bbNG:breadcrumb href="<%= href %>">
                <%
                  if ("system".equals (bcContextualize)) {
                    %>User Non-Course Sessions<%
                  } else {
                    %>User Login Attempts<%
                  }
                %>
              </bbNG:breadcrumb><%
            }
          }
        }
      }
    %>

  </bbNG:breadcrumbBar>

</bbNG:includedPage>
