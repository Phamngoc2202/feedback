<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gửi feedback</title>
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
                <a class="nav-link" href="${pageContext.request.contextPath}/student/history">Lịch sử feedback</a>
                <a class="nav-link logout" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </nav>
        </aside>

        <main class="main">
            <div class="topbar">
                <div class="page-title">
                    <h1>Gửi feedback</h1>
                    <p>Đánh giá lớp học phần theo các tiêu chí đang mở.</p>
                </div>
                <div class="top-actions">
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/student/home">Quay lại</a>
                </div>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <c:if test="${not empty classSection}">
                <div class="card">
                    <div class="stats-grid">
                        <div class="stat">
                            <span>Lớp</span>
                            <strong>${classSection.classCode}</strong>
                        </div>
                        <div class="stat">
                            <span>Môn học</span>
                            <strong>${classSection.courseCode}</strong>
                        </div>
                        <div class="stat">
                            <span>Giảng viên</span>
                            <strong>${classSection.teacherName}</strong>
                        </div>
                        <div class="stat">
                            <span>Phòng</span>
                            <strong>${classSection.room}</strong>
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${not empty form and not empty criteriaList}">
                <form action="${pageContext.request.contextPath}/student/feedback" method="POST">
                    <input type="hidden" name="classSectionId" value="${classSection.classSectionId}">
                    <input type="hidden" name="formId" value="${form.formId}">

                    <div class="card">
                        <h2>${form.title}</h2>
                        <p class="muted">Chọn điểm từ 1 đến thang điểm tối đa cho từng tiêu chí.</p>
                    </div>

                    <c:forEach items="${criteriaList}" var="criterion">
                        <div class="card">
                            <h2>${criterion.title}</h2>
                            <p class="muted">${criterion.description}</p>
                            <div class="score-grid">
                                <c:forEach begin="1" end="${criterion.maxScore}" var="score">
                                    <label class="score-option">
                                        <input type="radio" name="score_${criterion.criterionId}" value="${score}" required>
                                        ${score}
                                    </label>
                                </c:forEach>
                            </div>
                        </div>
                    </c:forEach>

                    <div class="card">
                        <label>Ý kiến chung</label>
                        <textarea name="generalComment" maxlength="1000" placeholder="Nhập góp ý của bạn cho giảng viên...">${generalComment}</textarea>
                    </div>

                    <button type="submit" class="btn btn-primary">Gửi feedback</button>
                </form>
            </c:if>
        </main>
    </div>
</body>
</html>
