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
        <form class="form" action="${pageContext.request.contextPath}/controller" method="post"
              style="border-radius: 2rem; margin: 10px 0; width: 100%; box-sizing: border-box; border: 2px solid var(--secondary-color)">
            <input type="hidden" name="command" value="account_update">

            <input type="text" required maxlength="45" pattern="[_0-9A-Za-z]{3,}"
                   title="<fmt:message key="registration.login_pattern_title" />" name="login"
                   placeholder="<fmt:message key="registration.login_placeholder" />" value="${user.login}">
            <c:if test="${show_authentication_error}">
                <div class="message">
                    <p class="alert-text"><fmt:message key="registration.login_already_used" /></p>
                </div>
            </c:if>

            <input type="text" pattern="[A-Z][A-Za-z]{1,}" title="<fmt:message key="registration.name_pattern_title" />"
                   required maxlength="45" name="name" placeholder="<fmt:message key="registration.name_placeholder" />" value="${user.name}">
            <input type="text" pattern="[A-Z][A-Za-z]{1,}" title="<fmt:message key="registration.name_pattern_title" />"
                   required maxlength="45" name="last_name" placeholder="<fmt:message key="registration.last_name_placeholder" />" value="${user.lastName}">
            <input type="text" pattern="[A-Z][A-Za-z]{1,}" title="<fmt:message key="registration.name_pattern_title" />"
                   maxlength="45" name="patronymic" placeholder="<fmt:message key="registration.patronymic_placeholder" />" value="${user.patronymic}">

            <label for="birth_date"><fmt:message key="registration.birth_date_label" /></label>
            <input type="date" required name="birth_date" id="birth_date" value="${user.getFormattedBirthDate()}">

            <div class="form_actions" style="justify-content: center;">
                <input class="btn shadow-none shadow-hov-small" type="submit" value="<fmt:message key="button.modify" />"
                       style="margin: 0; background-color: var(--accept-btn-color)">
            </div>
        </form>

        <c:if test="${user.role.name() == 'JOB_SEEKER'}">
            <div class="account_resumes">
                <h3>Resumes</h3>
                <div class="resumes">
                    <c:forEach items="${resumes}" var="resume">
                        <div class="single_resume_item">
                            <a href="${pageContext.request.contextPath}/controller?command=resume_info&resume_id=${resume.id}"><span>${resume.name}</span></a>
                            <form action="${pageContext.request.contextPath}/controller" method="post">
                                <input type="hidden" name="command" value="resume_remove">
                                <input type="hidden" name="resume_id" value="${resume.id}">

                                <button class="single_action">
                                    <img src="images/cross.png" alt="remove">
                                </button>
                            </form>
                        </div>
                    </c:forEach>
                </div>

                <form action="${pageContext.request.contextPath}/controller" method="post"
                      style="width: max-content;">
                    <input type="hidden" name="command" value="resume_add">

                    <input class="btn shadow-none shadow-hov-small" type="submit" value="<fmt:message key="button.add" />"
                           style="background-color: var(--accept-btn-color)">
                </form>
            </div>
        </c:if>
    </main>
</div>
</body>

</html>