package com.edu.io.pulse.ui.review;

import com.edu.io.pulse.ui.quiz.QuizQuestion;

public class AnsweredQuestion extends QuizQuestion {
    private int youranswer;
    public AnsweredQuestion(QuizQuestion question){
        super(question.getQuestion(), question.getOptions(), question.getAnswer());
    }

    public int getYouranswer() {
        return youranswer;
    }

    public void setYouranswer(int youranswer) {
        this.youranswer = youranswer;
    }
}
