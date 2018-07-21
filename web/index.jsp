<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<html>
<head><title>Start page</title></head>
<body>
<c:set var="role" value="${sessionScope.role}"/>
<c:choose>
    <c:when test="${role == 'ADMIN' || role == 'CLIENT'|| role == 'TRAINER'}">
        <jsp:forward page="jsp/general/main.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:forward page="jsp/general/login.jsp"/>
    </c:otherwise>
</c:choose>
</body></html>