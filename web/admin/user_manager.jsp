<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý tài khoản</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>
    <div class="layout">
        <aside class="sidebar">
            <div class="brand">
                <span class="brand-title">Hệ thống Feedback</span>
                <span class="brand-subtitle">Quản trị hệ thống</span>
            </div>
            <nav class="side-nav">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/home.jsp">Trang chủ</a>
                <a class="nav-link active" href="${pageContext.request.contextPath}/admin/users">Tài khoản</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/courses">Môn học</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/class-sections">Lớp học phần</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/enrollments">Ghi danh</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/feedback-forms">Khảo sát</a>
                <a class="nav-link logout" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </nav>
        </aside>

        <main class="main">
            <div class="topbar">
                <div class="page-title">
                    <h1>Quản lý tài khoản</h1>
                    <p>Tạo tài khoản và phân quyền cho quản trị viên, giảng viên, sinh viên.</p>
                </div>
            </div>

            <c:if test="${not empty requestScope.error}">
                <div class="alert alert-danger">${requestScope.error}</div>
            </c:if>

            <div class="card">
                <h2>Thêm tài khoản mới</h2>
                <form action="${pageContext.request.contextPath}/admin/users" method="POST">
                    <div class="form-grid">
                        <div class="form-row">
                            <label>Tên đăng nhập</label>
                            <input type="text" name="username" required>
                        </div>
                        <div class="form-row">
                            <label>Mật khẩu</label>
                            <input type="password" name="password" required>
                        </div>
                        <div class="form-row">
                            <label>Họ và tên</label>
                            <input type="text" name="fullname" required>
                        </div>
                        <div class="form-row">
                            <label>Email</label>
                            <input type="email" name="email">
                        </div>
                        <div class="form-row">
                            <label>Vai trò</label>
                            <select name="roleId">
                                <option value="1">Quản trị viên</option>
                                <option value="2">Giảng viên</option>
                                <option value="3">Sinh viên</option>
                            </select>
                        </div>
                        <div class="form-row" style="align-self:end;">
                            <button type="submit" class="btn btn-primary">Thêm tài khoản</button>
                        </div>
                    </div>
                </form>
            </div>

            <div class="card">
                <div class="card-header">
                    <h2>Danh sách tài khoản</h2>
                </div>
                <div class="table-wrap">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Tên đăng nhập</th>
                                <th>Họ và tên</th>
                                <th>Email</th>
                                <th>Vai trò</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${userList}" var="u">
                                <tr>
                                    <td>${u.userId}</td>
                                    <td>${u.username}</td>
                                    <td>${u.fullName}</td>
                                    <td>${u.email}</td>
                                    <td>
                                        <c:if test="${u.roleId == 1}"><span class="badge badge-warning">Quản trị viên</span></c:if>
                                        <c:if test="${u.roleId == 2}"><span class="badge badge-success">Giảng viên</span></c:if>
                                        <c:if test="${u.roleId == 3}"><span class="badge">Sinh viên</span></c:if>
                                    </td>
                                    <td>
                                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/users?action=edit&id=${u.userId}">Sửa</a>
                                        <c:if test="${u.userId != sessionScope.account.userId}">
                                            <a class="btn btn-danger" href="${pageContext.request.contextPath}/admin/users?action=delete&id=${u.userId}"
                                               onclick="return confirm('Bạn có chắc muốn xóa tài khoản này?');">Xóa</a>
                                        </c:if>
                                        <c:if test="${u.userId == sessionScope.account.userId}">
                                            <span class="badge badge-success">Tài khoản hiện tại</span>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>
    </div>
</body>
</html>
