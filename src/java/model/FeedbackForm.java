package model;

import java.sql.Date;

public class FeedbackForm {
    private int formId;
    private String title;
    private int semesterId;
    private String semesterName; // Thuộc tính bổ sung để hiển thị tên học kỳ ra bảng
    private Date startDate;
    private Date endDate;
    private boolean isActive;

    public FeedbackForm() {}

    public FeedbackForm(int formId, String title, int semesterId, String semesterName, Date startDate, Date endDate, boolean isActive) {
        this.formId = formId;
        this.title = title;
        this.semesterId = semesterId;
        this.semesterName = semesterName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
    }

    public int getFormId() { return formId; }
    public void setFormId(int formId) { this.formId = formId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getSemesterId() { return semesterId; }
    public void setSemesterId(int semesterId) { this.semesterId = semesterId; }
    public String getSemesterName() { return semesterName; }
    public void setSemesterName(String semesterName) { this.semesterName = semesterName; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public boolean isIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
}