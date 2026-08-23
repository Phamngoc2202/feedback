package model;

public class Criterion {
    private int criterionId;
    private int formId;
    private String title;
    private String description;
    private int maxScore;

    public Criterion() {
    }

    public Criterion(int criterionId, int formId, String title, String description, int maxScore) {
        this.criterionId = criterionId;
        this.formId = formId;
        this.title = title;
        this.description = description;
        this.maxScore = maxScore;
    }

    public int getCriterionId() { return criterionId; }
    public void setCriterionId(int criterionId) { this.criterionId = criterionId; }
    public int getFormId() { return formId; }
    public void setFormId(int formId) { this.formId = formId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getMaxScore() { return maxScore; }
    public void setMaxScore(int maxScore) { this.maxScore = maxScore; }
}
