package model;

import java.sql.Date;

public class Semester {
    private int semesterId;
    private String semesterName;
    private String academicYear;
    private Date startDate;
    private Date endDate;

    public Semester() {}

    public Semester(int semesterId, String semesterName, String academicYear, Date startDate, Date endDate) {
        this.semesterId = semesterId;
        this.semesterName = semesterName;
        this.academicYear = academicYear;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getSemesterId() { return semesterId; }
    public void setSemesterId(int semesterId) { this.semesterId = semesterId; }
    public String getSemesterName() { return semesterName; }
    public void setSemesterName(String semesterName) { this.semesterName = semesterName; }
    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
}