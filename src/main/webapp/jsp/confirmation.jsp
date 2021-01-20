<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" href="css/util.css">
    <link rel="stylesheet" href="css/login.css">

    <%@ include file="head_common.jsp" %>

    <title><fmt:message key="title.confirm"/></title>
</head>

<body>
<form class="login_form form shadow-medium"
      action="${pageContext.request.contextPath}/controller"
      method="post">
    <h2>
        <fmt:message key="title.confirm"/>
    </h2>
    <input type="hidden" name="command" value="confirmation">
    <c:forEach var="parameter" items="${parameters}">
        <input type="hidden" name="${parameter.key}" value="${parameter.value}">
    </c:forEach>
    <input type="hidden" name="previous_page" value="${previous_page}">

    <input type="password" name="password"
           placeholder="<fmt:message key="messages.password" />" id="password">

    <c:if test="${show_message}">
        <textarea style="height: 200px; resize: none;" name="message" required
                  minlength="3" maxlength="240"
                  placeholder="<fmt:message key="messages.reason_tip"/>"></textarea>
    </c:if>

    <div class="form_actions" style="justify-content: center;">
        <a class="btn"
           style="background-color: var(--alert-text-color); margin: 5px 10px;"
           href="${previous_page}"><fmt:message key="button.cancel"/></a>
        <input class="btn shadow-none shadow-hov-small" type="submit"
               value="<fmt:message key="button.confirm" />"
               style="margin: 5px 10px;">
    </div>
</form>
</body>

</html>