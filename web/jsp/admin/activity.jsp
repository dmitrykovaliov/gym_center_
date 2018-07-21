<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : ''}"/>
<fmt:setBundle basename="resource.locale"/>

<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <%--<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">--%>

    <style><%@include file="/css/style.css"%></style>

    <script type="text/javascript" src="../../js/script.js"></script>
</head>
<body>
<header>

    <c:set var="role" value="${sessionScope.role}"/>


    <c:choose>
        <c:when test="${role == 'ADMIN'}">
            <c:import url="header.jsp"/>
        </c:when>
        <c:when test="${role == 'TRAINER'}">
            <c:import url="header.jsp"/>
        </c:when>
        <c:otherwise>
            <c:import url="header.jsp"/>
        </c:otherwise>
    </c:choose>
</header>
<section>
    <br>
    <br>
    <div>
        <form id="createForm" name="createForm" method="get" action="controller">
            <input id="idHid" type="hidden" name="command" value="activity_create">

            <table class="greyGridTable">
                <thead>
                <tr>
                    <th><fmt:message key="table.activity.id"/></th>
                    <th><fmt:message key="table.activity.name"/></th>
                    <th><fmt:message key="table.activity.price"/></th>
                    <th><fmt:message key="table.activity.note"/></th>
                    <th colspan="2"></th>
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
                            <c:if test="${elem.name == null}">
                                <script>document.getElementById("name${status.count}").style.backgroundColor = "#ebe6d3"</script>
                            </c:if>
                        </td>
                        <td id="price${status.count}"><c:out value="${elem.price}"/>
                            <c:if test="${elem.price == null}">
                                <script>document.getElementById("price${status.count}").style.backgroundColor = "#ebe6d3"</script>
                            </c:if>
                        </td>
                        <td id="note${status.count}"><c:out value="${elem.note}"/>
                            <c:if test="${elem.note == null}">
                                <script>document.getElementById("note${status.count}").style.backgroundColor = "#ebe6d3"</script>
                            </c:if>
                        </td>
                        <td>
                            <a onclick="addIdToField('idAct', 'idHid', 'idSubm', ${elem.idActivity}, '<fmt:message
                                    key="body.create"/>',
                                    '<fmt:message key="body.update"/>')">
                                U
                            </a>
                        </td>
                        <td>
                            <a href="${pageContext.servletContext.contextPath}/controller?command=activity_delete&id=${elem.idActivity}">
                                D
                            </a>
                        </td>

                            <script>document.getElementById("firstCol").style.backgroundColor = "green"</script>


                            <script>document.getElementById("secondCol").style.backgroundColor = "green"</script>


                            <script>document.getElementById("thirdCol").style.backgroundColor = "green"</script>

                    </tr>
                </c:forEach>

                <tr>
                    <td><input form="createForm" type="text" id="idAct" name="id" readonly></td>
                    <td><input form="createForm" type="text" name="name"></td>
                    <td><input form="createForm" type="text" name="price"></td>
                    <td><input form="createForm" type="text" name="note"></td>
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

        <div class="leftMessage">${done}</div>
        <br>
       <div class="leftMessage">${error}</div>



        <%--<footer>--%>
        <%--<c:import url="../general/footer.jsp"/>--%>
        <%--</footer>--%>
</body>

</html>