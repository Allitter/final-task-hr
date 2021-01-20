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
                <div class="items">

                    <c:forEach var="applicationDto" items="${job_applications}">
                        <div class="item">
                            <div class="item_info">
                                <div class="item_description">

                                    <h3 class="item_name" style="display: inline;">${applicationDto.vacancyName}</h3>
                                    <span style="margin-left: 10px; color: var(--accept-btn-color)"><fmt:message key="enum.job_application_state.${applicationDto.state.name()}"/></span>
                                    <span style="margin-left: 10px; color: var(--main-bg-color)">${applicationDto.date}</span>
                                    <p class="item_short_desc">${applicationDto.vacancyShortDescription}</p>
                                </div>
                            </div>
                            <div class="actions">
                                <form action="${pageContext.request.contextPath}/controller" METHOD="get">
                                    <input type="hidden" name="command" value="vacancy_info">
                                    <input type="hidden" name="vacancy_id" value="${applicationDto.idVacancy}">
                                    <button class="btn"><fmt:message key="button.vacancy" /></button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <h3><fmt:message key="messages.no_job_applications" /></h3>
            </c:otherwise>
        </c:choose>

        <hrt:page-bar page="${page}" command="job_applications" />
    </main>
</div>
</body>

</html>