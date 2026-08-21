<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit User - Admin</title>
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
            max-width: 720px;
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
            padding: 28px 32px;
        }

        .card h2 {
            font-size: 16px;
            font-weight: 600;
            color: #111827;
            margin-bottom: 22px;
        }

        /* Form */
        .form-group {
            margin-bottom: 18px;
        }

        .form-group label {
            display: block;
            font-size: 13px;
            font-weight: 500;
            color: #374151;
            margin-bottom: 6px;
        }

        .form-group input,
        .form-group select {
            width: 100%;
            padding: 10px 12px;
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

        .form-actions {
            display: flex;
            align-items: center;
            gap: 16px;
            margin-top: 8px;
        }

        .btn-primary {
            background: #1e40af;
            color: #ffffff;
            border: none;
            padding: 10px 22px;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 500;
            cursor: pointer;
            transition: background 0.15s ease;
        }

        .btn-primary:hover {
            background: #1e3a8a;
        }

        .btn-cancel {
            font-size: 14px;
            color: #6b7280;
            text-decoration: none;
            font-weight: 500;
        }

        .btn-cancel:hover {
            color: #374151;
        }

        @media (max-width: 768px) {
            .sidebar {
                display: none;
            }
            .main {
                margin-left: 0;
                padding: 20px;
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
            <a href="${pageContext.request.contextPath}/admin/users" class="active">User Management</a>
            <a href="${pageContext.request.contextPath}/admin/courses">Course Management</a>
            <a href="${pageContext.request.contextPath}/admin/feedback-forms">Feedback Forms</a>
            <a href="${pageContext.request.contextPath}/logout" class="logout">Logout</a>
        </nav>
    </aside>

    <main class="main">
        <div class="header">
            <h1>Edit Account</h1>
            <a href="${pageContext.request.contextPath}/admin/users" class="back-link">← Back to User List</a>
        </div>

        <div class="card">
            <h2>Update User Information</h2>
            <form action="${pageContext.request.contextPath}/admin/users" method="POST">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="userId" value="${userEdit.userId}">

                <div class="form-group">
                    <label>Username</label>
                    <input type="text" name="username" value="${userEdit.username}" required>
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <input type="text" name="password" value="${userEdit.password}" required>
                </div>

                <div class="form-group">
                    <label>Full Name</label>
                    <input type="text" name="fullname" value="${userEdit.fullName}" required>
                </div>

                <div class="form-group">
                    <label>Email</label>
                    <input type="email" name="email" value="${userEdit.email}">
                </div>

                <div class="form-group">
                    <label>Role</label>
                    <select name="roleId">
                        <option value="1" <c:if test="${userEdit.roleId == 1}">selected</c:if>>Admin</option>
                        <option value="2" <c:if test="${userEdit.roleId == 2}">selected</c:if>>Teacher</option>
                        <option value="3" <c:if test="${userEdit.roleId == 3}">selected</c:if>>Student</option>
                    </select>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn-primary">Update User</button>
                    <a href="${pageContext.request.contextPath}/admin/users" class="btn-cancel">Cancel</a>
                </div>
            </form>
        </div>
    </main>
</body>
</html>