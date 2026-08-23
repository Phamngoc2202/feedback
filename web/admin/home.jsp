<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang quản trị</title>
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
                <a class="nav-link active" href="${pageContext.request.contextPath}/admin/home.jsp">Trang chủ</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/users">Tài khoản</a>
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
                    <h1>Trang quản trị</h1>
                    <p>Xin chào, ${sessionScope.account.fullName}. Chọn một mục bên trái để bắt đầu quản lý dữ liệu.</p>
                </div>
            </div>

            <div class="stats-grid">
                <div class="stat">
                    <span>Tài khoản</span>
                    <strong>Người dùng</strong>
                </div>
                <div class="stat">
                    <span>Đào tạo</span>
                    <strong>Môn học</strong>
                </div>
                <div class="stat">
                    <span>Lớp</span>
                    <strong>Học phần</strong>
                </div>
                <div class="stat">
                    <span>Khảo sát</span>
                    <strong>Đánh giá</strong>
                </div>
            </div>

            <div class="card">
                <div class="card-header">
                    <div>
                        <h2>Chức năng quản trị</h2>
                        <p class="muted">Các module chính đã được tách rõ để thao tác nhanh và dễ kiểm tra dữ liệu.</p>
                    </div>
                </div>
                <div class="table-wrap">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Module</th>
                                <th>Mục đích</th>
                                <th>Truy cập</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Tài khoản</td>
                                <td>Thêm, sửa, xóa tài khoản và phân quyền.</td>
                                <td><a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/users">Mở</a></td>
                            </tr>
                            <tr>
                                <td>Môn học</td>
                                <td>Quản lý danh mục môn học và khoa phụ trách.</td>
                                <td><a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/courses">Mở</a></td>
                            </tr>
                            <tr>
                                <td>Lớp học phần</td>
                                <td>Gán môn học, học kỳ, phòng học và giảng viên cho từng lớp.</td>
                                <td><a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/class-sections">Mở</a></td>
                            </tr>
                            <tr>
                                <td>Ghi danh</td>
                                <td>Xem sinh viên theo từng lớp và thêm sinh viên vào lớp.</td>
                                <td><a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/enrollments">Mở</a></td>
                            </tr>
                            <tr>
                                <td>Khảo sát</td>
                                <td>Tạo, mở hoặc đóng các đợt khảo sát feedback.</td>
                                <td><a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/feedback-forms">Mở</a></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>
    </div>
</body>
</html>
