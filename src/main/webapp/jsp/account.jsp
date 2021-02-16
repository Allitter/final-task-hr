<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>

  <%@ include file="head_common.jsp" %>
  <title><fmt:message key="title.account"/></title>
  <link rel="stylesheet" href="css/util.css">
  <link rel="stylesheet" href="css/main.css">
  <link rel="stylesheet" href="css/login.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>

<body>
<%@ include file="header.jsp" %>

<div class="nav-main">
  <%@ include file="navigation.jsp" %>

  <main class="main" style="width: 100%; text-align: left;">
    <div style="display: flex; flex-direction: column; justify-content: space-between; align-items: center; border-radius: 2rem; margin: 10px 0; width: 100%; box-sizing: border-box;">
      <div class="avatar"
           style="background-image: url(${pageContext.request.contextPath}/download/${user.avatarPath});
               min-width: 150px; min-height: 150px; margin: 20px">
      </div>

      <c:choose>
        <c:when test="${can_change_avatar}">
          <form method="post" action="${pageContext.request.contextPath}/controller?command=upload_file"
                enctype="multipart/form-data"
                class="form avatar_upload_form"
                style="display: flex; justify-content: space-between; align-items: center; flex-direction: column; width: -moz-fit-content; width: fit-content;">
            <label id="select_file_label" class="btn" style="margin: 10px 0; text-align: center; background-color: var(--danger-color);
                        width: -moz-fit-content; width: fit-content;">
              <span style="color: var(--font-light-color);"><fmt:message key="messages.select_file"/></span>
              <input type="file" name="file" style="display: none" accept=".jpg, .jpeg, .png" content="">
            </label>
            <input style="display: none; width: -moz-fit-content; width: fit-content;" id="upload_submit" class="btn" type="submit" value="<fmt:message key="button.upload"/>">
          </form>
        </c:when>
        <c:otherwise>
          <p style="margin: 10px;">
            <fmt:message key="messages.cant_change_avatar"/>
          </p>
        </c:otherwise>
      </c:choose>

      <script src="js/upload_picture.js"></script>
    </div>
    <form class="form" action="${pageContext.request.contextPath}/controller" method="post" style="border-radius: 2rem; margin: 10px 0; width: 100%; box-sizing: border-box;">
      <input type="hidden" name="command" value="account_update">
      <h2><fmt:message key="title.account"/></h2>
      <div class="message">
        <%@ include file="messages.jsp" %>
      </div>

      <input type="text" required maxlength="15"
             pattern="[_0-9A-Za-z]{3,}"
             title="<fmt:message key="registration.login.pattern_title" />"
             name="login"
             placeholder="<fmt:message key="registration.login.placeholder" />"
             value="${user.login}">

      <input type="text" pattern="(^[А-ЯЁ][а-яё]+$)|(^[A-Z][a-z]+$)"
             title="<fmt:message key="registration.name.pattern_title" />"
             required maxlength="15" name="name"
             placeholder="<fmt:message key="registration.name.placeholder" />"
             value="${user.name}">
      <input type="text" pattern="(^[А-ЯЁ][а-яё]+$)|(^[A-Z][a-z]+$)"
             title="<fmt:message key="registration.name.pattern_title" />"
             required maxlength="15" name="last_name"
             placeholder="<fmt:message key="registration.last_name.placeholder" />"
             value="${user.lastName}">
      <input type="text" pattern="(^[А-ЯЁ][а-яё]+$)|(^[A-Z][a-z]+$)"
             title="<fmt:message key="registration.name.pattern_title" />"
             maxlength="15" name="patronymic"
             placeholder="<fmt:message key="registration.patronymic.placeholder" />"
             value="${user.patronymic}">

      <input type="email"
             pattern="^[A-Za-z_0-9.-]+@([A-Za-z_0-9-]+\.)+[A-Za-z_0-9-]{2,4}$"
             required
             title="<fmt:message key="registration.email.pattern_title" />"
             name="email" maxlength="320"
             placeholder="<fmt:message key="registration.email.placeholder" />"
             value="${user.email}">

      <input type="tel" required pattern="[+][0-9]{7,14}"
             title="<fmt:message key="registration.phone_pattern_title" />"
             name="phone" maxlength="15"
             placeholder="<fmt:message key="registration.phone.placeholder" />"
             value="${user.phone}">

      <label for="birth_date"><fmt:message
          key="registration.birth_date.label"/></label>
      <input type="date" required name="birth_date" id="birth_date"
             value="${user.birthDate}" min="1971-01-01" max="2007-12-31">

      <div class="form_actions" style="justify-content: center;">
        <input class="btn shadow-none shadow-hov-small" type="submit"
               value="<fmt:message key="button.save" />"
               style="margin: 0; background-color: var(--success-color)">
      </div>
    </form>

    <c:if test="${user.role.name() == 'JOB_SEEKER'}">
      <div class="account_resumes">
        <h3><fmt:message key="header.resumes"/></h3>
        <div class="resumes">
          <c:forEach items="${resumes}" var="resume">
            <div style="display: flex;align-items: center;flex-wrap: wrap;justify-content: left;">
              <a class="single_resume_item"
                 style="text-align: center;"
                 href="${pageContext.request.contextPath}/controller?command=resume_info&resume_id=${resume.id}"><span>${resume.name}</span></a>
              <form action="${pageContext.request.contextPath}/controller" method="get">
                <input type="hidden" name="command" value="confirmation_page">
                <input type="hidden" name="target_command" value="resume_remove">
                <input type="hidden" name="resume_id" value="${resume.id}">

                <button class="single_action btn_icon" style="margin-left: 10px;padding: 4px;border-radius: 50%;border: 1px solid var(--dark-bg-color);">
                  <img src="images/cross.png" alt="<fmt:message key="button.remove" />">
                </button>
              </form>
            </div>
          </c:forEach>
        </div>

        <form action="${pageContext.request.contextPath}/controller" method="get" style="width: max-content;">
          <input type="hidden" name="command" value="resume_add">
          <input class="btn" type="submit" value="<fmt:message key="button.add" />" style="background-color: var(--success-color)">
        </form>
      </div>
    </c:if>
  </main>
</div>
</body>

</html>