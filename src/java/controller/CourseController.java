package controller;

import dao.CourseDAO;
import model.Course;
import model.Department;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "CourseController", urlPatterns = {"/admin/courses"})
public class CourseController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CourseDAO dao = new CourseDAO();
        String action = request.getParameter("action");

        // Xử lý Xóa
        if ("delete".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                if (!dao.deleteCourse(id)) {
                    request.setAttribute("error", "Cannot delete this course because it is linked to active classes!");
                }
            } catch (Exception e) {}
        }

        // Tải dữ liệu Môn học và Khoa
        List<Course> cList = dao.getAllCourses();
        List<Department> dList = dao.getAllDepartments();
        
        request.setAttribute("courseList", cList);
        request.setAttribute("deptList", dList);
        request.getRequestDispatcher("/admin/course_manager.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String code = request.getParameter("courseCode");
        String name = request.getParameter("courseName");
        int credits = Integer.parseInt(request.getParameter("credits"));
        int deptId = Integer.parseInt(request.getParameter("departmentId"));

        CourseDAO dao = new CourseDAO();
        Course newCourse = new Course(0, code, name, credits, deptId, "");
        dao.insertCourse(newCourse);

        response.sendRedirect(request.getContextPath() + "/admin/courses");
    }
}