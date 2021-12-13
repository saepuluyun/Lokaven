package com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailopen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.helper.date.DateHelper;import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.SchedulesItem;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.schedulemanagement.opentourschedule.OpenTourScheduleActivity;

import java.text.ParseException;
import java.util.List;

public class ListScheduleOpenTripDetailAdapter extends RecyclerView.Adapter<ListScheduleOpenTripDetailAdapter.v_holder> {
    private Context mContext;
    private List<SchedulesItem> mModelList;

    public ListScheduleOpenTripDetailAdapter(Context mContext, List<SchedulesItem> mModelList) {
        this.mContext = mContext;
        this.mModelList = mModelList;
    }

    @NonNull
    @Override
    public v_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_schedule_open_trip_package_detail, parent, false);
        return new v_holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull v_holder holder, int position) {
        final SchedulesItem data = mModelList.get(position);
        DateHelper dh = new DateHelper();
        try {
            holder.tv_startDate.setText(dh.getDateFormat(data.getStartDate()));
            holder.tv_endDate.setText(dh.getDateFormat(data.getEndDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, OpenTourScheduleActivity.class);
                i.putExtra("tour_id", data.tourId);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModelList.size();
    }

    public class v_holder extends RecyclerView.ViewHolder {
        private TextView tv_startDate;
        private TextView tv_endDate;
        public v_holder(@NonNull View itemView) {
            super(itemView);
            Initialization(itemView);
        }

        private void Initialization(View v) {
            tv_startDate = v.findViewById(R.id.tv_startDate);
            tv_endDate = v.findViewById(R.id.tv_endDate);
        }
    }
}
