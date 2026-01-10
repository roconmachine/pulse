package com.edu.io.pulse.utils;

import com.edu.io.pulse.apis.ApiClient;
import com.edu.io.pulse.apis.QuestionBankService;
import com.edu.io.pulse.apis.models.Question;
import com.edu.io.pulse.apis.models.Score;
import com.edu.io.pulse.apis.models.Sets;
import com.edu.io.pulse.ui.quiz.QuizQuestion;
import com.edu.io.pulse.ui.quiz_list.SetsDomain;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Database {
    private static List<QuizQuestion> questions;
    private static final Long examid = 10005L;
    public static void submitAnswers(String username, Long setid, List<QuizQuestion> quizQuestions, BackendCallback<Boolean> backendCallback) {
        QuestionBankService service = ApiClient.getClient().create(QuestionBankService.class);
        List<Score> scores = new ArrayList<>();
        quizQuestions.forEach(quizQuestion -> {
            scores.add(new Score(username, setid, (long) quizQuestion.getId(), String.valueOf(quizQuestion.getGivenAnswer()), quizQuestion.getScore()));
        });
        Call<Void> call = service.submitScore(scores);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response!=null && response.isSuccessful() && response.code() == 201)
                    backendCallback.onReceived(true);
                else backendCallback.onReceived(false);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                backendCallback.onError(t);
            }
        });
    }

    public interface SetsCallback {
        void onSetsReceived(List<SetsDomain> sets);
        void onError(Throwable t);
    }

    public interface BackendCallback<T> {
        void onReceived(T data);
        void onError(Throwable t);
    }

    public static void setQuestions(List<QuizQuestion> _questions){
        Database.questions = _questions;
    }

    public static void getQuestionBySet(Long setid, BackendCallback<List<QuizQuestion>> callback){
        List<QuizQuestion> quizQuestions = new ArrayList<>();
        QuestionBankService service = ApiClient.getClient().create(QuestionBankService.class);
        Call<List<Question>> call = service.getQuestionBySet(setid);
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    response.body().forEach(question -> {
                        quizQuestions.add(new QuizQuestion(question.getId() ,question.getQuestionText(),
                                List.of(question.getOptA(), question.getOptB(), question.getOptC(), question.getOptD()).toArray(new String[0]),
                                question.getAnswer().equalsIgnoreCase("A") ? 1 :
                                        question.getAnswer().equalsIgnoreCase("B") ? 2 :
                                                question.getAnswer().equalsIgnoreCase("C") ? 3 : 4
                        ));
                    });
                    callback.onReceived(quizQuestions);
                } else {
                    callback.onError(new Exception("Failed to fetch questions"));
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                callback.onError(t);
            }
        });
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
