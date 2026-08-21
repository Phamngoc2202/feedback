package controller;

import dao.UserDAO;
import model.User;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "UserController", urlPatterns = {"/admin/users"})
public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO dao = new UserDAO();
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                if (!dao.deleteUser(id)) {
                    request.setAttribute("error", "Cannot delete this user due to existing related data!");
                }
            } catch (Exception e) {}
        } else if ("edit".equals(action)) {
            // Chuyển sang trang Edit
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                User u = dao.getUserById(id);
                request.setAttribute("userEdit", u);
                request.getRequestDispatcher("/admin/edit_user.jsp").forward(request, response);
                return;
            } catch (Exception e) {}
        }

        // Mặc định load danh sách
        List<User> list = dao.getAllUsers();
        request.setAttribute("userList", list);
        request.getRequestDispatcher("/admin/user_manager.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action"); // Nhận diện Add hay Update
        
        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        int roleId = Integer.parseInt(request.getParameter("roleId"));

        UserDAO dao = new UserDAO();

        if ("update".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("userId"));
            User updateUser = new User(userId, username, pass, fullname, email, roleId);
            dao.updateUser(updateUser);
        } else {
            User newUser = new User(0, username, pass, fullname, email, roleId);
            dao.insertUser(newUser);
        }

        response.sendRedirect(request.getContextPath() + "/admin/users");
    }
}