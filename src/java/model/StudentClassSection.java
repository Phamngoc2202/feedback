package model;

public class StudentClassSection {
    private int classSectionId;
    private String classCode;
    private String courseCode;
    private String courseName;
    private int credits;
    private String semesterName;
    private String academicYear;
    private String teacherName;
    private String room;
    private int activeFormId;
    private String activeFormTitle;
    private boolean feedbackSubmitted;

    public StudentClassSection() {
    }

    public StudentClassSection(int classSectionId, String classCode, String courseCode,
            String courseName, int credits, String semesterName, String academicYear,
            String teacherName, String room, int activeFormId, String activeFormTitle,
            boolean feedbackSubmitted) {
        this.classSectionId = classSectionId;
        this.classCode = classCode;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.semesterName = semesterName;
        this.academicYear = academicYear;
        this.teacherName = teacherName;
        this.room = room;
        this.activeFormId = activeFormId;
        this.activeFormTitle = activeFormTitle;
        this.feedbackSubmitted = feedbackSubmitted;
    }

    public int getClassSectionId() { return classSectionId; }
    public void setClassSectionId(int classSectionId) { this.classSectionId = classSectionId; }
    public String getClassCode() { return classCode; }
    public void setClassCode(String classCode) { this.classCode = classCode; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    public String getSemesterName() { return semesterName; }
    public void setSemesterName(String semesterName) { this.semesterName = semesterName; }
    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
    public int getActiveFormId() { return activeFormId; }
    public void setActiveFormId(int activeFormId) { this.activeFormId = activeFormId; }
    public String getActiveFormTitle() { return activeFormTitle; }
    public void setActiveFormTitle(String activeFormTitle) { this.activeFormTitle = activeFormTitle; }
    public boolean isFeedbackSubmitted() { return feedbackSubmitted; }
    public void setFeedbackSubmitted(boolean feedbackSubmitted) { this.feedbackSubmitted = feedbackSubmitted; }
    public boolean isCanFeedback() { return activeFormId > 0 && !feedbackSubmitted; }
}
