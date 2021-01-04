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


    <main class="main item_edit">

        <form action="${pageContext.request.contextPath}/controller" method="POST">
            <input type="hidden" name="command" value="resume_add_accept">

            <label for="name" style="text-align: left; margin: 10px 0;">Name:</label>
            <input name="resume_name" id="name" class="item_name" type="text">
            <br>
            <label for="item_long_desc" style="text-align: left; margin: 10px 0;">Resume:</label>
            <textarea class="item_long_desc" id="item_long_desc" name="item_long_desc" style="width: auto; height: 600px; resize: none;"></textarea>
            <br>

            <div class="item_edit_actions">
                <a class="btn" style="box-sizing: border-box;" href="${pageContext.request.contextPath}/controller?command=account">Cancel</a>
                <input class="btn" style="box-sizing: border-box; width: 90px; margin-left: 20px;" type="submit" value="Accept">
            </div>
        </form>
    </main>
</div>
</body>

</html>
