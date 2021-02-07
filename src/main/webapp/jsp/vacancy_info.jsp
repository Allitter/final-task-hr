<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" href="css/util.css">
    <link rel="stylesheet" href="css/main.css">

    <%@ include file="head_common.jsp" %>
    <title>HR-ORG</title>
</head>

<body>
<%@ include file="header.jsp" %>

<div class="nav-main">
    <%@ include file="navigation.jsp" %>


    <main class="main item_more">
        <div class="control_set">
            <c:if test="${user.role.name() == 'EMPLOYEE'}">
                <form action="${pageContext.request.contextPath}/controller"
                      method="get">
                    <input type="hidden" name="command" value="vacancy_edit">
                    <input type="hidden" name="vacancy_id"
                           value="${vacancy.id}">
                    <button class="btn"><fmt:message
                            key="button.edit"/></button>
                </form>
            </c:if>
            <c:if test="${user.role.name() == 'EMPLOYEE'}">
                <form action="${pageContext.request.contextPath}/controller"
                      method="get">
                    <input type="hidden" name="command"
                           value="job_applications_for_vacancy">
                    <input type="hidden" name="vacancy_id"
                           value="${vacancy.id}">
                    <button class="btn"><fmt:message
                            key="button.applications"/></button>
                </form>
            </c:if>
            <c:if test="${user.role.name() == 'JOB_SEEKER' && !vacancy.isClosed() && !already_applied}">
                <form action="${pageContext.request.contextPath}/controller"
                      method="get">
                    <input type="hidden" name="command" value="vacancy_apply">
                    <input type="hidden" name="vacancy_id"
                           value="${vacancy.id}">
                    <button class="btn"><fmt:message
                            key="button.apply"/></button>
                </form>
            </c:if>
            <c:if test="${user.role.name() == 'JOB_SEEKER' && !vacancy.isClosed() && already_applied}">
                <form action="${pageContext.request.contextPath}/controller"
                      method="get">
                    <input type="hidden" name="command"
                           value="job_application_info">
                    <input type="hidden" name="job_application_id"
                           value="${job_application.id}">
                    <button class="btn"><fmt:message
                            key="button.job_application"/></button>
                </form>
            </c:if>
            <c:if test="${user.role.name() == 'EMPLOYEE'}">
                <form action="${pageContext.request.contextPath}/controller"
                      method="post">
                    <input type="hidden" name="command"
                           value="confirmation_page">
                    <input type="hidden" name="target_command"
                           value="vacancy_close">
                    <input type="hidden" name="vacancy_id"
                           value="${vacancy.id}">
                    <button class="btn"
                            style="background-color: var(--danger-color)">
                        <fmt:message key="button.close"/></button>
                </form>
            </c:if>
        </div>


        <div class="item_header">
            <h2 class="item_name">${vacancy.name}
                <c:if test="${vacancy.isClosed()}">
                    <span style="color: var(--danger-color); font-size: 1rem;"><fmt:message
                            key="messages.closed"/></span>
                </c:if>
            </h2>
        </div>

        <div class="item_body"><span
                style="text-align: start;">${vacancy.description}</span></div>
    </main>

</div>
</body>

</html>

