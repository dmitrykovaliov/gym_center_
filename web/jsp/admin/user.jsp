<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="selecttag" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : ''}"/>
<fmt:setBundle basename="resource.locale"/>

<html>
<head>
    <title>User</title>
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
    <h3><fmt:message key="table.user.head"/></h3>
        <form id="createForm" name="createForm" method="get" action="controller">
            <input id="idHid" type="hidden" name="command" value="user_create">

            <table class="greyGridTable">
                <thead>
                <tr>
                    <th><fmt:message key="table.user.id"/></th>
                    <th><fmt:message key="table.user.login"/></th>
                    <th><fmt:message key="table.user.pass"/></th>
                    <th><fmt:message key="table.user.role"/></th>
                    <th colspan="2"></th>
                </tr>
                </thead>
                <tbody>
                <jsp:useBean id="readAll" scope="request" type="java.util.List"/>
                <c:forEach var="elem" items="${readAll}" varStatus="status">
                    <tr>
                        <td>
                            <c:out value="${elem.idUser}"/>
                        </td>
                        <td id="login${status.count}">
                            <c:out value="${elem.login}"/>
                            <ctg:select tagId="login${status.count}" elem="${elem.login}"/>
                        </td>
                        <td id="pass${status.count}">
                            <c:out value="${elem.pass}"/>
                            <ctg:select tagId="pass${status.count}" elem="${elem.pass}"/>
                        </td>
                        <td id="role${status.count}">
                            <c:out value="${elem.role}"/>
                            <ctg:select tagId="role${status.count}" elem="${elem.role}"/>
                        </td>
                        <td>
                            <a onclick="addIdToFieldUser('idAct', 'idHid', 'idSubm', ${elem.idUser},
                                    '<fmt:message key="body.create"/>',
                                    '<fmt:message key="body.update"/>')">
                                U
                            </a>
                        </td>
                        <td>
                            <a href="${pageContext.servletContext.contextPath}
                            /controller?command=user_delete&id=${elem.idUser}">
                                D
                            </a>
                        </td>
                    </tr>
                </c:forEach>

                <tr>
                    <td><input form="createForm" type="text" id="idAct" name="id" readonly></td>
                    <td><input form="createForm" type="text" name="login"></td>
                    <td><input form="createForm" type="password" name="pass"></td>
                    <td><input form="createForm" type="text" name="role"></td>
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