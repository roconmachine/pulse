package com.edu.io.pulse.utils;

import com.edu.io.pulse.apis.ApiClient;
import com.edu.io.pulse.apis.QuestionBankService;
import com.edu.io.pulse.apis.models.Sets;
import com.edu.io.pulse.ui.quiz.QuizQuestion;
import com.edu.io.pulse.ui.quiz_list.SetsDomain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Database {
    private static List<QuizQuestion> questions;
    private static final int QUESTION_PERSET = 2;
    private static final Long examid = 10005L;

    public interface SetsCallback {
        void onSetsReceived(List<SetsDomain> sets);
        void onError(Throwable t);
    }

    public static void setQuestions(List<QuizQuestion> _questions){
        Database.questions = _questions;
    }

    public static List<QuizQuestion> getQuestionBySet(int set){
        if(set > questions.size()/QUESTION_PERSET) return null;

        int indexStart = (set - 1) * QUESTION_PERSET;
        int indexEnd = indexStart + QUESTION_PERSET-1;

        List<QuizQuestion> quizQuestions = new ArrayList<>();
        for (;indexStart <= indexEnd; indexStart++)
        {
            quizQuestions.add(questions.get(indexStart));
        }

        return quizQuestions;
    }

    public static void getSets(SetsCallback callback) {
        QuestionBankService service = ApiClient.getClient().create(QuestionBankService.class);

        Call<List<Sets>> call = service.getSets(examid);
        call.enqueue(new Callback<List<Sets>>() {
            @Override
            public void onResponse(Call<List<Sets>> call, Response<List<Sets>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<SetsDomain> list = new ArrayList<>();
                    response.body().forEach(sets1 -> list.add(new SetsDomain(sets1.getId(), sets1.getSetName())));
                    callback.onSetsReceived(list);
                } else {
                    callback.onError(new Exception("Failed to fetch sets"));
                }
            }

            @Override
            public void onFailure(Call<List<Sets>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public static String getSetName(int set){
        return "Set " + set;
    }
}
