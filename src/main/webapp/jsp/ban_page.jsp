<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" href="css/util.css">
    <link rel="stylesheet" href="css/login.css">

    <%@ include file="head_common.jsp"%>

    <title><fmt:message key="title.banned" /></title>
</head>

<body>
<form class="login_form form shadow-medium" action="${pageContext.request.contextPath}/controller" method="post">
    <h2 class="alert-text"><fmt:message key="header.banned" /></h2>
    <p style="margin: 10px;"><fmt:message key="messages.sorry_for_ban" /></p>
    <p style="margin: 10px;"><fmt:message key="messages.contact_administrator" /></p>
</form>
</body>

</html>
