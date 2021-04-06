
<head>
  <%@include file="head_common.jsp"%>
</head>

<header class="header">
  <div class="header_top">
    <div class="user_info">
      <p>${user.name}</p>
    </div>

    <div class="header_buttons">
      <form action="${pageContext.request.contextPath}/controller" method="GET">
        <input type="hidden" name="command" value="account">
        <input class="btn" type="submit" value="<fmt:message key="header.account_button"/>" style="width: 100px">
      </form>

      <div class= "dropdown">
        <button class="btn dropbtn" style="width: 100px">
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
                <input class="btn" type="submit" name="lang" value="en" style="width: 100px">
              </form>
            </li>
            <li>
              <form action="${pageContext.request.contextPath}/controller" method="POST">
                <input type="hidden" name="command" value="change_language">
                <input class="btn" type="submit" name="lang" value="ru" style="width: 100px">
              </form>
            </li>
            <li>
              <form action="${pageContext.request.contextPath}/controller" method="POST">
                <input type="hidden" name="command" value="change_language">
                <input class="btn" type="submit" name="lang" value="be" style="width: 100px">
              </form>
            </li>
          </ul>
        </div>
      </div>

      <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="logout">
        <input class="btn" type="submit" value="<fmt:message key="header.logout_button"/>" style="width: 100px; background-color: var(--danger-color)">
      </form>
    </div>
  </div>

  <div class="logo">
    <h1>HR-ORG</h1>
  </div>
</header>