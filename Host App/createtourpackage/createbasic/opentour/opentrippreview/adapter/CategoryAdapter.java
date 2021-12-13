package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.opentour.opentrippreview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder> {
    List<String> category;
    Context mContext;

    public CategoryAdapter(List<String> category, Context mContext) {
        this.category = category;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tags, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.titleCategory.setText(category.get(position));
        holder.titleCategory.setBackground(mContext.getResources().getDrawable(R.drawable.custom_button_blue));
    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView titleCategory;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            titleCategory = itemView.findViewById(R.id.title_tags);
        }
    }
}
