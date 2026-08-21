<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Home - Feedback System</title>
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
            max-width: 1100px;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 28px;
        }

        .header h1 {
            font-size: 22px;
            font-weight: 600;
            color: #111827;
        }

        .user-badge {
            font-size: 13px;
            color: #4b5563;
            background: #ffffff;
            border: 1px solid #e5e7eb;
            padding: 6px 14px;
            border-radius: 20px;
        }

        .user-badge strong {
            color: #1e40af;
            font-weight: 600;
        }

        /* Card */
        .card {
            background: #ffffff;
            border: 1px solid #e5e7eb;
            border-radius: 8px;
            padding: 28px 32px;
        }

        .card h2 {
            font-size: 17px;
            font-weight: 600;
            color: #111827;
            margin-bottom: 12px;
        }

        .card > p {
            font-size: 14px;
            line-height: 1.6;
            color: #4b5563;
            margin-bottom: 20px;
        }

        .feature-list {
            list-style: none;
            margin-bottom: 24px;
        }

        .feature-list li {
            display: flex;
            gap: 12px;
            padding: 14px 0;
            border-bottom: 1px solid #f3f4f6;
            font-size: 14px;
            line-height: 1.5;
            color: #374151;
        }

        .feature-list li:last-child {
            border-bottom: none;
        }

        .feature-list strong {
            color: #111827;
            font-weight: 600;
            min-width: 160px;
        }

        .note {
            background: #fef3c7;
            border: 1px solid #fcd34d;
            border-radius: 6px;
            padding: 12px 16px;
            font-size: 13px;
            color: #92400e;
            line-height: 1.5;
        }

        .note strong {
            font-weight: 600;
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
            <a href="${pageContext.request.contextPath}/admin/home.jsp" class="active">Home</a>
            <a href="${pageContext.request.contextPath}/admin/users">User Management</a>
            <a href="${pageContext.request.contextPath}/admin/courses">Course Management</a>
            <a href="${pageContext.request.contextPath}/admin/feedback-forms">Feedback Forms</a>
            <a href="${pageContext.request.contextPath}/logout" class="logout">Logout</a>
        </nav>
    </aside>

    <main class="main">
        <div class="header">
            <h1>System Administration</h1>
            <div class="user-badge">
                Welcome, <strong>${sessionScope.account.fullName}</strong>
            </div>
        </div>

        <div class="card">
            <h2>Welcome to Teacher Feedback Management System</h2>
            <p>This is the central control panel for administrators. Use the menu on the left to manage the core data of the system.</p>

            <ul class="feature-list">
                <li>
                    <strong>User Management</strong>
                    <span>Create, update, or delete accounts for Students, Teachers, and other Admins. Assign correct roles to ensure system security.</span>
                </li>
                <li>
                    <strong>Course Management</strong>
                    <span>Manage academic courses, update credits, and link them to their respective departments.</span>
                </li>
                <li>
                    <strong>Feedback Forms</strong>
                    <span>Open or close feedback sessions for specific semesters. Set the start and end dates to allow students to submit their evaluations.</span>
                </li>
            </ul>

            <div class="note">
                <strong>Important:</strong> Always ensure data accuracy before deleting records, as it might affect linked feedback data from students and teachers.
            </div>
        </div>
    </main>
</body>
</html>