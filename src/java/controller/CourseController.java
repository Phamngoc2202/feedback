package controller;

import dao.CourseDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Course;
import model.Department;
import util.ValidationUtils;

@WebServlet(name = "CourseController", urlPatterns = {"/admin/courses"})
public class CourseController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CourseDAO dao = new CourseDAO();
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int id = parseInt(request.getParameter("id"), 0);
            if (id > 0 && !dao.deleteCourse(id)) {
                request.setAttribute("error", "Không thể xóa môn học này vì đang được liên kết với lớp học phần.");
            }
        }

        loadData(request, dao);
        request.getRequestDispatcher("/admin/course_manager.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String code = ValidationUtils.trim(request.getParameter("courseCode")).toUpperCase();
        String name = ValidationUtils.trim(request.getParameter("courseName"));
        int credits = parseInt(request.getParameter("credits"), 0);
        int deptId = parseInt(request.getParameter("departmentId"), 0);

        CourseDAO dao = new CourseDAO();
        String error = validateCourse(dao, code, name, credits, deptId);
        if (error != null) {
            request.setAttribute("error", error);
            loadData(request, dao);
            request.getRequestDispatcher("/admin/course_manager.jsp").forward(request, response);
            return;
        }

        Course newCourse = new Course(0, code, name, credits, deptId, "");
        if (!dao.insertCourse(newCourse)) {
            request.setAttribute("error", "Không thể thêm môn học. Vui lòng thử lại.");
            loadData(request, dao);
            request.getRequestDispatcher("/admin/course_manager.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/admin/courses");
    }

    private String validateCourse(CourseDAO dao, String code, String name, int credits, int deptId) {
        if (!ValidationUtils.isLengthBetween(code, 2, 20)) {
            return "Mã môn học phải từ 2 đến 20 ký tự.";
        }
        if (!ValidationUtils.isLengthBetween(name, 2, 100)) {
            return "Tên môn học phải từ 2 đến 100 ký tự.";
        }
        if (!ValidationUtils.isBetween(credits, 1, 10)) {
            return "Số tín chỉ phải từ 1 đến 10.";
        }
        if (!dao.departmentExists(deptId)) {
            return "Khoa không tồn tại.";
        }
        if (dao.courseCodeExists(code)) {
            return "Mã môn học đã tồn tại.";
        }
        return null;
    }

    private void loadData(HttpServletRequest request, CourseDAO dao) {
        List<Course> cList = dao.getAllCourses();
        List<Department> dList = dao.getAllDepartments();
        request.setAttribute("courseList", cList);
        request.setAttribute("deptList", dList);
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
