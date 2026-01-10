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
import com.edu.io.pulse.ui.quiz.QuizQuestion;

import java.util.ArrayList;
import java.util.List;

public class QuizReviewFragment extends Fragment {
    List<QuizQuestion> questions = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questions = (List<QuizQuestion>) getArguments().getSerializable("quiz_questions");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_review_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new QuizReviewViewAdapter(this.questions));
        }
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
