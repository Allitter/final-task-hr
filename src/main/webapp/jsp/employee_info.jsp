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

        <div class="control_set">
            <a class="btn"
               href="${pageContext.request.contextPath}/controller?command=employees"><fmt:message
                    key="button.back"/></a>
            <c:if test="${user.role.name() == 'ADMINISTRATOR' && !employee.isBanned()}">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command"
                           value="confirmation_page_with_message">
                    <input type="hidden" name="target_command"
                           value="user_ban">
                    <input type="hidden" name="user_id"
                           value="${employee.id}">
                    <button class="btn"><fmt:message
                            key="button.ban"/></button>
                </form>
            </c:if>

            <c:if test="${user.role.name() == 'ADMINISTRATOR' && employee.isBanned()}">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command"
                           value="confirmation_page">
                    <input type="hidden" name="target_command"
                           value="user_unban">
                    <input type="hidden" name="user_id"
                           value="${employee.id}">
                    <button class="btn"><fmt:message
                            key="button.unban"/></button>
                </form>
            </c:if>
        </div>

        <div class="item_body" style="text-align: start; white-space: normal;">
            <div class="user_info_top">
                <div class="avatar"
                     style="background-image: url(${pageContext.request.contextPath}/download/${employee.avatarPath}); min-width: 150px; min-height: 150px; margin: 20px">
                </div>
                <h2 style="margin-left: 20px;" class="user_name">
                    ${employee.name} ${employee.patronymic} ${employee.lastName}
                </h2>

                <c:if test="${employee.isBanned()}">
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

            <p><fmt:message key="label.email"/>: ${employee.email}</p>
            <p><fmt:message key="label.phone"/>: ${employee.phone}</p>
        </div>


    </main>

</div>
</body>

</html>
