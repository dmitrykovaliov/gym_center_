<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="resource.locale"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome</title></head>
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
    <div style="margin: auto;">
        <ul class="nav navbar-nav">
            <li><a href="#some"><fmt:message key="body.create"/></a></li>
            <li><a href="/controller?command=readActivity"><fmt:message key="body.read"/></a></li>
            <li><a href="#some"><fmt:message key="body.update"/></a></li>
            <li><a href="#some"><fmt:message key="body.delete"/></a></li>
        </ul>
    </div>
    <br>
    <br>
    <div style="width:400px">
        <form style="border: 1px solid black; border-radius: 4px; background: gainsboro"
              action="controller" method="get" enctype="multipart/form-data">

            <input type="hidden" name="command" value="updateActivity">

            <fieldset style="border: 1px solid black; border-radius: 4px; margin: 25px 25px 30px 25px">
                ID: <input type="number" name="idActivity" min="1" max="2147483647" title="enter value between 1&2147483647"> <br>
                Name: <input type="text" name="actName" pattern=".{, 200}" title="enter name"> <br>
                Price: <input type="text" name="actPrice" pattern="\d{,12}[\.\,]d{,2}" title="enter correct number"> <br>
                Note: <input type="text" name="actNote" pattern=".{, 200}" title="enter note"> <br>
                <input type="submit" value="UPDATE">
            </fieldset>

        </form>

    </div>
    <div style="width:400px">
        <form style="border: 1px solid black; border-radius: 4px; background: gainsboro"
              action="controller" method="get" enctype="multipart/form-data">

            <input type="hidden" name="command" value="createActivity">

            <fieldset style="border: 1px solid black; border-radius: 4px; margin: 25px 25px 30px 25px">
                Name: <input type="text" name="actName" pattern=".{, 200}" title="enter name"> <br>
                Price: <input type="text" name="actPrice" pattern="\d{,12}[\.\,]d{,2}" title="enter correct number"> <br>
                Note: <input type="text" name="actNote" pattern=".{, 200}" title="enter note"> <br>
                <input type="submit" value="CREATE">
            </fieldset>

        </form>

    </div>

    <div style="width:400px">
        <form style="border: 1px solid black; border-radius: 4px; background: gainsboro"
              action="controller" method="get" enctype="multipart/form-data">

            <input type="hidden" name="command" value="deleteActivity">

            <fieldset style="border: 1px solid black; border-radius: 4px; margin: 25px 25px 30px 25px">
                ID: <input type="number" name="idActivity" min="1" max="2147483647" title="enter value between 1&2147483647"> <br>
                <input type="submit" value="DELETE">
            </fieldset>

        </form>

    </div>


    <div>
    <table>
        <caption><fmt:message key="table.activity.head"/></caption>
        <thead>
            <tr>
                <th><fmt:message key="table.activity.id"/></th>
                <th><fmt:message key="table.activity.name"/></th>
                <th><fmt:message key="table.activity.price"/></th>
                <th><fmt:message key="table.activity.note"/></th>
            </tr>
        </thead>
        <tbody>
        <%--<c:forEach var="elem" items="activityTable">--%>
            <%--<tr>--%>
                <%--<td><c:out value="${elem.idActivity}"/></td>--%>
                <%--<td><c:out value="${elem.name}"/></td>--%>
                <%--<td><c:out value="${elem.price}"/></td>--%>
                <%--<td><c:out value="${elem.note}"/></td>--%>
            <%--</tr>--%>
        <%--</c:forEach>--%>
        </tbody>
    </table>
</div>

<br>
<br>
</section>

<footer>
    <c:import url="../general/footer.jsp"/>
</footer>
</body>

</html>