<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý môn học</title>
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
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/users">Tài khoản</a>
                <a class="nav-link active" href="${pageContext.request.contextPath}/admin/courses">Môn học</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/class-sections">Lớp học phần</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/enrollments">Ghi danh</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/feedback-forms">Khảo sát</a>
                <a class="nav-link logout" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </nav>
        </aside>

        <main class="main">
            <div class="topbar">
                <div class="page-title">
                    <h1>Quản lý môn học</h1>
                    <p>Quản lý danh mục môn học, tín chỉ và khoa phụ trách.</p>
                </div>
            </div>

            <c:if test="${not empty requestScope.error}">
                <div class="alert alert-danger">${requestScope.error}</div>
            </c:if>

            <div class="card">
                <h2>Thêm môn học</h2>
                <form action="${pageContext.request.contextPath}/admin/courses" method="POST">
                    <div class="form-grid">
                        <div class="form-row">
                            <label>Mã môn học</label>
                            <input type="text" name="courseCode" required placeholder="Ví dụ: INT1234">
                        </div>
                        <div class="form-row">
                            <label>Tên môn học</label>
                            <input type="text" name="courseName" required>
                        </div>
                        <div class="form-row">
                            <label>Số tín chỉ</label>
                            <input type="number" name="credits" min="1" max="10" required>
                        </div>
                        <div class="form-row">
                            <label>Khoa</label>
                            <select name="departmentId">
                                <c:forEach items="${deptList}" var="d">
                                    <option value="${d.departmentId}">${d.departmentName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Thêm môn học</button>
                </form>
            </div>

            <div class="card">
                <div class="card-header">
                    <h2>Danh sách môn học</h2>
                </div>
                <form action="${pageContext.request.contextPath}/admin/courses" method="GET" class="filter-bar">
                    <div class="form-row">
                        <label>Tìm kiếm môn học</label>
                        <input type="text" name="q" value="${q}" placeholder="Mã môn, tên môn, khoa">
                    </div>
                    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/courses">Xóa lọc</a>
                </form>
                <div class="table-wrap">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Mã môn</th>
                                <th>Tên môn</th>
                                <th>Tín chỉ</th>
                                <th>Khoa</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${courseList}" var="c">
                                <tr>
                                    <td>${c.courseId}</td>
                                    <td>${c.courseCode}</td>
                                    <td>${c.courseName}</td>
                                    <td><span class="badge">${c.credits}</span></td>
                                    <td>${c.departmentName}</td>
                                    <td>
                                        <a class="btn btn-danger" href="${pageContext.request.contextPath}/admin/courses?action=delete&id=${c.courseId}"
                                           onclick="return confirm('Bạn có chắc muốn xóa môn học này?');">Xóa</a>
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
