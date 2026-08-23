package model;

import java.sql.Timestamp;

public class TeacherFeedbackComment {
    private int feedbackId;
    private String studentCode;
    private String studentName;
    private String generalComment;
    private Timestamp createdAt;
    private double averageScore;
    private String replyContent;
    private Timestamp repliedAt;

    public TeacherFeedbackComment() {
    }

    public TeacherFeedbackComment(int feedbackId, String studentCode, String studentName,
            String generalComment, Timestamp createdAt, double averageScore,
            String replyContent, Timestamp repliedAt) {
        this.feedbackId = feedbackId;
        this.studentCode = studentCode;
        this.studentName = studentName;
        this.generalComment = generalComment;
        this.createdAt = createdAt;
        this.averageScore = averageScore;
        this.replyContent = replyContent;
        this.repliedAt = repliedAt;
    }

    public int getFeedbackId() { return feedbackId; }
    public void setFeedbackId(int feedbackId) { this.feedbackId = feedbackId; }
    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getGeneralComment() { return generalComment; }
    public void setGeneralComment(String generalComment) { this.generalComment = generalComment; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public double getAverageScore() { return averageScore; }
    public void setAverageScore(double averageScore) { this.averageScore = averageScore; }
    public String getReplyContent() { return replyContent; }
    public void setReplyContent(String replyContent) { this.replyContent = replyContent; }
    public Timestamp getRepliedAt() { return repliedAt; }
    public void setRepliedAt(Timestamp repliedAt) { this.repliedAt = repliedAt; }
}
