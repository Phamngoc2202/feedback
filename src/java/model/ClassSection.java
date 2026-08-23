package model;

public class ClassSection {
    private int classSectionId;
    private String classCode;
    private int courseId;
    private String courseCode;
    private String courseName;
    private int semesterId;
    private String semesterName;
    private String academicYear;
    private int teacherId;
    private String teacherCode;
    private String teacherName;
    private String room;
    private int enrollmentCount;

    public ClassSection() {
    }

    public ClassSection(int classSectionId, String classCode, int courseId,
            String courseCode, String courseName, int semesterId, String semesterName,
            String academicYear, int teacherId, String teacherCode,
            String teacherName, String room, int enrollmentCount) {
        this.classSectionId = classSectionId;
        this.classCode = classCode;
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.semesterId = semesterId;
        this.semesterName = semesterName;
        this.academicYear = academicYear;
        this.teacherId = teacherId;
        this.teacherCode = teacherCode;
        this.teacherName = teacherName;
        this.room = room;
        this.enrollmentCount = enrollmentCount;
    }

    public int getClassSectionId() { return classSectionId; }
    public void setClassSectionId(int classSectionId) { this.classSectionId = classSectionId; }
    public String getClassCode() { return classCode; }
    public void setClassCode(String classCode) { this.classCode = classCode; }
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public int getSemesterId() { return semesterId; }
    public void setSemesterId(int semesterId) { this.semesterId = semesterId; }
    public String getSemesterName() { return semesterName; }
    public void setSemesterName(String semesterName) { this.semesterName = semesterName; }
    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    public int getTeacherId() { return teacherId; }
    public void setTeacherId(int teacherId) { this.teacherId = teacherId; }
    public String getTeacherCode() { return teacherCode; }
    public void setTeacherCode(String teacherCode) { this.teacherCode = teacherCode; }
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }
    public int getEnrollmentCount() { return enrollmentCount; }
    public void setEnrollmentCount(int enrollmentCount) { this.enrollmentCount = enrollmentCount; }
}
