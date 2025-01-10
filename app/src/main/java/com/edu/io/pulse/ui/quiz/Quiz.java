package com.edu.io.pulse.ui.quiz;

import androidx.lifecycle.ViewModelProvider;

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
import android.widget.TextView;
import android.widget.Toast;

import com.edu.io.pulse.R;
import com.edu.io.pulse.databinding.FragmentHomeBinding;
import com.edu.io.pulse.databinding.FragmentQuizBinding;

import java.util.ArrayList;
import java.util.List;

public class Quiz extends Fragment {

    private FragmentQuizBinding binding;
    CountDownTimer timer;
    int numberOfQuestion;
    List<QuizQuestion> quizQuestions;
    int currentQuestionIndex = 0;


    public static Quiz newInstance() {
        return new Quiz();
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
        this.quizQuestions.add(new QuizQuestion(
                "sss",
                new String[]{"sss", "eeee", " SFE", "efw a"}
                ,2
        ));
        this.quizQuestions.add(new QuizQuestion(
                "তুমি কি কিছু জান ?",
                new String[]{"তুমি ", "তুমি ", "তুমি ", "তুমি "},
                1
        ));
        currentQuestionIndex = -1;
        setNextQuestion();
        startTimer(this.quizQuestions.size() * 60);

        binding.nextBtn.setOnClickListener(v -> {
            this.setNextQuestion();
        });
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
        currentQuestionIndex++;
        if(currentQuestionIndex >= this.quizQuestions.size()){
            finishQuiz();
            return;
        }
        QuizQuestion question = this.quizQuestions.get(currentQuestionIndex);
        binding.question.setText(question.getQuestion());

        binding.optionA.setText(getString(R.string.option_label_format,"A. ", question.getOptions()[0]));
        binding.optionB.setText(getString(R.string.option_label_format,"B. ", question.getOptions()[1]));
        binding.optionC.setText(getString(R.string.option_label_format,"C. ", question.getOptions()[2]));
        binding.optionD.setText(getString(R.string.option_label_format,"D. ", question.getOptions()[3]));

        binding.questionCounter.setText(getString(
                R.string.question_counter_format,
                currentQuestionIndex+1,
                this.quizQuestions.size()
                )
        );

        if (currentQuestionIndex==this.quizQuestions.size()-1){
            binding.nextBtn.setText(R.string.btnFinishText);
        }

    }

    private void finishQuiz()
    {
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_quiz_main_to_nav_home);
    }



    @Override
    public void onDetach() {
        super.onDetach();
        timer.cancel();
    }

}