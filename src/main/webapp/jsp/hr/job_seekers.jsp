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
            <div class="items">
                <c:forEach var="user" items="${users}">
                    <div class="item">
                        <div class="item_info">
                            <div class="avatar" style="background-image: url(images/2577247.jpg);">
                            </div>
                            <div class="item_description">
                                <h3 class="item_name">${user.name}</h3>
                                <p class="item_short_desc">${user.login}</p>
                            </div>
                        </div>
                        <div class="actions">
                            <a href="">
                                <div class="single_action">
                                    <img src="images/open.png" alt="open">
                                </div>
                            </a>
                            <a href="">
                                <div class="single_action">
                                    <img src="images/edit.png" alt="edit">
                                </div>
                            </a>
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