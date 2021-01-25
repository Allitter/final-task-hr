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
            <a class="btn"
               href="${pageContext.request.getHeader("referer")}"><fmt:message
                    key="button.back"/></a>
            <c:if test="${job_application.state.name() == 'RECENTLY_CREATED'
                        && user.role.name() == 'EMPLOYEE'}">
                <form action="${pageContext.request.contextPath}/controller"
                      method="post">
                    <input type="hidden" name="command"
                           value="assign_preliminary_interview">
                    <input type="hidden" name="job_application_id"
                           value="${job_application.id}">
                    <button class="btn"><fmt:message
                            key="button.assign_preliminary_interview"/></button>
                </form>
            </c:if>

            <c:if test="${job_application.state.name() == 'PRELIMINARY_INTERVIEW'
                        && user.role.name() == 'EMPLOYEE'}">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command"
                           value="assign_technical_interview" method="post">
                    <input type="hidden" name="job_application_id"
                           value="${job_application.id}">
                    <button class="btn"><fmt:message
                            key="button.assign_technical_interview"/></button>
                </form>
            </c:if>

            <c:if test="${job_application.state.name() == 'TECHNICAL_INTERVIEW'
                        && user.role.name() == 'EMPLOYEE'}">
                <form action="${pageContext.request.contextPath}/controller">
                    <input type="hidden" name="command"
                           value="assign_for_job" method="post">
                    <input type="hidden" name="job_application_id"
                           value="${job_application.id}">
                    <button class="btn"><fmt:message
                            key="button.assign_for_job"/></button>
                </form>
            </c:if>

            <c:if test="${job_application.state.name() != 'APPLIED'
                        && job_application.state.name() != 'BLOCKED'
                        && user.role.name() == 'EMPLOYEE'}">
                <form action="${pageContext.request.contextPath}/controller"
                      method="post">
                    <input type="hidden" name="command"
                           value="confirmation_page">
                    <input type="hidden" name="target_command"
                           value="block_job_application">
                    <input type="hidden" name="job_application_id"
                           value="${job_application.id}">
                    <button class="btn alert"><fmt:message
                            key="button.block"/></button>
                </form>
            </c:if>
        </div>

        <div class="item_body" style="text-align: start; white-space: normal;">
            <div class="message">
                <c:if test='${fails.contains("preliminaryInterviewNoteNullOrEmpty")}'>
                    <p class="alert-text"><fmt:message
                            key="messages.preliminary_interview"/> <fmt:message
                            key="messages.cant_be_empty"/></p></c:if>
                <c:if test='${fails.contains("preliminaryInterviewNoteRegexFail")}'>
                    <p class="alert-text"><fmt:message
                            key="messages.preliminary_interview"/> <fmt:message
                            key="messages.incorrect"/></p></c:if>
                <c:if test='${fails.contains("technicalInterviewNoteNullOrEmpty")}'>
                    <p class="alert-text"><fmt:message
                            key="messages.technical_interview"/> <fmt:message
                            key="messages.cant_be_empty"/></p></c:if>
                <c:if test='${fails.contains("technicalInterviewNoteRegexFail")}'>
                    <p class="alert-text"><fmt:message
                            key="messages.technical_interview"/> <fmt:message
                            key="messages.incorrect"/></p></c:if>
            </div>

            <div class="item_body_top">
                <div class="user_info_top">
                    <div class="avatar"
                         style="background-image: url(${pageContext.request.contextPath}/download/${job_application.userAvatarPath}); min-width: 150px; min-height: 150px;">
                    </div>
                    <h2 style="margin-left: 20px;" class="user_name">
                        ${seeker.name} ${seeker.patronymic} ${seeker.lastName}
                    </h2>
                    <h3 style="color: var(--accept-btn-color); margin-left: 20px">
                        <fmt:message
                                key="enum.job_application_state.${job_application.state.name()}"/></h3>
                </div>
                <p><fmt:message key="label.email"/>: ${seeker.email}</p>
                <p><fmt:message key="label.phone"/>: ${seeker.phone}</p>
            </div>

            <div class="notes">
                <c:if test="${job_application.state.name() != 'RECENTLY_CREATED'
                           && job_application.state.name() != 'DENIED'
                           && user.role.name() == 'EMPLOYEE'}">

                    <form action="${pageContext.request.contextPath}/controller"
                          method="POST">
                        <input type="hidden" name="command"
                               value="update_preliminary_note">
                        <input type="hidden" name="job_application_id"
                               value="${job_application.id}">

                        <label for="preliminary_interview"
                               style="display: block; text-align: left; margin-top: 30px;"><fmt:message
                                key="label.preliminary_interview"/></label>
                        <br>
                        <textarea class="interview_note"
                                  name="preliminary_interview_note"
                                  id="preliminary_interview"
                                  maxlength="1024">${job_application.preliminaryInterviewNote}</textarea>
                        <br>

                        <div class="item_edit_actions">
                            <input class="btn interview_modify_btn"
                                   type="submit"
                                   value="<fmt:message key="button.save"/>">
                        </div>
                    </form>
                </c:if>

                <c:if test="${job_application.state.name() != 'RECENTLY_CREATED'
                           && job_application.state.name() != 'PRELIMINARY_INTERVIEW'
                           && job_application.state.name() != 'DENIED'
                           && user.role.name() == 'EMPLOYEE'}">

                    <form action="${pageContext.request.contextPath}/controller"
                          method="POST">
                        <input type="hidden" name="command"
                               value="update_technical_note">
                        <input type="hidden" name="job_application_id"
                               value="${job_application.id}">

                        <label for="technical_interview"
                               style="display: block; text-align: left; margin-top: 30px;"><fmt:message
                                key="label.technical_interview"/></label>
                        <br>
                        <textarea class="interview_note"
                                  name="technical_interview_note"
                                  id="technical_interview"
                                  maxlength="1024">${job_application.technicalInterviewNote}</textarea>

                        <div class="item_edit_actions">
                            <input class="btn interview_modify_btn"
                                   type="submit"
                                   value="<fmt:message key="button.save"/>">
                        </div>
                    </form>
                </c:if>
            </div>

            <div class="resume">
                <h3 style="margin-top: 10px; margin-bottom: 40px; text-align: center;">
                    <fmt:message key="header.resume"/></h3>
                <p style="white-space: pre-wrap; margin-top: 10px;">${job_application.resumeText}</p>
            </div>

            <div class="vacancy_description">
                <h3 style="margin-top: 10px; margin-bottom: 40px; text-align: center;">
                    <fmt:message key="header.vacancy"/></h3>
                <p style="white-space: pre-wrap; margin-top: 10px;">${job_application.vacancyDescription}</p>
            </div>
        </div>
    </main>

</div>
</body>

</html>
