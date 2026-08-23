<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sửa tài khoản</title>
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
                    <h1>Sửa tài khoản</h1>
                    <p>Cập nhật thông tin đăng nhập và vai trò người dùng.</p>
                </div>
                <div class="top-actions">
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/users">Quay lại</a>
                </div>
            </div>

            <div class="card">
                <form action="${pageContext.request.contextPath}/admin/users" method="POST">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="userId" value="${userEdit.userId}">

                    <div class="form-grid">
                        <div class="form-row">
                            <label>Tên đăng nhập</label>
                            <input type="text" name="username" value="${userEdit.username}" required>
                        </div>
                        <div class="form-row">
                            <label>Mật khẩu</label>
                            <input type="text" name="password" value="${userEdit.password}" required>
                        </div>
                        <div class="form-row">
                            <label>Họ và tên</label>
                            <input type="text" name="fullname" value="${userEdit.fullName}" required>
                        </div>
                        <div class="form-row">
                            <label>Email</label>
                            <input type="email" name="email" value="${userEdit.email}">
                        </div>
                        <div class="form-row">
                            <label>Vai trò</label>
                            <select name="roleId">
                                <option value="1" <c:if test="${userEdit.roleId == 1}">selected</c:if>>Quản trị viên</option>
                                <option value="2" <c:if test="${userEdit.roleId == 2}">selected</c:if>>Giảng viên</option>
                                <option value="3" <c:if test="${userEdit.roleId == 3}">selected</c:if>>Sinh viên</option>
                            </select>
                        </div>
                    </div>

                    <div class="top-actions" style="justify-content:flex-start;margin-top:8px;">
                        <button type="submit" class="btn btn-primary">Cập nhật</button>
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/users">Hủy</a>
                    </div>
                </form>
            </div>
        </main>
    </div>
</body>
</html>
