
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>Login</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat" rel="stylesheet" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        <%@include file="../../css/style.css" %>
    </style>
</head>
<body>

<form class="ui-form" name="loginForm" method="post" action="controller">

    <input type="hidden" name="command" value="login"/>

    <h3>GYM</h3>
    <div class="form-row">
        <input id="login" type="text" name="login" required autocomplete="off"/>
        <label for="login">Login</label>
    </div>
    <div class="form-row">
        <input id="pass" type="password" name="pass" required autocomplete="off"/>
        <label for="pass">Password</label>
    </div>

    ${error}
    <br>
    <input id="sId" type="submit" value="LogIn"/>
</form>
</body>
</html>