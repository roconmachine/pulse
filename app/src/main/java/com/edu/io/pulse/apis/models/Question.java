package com.edu.io.pulse.apis.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Question {

    @SerializedName("id")
    private int id;

    @SerializedName("questionText")
    private String questionText;

    @SerializedName("correctScore")
    private String correctScore; // Kept as String per JSON, can be double if preferred

    @SerializedName("incorrectScore")
    private String incorrectScore;

    @SerializedName("lang")
    private String lang;

    @SerializedName("status")
    private String status;

    @SerializedName("shapeType")
    private String shapeType;

    @SerializedName("shapeData")
    private String shapeData;

    @SerializedName("updatedBy")
    private String updatedBy;

    @SerializedName("optA")
    private String optA;

    @SerializedName("optB")
    private String optB;

    @SerializedName("optC")
    private String optC;

    @SerializedName("optD")
    private String optD;

    @SerializedName("answer")
    private String answer;

    @SerializedName("set")
    private int set;

    @SerializedName("category")
    private int category;

    @SerializedName("questionTagTags")
    private List<Integer> questionTagTags;

    // Default Constructor
    public Question() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public String getCorrectScore() { return correctScore; }
    public void setCorrectScore(String correctScore) { this.correctScore = correctScore; }

    public String getIncorrectScore() { return incorrectScore; }
    public void setIncorrectScore(String incorrectScore) { this.incorrectScore = incorrectScore; }

    public String getLang() { return lang; }
    public void setLang(String lang) { this.lang = lang; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getShapeType() { return shapeType; }
    public void setShapeType(String shapeType) { this.shapeType = shapeType; }

    public String getShapeData() { return shapeData; }
    public void setShapeData(String shapeData) { this.shapeData = shapeData; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public String getOptA() { return optA; }
    public void setOptA(String optA) { this.optA = optA; }

    public String getOptB() { return optB; }
    public void setOptB(String optB) { this.optB = optB; }

    public String getOptC() { return optC; }
    public void setOptC(String optC) { this.optC = optC; }

    public String getOptD() { return optD; }
    public void setOptD(String optD) { this.optD = optD; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public int getSet() { return set; }
    public void setSet(int set) { this.set = set; }

    public int getCategory() { return category; }
    public void setCategory(int category) { this.category = category; }

    public List<Integer> getQuestionTagTags() { return questionTagTags; }
    public void setQuestionTagTags(List<Integer> questionTagTags) { this.questionTagTags = questionTagTags; }
}
