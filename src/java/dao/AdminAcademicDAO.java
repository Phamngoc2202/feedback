package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.ClassSection;
import model.Course;
import model.Enrollment;
import model.Semester;
import model.User;

public class AdminAcademicDAO extends DBContext {

    public List<ClassSection> getAllClassSections() {
        List<ClassSection> list = new ArrayList<>();
        String query = "SELECT cs.class_section_id, cs.class_code, cs.course_id, c.course_code, c.course_name, "
                + "cs.semester_id, s.semester_name, s.academic_year, cs.teacher_id, "
                + "t.teacher_code, u.full_name AS teacher_name, cs.room, "
                + "COUNT(e.enrollment_id) AS enrollment_count "
                + "FROM class_sections cs "
                + "JOIN courses c ON cs.course_id = c.course_id "
                + "JOIN semesters s ON cs.semester_id = s.semester_id "
                + "JOIN teachers t ON cs.teacher_id = t.teacher_id "
                + "JOIN users u ON t.teacher_id = u.user_id "
                + "LEFT JOIN enrollments e ON cs.class_section_id = e.class_section_id "
                + "GROUP BY cs.class_section_id, cs.class_code, cs.course_id, c.course_code, c.course_name, "
                + "cs.semester_id, s.semester_name, s.academic_year, cs.teacher_id, "
                + "t.teacher_code, u.full_name, cs.room "
                + "ORDER BY s.academic_year DESC, s.semester_name DESC, cs.class_code";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ClassSection(rs.getInt("class_section_id"),
                        rs.getString("class_code"), rs.getInt("course_id"),
                        rs.getString("course_code"), rs.getString("course_name"),
                        rs.getInt("semester_id"), rs.getString("semester_name"),
                        rs.getString("academic_year"), rs.getInt("teacher_id"),
                        rs.getString("teacher_code"), rs.getString("teacher_name"),
                        rs.getString("room"), rs.getInt("enrollment_count")));
            }
        } catch (Exception e) {
            System.out.println("Loi getAllClassSections: " + e.getMessage());
        }
        return list;
    }

    public List<ClassSection> searchClassSections(String keyword) {
        List<ClassSection> list = new ArrayList<>();
        String query = "SELECT cs.class_section_id, cs.class_code, cs.course_id, c.course_code, c.course_name, "
                + "cs.semester_id, s.semester_name, s.academic_year, cs.teacher_id, "
                + "t.teacher_code, u.full_name AS teacher_name, cs.room, "
                + "COUNT(e.enrollment_id) AS enrollment_count "
                + "FROM class_sections cs "
                + "JOIN courses c ON cs.course_id = c.course_id "
                + "JOIN semesters s ON cs.semester_id = s.semester_id "
                + "JOIN teachers t ON cs.teacher_id = t.teacher_id "
                + "JOIN users u ON t.teacher_id = u.user_id "
                + "LEFT JOIN enrollments e ON cs.class_section_id = e.class_section_id "
                + "WHERE cs.class_code LIKE ? OR c.course_code LIKE ? OR c.course_name LIKE ? "
                + "OR u.full_name LIKE ? OR t.teacher_code LIKE ? OR s.semester_name LIKE ? OR s.academic_year LIKE ? "
                + "GROUP BY cs.class_section_id, cs.class_code, cs.course_id, c.course_code, c.course_name, "
                + "cs.semester_id, s.semester_name, s.academic_year, cs.teacher_id, "
                + "t.teacher_code, u.full_name, cs.room "
                + "ORDER BY s.academic_year DESC, s.semester_name DESC, cs.class_code";
        String pattern = "%" + keyword + "%";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            for (int i = 1; i <= 7; i++) {
                ps.setString(i, pattern);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ClassSection(rs.getInt("class_section_id"),
                        rs.getString("class_code"), rs.getInt("course_id"),
                        rs.getString("course_code"), rs.getString("course_name"),
                        rs.getInt("semester_id"), rs.getString("semester_name"),
                        rs.getString("academic_year"), rs.getInt("teacher_id"),
                        rs.getString("teacher_code"), rs.getString("teacher_name"),
                        rs.getString("room"), rs.getInt("enrollment_count")));
            }
        } catch (Exception e) {
            System.out.println("Loi searchClassSections: " + e.getMessage());
        }
        return list;
    }

    public boolean insertClassSection(ClassSection classSection) {
        String query = "INSERT INTO class_sections (class_code, course_id, semester_id, teacher_id, room) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, classSection.getClassCode());
            ps.setInt(2, classSection.getCourseId());
            ps.setInt(3, classSection.getSemesterId());
            ps.setInt(4, classSection.getTeacherId());
            ps.setString(5, classSection.getRoom());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi insertClassSection: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteClassSection(int classSectionId) {
        String query = "DELETE FROM class_sections WHERE class_section_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, classSectionId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi deleteClassSection: " + e.getMessage());
        }
        return false;
    }

    public ClassSection getClassSectionById(int classSectionId) {
        String query = "SELECT cs.class_section_id, cs.class_code, cs.course_id, c.course_code, c.course_name, "
                + "cs.semester_id, s.semester_name, s.academic_year, cs.teacher_id, "
                + "t.teacher_code, u.full_name AS teacher_name, cs.room, "
                + "COUNT(e.enrollment_id) AS enrollment_count "
                + "FROM class_sections cs "
                + "JOIN courses c ON cs.course_id = c.course_id "
                + "JOIN semesters s ON cs.semester_id = s.semester_id "
                + "JOIN teachers t ON cs.teacher_id = t.teacher_id "
                + "JOIN users u ON t.teacher_id = u.user_id "
                + "LEFT JOIN enrollments e ON cs.class_section_id = e.class_section_id "
                + "WHERE cs.class_section_id = ? "
                + "GROUP BY cs.class_section_id, cs.class_code, cs.course_id, c.course_code, c.course_name, "
                + "cs.semester_id, s.semester_name, s.academic_year, cs.teacher_id, "
                + "t.teacher_code, u.full_name, cs.room";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, classSectionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ClassSection(rs.getInt("class_section_id"),
                        rs.getString("class_code"), rs.getInt("course_id"),
                        rs.getString("course_code"), rs.getString("course_name"),
                        rs.getInt("semester_id"), rs.getString("semester_name"),
                        rs.getString("academic_year"), rs.getInt("teacher_id"),
                        rs.getString("teacher_code"), rs.getString("teacher_name"),
                        rs.getString("room"), rs.getInt("enrollment_count"));
            }
        } catch (Exception e) {
            System.out.println("Loi getClassSectionById: " + e.getMessage());
        }
        return null;
    }

    public List<Enrollment> getAllEnrollments() {
        List<Enrollment> list = new ArrayList<>();
        String query = "SELECT e.enrollment_id, e.class_section_id, cs.class_code, "
                + "c.course_code, c.course_name, e.student_id, st.student_code, "
                + "su.full_name AS student_name, tu.full_name AS teacher_name, "
                + "sem.semester_name, sem.academic_year "
                + "FROM enrollments e "
                + "JOIN students st ON e.student_id = st.student_id "
                + "JOIN users su ON st.student_id = su.user_id "
                + "JOIN class_sections cs ON e.class_section_id = cs.class_section_id "
                + "JOIN courses c ON cs.course_id = c.course_id "
                + "JOIN semesters sem ON cs.semester_id = sem.semester_id "
                + "JOIN teachers t ON cs.teacher_id = t.teacher_id "
                + "JOIN users tu ON t.teacher_id = tu.user_id "
                + "ORDER BY su.full_name, sem.academic_year DESC, cs.class_code";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Enrollment(rs.getInt("enrollment_id"),
                        rs.getInt("class_section_id"), rs.getString("class_code"),
                        rs.getString("course_code"), rs.getString("course_name"),
                        rs.getInt("student_id"), rs.getString("student_code"),
                        rs.getString("student_name"), rs.getString("teacher_name"),
                        rs.getString("semester_name"), rs.getString("academic_year")));
            }
        } catch (Exception e) {
            System.out.println("Loi getAllEnrollments: " + e.getMessage());
        }
        return list;
    }

    public List<Enrollment> getEnrollmentsByClassSectionId(int classSectionId) {
        List<Enrollment> list = new ArrayList<>();
        String query = "SELECT e.enrollment_id, e.class_section_id, cs.class_code, "
                + "c.course_code, c.course_name, e.student_id, st.student_code, "
                + "su.full_name AS student_name, tu.full_name AS teacher_name, "
                + "sem.semester_name, sem.academic_year "
                + "FROM enrollments e "
                + "JOIN students st ON e.student_id = st.student_id "
                + "JOIN users su ON st.student_id = su.user_id "
                + "JOIN class_sections cs ON e.class_section_id = cs.class_section_id "
                + "JOIN courses c ON cs.course_id = c.course_id "
                + "JOIN semesters sem ON cs.semester_id = sem.semester_id "
                + "JOIN teachers t ON cs.teacher_id = t.teacher_id "
                + "JOIN users tu ON t.teacher_id = tu.user_id "
                + "WHERE e.class_section_id = ? "
                + "ORDER BY su.full_name";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, classSectionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Enrollment(rs.getInt("enrollment_id"),
                        rs.getInt("class_section_id"), rs.getString("class_code"),
                        rs.getString("course_code"), rs.getString("course_name"),
                        rs.getInt("student_id"), rs.getString("student_code"),
                        rs.getString("student_name"), rs.getString("teacher_name"),
                        rs.getString("semester_name"), rs.getString("academic_year")));
            }
        } catch (Exception e) {
            System.out.println("Loi getEnrollmentsByClassSectionId: " + e.getMessage());
        }
        return list;
    }

    public List<Enrollment> searchEnrollmentsByClassSectionId(int classSectionId, String keyword) {
        List<Enrollment> list = new ArrayList<>();
        String query = "SELECT e.enrollment_id, e.class_section_id, cs.class_code, "
                + "c.course_code, c.course_name, e.student_id, st.student_code, "
                + "su.full_name AS student_name, tu.full_name AS teacher_name, "
                + "sem.semester_name, sem.academic_year "
                + "FROM enrollments e "
                + "JOIN students st ON e.student_id = st.student_id "
                + "JOIN users su ON st.student_id = su.user_id "
                + "JOIN class_sections cs ON e.class_section_id = cs.class_section_id "
                + "JOIN courses c ON cs.course_id = c.course_id "
                + "JOIN semesters sem ON cs.semester_id = sem.semester_id "
                + "JOIN teachers t ON cs.teacher_id = t.teacher_id "
                + "JOIN users tu ON t.teacher_id = tu.user_id "
                + "WHERE e.class_section_id = ? "
                + "AND (st.student_code LIKE ? OR su.full_name LIKE ? OR su.email LIKE ?) "
                + "ORDER BY su.full_name";
        String pattern = "%" + keyword + "%";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, classSectionId);
            ps.setString(2, pattern);
            ps.setString(3, pattern);
            ps.setString(4, pattern);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Enrollment(rs.getInt("enrollment_id"),
                        rs.getInt("class_section_id"), rs.getString("class_code"),
                        rs.getString("course_code"), rs.getString("course_name"),
                        rs.getInt("student_id"), rs.getString("student_code"),
                        rs.getString("student_name"), rs.getString("teacher_name"),
                        rs.getString("semester_name"), rs.getString("academic_year")));
            }
        } catch (Exception e) {
            System.out.println("Loi searchEnrollmentsByClassSectionId: " + e.getMessage());
        }
        return list;
    }

    public boolean insertEnrollment(int classSectionId, int studentId) {
        if (isEnrolled(classSectionId, studentId)) {
            return false;
        }

        String query = "INSERT INTO enrollments (class_section_id, student_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, classSectionId);
            ps.setInt(2, studentId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi insertEnrollment: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteEnrollment(int enrollmentId) {
        String query = "DELETE FROM enrollments WHERE enrollment_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, enrollmentId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Loi deleteEnrollment: " + e.getMessage());
        }
        return false;
    }

    public boolean classCodeExists(String classCode, int semesterId) {
        String query = "SELECT 1 FROM class_sections WHERE class_code = ? AND semester_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, classCode);
            ps.setInt(2, semesterId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Loi classCodeExists: " + e.getMessage());
        }
        return true;
    }

    public boolean courseExists(int courseId) {
        return existsById("courses", "course_id", courseId);
    }

    public boolean semesterExists(int semesterId) {
        return existsById("semesters", "semester_id", semesterId);
    }

    public boolean teacherExists(int teacherId) {
        return existsById("teachers", "teacher_id", teacherId);
    }

    public boolean studentExists(int studentId) {
        return existsById("students", "student_id", studentId);
    }

    public boolean classSectionExists(int classSectionId) {
        return existsById("class_sections", "class_section_id", classSectionId);
    }

    public boolean enrollmentHasFeedback(int enrollmentId) {
        String query = "SELECT 1 "
                + "FROM enrollments e "
                + "JOIN feedbacks f ON f.class_section_id = e.class_section_id "
                + "    AND f.student_id = e.student_id "
                + "WHERE e.enrollment_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, enrollmentId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Loi enrollmentHasFeedback: " + e.getMessage());
        }
        return true;
    }

    public List<Course> getCourseOptions() {
        List<Course> list = new ArrayList<>();
        String query = "SELECT c.*, d.department_name "
                + "FROM courses c "
                + "JOIN departments d ON c.department_id = d.department_id "
                + "ORDER BY c.course_code";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Course(rs.getInt("course_id"), rs.getString("course_code"),
                        rs.getString("course_name"), rs.getInt("credits"),
                        rs.getInt("department_id"), rs.getString("department_name")));
            }
        } catch (Exception e) {
            System.out.println("Loi getCourseOptions: " + e.getMessage());
        }
        return list;
    }

    public List<Semester> getSemesterOptions() {
        List<Semester> list = new ArrayList<>();
        String query = "SELECT * FROM semesters ORDER BY start_date DESC";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Semester(rs.getInt("semester_id"),
                        rs.getString("semester_name"), rs.getString("academic_year"),
                        rs.getDate("start_date"), rs.getDate("end_date")));
            }
        } catch (Exception e) {
            System.out.println("Loi getSemesterOptions: " + e.getMessage());
        }
        return list;
    }

    public List<User> getTeacherOptions() {
        List<User> list = new ArrayList<>();
        String query = "SELECT u.user_id, u.username, u.password_hash, u.full_name, u.email, u.role_id "
                + "FROM teachers t "
                + "JOIN users u ON t.teacher_id = u.user_id "
                + "ORDER BY u.full_name";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapUser(rs));
            }
        } catch (Exception e) {
            System.out.println("Loi getTeacherOptions: " + e.getMessage());
        }
        return list;
    }

    public List<User> getStudentOptions() {
        List<User> list = new ArrayList<>();
        String query = "SELECT u.user_id, u.username, u.password_hash, u.full_name, u.email, u.role_id "
                + "FROM students s "
                + "JOIN users u ON s.student_id = u.user_id "
                + "ORDER BY u.full_name";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapUser(rs));
            }
        } catch (Exception e) {
            System.out.println("Loi getStudentOptions: " + e.getMessage());
        }
        return list;
    }

    private boolean isEnrolled(int classSectionId, int studentId) {
        String query = "SELECT 1 FROM enrollments WHERE class_section_id = ? AND student_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, classSectionId);
            ps.setInt(2, studentId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Loi isEnrolled: " + e.getMessage());
        }
        return true;
    }

    private boolean existsById(String table, String idColumn, int id) {
        String query = "SELECT 1 FROM " + table + " WHERE " + idColumn + " = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("Loi existsById: " + e.getMessage());
        }
        return false;
    }

    private User mapUser(ResultSet rs) throws Exception {
        return new User(rs.getInt("user_id"), rs.getString("username"),
                rs.getString("password_hash"), rs.getString("full_name"),
                rs.getString("email"), rs.getInt("role_id"));
    }
}
