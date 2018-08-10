<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : ''}"/>
<fmt:setBundle basename="resource.locale"/>

<html>
<head>
    <title>Client</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        <%@include file="../../css/style.css" %>
    </style>
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

    <form class="ui-form" name="loginForm" method="post"
          action="${pageContext.request.contextPath}/controller" enctype="multipart/form-data">
        <input type="hidden" name="command" value="trainer_update"/>
        <input id="id" type="hidden" name="id" value="${param.id}"/>

        <div class="form-row">
            <input id="count" type="text" name="count" value="${param.count}" autocomplete="off" readonly/>
        </div>
        <div class="form-row">
            <input id="name" type="text" name="name" autocomplete="off"/>
            <label for="name"><fmt:message key="table.trainer.name"/></label>
        </div>
        <div class="form-row">
            <input id="lastName" type="text" name="lastName" autocomplete="off"/>
            <label for="lastName"><fmt:message key="table.trainer.lastName"/></label>
        </div>
        <div class="form-row">
            <input id="phone" type="text" name="phone" autocomplete="off"/>
            <label for="phone"><fmt:message key="table.trainer.phone"/></label>
        </div>
        <div class="form-row">
            <input id="email" type="text" name="email" autocomplete="off"/>
            <label for="email"><fmt:message key="table.trainer.phone"/></label>
        </div>
        <div class="form-row">
            <input id="personalData" type="text" name="personalData" autocomplete="off"/>
            <label for="personalData"><fmt:message key="table.trainer.personalData"/></label>
        </div>
        <div class="form-row">
            <input id="icon" type="file" name="iconPath"/>
            <label for="icon"><fmt:message key="table.trainer.iconPath"/></label>
        </div>

        <input type="submit"
               value="<fmt:message key="body.update"/>"/>
    </form>
</body>

</html>