package com.edu.io.pulse.ui.quiz;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.io.pulse.R;
import com.edu.io.pulse.databinding.FragmentQuizBinding;
import com.edu.io.pulse.utils.AppSharedPreference;
import com.edu.io.pulse.utils.Database;

import java.util.ArrayList;
import java.util.List;

public class Quiz extends Fragment {

    private FragmentQuizBinding binding;
    private Long setid = 0L;
    CountDownTimer timer;
    List<QuizQuestion> quizQuestions = new ArrayList<>();
    int currentQuestionIndex = 0;
    private QuizViewModel quizViewModel;
    private String username = "roconmachine@gmail.com";

    public static Quiz newInstance() {
        return new Quiz();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            setid = getArguments().getLong("set_id");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);

        binding = FragmentQuizBinding.bind(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        quizViewModel.getQuestions().observe(getViewLifecycleOwner(), questions -> {
            if (questions != null && !questions.isEmpty()) {
                quizQuestions.clear();
                quizQuestions.addAll(questions);
                initView();
            } else {
                Toast.makeText(getContext(), "No questions available", Toast.LENGTH_SHORT).show();
            }
        });

        quizViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        });

        quizViewModel.fetchQuestionsBySet(setid);

    }

    private void initView() {
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
            if (currentQuestionIndex >= 0 && currentQuestionIndex < quizQuestions.size()) {
                quizQuestions.get(currentQuestionIndex).setGivenAnswer(index + 1);
            }
        });
    }

    void startTimer(int secs) {
        timer = new CountDownTimer(1000L * secs, 1000) {
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
        if (currentQuestionIndex >= this.quizQuestions.size()) {
            finishQuiz();
            return;
        }
        QuizQuestion question = this.quizQuestions.get(currentQuestionIndex);
        this.setQuestion(question);
    }

    private void setPrevQuestion() {
        if (this.currentQuestionIndex <= 0) {
            return;
        }
        currentQuestionIndex--;
        binding.nextBtn.setText(R.string.btnNextText);
        QuizQuestion question = this.quizQuestions.get(currentQuestionIndex);
        this.setQuestion(question);
    }

    private void setQuestion(QuizQuestion question) {
        binding.question.setText(question.getQuestion());
        binding.options.clearCheck();
        binding.optionA.setText(getString(R.string.option_label_format, "1", question.getOptions()[0]));
        binding.optionB.setText(getString(R.string.option_label_format, "2", question.getOptions()[1]));
        binding.optionC.setText(getString(R.string.option_label_format, "3", question.getOptions()[2]));
        binding.optionD.setText(getString(R.string.option_label_format, "4", question.getOptions()[3]));

        binding.questionCounter.setText(getString(
                R.string.question_counter_format,
                currentQuestionIndex + 1,
                this.quizQuestions.size()
        ));

        if (currentQuestionIndex == this.quizQuestions.size() - 1) {
            binding.nextBtn.setText(R.string.btnFinishText);
        }

        // Pre-select the option if already answered
        if (question.getGivenAnswer() > 0) {
            ((android.widget.RadioButton) binding.options.getChildAt(question.getGivenAnswer() - 1)).setChecked(true);
        }
    }

    private void submitAnswers() {
        Database.submitAnswers(username, setid, quizQuestions, new Database.BackendCallback<Boolean>() {
            @Override
            public void onReceived(Boolean success) {
                if (success) {
                    showResultDialog();
                } else {
                    Toast.makeText(getContext(), "Failed to submit answers", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showResultDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_quiz_result, null);
        builder.setView(dialogView);

        TextView tvTotalScore = dialogView.findViewById(R.id.tv_total_score);
        RecyclerView rvResults = dialogView.findViewById(R.id.rv_results);

        int totalScore = 0;
        for (QuizQuestion q : quizQuestions) {
            if (q.isCorrect()) {
                totalScore++;
            }
        }

        tvTotalScore.setText("Total Score: " + totalScore + " / " + quizQuestions.size());

        QuizResultAdapter adapter = new QuizResultAdapter(quizQuestions);
        rvResults.setLayoutManager(new LinearLayoutManager(getContext()));
        rvResults.setAdapter(adapter);

        builder.setNegativeButton("Cancel", (dialog, which) ->{
            dialog.dismiss();
            AppSharedPreference.getInstance(getContext()).saveList("quiz_ans_"+ setid, quizQuestions);
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_home);
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void finishQuiz() {
        submitAnswers();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (timer != null) {
            timer.cancel();
        }
    }
}
