package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.FeedbackForm;
import model.Semester;
import model.SurveyClassTarget;
import model.SurveyStudentTarget;

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

    public FeedbackForm getFormById(int formId) {
        String query = "SELECT f.*, s.semester_name, s.academic_year "
                + "FROM feedback_forms f "
                + "JOIN semesters s ON f.semester_id = s.semester_id "
                + "WHERE f.form_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, formId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String fullSemesterName = rs.getString("semester_name") + " (" + rs.getString("academic_year") + ")";
                return new FeedbackForm(rs.getInt("form_id"), rs.getString("title"),
                        rs.getInt("semester_id"), fullSemesterName,
                        rs.getDate("start_date"), rs.getDate("end_date"),
                        rs.getBoolean("is_active"));
            }
        } catch (Exception e) {
            System.out.println("Loi getFormById: " + e.getMessage());
        }
        return null;
    }

    public List<SurveyClassTarget> getSurveyClassTargets(int formId) {
        List<SurveyClassTarget> list = new ArrayList<>();
        String query = "SELECT cs.class_section_id, cs.class_code, c.course_code, c.course_name, "
                + "u.full_name AS teacher_name, cs.room, "
                + "COUNT(DISTINCT e.student_id) AS student_count, "
                + "COUNT(DISTINCT fb.feedback_id) AS submitted_count "
                + "FROM feedback_forms ff "
                + "JOIN class_sections cs ON ff.semester_id = cs.semester_id "
                + "JOIN courses c ON cs.course_id = c.course_id "
                + "JOIN teachers t ON cs.teacher_id = t.teacher_id "
                + "JOIN users u ON t.teacher_id = u.user_id "
                + "LEFT JOIN enrollments e ON cs.class_section_id = e.class_section_id "
                + "LEFT JOIN feedbacks fb ON fb.form_id = ff.form_id "
                + "    AND fb.class_section_id = cs.class_section_id "
                + "    AND fb.student_id = e.student_id "
                + "WHERE ff.form_id = ? "
                + "GROUP BY cs.class_section_id, cs.class_code, c.course_code, c.course_name, "
                + "u.full_name, cs.room "
                + "ORDER BY cs.class_code";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, formId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new SurveyClassTarget(rs.getInt("class_section_id"),
                        rs.getString("class_code"), rs.getString("course_code"),
                        rs.getString("course_name"), rs.getString("teacher_name"),
                        rs.getString("room"), rs.getInt("student_count"),
                        rs.getInt("submitted_count")));
            }
        } catch (Exception e) {
            System.out.println("Loi getSurveyClassTargets: " + e.getMessage());
        }
        return list;
    }

    public List<SurveyStudentTarget> getSurveyStudentTargets(int formId) {
        List<SurveyStudentTarget> list = new ArrayList<>();
        String query = "SELECT st.student_id, st.student_code, su.full_name AS student_name, "
                + "cs.class_code, c.course_code, c.course_name, tu.full_name AS teacher_name, "
                + "CASE WHEN fb.feedback_id IS NULL THEN 0 ELSE 1 END AS submitted, "
                + "fb.created_at AS submitted_at "
                + "FROM feedback_forms ff "
                + "JOIN class_sections cs ON ff.semester_id = cs.semester_id "
                + "JOIN courses c ON cs.course_id = c.course_id "
                + "JOIN teachers t ON cs.teacher_id = t.teacher_id "
                + "JOIN users tu ON t.teacher_id = tu.user_id "
                + "JOIN enrollments e ON cs.class_section_id = e.class_section_id "
                + "JOIN students st ON e.student_id = st.student_id "
                + "JOIN users su ON st.student_id = su.user_id "
                + "LEFT JOIN feedbacks fb ON fb.form_id = ff.form_id "
                + "    AND fb.class_section_id = cs.class_section_id "
                + "    AND fb.student_id = st.student_id "
                + "WHERE ff.form_id = ? "
                + "ORDER BY cs.class_code, su.full_name";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, formId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new SurveyStudentTarget(rs.getInt("student_id"),
                        rs.getString("student_code"), rs.getString("student_name"),
                        rs.getString("class_code"), rs.getString("course_code"),
                        rs.getString("course_name"), rs.getString("teacher_name"),
                        rs.getBoolean("submitted"), rs.getTimestamp("submitted_at")));
            }
        } catch (Exception e) {
            System.out.println("Loi getSurveyStudentTargets: " + e.getMessage());
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

    public boolean hasCriteria(int formId) {
        String query = "SELECT 1 FROM criteria WHERE form_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, formId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Loi hasCriteria: " + e.getMessage());
        }
        return false;
    }

    public boolean hasOtherActiveFormInSemester(int semesterId, int excludeFormId) {
        String query = "SELECT 1 FROM feedback_forms WHERE semester_id = ? AND is_active = 1 AND form_id <> ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, semesterId);
            ps.setInt(2, excludeFormId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Loi hasOtherActiveFormInSemester: " + e.getMessage());
        }
        return true;
    }

    public boolean semesterExists(int semesterId) {
        String query = "SELECT 1 FROM semesters WHERE semester_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, semesterId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Loi semesterExists: " + e.getMessage());
        }
        return false;
    }
}
