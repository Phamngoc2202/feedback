<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lịch sử feedback</title>
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
                <a class="nav-link" href="${pageContext.request.contextPath}/student/home">Lớp của tôi</a>
                <a class="nav-link active" href="${pageContext.request.contextPath}/student/history">Lịch sử feedback</a>
                <a class="nav-link logout" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </nav>
        </aside>

        <main class="main">
            <div class="topbar">
                <div class="page-title">
                    <h1>Lịch sử feedback</h1>
                    <p>Xem lại các đánh giá bạn đã gửi cho từng lớp học phần.</p>
                </div>
            </div>

            <c:if test="${submitted}">
                <div class="alert alert-success">Gửi feedback thành công.</div>
            </c:if>

            <div class="card">
                <div class="card-header">
                    <h2>Danh sách feedback đã gửi</h2>
                </div>
                <c:choose>
                    <c:when test="${empty historyList}">
                        <div class="empty-state">Bạn chưa gửi feedback nào.</div>
                    </c:when>
                    <c:otherwise>
                        <div class="table-wrap">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Thời gian</th>
                                        <th>Lớp</th>
                                        <th>Môn học</th>
                                        <th>Giảng viên</th>
                                        <th>Điểm TB</th>
                                        <th>Ý kiến</th>
                                        <th>Chi tiết</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${historyList}" var="item">
                                        <tr>
                                            <td>${item.createdAt}</td>
                                            <td>${item.classCode}</td>
                                            <td>${item.courseCode} - ${item.courseName}</td>
                                            <td>${item.teacherName}</td>
                                            <td><span class="badge">${item.averageScore}</span></td>
                                            <td>${item.generalComment}</td>
                                            <td>
                                                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/student/history?feedbackId=${item.feedbackId}">
                                                    Xem
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

            <c:if test="${not empty detailList}">
                <div class="card">
                    <div class="card-header">
                        <h2>Chi tiết feedback #${selectedFeedbackId}</h2>
                    </div>
                    <div class="table-wrap">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Tiêu chí</th>
                                    <th>Điểm</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${detailList}" var="detail">
                                    <tr>
                                        <td>${detail.criterionTitle}</td>
                                        <td><span class="badge">${detail.score} / ${detail.maxScore}</span></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:if>
        </main>
    </div>
</body>
</html>
