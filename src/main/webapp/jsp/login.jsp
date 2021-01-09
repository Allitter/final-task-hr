<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" href="css/util.css">
    <link rel="stylesheet" href="css/login.css">

    <%@ include file="head_common.jsp"%>

    <title><fmt:message key="login.title" /></title>
</head>

<body>
<form class="login_form form shadow-medium" action="${pageContext.request.contextPath}/controller" method="post">
    <h2><fmt:message key="login.title" /></h2>

    <div class="message">
        <c:choose>
            <c:when test='${fails.contains("loginNullOrEmpty")}'><p class="alert-text"><fmt:message key="login.bad_credentials" /></p></c:when>
            <c:when test='${fails.contains("loginRegexFail")}'><p class="alert-text"><fmt:message key="login.bad_credentials" /></p></c:when>
            <c:when test='${fails.contains("passwordNullOrEmpty")}'><p class="alert-text"><fmt:message key="login.bad_credentials" /></p></c:when>
            <c:when test='${fails.contains("passwordRegexFail")}'><p class="alert-text"><fmt:message key="login.bad_credentials" /></p></c:when>
            <c:when test='${fails.contains("incorrectLoginOrPassword")}'><p class="alert-text"><fmt:message key="login.bad_credentials" /></p></c:when>
        </c:choose>
    </div>

    <input type="hidden" name="command" value="login">
    <input type="text" name="login" placeholder="<fmt:message key="login.login_label"/>" id="login">
    <input type="password" name="password" placeholder="<fmt:message key="login.password_label"/>" id="password">

    <div class="form_actions">
        <input class="btn shadow-none shadow-hov-small" type="submit" value="<fmt:message key="login.login_button"/>">
        <a href="${pageContext.request.contextPath}/controller?command=registration_page"><fmt:message key="login.register_suggestion"/></a>
    </div>
</form>
</body>

</html>
