<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý lớp học phần</title>
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
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/courses">Môn học</a>
                <a class="nav-link active" href="${pageContext.request.contextPath}/admin/class-sections">Lớp học phần</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/enrollments">Ghi danh</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/feedback-forms">Khảo sát</a>
                <a class="nav-link logout" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </nav>
        </aside>

        <main class="main">
            <div class="topbar">
                <div class="page-title">
                    <h1>Quản lý lớp học phần</h1>
                    <p>Tạo lớp, gán môn học, học kỳ, giảng viên và phòng học.</p>
                </div>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <div class="card">
                <h2>Tạo lớp học phần</h2>
                <form action="${pageContext.request.contextPath}/admin/class-sections" method="POST">
                    <div class="form-grid">
                        <div class="form-row">
                            <label>Mã lớp</label>
                            <input type="text" name="classCode" required placeholder="Ví dụ: INT1234_03">
                        </div>
                        <div class="form-row">
                            <label>Phòng học</label>
                            <input type="text" name="room" placeholder="Ví dụ: A2-203">
                        </div>
                        <div class="form-row">
                            <label>Môn học</label>
                            <select name="courseId" required>
                                <c:forEach items="${courseList}" var="course">
                                    <option value="${course.courseId}">${course.courseCode} - ${course.courseName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-row">
                            <label>Học kỳ</label>
                            <select name="semesterId" required>
                                <c:forEach items="${semesterList}" var="semester">
                                    <option value="${semester.semesterId}">${semester.semesterName} (${semester.academicYear})</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-row">
                            <label>Giảng viên</label>
                            <select name="teacherId" required>
                                <c:forEach items="${teacherList}" var="teacher">
                                    <option value="${teacher.userId}">${teacher.fullName} (${teacher.username})</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Tạo lớp học phần</button>
                </form>
            </div>

            <div class="card">
                <div class="card-header">
                    <h2>Danh sách lớp học phần</h2>
                </div>
                <form action="${pageContext.request.contextPath}/admin/class-sections" method="GET" class="filter-bar">
                    <div class="form-row">
                        <label>Tìm kiếm lớp học phần</label>
                        <input type="text" name="q" value="${q}" placeholder="Mã lớp, môn học, giảng viên, học kỳ">
                    </div>
                    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/class-sections">Xóa lọc</a>
                </form>
                <div class="table-wrap">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Lớp</th>
                                <th>Môn học</th>
                                <th>Giảng viên</th>
                                <th>Học kỳ</th>
                                <th>Phòng</th>
                                <th>Sinh viên</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${classSectionList}" var="cls">
                                <tr>
                                    <td>${cls.classSectionId}</td>
                                    <td>${cls.classCode}</td>
                                    <td>${cls.courseCode} - ${cls.courseName}</td>
                                    <td>${cls.teacherName} (${cls.teacherCode})</td>
                                    <td>${cls.semesterName} (${cls.academicYear})</td>
                                    <td>${cls.room}</td>
                                    <td><span class="badge">${cls.enrollmentCount}</span></td>
                                    <td>
                                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/enrollments?classSectionId=${cls.classSectionId}">Xem sinh viên</a>
                                        <a class="btn btn-danger" href="${pageContext.request.contextPath}/admin/class-sections?action=delete&id=${cls.classSectionId}"
                                           onclick="return confirm('Bạn có chắc muốn xóa lớp học phần này?');">Xóa</a>
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
