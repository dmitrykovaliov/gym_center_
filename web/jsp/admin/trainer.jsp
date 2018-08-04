<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : ''}"/>
<fmt:setBundle basename="resource.locale"/>

<html>
<head>
    <title>Trainer</title>
    <%--<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">--%>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style><%@include file="../../css/style.css"%></style>
    <script type="text/javascript" src="../../js/script.js"></script>
</head>

<body id="myPage" data-spy="scroll" data-target=".navbar" data-offset="50">

    <c:set var="role" value="${sessionScope.role}"/>


    <c:choose>
        <c:when test="${role == 'ADMIN'}">
            <%@include file="../admin/jspf/header.jspf"%>
        </c:when>
        <c:when test="${role == 'TRAINER'}">
            <%@include file="../admin/jspf/header.jspf"%>
        </c:when>
        <c:when test="${role == 'CLIENT'}">
            <%@include file="../admin/jspf/header.jspf"%>
        </c:when>
    </c:choose>
    <br>
    <br>
    <h3><fmt:message key="table.trainer.head"/></h3>
    <div>
        <form id="createForm" name="createForm" method="get" action="controller">
            <input id="idHid" type="hidden" name="command" value="trainer_create">

            <table class="greyGridTable">
                <thead>
                <tr>
                    <th><fmt:message key="table.trainer.id"/></th>
                    <th><fmt:message key="table.trainer.name"/></th>
                    <th><fmt:message key="table.trainer.lastName"/></th>
                    <th><fmt:message key="table.trainer.phone"/></th>
                    <th><fmt:message key="table.trainer.personalData"/></th>
                    <th colspan="2"></th>
                </tr>
                </thead>
                <tbody>
                <jsp:useBean id="readAll" scope="request" type="java.util.List"/>
                <c:forEach var="elem" items="${readAll}" varStatus="status">
                    <tr>
                        <td><c:out value="${elem.idTrainer}"/>
                        </td>
                        <td id="name${status.count}">
                            <c:out value="${elem.name}"/>
                            <c:if test="${elem.name == null}">
                                <script>document.getElementById("name${status.count}").style.backgroundColor = "#ebe6d3"</script>
                            </c:if>
                        </td>
                        <td id="lastName${status.count}"><c:out value="${elem.lastName}"/>
                            <c:if test="${elem.lastName == null}">
                                <script>document.getElementById("lastName${status.count}").style.backgroundColor = "#ebe6d3"</script>
                            </c:if>
                        </td>
                        <td id="phone${status.count}"><c:out value="${elem.phone}"/>
                            <c:if test="${elem.phone == null}">
                                <script>document.getElementById("phone${status.count}").style.backgroundColor = "#ebe6d3"</script>
                            </c:if>
                        </td>
                        <td id="personalData${status.count}"><c:out value="${elem.personalData}"/>
                            <c:if test="${elem.personalData == null}">
                                <script>document.getElementById("personalData${status.count}").style.backgroundColor = "#ebe6d3"</script>
                            </c:if>
                        </td>

                        <td>
                            <a onclick="addIdToFieldTrainer('idAct', 'idHid', 'idSubm', ${elem.idTrainer}, '<fmt:message
                                    key="body.create"/>',
                                    '<fmt:message key="body.update"/>')">
                                U
                            </a>
                        </td>
                        <td>
                            <a href="${pageContext.servletContext.contextPath}/controller?command=trainer_delete&id=${elem.idTrainer}">
                                D
                            </a>
                        </td>
                    </tr>
                </c:forEach>

                <tr>
                    <td><input form="createForm" type="text" id="idAct" name="id" readonly></td>
                    <td><input form="createForm" type="text" name="name"></td>
                    <td><input form="createForm" type="text" name="lastName"></td>
                    <td><input form="createForm" type="text" name="phone"></td>
                    <td><input form="createForm" type="text" name="personalData"></td>
                    <td colspan="2"><input form="createForm" id="idSubm" type="submit"
                                           value="<fmt:message key="body.create"/>"></td>
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