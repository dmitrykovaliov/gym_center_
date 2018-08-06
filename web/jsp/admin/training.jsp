<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="selecttag" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : ''}"/>
<fmt:setBundle basename="resource.locale"/>

<html>
<head>
    <title>Training</title>
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
            <%@include file="../trainer/jspf/header.jspf" %>
        </c:when>
        <c:when test="${role == 'CLIENT'}">
            <%@include file="../client/jspf/header.jspf" %>
        </c:when>
    </c:choose>
</header>
    <br>
    <br>
<h3><fmt:message key="table.training.head"/></h3>
        <form id="createForm" name="createForm" method="get" action="controller">
            <input id="idHid" type="hidden" name="command" value="training_create">

            <table class="greyGridTable">
                <thead>
                <tr>
                    <th><fmt:message key="table.training.id"/></th>
                    <th><fmt:message key="table.training.date"/></th>
                    <th><fmt:message key="table.training.startTime"/></th>
                    <th><fmt:message key="table.training.endTime"/></th>
                    <th><fmt:message key="table.training.visited"/></th>
                    <th><fmt:message key="table.training.clientNote"/></th>
                    <th><fmt:message key="table.training.trainterNote"/></th>
                    <th><fmt:message key="table.training.idOrder"/></th>
                    <th><fmt:message key="table.training.idTrainer"/></th>
                    <th colspan="2"></th>
                </tr>
                </thead>
                <tbody>
                <jsp:useBean id="readAll" scope="request" type="java.util.List"/>
                <jsp:useBean id="readTrainer" scope="request" type="java.util.Set"/>
                <c:forEach var="elem" items="${readAll}" varStatus="status">
                    <tr>
                        <td>
                            <c:out value="${elem.idTraining}"/>
                        </td>

                        <td id="date${status.count}">
                            <c:out value="${elem.date}"/>
                            <ctg:select tagId="date${status.count}" elem="${elem.date}"/>
                        </td>
                        <td id="startTime${status.count}">
                            <c:out value="${elem.startTime}"/>
                            <ctg:select tagId="startTime${status.count}" elem="${elem.startTime}"/>
                        </td>
                        <td id="endTime${status.count}">
                            <c:out value="${elem.endTime}"/>
                            <ctg:select tagId="endTime${status.count}" elem="${elem.endTime}"/>
                        </td>
                        <td id="visited${status.count}">
                            <c:if test="${elem.visited == 'true'}">
                                <c:out value="visited"/>
                            </c:if>
                            <ctg:select tagId="endTime${status.count}" elem="${elem.visited}"/>
                        </td>
                        <td id="clientNote${status.count}">
                            <c:out value="${elem.clientNote}"/>
                            <ctg:select tagId="clientNote${status.count}" elem="${elem.clientNote}"/>
                        </td>
                        <td id="trainerNote${status.count}">
                            <c:out value="${elem.trainerNote}"/>
                            <ctg:select tagId="trainerNote${status.count}" elem="${elem.trainerNote}"/>
                        </td>
                        <td id="idOrder${status.count}">
                            <c:out value="${elem.idOrder}"/>
                            <ctg:select tagId="idOrder${status.count}" elem="${elem.idOrder}"/>
                        </td>
                        <td id="idTrainer${status.count}">
                            <c:forEach var="elemTrainer" items="${readTrainer}" varStatus="status">
                                <c:if test="${elem.idTrainer == elemTrainer.idTrainer}">
                                    <c:set value="${elemTrainer.name}" var="name"/>
                                    <c:set value="${elemTrainer.lastName}" var="lastName"/>
                                </c:if>
                            </c:forEach>
                            <c:out value="${name} ${lastName}"/>
                            <ctg:select tagId="idTrainer${status.count}" elem="${name} ${lastName}"/>
                        </td>
                        <td>
                            <a onclick="addIdToFieldTraining('idAct', 'idHid', 'idSubm', ${elem.idTraining},
                                    '<fmt:message key="body.create"/>',
                                    '<fmt:message key="body.update"/>')">
                                U
                            </a>
                        </td>
                        <td>
                            <a href="${pageContext.servletContext.contextPath}
                            /controller?command=training_delete&id=${elem.idTraining}">
                                D
                            </a>
                        </td>
                    </tr>
                </c:forEach>

                <jsp:useBean id="readAllOrder" scope="request" type="java.util.List"/>
                <jsp:useBean id="readAllTrainer" scope="request" type="java.util.List"/>
                <tr>
                    <td><input form="createForm" type="text" id="idAct" name="id" readonly></td>
                    <td><input form="createForm" type="text" placeholder="yyyy/mm/dd" name="date"></td>
                    <td><input form="createForm" type="text" placeholder="hh:mm" name="startTime"></td>
                    <td><input form="createForm" type="text" placeholder="hh:mm" name="endTime"></td>
                    <td><input form="createForm" type="text" name="visited"></td>
                    <td><input form="createForm" type="text" name="clientNote"></td>
                    <td><input form="createForm" type="text" name="trainerNote"></td>
                    <td>
                        <select form="createForm" name="orderId">
                            <c:forEach var="elem" items="${readAllOrder}" varStatus="status">
                                <option value="${elem.idOrder}">${elem.idOrder}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <select form="createForm" name="trainerId">
                            <option></option>
                            <c:forEach var="elem" items="${readAllTrainer}" varStatus="status">
                                <option value="${elem.idTrainer}">${elem.name} ${elem.lastName}</option>
                            </c:forEach>
                        </select>
                    </td>
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