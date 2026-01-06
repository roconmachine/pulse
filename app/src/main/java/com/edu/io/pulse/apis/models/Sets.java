package com.edu.io.pulse.apis.models;

import com.google.gson.annotations.SerializedName;

public class Sets {

    @SerializedName("id")
    private Long id;

    @SerializedName("setName")
    private String setName;

    @SerializedName("status")
    private String status;

    @SerializedName("updatedBy")
    private String updatedBy;

    @SerializedName("exam")
    private int exam;

    @SerializedName("isFree")
    private boolean isFree;

    // Default Constructor
    public Sets() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getExam() {
        return exam;
    }

    public void setExam(int exam) {
        this.exam = exam;
    }

    public boolean isIsFree() {
        return isFree;
    }

    public void setIsFree(boolean isFree) {
        this.isFree = isFree;
    }
}
