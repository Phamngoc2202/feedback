package controller;

import dao.StudentFeedbackDAO;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Criterion;
import model.FeedbackForm;
import model.StudentClassSection;
import model.User;
import util.ValidationUtils;

@WebServlet(name = "StudentFeedbackController", urlPatterns = {"/student/feedback"})
public class StudentFeedbackController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User account = getAccount(request);
        if (account == null || account.getRoleId() != 3) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int classSectionId = parseInt(request.getParameter("classSectionId"), 0);
        if (classSectionId <= 0) {
            response.sendRedirect(request.getContextPath() + "/student/home");
            return;
        }

        StudentFeedbackDAO dao = new StudentFeedbackDAO();
        StudentClassSection classSection = dao.getClassForStudent(account.getUserId(), classSectionId);
        if (classSection == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        if (classSection.isFeedbackSubmitted()) {
            response.sendRedirect(request.getContextPath() + "/student/history");
            return;
        }

        FeedbackForm form = dao.getActiveFormForClass(account.getUserId(), classSectionId);
        if (form == null) {
            request.setAttribute("error", "Lớp học này hiện chưa có đợt khảo sát đang mở.");
            request.setAttribute("classSection", classSection);
            request.getRequestDispatcher("/student/feedback_form.jsp").forward(request, response);
            return;
        }

        List<Criterion> criteria = dao.getCriteriaByFormId(form.getFormId());
        request.setAttribute("classSection", classSection);
        request.setAttribute("form", form);
        request.setAttribute("criteriaList", criteria);
        request.getRequestDispatcher("/student/feedback_form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        User account = getAccount(request);
        if (account == null || account.getRoleId() != 3) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int classSectionId = parseInt(request.getParameter("classSectionId"), 0);
        int formId = parseInt(request.getParameter("formId"), 0);
        StudentFeedbackDAO dao = new StudentFeedbackDAO();
        StudentClassSection classSection = dao.getClassForStudent(account.getUserId(), classSectionId);
        FeedbackForm form = dao.getActiveFormForClass(account.getUserId(), classSectionId);

        if (classSection == null || form == null || form.getFormId() != formId) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        List<Criterion> criteria = dao.getCriteriaByFormId(formId);
        Map<Integer, Integer> scores = new LinkedHashMap<>();
        String error = validateAndCollectScores(request, criteria, scores);
        String generalComment = ValidationUtils.trim(request.getParameter("generalComment"));
        if (error == null && !ValidationUtils.isMaxLength(generalComment, 1000)) {
            error = "Góp ý không được vượt quá 1000 ký tự.";
        }

        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("classSection", classSection);
            request.setAttribute("form", form);
            request.setAttribute("criteriaList", criteria);
            request.setAttribute("generalComment", generalComment);
            request.getRequestDispatcher("/student/feedback_form.jsp").forward(request, response);
            return;
        }

        boolean saved = dao.saveFeedback(formId, classSectionId, account.getUserId(), generalComment, scores);
        if (!saved) {
            request.setAttribute("error", "Không thể lưu feedback. Có thể bạn đã gửi đánh giá cho lớp này.");
            request.setAttribute("classSection", classSection);
            request.setAttribute("form", form);
            request.setAttribute("criteriaList", criteria);
            request.setAttribute("generalComment", generalComment);
            request.getRequestDispatcher("/student/feedback_form.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/student/history?submitted=1");
    }

    private String validateAndCollectScores(HttpServletRequest request,
            List<Criterion> criteria, Map<Integer, Integer> scores) {
        if (criteria == null || criteria.isEmpty()) {
            return "Đợt khảo sát này chưa có tiêu chí đánh giá.";
        }

        for (Criterion criterion : criteria) {
            if (criterion.getMaxScore() < 1) {
                return "Tiêu chí đánh giá chưa có thang điểm hợp lệ.";
            }
            int score = parseInt(request.getParameter("score_" + criterion.getCriterionId()), 0);
            if (score < 1 || score > criterion.getMaxScore()) {
                return "Vui lòng chọn điểm hợp lệ cho tất cả tiêu chí.";
            }
            scores.put(criterion.getCriterionId(), score);
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

    private User getAccount(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute("account");
    }
}
