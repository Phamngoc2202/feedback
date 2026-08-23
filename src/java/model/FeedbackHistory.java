package model;

import java.sql.Timestamp;

public class FeedbackHistory {
    private int feedbackId;
    private String formTitle;
    private String classCode;
    private String courseCode;
    private String courseName;
    private String teacherName;
    private String generalComment;
    private Timestamp createdAt;
    private double averageScore;

    public FeedbackHistory() {
    }

    public FeedbackHistory(int feedbackId, String formTitle, String classCode,
            String courseCode, String courseName, String teacherName,
            String generalComment, Timestamp createdAt, double averageScore) {
        this.feedbackId = feedbackId;
        this.formTitle = formTitle;
        this.classCode = classCode;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.generalComment = generalComment;
        this.createdAt = createdAt;
        this.averageScore = averageScore;
    }

    public int getFeedbackId() { return feedbackId; }
    public void setFeedbackId(int feedbackId) { this.feedbackId = feedbackId; }
    public String getFormTitle() { return formTitle; }
    public void setFormTitle(String formTitle) { this.formTitle = formTitle; }
    public String getClassCode() { return classCode; }
    public void setClassCode(String classCode) { this.classCode = classCode; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public String getGeneralComment() { return generalComment; }
    public void setGeneralComment(String generalComment) { this.generalComment = generalComment; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public double getAverageScore() { return averageScore; }
    public void setAverageScore(double averageScore) { this.averageScore = averageScore; }
}
