package com.edu.io.pulse.core;

import androidx.recyclerview.widget.RecyclerView;

public interface OnItemClickListener<VH extends RecyclerView.ViewHolder, E> {
    void onItemClick(VH viewHolder, int position, E item);
}
