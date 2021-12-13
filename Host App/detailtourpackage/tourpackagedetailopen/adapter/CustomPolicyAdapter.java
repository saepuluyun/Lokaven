package com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailopen.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.CustomPolicyItem;

import java.util.List;

public class CustomPolicyAdapter extends RecyclerView.Adapter<CustomPolicyAdapter.viewHolder>{
    private Activity activity;
    private List<CustomPolicyItem> items;

    public CustomPolicyAdapter(Activity activity, List<CustomPolicyItem> items) {
        this.activity = activity;
        this.items = items;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_policies_private_trip, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDet;
        View divider;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDet = itemView.findViewById(R.id.tvDet);
            divider = itemView.findViewById(R.id.divider);
        }
        public void bind(final CustomPolicyItem item) {
            tvTitle.setText(item.getPolicyName());
            tvDet.setText(item.getPolicy());
        }
    }
}
