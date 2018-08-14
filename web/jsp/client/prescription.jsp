<%@ page contentType="text/html; charset=UTF-8" %>
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

    <style>
        <%@include file="/css/style.css" %>
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
<h3 style="margin-left: 30px"><fmt:message key="table.prescription.head"/></h3>
        <form id="createForm" name="createForm" method="get" action="controller">
            <input type="hidden" name="formId" value="${sessionScope.formSessionId}">
            <input type="hidden" name="command" value="prescription_update_client">

            <table class="greyGridTable">
                <thead>
                <tr>
                    <th><fmt:message key="table.prescription.number"/></th>
                    <th><fmt:message key="table.prescription.date"/></th>
                    <th><fmt:message key="table.prescription.weeks"/></th>
                    <th><fmt:message key="table.prescription.trainingsPerWeek"/></th>
                    <th><fmt:message key="table.prescription.trainerNote"/></th>
                    <th><fmt:message key="table.prescription.clientNote"/></th>
                    <th><fmt:message key="table.prescription.agreed"/></th>
                    <th><fmt:message key="table.prescription.order"/></th>
                    <th><fmt:message key="table.prescription.trainer"/></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <jsp:useBean id="readAll" scope="request" type="java.util.List"/>
                <jsp:useBean id="readTrainer" scope="request" type="java.util.List"/>
                <c:forEach var="elem" items="${readAll}" varStatus="status">
                    <tr>
                        <td>
                            <c:out value="${status.count}"/>
                        </td>
                        <td id="date${status.count}">
                            <c:out value="${elem.date}"/>
                            <ctg:select tagId="date${status.count}" elem="${elem.date}"/>
                        </td>
                        <td id="weekQuantity${status.count}">
                            <c:out value="${elem.weekQuantity}"/>
                            <ctg:select tagId="weekQuantity${status.count}" elem="${elem.weekQuantity}"/>
                        </td>
                        <td id="trainingsWeek${status.count}">
                            <c:out value="${elem.trainingsWeek}"/>
                            <ctg:select tagId="trainingsWeek${status.count}" elem="${elem.trainingsWeek}"/>
                        </td>
                        <td id="trainerNote${status.count}">
                            <c:out value="${elem.trainerNote}"/>
                            <ctg:select tagId="trainerNote${status.count}" elem="${elem.trainerNote}"/>
                        </td>
                        <td id="clientNote${status.count}">
                            <c:out value="${elem.clientNote}"/>
                            <ctg:select tagId="clientNote${status.count}" elem="${elem.clientNote}"/>
                        </td>
                        <td id="agreedDate${status.count}">
                            <c:out value="${elem.agreedDate}"/>
                            <ctg:select tagId="agreedDate${status.count}" elem="${elem.agreedDate}"/>
                        </td>
                        <td>
                            <c:out value="${elem.idOrder}"/>
                        </td>
                        <td id="idTrainer${status.count}">

                            <c:set value="${readTrainer.get(status.count-1).name}" var="name"/>
                            <c:set value="${readTrainer.get(status.count-1).lastName}" var="lastName"/>

                            <c:out value="${name} ${lastName}"/>
                            <ctg:select tagId="idTrainer${status.count}" elem="${name} ${lastName}"/>
                        </td>
                        <td>
                            <a class="onClickCursor" onclick="fillPrescriptionFormClient('idCount', ${status.count}, 'idOrder', 'idTrainer',
                                'idSubmit',
                                ${elem.idOrder},
                                ${elem.idTrainer})">
                                U
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td><input form="createForm" type="text" id="idCount" name="count" readonly></td>
                    <td><input form="createForm" type="text" name="date" placeholder="yyyy-mm-dd" hidden></td>
                    <td><input form="createForm" type="text" name="weeks" hidden></td>
                    <td><input form="createForm" type="text" name="trainingsWeek" hidden></td>
                    <td><input form="createForm" type="text" name="trainerNote" hidden></td>
                    <td><input form="createForm" type="text" name="clientNote"></td>
                    <td><input form="createForm" type="text" name="agreed" placeholder="yyyy-mm-dd"></td>
                    <td>
                        <input form="createForm" id="idOrder" name="orderId" hidden>
                    </td>
                    <td>
                        <input form="createForm" id="idTrainer" name="trainerId" hidden>
                    </td>
                    <td><input form="createForm" id="idSubmit" type="submit"
                                           value="<fmt:message key="body.update"/>" hidden></td>
                </tr>
                </tbody>
            </table>

        </form>
        <br>
        <div class="leftMessage">${error}</div>

</body>

</html>