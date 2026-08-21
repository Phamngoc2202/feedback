package model;

public class Course {
    private int courseId;
    private String courseCode;
    private String courseName;
    private int credits;
    private int departmentId;
    private String departmentName; // Phục vụ hiển thị

    public Course() {}

    public Course(int courseId, String courseCode, String courseName, int credits, int departmentId, String departmentName) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    // Getters and Setters
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
}