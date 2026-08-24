package controller;

import dao.TeacherFeedbackDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.TeacherClassOverview;
import model.User;

@WebServlet(name = "TeacherHomeController", urlPatterns = {"/teacher/home"})
public class TeacherHomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User account = getAccount(request);
        if (account == null || account.getRoleId() != 2) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        TeacherFeedbackDAO dao = new TeacherFeedbackDAO();
        List<TeacherClassOverview> classList = dao.getClassesByTeacherId(account.getUserId());
        int totalStudents = 0;
        int totalFeedbacks = 0;
        double totalAverage = 0;
        int scoredClasses = 0;

        for (TeacherClassOverview classItem : classList) {
            totalStudents += classItem.getStudentCount();
            totalFeedbacks += classItem.getFeedbackCount();
            if (classItem.getAverageScore() > 0) {
                totalAverage += classItem.getAverageScore();
                scoredClasses++;
            }
        }

        request.setAttribute("classList", classList);
        request.setAttribute("totalClasses", classList.size());
        request.setAttribute("totalStudents", totalStudents);
        request.setAttribute("totalFeedbacks", totalFeedbacks);
        request.setAttribute("overallAverage", scoredClasses == 0 ? 0 : totalAverage / scoredClasses);
        request.getRequestDispatcher("/teacher/home.jsp").forward(request, response);
    }

    private User getAccount(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute("account");
    }
}
