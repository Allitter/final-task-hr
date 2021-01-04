<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>HR-ORG</title>
    <link rel="stylesheet" href="css/util.css">
    <link rel="stylesheet" href="css/main.css">

    <%@ include file="../head_common.jsp" %>
</head>

<body>
<%@ include file="../header.jsp" %>

<div class="nav-main">
    <%@ include file="../navigation.jsp" %>

    <main class="main item_more">
        <div class="item_header" style="display: flex; justify-content: flex-end;">
            <div class="item_header_buttons">
                <a class="btn" href="${pageContext.request.contextPath}/controller?command=job_seekers">Back</a>
                <c:if test="${job_application.state.name() == 'RECENTLY_CREATED'}">
                    <form action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="assign_preliminary_interview">
                        <input type="hidden" name="job_application_id" value="${job_application.id}">
                        <button class="btn">Assign preliminary interview</button>
                    </form>
                </c:if>

                <c:if test="${job_application.state.name() == 'FIRST_INTERVIEW'}">
                    <form action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="assign_technical_interview">
                        <input type="hidden" name="job_application_id" value="${job_application.id}">
                        <button class="btn">Assign technical interview</button>
                    </form>
                </c:if>

                <c:if test="${job_application.state.name() == 'TECHNICAL_INTERVIEW'}">
                    <form action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="assign_for_job">
                        <input type="hidden" name="job_application_id" value="${job_application.id}">
                        <button class="btn">Assign for job</button>
                    </form>
                </c:if>

                <c:if test="${job_application.state.name() != 'APPLIED' && job_application.state.name() != 'DENIED'}">
                    <form action="${pageContext.request.contextPath}/controller">
                        <input type="hidden" name="command" value="block_job_application">
                        <input type="hidden" name="job_application_id" value="${job_application.id}">
                        <button class="btn alert">Block</button>
                    </form>
                </c:if>
            </div>
        </div>

        <div class="item_body" style="text-align: start; white-space: normal;">
            <div class="item_body_top">
                <div class="user_info_top">
                    <div class="avatar"
                         style="background-image: url(/hr/images/2577247.jpg); min-width: 100px; min-height: 100px;">
                    </div>
                    <h2 style="margin-left: 20px;" class="item_name">
                        ${seeker.name} ${seeker.lastName} ${seeker.patronymic}
                    </h2>
                    <h3 style="color: var(--accept-btn-color); margin-left: 20px" >${job_application.state}</h3>
                </div>
                <p>e-mail: example@gmail.com</p>
                <p>phone: +375 (33) 123-45-67</p>
            </div>

            <div class="notes">
                <c:if test="${job_application.state.name() != 'RECENTLY_CREATED' && job_application.state.name() != 'DENIED'}">
                    <form action="" method="POST">
                        <input type="hidden" name="command" value="update_preliminary_note">
                        <input type="hidden" name="user_id" value="0">

                        <label for="preliminary_interview"
                               style="display: block; text-align: left; margin-top: 30px;">Preliminary
                            interview:</label>
                        <br>
                        <textarea class="interview_note" name="preliminary_interview"
                                  id="preliminary_interview">${job_application.preliminaryInterviewNote}</textarea>
                        <br>

                        <div class="item_edit_actions">
                            <input class="btn interview_modify_btn" type="submit" value="Modify">
                        </div>
                    </form>
                </c:if>

                <c:if test="${job_application.state.name() != 'RECENTLY_CREATED' && job_application.state.name() != 'FIRST_INTERVIEW' && job_application.state.name() != 'DENIED'}">
                    <form action="" method="POST">
                        <input type="hidden" name="command" value="update_technical_note">
                        <input type="hidden" name="user_id" value="0">

                        <label for="technical_interview"
                               style="display: block; text-align: left; margin-top: 30px;">Technical
                            interview:</label>
                        <br>
                        <textarea class="interview_note" name="technical_interview"
                                  id="technical_interview">${job_application.technicalInterviewNote}</textarea>

                        <div class="item_edit_actions">
                            <input class="btn interview_modify_btn" type="submit" value="Modify">
                        </div>
                    </form>
                </c:if>
            </div>

            <div class="resume">
                <h3 style="margin-top: 10px; margin-bottom: 40px; text-align: center;">Resume</h3>
                <p style="white-space: pre-wrap; margin-top: 10px;">${resume.text}</p>
            </div>
        </div>
    </main>

</div>
</body>

</html>
