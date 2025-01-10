package com.edu.io.pulse.utils;

import com.edu.io.pulse.ui.quiz.QuizQuestion;
import com.edu.io.pulse.ui.quiz_list.SetsDomain;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static List<QuizQuestion> questions;
    private static final int QUESTION_PERSET = 2;

    public static void setQuestions(List<QuizQuestion> _questions){
        Database.questions = _questions;
    }

    public static List<QuizQuestion> getQuestionBySet(int set){
        if(set > questions.size()/QUESTION_PERSET) return null;

        int indexStart = (set - 1) * QUESTION_PERSET;
        int indexEnd = indexStart + QUESTION_PERSET;

        List<QuizQuestion> quizQuestions = new ArrayList<>();
        for (;indexStart <= indexEnd; indexStart++)
        {
            quizQuestions.add(questions.get(indexStart));
        }

        return quizQuestions;
    }

    public static List<SetsDomain> getSets(){
        List<SetsDomain> list = new ArrayList<>(0);

        for (int i = 1; i <= questions.size()/QUESTION_PERSET;i++)
            list.add(new SetsDomain("Set " + i));

        return list;
    }

}
