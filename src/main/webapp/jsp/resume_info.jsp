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
                <c:if test="${user.role.name() == 'JOB_SEEKER' && user.id == resume.idUser}">
                    <form action="${pageContext.request.contextPath}/controller" method="post">
                        <input type="hidden" name="command" value="resume_remove">
                        <input type="hidden" name="resume_id" value="${resume.id}">
                        <button class="btn" style="margin: 5px 10px;"><fmt:message key="button.remove"/></button>
                    </form>
                </c:if>

                <c:if test="${user.role.name() == 'JOB_SEEKER' && user.id == resume.idUser}">
                    <form action="${pageContext.request.contextPath}/controller" method="post">
                        <input type="hidden" name="command" value="resume_edit">
                        <input type="hidden" name="resume_id" value="${resume.id}">
                        <button class="btn" style="margin: 5px 20px;"><fmt:message key="button.edit"/></button>
                    </form>
                </c:if>
            </div>
        </div>

        <div class="item_body" style="text-align: start; white-space: normal;">
            <div class="user_info_top">
                <div class="avatar" style="background-image: url(/hr/images/2577247.jpg); min-width: 100px; min-height: 100px;"></div>
                <h2 style="margin-left: 20px;" class="item_name">
                    ${seeker.name} ${seeker.lastName} ${seeker.patronymic}
                </h2>
            </div>
            <br>
            <p style="white-space: pre-wrap">${resume.text}</p>
        </div>
    </main>

</div>
</body>
</html>
