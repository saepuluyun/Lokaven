package com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.edittripprivatepackage.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.utils.misc.inlinehelp.TooltipWindowUtils;
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
    private Date date1;
    private List<String> temp = new ArrayList<>();
    private TooltipWindowUtils tipWindow;

    DatePickerDialog.OnDateSetListener startDatePick, endDatePick;

    public AddSchedulesAdapter(Context context, List<SchedulesItem> modelList) {
        mModelList = modelList;
        this.context = context;
        tipWindow = new TooltipWindowUtils(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedules_private, parent, false);
        return new MyViewHolder(view);
    }

    public List<SchedulesItem> getSchedules(){
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
        holder.setRemove(mModelList.get(position));
        final DateHelper dh = new DateHelper();
        final SchedulesItem dataSchedules = mModelList.get(position);
        final String myFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        final Calendar thisDate = Calendar.getInstance();

        try {
            holder.etStartDate.setText(dh.getDateFormat(dataSchedules.getStartDate()));
            holder.etEndDate.setText(dh.getDateFormat(dataSchedules.getEndDate()));
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

        holder.tvRemovePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.etStartDate.setText("");
                holder.etEndDate.setText("");
                if (!mModelList.get(position).getScheduleId().isEmpty()){
                    temp.add(mModelList.get(position).getScheduleId());
                }
                mModelList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.ivHelpSchedules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tipWindow.isTooltipShown()){
                    tipWindow.showToolTip(holder.ivHelpSchedules, context.getString(R.string.text_you_can_set_the_same_day_for_start_and_end_date));
                } else {
                    tipWindow.dismissTooltip();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextInputEditText etStartDate, etEndDate;
        private TextInputLayout tilStartDate, tilEndDate;
        final Calendar myCalendar = Calendar.getInstance();
        private TextView tvRemovePrice;
        private ImageView ivHelpSchedules;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            etStartDate = itemView.findViewById(R.id.etStartDate);
            tvRemovePrice = itemView.findViewById(R.id.tvRemovePrice);
            etEndDate = itemView.findViewById(R.id.etEndDate);
            etStartDate.setKeyListener(null);
            etEndDate.setKeyListener(null);
            tvRemovePrice.setVisibility(View.VISIBLE);
            ivHelpSchedules = itemView.findViewById(R.id.ivHelpSchedules);
            tilStartDate = itemView.findViewById(R.id.tilStartDate);
            tilEndDate = itemView.findViewById(R.id.tilEndDate);
        }

        public void setRemove(SchedulesItem schedulesItem) {
            if (mModelList.size() == 1){
                tvRemovePrice.setVisibility(View.GONE);
            }else {
                tvRemovePrice.setVisibility(View.VISIBLE);
            }
        }
    }
}
