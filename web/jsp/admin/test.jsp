<%--
  Created by IntelliJ IDEA.
  User: dk
  Date: 7/16/18
  Time: 3:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tag attributes</title>
    <meta charset="UTF-8">
</head>
<body>
<h3> Form validation by tag attributes</h3>
<form>
	  <pre style="font-size: 16px;">
	First name: <input type="text" name="fname"> (without validation)<br>
	Last name : <input type="text" name="lname" disabled> (disabled)<br>
	Username  : <input type="text" name="usrname" required
                       title="Fill field obligatory"> (required)<br>
	Birthdate : <input type="date" name="bday" min="1900-01-01" title="Input date after 01/01/1900"> (min &amp; type=&quot;date&quot;)<br>
	Quantity  : <input type="number" name="quantity" min="1" max="5" title="Value between 1&5">  (max &amp; min  &amp; type=&quot;number&quot;)<br>
	Country   : <input type="text" name="country_code" pattern="[A-Z]{3}" title="Three caps letter country code"> (pattern) <br><br>
	  <input type="submit" value="Submit"></pre>
</form>
</body>
</html>
