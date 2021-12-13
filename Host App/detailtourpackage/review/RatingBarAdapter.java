package com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.model.item.rateandreview.DataRatesItem;

import java.util.ArrayList;

public class RatingBarAdapter extends RecyclerView.Adapter<RatingBarAdapter.vHolder> {
    private Context mContext;
    private ArrayList<DataRatesItem> mModelList;

    public RatingBarAdapter(Context mContext, ArrayList<DataRatesItem> mModelList) {
        this.mContext = mContext;
        this.mModelList = mModelList;
    }

    @NonNull
    @Override
    public vHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.component_info_rating_bar, viewGroup, false);
        return new vHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vHolder holder, int position) {
        DataRatesItem data = mModelList.get(position);
        holder.progressBar.setProgress(data.getCount());
        holder.ratingBar.setRating(Float.parseFloat(data.getStars()));
        holder.tvValueProgress.setText(String.valueOf(data.getCount()));
    }

    @Override
    public int getItemCount() {
        return mModelList.size();
    }

    public class vHolder extends RecyclerView.ViewHolder {
        private RatingBar ratingBar;
        private ProgressBar progressBar;
        private TextView tvValueProgress;
        public vHolder(@NonNull View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View v) {
            ratingBar = v.findViewById(R.id.ratingBar);
            progressBar = v.findViewById(R.id.pbInfo);
            tvValueProgress = v.findViewById(R.id.tvValueProgress);
        }
    }
}
