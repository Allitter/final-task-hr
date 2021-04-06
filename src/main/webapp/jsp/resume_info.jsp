<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
  <link rel="stylesheet" href="css/util.css">
  <link rel="stylesheet" href="css/main.css">

  <%@ include file="head_common.jsp" %>
  <title>HR-ORG</title>
</head>

<body>
<%@ include file="header.jsp" %>

<div class="nav-main">
  <%@ include file="navigation.jsp" %>

  <main class="main item_more">

    <div class="control_set">
      <c:if test="${user.role.name() == 'JOB_SEEKER' && user.id == resume.idUser}">
        <form action="${pageContext.request.contextPath}/controller" method="post">
          <input type="hidden" name="command" value="confirmation_page">
          <input type="hidden" name="target_command" value="resume_remove">
          <input type="hidden" name="resume_id" value="${resume.id}">
          <button class="btn" style="margin: 5px 10px;"><fmt:message key="button.remove"/></button>
        </form>
      </c:if>

      <c:if test="${user.role.name() == 'JOB_SEEKER' && user.id == resume.idUser}">
        <form action="${pageContext.request.contextPath}/controller" method="post">
          <input type="hidden" name="command" value="resume_edit">
          <input type="hidden" name="resume_id" value="${resume.id}">
          <button class="btn" style="margin: 5px 20px;"><fmt:message key="button.edit"/></button>
        </form>
      </c:if>
    </div>

    <div class="item_body" style="text-align: start; white-space: normal;">
      <div class="user_info_top">
        <div class="avatar" style="background-image: url(${pageContext.request.contextPath}/download/${seeker.avatarPath}); min-width: 150px; min-height: 150px; margin: 20px;"></div>
        <h2 style="margin-left: 20px;" class="item_name">
          ${seeker.name} ${seeker.lastName} ${seeker.patronymic}
        </h2>
      </div>
      <br>
      <p style="white-space: pre-wrap">${resume.text}</p>
    </div>
  </main>

</div>
</body>
</html>
