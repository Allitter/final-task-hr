<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
  <link rel="stylesheet" href="css/util.css">
  <link rel="stylesheet" href="css/login.css">

  <%@ include file="head_common.jsp" %>

  <title><fmt:message key="registration.title"/></title>
</head>

<body>
<form class="form registration_form raised"
      action="${pageContext.request.contextPath}/controller" method="post">
  <input type="hidden" name="command" value="registration">

  <h2><fmt:message key="registration.title"/></h2>

  <div class="message">
    <%@ include file="messages.jsp" %>
  </div>

  <input type="text" required maxlength="15" pattern="[_0-9A-Za-z]{3,}"
         title="<fmt:message key="registration.login.pattern_title" />"
         name="login"
         placeholder="<fmt:message key="registration.login.placeholder" />"
         value="${requestScope.user.login}">
  <c:if test="${show_authentication_error}">
    <div class="message">
      <p class="alert-text"><fmt:message
          key="registration.login_already_used"/></p>
    </div>
  </c:if>

  <input type="password" pattern="[_*$()%0-9A-Za-z]{8,}" maxlength="32"
         title="<fmt:message key="registration.password.pattern_title" />"
         required
         name="password" id="password"
         placeholder="<fmt:message key="registration.password.placeholder" />"
         value="${requestScope.user.password}">
  <input type="password" pattern="[_*$()%0-9A-Za-z]{8,}" maxlength="32"
         title="<fmt:message key="registration.password.pattern_title" />"
         required
         name="password_repeat" id="password_repeat"
         placeholder="<fmt:message key="registration.password_repeat.placeholder" />"
         value="${requestScope.user.password}">
  <div id="passwords_dont_match_message" style="display: none;">
    <p class="alert-text"><fmt:message key="registration.passwords_dont_match"/></p>
  </div>

  <input type="text" pattern="(^[А-ЯЁ][а-яё]+$)|(^[A-Z][a-z]+$)"
         title="<fmt:message key="registration.name.pattern_title" />"
         required maxlength="15" name="name"
         placeholder="<fmt:message key="registration.name.placeholder" />"
         value="${requestScope.user.name}">
  <input type="text" pattern="(^[А-ЯЁ][а-яё]+$)|(^[A-Z][a-z]+$)"
         title="<fmt:message key="registration.name.pattern_title" />"
         required maxlength="15" name="last_name"
         placeholder="<fmt:message key="registration.last_name.placeholder" />"
         value="${requestScope.user.lastName}">
  <input type="text" pattern="(^[А-ЯЁ][а-яё]+$)|(^[A-Z][a-z]+$)"
         title="<fmt:message key="registration.name.pattern_title" />"
         maxlength="15" name="patronymic"
         placeholder="<fmt:message key="registration.patronymic.placeholder" />"
         value="${requestScope.user.patronymic}">

  <input type="email"
         pattern="^[A-Za-z_0-9.-]+@([A-Za-z_0-9-]+\.)+[A-Za-z_0-9-]{2,4}$"
         required
         title="<fmt:message key="registration.email.pattern_title" />"
         name="email" maxlength="320"
         placeholder="<fmt:message key="registration.email.placeholder" />"
         value="${requestScope.user.email}">

  <input type="tel" required pattern="[+][0-9]{7,14}"
         title="<fmt:message key="registration.phone_pattern_title" />"
         name="phone" maxlength="15"
         placeholder="<fmt:message key="registration.phone.placeholder" />"
         value="${requestScope.user.phone}">

  <label for="birth_date"><fmt:message key="registration.birth_date.label"/></label>
  <input type="date" required name="birth_date" id="birth_date"
         value="${requestScope.user.birthDate}" min="1971-01-01" max="2007-12-31">

  <div class="form_actions">
    <input class="btn shadow-none shadow-hov-small" type="submit"
           value="<fmt:message key="registration.submit_button.caption" />">
    <a class="reference" href="${pageContext.request.contextPath}/controller"><fmt:message
        key="registration.login_suggestion"/></a>
  </div>
</form>

<script src="js/registration_validation.js"></script>
</body>

</html>






