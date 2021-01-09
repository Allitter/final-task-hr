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
        <div class="item_header" style="display: flex; justify-content: flex-end;">
            <div class="item_header_buttons">
                <a class="btn" href="${pageContext.request.contextPath}/controller?command=job_seekers"><fmt:message key="button.back"/></a>
                <c:if test="${user.role.name() == 'ADMINISTRATOR' && !seeker.isBanned()}">
                    <form action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="user_ban">
                        <input type="hidden" name="user_id" value="${seeker.id}">
                        <button class="btn"><fmt:message key="button.ban"/></button>
                    </form>
                </c:if>

                <c:if test="${user.role.name() == 'ADMINISTRATOR' && seeker.isBanned()}">
                    <form action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="user_unban">
                        <input type="hidden" name="user_id" value="${seeker.id}">
                        <button class="btn"><fmt:message key="button.unban"/></button>
                    </form>
                </c:if>
            </div>
        </div>

        <div class="item_body" style="text-align: start; white-space: normal;">
            <!--
            <div class="user_info_top">
                <div class="avatar" style="background-image: url(/hr/images/2577247.jpg); min-width: 100px; min-height: 100px;"></div>
                <h2 style="margin-left: 20px;" class="item_name">
                    ${seeker.name} ${seeker.lastName} ${seeker.patronymic}
                </h2>
            </div>
-->
            <div class="user_info_top">
                <div class="avatar"
                     style="background-image: url(/hr/images/2577247.jpg); min-width: 100px; min-height: 100px;">
                </div>
                <h2 style="margin-left: 20px;" class="item_name">
                    ${seeker.name} ${seeker.lastName} ${seeker.patronymic}
                </h2>
            </div>
            <p><fmt:message key="label.email"/>: ${seeker.email}</p>
            <p><fmt:message key="label.phone"/>: ${seeker.phone}</p>

            <div style="margin-top: 20px;">
                <h3><fmt:message key="header.resumes"/></h3>
                <div class="resumes">
                    <c:forEach items="${resumes}" var="resume">
                        <div class="single_resume_item">
                            <a href="${pageContext.request.contextPath}/controller?command=resume_info&resume_id=${resume.id}"><span>${resume.name}</span></a>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </main>

</div>
</body>

</html>
