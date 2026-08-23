package controller;

import dao.UserDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import util.ValidationUtils;

@WebServlet(name = "UserController", urlPatterns = {"/admin/users"})
public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO dao = new UserDAO();
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int id = parseInt(request.getParameter("id"), 0);
            User currentUser = getCurrentUser(request);
            if (currentUser != null && currentUser.getUserId() == id) {
                request.setAttribute("error", "Không thể xóa tài khoản đang đăng nhập.");
            } else if (id > 0 && !dao.deleteUser(id)) {
                request.setAttribute("error", "Không thể xóa tài khoản này vì đang có dữ liệu liên quan.");
            }
        } else if ("edit".equals(action)) {
            int id = parseInt(request.getParameter("id"), 0);
            User u = dao.getUserById(id);
            request.setAttribute("userEdit", u);
            request.getRequestDispatcher("/admin/edit_user.jsp").forward(request, response);
            return;
        }

        List<User> list = dao.getAllUsers();
        request.setAttribute("userList", list);
        request.getRequestDispatcher("/admin/user_manager.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        int roleId = parseInt(request.getParameter("roleId"), 0);
        username = ValidationUtils.trim(username);
        pass = ValidationUtils.trim(pass);
        fullname = ValidationUtils.trim(fullname);
        email = ValidationUtils.trim(email);

        UserDAO dao = new UserDAO();

        if ("update".equals(action)) {
            int userId = parseInt(request.getParameter("userId"), 0);
            String error = validateUser(dao, userId, username, pass, fullname, email, roleId);
            if (error != null) {
                request.setAttribute("error", error);
                request.setAttribute("userEdit", new User(userId, username, pass, fullname, email, roleId));
                request.getRequestDispatcher("/admin/edit_user.jsp").forward(request, response);
                return;
            }
            User updateUser = new User(userId, username, pass, fullname, email, roleId);
            if (!dao.updateUser(updateUser)) {
                request.setAttribute("error", "Không thể cập nhật tài khoản.");
                request.setAttribute("userEdit", updateUser);
                request.getRequestDispatcher("/admin/edit_user.jsp").forward(request, response);
                return;
            }
        } else {
            String error = validateUser(dao, 0, username, pass, fullname, email, roleId);
            if (error != null) {
                request.setAttribute("error", error);
                request.setAttribute("userList", dao.getAllUsers());
                request.getRequestDispatcher("/admin/user_manager.jsp").forward(request, response);
                return;
            }
            User newUser = new User(0, username, pass, fullname, email, roleId);
            if (!dao.insertUser(newUser)) {
                request.setAttribute("error", "Không thể thêm tài khoản.");
                request.setAttribute("userList", dao.getAllUsers());
                request.getRequestDispatcher("/admin/user_manager.jsp").forward(request, response);
                return;
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/users");
    }

    private User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute("account");
    }

    private String validateUser(UserDAO dao, int userId, String username,
            String password, String fullName, String email, int roleId) {
        username = ValidationUtils.trim(username);
        fullName = ValidationUtils.trim(fullName);
        email = ValidationUtils.trim(email);

        if (!ValidationUtils.isLengthBetween(username, 4, 50)) {
            return "Tên đăng nhập phải từ 4 đến 50 ký tự.";
        }
        if (!ValidationUtils.isLengthBetween(password, 3, 255)) {
            return "Mật khẩu phải có ít nhất 3 ký tự.";
        }
        if (!ValidationUtils.isLengthBetween(fullName, 2, 100)) {
            return "Họ và tên phải từ 2 đến 100 ký tự.";
        }
        if (!ValidationUtils.isEmail(email) || !ValidationUtils.isMaxLength(email, 100)) {
            return "Email không hợp lệ.";
        }
        if (!ValidationUtils.isBetween(roleId, 1, 3)) {
            return "Vai trò không hợp lệ.";
        }
        if (dao.usernameExists(username, userId)) {
            return "Tên đăng nhập đã tồn tại.";
        }
        return null;
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
