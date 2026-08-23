package controller;

import dao.StudentFeedbackDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.StudentClassSection;
import model.User;

@WebServlet(name = "StudentHomeController", urlPatterns = {"/student/home"})
public class StudentHomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User account = getAccount(request);
        if (account == null || account.getRoleId() != 3) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        StudentFeedbackDAO dao = new StudentFeedbackDAO();
        List<StudentClassSection> classList = dao.getEnrolledClasses(account.getUserId());

        request.setAttribute("classList", classList);
        request.getRequestDispatcher("/student/home.jsp").forward(request, response);
    }

    private User getAccount(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute("account");
    }
}
