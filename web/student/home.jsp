<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Dashboard</title>
</head>
<body>
    <h1 style="color: orange;">Welcome STUDENT: ${sessionScope.account.fullName}</h1>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
</body>
</html>