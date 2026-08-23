package model;

import java.sql.Timestamp;

public class SurveyStudentTarget {
    private int studentId;
    private String studentCode;
    private String studentName;
    private String classCode;
    private String courseCode;
    private String courseName;
    private String teacherName;
    private boolean submitted;
    private Timestamp submittedAt;

    public SurveyStudentTarget() {
    }

    public SurveyStudentTarget(int studentId, String studentCode, String studentName,
            String classCode, String courseCode, String courseName, String teacherName,
            boolean submitted, Timestamp submittedAt) {
        this.studentId = studentId;
        this.studentCode = studentCode;
        this.studentName = studentName;
        this.classCode = classCode;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.submitted = submitted;
        this.submittedAt = submittedAt;
    }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getClassCode() { return classCode; }
    public void setClassCode(String classCode) { this.classCode = classCode; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public boolean isSubmitted() { return submitted; }
    public void setSubmitted(boolean submitted) { this.submitted = submitted; }
    public Timestamp getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(Timestamp submittedAt) { this.submittedAt = submittedAt; }
}
