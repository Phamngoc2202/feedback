package controller;

import dao.TeacherFeedbackDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FeedbackDetailItem;
import model.TeacherClassOverview;
import model.TeacherFeedbackComment;
import model.TeacherStudentFeedbackStatus;
import model.User;
import util.ValidationUtils;

@WebServlet(name = "TeacherFeedbackController", urlPatterns = {"/teacher/feedback"})
public class TeacherFeedbackController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User account = getAccount(request);
        if (account == null || account.getRoleId() != 2) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int classSectionId = parseInt(request.getParameter("classSectionId"), 0);
        if (classSectionId <= 0) {
            response.sendRedirect(request.getContextPath() + "/teacher/home");
            return;
        }

        TeacherFeedbackDAO dao = new TeacherFeedbackDAO();
        loadData(request, dao, account.getUserId(), classSectionId);
        if (request.getAttribute("classSection") == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        request.setAttribute("replySuccess", "1".equals(request.getParameter("replySuccess")));
        request.getRequestDispatcher("/teacher/feedback_result.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        User account = getAccount(request);
        if (account == null || account.getRoleId() != 2) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int classSectionId = parseInt(request.getParameter("classSectionId"), 0);
        int feedbackId = parseInt(request.getParameter("feedbackId"), 0);
        String content = ValidationUtils.trim(request.getParameter("content"));

        TeacherFeedbackDAO dao = new TeacherFeedbackDAO();
        String error = validateReply(classSectionId, feedbackId, content);
        if (error != null) {
            request.setAttribute("error", error);
            loadData(request, dao, account.getUserId(), classSectionId);
            request.getRequestDispatcher("/teacher/feedback_result.jsp").forward(request, response);
            return;
        }

        boolean inserted = dao.insertReply(account.getUserId(), feedbackId, content);
        if (!inserted) {
            request.setAttribute("error", "Không thể gửi phản hồi. Vui lòng kiểm tra nội dung trả lời.");
            loadData(request, dao, account.getUserId(), classSectionId);
            request.getRequestDispatcher("/teacher/feedback_result.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath()
                + "/teacher/feedback?classSectionId=" + classSectionId + "&replySuccess=1");
    }

    private String validateReply(int classSectionId, int feedbackId, String content) {
        if (classSectionId <= 0 || feedbackId <= 0) {
            return "Feedback không hợp lệ.";
        }
        if (ValidationUtils.isBlank(content)) {
            return "Nội dung phản hồi không được để trống.";
        }
        if (!ValidationUtils.isMaxLength(content, 1000)) {
            return "Nội dung phản hồi không được vượt quá 1000 ký tự.";
        }
        return null;
    }

    private void loadData(HttpServletRequest request, TeacherFeedbackDAO dao,
            int teacherId, int classSectionId) {
        TeacherClassOverview classSection = dao.getClassByTeacherId(teacherId, classSectionId);
        request.setAttribute("classSection", classSection);
        if (classSection != null) {
            List<TeacherStudentFeedbackStatus> studentStatuses =
                    dao.getStudentFeedbackStatuses(teacherId, classSectionId);
            int submittedCount = 0;
            for (TeacherStudentFeedbackStatus status : studentStatuses) {
                if (status.isSubmitted()) {
                    submittedCount++;
                }
            }

            String replyStatus = request.getParameter("replyStatus");
            List<TeacherFeedbackComment> feedbackComments =
                    filterFeedbackComments(dao.getFeedbackComments(teacherId, classSectionId), replyStatus);
            Map<Integer, List<FeedbackDetailItem>> feedbackDetailMap = new HashMap<>();
            for (TeacherFeedbackComment comment : feedbackComments) {
                feedbackDetailMap.put(comment.getFeedbackId(),
                        dao.getFeedbackDetailsForTeacher(teacherId, comment.getFeedbackId()));
            }
            long completionRate = studentStatuses.isEmpty()
                    ? 0
                    : Math.round(submittedCount * 100.0 / studentStatuses.size());

            request.setAttribute("criterionStats", dao.getCriterionStats(teacherId, classSectionId));
            request.setAttribute("feedbackComments", feedbackComments);
            request.setAttribute("feedbackDetailMap", feedbackDetailMap);
            request.setAttribute("studentStatuses", studentStatuses);
            request.setAttribute("submittedCount", submittedCount);
            request.setAttribute("pendingCount", studentStatuses.size() - submittedCount);
            request.setAttribute("completionRate", completionRate);
            request.setAttribute("replyStatus", replyStatus == null ? "all" : replyStatus);
        }
    }

    private List<TeacherFeedbackComment> filterFeedbackComments(
            List<TeacherFeedbackComment> comments, String replyStatus) {
        if (replyStatus == null || replyStatus.isEmpty() || "all".equals(replyStatus)) {
            return comments;
        }

        List<TeacherFeedbackComment> result = new ArrayList<>();
        for (TeacherFeedbackComment comment : comments) {
            boolean hasReply = !ValidationUtils.isBlank(comment.getReplyContent());
            if ("replied".equals(replyStatus) && hasReply) {
                result.add(comment);
            } else if ("unreplied".equals(replyStatus) && !hasReply) {
                result.add(comment);
            }
        }
        return result;
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
