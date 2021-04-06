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

  <main class="main">
    <h2><fmt:message key="navigation.job_applications"/></h2>
    <c:choose>
      <c:when test="${job_applications.size() > 0}">
        <div class="items">

          <c:forEach var="application" items="${job_applications}">
            <div class="item">
              <div class="item_info">
                <div class="item_description">
                  <h3>${application.vacancyName}</h3>
                  <p>
                    <fmt:message key="label.status"/>:
                    <span style="margin-left: 10px; color: var(--success-color)"><fmt:message key="enum.job_application_state.${application.state.name()}"/></span>
                  </p>
                  <p>
                    <fmt:message key="label.creation_date"/>:
                    <span style="margin-left: 10px; color: var(--main-bg-color)"><fmt:formatDate value="${application.date}" pattern="${dateFormat}"/></span>
                  </p>

                  <c:if test="${user.role.name() == 'EMPLOYEE'}">
                    <p>
                      <fmt:message key="label.job_seeker"/>:
                      <span>${application.userName}</span>
                      <span> ${application.userPatronymic}</span>
                      <span> ${application.userLastName}</span>
                    </p>
                  </c:if>
                </div>
              </div>
              <div class="actions">
                <a class="single_action reference" href="${pageContext.request.contextPath}/controller?command=job_application_info&job_application_id=${application.id}"><fmt:message key="button.open" /></a>
                <a class="single_action reference" href="${pageContext.request.contextPath}/controller?command=vacancy_info&vacancy_id=${application.idVacancy}"><fmt:message key="button.see_vacancy" /></a>
              </div>
            </div>

          </c:forEach>
        </div>
      </c:when>
      <c:otherwise>
        <h3 style="margin-top: 20px">
          <fmt:message key="messages.no_job_applications"/>
          <br>
          <a class="reference" href="${pageContext.request.contextPath}/controller?command=vacancies"><fmt:message key="messages.browse_vacancies"/></a>
        </h3>
      </c:otherwise>
    </c:choose>

    <hrt:page-bar page="${page}" command="job_applications_for_seeker"/>
  </main>
</div>
</body>

</html>