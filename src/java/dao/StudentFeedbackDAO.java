package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.Criterion;
import model.FeedbackDetailItem;
import model.FeedbackForm;
import model.FeedbackHistory;
import model.StudentClassSection;
import util.ValidationUtils;

public class StudentFeedbackDAO extends DBContext {

    public List<StudentClassSection> getEnrolledClasses(int studentId) {
        List<StudentClassSection> list = new ArrayList<>();
        String query = "SELECT cs.class_section_id, cs.class_code, c.course_code, c.course_name, c.credits, "
                + "s.semester_name, s.academic_year, tu.full_name AS teacher_name, cs.room, "
                + "ISNULL(ff.form_id, 0) AS active_form_id, ff.title AS active_form_title, "
                + "CASE WHEN fb.feedback_id IS NULL THEN 0 ELSE 1 END AS feedback_submitted "
                + "FROM enrollments e "
                + "JOIN class_sections cs ON e.class_section_id = cs.class_section_id "
                + "JOIN courses c ON cs.course_id = c.course_id "
                + "JOIN semesters s ON cs.semester_id = s.semester_id "
                + "JOIN teachers t ON cs.teacher_id = t.teacher_id "
                + "JOIN users tu ON t.teacher_id = tu.user_id "
                + "OUTER APPLY ( "
                + "    SELECT TOP 1 form_id, title "
                + "    FROM feedback_forms "
                + "    WHERE semester_id = cs.semester_id AND is_active = 1 "
                + "    ORDER BY form_id DESC "
                + ") ff "
                + "LEFT JOIN feedbacks fb ON fb.form_id = ff.form_id "
                + "    AND fb.class_section_id = cs.class_section_id "
                + "    AND fb.student_id = e.student_id "
                + "WHERE e.student_id = ? "
                + "ORDER BY s.start_date DESC, c.course_code, cs.class_code";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapClassSection(rs));
            }
        } catch (Exception e) {
            System.out.println("Loi getEnrolledClasses: " + e.getMessage());
        }
        return list;
    }

    public StudentClassSection getClassForStudent(int studentId, int classSectionId) {
        String query = "SELECT cs.class_section_id, cs.class_code, c.course_code, c.course_name, c.credits, "
                + "s.semester_name, s.academic_year, tu.full_name AS teacher_name, cs.room, "
                + "ISNULL(ff.form_id, 0) AS active_form_id, ff.title AS active_form_title, "
                + "CASE WHEN fb.feedback_id IS NULL THEN 0 ELSE 1 END AS feedback_submitted "
                + "FROM enrollments e "
                + "JOIN class_sections cs ON e.class_section_id = cs.class_section_id "
                + "JOIN courses c ON cs.course_id = c.course_id "
                + "JOIN semesters s ON cs.semester_id = s.semester_id "
                + "JOIN teachers t ON cs.teacher_id = t.teacher_id "
                + "JOIN users tu ON t.teacher_id = tu.user_id "
                + "OUTER APPLY ( "
                + "    SELECT TOP 1 form_id, title "
                + "    FROM feedback_forms "
                + "    WHERE semester_id = cs.semester_id AND is_active = 1 "
                + "    ORDER BY form_id DESC "
                + ") ff "
                + "LEFT JOIN feedbacks fb ON fb.form_id = ff.form_id "
                + "    AND fb.class_section_id = cs.class_section_id "
                + "    AND fb.student_id = e.student_id "
                + "WHERE e.student_id = ? AND cs.class_section_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, studentId);
            ps.setInt(2, classSectionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapClassSection(rs);
            }
        } catch (Exception e) {
            System.out.println("Loi getClassForStudent: " + e.getMessage());
        }
        return null;
    }

    public FeedbackForm getActiveFormForClass(int studentId, int classSectionId) {
        String query = "SELECT TOP 1 f.* "
                + "FROM feedback_forms f "
                + "JOIN class_sections cs ON f.semester_id = cs.semester_id "
                + "JOIN enrollments e ON e.class_section_id = cs.class_section_id "
                + "WHERE e.student_id = ? AND cs.class_section_id = ? AND f.is_active = 1 "
                + "ORDER BY f.form_id DESC";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, studentId);
            ps.setInt(2, classSectionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new FeedbackForm(rs.getInt("form_id"), rs.getString("title"),
                        rs.getInt("semester_id"), "", rs.getDate("start_date"),
                        rs.getDate("end_date"), rs.getBoolean("is_active"));
            }
        } catch (Exception e) {
            System.out.println("Loi getActiveFormForClass: " + e.getMessage());
        }
        return null;
    }

    public List<Criterion> getCriteriaByFormId(int formId) {
        List<Criterion> list = new ArrayList<>();
        String query = "SELECT * FROM criteria WHERE form_id = ? ORDER BY criterion_id";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, formId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Criterion(rs.getInt("criterion_id"), rs.getInt("form_id"),
                        rs.getString("title"), rs.getString("description"),
                        rs.getInt("max_score")));
            }
        } catch (Exception e) {
            System.out.println("Loi getCriteriaByFormId: " + e.getMessage());
        }
        return list;
    }

    public boolean hasSubmittedFeedback(int formId, int classSectionId, int studentId) {
        String query = "SELECT 1 FROM feedbacks WHERE form_id = ? AND class_section_id = ? AND student_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, formId);
            ps.setInt(2, classSectionId);
            ps.setInt(3, studentId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Loi hasSubmittedFeedback: " + e.getMessage());
        }
        return true;
    }

    public boolean saveFeedback(int formId, int classSectionId, int studentId,
            String generalComment, Map<Integer, Integer> scores) {
        String normalizedComment = ValidationUtils.trim(generalComment);
        if (!ValidationUtils.isMaxLength(normalizedComment, 1000)) {
            return false;
        }

        String insertFeedback = "INSERT INTO feedbacks (form_id, class_section_id, student_id, general_comment) "
                + "VALUES (?, ?, ?, ?)";
        String insertDetail = "INSERT INTO feedback_details (feedback_id, criterion_id, score) VALUES (?, ?, ?)";

        try {
            connection.setAutoCommit(false);

            if (!isStudentEnrolledInClass(studentId, classSectionId)
                    || !isFormActiveForClass(formId, classSectionId)
                    || hasSubmittedFeedback(formId, classSectionId, studentId)) {
                connection.rollback();
                return false;
            }

            int feedbackId;
            try (PreparedStatement ps = connection.prepareStatement(insertFeedback, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, formId);
                ps.setInt(2, classSectionId);
                ps.setInt(3, studentId);
                ps.setString(4, normalizedComment);
                if (ps.executeUpdate() == 0) {
                    connection.rollback();
                    return false;
                }
                ResultSet keys = ps.getGeneratedKeys();
                if (!keys.next()) {
                    connection.rollback();
                    return false;
                }
                feedbackId = keys.getInt(1);
            }

            try (PreparedStatement ps = connection.prepareStatement(insertDetail)) {
                for (Map.Entry<Integer, Integer> entry : scores.entrySet()) {
                    ps.setInt(1, feedbackId);
                    ps.setInt(2, entry.getKey());
                    ps.setInt(3, entry.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            connection.commit();
            return true;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Loi rollback saveFeedback: " + ex.getMessage());
            }
            System.out.println("Loi saveFeedback: " + e.getMessage());
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Loi reset autoCommit: " + e.getMessage());
            }
        }
    }

    public List<FeedbackHistory> getFeedbackHistory(int studentId) {
        List<FeedbackHistory> list = new ArrayList<>();
        String query = "SELECT f.feedback_id, ff.title AS form_title, cs.class_code, c.course_code, "
                + "c.course_name, tu.full_name AS teacher_name, f.general_comment, f.created_at, "
                + "AVG(CAST(fd.score AS FLOAT)) AS average_score "
                + "FROM feedbacks f "
                + "JOIN feedback_forms ff ON f.form_id = ff.form_id "
                + "JOIN class_sections cs ON f.class_section_id = cs.class_section_id "
                + "JOIN courses c ON cs.course_id = c.course_id "
                + "JOIN teachers t ON cs.teacher_id = t.teacher_id "
                + "JOIN users tu ON t.teacher_id = tu.user_id "
                + "LEFT JOIN feedback_details fd ON f.feedback_id = fd.feedback_id "
                + "WHERE f.student_id = ? "
                + "GROUP BY f.feedback_id, ff.title, cs.class_code, c.course_code, c.course_name, "
                + "tu.full_name, f.general_comment, f.created_at "
                + "ORDER BY f.created_at DESC";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new FeedbackHistory(rs.getInt("feedback_id"),
                        rs.getString("form_title"), rs.getString("class_code"),
                        rs.getString("course_code"), rs.getString("course_name"),
                        rs.getString("teacher_name"), rs.getString("general_comment"),
                        rs.getTimestamp("created_at"), rs.getDouble("average_score")));
            }
        } catch (Exception e) {
            System.out.println("Loi getFeedbackHistory: " + e.getMessage());
        }
        return list;
    }

    public List<FeedbackDetailItem> getFeedbackDetails(int feedbackId, int studentId) {
        List<FeedbackDetailItem> list = new ArrayList<>();
        String query = "SELECT c.title, fd.score, c.max_score "
                + "FROM feedback_details fd "
                + "JOIN criteria c ON fd.criterion_id = c.criterion_id "
                + "JOIN feedbacks f ON fd.feedback_id = f.feedback_id "
                + "WHERE fd.feedback_id = ? AND f.student_id = ? "
                + "ORDER BY c.criterion_id";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, feedbackId);
            ps.setInt(2, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new FeedbackDetailItem(rs.getString("title"),
                        rs.getInt("score"), rs.getInt("max_score")));
            }
        } catch (Exception e) {
            System.out.println("Loi getFeedbackDetails: " + e.getMessage());
        }
        return list;
    }

    private boolean isStudentEnrolledInClass(int studentId, int classSectionId) throws SQLException {
        String query = "SELECT 1 FROM enrollments WHERE student_id = ? AND class_section_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, studentId);
            ps.setInt(2, classSectionId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    private boolean isFormActiveForClass(int formId, int classSectionId) throws SQLException {
        String query = "SELECT 1 "
                + "FROM feedback_forms f "
                + "JOIN class_sections cs ON f.semester_id = cs.semester_id "
                + "WHERE f.form_id = ? AND cs.class_section_id = ? AND f.is_active = 1";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, formId);
            ps.setInt(2, classSectionId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    private StudentClassSection mapClassSection(ResultSet rs) throws SQLException {
        return new StudentClassSection(rs.getInt("class_section_id"),
                rs.getString("class_code"), rs.getString("course_code"),
                rs.getString("course_name"), rs.getInt("credits"),
                rs.getString("semester_name"), rs.getString("academic_year"),
                rs.getString("teacher_name"), rs.getString("room"),
                rs.getInt("active_form_id"), rs.getString("active_form_title"),
                rs.getBoolean("feedback_submitted"));
    }
}
