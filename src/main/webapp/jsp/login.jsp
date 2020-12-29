<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" href="css/util.css">
    <link rel="stylesheet" href="css/login.css">

    <%@ include file="head_common.jsp"%>
</head>

<body>
    <form class="signin_form" action="${pageContext.request.contextPath}/controller" method="post">
        <c:if test="${show_authentication_error}">
            <div class="signin_message">
                <p class="alert-text">
                    <fmt:message key="login.bad_credentials" />
                </p>
            </div>
        </c:if>

        <input type="hidden" name="command" value="login">
        <input type="text" name="login" placeholder="<fmt:message key="login.login_label"/>" id="login">
        <input type="password" name="password" placeholder="<fmt:message key="login.password_label"/>" id="password">

        <input class="btn shadow-none shadow-hov-small" type="submit" value="<fmt:message key="login.login_button"/>">
    </form>
</body>

</html>
