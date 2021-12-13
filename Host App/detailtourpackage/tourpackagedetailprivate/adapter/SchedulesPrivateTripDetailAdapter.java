package com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailprivate.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.helper.date.DateHelper;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.SchedulesItem;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.schedulemanagement.privatetourschedule.PrivateTourScheduleActivity;

import java.text.ParseException;
import java.util.List;

public class SchedulesPrivateTripDetailAdapter extends RecyclerView.Adapter<SchedulesPrivateTripDetailAdapter.ViewHolder> {

    private List<SchedulesItem> items;
    private Activity activity;

    public SchedulesPrivateTripDetailAdapter(Activity activity, List<SchedulesItem> items) {
        this.activity = activity;
        this.items = items;
    }

    @NonNull
    @Override
    public SchedulesPrivateTripDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_schedules_private_trip, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchedulesPrivateTripDetailAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvStatus);
        }

        public void bind(final SchedulesItem item, int position) {
//            DateFormat df = new SimpleDateFormat("dd MMM yyyy");
            DateHelper dh = new DateHelper();
            try {
                tvDate.setText(dh.getDateFormat(item.getStartDate())+" - "+dh.getDateFormat(item.getEndDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, PrivateTourScheduleActivity.class);
                    i.putExtra("tour_id", item.tourId);
                    activity.startActivity(i);
                }
            });
        }
    }
}
