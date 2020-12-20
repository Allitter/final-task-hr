<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>

</head>

<body>
    <fmt:setLocale value="${lang}"/>
    <fmt:setBundle basename="properties.text" />

    <%@ include file="header.jsp" %>

    <div class="nav-main">
        <%@ include file="navigation.jsp" %>

        <main class="main">
            <div class="items">
                <c:forEach var="vacncy" items="${vacancies}">
                    <div class="item">
                        <div class="item_description">
                            <h3 class="item_name">${vacncy.name}</h3>
                            <p class="item_short_desc">${vacncy.shortDescription}</p>
                        </div>
                        <div class="actions">
                            <a href="">
                                <div class="single_action">
                                    <img src="assets/images/open.png" alt="open">
                                </div>
                            </a>
                            <a href="">
                                <div class="single_action">
                                    <img src="assets/images/edit.png" alt="edit">
                                </div>
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="page_bar">
                <a href="${pageContext.request.contextPath}/controller?command=vacancies&page=${page - 1}"><fmt:message key="main.previous_page" /></a>
                <a href="${pageContext.request.contextPath}/controller?command=vacancies&page=${page + 1}"><fmt:message key="main.next_page" /></a>
            </div>
        </main>
    </div>
</body>

</html>