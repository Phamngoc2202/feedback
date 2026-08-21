<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>System Login</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
            min-height: 100vh;
            display: flex;
            background: #0f172a;
        }

        /* Left panel */
        .left-panel {
            flex: 1.1;
            background: linear-gradient(160deg, #0f172a 0%, #1e3a8a 55%, #1e40af 100%);
            display: flex;
            flex-direction: column;
            justify-content: center;
            padding: 70px 80px;
            color: #f8fafc;
        }

        .left-panel .brand {
            font-size: 15px;
            font-weight: 600;
            letter-spacing: 1.5px;
            text-transform: uppercase;
            color: #93c5fd;
            margin-bottom: 28px;
        }

        .left-panel h1 {
            font-size: 42px;
            font-weight: 700;
            line-height: 1.25;
            margin-bottom: 20px;
            max-width: 480px;
        }

        .left-panel p {
            font-size: 17px;
            line-height: 1.7;
            color: #cbd5e1;
            max-width: 420px;
        }

        /* Right panel */
        .right-panel {
            width: 520px;
            background: #ffffff;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 50px 60px;
        }

        .login-box {
            width: 100%;
            max-width: 380px;
        }

        .login-box h2 {
            font-size: 28px;
            font-weight: 700;
            color: #111827;
            margin-bottom: 10px;
        }

        .login-box .subtitle {
            font-size: 15px;
            color: #6b7280;
            margin-bottom: 32px;
        }

        .error-box {
            background: #fef2f2;
            border: 1px solid #fecaca;
            color: #b91c1c;
            padding: 12px 16px;
            border-radius: 8px;
            font-size: 14px;
            margin-bottom: 22px;
            text-align: center;
        }

        .form-group {
            margin-bottom: 22px;
        }

        .form-group label {
            display: block;
            font-size: 14px;
            font-weight: 600;
            color: #374151;
            margin-bottom: 8px;
        }

        .form-group input {
            width: 100%;
            padding: 13px 16px;
            border: 1.5px solid #d1d5db;
            border-radius: 8px;
            font-size: 15px;
            color: #111827;
            background: #fff;
            transition: border-color 0.15s ease, box-shadow 0.15s ease;
        }

        .form-group input:focus {
            outline: none;
            border-color: #1e40af;
            box-shadow: 0 0 0 3px rgba(30, 64, 175, 0.15);
        }

        .btn-login {
            width: 100%;
            padding: 14px;
            background: #1e40af;
            color: #ffffff;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.15s ease;
            margin-top: 10px;
        }

        .btn-login:hover {
            background: #1e3a8a;
        }

        .footer-text {
            margin-top: 36px;
            text-align: center;
            font-size: 13px;
            color: #9ca3af;
        }

        @media (max-width: 960px) {
            body {
                flex-direction: column;
            }
            .left-panel {
                padding: 48px 32px;
                min-height: 260px;
            }
            .left-panel h1 {
                font-size: 32px;
            }
            .left-panel p {
                font-size: 15px;
            }
            .right-panel {
                width: 100%;
                flex: 1;
                padding: 40px 28px;
            }
        }
    </style>
</head>
<body>
    <div class="left-panel">
        <div class="brand">Feedback System</div>
        <h1>Teacher Feedback<br>Management</h1>
        <p>Secure access for administrators, teachers and students. Manage courses, feedback periods and evaluation results in one place.</p>
    </div>

    <div class="right-panel">
        <div class="login-box">
            <h2>Sign in</h2>
            <p class="subtitle">Enter your credentials to continue</p>

            <%-- Chỉ hiện khi có lỗi --%>
            <% if (request.getAttribute("error") != null) { %>
                <div class="error-box">${requestScope.error}</div>
            <% } %>

            <form action="${pageContext.request.contextPath}/login" method="POST">
                <div class="form-group">
                    <label>Username</label>
                    <input type="text" name="username" value="${requestScope.username}" required autofocus>
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <input type="password" name="password" required>
                </div>

                <button type="submit" class="btn-login">Login</button>
            </form>

            <p class="footer-text">© Feedback Management System</p>
        </div>
    </div>
</body>
</html>