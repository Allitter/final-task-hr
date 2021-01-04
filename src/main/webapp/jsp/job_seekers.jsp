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

        <main class="main">
            <div class="items">
                <c:forEach var="user" items="${users}">
                    <div class="item">
                        <div class="item_info">
                            <div class="avatar" style="background-image: url(images/2577247.jpg);">
                            </div>
                            <div class="item_description">
                                <h3 class="item_name" style="display: inline;">${user.name}</h3>
                                <c:if test="${user.isBanned()}">
                                    <span style="color: var(--alert-text-color)">banned</span>
                                </c:if>
                                <br>
                                <p class="item_short_desc">${user.login}</p>
                            </div>
                        </div>
                        <div class="actions">
                            <form action="${pageContext.request.contextPath}/controller" METHOD="post">
                                <input type="hidden" name="command" value="job_seeker_info">
                                <input type="hidden" name="user_id" value="${user.id}">
                                <button class="single_action">
                                    <img src="images/open.png" alt="open">
                                </button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="page_bar">
                <a href="${pageContext.request.contextPath}/controller?command=job_seekers&page=${page - 1}"><fmt:message key="main.previous_page" /></a>
                <a href="${pageContext.request.contextPath}/controller?command=job_seekers&page=${page + 1}"><fmt:message key="main.next_page" /></a>
            </div>
        </main>
    </div>
</body>

</html>