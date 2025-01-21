package com.edu.io.pulse.ui.review;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.edu.io.pulse.databinding.FragmentQuizReviewBinding;

import java.util.ArrayList;
import java.util.List;
public class QuizReviewViewAdapter extends RecyclerView.Adapter<QuizReviewViewAdapter.ViewHolder> {

    private final List<AnsweredQuestion> mValues;

    public QuizReviewViewAdapter(List<AnsweredQuestion> items) {
        mValues = (items != null) ? items : new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentQuizReviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.textViewAnser.setText(mValues.get(position).getYouranswer());
        holder.textViewQuestion.setText(mValues.get(position).getQuestion());
        holder.textViewAnswerIfWrong.setText(mValues.get(position).getAnswer());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textViewQuestion;
        public final TextView textViewAnser;
        public final ImageView imgCheckedCorrent;
        public final TextView textViewAnswerIfWrong;

        public AnsweredQuestion mItem;

        public ViewHolder(FragmentQuizReviewBinding binding) {
            super(binding.getRoot());
            textViewQuestion = binding.question;
            textViewAnser = binding.answer;
            imgCheckedCorrent = binding.checkedCorrect;
            textViewAnswerIfWrong = binding.answerIfWrong;
        }


    }
}