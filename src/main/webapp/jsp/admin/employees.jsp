<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" href="css/util.css">
    <link rel="stylesheet" href="css/main.css">

    <%@ include file="../head_common.jsp" %>
    <title>HR-ORG</title>
</head>

<body>
<%@ include file="../header.jsp" %>

<div class="nav-main">
    <%@ include file="../navigation.jsp" %>

    <main class="main">
        <c:choose>
            <c:when test="${users.size() > 0}">
                <div class="items">
                    <c:forEach var="employee" items="${users}">
                        <div class="item">
                            <div class="item_info">
                                <div class="avatar"
                                     style="background-image: url(${pageContext.request.contextPath}/download/${employee.avatarPath});">
                                </div>
                                <div class="item_description">
                                    <a href="${pageContext.request.contextPath}/controller?command=employee_info&user_id=${employee.id}">
                                        <h3 class="item_name"
                                            style="display: inline;">${employee.name}</h3>
                                    </a>
                                    <c:if test="${employee.isBanned()}">
                                        <span style="color: var(--danger-color)"><fmt:message
                                                key="messages.banned"/></span>
                                    </c:if>
                                    <br>
                                    <p class="item_short_desc">${employee.login}</p>
                                </div>
                            </div>

                            <div class="actions">
                                <form action="${pageContext.request.contextPath}/controller"
                                      METHOD="get">
                                    <input type="hidden" name="command"
                                           value="employee_info">
                                    <input type="hidden" name="user_id"
                                           value="${employee.id}">
                                    <button class="single_action">
                                        <img src="images/open.png"
                                             alt="<fmt:message key="button.open"/>">
                                    </button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <h3>No employees found</h3>
            </c:otherwise>
        </c:choose>

        <hrt:page-bar page="${page}" command="employees"/>
    </main>
</div>
</body>

</html>