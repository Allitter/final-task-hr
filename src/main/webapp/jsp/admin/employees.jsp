<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>HR-ORG</title>
    <link rel="stylesheet" href="css/util.css">
    <link rel="stylesheet" href="css/main.css">

    <%@ include file="../head_common.jsp" %>
</head>

<body>
    <%@ include file="../header.jsp" %>

    <div class="nav-main">
        <%@ include file="../navigation.jsp" %>

        <main class="main">
            <c:choose>
                <c:when test="${users.size() > 0}">
                    <div class="items">
                        <c:forEach var="user" items="${users}">
                            <div class="item">
                                <div class="item_info">
                                    <div class="avatar" style="background-image: url(images/test_hr.png);">
                                    </div>
                                    <div class="item_description">
                                        <h3 class="item_name" style="display: inline;">${user.name}</h3>
                                        <c:if test="${user.isBanned()}">
                                            <span style="color: var(--alert-text-color)"><fmt:message key="messages.banned"/></span>
                                        </c:if>
                                        <br>
                                        <p class="item_short_desc">${user.login}</p>
                                    </div>
                                </div>

                                <div class="actions">
                                    <form action="${pageContext.request.contextPath}/controller" METHOD="get">
                                        <input type="hidden" name="command" value="employee_info">
                                        <input type="hidden" name="user_id" value="${user.id}">
                                        <button class="single_action">
                                            <img src="images/open.png" alt="<fmt:message key="button.open"/>">
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

            <hrt:page-bar page="${page}" command="employees" />
        </main>
    </div>
</body>

</html>