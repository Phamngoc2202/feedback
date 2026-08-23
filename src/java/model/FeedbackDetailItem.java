package model;

public class FeedbackDetailItem {
    private String criterionTitle;
    private int score;
    private int maxScore;

    public FeedbackDetailItem() {
    }

    public FeedbackDetailItem(String criterionTitle, int score, int maxScore) {
        this.criterionTitle = criterionTitle;
        this.score = score;
        this.maxScore = maxScore;
    }

    public String getCriterionTitle() { return criterionTitle; }
    public void setCriterionTitle(String criterionTitle) { this.criterionTitle = criterionTitle; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public int getMaxScore() { return maxScore; }
    public void setMaxScore(int maxScore) { this.maxScore = maxScore; }
}
