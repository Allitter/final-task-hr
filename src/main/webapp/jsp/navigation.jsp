<nav class="navigation shadow-medium">
    <h2 class="nav_header"><fmt:message key="nav.navigation_label" /></h2>
    
    <div class="separator"></div>

    <ul>
        <c:if test="${user.role.name() == 'EMPLOYEE' || user.role.name() == 'JOB_SEEKER'}">
            <li>
                <a href="${pageContext.request.contextPath}/controller?command=vacancies&page=0">
                    <fmt:message key="navigation.vacancies" />
                </a>
            </li>
        </c:if>

        <c:if test="${user.role.name() == 'EMPLOYEE' || user.role.name() == 'ADMINISTRATOR'}">
            <li>
                <a href="${pageContext.request.contextPath}/controller?command=job_seekers&page=0">
                    <fmt:message key="navigation.job_seekers" />
                </a>
            </li>
        </c:if>

        <c:if test="${user.role.name() == 'ADMINISTRATOR'}">
            <li>
                <a href="${pageContext.request.contextPath}/controller?command=employees&page=0">
                    <fmt:message key="navigation.employees" />
                </a>
            </li>
        </c:if>
    </ul>
</nav>