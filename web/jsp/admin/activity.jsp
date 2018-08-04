<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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

    <br>
    <br>
        <form id="createForm" name="createForm" method="get" action="controller">
            <input id="idHid" type="hidden" name="command" value="activity_create">

            <table class="greyGridTable">
                <thead>
                <tr>
                    <th><fmt:message key="table.activity.id"/></th>
                    <th><fmt:message key="table.activity.name"/></th>
                    <th><fmt:message key="table.activity.price"/></th>
                    <th><fmt:message key="table.activity.note"/></th>
                    <th colspan="3"></th>
                </tr>
                </thead>
                <tbody>
                <jsp:useBean id="readAll" scope="request" type="java.util.List"/>
                <c:forEach var="elem" items="${readAll}" varStatus="status">
                    <tr>
                        <td><c:out value="${elem.idActivity}"/>
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
                            <a onclick="addIdToFieldActivity('idAct', 'idHid', 'idSubm', ${elem.idActivity},
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
                        <td>
                            <a href="${pageContext.servletContext.contextPath}
                            /controller?command=order_read&id=${elem.idActivity}">
                        <th><fmt:message key="table.order.head"/></th>
                        </a>
                        </td>
                    </tr>
                </c:forEach>

                <tr>
                    <td><input form="createForm" type="text" id="idAct" name="id" readonly></td>
                    <td><input form="createForm" type="text" name="name"></td>
                    <td><input form="createForm" type="text" name="price"></td>
                    <td><input form="createForm" type="text" name="note"></td>
                    <td colspan="2"><input form="createForm" id="idSubm" type="submit"
                                           value="<fmt:message key="body.create"/>"></td>
                    <td></td>
                </tr>

                <%--<tr>--%>
                <%--<td><input form="createForm" type="number" id="idAct" name="id" readonly></td>--%>
                <%--<td><input form="createForm" type="text" name="name" pattern=".{0,200}" title="enter correct number">--%>
                <%--</td>--%>
                <%--<td><input form="createForm" type="text" name="price" pattern="(\d{1,18}[.,]\d{0,2})|(\d{1,20})"--%>
                <%--title="please check number"></td>--%>
                <%--<td><input form="createForm" type="text" name="note" pattern=".{0, 1500}" title="Please check length"></td>--%>
                <%--<td colspan="2"><input form="createForm" id="idSubm" type="submit"--%>
                <%--value="<fmt:message key="body.create"/>"></td>--%>
                <%--</tr>--%>

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