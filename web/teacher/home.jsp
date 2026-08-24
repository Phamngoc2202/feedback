<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang giảng viên</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>
    <div class="layout">
        <aside class="sidebar">
            <div class="brand">
                <span class="brand-title">Hệ thống Feedback</span>
                <span class="brand-subtitle">Giảng viên</span>
            </div>
            <nav class="side-nav">
                <a class="nav-link active" href="${pageContext.request.contextPath}/teacher/home">Lớp phụ trách</a>
                <a class="nav-link logout" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </nav>
        </aside>

        <main class="main">
            <div class="topbar">
                <div class="page-title">
                    <h1>Lớp phụ trách</h1>
                    <p>Xin chào, ${sessionScope.account.fullName}. Theo dõi kết quả feedback theo từng lớp.</p>
                </div>
            </div>

            <div class="card">
                <div class="stats-grid">
                    <div class="stat">
                        <span>Số lớp phụ trách</span>
                        <strong>${totalClasses}</strong>
                    </div>
                    <div class="stat">
                        <span>Tổng sinh viên</span>
                        <strong>${totalStudents}</strong>
                    </div>
                    <div class="stat">
                        <span>Tổng feedback</span>
                        <strong>${totalFeedbacks}</strong>
                    </div>
                    <div class="stat">
                        <span>Điểm TB chung</span>
                        <strong>${overallAverage}</strong>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-header">
                    <h2>Danh sách lớp học phần</h2>
                </div>
                <c:choose>
                    <c:when test="${empty classList}">
                        <div class="empty-state">Bạn chưa được phân công lớp học phần nào.</div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-wrap">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Lớp</th>
                                        <th>Môn học</th>
                                        <th>Học kỳ</th>
                                        <th>Phòng</th>
                                        <th>Sinh viên</th>
                                        <th>Feedback</th>
                                        <th>Điểm TB</th>
                                        <th>Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${classList}" var="cls">
                                        <tr>
                                            <td>${cls.classCode}</td>
                                            <td>${cls.courseCode} - ${cls.courseName}</td>
                                            <td>${cls.semesterName} (${cls.academicYear})</td>
                                            <td>${cls.room}</td>
                                            <td><span class="badge">${cls.studentCount}</span></td>
                                            <td><span class="badge badge-success">${cls.feedbackCount}</span></td>
                                            <td><span class="badge">${cls.averageScore}</span></td>
                                            <td>
                                                <a class="btn btn-primary" href="${pageContext.request.contextPath}/teacher/feedback?classSectionId=${cls.classSectionId}">
                                                    Chi tiết lớp
                                                </a>
                                            </td>
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
