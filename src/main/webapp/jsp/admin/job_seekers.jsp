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

  <main class="main">
    <c:choose>
      <c:when test="${users.size() > 0}">
        <div class="items">
          <c:forEach var="seeker" items="${users}">
            <div class="item">
              <div class="item_info">
                <div class="avatar" style="background-image: url(${pageContext.request.contextPath}/download/${seeker.avatarPath});"></div>
                <div class="item_description">
                  <a href="${pageContext.request.contextPath}/controller?command=job_seeker_info&user_id=${seeker.id}">
                    <h3 class="item_name" style="display: inline;">${seeker.name}</h3>
                  </a>
                  <c:if test="${seeker.isBanned()}">
                    <span style="color: var(--danger-color)"><fmt:message key="messages.banned"/></span>
                  </c:if>
                  <br>
                  <p class="item_short_desc">${seeker.login}</p>
                </div>
              </div>
              <div class="actions">
                <a class="single_action reference" href="${pageContext.request.contextPath}/controller?command=job_seeker_info&user_id=${seeker.id}"><fmt:message key="button.open"/></a>
              </div>
            </div>
          </c:forEach>
        </div>
      </c:when>
      <c:otherwise>
        <h3><fmt:message key="messages.no_job_seekers_found"/></h3>
      </c:otherwise>
    </c:choose>

    <hrt:page-bar page="${page}" command="job_seekers"/>
  </main>
</div>
</body>

</html>