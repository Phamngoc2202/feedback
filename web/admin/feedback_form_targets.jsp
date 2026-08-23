<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đối tượng khảo sát</title>
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
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/class-sections">Lớp học phần</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/enrollments">Ghi danh</a>
                <a class="nav-link active" href="${pageContext.request.contextPath}/admin/feedback-forms">Khảo sát</a>
                <a class="nav-link logout" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </nav>
        </aside>

        <main class="main">
            <div class="topbar">
                <div class="page-title">
                    <h1>Đối tượng khảo sát</h1>
                    <p>${form.title}</p>
                </div>
                <div class="top-actions">
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/feedback-forms">Quay lại</a>
                </div>
            </div>

            <div class="card">
                <div class="stats-grid">
                    <div class="stat">
                        <span>Học kỳ</span>
                        <strong>${form.semesterName}</strong>
                    </div>
                    <div class="stat">
                        <span>Lớp áp dụng</span>
                        <strong>${classCount}</strong>
                    </div>
                    <div class="stat">
                        <span>Sinh viên</span>
                        <strong>${studentCount}</strong>
                    </div>
                    <div class="stat">
                        <span>Đã feedback</span>
                        <strong>${submittedCount}</strong>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-header">
                    <div>
                        <h2>Cách xác định đối tượng</h2>
                        <p class="muted">
                            Khảo sát áp dụng cho tất cả lớp học phần thuộc học kỳ của khảo sát.
                            Sinh viên được tham gia nếu đã được ghi danh vào các lớp đó.
                        </p>
                    </div>
                </div>
                <div class="stats-grid">
                    <div class="stat">
                        <span>Trạng thái</span>
                        <strong>${form.isActive ? 'Đang mở' : 'Đã đóng'}</strong>
                    </div>
                    <div class="stat">
                        <span>Ngày bắt đầu</span>
                        <strong>${form.startDate}</strong>
                    </div>
                    <div class="stat">
                        <span>Ngày kết thúc</span>
                        <strong>${form.endDate}</strong>
                    </div>
                    <div class="stat">
                        <span>Chưa feedback</span>
                        <strong>${pendingCount}</strong>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-header">
                    <h2>Lớp học phần áp dụng</h2>
                </div>
                <c:choose>
                    <c:when test="${empty classTargets}">
                        <div class="empty-state">Chưa có lớp học phần nào thuộc học kỳ của khảo sát này.</div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-wrap">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Lớp</th>
                                        <th>Môn học</th>
                                        <th>Giảng viên</th>
                                        <th>Phòng</th>
                                        <th>Sinh viên</th>
                                        <th>Đã feedback</th>
                                        <th>Chưa feedback</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${classTargets}" var="cls">
                                        <tr>
                                            <td>${cls.classCode}</td>
                                            <td>${cls.courseCode} - ${cls.courseName}</td>
                                            <td>${cls.teacherName}</td>
                                            <td>${cls.room}</td>
                                            <td><span class="badge">${cls.studentCount}</span></td>
                                            <td><span class="badge badge-success">${cls.submittedCount}</span></td>
                                            <td><span class="badge badge-warning">${cls.pendingCount}</span></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="card">
                <div class="card-header">
                    <h2>Sinh viên tham gia khảo sát</h2>
                </div>
                <c:choose>
                    <c:when test="${empty studentTargets}">
                        <div class="empty-state">Chưa có sinh viên nào thuộc các lớp áp dụng khảo sát.</div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-wrap">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Sinh viên</th>
                                        <th>Lớp</th>
                                        <th>Môn học</th>
                                        <th>Giảng viên</th>
                                        <th>Trạng thái</th>
                                        <th>Thời gian gửi</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${studentTargets}" var="student">
                                        <tr>
                                            <td>
                                                <strong>${student.studentName}</strong><br>
                                                <span class="muted">${student.studentCode}</span>
                                            </td>
                                            <td>${student.classCode}</td>
                                            <td>${student.courseCode} - ${student.courseName}</td>
                                            <td>${student.teacherName}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${student.submitted}">
                                                        <span class="badge badge-success">Đã feedback</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge badge-warning">Chưa feedback</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${student.submittedAt}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
    </div>
</body>
</html>
