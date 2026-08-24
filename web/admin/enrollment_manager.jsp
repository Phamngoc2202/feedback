<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý ghi danh</title>
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
                <a class="nav-link active" href="${pageContext.request.contextPath}/admin/enrollments">Ghi danh</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/feedback-forms">Khảo sát</a>
                <a class="nav-link logout" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </nav>
        </aside>

        <main class="main">
            <div class="topbar">
                <div class="page-title">
                    <h1>Quản lý ghi danh</h1>
                    <p>Chọn một lớp học phần để xem và cập nhật danh sách sinh viên.</p>
                </div>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <div class="card">
                <div class="card-header">
                    <h2>Danh sách lớp học phần</h2>
                </div>
                <form action="${pageContext.request.contextPath}/admin/enrollments" method="GET" class="filter-bar">
                    <div class="form-row">
                        <label>Tìm kiếm lớp</label>
                        <input type="text" name="q" value="${q}" placeholder="Mã lớp, môn học, giảng viên, học kỳ">
                    </div>
                    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/enrollments">Xóa lọc</a>
                </form>
                <div class="table-wrap">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Lớp</th>
                                <th>Môn học</th>
                                <th>Giảng viên</th>
                                <th>Học kỳ</th>
                                <th>Số sinh viên</th>
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${classSectionList}" var="cls">
                                <tr class="${selectedClassSectionId == cls.classSectionId ? 'selected' : ''}">
                                    <td>${cls.classCode}</td>
                                    <td>${cls.courseCode} - ${cls.courseName}</td>
                                    <td>${cls.teacherName}</td>
                                    <td>${cls.semesterName} (${cls.academicYear})</td>
                                    <td><span class="badge">${cls.enrollmentCount}</span></td>
                                    <td>
                                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/enrollments?classSectionId=${cls.classSectionId}">
                                            Xem sinh viên
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <c:choose>
                <c:when test="${empty selectedClassSection}">
                    <div class="empty-state">Chọn một lớp ở bảng trên để xem sinh viên đang học lớp đó.</div>
                </c:when>
                <c:otherwise>
                    <div class="card">
                        <div class="card-header">
                            <div>
                                <h2>Lớp ${selectedClassSection.classCode}</h2>
                                <p class="muted">
                                    ${selectedClassSection.courseCode} - ${selectedClassSection.courseName}
                                    | Giảng viên: ${selectedClassSection.teacherName}
                                    | Học kỳ: ${selectedClassSection.semesterName} (${selectedClassSection.academicYear})
                                </p>
                            </div>
                        </div>

                        <form action="${pageContext.request.contextPath}/admin/enrollments" method="POST">
                            <input type="hidden" name="classSectionId" value="${selectedClassSection.classSectionId}">
                            <div class="form-grid">
                                <div class="form-row">
                                    <label>Thêm sinh viên vào lớp</label>
                                    <select name="studentId" required>
                                        <c:forEach items="${studentList}" var="student">
                                            <option value="${student.userId}">${student.fullName} (${student.username})</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-row" style="align-self:end;">
                                    <button type="submit" class="btn btn-primary">Thêm vào lớp</button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="card">
                        <div class="card-header">
                            <h2>Danh sách sinh viên</h2>
                        </div>
                        <form action="${pageContext.request.contextPath}/admin/enrollments" method="GET" class="filter-bar">
                            <input type="hidden" name="classSectionId" value="${selectedClassSection.classSectionId}">
                            <input type="hidden" name="q" value="${q}">
                            <div class="form-row">
                                <label>Tìm kiếm sinh viên trong lớp</label>
                                <input type="text" name="studentQ" value="${studentQ}" placeholder="Mã sinh viên, họ tên, email">
                            </div>
                            <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/enrollments?classSectionId=${selectedClassSection.classSectionId}">Xóa lọc</a>
                        </form>
                        <c:choose>
                            <c:when test="${empty enrollmentList}">
                                <div class="empty-state">Lớp này chưa có sinh viên.</div>
                            </c:when>
                            <c:otherwise>
                                <div class="table-wrap">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Mã sinh viên</th>
                                                <th>Họ và tên</th>
                                                <th>ID tài khoản</th>
                                                <th>Thao tác</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${enrollmentList}" var="enrollment">
                                                <tr>
                                                    <td>${enrollment.studentCode}</td>
                                                    <td>${enrollment.studentName}</td>
                                                    <td>${enrollment.studentId}</td>
                                                    <td>
                                                        <a class="btn btn-danger"
                                                           href="${pageContext.request.contextPath}/admin/enrollments?action=delete&id=${enrollment.enrollmentId}&classSectionId=${selectedClassSection.classSectionId}"
                                                           onclick="return confirm('Bạn có chắc muốn xóa sinh viên này khỏi lớp?');">Xóa khỏi lớp</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>
    </div>
</body>
</html>
