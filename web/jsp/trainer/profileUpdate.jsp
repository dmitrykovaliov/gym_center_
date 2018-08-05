<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : ''}"/>
<fmt:setBundle basename="resource.locale"/>

<html>
<head>
    <title>Client</title>
    <%--<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">--%>

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
    <script type="text/javascript" src="../../js/script.js"></script>
</head>
<body>
<header>

    <c:set var="role" value="${sessionScope.role}"/>


    <c:choose>
        <c:when test="${role == 'ADMIN'}">
            <%@include file="../admin/jspf/header.jspf" %>
        </c:when>
        <c:when test="${role == 'TRAINER'}">
            <%@include file="../admin/jspf/header.jspf" %>
        </c:when>
        <c:when test="${role == 'CLIENT'}">
            <%@include file="../admin/jspf/header.jspf" %>
        </c:when>
    </c:choose>
</header>
<div>
    <form class="ui-form" id="createForm" name="createForm" method="post"
          action="${pageContext.request.contextPath}/controller" enctype="multipart/form-data">
        <input type="hidden" name="command" value="trainer_update">
        <div class="form-row">
            <input id="id" form="createForm" type="text" name="id" readonly autocomplete="off">
            <label for="id"><fmt:message key="table.trainer.id"/></label>
        </div>
        <div class="form-row">
            <input id="name" form="createForm" type="text" name="name" autocomplete="off">
            <label for="name"><fmt:message key="table.trainer.name"/></label>
        </div>
        <div class="form-row">
            <input id="lastName" form="createForm" type="text" name="lastName" autocomplete="off">
            <label for="lastName"><fmt:message key="table.trainer.lastName"/></label>
        </div>
        <div class="form-row">
            <input id="phone" form="createForm" type="text" name="phone" autocomplete="off">
            <label for="phone"><fmt:message key="table.trainer.phone"/></label>
        </div>
        <div class="form-row">
            <input id="personalData" form="createForm" type="text" name="personalData" autocomplete="off">
            <label for="personalData"><fmt:message key="table.trainer.personalData"/></label>
        </div>
        <div class="form-row">
            <input id="icon" form="createForm" type="file" name="iconPath">
            <label for="icon"><fmt:message key="table.trainer.iconPath"/></label>
        </div>

        <input form="createForm" id="idSubm" type="submit"
               value="<fmt:message key="body.update"/>">
    </form>


    <%--<footer>--%>
    <%--<c:import url="../general/footer.jsp"/>--%>
    <%--</footer>--%>
</body>

</html>