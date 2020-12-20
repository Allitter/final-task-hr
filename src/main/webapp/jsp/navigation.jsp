<nav class="navigation">
    <h2 class="nav_header"><fmt:message key="nav.navigation_label" /></h2>
    
    <div class="separator"></div>

    <c:if test="${user.role.name() == 'EMPLOYEE' || user.role.name() == 'JOB_SEEKER'}">
        <a href="${pageContext.request.contextPath}/controller?command=vacancies&page=0">
            <p class="nav_item"><span><fmt:message key="navigation.vacancies" /></span></p>
        </a>
    </c:if>
    <c:if test="${user.role.name() == 'EMPLOYEE' || user.role.name() == 'ADMINISTRATOR'}">
        <a href="${pageContext.request.contextPath}/controller?command=job_seekers&page=0">
            <p class="nav_item"><span><fmt:message key="navigation.job_seekers" /></span></p>
        </a>
    </c:if>
    <c:if test="${user.role.name() == 'ADMINISTRATOR'}">
        <a href="${pageContext.request.contextPath}/controller?command=employees&page=0">
            <p class="nav_item"><span><fmt:message key="navigation.employees" /></span></p>
        </a>
    </c:if>
</nav>