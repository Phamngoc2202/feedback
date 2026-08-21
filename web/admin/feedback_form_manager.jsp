<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Feedback Forms Management - Admin</title>
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

        .form-group.full {
            grid-column: 1 / -1;
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

        .badge {
            display: inline-block;
            padding: 3px 10px;
            border-radius: 12px;
            font-size: 12px;
            font-weight: 500;
        }

        .badge-active {
            background: #d1fae5;
            color: #065f46;
        }

        .badge-inactive {
            background: #fee2e2;
            color: #991b1b;
        }

        .btn-toggle {
            display: inline-block;
            font-size: 13px;
            font-weight: 500;
            padding: 5px 12px;
            border-radius: 6px;
            text-decoration: none;
            border: 1px solid #d1d5db;
            background: #f9fafb;
            color: #374151;
            transition: all 0.15s ease;
        }

        .btn-toggle:hover {
            background: #f3f4f6;
            border-color: #9ca3af;
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
            <a href="${pageContext.request.contextPath}/admin/courses">Course Management</a>
            <a href="${pageContext.request.contextPath}/admin/feedback-forms" class="active">Feedback Forms</a>
            <a href="${pageContext.request.contextPath}/logout" class="logout">Logout</a>
        </nav>
    </aside>

    <main class="main">
        <div class="header">
            <h1>Feedback Forms</h1>
            <a href="${pageContext.request.contextPath}/admin/home.jsp" class="back-link">← Back to Dashboard</a>
        </div>

        <!-- Create Form -->
        <div class="card">
            <h2>Create New Feedback Period</h2>
            <form action="${pageContext.request.contextPath}/admin/feedback-forms" method="POST">
                <div class="form-grid">
                    <div class="form-group full">
                        <label>Survey Title</label>
                        <input type="text" name="title" required placeholder="E.g., Spring 2026 Teacher Evaluation">
                    </div>
                    <div class="form-group">
                        <label>Select Semester</label>
                        <select name="semesterId">
                            <c:forEach items="${semesterList}" var="s">
                                <option value="${s.semesterId}">${s.semesterName} - ${s.academicYear}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <!-- empty for layout -->
                    </div>
                    <div class="form-group">
                        <label>Start Date</label>
                        <input type="date" name="startDate" required>
                    </div>
                    <div class="form-group">
                        <label>End Date</label>
                        <input type="date" name="endDate" required>
                    </div>
                    <div class="form-group" style="display: flex; align-items: flex-end;">
                        <button type="submit" class="btn-primary">Create Feedback Form</button>
                    </div>
                </div>
            </form>
        </div>

        <!-- List -->
        <div class="card">
            <h2>Feedback Periods List</h2>
            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Semester</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Status</th>
                            <th>Action</th>
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
                                            <span class="badge badge-active">Active</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-inactive">Closed</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/feedback-forms?action=toggle&id=${f.formId}&status=${f.isActive ? 1 : 0}"
                                       class="btn-toggle">
                                        ${f.isActive ? 'Close Survey' : 'Re-open Survey'}
                                    </a>
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