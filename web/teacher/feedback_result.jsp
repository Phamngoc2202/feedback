<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kết quả feedback</title>
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
                <a class="nav-link" href="${pageContext.request.contextPath}/teacher/home">Lớp phụ trách</a>
                <a class="nav-link logout" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </nav>
        </aside>

        <main class="main">
            <div class="topbar">
                <div class="page-title">
                    <h1>Kết quả feedback lớp ${classSection.classCode}</h1>
                    <p>${classSection.courseCode} - ${classSection.courseName}</p>
                </div>
                <div class="top-actions">
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/teacher/home">Quay lại</a>
                </div>
            </div>

            <c:if test="${replySuccess}">
                <div class="alert alert-success">Đã gửi phản hồi thành công.</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <div class="card">
                <div class="stats-grid">
                    <div class="stat">
                        <span>Học kỳ</span>
                        <strong>${classSection.semesterName}</strong>
                    </div>
                    <div class="stat">
                        <span>Sinh viên</span>
                        <strong>${classSection.studentCount}</strong>
                    </div>
                    <div class="stat">
                        <span>Feedback</span>
                        <strong>${classSection.feedbackCount}</strong>
                    </div>
                    <div class="stat">
                        <span>Điểm TB</span>
                        <strong>${classSection.averageScore}</strong>
                    </div>
                </div>
            </div>

            <div class="card">
                <div class="card-header">
                    <h2>Điểm trung bình theo tiêu chí</h2>
                </div>
                <c:choose>
                    <c:when test="${empty criterionStats}">
                        <div class="empty-state">Chưa có dữ liệu điểm feedback cho lớp này.</div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-wrap">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Tiêu chí</th>
                                        <th>Số lượt</th>
                                        <th>Điểm TB</th>
                                        <th>Thang điểm</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${criterionStats}" var="stat">
                                        <tr>
                                            <td>${stat.criterionTitle}</td>
                                            <td>${stat.responseCount}</td>
                                            <td><span class="badge badge-success">${stat.averageScore}</span></td>
                                            <td>${stat.maxScore}</td>
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
                    <h2>Ý kiến sinh viên</h2>
                </div>
                <c:choose>
                    <c:when test="${empty feedbackComments}">
                        <div class="empty-state">Chưa có sinh viên gửi feedback cho lớp này.</div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-wrap">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Sinh viên</th>
                                        <th>Ý kiến</th>
                                        <th>Điểm TB</th>
                                        <th>Trả lời</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${feedbackComments}" var="comment">
                                        <tr>
                                            <td>
                                                <strong>${comment.studentName}</strong><br>
                                                <span class="muted">${comment.studentCode}</span><br>
                                                <span class="muted">${comment.createdAt}</span>
                                            </td>
                                            <td>${comment.generalComment}</td>
                                            <td><span class="badge">${comment.averageScore}</span></td>
                                            <td>
                                                <c:if test="${not empty comment.replyContent}">
                                                    <div class="reply-box">
                                                        <strong>Phản hồi gần nhất</strong><br>
                                                        ${comment.replyContent}<br>
                                                        <span class="muted">${comment.repliedAt}</span>
                                                    </div>
                                                </c:if>
                                                <form action="${pageContext.request.contextPath}/teacher/feedback" method="POST">
                                                    <input type="hidden" name="classSectionId" value="${classSection.classSectionId}">
                                                    <input type="hidden" name="feedbackId" value="${comment.feedbackId}">
                                                    <textarea name="content" maxlength="1000" placeholder="Nhập phản hồi cho sinh viên..." required></textarea>
                                                    <button type="submit" class="btn btn-primary">Gửi phản hồi</button>
                                                </form>
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
