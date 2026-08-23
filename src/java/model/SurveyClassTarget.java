package model;

public class SurveyClassTarget {
    private int classSectionId;
    private String classCode;
    private String courseCode;
    private String courseName;
    private String teacherName;
    private String room;
    private int studentCount;
    private int submittedCount;

    public SurveyClassTarget() {
    }

    public SurveyClassTarget(int classSectionId, String classCode, String courseCode,
            String courseName, String teacherName, String room,
            int studentCount, int submittedCount) {
        this.classSectionId = classSectionId;
        this.classCode = classCode;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.room = room;
        this.studentCount = studentCount;
        this.submittedCount = submittedCount;
    }

    public int getClassSectionId() { return classSectionId; }
    public void setClassSectionId(int classSectionId) { this.classSectionId = classSectionId; }
    public String getClassCode() { return classCode; }
    public void setClassCode(String classCode) { this.classCode = classCode; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
    public int getStudentCount() { return studentCount; }
    public void setStudentCount(int studentCount) { this.studentCount = studentCount; }
    public int getSubmittedCount() { return submittedCount; }
    public void setSubmittedCount(int submittedCount) { this.submittedCount = submittedCount; }
    public int getPendingCount() { return studentCount - submittedCount; }
}
