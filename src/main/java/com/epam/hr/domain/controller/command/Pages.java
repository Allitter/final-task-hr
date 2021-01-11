package com.epam.hr.domain.controller.command;

public final class Pages {
    public static final String LOGIN = "jsp/login.jsp";
    public static final String REGISTRATION = "jsp/registration.jsp";
    public static final String ACCOUNT = "jsp/account.jsp";
    public static final String BAN_PAGE = "jsp/ban_page.jsp";
    public static final String VERIFICATION = "jsp/verification_page.jsp";

    public static final String VACANCIES = "jsp/vacancies.jsp";
    public static final String VACANCY_INFO = "jsp/vacancy_info.jsp";
    public static final String VACANCY_UPDATE = "jsp/hr/vacancy_update.jsp";
    public static final String VACANCY_APPLY = "jsp/seeker/vacancy_apply.jsp";

    public static final String JOB_SEEKERS = "jsp/job_seekers.jsp";
    public static final String JOB_SEEKER_INFO = "jsp/job_seeker_info.jsp";

    public static final String JOB_APPLICATIONS= "jsp/seeker/job_applications.jsp";
    public static final String JOB_APPLICATIONS_VACANCY = "jsp/hr/job_applications_for_vacancy.jsp";
    public static final String JOB_APPLICATION_INFO = "jsp/job_application_info.jsp";

    public static final String EMPLOYEES = "jsp/admin/employees.jsp";
    public static final String EMPLOYEE_INFO = "jsp/employee_info.jsp";

    public static final String RESUME_ADD = "jsp/seeker/resume_add.jsp";
    public static final String RESUME_EDIT = "jsp/seeker/resume_edit.jsp";
    public static final String RESUME_INFO = "jsp/resume_info.jsp";

    public static final String ACCESS_DENIED = "jsp/error/403error.jsp";
    public static final String SERVER_ERROR = "jsp/error/500error.jsp";
    public static final String PAGE_NOT_FOUND = "jsp/error/404error.jsp";

    private Pages () {
    }
}
