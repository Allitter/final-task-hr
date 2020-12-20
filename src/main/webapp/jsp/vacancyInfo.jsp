<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt" %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=0.86, maximum-scale=3.0, minimum-scale=0.86">
    <title>HR-ORG</title>
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="shortcut icon" href="favicon.ico" type="image/png">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Lora&family=Playfair+Display&display=swap" rel="stylesheet">
</head>

<body>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="properties.text" />

<%@ include file="header.jsp" %>

<div class="nav-main">
    <%@ include file="navigation.jsp" %>


    <main class="main item_more">
        <div class="item_header">
            <h2 class="item_name">
                ${vacancy.name}
            </h2>

            <div class="item_header_buttons">
                <a class="btn" href="${pageContext.request.contextPath}/controller?command=vacancies">Back</a>
                <c:if test="${user.role.name() == 'EMPLOYEE'}">
                    <form action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="vacancy_edit">
                        <input type="hidden" name="vacancy_id" value="${vacancy.id}">
                        <button class="btn">Edit</button>
                    </form>

                </c:if>
                <c:if test="${user.role.name() == 'JOB_SEEKER'}">
                    <a class="btn" href="">Apply</a>
                </c:if>
            </div>
        </div>

        <div class="item_body">
            <span style="text-align: start;">${vacancy.description}</span>
        </div>
    </main>

</div>
</body>

</html>

