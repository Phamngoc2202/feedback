package controller;

import dao.FeedbackFormDAO;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.FeedbackForm;
import model.Semester;
import model.SurveyClassTarget;
import model.SurveyStudentTarget;
import util.ValidationUtils;

@WebServlet(name = "FeedbackFormController", urlPatterns = {"/admin/feedback-forms"})
public class FeedbackFormController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FeedbackFormDAO dao = new FeedbackFormDAO();
        String action = request.getParameter("action");

        if ("targets".equals(action)) {
            int id = parseInt(request.getParameter("id"), 0);
            FeedbackForm form = dao.getFormById(id);
            if (form == null) {
                response.sendRedirect(request.getContextPath() + "/admin/feedback-forms");
                return;
            }

            List<SurveyClassTarget> classTargets = dao.getSurveyClassTargets(id);
            List<SurveyStudentTarget> studentTargets = dao.getSurveyStudentTargets(id);
            int submittedCount = 0;
            for (SurveyStudentTarget student : studentTargets) {
                if (student.isSubmitted()) {
                    submittedCount++;
                }
            }

            request.setAttribute("form", form);
            request.setAttribute("classTargets", classTargets);
            request.setAttribute("studentTargets", studentTargets);
            request.setAttribute("classCount", classTargets.size());
            request.setAttribute("studentCount", studentTargets.size());
            request.setAttribute("submittedCount", submittedCount);
            request.setAttribute("pendingCount", studentTargets.size() - submittedCount);
            request.getRequestDispatcher("/admin/feedback_form_targets.jsp").forward(request, response);
            return;
        }

        if ("toggle".equals(action)) {
            int id = parseInt(request.getParameter("id"), 0);
            int currentStatus = parseInt(request.getParameter("status"), 0);
            FeedbackForm form = dao.getFormById(id);
            if (form == null) {
                response.sendRedirect(request.getContextPath() + "/admin/feedback-forms");
                return;
            }
            if (currentStatus == 0 && dao.hasOtherActiveFormInSemester(form.getSemesterId(), id)) {
                request.setAttribute("error", "Học kỳ này đã có một khảo sát đang mở.");
                loadList(request, dao);
                request.getRequestDispatcher("/admin/feedback_form_manager.jsp").forward(request, response);
                return;
            }
            if (currentStatus == 0 && !dao.hasCriteria(id)) {
                request.setAttribute("error", "Không thể mở khảo sát vì chưa có tiêu chí đánh giá.");
                loadList(request, dao);
                request.getRequestDispatcher("/admin/feedback_form_manager.jsp").forward(request, response);
                return;
            }
            if (id > 0) {
                dao.toggleStatus(id, currentStatus);
            }
            response.sendRedirect(request.getContextPath() + "/admin/feedback-forms");
            return;
        }

        loadList(request, dao);
        request.getRequestDispatcher("/admin/feedback_form_manager.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String title = ValidationUtils.trim(request.getParameter("title"));
        int semesterId = parseInt(request.getParameter("semesterId"), 0);
        Date startDate = parseDate(request.getParameter("startDate"));
        Date endDate = parseDate(request.getParameter("endDate"));

        FeedbackFormDAO dao = new FeedbackFormDAO();
        String error = validateForm(dao, title, semesterId, startDate, endDate);
        if (error != null) {
            request.setAttribute("error", error);
            loadList(request, dao);
            request.getRequestDispatcher("/admin/feedback_form_manager.jsp").forward(request, response);
            return;
        }

        FeedbackForm newForm = new FeedbackForm(0, title, semesterId, "", startDate, endDate, false);
        if (!dao.insertForm(newForm)) {
            request.setAttribute("error", "Không thể tạo đợt khảo sát.");
            loadList(request, dao);
            request.getRequestDispatcher("/admin/feedback_form_manager.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/admin/feedback-forms");
    }

    private String validateForm(FeedbackFormDAO dao, String title, int semesterId,
            Date startDate, Date endDate) {
        if (!ValidationUtils.isLengthBetween(title, 5, 200)) {
            return "Tiêu đề khảo sát phải từ 5 đến 200 ký tự.";
        }
        if (!dao.semesterExists(semesterId)) {
            return "Học kỳ không tồn tại.";
        }
        if (startDate == null || endDate == null) {
            return "Ngày bắt đầu và ngày kết thúc là bắt buộc.";
        }
        if (endDate.before(startDate)) {
            return "Ngày kết thúc phải sau hoặc bằng ngày bắt đầu.";
        }
        return null;
    }

    private void loadList(HttpServletRequest request, FeedbackFormDAO dao) {
        List<FeedbackForm> fList = dao.getAllForms();
        List<Semester> sList = dao.getAllSemesters();
        request.setAttribute("formList", fList);
        request.setAttribute("semesterList", sList);
    }

    private Date parseDate(String value) {
        try {
            return Date.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
