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
import model.FeedbackDetailItem;
import model.FeedbackHistory;
import model.User;

@WebServlet(name = "StudentHistoryController", urlPatterns = {"/student/history"})
public class StudentHistoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User account = getAccount(request);
        if (account == null || account.getRoleId() != 3) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        StudentFeedbackDAO dao = new StudentFeedbackDAO();
        List<FeedbackHistory> historyList = dao.getFeedbackHistory(account.getUserId());
        int feedbackId = parseInt(request.getParameter("feedbackId"), 0);
        if (feedbackId > 0) {
            List<FeedbackDetailItem> detailList = dao.getFeedbackDetails(feedbackId, account.getUserId());
            request.setAttribute("detailList", detailList);
            request.setAttribute("selectedFeedbackId", feedbackId);
        }

        request.setAttribute("historyList", historyList);
        request.setAttribute("submitted", "1".equals(request.getParameter("submitted")));
        request.getRequestDispatcher("/student/history.jsp").forward(request, response);
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private User getAccount(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute("account");
    }
}
