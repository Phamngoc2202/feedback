package model;

public class Enrollment {
    private int enrollmentId;
    private int classSectionId;
    private String classCode;
    private String courseCode;
    private String courseName;
    private int studentId;
    private String studentCode;
    private String studentName;
    private String teacherName;
    private String semesterName;
    private String academicYear;

    public Enrollment() {
    }

    public Enrollment(int enrollmentId, int classSectionId, String classCode,
            String courseCode, String courseName, int studentId, String studentCode,
            String studentName, String teacherName, String semesterName,
            String academicYear) {
        this.enrollmentId = enrollmentId;
        this.classSectionId = classSectionId;
        this.classCode = classCode;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.studentId = studentId;
        this.studentCode = studentCode;
        this.studentName = studentName;
        this.teacherName = teacherName;
        this.semesterName = semesterName;
        this.academicYear = academicYear;
    }

    public int getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }
    public int getClassSectionId() { return classSectionId; }
    public void setClassSectionId(int classSectionId) { this.classSectionId = classSectionId; }
    public String getClassCode() { return classCode; }
    public void setClassCode(String classCode) { this.classCode = classCode; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public String getSemesterName() { return semesterName; }
    public void setSemesterName(String semesterName) { this.semesterName = semesterName; }
    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
}
