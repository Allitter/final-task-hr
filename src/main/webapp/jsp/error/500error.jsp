<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>500</title>
    <link rel="stylesheet" href="css/500error.css">
    <link href="https://fonts.googleapis.com/css?family=Encode+Sans+Semi+Condensed:100,200,300,400" rel="stylesheet">

    <%@ include file="../head_common.jsp"%>
</head>

<body>
<h1>500</h1>
<h2><fmt:message key="error.server_error"/><b>:(</b></h2>
<h2>
    <fmt:message key="error.go" />
    <a href="${pageContext.request.contextPath}/controller">
        <fmt:message key="error.home" />
    </a>
</h2>
<div class="gears">
    <div class="gear one">
        <div class="bar"></div>
        <div class="bar"></div>
        <div class="bar"></div>
    </div>
    <div class="gear two">
        <div class="bar"></div>
        <div class="bar"></div>
        <div class="bar"></div>
    </div>
    <div class="gear three">
        <div class="bar"></div>
        <div class="bar"></div>
        <div class="bar"></div>
    </div>
</div>
</body>

</html>