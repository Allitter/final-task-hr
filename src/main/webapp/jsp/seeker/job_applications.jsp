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
                        <form action="${pageContext.request.contextPath}/controller" METHOD="post">
                            <input type="hidden" name="command" value="resume_info">
                            <input type="hidden" name="resume_id" value="${applicationDto.idResume}">
                            <button class="btn">
                                resume
                            </button>
                        </form>
                        <form action="${pageContext.request.contextPath}/controller" METHOD="post">
                            <input type="hidden" name="command" value="vacancy_info">
                            <input type="hidden" name="vacancy_id" value="${applicationDto.idVacancy}">
                            <button class="btn">
                                vacancy
                            </button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="page_bar">
            <a href="${pageContext.request.contextPath}/controller?command=job_applications&page=${page - 1}"><fmt:message key="main.previous_page" /></a>
            <a href="${pageContext.request.contextPath}/controller?command=job_applications&page=${page + 1}"><fmt:message key="main.next_page" /></a>
        </div>
    </main>
</div>
</body>

</html>