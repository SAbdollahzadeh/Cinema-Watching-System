<%--
  Created by IntelliJ IDEA.
  User: SHS-WS
  Date: 11/3/2025
  Time: 10:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Login</title></head>
<body>
<h2>Please Login</h2>
<form method="POST" action="j_security_check">
  Username: <input type="text" name="j_username"/><br/>
  Password: <input type="password" name="j_password"/><br/>
  <input type="submit" value="Login"/>
</form>
</body>
</html>
