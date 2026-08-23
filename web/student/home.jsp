<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang sinh viên</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>
    <div class="layout">
        <aside class="sidebar">
            <div class="brand">
                <span class="brand-title">Hệ thống Feedback</span>
                <span class="brand-subtitle">Sinh viên</span>
            </div>
            <nav class="side-nav">
                <a class="nav-link active" href="${pageContext.request.contextPath}/student/home">Lớp của tôi</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/student/history">Lịch sử feedback</a>
                <a class="nav-link logout" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </nav>
        </aside>

        <main class="main">
            <div class="topbar">
                <div class="page-title">
                    <h1>Lớp của tôi</h1>
                    <p>Xin chào, ${sessionScope.account.fullName}. Chọn lớp có khảo sát đang mở để gửi feedback.</p>
                </div>
            </div>

            <div class="card">
                <div class="card-header">
                    <h2>Danh sách lớp học phần</h2>
                </div>
                <c:choose>
                    <c:when test="${empty classList}">
                        <div class="empty-state">Bạn chưa được ghi danh vào lớp học phần nào.</div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-wrap">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Lớp</th>
                                        <th>Môn học</th>
                                        <th>Giảng viên</th>
                                        <th>Học kỳ</th>
                                        <th>Phòng</th>
                                        <th>Feedback</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${classList}" var="cls">
                                        <tr>
                                            <td>${cls.classCode}</td>
                                            <td>${cls.courseCode} - ${cls.courseName}</td>
                                            <td>${cls.teacherName}</td>
                                            <td>${cls.semesterName} (${cls.academicYear})</td>
                                            <td>${cls.room}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${cls.canFeedback}">
                                                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/student/feedback?classSectionId=${cls.classSectionId}">
                                                            Gửi feedback
                                                        </a>
                                                    </c:when>
                                                    <c:when test="${cls.feedbackSubmitted}">
                                                        <span class="badge badge-success">Đã gửi</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge">Chưa mở khảo sát</span>
                                                    </c:otherwise>
                                                </c:choose>
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
