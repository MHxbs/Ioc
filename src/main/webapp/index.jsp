<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2018/3/27
  Time: 22:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>index</title>
</head>
<body>
<form action="/dispatcher/studentByMethod" , method="post">
    name:<input type="text" name="name"><br>
    age:<input type="text" name="age"><br>
    gender:<input type="text" name="gender"><br>
    stu_num:<input type="text" name="stu_num"><br>
    address:<input type="text" name="address"><br>
    mail:<input type="text" name="mail"><br>
    type:<input type="text" name="type"><br>
    <input type="submit"><br>
</form>
</body>
</html>
