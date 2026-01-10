package com.edu.io.pulse.ui.quiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.io.pulse.R;

import java.util.List;

public class QuizResultAdapter extends RecyclerView.Adapter<QuizResultAdapter.ViewHolder> {

    private final List<QuizQuestion> questions;

    public QuizResultAdapter(List<QuizQuestion> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuizQuestion question = questions.get(position);
        holder.tvQuestion.setText(question.getQuestion());
        
        String givenAnswer = "Not Answered";
        if (question.getGivenAnswer() > 0 && question.getGivenAnswer() <= question.getOptions().length) {
            givenAnswer = question.getOptions()[question.getGivenAnswer() - 1];
        }
        holder.tvGivenAnswer.setText("Your Answer: " + givenAnswer);

        String correctAnswer = "";
        if (question.getAnswer() > 0 && question.getAnswer() <= question.getOptions().length) {
            correctAnswer = question.getOptions()[question.getAnswer() - 1];
        }
        holder.tvCorrectAnswer.setText("Correct Answer: " + correctAnswer);
        
        holder.tvScore.setText("Score: " + (question.isCorrect() ? "1" : "0"));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion, tvGivenAnswer, tvCorrectAnswer, tvScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tv_question);
            tvGivenAnswer = itemView.findViewById(R.id.tv_given_answer);
            tvCorrectAnswer = itemView.findViewById(R.id.tv_correct_answer);
            tvScore = itemView.findViewById(R.id.tv_score);
        }
    }
}
