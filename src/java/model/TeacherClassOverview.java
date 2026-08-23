package model;

public class TeacherClassOverview {
    private int classSectionId;
    private String classCode;
    private String courseCode;
    private String courseName;
    private String semesterName;
    private String academicYear;
    private String room;
    private int studentCount;
    private int feedbackCount;
    private double averageScore;

    public TeacherClassOverview() {
    }

    public TeacherClassOverview(int classSectionId, String classCode, String courseCode,
            String courseName, String semesterName, String academicYear, String room,
            int studentCount, int feedbackCount, double averageScore) {
        this.classSectionId = classSectionId;
        this.classCode = classCode;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.semesterName = semesterName;
        this.academicYear = academicYear;
        this.room = room;
        this.studentCount = studentCount;
        this.feedbackCount = feedbackCount;
        this.averageScore = averageScore;
    }

    public int getClassSectionId() { return classSectionId; }
    public void setClassSectionId(int classSectionId) { this.classSectionId = classSectionId; }
    public String getClassCode() { return classCode; }
    public void setClassCode(String classCode) { this.classCode = classCode; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getSemesterName() { return semesterName; }
    public void setSemesterName(String semesterName) { this.semesterName = semesterName; }
    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
    public int getStudentCount() { return studentCount; }
    public void setStudentCount(int studentCount) { this.studentCount = studentCount; }
    public int getFeedbackCount() { return feedbackCount; }
    public void setFeedbackCount(int feedbackCount) { this.feedbackCount = feedbackCount; }
    public double getAverageScore() { return averageScore; }
    public void setAverageScore(double averageScore) { this.averageScore = averageScore; }
}
