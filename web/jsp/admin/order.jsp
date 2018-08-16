<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="selecttag" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : ''}"/>
<fmt:setBundle basename="resource.locale"/>

<html>
<head>
    <title>Order</title>
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
<h3 style="margin-left: 30px"><fmt:message key="table.order.head"/></h3>
<form id="createForm" name="createForm" method="get" action="controller">
    <input type="hidden" name="formId" value="${sessionScope.formSessionId}">
    <input type="hidden" id="idCommand" name="command" value="order_create">

    <table class="greyGridTable">
        <thead>
        <tr>
            <th><fmt:message key="table.order.number"/></th>
            <th><fmt:message key="table.order.date"/></th>
            <th><fmt:message key="table.order.price"/></th>
            <th><fmt:message key="table.order.discount"/></th>
            <th><fmt:message key="table.order.closure"/></th>
            <th><fmt:message key="table.order.feedback"/></th>
            <th><fmt:message key="table.order.client"/></th>
            <th><fmt:message key="table.order.activity"/></th>
            <th colspan="2"></th>
        </tr>
        </thead>
        <tbody>

        <jsp:useBean id="readAll" scope="request" type="java.util.List"/>
        <jsp:useBean id="readClient" scope="request" type="java.util.List"/>
        <jsp:useBean id="readActivity" scope="request" type="java.util.List"/>

        <c:forEach var="elem" items="${readAll}" varStatus="status">
            <tr>
                <td>
                    <c:out value="${elem.idOrder}"/>
                </td>

                <td id="date${status.count}">
                    <c:out value="${elem.date}"/>
                    <ctg:select tagId="date${status.count}" elem="${elem.date}"/>
                </td>
                <td id="price${status.count}">
                    <c:out value="${elem.price}"/>
                    <ctg:select tagId="price${status.count}" elem="${elem.price}"/>
                </td>
                <td id="discount${status.count}">
                    <c:out value="${elem.discount}"/>
                    <ctg:select tagId="discount${status.count}" elem="${elem.discount}"/>
                </td>
                <td id="closureDate${status.count}">
                    <c:out value="${elem.closureDate}"/>
                    <ctg:select tagId="closureDate${status.count}" elem="${elem.closureDate}"/>
                </td>
                <td id="feedback${status.count}">
                    <c:out value="${elem.feedback}"/>
                    <ctg:select tagId="feedback${status.count}" elem="${elem.feedback}"/>
                </td>

                <td id="idClient${status.count}">
                    <c:set value="${readClient.get(status.count-1).name}" var="name"/>
                    <c:set value="${readClient.get(status.count-1).lastName}" var="lastName"/>
                    <c:out value="${name} ${lastName}"/>
                    <ctg:select tagId="idClient${status.count}" elem="${name} ${lastName}"/>
                </td>
                <td id="idActivity${status.count}">
                    <c:set value="${readActivity.get(status.count-1).name}" var="name"/>
                    <c:out value="${name}"/>
                    <ctg:select tagId="idActivity${status.count}" elem="${name}"/>
                </td>
                <td>
                    <a class="onClickCursor" onclick="fillOrderForm('idOrder', 'idCommand', 'idSubmit', 'idClient',
                            'idActivity', ${elem.idOrder}, ${elem.idClient}, ${elem.idActivity},
                            '<fmt:message key="body.create"/>',
                            '<fmt:message key="body.update"/>')">
                        U
                    </a>
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}
                            /controller?command=order_delete&id=${elem.idOrder}">
                        D
                    </a>
                </td>
            </tr>
        </c:forEach>

        <jsp:useBean id="readAllClient" scope="request" type="java.util.List"/>
        <jsp:useBean id="readAllActivity" scope="request" type="java.util.List"/>
        <tr>
            <td><input form="createForm" type="text" id="idOrder" name="id" readonly></td>
            <td><input form="createForm" type="text" placeholder="yyyy-mm-dd" name="date"></td>
            <td><input form="createForm" type="text" name="price"></td>
            <td><input form="createForm" type="text" name="discount"></td>
            <td><input form="createForm" type="text" placeholder="yyyy-mm-dd" name="closure"></td>
            <td><input form="createForm" type="text" name="feedback"></td>
            <td>
                <select form="createForm" id="idClient" name="clientId" required>
                    <option></option>
                    <c:forEach var="elem" items="${readAllClient}" varStatus="status">
                        <option value="${elem.idClient}">${elem.name} ${elem.lastName}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <select form="createForm" id="idActivity" name="activityId" required>
                    <option></option>
                    <c:forEach var="elem" items="${readAllActivity}" varStatus="status">
                        <option value="${elem.idActivity}">${elem.name}</option>
                    </c:forEach>
                </select>
            </td>
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