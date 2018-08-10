<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="selecttag" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : ''}"/>
<fmt:setBundle basename="resource.locale"/>

<html>
<head>
    <title>Trainer</title>

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
<h3 style="margin-left: 30px"><fmt:message key="table.trainer.head"/></h3>
    <table class="greyGridTable">
        <thead>
        <tr>
            <th><fmt:message key="table.trainer.name"/></th>
            <th><fmt:message key="table.trainer.lastName"/></th>
            <th><fmt:message key="table.trainer.phone"/></th>
            <th><fmt:message key="table.trainer.personalData"/></th>
            <th><fmt:message key="table.trainer.iconPath"/></th>
        </tr>
        </thead>
        <tbody>
        <jsp:useBean id="read" scope="request" type="com.dk.gym.entity.Client"/>
            <tr>
                <td id="name">
                    <c:out value="${read.name}"/>
                    <ctg:select tagId="name" elem="${read.name}"/>
                </td>
                <td id="lastName"><c:out value="${read.lastName}"/>
                    <ctg:select tagId="lastName" elem="${read.lastName}"/>
                </td>
                <td id="phone"><c:out value="${read.phone}"/>
                    <ctg:select tagId="phone" elem="${read.phone}"/>
                </td>
                <td id="email"><c:out value="${read.email}"/>
                    <ctg:select tagId="email" elem="${read.email}"/>
                </td>
                <td id="personalData"><c:out value="${read.personalData}"/>
                    <ctg:select tagId="personalData" elem="${read.personalData}"/>
                </td>
                <td>
                    <c:if test="${not empty read.iconPath}">
                        <img class="idconBitLarge" src="/picture/${read.iconPath}"/>
                    </c:if>
                </td>
            </tr>
        </tbody>
    </table>
    <br>
    <div class="leftMessage">${error}</div>

</body>

</html>