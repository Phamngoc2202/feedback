<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập hệ thống</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>
    <div class="auth-page">
        <section class="auth-info">
            <span class="brand-subtitle">Hệ thống Feedback</span>
            <h1>Quản lý đánh giá giảng viên</h1>
            <p>Không gian dành cho sinh viên gửi phản hồi, giảng viên theo dõi kết quả và quản trị viên quản lý dữ liệu học tập.</p>
        </section>

        <section class="auth-panel">
            <div class="auth-card">
                <h1>Đăng nhập</h1>
                <p>Nhập tài khoản để tiếp tục sử dụng hệ thống.</p>

                <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-danger">${requestScope.error}</div>
                <% } %>

                <form action="${pageContext.request.contextPath}/login" method="POST">
                    <div class="form-row">
                        <label>Tên đăng nhập</label>
                        <input type="text" name="username" value="${requestScope.username}" required autofocus>
                    </div>

                    <div class="form-row">
                        <label>Mật khẩu</label>
                        <input type="password" name="password" required>
                    </div>

                    <button type="submit" class="btn btn-primary" style="width:100%;">Đăng nhập</button>
                </form>
            </div>
        </section>
    </div>
</body>
</html>
