<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : ''}"/>
<fmt:setBundle basename="resource.locale"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome</title></head>
<body>


<c:set var="role" value="${sessionScope.role}"/>

<header>
<c:choose>
    <c:when test="${role == 'ADMIN'}">
        <c:import url="../admin/header.jsp"/>
    </c:when>
    <c:when test="${role == 'TRAINER'}">
        <c:import url="../admin/header.jsp"/>
    </c:when>
    <c:otherwise>
        <c:import url="../admin/header.jsp"/>
    </c:otherwise>
</c:choose>
</header>
<br>
<br>
<br>
<div>
    <h2><fmt:message key="article.main.head"/></h2>

    <p><fmt:message key="article.main.part1"/></p>
    <p><fmt:message key="article.main.part2"/></p>
</div>



<%--<footer>--%>
<%--<c:import url="footer.jsp"/>--%>
<%--</footer>--%>
</body>

</html>