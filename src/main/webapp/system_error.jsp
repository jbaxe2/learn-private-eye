<%@ page contentType="text/html; charset=ISO-8859-1" isErrorPage="true" %>

<%@ taglib prefix="bbNG" uri="/bbNG" %>

<bbNG:learningSystemPage title="Learn PrivateEye Error!">
  <p>
    Learn PrivateEye's vision has been obstructed!<br><br>
  </p>

  <bbNG:error exception="<%= exception %>" />
</bbNG:learningSystemPage>
