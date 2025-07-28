<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Add User</title></head>
<body>
<h2>Add User</h2>
<form method="post" action="addUser">
    Name: <input type="text" name="name"/><br>
    Is Admin: <input type="checkbox" name="isAdmin" value="true"/><br>
    <input type="submit" value="Add"/>
</form>
<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>
<c:if test="${not empty success}">
    <p style="color:green;">${success}</p>
</c:if>
<a href="/">Back</a>
</body>
</html>
