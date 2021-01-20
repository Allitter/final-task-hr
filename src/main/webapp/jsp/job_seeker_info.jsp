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
        <div class="item_header"
             style="display: flex; justify-content: flex-end;">
            <div class="item_header_buttons">
                <a class="btn"
                   href="${pageContext.request.contextPath}/controller?command=job_seekers"><fmt:message
                        key="button.back"/></a>
                <c:if test="${user.role.name() == 'ADMINISTRATOR' && !seeker.isBanned()}">
                    <form action="${pageContext.request.contextPath}/controller"
                          method="post">
                        <input type="hidden" name="command"
                               value="confirmation_page_with_message">
                        <input type="hidden" name="target_command"
                               value="user_ban">
                        <input type="hidden" name="user_id"
                               value="${seeker.id}">
                        <button class="btn"><fmt:message
                                key="button.ban"/></button>
                    </form>
                </c:if>

                <c:if test="${user.role.name() == 'ADMINISTRATOR' && seeker.isBanned()}">
                    <form action="${pageContext.request.contextPath}/controller"
                          method="post">
                        <input type="hidden" name="command"
                               value="confirmation_page">
                        <input type="hidden" name="target_command"
                               value="user_unban">
                        <input type="hidden" name="user_id"
                               value="${seeker.id}">
                        <button class="btn"><fmt:message
                                key="button.unban"/></button>
                    </form>
                </c:if>
            </div>
        </div>

        <div class="item_body" style="text-align: start; white-space: normal;">
            <div class="user_info_top">
                <div class="avatar"
                     style="background-image: url(/hr/images/2577247.jpg); min-width: 150px; min-height: 150px;">
                </div>
                <h2 style="margin-left: 20px;" class="item_name">
                    ${seeker.name} ${seeker.lastName} ${seeker.patronymic}
                </h2>

                <c:if test="${seeker.isBanned()}">
                    <div class="ban_info">
                        <span style="color: var(--alert-text-color)"><fmt:message
                                key="messages.banned"/></span>
                        <c:if test="${user.role.name() == 'ADMINISTRATOR'}">
                            <span><fmt:message
                                    key="messages.reason"/>: ${ban.reason}</span>
                        </c:if>
                    </div>
                </c:if>

            </div>

            <c:if test="${user.role.name() == 'ADMINISTRATOR' && seeker.isBanned()}">
                <p><fmt:message key="messages.reason"/>: ${ban.reason}</p>
            </c:if>

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
