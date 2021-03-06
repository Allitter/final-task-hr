<nav class="navigation shadow-medium">
  <h2 class="nav_header"><fmt:message key="nav.navigation_label" /></h2>

  <div class="separator"></div>

  <ul>
    <c:if test="${user.role.name() == 'JOB_SEEKER'}">
      <li>
        <a class="reference" href="${pageContext.request.contextPath}/controller?command=job_applications_for_seeker&page=0">
          <fmt:message key="navigation.job_applications" />
        </a>
      </li>
    </c:if>

    <c:if test="${user.role.name() == 'EMPLOYEE' || user.role.name() == 'JOB_SEEKER'}">
      <li>
        <a class="reference" href="${pageContext.request.contextPath}/controller?command=vacancies&page=0">
          <fmt:message key="navigation.vacancies" />
        </a>
      </li>
    </c:if>

    <c:if test="${user.role.name() == 'ADMINISTRATOR'}">
      <li>
        <a class="reference" href="${pageContext.request.contextPath}/controller?command=job_seekers&page=0">
          <fmt:message key="navigation.job_seekers" />
        </a>
      </li>
    </c:if>

    <c:if test="${user.role.name() == 'EMPLOYEE'}">
      <li>
        <a class="reference" href="${pageContext.request.contextPath}/controller?command=job_applications&page=0">
          <fmt:message key="navigation.job_applications" />
        </a>
      </li>
    </c:if>

    <c:if test="${user.role.name() == 'ADMINISTRATOR'}">
      <li>
        <a class="reference" href="${pageContext.request.contextPath}/controller?command=employees&page=0">
          <fmt:message key="navigation.employees" />
        </a>
      </li>
    </c:if>
  </ul>
</nav>