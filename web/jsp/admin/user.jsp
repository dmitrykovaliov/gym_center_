<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="selecttag" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : ''}"/>
<fmt:setBundle basename="resource.locale"/>

<html>
<head>
    <title>User</title>
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
        <%@include file="../admin/jspf/header.jspf" %>
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
<h3 style="margin-left: 30px"><fmt:message key="table.user.head"/></h3>
<form id="createForm" name="createForm" method="get" action="controller">
    <input type="hidden" name="formId" value="${sessionScope.formSessionId}" >
    <input id="idHid" type="hidden" name="command" value="user_create">

    <table class="greyGridTable">
        <thead>
        <tr>
            <th><fmt:message key="table.user.number"/></th>
            <th><fmt:message key="table.user.login"/></th>
            <th><fmt:message key="table.user.pass"/></th>
            <th><fmt:message key="table.user.role"/></th>
            <th><fmt:message key="table.user.client"/></th>
            <th><fmt:message key="table.user.trainer"/></th>
            <th colspan="1"></th>
        </tr>
        </thead>
        <tbody>
        <jsp:useBean id="readAll" scope="request" type="java.util.List"/>
        <jsp:useBean id="readClient" scope="request" type="java.util.List"/>
        <jsp:useBean id="readTrainer" scope="request" type="java.util.List"/>
        <c:forEach var="elem" items="${readAll}" varStatus="status">
            <tr>
                <td>
                    <c:out value="${status.count}"/>
                </td>
                <td id="login${status.count}">
                    <c:out value="${elem.login}"/>
                    <ctg:select tagId="login${status.count}" elem="${elem.login}"/>
                </td>
                <td id="pass${status.count}">
                    <c:out value="********"/>
                    <ctg:select tagId="pass${status.count}" elem="********"/>
                </td>
                <td id="role${status.count}">
                    <c:out value="${elem.role}"/>
                    <ctg:select tagId="role${status.count}" elem="${elem.role}"/>
                </td>
                <td id="idClient${status.count}">
                    <c:set value="${readClient.get(status.count-1).idClient}" var="idClient"/>
                    <c:set value="${readClient.get(status.count-1).name}" var="name"/>
                    <c:set value="${readClient.get(status.count-1).lastName}" var="lastName"/>

                    <c:out value="${name} ${lastName}"/>
                    <ctg:select tagId="idClient${status.count}" elem="${name} ${lastName}"/>
                </td>
                <td id="idTrainer${status.count}">

                    <c:set value="${readTrainer.get(status.count-1).idTrainer}" var="idTrainer" scope="page"/>
                    <c:set value="${readTrainer.get(status.count-1).name}" var="name"/>
                    <c:set value="${readTrainer.get(status.count-1).lastName}" var="lastName"/>

                    <c:out value="${name} ${lastName}"/>
                    <ctg:select tagId="idTrainer${status.count}" elem="${name} ${lastName}"/>
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}
                            /controller?command=user_delete&id=${elem.idUser}&clientId=${idClient}&trainerId=${idTrainer}">
                        D
                    </a>
                </td>
            </tr>
        </c:forEach>

        <jsp:useBean id="readAllClient" scope="request" type="java.util.List"/>
        <jsp:useBean id="readAllTrainer" scope="request" type="java.util.List"/>
        <jsp:useBean id="readAllRole" scope="request" type="java.util.List"/>
        <tr>
            <td><input form="createForm" type="text" id="idAct" name="id" readonly></td>
            <td><input form="createForm" type="text" name="login"></td>
            <td><input form="createForm" type="password" name="pass"></td>
            <td>
                <select form="createForm" name="role">
                    <c:forEach var="elem" items="${readAllRole}" varStatus="status">
                        <option value="${elem}">${elem}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <select form="createForm" name="clientId">
                    <option id="idCl" value="0"></option>
                    <c:forEach var="elem" items="${readAllClient}" varStatus="status">
                        <option value="${elem.idClient}">${elem.name} ${elem.lastName}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <select form="createForm" name="trainerId">
                    <option id="idTr" value="0"></option>
                    <c:forEach var="elem" items="${readAllTrainer}" varStatus="status">
                        <option value="${elem.idTrainer}">${elem.name} ${elem.lastName}</option>
                    </c:forEach>
                </select>
            </td>
            <td><input form="createForm" id="idSubm" type="submit"
                       value="<fmt:message key="body.create"/>"></td>
        </tr>
        </tbody>
    </table>

</form>
<br>
<div class="leftMessage">${error}</div>

</body>

</html>