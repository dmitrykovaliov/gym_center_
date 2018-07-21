
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : ''}"/>
<fmt:setBundle basename="resource.locale"/>


<html lang="en">
<head>
    <!-- Theme Made By www.w3schools.com - No Copyright -->
    <title>Bootstrap Theme The Band</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="../../js/script.js"></script>

</head>
<body id="myPage" data-spy="scroll" data-target=".navbar" data-offset="50">

<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#myPage"><b>GYM24</b></a>
            <div style="float:left">
                <a href="${pageContext.request.contextPath}${sessionScope.locale == 'ru_RU' ? '/controller?command=en': ''}">EN</a>/
                <a href="${pageContext.request.contextPath}${sessionScope.locale != 'ru_RU' ? '/controller?command=ru': ''}">RU</a>
            </div>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="${pageContext.servletContext.contextPath}/controller?command=activity_read">
                        <fmt:message key="menu.activity"/>
                    </a>
                </li>
                <li><a href="#band"><fmt:message key="menu.client"/></a></li>
                <li><a href="#tour"><fmt:message key="menu.order"/></a></li>
                <li><a href="#tour"><fmt:message key="menu.payment"/></a></li>
                <li><a href="#tour"><fmt:message key="menu.trainer"/></a></li>
                <li><a href="#tour"><fmt:message key="menu.prescription"/></a></li>
                <li><a href="#tour"><fmt:message key="menu.schedule"/></a></li>
                <li><a href="#tour"><fmt:message key="menu.training"/></a></li>
                <li><a href="#user"><fmt:message key="menu.user"/></a></li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown">
                        <div>${sessionScope.role}
                        <span class="caret"></span></div>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Something1</a></li>
                        <li><a href="#">Something2</a></li>
                        <li><a href="${pageContext.request.contextPath}/controller?command=logout">LogOut</a></li>
                    </ul>
                </li>

            </ul>
        </div>
    </div>
</nav>
</body>
</html>
