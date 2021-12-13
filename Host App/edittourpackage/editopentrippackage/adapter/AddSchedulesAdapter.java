package com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.editopentrippackage.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.helper.date.DateHelper;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.SchedulesItem;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddSchedulesAdapter extends RecyclerView.Adapter<AddSchedulesAdapter.MyViewHolder> {

    private List<SchedulesItem> mModelList;
    private Context context;
    DateHelper dh = new DateHelper();
    private Date date1;
    private List<String> temp = new ArrayList<>();

    public AddSchedulesAdapter(Context context, List<SchedulesItem> modelList) {
        mModelList = modelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedules_open, parent, false);
        return new MyViewHolder(view);
    }

    public List<SchedulesItem> getOpenSchedule(){
        return mModelList;
    }


    public List<String> getDeleteSchedules(){

        return temp;
    }

    public List<String> removeDeleteSchedules(){
        temp.remove(0);
        notifyItemRemoved(0);
        return temp;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvRemovePrice.setVisibility(View.VISIBLE);
        final String myFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        final SchedulesItem schedulesEditItem = mModelList.get(position);
        final Calendar thisDate = Calendar.getInstance();

        try {
            holder.etStartDate.setText(dh.getDateFormat(schedulesEditItem.getStartDate()));
            holder.etEndDate.setText(dh.getDateFormat(schedulesEditItem.getEndDate()));
            holder.etMinQuota.setText(schedulesEditItem.getMinQuota());
            holder.etMaxQuota.setText(schedulesEditItem.getMaxQuota());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        if (thisDate.getTime().before(newDate.getTime())){

                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                holder.etStartDate.setText(dh.getDateFormat(sdf.format(newDate.getTime())));
                                mModelList.get(position).setStartDate(DateHelper.getTimeStampFormat(holder.etStartDate.getText().toString()));
                                date1 = newDate.getTime();
                                notifyItemChanged(position);
                                holder.tilStartDate.setError(null);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        } else {
                            holder.tilStartDate.setError(context.getString(R.string.text_please_input_start_date));
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
                    holder.tilStartDate.setError(context.getString(R.string.text_please_input_start_date));
                    holder.tilStartDate.requestFocus();
                } else {
                    Calendar newCalendar2 = Calendar.getInstance();
                    DatePickerDialog datePickerDialog2 = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            Calendar newDate2 = Calendar.getInstance();
                            newDate2.set(year, monthOfYear, dayOfMonth);

                            if (date1.before(newDate2.getTime())){
                                try {
                                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                    holder.etEndDate.setText(dh.getDateFormat(sdf.format(newDate2.getTime())));
                                    mModelList.get(position).setEndDate(DateHelper.getTimeStampFormat(holder.etEndDate.getText().toString()));
                                    notifyItemChanged(position);
                                    holder.tilEndDate.setError(null);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            } else {
                                holder.tilEndDate.setError(context.getString(R.string.text_please_input_end_date));
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
                schedulesEditItem.setMinQuota(s.toString());
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
                schedulesEditItem.setMaxQuota(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (mModelList.size() == 1){
            holder.tvRemovePrice.setVisibility(View.GONE);
        }

        holder.tvRemovePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.etEndDate.setText("");
                holder.etStartDate.setText("");
                holder.etMaxQuota.setText("");
                holder.etMinQuota.setText("");
                if (!mModelList.get(position).getScheduleId().isEmpty()){
                    temp.add(mModelList.get(position).getScheduleId());
                }
                mModelList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_desc, tvRemovePrice;
        TextInputEditText etStartDate, etEndDate, etMinQuota, etMaxQuota;
        TextInputLayout tilStartDate, tilEndDate, tilMinQuota, tilMaxQuota;

        private MyViewHolder(View itemView) {
            super(itemView);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tvRemovePrice = itemView.findViewById(R.id.tvRemovePrice);
            etStartDate = itemView.findViewById(R.id.etStartDate);
            etEndDate = itemView.findViewById(R.id.etEndDate);
            etMinQuota = itemView.findViewById(R.id.etMinQuota);
            etMaxQuota = itemView.findViewById(R.id.etMaxQuota);
            tilStartDate =itemView.findViewById(R.id.tilStartDate);
            tilEndDate =itemView.findViewById(R.id.tilEndDate);
            tilMinQuota =itemView.findViewById(R.id.tilMinQuota);
            tilMaxQuota =itemView.findViewById(R.id.tilMaxQuota);
            etStartDate.setKeyListener(null);
            etEndDate.setKeyListener(null);
            tv_desc.setText(Html.fromHtml("Leave the <font color= \"#000000\"><b>max quota</b></font> blank if you don’t limit the quota. <font color= \"#000000\"><b>Min quota</b></font> is required"));
        }
    }
}
