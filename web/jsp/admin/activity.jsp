<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="selecttag" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : ''}"/>
<fmt:setBundle basename="resource.locale"/>

<html>
<head>
    <title>Activity</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        <%@include file="/css/style.css" %>
    </style>
    <script>
        <%@include file="../../js/script.js" %>
    </script>
</head>
<body>

<c:set var="role" value="${sessionScope.role}"/>
<c:choose>
    <c:when test="${role == 'ADMIN'}">
        <%@include file="jspf/header.jspf" %>
    </c:when>
    <c:when test="${role == 'TRAINER'}">
        <%@include file="../trainer/jspf/header.jspf" %>
    </c:when>
    <c:when test="${role == 'CLIENT'}">
        <%@include file="../client/jspf/header.jspf" %>
    </c:when>
</c:choose>

<br>
<br>
<h3 style="margin-left: 30px">    <fmt:message key="table.activity.head"/></h3>
<form id="createForm" name="createForm" method="get" action="controller">
    <input type="hidden" id="idCommand" name="command" value="activity_create">
    <input type="text" id="idActivity" name="id" hidden>

    <table class="greyGridTable">
        <thead>
        <tr>
            <th><fmt:message key="table.activity.number"/></th>
            <th><fmt:message key="table.activity.name"/></th>
            <th><fmt:message key="table.activity.price"/></th>
            <th><fmt:message key="table.activity.note"/></th>
            <th colspan="2"></th>
        </tr>
        </thead>
        <tbody>
        <jsp:useBean id="readAll" scope="request" type="java.util.List"/>
        <c:forEach var="elem" items="${readAll}" varStatus="status">
            <tr>
                <td><c:out value="${status.count}"/>
                </td>

                <td id="name${status.count}">
                    <c:out value="${elem.name}"/>
                    <ctg:select tagId="name${status.count}" elem="${elem.name}"/>
                </td>
                <td id="price${status.count}">
                    <c:out value="${elem.price}"/>
                    <ctg:select tagId="price${status.count}" elem="${elem.price}"/>
                </td>
                <td id="note${status.count}">
                    <c:out value="${elem.note}"/>
                    <ctg:select tagId="note${status.count}" elem="${elem.note}"/>
                </td>
                <td>
                    <a class="onClickCursor" onclick="fillActivityForm('idCount', ${status.count}, 'idActivity', 'idCommand', 'idSubmit', ${elem.idActivity},
                            '<fmt:message key="body.create"/>',
                            '<fmt:message key="body.update"/>')">
                        U
                    </a>
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}
                            /controller?command=activity_delete&id=${elem.idActivity}">
                        D
                    </a>
                </td>
            </tr>
        </c:forEach>

        <tr>
            <td><input form="createForm" type="text" id="idCount" name="count" readonly></td>
            <td><input form="createForm" type="text" name="name"></td>
            <td><input form="createForm" type="text" name="price"></td>
            <td><input form="createForm" type="text" name="note"></td>
            <td colspan="2"><input form="createForm" id="idSubmit" type="submit"
                                   value="<fmt:message key="body.create"/>"></td>
        </tr>
        </tbody>
    </table>
</form>
<br>
<div class="leftMessage">${error}</div>
</body>
</html>