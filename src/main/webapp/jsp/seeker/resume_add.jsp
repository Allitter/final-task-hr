<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
  <link rel="stylesheet" href="css/util.css">
  <link rel="stylesheet" href="css/main.css">

  <%@ include file="../head_common.jsp" %>
  <title>HR-ORG</title>
</head>

<body>
<%@ include file="../header.jsp" %>

<div class="nav-main">
  <%@ include file="../navigation.jsp" %>


  <main class="main item_edit">
    <div class="message">
      <%@ include file="../messages.jsp" %>
    </div>

    <form action="${pageContext.request.contextPath}/controller" method="POST">
      <input type="hidden" name="command" value="resume_add_accept">

      <label for="name" style="text-align: left; margin: 10px 0;"><fmt:message key="label.name" /></label>
      <input name="resume_name" pattern="[A-Za-zА-ЯЁа-яё_0-9 ]{3,}" maxlength="15" id="name" class="item_name" type="text" value="${resume.name}">
      <br>
      <label for="item_long_desc" style="text-align: left; margin: 10px 0;"><fmt:message key="label.resume" /></label>
      <textarea class="item_long_desc" maxlength="2048" id="item_long_desc" name="item_long_desc" style="width: auto; height: 600px; resize: none;">${resume.text}</textarea>
      <br>

      <div class="item_edit_actions">
        <a class="btn" style="box-sizing: border-box;" href="${pageContext.request.contextPath}/controller?command=account"><fmt:message key="button.cancel" /></a>
        <input class="btn" style="box-sizing: border-box; width: 90px; margin-left: 20px;" type="submit" value="<fmt:message key="button.accept" />">
      </div>
    </form>
  </main>
</div>
</body>

</html>
