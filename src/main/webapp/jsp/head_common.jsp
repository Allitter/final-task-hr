<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=0.86, maximum-scale=2.0, minimum-scale=0.86">
<!--favicon-->
<link rel="shortcut icon" href="favicon.ico" type="image/png">

<!--fonts-->
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Fira+Sans&display=swap" rel="stylesheet">

<!--jstl import-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="hrt" uri="/WEB-INF/tld/hr-tags.tld" %>

<!--localisation-->
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="properties.text" />
<c:choose>
    <c:when test="${'ru'.equals(lang)}"><c:set value="dd.MM.yyyy" var="dateFormat" /></c:when>
    <c:when test="${'be'.equals(lang)}"><c:set value="dd.MM.yyyy" var="dateFormat" /></c:when>
    <c:otherwise><c:set value="dd-MM-yyyy" var="dateFormat" /></c:otherwise>
</c:choose>