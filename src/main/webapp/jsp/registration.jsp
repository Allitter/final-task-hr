<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" href="css/util.css">
    <link rel="stylesheet" href="css/login.css">

    <%@ include file="head_common.jsp"%>

    <title><fmt:message key="registration.title" /></title>
</head>

<body>
<form class="form registration_form shadow-medium" action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="registration">

    <h2><fmt:message key="registration.title" /></h2>

    <input type="text" required maxlength="45" pattern="[_0-9A-Za-z]{3,}"
           title="<fmt:message key="registration.login_pattern_title" />" name="login"
           placeholder="<fmt:message key="registration.login_placeholder" />">
    <c:if test="${show_authentication_error}">
        <div class="message">
            <p class="alert-text"><fmt:message key="registration.login_already_used" /></p>
        </div>
    </c:if>

    <input type="password" pattern="[_*$()%0-9A-Za-z]{8,}"
           title="<fmt:message key="registration.password_pattern_title" />" required
           name="password" id="password" placeholder="<fmt:message key="registration.password_placeholder" />">
    <input type="password" pattern="[_*$()%0-9A-Za-z]{8,}"
           title="<fmt:message key="registration.password_pattern_title" />" required
           name="password_repeat" id="password_repeat" placeholder="<fmt:message key="registration.password_repeat_placeholder" />">
    <div class="message" id="passwords_dont_match_message"
            <c:choose>
                <c:when test="${show_none_unique_login_message}">
                    style="display: block;"
                </c:when>
                <c:otherwise>
                    style="display: none;"
                </c:otherwise>
            </c:choose>
            >
        <p class="alert-text"><fmt:message key="registration.passwords_dont_match" /></p>
    </div>


    <input type="text" pattern="[A-Z][A-Za-z]{1,}" title="<fmt:message key="registration.name_pattern_title" />"
           required maxlength="45" name="name" placeholder="<fmt:message key="registration.name_placeholder" />">
    <input type="text" pattern="[A-Z][A-Za-z]{1,}" title="<fmt:message key="registration.name_pattern_title" />"
           required maxlength="45" name="last_name" placeholder="<fmt:message key="registration.last_name_placeholder" />">



    <input type="text" pattern="[A-Z][A-Za-z]{1,}" title="<fmt:message key="registration.name_pattern_title" />"
           maxlength="45" name="patronymic" placeholder="<fmt:message key="registration.patronymic_placeholder" />">

    <label for="birth_date"><fmt:message key="registration.birth_date_label" /></label>
    <input type="date" required name="birth_date" id="birth_date">

    <div class="form_actions">
        <input class="btn shadow-none shadow-hov-small" type="submit" value="<fmt:message key="registration.submit_button" />">
        <a href="${pageContext.request.contextPath}/controller"><fmt:message key="registration.login_suggestion" /></a>
    </div>
</form>

<script src="js/registration_validation.js"></script>
</body>

</html>






