package controller;

import dao.AdminAcademicDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ClassSection;
import util.ValidationUtils;

@WebServlet(name = "EnrollmentController", urlPatterns = {"/admin/enrollments"})
public class EnrollmentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AdminAcademicDAO dao = new AdminAcademicDAO();
        String action = request.getParameter("action");
        int classSectionId = parseInt(request.getParameter("classSectionId"), 0);
        String q = ValidationUtils.trim(request.getParameter("q"));
        String studentQ = ValidationUtils.trim(request.getParameter("studentQ"));

        if ("delete".equals(action)) {
            int id = parseInt(request.getParameter("id"), 0);
            if (id <= 0) {
                request.setAttribute("error", "Ghi danh không hợp lệ.");
            } else if (dao.enrollmentHasFeedback(id)) {
                request.setAttribute("error", "Không thể xóa ghi danh vì sinh viên đã gửi feedback.");
            } else if (!dao.deleteEnrollment(id)) {
                request.setAttribute("error", "Không thể xóa ghi danh này.");
            }
        }

        loadData(request, dao, classSectionId, q, studentQ);
        request.getRequestDispatcher("/admin/enrollment_manager.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int classSectionId = parseInt(request.getParameter("classSectionId"), 0);
        int studentId = parseInt(request.getParameter("studentId"), 0);

        AdminAcademicDAO dao = new AdminAcademicDAO();
        String error = validateEnrollment(dao, classSectionId, studentId);
        if (error != null || !dao.insertEnrollment(classSectionId, studentId)) {
            request.setAttribute("error", error != null ? error : "Không thể thêm ghi danh. Sinh viên có thể đã thuộc lớp này.");
            loadData(request, dao, classSectionId, "", "");
            request.getRequestDispatcher("/admin/enrollment_manager.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/admin/enrollments?classSectionId=" + classSectionId);
    }

    private String validateEnrollment(AdminAcademicDAO dao, int classSectionId, int studentId) {
        if (!dao.classSectionExists(classSectionId)) {
            return "Lớp học phần không tồn tại.";
        }
        if (!dao.studentExists(studentId)) {
            return "Sinh viên không tồn tại.";
        }
        return null;
    }

    private void loadData(HttpServletRequest request, AdminAcademicDAO dao,
            int classSectionId, String q, String studentQ) {
        List<ClassSection> classSectionList =
                ValidationUtils.isBlank(q) ? dao.getAllClassSections() : dao.searchClassSections(q);
        request.setAttribute("classSectionList", classSectionList);
        request.setAttribute("studentList", dao.getStudentOptions());
        request.setAttribute("q", q);
        request.setAttribute("studentQ", studentQ);

        if (classSectionId > 0) {
            request.setAttribute("selectedClassSection", dao.getClassSectionById(classSectionId));
            request.setAttribute("selectedClassSectionId", classSectionId);
            request.setAttribute("enrollmentList", ValidationUtils.isBlank(studentQ)
                    ? dao.getEnrollmentsByClassSectionId(classSectionId)
                    : dao.searchEnrollmentsByClassSectionId(classSectionId, studentQ));
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
