package com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailopen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder>{
    private String[] category;
    private Context context;

    public CategoryAdapter(String[] category, Context context) {
        this.category = category;
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
        holder.tvCategory.setText(category[position]);
        holder.tvCategory.setBackground(context.getResources().getDrawable(R.drawable.custom_button_blue));
    }

    @Override
    public int getItemCount() {
        return category.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView tvCategory;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvChip);
        }
    }
}
