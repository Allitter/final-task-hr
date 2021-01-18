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
            <c:when test="${job_applications.size() > 0}">
                <div class="items">
                    <c:forEach var="applicationDto" items="${job_applications}">
                        <div class="item">
                            <div class="item_info">
                                <div class="item_description">
                                    <h3 class="item_name" style="display: inline;">${applicationDto.vacancyName}</h3>
                                    <span style="margin-left: 10px; color: var(--accept-btn-color)"><fmt:message key="enum.job_application_state.${applicationDto.state.name()}"/></span>
                                    <span style="margin-left: 10px; color: var(--main-bg-color)"><fmt:formatDate value="${applicationDto.date}" pattern="${dateFormat}" /></span>
                                    <br>
                                    <span>${applicationDto.userName}</span>
                                    <span style="margin-left: 10px;">${applicationDto.userLastName}</span>
                                    <span style="margin-left: 10px;">${applicationDto.userPatronymic}</span>
                                </div>
                            </div>
                            <div class="actions">
                                <form action="${pageContext.request.contextPath}/controller" METHOD="get">
                                    <input type="hidden" name="command" value="job_application_info">
                                    <input type="hidden" name="job_application_id" value="${applicationDto.id}">
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