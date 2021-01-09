<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>HR-ORG</title>
    <link rel="stylesheet" href="css/util.css">
    <link rel="stylesheet" href="css/main.css">

    <%@ include file="../head_common.jsp"%>
</head>

<body>
<%@ include file="../header.jsp" %>

<div class="nav-main">
    <%@ include file="../navigation.jsp" %>

    <main class="main">
        <c:choose>
            <c:when test="${job_application_dtos.size() > 0}">
                <div class="items">

                    <c:forEach var="applicationDto" items="${job_application_dtos}">
                        <div class="item">
                            <div class="item_info">
                                <div class="item_description">

                                    <h3 class="item_name" style="display: inline;">${applicationDto.vacancyName}</h3>
                                    <span style="margin-left: 10px; color: var(--accept-btn-color)">${applicationDto.state}</span>
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

        <hrt:page-bar numberOfPages="${number_of_pages}"
                      currentPage="${page}"
                      command="job_applications" />
    </main>
</div>
</body>

</html>