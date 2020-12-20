<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <link rel="stylesheet" href="css/login.css">
    <link rel="shortcut icon" href="favicon.ico" type="image/png">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Lora&family=Playfair+Display&display=swap" rel="stylesheet">
</head>

<body>
    <fmt:setLocale value="${lang}"/>
    <fmt:setBundle basename="properties.text" />

    <c:if test="${show_authentication_error}">
        <div id="signin_message_block" class="signin_message_block">
            <p id="signin_message"><fmt:message key="login.bad_credentials" /></p>
        </div>
    </c:if>

    <form class="signin_form" action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="login">
        <p>
            <label for="login"><fmt:message key="login.login_label"/>:</label>
            <input type="text" name="login" id="login">
        </p>
        <p>
            <label for="password"><fmt:message key="login.password_label"/>:</label>
            <input type="password" name="password" id="password">
        </p>
        <p>
            <input type="submit" value="<fmt:message key="login.login_button"/>">
        </p>
    </form>
</body>

</html>
