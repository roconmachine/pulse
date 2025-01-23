package com.edu.io.pulse.ui.reviews;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.io.pulse.R;
import com.edu.io.pulse.ui.quiz.Answer;
import com.edu.io.pulse.ui.quiz.QuizQuestion;
import com.edu.io.pulse.ui.review.AnsweredQuestion;
import com.edu.io.pulse.ui.reviews.placeholder.PlaceholderContent;
import com.edu.io.pulse.utils.AppSharedPreference;
import com.edu.io.pulse.utils.Database;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A fragment representing a list of Items.
 */
public class ReviewFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private int set = 0;
    private final Gson gson = new Gson();
    FloatingActionButton floating;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReviewFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ReviewFragment newInstance(int columnCount) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String ARG_PARAM1 = "set_no";
            set = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new ReviewsAdapter(getData()));
        }

        floating = requireActivity().findViewById(R.id.floating_btn);
        floating.setVisibility(View.VISIBLE);
        floating.setOnClickListener(v -> {
            Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            floating.setVisibility(View.INVISIBLE);
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.action_quiz_review_to_nav_home);
        });
        return view;
    }

    private List<AnsweredQuestion> getData(){
        List<AnsweredQuestion> answeredQuestions =  new ArrayList<>(0);
        List<QuizQuestion> questions = Database.getQuestionBySet(this.set);
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

        return answeredQuestions;
    }



    @Override
    public void onDetach() {
        super.onDetach();
        floating.setVisibility(View.INVISIBLE);
    }
}