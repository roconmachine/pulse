package com.edu.io.pulse.ui.review;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.edu.io.pulse.R;
import com.edu.io.pulse.ui.quiz.Answer;
import com.edu.io.pulse.ui.quiz.QuizQuestion;
import com.edu.io.pulse.utils.AppSharedPreference;
import com.edu.io.pulse.utils.Database;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class QuizReviewFragment extends Fragment {

    private List<AnsweredQuestion> answeredQuestions;
    private int set = 0;
    private final String ARG_PARAM1 = "set_no";
    private final Gson gson = new Gson();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            set = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_review_list, container, false);

        List<QuizQuestion> questions = null;
        Database.getQuestionBySet(1100L, null);
        if (questions != null && !questions.isEmpty())
        {
            answeredQuestions = new ArrayList<>(0);

            AnsweredQuestion answeredQuestion;
            for (QuizQuestion q:questions) {
                answeredQuestion = new AnsweredQuestion(q);
                String jsonQuizQuestion = AppSharedPreference.getInstance(getContext()).getString(q.getId()+"", null);
                answeredQuestion.setYouranswer(this.gson.fromJson(jsonQuizQuestion, Answer.class).getAns());
                answeredQuestions.add(answeredQuestion);
            }
        }


        // Set the adapter
        if (view instanceof RecyclerView && !answeredQuestions.isEmpty()) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            recyclerView.setAdapter(new QuizReviewViewAdapter(this.answeredQuestions));
        }
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}