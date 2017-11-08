<%--
  Created by IntelliJ IDEA.
  User: Oleg.Shmyrin
  Date: 11/7/2017
  Time: 12:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/hello.tld" prefix="jstlpg"%>
<html>
<head>
    <title>Hello World Sample</title>
</head>
<body>
    <h1>
        <jstlpg:hello name="<%= request.getParameter("name")%>" />
    </h1>
</body>
</html>
