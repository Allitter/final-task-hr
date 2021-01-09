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

    <div class="signin_message">
        <c:if test='${fails.contains("loginNullOrEmpty")}'><p class="alert-text"><fmt:message key="messages.login"/> <fmt:message key="messages.cant_be_empty"/></p></c:if>
        <c:if test='${fails.contains("loginRegexFail")}'><p class="alert-text"><fmt:message key="messages.login"/> <fmt:message key="messages.incorrect"/></p></c:if>
        <c:if test='${fails.contains("loginNotUnique")}'><p class="alert-text"><fmt:message key="messages.login"/> <fmt:message key="messages.not_unique"/></c:if>
        <c:if test='${fails.contains("passwordNullOrEmpty")}'><p class="alert-text"><fmt:message key="messages.password"/> <fmt:message key="messages.cant_be_empty"/></p></c:if>
        <c:if test='${fails.contains("passwordRegexFail")}'><p class="alert-text"><fmt:message key="messages.password"/> <fmt:message key="messages.incorrect"/></p></c:if>
        <c:if test='${fails.contains("passwordTooWeak")}'><p class="alert-text"><fmt:message key="messages.password"/> <fmt:message key="messages.too_weak"/></p></c:if>
        <c:if test='${fails.contains("nameNullOrEmpty")}'><p class="alert-text"><fmt:message key="messages.name"/> <fmt:message key="messages.cant_be_empty"/></p></c:if>
        <c:if test='${fails.contains("nameRegexFail")}'><p class="alert-text"><fmt:message key="messages.name"/> <fmt:message key="messages.incorrect"/></p></c:if>
        <c:if test='${fails.contains("lastNameNullOrEmpty")}'><p class="alert-text"><fmt:message key="messages.lastName"/> <fmt:message key="messages.cant_be_empty"/></p></c:if>
        <c:if test='${fails.contains("lastNameRegexfail")}'><p class="alert-text"><fmt:message key="messages.lastName"/> <fmt:message key="messages.incorrect"/></p></c:if>
        <c:if test='${fails.contains("patronymicNullOrEmpty")}'><p class="alert-text"><fmt:message key="messages.password"/> <fmt:message key="messages.cant_be_empty"/></p></c:if>
        <c:if test='${fails.contains("patronymicRegexFail")}'><p class="alert-text"><fmt:message key="messages.patronymic"/> <fmt:message key="messages.incorrect"/></p></c:if>
        <c:if test='${fails.contains("emailNullOrEmpty")}'><p class="alert-text"><fmt:message key="messages.email"/> <fmt:message key="messages.cant_be_empty"/></p></c:if>
        <c:if test='${fails.contains("emailRegexFail")}'><p class="alert-text"><fmt:message key="messages.email"/> <fmt:message key="messages.incorrect"/></p></c:if>
        <c:if test='${fails.contains("emailOutOfBounds")}'><p class="alert-text"><fmt:message key="messages.email"/> <fmt:message key="messages.out_of_bounds"/></p></c:if>
        <c:if test='${fails.contains("phoneNullOrEmpty")}'><p class="alert-text"><fmt:message key="messages.phone"/> <fmt:message key="messages.cant_be_empty"/></p></c:if>
        <c:if test='${fails.contains("phoneRegexFail")}'><p class="alert-text"><fmt:message key="messages.phone"/> <fmt:message key="messages.incorrect"/></p></c:if>
        <c:if test='${fails.contains("birthDateNullOrEmpty")}'><p class="alert-text"><fmt:message key="messages.birthDate"/> <fmt:message key="messages.cant_be_empty"/></p></c:if>
        <c:if test='${fails.contains("birthDateRegexFail")}'><p class="alert-text"><fmt:message key="messages.birthDate"/> <fmt:message key="messages.incorrect"/></p></c:if>
        <c:if test='${fails.contains("birthDateOutOfBounds")}'><p class="alert-text"><fmt:message key="messages.birthDate"/> <fmt:message key="messages.out_of_bounds"/></p></c:if>
    </div>

    <input type="text" required maxlength="15" pattern="[_0-9A-Za-z]{3,}"
           title="<fmt:message key="registration.login.pattern_title" />" name="login"
           placeholder="<fmt:message key="registration.login.placeholder" />">
    <c:if test="${show_authentication_error}">
        <div class="message">
            <p class="alert-text"><fmt:message key="registration.login_already_used" /></p>
        </div>
    </c:if>

    <input type="password" pattern="[_*$()%0-9A-Za-z]{8,}" maxlength="32"
           title="<fmt:message key="registration.password.pattern_title" />" required
           name="password" id="password" placeholder="<fmt:message key="registration.password.placeholder" />">
    <input type="password" pattern="[_*$()%0-9A-Za-z]{8,}" maxlength="32"
           title="<fmt:message key="registration.password.pattern_title" />" required
           name="password_repeat" id="password_repeat" placeholder="<fmt:message key="registration.password_repeat.placeholder" />">
    <div id="passwords_dont_match_message" style="display: none;">
        <p class="alert-text"><fmt:message key="registration.passwords_dont_match" /></p>
    </div>


    <input type="text" pattern="(^[А-ЯЁ][а-яё]$)|(^[A-Z][a-z]+$)" title="<fmt:message key="registration.name.pattern_title" />"
           required maxlength="15" name="name" placeholder="<fmt:message key="registration.name.placeholder" />">
    <input type="text" pattern="(^[А-ЯЁ][а-яё]+$)|(^[A-Z][a-z]+$)" title="<fmt:message key="registration.name.pattern_title" />"
           required maxlength="15" name="last_name" placeholder="<fmt:message key="registration.last_name.placeholder" />">
    <input type="text" pattern="(^[А-ЯЁ][а-яё]+$)|(^[A-Z][a-z]+$)" title="<fmt:message key="registration.name.pattern_title" />"
           maxlength="15" name="patronymic" placeholder="<fmt:message key="registration.patronymic.placeholder" />">

    <input type="email" maxlength="320" pattern="^[A-Za-z_0-9.-]+@([A-Za-z_0-9-]+\.)+[A-Za-z_0-9-]{2,4}$" required title="<fmt:message key="registration.email.pattern_title" />"
           name="email" placeholder="<fmt:message key="registration.email.placeholder" />">

    <input type="tel" maxlength="15" required pattern="[+][0-9]{7,14}" title="<fmt:message key="registration.phone_pattern_title" />"
           name="phone" placeholder="<fmt:message key="registration.phone.placeholder" />">

    <label for="birth_date"><fmt:message key="registration.birth_date.label" /></label>
    <input type="date" required name="birth_date" id="birth_date"  min="1950-01-01" max="2007-12-31">

    <div class="form_actions">
        <input class="btn shadow-none shadow-hov-small" type="submit" value="<fmt:message key="registration.submit_button.caption" />">
        <a href="${pageContext.request.contextPath}/controller"><fmt:message key="registration.login_suggestion" /></a>
    </div>
</form>

<script src="js/registration_validation.js"></script>
</body>

</html>






