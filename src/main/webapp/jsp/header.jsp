<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt" %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=0.86, maximum-scale=3.0, minimum-scale=0.86">
    <title>HR-ORG</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="shortcut icon" href="favicon.ico" type="image/png">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Lora&family=Playfair+Display&display=swap" rel="stylesheet">
</head>
<header class="header">
        <div class="header_top">
            <div class="user_info">
                <p>${user.name}</p>
            </div>

            <div class="header_buttons">
                <form action="${pageContext.request.contextPath}/controller" method="GET">
                    <input type="hidden" name="command" value="logout">
                    <input class="btn" type="submit" value="<fmt:message key="header.logout_button"/>">
                </form>

                <div class= "dropdown">
                    <button class="btn dropbtn">
                        <c:choose>
                            <c:when test="${lang != null}">
                                ${lang}
                            </c:when>
                            <c:otherwise>
                                en
                            </c:otherwise>
                        </c:choose>
                    </button>
                    <div class="dropdown-content">
                        <ul style="list-style: none;">
                            <li>
                                <form action="${pageContext.request.contextPath}/controller" method="POST">
                                    <input type="hidden" name="command" value="change_language">
                                    <input class="btn" type="submit" name="lang" value="en">
                                </form>
                            </li>
                            <li>
                                <form action="${pageContext.request.contextPath}/controller" method="POST">
                                    <input type="hidden" name="command" value="change_language">
                                    <input class="btn" type="submit" name="lang" value="ru">
                                </form>
                            </li>
                            <li>
                                <form action="${pageContext.request.contextPath}/controller" method="POST">
                                    <input type="hidden" name="command" value="change_language">
                                    <input class="btn" type="submit" name="lang" value="be">
                                </form>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <div class="logo">
            <h1>HR-ORG</h1>
        </div>
</header>