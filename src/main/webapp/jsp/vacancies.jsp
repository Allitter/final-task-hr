<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>HR-ORG</title>
    <link rel="stylesheet" href="css/util.css">
    <link rel="stylesheet" href="css/main.css">

    <%@ include file="head_common.jsp"%>
</head>

<body>
    <%@ include file="header.jsp" %>

    <div class="nav-main">
        <%@ include file="navigation.jsp" %>

        <main class="main">
            <c:if test="${user.role.name() == 'EMPLOYEE'}">
                <div class="control_set">
                    <form action="${pageContext.request.contextPath}/controller" method="get">
                        <input type="hidden" name="command" value="vacancy_add">
                        <input class="btn" type="submit" value="<fmt:message key="button.add"/>">
                    </form>
                </div>
            </c:if>

            <div class="items">
                <c:forEach var="vacancy" items="${vacancies}">
                    <div class="item">
                        <div class="item_info">
                            <div class="item_description">
                                <h3 class="item_name">${vacancy.name}</h3>
                                <p class="item_short_desc">${vacancy.shortDescription}</p>
                            </div>
                        </div>
                        <div class="actions">
                            <form action="${pageContext.request.contextPath}/controller" METHOD="get">
                                <input type="hidden" name="command" value="vacancy_info">
                                <input type="hidden" name="vacancy_id" value="${vacancy.id}">
                                <button class="single_action">
                                    <img src="images/open.png" alt="<fmt:message key="button.open"/>">
                                </button>
                            </form>
                            <c:if test="${user.role.name() == 'EMPLOYEE' || user.role.name() == 'ADMINISTRATOR'}">
                                <form action="${pageContext.request.contextPath}/controller" METHOD="get">
                                    <input type="hidden" name="command" value="vacancy_edit">
                                    <input type="hidden" name="vacancy_id" value="${vacancy.id}">
                                    <button class="single_action">
                                        <img src="images/edit.png" alt="<fmt:message key="button.edit"/>">
                                    </button>
                                </form>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <hrt:page-bar page="${page}" command="vacancies" />
        </main>
    </div>
</body>

</html>