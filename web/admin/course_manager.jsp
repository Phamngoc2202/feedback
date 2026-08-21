<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Course Management - Admin</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
            background: #f0f2f5;
            color: #1f2937;
            display: flex;
            min-height: 100vh;
        }

        /* Sidebar */
        .sidebar {
            width: 240px;
            background: #111827;
            color: #e5e7eb;
            position: fixed;
            top: 0;
            left: 0;
            bottom: 0;
            display: flex;
            flex-direction: column;
            border-right: 1px solid #1f2937;
        }

        .sidebar-header {
            padding: 24px 20px;
            border-bottom: 1px solid #1f2937;
        }

        .sidebar-header h2 {
            font-size: 15px;
            font-weight: 600;
            letter-spacing: 0.3px;
            color: #f9fafb;
        }

        .sidebar-header span {
            display: block;
            font-size: 12px;
            color: #9ca3af;
            margin-top: 4px;
        }

        .sidebar-nav {
            flex: 1;
            padding: 16px 12px;
            display: flex;
            flex-direction: column;
        }

        .sidebar-nav a {
            display: flex;
            align-items: center;
            gap: 12px;
            padding: 11px 14px;
            color: #d1d5db;
            text-decoration: none;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 500;
            margin-bottom: 4px;
            transition: background 0.15s ease, color 0.15s ease;
        }

        .sidebar-nav a:hover {
            background: #1f2937;
            color: #f9fafb;
        }

        .sidebar-nav a.active {
            background: #1e40af;
            color: #ffffff;
        }

        .sidebar-nav a.logout {
            margin-top: auto;
            color: #fca5a5;
        }

        .sidebar-nav a.logout:hover {
            background: #7f1d1d;
            color: #fee2e2;
        }

        /* Main */
        .main {
            margin-left: 240px;
            flex: 1;
            padding: 28px 32px;
            max-width: 1200px;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 24px;
        }

        .header h1 {
            font-size: 22px;
            font-weight: 600;
            color: #111827;
        }

        .back-link {
            font-size: 13px;
            color: #4b5563;
            text-decoration: none;
        }

        .back-link:hover {
            color: #1e40af;
        }

        /* Error */
        .error-box {
            background: #fef2f2;
            border: 1px solid #fecaca;
            color: #b91c1c;
            padding: 12px 16px;
            border-radius: 6px;
            font-size: 14px;
            margin-bottom: 20px;
        }

        /* Card */
        .card {
            background: #ffffff;
            border: 1px solid #e5e7eb;
            border-radius: 8px;
            padding: 24px 28px;
            margin-bottom: 24px;
        }

        .card h2 {
            font-size: 16px;
            font-weight: 600;
            color: #111827;
            margin-bottom: 18px;
        }

        /* Form */
        .form-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 16px 20px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
            gap: 6px;
        }

        .form-group label {
            font-size: 13px;
            font-weight: 500;
            color: #374151;
        }

        .form-group input,
        .form-group select {
            padding: 9px 12px;
            border: 1px solid #d1d5db;
            border-radius: 6px;
            font-size: 14px;
            color: #111827;
            background: #fff;
            transition: border-color 0.15s ease;
        }

        .form-group input:focus,
        .form-group select:focus {
            outline: none;
            border-color: #1e40af;
        }

        .btn-primary {
            display: inline-block;
            background: #1e40af;
            color: #ffffff;
            border: none;
            padding: 10px 20px;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 500;
            cursor: pointer;
            transition: background 0.15s ease;
        }

        .btn-primary:hover {
            background: #1e3a8a;
        }

        /* Table */
        .table-wrapper {
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 14px;
        }

        thead th {
            text-align: left;
            padding: 12px 14px;
            background: #f9fafb;
            color: #4b5563;
            font-weight: 600;
            font-size: 12px;
            text-transform: uppercase;
            letter-spacing: 0.3px;
            border-bottom: 1px solid #e5e7eb;
        }

        tbody td {
            padding: 14px;
            border-bottom: 1px solid #f3f4f6;
            color: #374151;
            vertical-align: middle;
        }

        tbody tr:hover {
            background: #f9fafb;
        }

        .credits-badge {
            display: inline-block;
            background: #eff6ff;
            color: #1e40af;
            padding: 3px 10px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 500;
        }

        .btn-delete {
            font-size: 13px;
            font-weight: 500;
            color: #dc2626;
            text-decoration: none;
        }

        .btn-delete:hover {
            color: #b91c1c;
        }

        @media (max-width: 768px) {
            .sidebar {
                display: none;
            }
            .main {
                margin-left: 0;
                padding: 20px;
            }
            .form-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <aside class="sidebar">
        <div class="sidebar-header">
            <h2>Feedback System</h2>
            <span>Administration</span>
        </div>
        <nav class="sidebar-nav">
            <a href="${pageContext.request.contextPath}/admin/home.jsp">Home</a>
            <a href="${pageContext.request.contextPath}/admin/users">User Management</a>
            <a href="${pageContext.request.contextPath}/admin/courses" class="active">Course Management</a>
            <a href="${pageContext.request.contextPath}/admin/feedback-forms">Feedback Forms</a>
            <a href="${pageContext.request.contextPath}/logout" class="logout">Logout</a>
        </nav>
    </aside>

    <main class="main">
        <div class="header">
            <h1>Course Management</h1>
            <a href="${pageContext.request.contextPath}/admin/home.jsp" class="back-link">← Back to Dashboard</a>
        </div>

        <c:if test="${not empty requestScope.error}">
            <div class="error-box">${requestScope.error}</div>
        </c:if>

        <!-- Add Course Form -->
        <div class="card">
            <h2>Add New Course</h2>
            <form action="${pageContext.request.contextPath}/admin/courses" method="POST">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Course Code</label>
                        <input type="text" name="courseCode" required placeholder="e.g., INT1234">
                    </div>
                    <div class="form-group">
                        <label>Course Name</label>
                        <input type="text" name="courseName" required>
                    </div>
                    <div class="form-group">
                        <label>Credits</label>
                        <input type="number" name="credits" min="1" max="10" required>
                    </div>
                    <div class="form-group">
                        <label>Department</label>
                        <select name="departmentId">
                            <c:forEach items="${deptList}" var="d">
                                <option value="${d.departmentId}">${d.departmentName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group" style="display: flex; align-items: flex-end;">
                        <button type="submit" class="btn-primary">Add Course</button>
                    </div>
                </div>
            </form>
        </div>

        <!-- Course List -->
        <div class="card">
            <h2>Course List</h2>
            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Course Code</th>
                            <th>Course Name</th>
                            <th>Credits</th>
                            <th>Department</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${courseList}" var="c">
                            <tr>
                                <td>${c.courseId}</td>
                                <td>${c.courseCode}</td>
                                <td>${c.courseName}</td>
                                <td>
                                    <span class="credits-badge">${c.credits}</span>
                                </td>
                                <td>${c.departmentName}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/courses?action=delete&id=${c.courseId}"
                                       class="btn-delete"
                                       onclick="return confirm('Are you sure you want to delete this course?');">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
</body>
</html>