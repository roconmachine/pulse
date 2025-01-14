package com.edu.io.pulse.ui.quiz;

import java.io.Serializable;

public class Answer implements Serializable {
    private int idQuestion;
    private int ans;
    private int currectAnswer;

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public int getAns() {
        return ans;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public int getCurrectAnswer() {
        return currectAnswer;
    }

    public void setCurrectAnswer(int currectAnswer) {
        this.currectAnswer = currectAnswer;
    }
}
