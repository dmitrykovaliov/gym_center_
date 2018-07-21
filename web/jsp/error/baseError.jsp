<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8"/>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
    <title>Error page</title>
</head>

<body>
<div>
    <h2>Status code: ${pageContext.errorData.statusCode} </h2>
    <div>Request from: ${pageContext.errorData.requestURI} is failed</div>
    <div>Servlet name: ${pageContext.errorData.servletName}</div>
    <div>Exception: ${pageContext.exception}</div>
    <div>Exception message: ${pageContext.exception.message}</div>
    <div>Exception message: ${pageContext.exception.stackTrace}</div>
</div>
</body>
</html>
