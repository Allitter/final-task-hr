<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" href="css/util.css">
    <link rel="stylesheet" href="css/login.css">

    <%@ include file="head_common.jsp"%>

    <title>Banned</title>
</head>

<body>
<form class="login_form form shadow-medium" action="${pageContext.request.contextPath}/controller" method="post">
    <h2 class="alert-text">Banned</h2>
    <p style="margin: 10px;">Sorry, you was banned.</p>
    <p style="margin: 10px;">Contact the administrator: example@gmail.com</p>
</form>
</body>

</html>
