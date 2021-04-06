<c:if test="${fails.contains('login') || fails.contains('password') || fails.contains('incorrectLoginOrPassword')}">
  <span class="alert-text"><fmt:message key="login.bad_credentials"/></span>
</c:if>
<c:if test='${fails.contains("loginNotUnique")}'>
  <p class="alert-text"><fmt:message key="registration.login_already_used"/></p>
</c:if>
<c:if test='${fails.contains("login")}'>
  <p class="alert-text"><fmt:message key="messages.login"/> <fmt:message key="messages.incorrect"/></p>
</c:if>
<c:if test='${fails.contains("name")}'>
  <p class="alert-text"><fmt:message key="messages.name"/> <fmt:message key="messages.incorrect"/></p>
</c:if>
<c:if test='${fails.contains("last")}'>
  <p class="alert-text"><fmt:message key="messages.lastName"/> <fmt:message key="messages.incorrect"/></p>
</c:if>
<c:if test='${fails.contains("patronymic")}'>
  <p class="alert-text"><fmt:message key="messages.patronymic"/> <fmt:message key="messages.incorrect"/></p>
</c:if>
<c:if test='${fails.contains("email")}'>
  <p class="alert-text"><fmt:message key="messages.email"/> <fmt:message key="messages.incorrect"/></p>
</c:if>
<c:if test='${fails.contains("phone")}'>
  <p class="alert-text"><fmt:message key="messages.phone"/> <fmt:message key="messages.incorrect"/></p>
</c:if>
<c:if test='${fails.contains("birthDate")}'>
  <p class="alert-text"><fmt:message key="messages.birthDate"/> <fmt:message key="messages.incorrect"/></p>
</c:if>
<c:if test='${fails.contains("name")}'>
  <p class="alert-text"><fmt:message key="messages.name"/> <fmt:message key="messages.incorrect"/></p>
</c:if>
<c:if test='${fails.contains("text")}'>
  <p class="alert-text"><fmt:message key="messages.text"/> <fmt:message key="messages.incorrect"/></p>
</c:if>
<c:if test='${fails.contains("noMoreResumes")}'>
  <p class="alert-text"><fmt:message key="messages.resume_limit_exceeded"/></p>
</c:if>
<c:if test='${fails.contains("shortDescription")}'>
  <p class="alert-text"><fmt:message key="messages.shortDescription"/> <fmt:message key="messages.incorrect"/></p>
</c:if>
<c:if test='${fails.contains("description")}'>
  <p class="alert-text"><fmt:message key="messages.description"/> <fmt:message key="messages.incorrect"/>
</c:if>