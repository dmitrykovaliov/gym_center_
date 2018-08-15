<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="selecttag" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : ''}"/>
<fmt:setBundle basename="resource.locale"/>

<html>
<head>
    <title>Training</title>
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
<header>

    <c:set var="role" value="${sessionScope.role}"/>


    <c:choose>
        <c:when test="${role == 'ADMIN'}">
            <%@include file="../admin/jspf/header.jspf" %>
        </c:when>
        <c:when test="${role == 'TRAINER'}">
            <%@include file="../trainer/jspf/header.jspf" %>
        </c:when>
        <c:when test="${role == 'CLIENT'}">
            <%@include file="../client/jspf/header.jspf" %>
        </c:when>
    </c:choose>
</header>
<br>
<br>
<h3 style="margin-left: 30px"><fmt:message key="table.training.head"/></h3>
<form id="createForm" name="createForm" method="get" action="controller">
    <input type="hidden" name="command" value="training_update_trainer">
    <input type="hidden" name="formId" value="${sessionScope.formSessionId}" >
    <input type="hidden" id="idTraining" name="id" >
    <table class="greyGridTable">
        <thead>
        <tr>
            <th><fmt:message key="table.training.number"/></th>
            <th><fmt:message key="table.training.date"/></th>
            <th><fmt:message key="table.training.startTime"/></th>
            <th><fmt:message key="table.training.endTime"/></th>
            <th><fmt:message key="table.training.visited"/></th>
            <th><fmt:message key="table.training.clientNote"/></th>
            <th><fmt:message key="table.training.trainterNote"/></th>
            <th><fmt:message key="table.training.order"/></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <jsp:useBean id="readAll" scope="request" type="java.util.List"/>
        <c:forEach var="elem" items="${readAll}" varStatus="status">
            <tr>
                <td>
                    <c:out value="${status.count}"/>
                </td>

                <td id="date${status.count}">
                    <c:out value="${elem.date}"/>
                    <ctg:select tagId="date${status.count}" elem="${elem.date}"/>
                </td>
                <td id="startTime${status.count}">
                    <c:out value="${elem.startTime}"/>
                    <ctg:select tagId="startTime${status.count}" elem="${elem.startTime}"/>
                </td>
                <td id="endTime${status.count}">
                    <c:out value="${elem.endTime}"/>
                    <ctg:select tagId="endTime${status.count}" elem="${elem.endTime}"/>
                </td>
                <td id="visited${status.count}">
                    <c:if test="${elem.visited == 'true'}">
                        <c:out value="visited"/>
                    </c:if>
                    <ctg:select tagId="endTime${status.count}" elem="${elem.visited}"/>
                </td>
                <td id="clientNote${status.count}">
                    <c:out value="${elem.clientNote}"/>
                    <ctg:select tagId="clientNote${status.count}" elem="${elem.clientNote}"/>
                </td>
                <td id="trainerNote${status.count}">
                    <c:out value="${elem.trainerNote}"/>
                    <ctg:select tagId="trainerNote${status.count}" elem="${elem.trainerNote}"/>
                </td>
                <td id="idOrder${status.count}">
                    <c:out value="${elem.idOrder}"/>
                    <ctg:select tagId="idOrder${status.count}" elem="${elem.idOrder}"/>
                </td>
                <td>
                    <a class="onClickCursor" onclick="fillTrainingFormTrainer('idCount', ${status.count}, 'idTraining',
                            'idOrder', 'idSubmit', 'trainerNote',
                        ${elem.idTraining},
                            ${elem.idOrder})">
                        U
                    </a>
                </td>
            </tr>
        </c:forEach>

        <tr>
            <td><input form="createForm" type="text" id="idCount" name="count" readonly></td>
            <td><input form="createForm" type="text" name="date" placeholder="yyyy/mm/dd" hidden></td>
            <td><input form="createForm" type="text" name="startTime" placeholder="hh:mm" hidden></td>
            <td><input form="createForm" type="text" name="endTime" placeholder="hh:mm" hidden></td>
            <td><input form="createForm" type="text" name="visited" hidden></td>
            <td><input form="createForm" type="text" name="clientNote" hidden></td>
            <td><input form="createForm" type="text" id="trainerNote" name="trainerNote" hidden></td>
            <td>
                <input form="createForm" id="idOrder" name="orderId" hidden>
            </td>
            <td><input form="createForm" id="idSubmit" type="submit"
                                   value="<fmt:message key="body.update"/>" hidden></td>
        </tr>

        </tbody>
    </table>

</form>
<br>
<div class="leftMessage">${error}</div>
</body>

</html>