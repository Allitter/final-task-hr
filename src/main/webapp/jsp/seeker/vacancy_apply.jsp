<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" href="css/util.css">
    <link rel="stylesheet" href="css/main.css">
    <link rel="stylesheet" href="css/login.css">

    <%@ include file="../head_common.jsp"%>
    <title>HR-ORG</title>
</head>

<body>
<%@ include file="../header.jsp" %>

<div class="nav-main">
    <%@ include file="../navigation.jsp" %>

    <main class="main" style="width: 100%; text-align: left;">
            <div class="account_resumes">
                <c:choose>
                    <c:when test="${resumes.size() > 0}">
                        <h3><fmt:message key="header.choose_resume" /></h3>
                        <div class="resumes">
                            <c:forEach items="${resumes}" var="resume">
                                <div style="display: flex;align-items: center;flex-wrap: wrap;justify-content: left;">
                                    <a class="single_resume_item" style="text-align: center;" href="${pageContext.request.contextPath}/controller?command=resume_info&resume_id=${resume.id}"><span>${resume.name}</span></a>
                                    <form action="${pageContext.request.contextPath}/controller" method="get">
                                        <input type="hidden" name="command" value="vacancy_apply_accept">
                                        <input type="hidden" name="resume_id" value="${resume.id}">
                                        <input type="hidden" name="vacancy_id" value="${vacancy.id}">

                                        <button class="single_action" style="margin-left: 10px;padding: 4px;border-radius: 50%;border: 1px solid var(--dark-bg-color);">
                                            <img src="images/check.png" alt="<fmt:message key="button.edit" />" style="height: 23px;margin-left: 0px">
                                        </button>
                                    </form>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <h3><fmt:message key="messages.no_resumes" /></h3>
                        <form action="${pageContext.request.contextPath}/controller" method="post" style="width: max-content;">
                            <input type="hidden" name="command" value="resume_add">
                            <input class="btn shadow-none shadow-hov-small" type="submit" value="<fmt:message key="button.add" />" style="background-color: var(--success-color)">
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
    </main>
</div>
</body>

</html>