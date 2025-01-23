package com.edu.io.pulse.ui.quiz;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edu.io.pulse.R;
import com.edu.io.pulse.databinding.FragmentQuizBinding;
import com.edu.io.pulse.utils.AppSharedPreference;
import com.edu.io.pulse.utils.Database;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quiz extends Fragment {

    private FragmentQuizBinding binding;
    private static final String ARG_PARAM1 = "set_no";
    private Typeface fontBangla;
    private int set = 0;
    CountDownTimer timer;
    int numberOfQuestion;
    List<QuizQuestion> quizQuestions;
    HashMap<Integer, Answer> answers = new HashMap<Integer, Answer>(0);
    Answer currentAnswer = new Answer();
    int currentQuestionIndex = 0;


    public static Quiz newInstance() {
        return new Quiz();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            set = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);

        binding = FragmentQuizBinding.bind(rootView);
        initView();
        return rootView;
    }

    private void initView(){
        this.quizQuestions = new ArrayList<>(0);
        this.quizQuestions = Database.getQuestionBySet(this.set);
        currentQuestionIndex = -1;
        setNextQuestion();
        startTimer(this.quizQuestions.size() * 60);

        binding.nextBtn.setOnClickListener(v -> {
            this.setNextQuestion();
        });
        binding.preBtn.setOnClickListener(v -> {
            this.setPrevQuestion();
        });

        binding.options.setOnCheckedChangeListener((group, checkedId) -> {

            int index = group.indexOfChild(group.findViewById(checkedId));
            currentAnswer.setAns(index+1);
        });
        answers = new HashMap<Integer, Answer>(0);
    }

    void startTimer(int secs){
        timer = new CountDownTimer(1000L * secs,1000) {
            @Override
            public void onTick(long l) {
                long hours = (l / (1000 * 60 * 60)) % 24;  // Hours
                long minutes = (l / (1000 * 60)) % 60;    // Minutes
                long seconds = (l / 1000) % 60;          // Seconds

                // Format the time as HH:mm:ss
                String timeLeft = String.format("%02d:%02d:%02d", hours, minutes, seconds);

                binding.timer.setText(timeLeft);
            }

            @Override
            public void onFinish() {
                finishQuiz();
            }
        };
        timer.start();
    }

    private void setNextQuestion() {
        this.answers.put((Integer) this.currentAnswer.getIdQuestion(), this.currentAnswer);
        currentQuestionIndex++;
        if(currentQuestionIndex >= this.quizQuestions.size()){
            finishQuiz();
            return;
        }
        QuizQuestion question = this.quizQuestions.get(currentQuestionIndex);
        this.setQuestion(question);
        //this.setAnswer();
    }

    private void setAnswer() {
        this.currentAnswer = new Answer();
        this.currentAnswer.setIdQuestion(this.quizQuestions.get(currentQuestionIndex).getId());
        this.currentAnswer.setCurrectAnswer(this.quizQuestions.get(currentQuestionIndex).getAnswer());
    }

    private void setPrevQuestion() {
        this.answers.put((Integer) this.currentAnswer.getIdQuestion(), this.currentAnswer);
        if (this.currentQuestionIndex <=0)
        {
            return;
        }
        currentQuestionIndex--;
        binding.nextBtn.setText(R.string.btnNextText);
        QuizQuestion question = this.quizQuestions.get(currentQuestionIndex);
        this.setQuestion(question);
    }


    private void setQuestion(QuizQuestion question){


        binding.question.setText(question.getQuestion());
        binding.options.clearCheck();
        binding.optionA.setText(getString(R.string.option_label_format,"1", question.getOptions()[0]));
        binding.optionB.setText(getString(R.string.option_label_format,"2", question.getOptions()[1]));
        binding.optionC.setText(getString(R.string.option_label_format,"3", question.getOptions()[2]));
        binding.optionD.setText(getString(R.string.option_label_format,"4", question.getOptions()[3]));

        binding.questionCounter.setText(getString(
                        R.string.question_counter_format,
                        currentQuestionIndex+1,
                        this.quizQuestions.size()
                )
        );

        if (currentQuestionIndex==this.quizQuestions.size()-1){
            binding.nextBtn.setText(R.string.btnFinishText);
        }

        setAnswer();
    }




    private void finishQuiz()
    {
        for (Map.Entry<Integer, Answer> entry : this.answers.entrySet()) {
            AppSharedPreference.getInstance(getContext()).saveString(entry.getKey() + "", new Gson().toJson(entry.getValue()));
        }

        Bundle bundle = new Bundle();
        bundle.putInt("set_no", this.set);
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_quiz_main_to_quiz_review, bundle);
    }



    @Override
    public void onDetach() {
        super.onDetach();
        timer.cancel();
    }

}