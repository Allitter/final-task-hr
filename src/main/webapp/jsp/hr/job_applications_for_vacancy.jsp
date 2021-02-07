<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" href="css/util.css">
    <link rel="stylesheet" href="css/main.css">

    <%@ include file="../head_common.jsp"%>
    <title>HR-ORG</title>
</head>

<body>
<%@ include file="../header.jsp" %>

<div class="nav-main">
    <%@ include file="../navigation.jsp" %>

    <main class="main">
        <c:choose>
            <c:when test="${job_applications.size() > 0}">
                <h2><fmt:message key="title.job_applications_for"/> <q>${job_applications[0].vacancyName}</q></h2>
                <div class="items">
                    <c:forEach var="application" items="${job_applications}">
                        <div class="item">
                            <div class="item_info">
                                <div class="item_description">
                                    <p>
                                        <fmt:message key="label.status"/>:
                                        <span style="margin-left: 10px; color: var(--success-color)"><fmt:message key="enum.job_application_state.${application.state.name()}"/></span>
                                    </p>
                                    <p>
                                        <fmt:message key="label.creation_date"/>:
                                        <span style="margin-left: 10px; color: var(--main-bg-color)"><fmt:formatDate value="${application.date}" pattern="${dateFormat}" /></span>
                                    </p>
                                    <p>
                                        <fmt:message key="label.job_seeker"/>:
                                        <span>${application.userName}</span>
                                        <span style="margin-left: 10px;">${application.userLastName}</span>
                                        <span style="margin-left: 10px;">${application.userPatronymic}</span>
                                    </p>
                                </div>
                            </div>
                            <div class="actions">
                                <form action="${pageContext.request.contextPath}/controller" METHOD="get">
                                    <input type="hidden" name="command" value="job_application_info">
                                    <input type="hidden" name="job_application_id" value="${application.id}">
                                    <button class="single_action">
                                        <img src="images/open.png" alt="<fmt:message key="button.open" />">
                                    </button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <h3><fmt:message key="messages.no_job_applications_for_vacancy" /></h3>
            </c:otherwise>
        </c:choose>

        <hrt:page-bar page="${page}" command="job_applications_for_vacancy" />
    </main>
</div>
</body>

</html>