package controller;

import dao.AdminAcademicDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ClassSection;
import util.ValidationUtils;

@WebServlet(name = "ClassSectionController", urlPatterns = {"/admin/class-sections"})
public class ClassSectionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AdminAcademicDAO dao = new AdminAcademicDAO();
        String action = request.getParameter("action");
        String q = ValidationUtils.trim(request.getParameter("q"));

        if ("delete".equals(action)) {
            int id = parseInt(request.getParameter("id"), 0);
            if (id > 0 && !dao.deleteClassSection(id)) {
                request.setAttribute("error", "Không thể xóa lớp học phần này vì đang có ghi danh hoặc feedback liên quan.");
            }
        }

        loadData(request, dao, q);
        request.getRequestDispatcher("/admin/class_section_manager.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String classCode = ValidationUtils.trim(request.getParameter("classCode")).toUpperCase();
        int courseId = parseInt(request.getParameter("courseId"), 0);
        int semesterId = parseInt(request.getParameter("semesterId"), 0);
        int teacherId = parseInt(request.getParameter("teacherId"), 0);
        String room = ValidationUtils.trim(request.getParameter("room"));

        AdminAcademicDAO dao = new AdminAcademicDAO();
        String error = validateClassSection(dao, classCode, courseId, semesterId, teacherId, room);
        if (error != null) {
            request.setAttribute("error", error);
            loadData(request, dao, "");
            request.getRequestDispatcher("/admin/class_section_manager.jsp").forward(request, response);
            return;
        }

        ClassSection classSection = new ClassSection();
        classSection.setClassCode(classCode);
        classSection.setCourseId(courseId);
        classSection.setSemesterId(semesterId);
        classSection.setTeacherId(teacherId);
        classSection.setRoom(room);

        if (!dao.insertClassSection(classSection)) {
            request.setAttribute("error", "Không thể tạo lớp học phần. Vui lòng thử lại.");
            loadData(request, dao, "");
            request.getRequestDispatcher("/admin/class_section_manager.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/admin/class-sections");
    }

    private String validateClassSection(AdminAcademicDAO dao, String classCode,
            int courseId, int semesterId, int teacherId, String room) {
        if (!ValidationUtils.isLengthBetween(classCode, 2, 50)) {
            return "Mã lớp phải từ 2 đến 50 ký tự.";
        }
        if (!ValidationUtils.isMaxLength(room, 20)) {
            return "Phòng học tối đa 20 ký tự.";
        }
        if (!dao.courseExists(courseId)) {
            return "Môn học không tồn tại.";
        }
        if (!dao.semesterExists(semesterId)) {
            return "Học kỳ không tồn tại.";
        }
        if (!dao.teacherExists(teacherId)) {
            return "Giảng viên không tồn tại.";
        }
        if (dao.classCodeExists(classCode, semesterId)) {
            return "Mã lớp đã tồn tại trong học kỳ này.";
        }
        return null;
    }

    private void loadData(HttpServletRequest request, AdminAcademicDAO dao, String q) {
        request.setAttribute("classSectionList",
                ValidationUtils.isBlank(q) ? dao.getAllClassSections() : dao.searchClassSections(q));
        request.setAttribute("courseList", dao.getCourseOptions());
        request.setAttribute("semesterList", dao.getSemesterOptions());
        request.setAttribute("teacherList", dao.getTeacherOptions());
        request.setAttribute("q", q);
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
