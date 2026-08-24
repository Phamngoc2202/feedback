package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Course;
import model.Department;

public class CourseDAO extends DBContext {

    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
        String query = "SELECT * FROM departments ORDER BY department_name";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Department(rs.getInt("department_id"),
                        rs.getString("department_code"),
                        rs.getString("department_name")));
            }
        } catch (Exception e) {
            System.out.println("Loi getAllDepartments: " + e.getMessage());
        }
        return list;
    }

    public List<Course> getAllCourses() {
        List<Course> list = new ArrayList<>();
        String query = "SELECT c.*, d.department_name "
                + "FROM courses c "
                + "JOIN departments d ON c.department_id = d.department_id "
                + "ORDER BY c.course_code";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapCourse(rs));
            }
        } catch (Exception e) {
            System.out.println("Loi getAllCourses: " + e.getMessage());
        }
        return list;
    }

    public List<Course> searchCourses(String keyword) {
        List<Course> list = new ArrayList<>();
        String query = "SELECT c.*, d.department_name "
                + "FROM courses c "
                + "JOIN departments d ON c.department_id = d.department_id "
                + "WHERE c.course_code LIKE ? OR c.course_name LIKE ? OR d.department_name LIKE ? "
                + "ORDER BY c.course_code";
        String pattern = "%" + keyword + "%";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, pattern);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapCourse(rs));
            }
        } catch (Exception e) {
            System.out.println("Loi searchCourses: " + e.getMessage());
        }
        return list;
    }

    public boolean insertCourse(Course c) {
        String query = "INSERT INTO courses (course_code, course_name, credits, department_id) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, c.getCourseCode());
            ps.setString(2, c.getCourseName());
            ps.setInt(3, c.getCredits());
            ps.setInt(4, c.getDepartmentId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi insertCourse: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCourse(int courseId) {
        String query = "DELETE FROM courses WHERE course_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, courseId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi deleteCourse: " + e.getMessage());
            return false;
        }
    }

    public boolean courseCodeExists(String courseCode) {
        String query = "SELECT 1 FROM courses WHERE course_code = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, courseCode);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Loi courseCodeExists: " + e.getMessage());
        }
        return true;
    }

    public boolean departmentExists(int departmentId) {
        String query = "SELECT 1 FROM departments WHERE department_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, departmentId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Loi departmentExists: " + e.getMessage());
        }
        return false;
    }

    private Course mapCourse(ResultSet rs) throws Exception {
        return new Course(rs.getInt("course_id"), rs.getString("course_code"),
                rs.getString("course_name"), rs.getInt("credits"),
                rs.getInt("department_id"), rs.getString("department_name"));
    }
}
