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

    <div style="display: flex; justify-content: center; margin-top: 20px; margin-bottom: 10px;">
        <form action="${pageContext.request.contextPath}/controller" method="GET">
            <input type="hidden" name="command" value="logout">
            <input class="btn" type="submit" value="<fmt:message key="header.logout_button"/>" style="width: 100px; background-color: var(--danger-color); margin: 0;">
        </form>
    </div>
</form>
</body>

</html>
