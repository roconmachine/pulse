package com.edu.io.pulse.apis.models;

import com.google.gson.annotations.SerializedName;

public class Score {

    @SerializedName("id")
    private int id;

    @SerializedName("userid")
    private String userId;

    @SerializedName("setid")
    private int setId;

    @SerializedName("questionId")
    private int questionId;

    @SerializedName("givenAnswer")
    private String givenAnswer;

    @SerializedName("score")
    private double score;

    // Default Constructor
    public Score() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getSetId() {
        return setId;
    }

    public void setSetId(int setId) {
        this.setId = setId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}