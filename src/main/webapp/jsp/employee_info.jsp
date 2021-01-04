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
                <a class="btn" href="${pageContext.request.contextPath}/controller?command=employees">Back</a>
                <c:if test="${user.role.name() == 'ADMINISTRATOR' && !employee.isBanned()}">
                    <form action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="user_ban">
                        <input type="hidden" name="user_id" value="${employee.id}">
                        <button class="btn">Ban</button>
                    </form>
                </c:if>

                <c:if test="${user.role.name() == 'ADMINISTRATOR' && employee.isBanned()}">
                    <form action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="user_unban">
                        <input type="hidden" name="user_id" value="${employee.id}">
                        <button class="btn">Unban</button>
                    </form>
                </c:if>
            </div>
        </div>

        <div class="item_body" style="text-align: start; white-space: normal;">
            <div class="user_info_top">
                <div class="avatar"
                     style="background-image: url(/hr/images/test_hr.png); min-width: 100px; min-height: 100px;">
                </div>
                <h2 style="margin-left: 20px;" class="item_name">
                    ${employee.name} ${employee.lastName}${employee.patronymic}
                </h2>
            </div>
        </div>
    </main>

</div>
</body>

</html>
