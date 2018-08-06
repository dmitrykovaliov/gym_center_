<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="selecttag" %>

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
    <script>
        <%@include file="../../js/script.js" %>
    </script>
    <%--<script type="text/javascript" src="../../js/script.js"></script>--%>
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
<br>
<br>
<h3><fmt:message key="table.client.head"/></h3>
<div>
    <table class="greyGridTable">
        <thead>
        <tr>
            <th><fmt:message key="table.client.id"/></th>
            <th><fmt:message key="table.client.name"/></th>
            <th><fmt:message key="table.client.lastName"/></th>
            <th><fmt:message key="table.client.phone"/></th>
            <th><fmt:message key="table.client.email"/></th>
            <th><fmt:message key="table.client.personalData"/></th>
            <th><fmt:message key="table.client.iconPath"/></th>
            <th colspan="2"></th>
        </tr>
        </thead>
        <tbody>
        <jsp:useBean id="readAll" scope="request" type="java.util.List"/>
        <c:forEach var="elem" items="${readAll}" varStatus="status">
            <tr>
                <td><c:out value="${elem.idClient}"/>
                </td>

                <td id="name${status.count}">
                    <c:out value="${elem.name}"/>
                    <ctg:select tagId="name${status.count}" elem="${elem.name}"/>
                </td>
                <td id="lastName${status.count}"><c:out value="${elem.lastName}"/>
                    <ctg:select tagId="lastName${status.count}" elem="${elem.lastName}"/>
                </td>
                <td id="phone${status.count}"><c:out value="${elem.phone}"/>
                    <ctg:select tagId="phone${status.count}" elem="${elem.phone}"/>
                </td>
                <td id="email${status.count}"><c:out value="${elem.email}"/>
                    <ctg:select tagId="email${status.count}" elem="${elem.email}"/>
                </td>
                <td id="personalData${status.count}"><c:out value="${elem.personalData}"/>
                    <ctg:select tagId="personalData${status.count}" elem="${elem.personalData}"/>
                </td>
                <td>
                    <c:if test="${not empty elem.iconPath}">
                        <img class="iconBit" src="/picture/${elem.iconPath}"/>
                    </c:if>
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}
                        /jsp/client/update.jsp?id=${elem.idClient}">
                        U
                    </a>
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}
                        /controller?command=client_delete&id=${elem.idClient}">
                        D
                    </a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="8"></td>
            <td colspan="2">
                <a href="${pageContext.servletContext.contextPath}/jsp/client/createForm.jsp">
                    <fmt:message key="body.create"/>
                </a>
            </td>
        </tr>
        </tbody>
    </table>
    <br>
    <div class="leftMessage">${error}</div>


    <%--<footer>--%>
    <%--<c:import url="../general/footer.jsp"/>--%>
    <%--</footer>--%>
</body>

</html>