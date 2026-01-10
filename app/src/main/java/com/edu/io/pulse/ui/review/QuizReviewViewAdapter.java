package com.edu.io.pulse.ui.review;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.edu.io.pulse.databinding.FragmentQuizReviewBinding;
import com.edu.io.pulse.ui.quiz.QuizQuestion;

import java.util.List;

public class QuizReviewViewAdapter extends RecyclerView.Adapter<QuizReviewViewAdapter.ViewHolder> {

    private final List<QuizQuestion> questions;

    public QuizReviewViewAdapter(List<QuizQuestion> items) {
        questions = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentQuizReviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        QuizQuestion question = this.questions.get(position);
        holder.mItem = question;

        // Fix: Convert int to String or get the option text to avoid Resources$NotFoundException
        String givenAnswerText = "Not Answered";
        if (question.getGivenAnswer() > 0 && question.getGivenAnswer() <= question.getOptions().length) {
            givenAnswerText = question.getOptions()[question.getGivenAnswer() - 1];
        }
        holder.textViewAnser.setText(givenAnswerText);

        holder.textViewQuestion.setText(question.getQuestion());

        String correctAnswerText = "";
        if (question.getAnswer() > 0 && question.getAnswer() <= question.getOptions().length) {
            correctAnswerText = "Correct: " + question.getOptions()[question.getAnswer() - 1];
        }
        holder.textViewAnswerIfWrong.setText(correctAnswerText);

        // Optional: Visual feedback for correct/incorrect
        if (question.isCorrect()) {
            holder.imgCheckedCorrent.setVisibility(View.VISIBLE);
            holder.textViewAnswerIfWrong.setVisibility(View.GONE);
        } else {
            holder.imgCheckedCorrent.setVisibility(View.INVISIBLE);
            holder.textViewAnswerIfWrong.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textViewQuestion;
        public final TextView textViewAnser;
        public final ImageView imgCheckedCorrent;
        public final TextView textViewAnswerIfWrong;

        public QuizQuestion mItem;

        public ViewHolder(FragmentQuizReviewBinding binding) {
            super(binding.getRoot());
            textViewQuestion = binding.question;
            textViewAnser = binding.answer;
            imgCheckedCorrent = binding.checkedCorrect;
            textViewAnswerIfWrong = binding.answerIfWrong;
        }
    }
}
