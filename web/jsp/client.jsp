<%--
  Created by IntelliJ IDEA.
  User: dk
  Date: 6/28/18
  Time: 1:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="ru_RU" scope="session" />
<fmt:bundle basename="resource.locale" prefix = "client." >


<html>
<head>
    <title>Upload page</title>
    <link rel="stylesheet" href="style.css">
    <style>
        table {
            border: 2px solid black;
            border-collapse: collapse;

            width: 100%;
        }

        td, th {
            text-align: left;
            padding: 4px;
            border: 1px solid gray;

        }

        th {
            text-align: center;
        }

    </style>
</head>
</head>
<body>

<div>
    <h2><fmt:message key="head"/></h2>

    <table>

        <tr>
            <th><fmt:message key="id"/></th>
            <th><fmt:message key="name"/></th>
            <th><fmt:message key="lastname"/></th>
            <th><fmt:message key="phone"/></th>
            <th><fmt:message key="email"/></th>
            <th><fmt:message key="personaldata"/></th>
            <th><fmt:message key="user"/></th>
        </tr>

        <tbody>

    <%--<c:forEach var="elem" items="${first}" varStatus="status">--%>
        <%--<tr>--%>
            <%--<td><c:out value="${elem.idMedicin}"/></td>--%>


        <%--</tr>--%>
    <%--</c:forEach>--%>

        </tbody>
    </table>
</div>
</body>
</html>
