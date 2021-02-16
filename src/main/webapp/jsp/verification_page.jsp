<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
  <link rel="stylesheet" href="css/util.css">
  <link rel="stylesheet" href="css/login.css">

  <%@ include file="head_common.jsp"%>

  <title><fmt:message key="verification.title" /></title>
</head>

<body>
<form class="login_form form raised" action="${pageContext.request.contextPath}/controller" method="post">
  <h2><fmt:message key="verification.title" /></h2>
  <h3><fmt:message key="verification.tip" /></h3>

  <c:if test="${fails.contains('incorrectCode')}">
    <div class="message">
      <p class="alert-text">
        <fmt:message key="verification.incorrect_code" />
      </p>
    </div>
  </c:if>
  <input type="hidden" name="command" value="verification">
  <input type="text" name="verification_code" placeholder="<fmt:message key="verification.code"/>" id="login">

  <div class="form_actions" style="display: flex; justify-content: space-around;">
    <a class="btn" style="width: 100px; background-color: var(--danger-color); margin: 0;" href="${pageContext.request.contextPath}/controller?command=logout"><fmt:message key="header.logout_button"/></a>
    <input class="btn" type="submit" value="<fmt:message key="button.accept"/>" style="margin: 0;">
  </div>
</form>
</body>

</html>
