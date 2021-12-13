package com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailprivate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.viewHolder> {
    private String[] tags;
    private Context context;

    public TagsAdapter(String[] tags, Context context) {
        this.tags = tags;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_chips_small, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.tvTags.setText(tags[position]);
        holder.tvTags.setBackground(context.getResources().getDrawable(R.drawable.custom_button_gray));
    }

    @Override
    public int getItemCount() {
        return tags.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView tvTags;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTags = itemView.findViewById(R.id.tvChip);
        }
    }
}
