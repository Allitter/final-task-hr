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
                            <br>
                            <span>${applicationDto.userName}</span>
                            <span style="margin-left: 10px;">${applicationDto.userLastName}</span>
                            <span style="margin-left: 10px;">${applicationDto.userPatronymic}</span>
                        </div>
                    </div>
                    <div class="actions">
                        <form action="${pageContext.request.contextPath}/controller" METHOD="get ">
                            <input type="hidden" name="command" value="job_application_info">
                            <input type="hidden" name="job_application_id" value="${applicationDto.id}">
                            <button class="single_action">
                                <img src="images/open.png" alt="open">
                            </button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>

        <hrt:page-bar numberOfPages="${number_of_pages}"
                      currentPage="${page}"
                      command="job_applications_for_vacancy" />
    </main>
</div>
</body>

</html>