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
        <input type="hidden" name="command" value="client_update">
        <div class="form-row">
            <input id="id" form="createForm" type="text" name="id" readonly autocomplete="off">
            <label for="id"><fmt:message key="table.client.id"/></label>
        </div>
        <div class="form-row">
            <input id="name" form="createForm" type="text" name="name" autocomplete="off">
            <label for="name"><fmt:message key="table.client.name"/></label>
        </div>
        <div class="form-row">
            <input id="lastName" form="createForm" type="text" name="lastName" autocomplete="off">
            <label for="lastName"><fmt:message key="table.client.lastName"/></label>
        </div>
        <div class="form-row">
            <input id="phone" form="createForm" type="text" name="phone" autocomplete="off">
            <label for="phone"><fmt:message key="table.client.phone"/></label>
        </div>
        <div class="form-row">
            <input id="email" form="createForm" type="text" name="email" autocomplete="off">
            <label for="email"><fmt:message key="table.client.email"/></label>
        </div>
        <div class="form-row">
            <input id="personalData" form="createForm" type="text" name="personalData" autocomplete="off">
            <label for="personalData"><fmt:message key="table.client.personalData"/></label>
        </div>
        <div class="form-row">
            <input id="login" form="createForm" type="text" name="login" autocomplete="off">
            <label for="login"><fmt:message key="table.client.login"/></label>
        </div>
        <div class="form-row">
            <input id="pass" form="createForm" type="text" name="pass" autocomplete="off">
            <label for="pass"><fmt:message key="table.client.pass"/></label>
        </div>
        <div class="form-row">
            <input id="role" form="createForm" type="text" name="role" autocomplete="off">
            <label for="role"><fmt:message key="table.client.role"/></label>
        </div>
        <div class="form-row">
            <input id="icon" form="createForm" type="file" name="iconPath">
            <label for="icon"><fmt:message key="table.client.iconPath"/></label>
        </div>

        <input form="createForm" id="idSubm" type="submit"
               value="<fmt:message key="body.create"/>">
    </form>


    <%--<footer>--%>
    <%--<c:import url="../general/footer.jsp"/>--%>
    <%--</footer>--%>
</body>

</html>