package model;

import java.sql.Timestamp;

public class TeacherStudentFeedbackStatus {

    private int studentId;
    private String studentCode;
    private String studentName;
    private String email;
    private boolean submitted;
    private Timestamp submittedAt;
    private double averageScore;

    public TeacherStudentFeedbackStatus(int studentId, String studentCode,
            String studentName, String email, boolean submitted,
            Timestamp submittedAt, double averageScore) {
        this.studentId = studentId;
        this.studentCode = studentCode;
        this.studentName = studentName;
        this.email = email;
        this.submitted = submitted;
        this.submittedAt = submittedAt;
        this.averageScore = averageScore;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public Timestamp getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Timestamp submittedAt) {
        this.submittedAt = submittedAt;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }
}
