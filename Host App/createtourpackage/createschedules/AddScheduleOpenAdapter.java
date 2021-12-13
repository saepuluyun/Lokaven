package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createschedules;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.helper.date.DateHelper;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.SchedulesItem;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddScheduleOpenAdapter extends RecyclerView.Adapter<AddScheduleOpenAdapter.ViewHolder> {
    private List<SchedulesItem> items;
    private Activity activity;
    DateHelper dh = new DateHelper();
    private Date date1;
    private String myFormat;

    public AddScheduleOpenAdapter(List<SchedulesItem> items, Activity activity) {
        this.items = items;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_schedules_open, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        try {
            if (items.get(position).getEndDate() != null && !items.get(position).getEndDate().isEmpty()){
                holder.etEndDate.setText(dh.getDateFormat(items.get(position).getEndDate()));
            }

            if (items.get(position).getStartDate() != null && !items.get(position).getStartDate().isEmpty()){
                holder.etStartDate.setText(dh.getDateFormat(items.get(position).getStartDate()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (items.get(position).getMinQuota() != null && !items.get(position).getMinQuota().isEmpty()){
            holder.etMinQuota.setText(items.get(position).getMinQuota());
        }

        if (items.get(position).getMaxQuota() != null && !items.get(position).getMaxQuota().isEmpty()){
            holder.etMaxQuota.setText(items.get(position).getMaxQuota());
        }

        if (items.size() == 1){
            holder.tvRemovePrice.setVisibility(View.GONE);
        } else {
            holder.tvRemovePrice.setVisibility(View.VISIBLE);
        }

        final String myFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        final Calendar thisDate = Calendar.getInstance();
//        thisDate.add(Calendar.DAY_OF_YEAR, +3);

        holder.etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        if (thisDate.getTime().before(newDate.getTime())){

                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                holder.etStartDate.setText(dh.getDateFormat(sdf.format(newDate.getTime())));
                                items.get(position).setStartDate(DateHelper.getTimeStampFormat(dh.getDateFormat(sdf.format(newDate.getTime()))));
                                date1 = newDate.getTime();
                                holder.tilStartDate.setError(null);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
//                        else if (thisDate.getTime().after(newDate.getTime())) {
//                            holder.tilStartDate.setError(activity.getText(R.string.text_please_input_start_date_H3));
//                            holder.tilStartDate.requestFocus();
//                        }
                        else {
                            holder.tilStartDate.setError(activity.getText(R.string.text_start_date_must_be_greater_than_today));
                            holder.tilStartDate.requestFocus();
                        }
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }

        });

        holder.etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.etStartDate.getText().toString().trim().isEmpty()){
                    holder.tilStartDate.setError(activity.getText(R.string.text_please_input_start_date));
                    holder.tilStartDate.requestFocus();
                } else {
                    Calendar newCalendar2 = Calendar.getInstance();
                    DatePickerDialog datePickerDialog2 = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            Calendar newDate2 = Calendar.getInstance();
                            newDate2.set(year, monthOfYear, dayOfMonth);

                            if (date1.before(newDate2.getTime())){
                                try {
                                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                    holder.etEndDate.setText(dh.getDateFormat(sdf.format(newDate2.getTime())));
                                    items.get(position).setEndDate(DateHelper.getTimeStampFormat(dh.getDateFormat(sdf.format(newDate2.getTime()))));
                                    holder.tilEndDate.setError(null);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else if(date1 == null) {
                                holder.tilEndDate.setError(activity.getText(R.string.text_dont_selected_end_date_before_start_date));
                                holder.tilEndDate.requestFocus();
                            } else {
                                holder.tilEndDate.setError(activity.getText(R.string.text_end_date_must_be_greater_than_start_date));
                                holder.tilEndDate.requestFocus();
                            }
                        }

                    }, newCalendar2.get(Calendar.YEAR), newCalendar2.get(Calendar.MONTH), newCalendar2.get(Calendar.DAY_OF_MONTH));

                    datePickerDialog2.show();
                }
            }
        });

        holder.etMinQuota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                items.get(position).setMinQuota(s.toString());
                if (items.get(position).getMaxQuota() != null && !items.get(position).getMaxQuota().trim().isEmpty()){
                    if (!s.toString().trim().isEmpty()){
                        if (Integer.valueOf(items.get(position).getMaxQuota().trim()) < Integer.valueOf(s.toString().trim())){
                            holder.tilMaxQuota.setError(activity.getText(R.string.text_please_input_max_quota_greater_than_min_quota));
                        } else {
                            holder.tilMaxQuota.setError(null);
                        }
                    } else {
                        holder.tilMinQuota.setError(activity.getText(R.string.text_please_input_min_quota));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.etMaxQuota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                items.get(position).setMaxQuota(s.toString());
                if (items.get(position).getMinQuota() != null && !items.get(position).getMinQuota().trim().isEmpty()){
                    if (!s.toString().trim().isEmpty()) {
                        if (Integer.valueOf(items.get(position).getMinQuota().trim()) > Integer.valueOf(s.toString().trim())) {
                            holder.tilMaxQuota.setError(activity.getText(R.string.text_please_input_max_quota_greater_than_min_quota));
                        }
                    }
                } else {
                    holder.tilMinQuota.setError(activity.getText(R.string.text_please_input_min_quota));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.tvRemovePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                holder.etEndDate.getText().clear();
                holder.etStartDate.getText().clear();
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextInputEditText etStartDate, etEndDate, etMinQuota, etMaxQuota;
        TextView tvRemovePrice;
        TextInputLayout tilStartDate, tilEndDate, tilMinQuota, tilMaxQuota;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etStartDate = itemView.findViewById(R.id.etStartDate);
            etEndDate = itemView.findViewById(R.id.etEndDate);
            etMinQuota = itemView.findViewById(R.id.etMinQuota);
            etMaxQuota = itemView.findViewById(R.id.etMaxQuota);
            tvRemovePrice = itemView.findViewById(R.id.tvRemovePrice);
            tilStartDate = itemView.findViewById(R.id.tilStartDate);
            tilEndDate = itemView.findViewById(R.id.tilEndDate);
            tilMaxQuota = itemView.findViewById(R.id.tilMaxQuota);
            tilMinQuota = itemView.findViewById(R.id.tilMinQuota);
            etStartDate.setKeyListener(null);
            etEndDate.setKeyListener(null);

            myFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        }
    }

    public List<SchedulesItem> getItem(){
        return items;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void addItem() {
        notifyItemInserted(getItemCount()-1);
    }
}
