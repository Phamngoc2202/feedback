package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Course;
import model.Department;

public class CourseDAO extends DBContext {
    
    // Lấy danh sách các Khoa để đổ vào thẻ <select> khi thêm môn học
    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
        String query = "SELECT * FROM departments";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Department(rs.getInt("department_id"), 
                                        rs.getString("department_code"), 
                                        rs.getString("department_name")));
            }
        } catch (Exception e) {
            System.out.println("Lỗi getAllDepartments: " + e.getMessage());
        }
        return list;
    }

    // Lấy danh sách Môn học (Join với bảng departments để lấy tên khoa)
    public List<Course> getAllCourses() {
        List<Course> list = new ArrayList<>();
        String query = "SELECT c.*, d.department_name FROM courses c JOIN departments d ON c.department_id = d.department_id";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Course(rs.getInt("course_id"), 
                                    rs.getString("course_code"), 
                                    rs.getString("course_name"), 
                                    rs.getInt("credits"), 
                                    rs.getInt("department_id"), 
                                    rs.getString("department_name")));
            }
        } catch (Exception e) {
            System.out.println("Lỗi getAllCourses: " + e.getMessage());
        }
        return list;
    }

    // Thêm môn học mới
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
            System.out.println("Lỗi insertCourse: " + e.getMessage());
            return false;
        }
    }

    // Xóa môn học
    public boolean deleteCourse(int courseId) {
        String query = "DELETE FROM courses WHERE course_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, courseId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Lỗi deleteCourse: " + e.getMessage());
            return false;
        }
    }
}