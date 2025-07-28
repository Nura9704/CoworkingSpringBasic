<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Spaces</title></head>
<body>
<h2>All Spaces</h2>
<c:forEach var="s" items="${spaces}">
    <p>${s}</p>
</c:forEach>
<a href="/">Back</a>
</body>
</html>
