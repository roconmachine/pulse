package com.edu.io.pulse.ui.reviews;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.io.pulse.ui.review.AnsweredQuestion;
import com.edu.io.pulse.ui.reviews.placeholder.PlaceholderContent.PlaceholderItem;
import com.edu.io.pulse.databinding.FragmentReviewBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private final List<AnsweredQuestion> mValues;

    public ReviewsAdapter(List<AnsweredQuestion> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentReviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getId() +"");
        holder.mContentView.setText(mValues.get(position).getQuestion());
        holder.answer.setText(mValues.get(position).getAnswer() + "");


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView answer;
        public final ImageView checkedAnser;
        public final TextView correctAnswer;
        public AnsweredQuestion mItem;

        public ViewHolder(FragmentReviewBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            answer = binding.answer;
            checkedAnser = binding.checkedAnswer;
            correctAnswer = binding.correctAnswer;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}