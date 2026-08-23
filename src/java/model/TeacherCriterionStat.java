package model;

public class TeacherCriterionStat {
    private String criterionTitle;
    private int maxScore;
    private int responseCount;
    private double averageScore;

    public TeacherCriterionStat() {
    }

    public TeacherCriterionStat(String criterionTitle, int maxScore, int responseCount, double averageScore) {
        this.criterionTitle = criterionTitle;
        this.maxScore = maxScore;
        this.responseCount = responseCount;
        this.averageScore = averageScore;
    }

    public String getCriterionTitle() { return criterionTitle; }
    public void setCriterionTitle(String criterionTitle) { this.criterionTitle = criterionTitle; }
    public int getMaxScore() { return maxScore; }
    public void setMaxScore(int maxScore) { this.maxScore = maxScore; }
    public int getResponseCount() { return responseCount; }
    public void setResponseCount(int responseCount) { this.responseCount = responseCount; }
    public double getAverageScore() { return averageScore; }
    public void setAverageScore(double averageScore) { this.averageScore = averageScore; }
}
