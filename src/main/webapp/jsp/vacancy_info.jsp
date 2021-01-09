<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>HR-ORG</title>
    <link rel="stylesheet" href="css/util.css">
    <link rel="stylesheet" href="css/main.css">

    <%@ include file="head_common.jsp" %>
</head>

<body>
<%@ include file="header.jsp" %>

<div class="nav-main">
    <%@ include file="navigation.jsp" %>


    <main class="main item_more">
        <div class="item_header">
            <h2 class="item_name">${vacancy.name}</h2>

            <div class="item_header_buttons">
                <c:if test="${user.role.name() == 'EMPLOYEE'}">
                    <form action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="vacancy_edit">
                        <input type="hidden" name="vacancy_id" value="${vacancy.id}">
                        <button class="btn"><fmt:message key="button.edit"/></button>
                    </form>
                </c:if>
                <c:if test="${user.role.name() == 'EMPLOYEE'}">
                    <form action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="job_applications_for_vacancy">
                        <input type="hidden" name="vacancy_id" value="${vacancy.id}">
                        <button class="btn"><fmt:message key="button.applications"/></button>
                    </form>
                </c:if>
                <c:if test="${user.role.name() == 'JOB_SEEKER'}">
                    <form action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="vacancy_apply">
                        <input type="hidden" name="vacancy_id" value="${vacancy.id}">
                        <button class="btn"><fmt:message key="button.apply"/></button>
                    </form>
                </c:if>
            </div>
        </div>

        <div class="item_body"><span style="text-align: start;">${vacancy.description}</span></div>
    </main>

</div>
</body>

</html>

