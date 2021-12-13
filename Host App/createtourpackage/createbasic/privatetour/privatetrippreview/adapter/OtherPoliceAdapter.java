package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privatetrippreview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.CustomPolicyItem;

import java.util.List;

public class OtherPoliceAdapter extends RecyclerView.Adapter<OtherPoliceAdapter.v_holder> {
    private Context mContext;
    private List<CustomPolicyItem> data;

    public OtherPoliceAdapter(Context mContext, List<CustomPolicyItem> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public v_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_other_policies, parent, false);
        return new v_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull v_holder holder, int position) {
        CustomPolicyItem getData = data.get(position);
        holder.tvTitle.setText(getData.getPolicyName());
        holder.tvDescription.setText(getData.getPolicy());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class v_holder extends RecyclerView.ViewHolder {
     private TextView tvTitle, tvDescription;
        public v_holder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }
}
