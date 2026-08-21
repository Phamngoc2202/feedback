package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.FeedbackForm;
import model.Semester;

public class FeedbackFormDAO extends DBContext {

    // Lấy danh sách Học kỳ để đổ vào Dropdown list
    public List<Semester> getAllSemesters() {
        List<Semester> list = new ArrayList<>();
        String query = "SELECT * FROM semesters ORDER BY start_date DESC";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Semester(rs.getInt("semester_id"), rs.getString("semester_name"),
                        rs.getString("academic_year"), rs.getDate("start_date"), rs.getDate("end_date")));
            }
        } catch (Exception e) {
            System.out.println("Lỗi getAllSemesters: " + e.getMessage());
        }
        return list;
    }

    // Lấy danh sách Đợt khảo sát (JOIN với semesters để lấy tên học kỳ)
    public List<FeedbackForm> getAllForms() {
        List<FeedbackForm> list = new ArrayList<>();
        String query = "SELECT f.*, s.semester_name, s.academic_year FROM feedback_forms f JOIN semesters s ON f.semester_id = s.semester_id ORDER BY f.form_id DESC";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String fullSemesterName = rs.getString("semester_name") + " (" + rs.getString("academic_year") + ")";
                list.add(new FeedbackForm(
                        rs.getInt("form_id"),
                        rs.getString("title"),
                        rs.getInt("semester_id"),
                        fullSemesterName,
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getBoolean("is_active")
                ));
            }
        } catch (Exception e) {
            System.out.println("Lỗi getAllForms: " + e.getMessage());
        }
        return list;
    }

    // Thêm Đợt khảo sát mới
    public boolean insertForm(FeedbackForm f) {
        String query = "INSERT INTO feedback_forms (title, semester_id, start_date, end_date, is_active) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, f.getTitle());
            ps.setInt(2, f.getSemesterId());
            ps.setDate(3, f.getStartDate());
            ps.setDate(4, f.getEndDate());
            ps.setBoolean(5, f.isIsActive());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Lỗi insertForm: " + e.getMessage());
            return false;
        }
    }

    // Đổi trạng thái Bật/Tắt khảo sát
    public boolean toggleStatus(int formId, int currentStatus) {
        String query = "UPDATE feedback_forms SET is_active = ? WHERE form_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, currentStatus == 1 ? 0 : 1); // Đảo ngược trạng thái
            ps.setInt(2, formId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Lỗi toggleStatus: " + e.getMessage());
            return false;
        }
    }
}