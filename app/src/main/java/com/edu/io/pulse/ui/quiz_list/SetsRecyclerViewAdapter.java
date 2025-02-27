package com.edu.io.pulse.ui.quiz_list;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.io.pulse.R;
import com.edu.io.pulse.core.OnItemClickListener;
import com.edu.io.pulse.databinding.FragmentItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SetsRecyclerViewAdapter extends RecyclerView.Adapter<SetsRecyclerViewAdapter.ViewHolder> {

    private final List<SetsDomain> mValues;
    private OnItemClickListener onItemClickListener;

    public SetsRecyclerViewAdapter(List<SetsDomain> items, OnItemClickListener onItemClickListener) {
        mValues = items;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setName.setText(mValues.get(position).getSetName());
        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(holder, position, mValues.get(position));
            }
        });
        holder.itemView.setBackgroundResource(
                mValues.get(position).getStatus() == SetsDomain.Status.LOCKED?
                        R.drawable.locked_list_item:
                        R.drawable.unlocked_list_item
        );

        holder.statusImage.setImageResource(
                mValues.get(position).getStatus() == SetsDomain.Status.LOCKED ?
                        R.drawable.lock_yellow :
                        R.drawable.bg_round

        );

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView setName;
        public final ImageView statusImage;


        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            setName = binding.setName;
            statusImage = binding.statusImg;
        }


        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + setName.getText() + "'";
        }
    }
}