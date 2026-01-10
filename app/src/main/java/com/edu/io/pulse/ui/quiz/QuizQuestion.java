package com.edu.io.pulse.ui.quiz;

import java.io.Serializable;

public class QuizQuestion implements Serializable {
    private Long id;
    private String question;
    private String[] options;
    private int answer;
    private int givenAnswer;

    public QuizQuestion(Long id, String question, String[] options, int answer) {
        this.id = id;
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    public QuizQuestion() {

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int youranswer) {
        this.answer = youranswer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public int getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(int givenAnswer) {
        this.givenAnswer = givenAnswer;
    }
    public boolean isCorrect() {
        return answer == givenAnswer;
    }
    public double getScore(){
        return isCorrect()?1.0 : -0.5;
    }


}
