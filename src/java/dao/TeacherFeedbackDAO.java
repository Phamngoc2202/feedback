package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.FeedbackDetailItem;
import model.TeacherClassOverview;
import model.TeacherCriterionStat;
import model.TeacherFeedbackComment;
import model.TeacherStudentFeedbackStatus;
import util.ValidationUtils;

public class TeacherFeedbackDAO extends DBContext {

    public List<TeacherClassOverview> getClassesByTeacherId(int teacherId) {
        List<TeacherClassOverview> list = new ArrayList<>();
        String query = "SELECT cs.class_section_id, cs.class_code, c.course_code, c.course_name, "
                + "s.semester_name, s.academic_year, cs.room, "
                + "COUNT(DISTINCT e.enrollment_id) AS student_count, "
                + "COUNT(DISTINCT f.feedback_id) AS feedback_count, "
                + "ISNULL(AVG(CAST(fd.score AS FLOAT)), 0) AS average_score "
                + "FROM class_sections cs "
                + "JOIN courses c ON cs.course_id = c.course_id "
                + "JOIN semesters s ON cs.semester_id = s.semester_id "
                + "LEFT JOIN enrollments e ON cs.class_section_id = e.class_section_id "
                + "LEFT JOIN feedbacks f ON cs.class_section_id = f.class_section_id "
                + "LEFT JOIN feedback_details fd ON f.feedback_id = fd.feedback_id "
                + "WHERE cs.teacher_id = ? "
                + "GROUP BY cs.class_section_id, cs.class_code, c.course_code, c.course_name, "
                + "s.semester_name, s.academic_year, s.start_date, cs.room "
                + "ORDER BY s.start_date DESC, cs.class_code";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, teacherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapClassOverview(rs));
            }
        } catch (Exception e) {
            System.out.println("Loi getClassesByTeacherId: " + e.getMessage());
        }
        return list;
    }

    public TeacherClassOverview getClassByTeacherId(int teacherId, int classSectionId) {
        String query = "SELECT cs.class_section_id, cs.class_code, c.course_code, c.course_name, "
                + "s.semester_name, s.academic_year, cs.room, "
                + "COUNT(DISTINCT e.enrollment_id) AS student_count, "
                + "COUNT(DISTINCT f.feedback_id) AS feedback_count, "
                + "ISNULL(AVG(CAST(fd.score AS FLOAT)), 0) AS average_score "
                + "FROM class_sections cs "
                + "JOIN courses c ON cs.course_id = c.course_id "
                + "JOIN semesters s ON cs.semester_id = s.semester_id "
                + "LEFT JOIN enrollments e ON cs.class_section_id = e.class_section_id "
                + "LEFT JOIN feedbacks f ON cs.class_section_id = f.class_section_id "
                + "LEFT JOIN feedback_details fd ON f.feedback_id = fd.feedback_id "
                + "WHERE cs.teacher_id = ? AND cs.class_section_id = ? "
                + "GROUP BY cs.class_section_id, cs.class_code, c.course_code, c.course_name, "
                + "s.semester_name, s.academic_year, cs.room";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, teacherId);
            ps.setInt(2, classSectionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapClassOverview(rs);
            }
        } catch (Exception e) {
            System.out.println("Loi getClassByTeacherId: " + e.getMessage());
        }
        return null;
    }

    public List<TeacherCriterionStat> getCriterionStats(int teacherId, int classSectionId) {
        List<TeacherCriterionStat> list = new ArrayList<>();
        String query = "SELECT c.title, c.max_score, COUNT(fd.detail_id) AS response_count, "
                + "ISNULL(AVG(CAST(fd.score AS FLOAT)), 0) AS average_score "
                + "FROM criteria c "
                + "JOIN feedback_forms ff ON c.form_id = ff.form_id "
                + "JOIN feedbacks f ON f.form_id = ff.form_id "
                + "JOIN class_sections cs ON f.class_section_id = cs.class_section_id "
                + "LEFT JOIN feedback_details fd ON fd.feedback_id = f.feedback_id "
                + "    AND fd.criterion_id = c.criterion_id "
                + "WHERE cs.teacher_id = ? AND cs.class_section_id = ? "
                + "GROUP BY c.criterion_id, c.title, c.max_score "
                + "ORDER BY c.criterion_id";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, teacherId);
            ps.setInt(2, classSectionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new TeacherCriterionStat(rs.getString("title"),
                        rs.getInt("max_score"), rs.getInt("response_count"),
                        rs.getDouble("average_score")));
            }
        } catch (Exception e) {
            System.out.println("Loi getCriterionStats: " + e.getMessage());
        }
        return list;
    }

    public List<TeacherFeedbackComment> getFeedbackComments(int teacherId, int classSectionId) {
        List<TeacherFeedbackComment> list = new ArrayList<>();
        String query = "SELECT f.feedback_id, st.student_code, su.full_name AS student_name, "
                + "f.general_comment, f.created_at, "
                + "ISNULL(AVG(CAST(fd.score AS FLOAT)), 0) AS average_score, "
                + "reply.content AS reply_content, reply.created_at AS replied_at "
                + "FROM feedbacks f "
                + "JOIN class_sections cs ON f.class_section_id = cs.class_section_id "
                + "JOIN students st ON f.student_id = st.student_id "
                + "JOIN users su ON st.student_id = su.user_id "
                + "LEFT JOIN feedback_details fd ON f.feedback_id = fd.feedback_id "
                + "OUTER APPLY ( "
                + "    SELECT TOP 1 content, created_at "
                + "    FROM feedback_replies "
                + "    WHERE feedback_id = f.feedback_id AND user_id = ? "
                + "    ORDER BY created_at DESC "
                + ") reply "
                + "WHERE cs.teacher_id = ? AND cs.class_section_id = ? "
                + "GROUP BY f.feedback_id, st.student_code, su.full_name, f.general_comment, "
                + "f.created_at, reply.content, reply.created_at "
                + "ORDER BY f.created_at DESC";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, teacherId);
            ps.setInt(2, teacherId);
            ps.setInt(3, classSectionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new TeacherFeedbackComment(rs.getInt("feedback_id"),
                        rs.getString("student_code"), rs.getString("student_name"),
                        rs.getString("general_comment"), rs.getTimestamp("created_at"),
                        rs.getDouble("average_score"), rs.getString("reply_content"),
                        rs.getTimestamp("replied_at")));
            }
        } catch (Exception e) {
            System.out.println("Loi getFeedbackComments: " + e.getMessage());
        }
        return list;
    }

    public List<TeacherStudentFeedbackStatus> getStudentFeedbackStatuses(int teacherId, int classSectionId) {
        List<TeacherStudentFeedbackStatus> list = new ArrayList<>();
        String query = "SELECT st.student_id, st.student_code, su.full_name AS student_name, su.email, "
                + "CASE WHEN fb.feedback_id IS NULL THEN 0 ELSE 1 END AS submitted, "
                + "fb.created_at AS submitted_at, "
                + "ISNULL(AVG(CAST(fd.score AS FLOAT)), 0) AS average_score "
                + "FROM enrollments e "
                + "JOIN class_sections cs ON e.class_section_id = cs.class_section_id "
                + "JOIN students st ON e.student_id = st.student_id "
                + "JOIN users su ON st.student_id = su.user_id "
                + "OUTER APPLY ( "
                + "    SELECT TOP 1 feedback_id, created_at "
                + "    FROM feedbacks "
                + "    WHERE class_section_id = e.class_section_id AND student_id = e.student_id "
                + "    ORDER BY created_at DESC "
                + ") fb "
                + "LEFT JOIN feedback_details fd ON fb.feedback_id = fd.feedback_id "
                + "WHERE cs.teacher_id = ? AND cs.class_section_id = ? "
                + "GROUP BY st.student_id, st.student_code, su.full_name, su.email, "
                + "fb.feedback_id, fb.created_at "
                + "ORDER BY submitted ASC, su.full_name";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, teacherId);
            ps.setInt(2, classSectionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new TeacherStudentFeedbackStatus(rs.getInt("student_id"),
                        rs.getString("student_code"), rs.getString("student_name"),
                        rs.getString("email"), rs.getBoolean("submitted"),
                        rs.getTimestamp("submitted_at"), rs.getDouble("average_score")));
            }
        } catch (Exception e) {
            System.out.println("Loi getStudentFeedbackStatuses: " + e.getMessage());
        }
        return list;
    }

    public List<FeedbackDetailItem> getFeedbackDetailsForTeacher(int teacherId, int feedbackId) {
        List<FeedbackDetailItem> list = new ArrayList<>();
        String query = "SELECT c.title, fd.score, c.max_score "
                + "FROM feedback_details fd "
                + "JOIN criteria c ON fd.criterion_id = c.criterion_id "
                + "JOIN feedbacks f ON fd.feedback_id = f.feedback_id "
                + "JOIN class_sections cs ON f.class_section_id = cs.class_section_id "
                + "WHERE fd.feedback_id = ? AND cs.teacher_id = ? "
                + "ORDER BY c.criterion_id";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, feedbackId);
            ps.setInt(2, teacherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new FeedbackDetailItem(rs.getString("title"),
                        rs.getInt("score"), rs.getInt("max_score")));
            }
        } catch (Exception e) {
            System.out.println("Loi getFeedbackDetailsForTeacher: " + e.getMessage());
        }
        return list;
    }

    public boolean insertReply(int teacherId, int feedbackId, String content) {
        String normalizedContent = ValidationUtils.trim(content);
        if (ValidationUtils.isBlank(normalizedContent)
                || !ValidationUtils.isMaxLength(normalizedContent, 1000)
                || !feedbackBelongsToTeacher(teacherId, feedbackId)) {
            return false;
        }

        String query = "INSERT INTO feedback_replies (feedback_id, user_id, content) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, feedbackId);
            ps.setInt(2, teacherId);
            ps.setString(3, normalizedContent);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi insertReply: " + e.getMessage());
        }
        return false;
    }

    private boolean feedbackBelongsToTeacher(int teacherId, int feedbackId) {
        String query = "SELECT 1 "
                + "FROM feedbacks f "
                + "JOIN class_sections cs ON f.class_section_id = cs.class_section_id "
                + "WHERE f.feedback_id = ? AND cs.teacher_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, feedbackId);
            ps.setInt(2, teacherId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Loi feedbackBelongsToTeacher: " + e.getMessage());
        }
        return false;
    }

    private TeacherClassOverview mapClassOverview(ResultSet rs) throws Exception {
        return new TeacherClassOverview(rs.getInt("class_section_id"),
                rs.getString("class_code"), rs.getString("course_code"),
                rs.getString("course_name"), rs.getString("semester_name"),
                rs.getString("academic_year"), rs.getString("room"),
                rs.getInt("student_count"), rs.getInt("feedback_count"),
                rs.getDouble("average_score"));
    }
}
