<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : ''}"/>
<fmt:setBundle basename="resource.locale"/>

<html>
<head>
    <title>Client</title>
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
<div>

    <div style="width: 600px">

        <form style="border: 1px solid black; border-radius: 4px; background: gainsboro"
              action="${pageContext.request.contextPath}/picture" method="post" enctype="multipart/form-data">

            <input type="hidden" name="command" value="client_upload">
            <input type="hidden" name="id" value="${param.id}">

            <fieldset style="border: 1px solid black; border-radius: 4px; background: grey; margin: 25px 25px 30px 25px">
                <legend style="color: white; background: red; border-radius: 4px; border: 1px solid black">Upload picture
                </legend>

                <div>
                    <input type="file" id="file" name="file">
                </div>
                <div>
                    <input type="submit" value="Submit">
                </div>
            </fieldset>
        </form>
    </div>

    <%--<footer>--%>
    <%--<c:import url="../general/footer.jsp"/>--%>
    <%--</footer>--%>
</body>

</html>