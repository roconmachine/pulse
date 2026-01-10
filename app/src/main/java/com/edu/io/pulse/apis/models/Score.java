package com.edu.io.pulse.apis.models;

import com.google.gson.annotations.SerializedName;

public class Score {

    @SerializedName("id")
    private int id;

    @SerializedName("userid")
    private String userId;

    @SerializedName("setid")
    private Long setId;

    @SerializedName("questionId")
    private Long questionId;

    @SerializedName("givenAnswer")
    private String givenAnswer;

    @SerializedName("score")
    private double score;

    // Default Constructor
    public Score(String username, Long setid, Long questionid, String givenAnswer, double score) {
        this.userId = username;
        this.setId = setid;
        this.questionId = questionid;
        this.givenAnswer = givenAnswer;
        this.score = score;
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

    public Long getSetId() {
        return setId;
    }

    public void setSetId(Long setId) {
        this.setId = setId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
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