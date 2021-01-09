<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" href="css/util.css">
    <link rel="stylesheet" href="css/login.css">

    <%@ include file="head_common.jsp"%>

    <title><fmt:message key="verification.title" /></title>
</head>

<body>
<form class="login_form form shadow-medium" action="${pageContext.request.contextPath}/controller" method="post">
    <h2><fmt:message key="verification.title" /></h2>
    <h3><fmt:message key="verification.tip" /></h3>

    <c:if test="${show_authentication_error}">
        <div class="message">
            <p class="alert-text">
                <fmt:message key="verification.incorrect_code" />
            </p>
        </div>
    </c:if>

    <input type="hidden" name="command" value="verification">
    <input type="text" name="verification_code" placeholder="<fmt:message key="verification.code"/>" id="login">

    <div class="form_actions">
        <input class="btn shadow-none shadow-hov-small" type="submit" value="<fmt:message key="button.accept"/>">
    </div>
</form>
</body>

</html>
