package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardDAO extends DBContext {

    // Đếm tổng số người dùng
    public int getTotalUsers() {
        String query = "SELECT COUNT(*) FROM users";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Lỗi getTotalUsers: " + e.getMessage());
        }
        return 0;
    }

    // Đếm tổng số môn học
    public int getTotalCourses() {
        String query = "SELECT COUNT(*) FROM courses";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Lỗi getTotalCourses: " + e.getMessage());
        }
        return 0;
    }

    // Đếm số đợt khảo sát đang Active (is_active = 1)
    public int getActiveFeedbackForms() {
        String query = "SELECT COUNT(*) FROM feedback_forms WHERE is_active = 1";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Lỗi getActiveFeedbackForms: " + e.getMessage());
        }
        return 0;
    }
}