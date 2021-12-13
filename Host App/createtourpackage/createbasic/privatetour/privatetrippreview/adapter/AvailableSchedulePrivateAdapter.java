package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privatetrippreview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.helper.date.DateHelper;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.SchedulesItem;

import java.text.ParseException;
import java.util.List;

public class AvailableSchedulePrivateAdapter extends RecyclerView.Adapter<AvailableSchedulePrivateAdapter.MyViewHolder> {
    private List<SchedulesItem> mModelList;
    private Context context;

    public AvailableSchedulePrivateAdapter(List<SchedulesItem> mModelList, Context context) {
        this.mModelList = mModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule_private, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final SchedulesItem avaiableScheduleItem = mModelList.get(position);
        try {
            holder.start_date.setText(DateHelper.getDateFormat(avaiableScheduleItem.getStartDate()));
            holder.end_date.setText(DateHelper.getDateFormat(avaiableScheduleItem.getEndDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateHelper dh = new DateHelper();
        try {
            holder.start_date.setText(dh.getDateFormat(avaiableScheduleItem.getStartDate()));
            holder.end_date.setText(dh.getDateFormat(avaiableScheduleItem.getEndDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView start_date, end_date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            start_date = view.findViewById(R.id.startDate);
            end_date = view.findViewById(R.id.endDate);
        }
    }
}
