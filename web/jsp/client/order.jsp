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

    <%--<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">--%>

    <style>
        <%@include file="/css/style.css" %>
    </style>
    <%--<script type="text/javascript" src="../../js/script.js"></script>--%>
    <script>
        <%@include file="../../js/script.js" %>
    </script>
</head>
<body>
<p>Something to TYPE</p>

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
<h3><fmt:message key="table.order.head"/></h3>
<form id="createForm" name="createForm" method="get" action="controller">
    <input type="hidden" name="command" value="order_update_client">

    <table class="greyGridTable">
        <thead>
        <tr>
            <th><fmt:message key="table.order.number"/></th>
            <th><fmt:message key="table.order.date"/></th>
            <th><fmt:message key="table.order.price"/></th>
            <th><fmt:message key="table.order.discount"/></th>
            <th><fmt:message key="table.order.closure"/></th>
            <th><fmt:message key="table.order.feedback"/></th>
            <th><fmt:message key="table.order.activity"/></th>
            <th></th>
        </tr>
        </thead>
        <tbody>

        <jsp:useBean id="readAll" scope="request" type="java.util.List"/>
        <jsp:useBean id="readActivity" scope="request" type="java.util.List"/>

        <c:forEach var="elem" items="${readAll}" varStatus="status">
            <tr>
                <td>
                    <c:out value="${status.count}"/>
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
                <td id="idActivity${status.count}">
                    <c:set value="${readActivity.get(status.count-1).name}" var="name" scope="page"/>
                    <c:out value="${name}"/>
                    <ctg:select tagId="idActivity${status.count}" elem="${name}"/>
                </td>
                <td>
                    <a onclick="fillOrderFormClient('idCount', ${status.count}, 'idOrder', 'idClient', 'idActivity',
                        ${elem.idOrder},
                        ${elem.idClient},
                        ${elem.idActivity})">
                        U
                    </a>
                </td>
            </tr>
        </c:forEach>

        <tr>
            <input form="createForm" type="text" id="idOrder" name="id" hidden>
            <td><input form="createForm" type="text" id="idCount" name="count" readonly></td>
            <td><input form="createForm" type="text" placeholder="yyyy/mm/dd" name="date" hidden></td>
            <td><input form="createForm" type="text" name="price" hidden></td>
            <td><input form="createForm" type="text" name="discount" hidden></td>
            <td><input form="createForm" type="text" name="closure" hidden></td>
            <td><input form="createForm" type="text" name="feedback"></td>
            <input form="createForm" type="hidden" id="idClient" name="idClient">
            <td>
                <input form="createForm" type="text" id="idActivity" name="activityId" hidden>
            </td>
            <td colspan="2"><input form="createForm" type="submit"
                                   value="<fmt:message key="body.update"/>"></td>
        </tr>
        </tbody>
    </table>

</form>
<br>
<div class="leftMessage">${error}</div>


<%--<footer>--%>
<%--<c:import url="../general/footer.jsp"/>--%>
<%--</footer>--%>
</body>

</html>