<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>HR-ORG</title>
    <link rel="stylesheet" href="css/util.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/login.css">

    <%@ include file="head_common.jsp"%>
</head>

<body>
<%@ include file="header.jsp" %>

<div class="nav-main">
    <%@ include file="navigation.jsp" %>

    <main class="main" style="width: 100%; text-align: left;">
        <form class="form" action="${pageContext.request.contextPath}/controller" method="post" style="border-radius: 2rem; margin: 10px 0; width: 100%; box-sizing: border-box; border: 2px solid var(--secondary-color)">
            <input type="hidden" name="command" value="account_update">

            <h2><!--<fmt:message key="registration.title" />--></h2>

            <div class="message">
                <c:if test='${fails.contains("loginNullOrEmpty")}'><p class="alert-text"><fmt:message key="messages.login"/> <fmt:message key="messages.cant_be_empty"/></p></c:if>
                <c:if test='${fails.contains("loginRegexFail")}'><p class="alert-text"><fmt:message key="messages.login"/> <fmt:message key="messages.incorrect"/></p></c:if>
                <c:if test='${fails.contains("loginNotUnique")}'><p class="alert-text"><fmt:message key="messages.login"/> <fmt:message key="messages.not_unique"/></c:if>
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
                   placeholder="<fmt:message key="registration.login.placeholder" />" value="${user.login}">

            <input type="text" pattern="(^[А-ЯЁ][а-яё]+$)|(^[A-Z][a-z]+$)" title="<fmt:message key="registration.name.pattern_title" />"
                   required maxlength="15" name="name" placeholder="<fmt:message key="registration.name.placeholder" />" value="${user.name}">
            <input type="text" pattern="(^[А-ЯЁ][а-яё]+$)|(^[A-Z][a-z]+$)" title="<fmt:message key="registration.name.pattern_title" />"
                   required maxlength="15" name="last_name" placeholder="<fmt:message key="registration.last_name.placeholder" />" value="${user.lastName}">
            <input type="text" pattern="(^[А-ЯЁ][а-яё]+$)|(^[A-Z][a-z]+$)" title="<fmt:message key="registration.name.pattern_title" />"
                   maxlength="15" name="patronymic" placeholder="<fmt:message key="registration.patronymic.placeholder" />" value="${user.patronymic}">

            <input type="email" pattern="^[A-Za-z_0-9.-]+@([A-Za-z_0-9-]+\.)+[A-Za-z_0-9-]{2,4}$" required title="<fmt:message key="registration.email.pattern_title" />"
                   name="email" maxlength="320" placeholder="<fmt:message key="registration.email.placeholder" />" value="${user.email}">

            <input type="tel" required pattern="[+][0-9]{7,14}" title="<fmt:message key="registration.phone_pattern_title" />"
                   name="phone" maxlength="15" placeholder="<fmt:message key="registration.phone.placeholder" />" value="${user.phone}">

            <label for="birth_date"><fmt:message key="registration.birth_date.label" /></label>
            <input type="date" required name="birth_date" id="birth_date" value="${user.getFormattedBirthDate()}" min="1950-01-01" max="2007-12-31">

            <div class="form_actions" style="justify-content: center;">
                <input class="btn shadow-none shadow-hov-small" type="submit" value="<fmt:message key="button.save" />" style="margin: 0; background-color: var(--accept-btn-color)">
            </div>
        </form>

        <c:if test="${user.role.name() == 'JOB_SEEKER'}">
            <div class="account_resumes">
                <h3><fmt:message key="header.resumes" /></h3>
                <div class="resumes">
                    <c:forEach items="${resumes}" var="resume">
                        <div class="single_resume_item">
                            <a href="${pageContext.request.contextPath}/controller?command=resume_info&resume_id=${resume.id}"><span>${resume.name}</span></a>
                            <form action="${pageContext.request.contextPath}/controller" method="post">
                                <input type="hidden" name="command" value="resume_remove">
                                <input type="hidden" name="resume_id" value="${resume.id}">

                                <button class="single_action">
                                    <img src="images/cross.png" alt="<fmt:message key="button.remove" />">
                                </button>
                            </form>
                        </div>
                    </c:forEach>
                </div>

                <form action="${pageContext.request.contextPath}/controller" method="get" style="width: max-content;">
                    <input type="hidden" name="command" value="resume_add">
                    <input class="btn shadow-none shadow-hov-small" type="submit" value="<fmt:message key="button.add" />" style="background-color: var(--accept-btn-color)">
                </form>
            </div>
        </c:if>
    </main>
</div>
</body>

</html>