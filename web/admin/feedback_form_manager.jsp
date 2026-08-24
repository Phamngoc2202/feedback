<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý đợt khảo sát</title>
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
                    <h1>Quản lý đợt khảo sát</h1>
                    <p>Tạo đợt feedback theo học kỳ và bật/tắt trạng thái khảo sát.</p>
                </div>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <div class="card">
                <h2>Tạo đợt khảo sát</h2>
                <form action="${pageContext.request.contextPath}/admin/feedback-forms" method="POST">
                    <div class="form-grid">
                        <div class="form-row full">
                            <label>Tiêu đề khảo sát</label>
                            <input type="text" name="title" required minlength="5" maxlength="200" placeholder="Ví dụ: Khảo sát giảng viên học kỳ 1">
                        </div>
                        <div class="form-row">
                            <label>Học kỳ</label>
                            <select name="semesterId">
                                <c:forEach items="${semesterList}" var="s">
                                    <option value="${s.semesterId}">${s.semesterName} - ${s.academicYear}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-row">
                            <label>Ngày bắt đầu</label>
                            <input type="date" name="startDate" required>
                        </div>
                        <div class="form-row">
                            <label>Ngày kết thúc</label>
                            <input type="date" name="endDate" required>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Tạo khảo sát</button>
                </form>
            </div>

            <div class="card">
                <div class="card-header">
                    <h2>Danh sách đợt khảo sát</h2>
                </div>
                <form action="${pageContext.request.contextPath}/admin/feedback-forms" method="GET" class="filter-bar">
                    <div class="form-row">
                        <label>Tìm kiếm khảo sát</label>
                        <input type="text" name="q" value="${q}" placeholder="Tiêu đề, học kỳ, năm học">
                    </div>
                    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/feedback-forms">Xóa lọc</a>
                </form>
                <div class="table-wrap">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Tiêu đề</th>
                                <th>Học kỳ</th>
                                <th>Ngày bắt đầu</th>
                                <th>Ngày kết thúc</th>
                                <th>Trạng thái</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${formList}" var="f">
                                <tr>
                                    <td>${f.formId}</td>
                                    <td>${f.title}</td>
                                    <td>${f.semesterName}</td>
                                    <td>${f.startDate}</td>
                                    <td>${f.endDate}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${f.isActive}">
                                                <span class="badge badge-success">Đang mở</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge badge-danger">Đã đóng</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/feedback-forms?action=targets&id=${f.formId}">Đối tượng</a>
                                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/feedback-forms?action=toggle&id=${f.formId}&status=${f.isActive ? 1 : 0}">
                                            ${f.isActive ? 'Đóng khảo sát' : 'Mở lại'}
                                        </a>
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
