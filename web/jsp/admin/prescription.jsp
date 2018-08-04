<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="selecttag" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : ''}"/>
<fmt:setBundle basename="resource.locale"/>

<html>
<head>
    <title>Prescription</title>
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
<h3><fmt:message key="table.prescription.head"/></h3>
        <form id="createForm" name="createForm" method="get" action="controller">
            <input id="idHid" type="hidden" name="command" value="prescription_create">

            <table class="greyGridTable">
                <thead>
                <tr>
                    <th><fmt:message key="table.prescription.idOrder"/></th>
                    <th><fmt:message key="table.prescription.idTrainer"/></th>
                    <th><fmt:message key="table.prescription.date"/></th>
                    <th><fmt:message key="table.prescription.weeks"/></th>
                    <th><fmt:message key="table.prescription.trainingsPerWeek"/></th>
                    <th><fmt:message key="table.prescription.trainerNote"/></th>
                    <th><fmt:message key="table.prescription.clientNote"/></th>
                    <th><fmt:message key="table.prescription.agreed"/></th>
                    <th colspan="2"></th>
                </tr>
                </thead>
                <tbody>
                <jsp:useBean id="readAll" scope="request" type="java.util.List"/>
                <c:forEach var="elem" items="${readAll}" varStatus="status">
                    <tr>
                        <td>
                            <c:out value="${elem.prescription.idOrder}"/>
                        </td>
                        <td>
                            <c:set value="${elem.trainer.name}" var="name"/>
                            <c:set value="${elem.trainer.lastName}" var="lastName"/>
                            <c:out value="${name} ${lastName}"/>
                        </td>

                        <td id="date${status.count}">
                            <c:out value="${elem.prescription.date}"/>
                            <ctg:select tagId="date${status.count}" elem="${elem.prescription.date}"/>
                        </td>
                        <td id="weekQuantity${status.count}">
                            <c:out value="${elem.prescription.weekQuantity}"/>
                            <ctg:select tagId="weekQuantity${status.count}" elem="${elem.prescription.weekQuantity}"/>
                        </td>
                        <td id="trainingsWeek${status.count}">
                            <c:out value="${elem.prescription.trainingsWeek}"/>
                            <ctg:select tagId="trainingsWeek${status.count}" elem="${elem.prescription.trainingsWeek}"/>
                        </td>
                        <td id="trainerNote${status.count}">
                            <c:out value="${elem.prescription.trainerNote}"/>
                            <ctg:select tagId="trainerNote${status.count}" elem="${elem.prescription.trainerNote}"/>
                        </td>
                        <td id="clientNote${status.count}">
                            <c:out value="${elem.prescription.clientNote}"/>
                            <ctg:select tagId="clientNote${status.count}" elem="${elem.prescription.clientNote}"/>
                        </td>
                        <td id="agreedDate${status.count}">
                            <c:out value="${elem.prescription.agreedDate}"/>
                            <ctg:select tagId="agreedDate${status.count}" elem="${elem.prescription.agreedDate}"/>
                        </td>
                        <td>
                            <a onclick="addIdToFieldPrescription('idAct', 'idAct1', 'idHid', 'idSubm',
                                ${elem.prescription.idOrder}, ${elem.trainer.idTrainer},
                                    '<fmt:message key="body.create"/>',
                                    '<fmt:message key="body.update"/>')">
                                U
                            </a>
                        </td>
                        <td>
                            <a href="${pageContext.servletContext.contextPath}
                            /controller?command=prescription_delete&idOrder=${elem.prescription.idOrder}&idTrainer=${elem.trainer.idTrainer}">
                                D
                            </a>
                        </td>
                    </tr>
                </c:forEach>

                <jsp:useBean id="readAllOrder" scope="request" type="java.util.List"/>
                <jsp:useBean id="readAllTrainer" scope="request" type="java.util.List"/>
                <tr>
                    <td>
                        <select form="createForm" name="orderId">
                            <c:forEach var="elem" items="${readAllOrder}" varStatus="status">
                                <option value="${elem.idOrder}">${elem.idOrder}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <select form="createForm" name="trainerId">
                            <c:forEach var="elem" items="${readAllTrainer}" varStatus="status">
                                <option value="${elem.idTrainer}">${elem.name} ${elem.lastName}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td><input form="createForm" type="text" name="date"></td>
                    <td><input form="createForm" type="text" name="weeks"></td>
                    <td><input form="createForm" type="text" name="trainingsWeek"></td>
                    <td><input form="createForm" type="text" name="trainerNote"></td>
                    <td><input form="createForm" type="text" name="clientNote"></td>
                    <td><input form="createForm" type="text" name="agreed"></td>
                    <td colspan="2"><input form="createForm" id="idSubm" type="submit"
                                           value="<fmt:message key="body.create"/>"></td>
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