package controller;

import dao.DashboardDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Chú ý URL pattern ở đây là /admin/dashboard
@WebServlet(name = "DashboardController", urlPatterns = {"/admin/dashboard"})
public class DashboardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DashboardDAO dao = new DashboardDAO();
        
        // Lấy số liệu và đặt vào Attribute
        request.setAttribute("totalUsers", dao.getTotalUsers());
        request.setAttribute("totalCourses", dao.getTotalCourses());
        request.setAttribute("activeForms", dao.getActiveFeedbackForms());
        
        // Chuyển hướng tới file giao diện home.jsp
        request.getRequestDispatcher("/admin/home.jsp").forward(request, response);
    }
}