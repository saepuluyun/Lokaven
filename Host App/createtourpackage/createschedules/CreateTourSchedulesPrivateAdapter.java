package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createschedules;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.utils.misc.inlinehelp.TooltipWindowUtils;
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

public class CreateTourSchedulesPrivateAdapter extends RecyclerView.Adapter<CreateTourSchedulesPrivateAdapter.ViewHolder> {

    private List<SchedulesItem> items;
    private Activity activity;
    private SimpleDateFormat dateFormatter, dateStamp;
    DateHelper dh = new DateHelper();
    private Date date1;
    private Date date2;
    Calendar newDate2, newDate;
    private TooltipWindowUtils tipWindow;

    public CreateTourSchedulesPrivateAdapter(Activity activity, List<SchedulesItem> items) {
        this.activity = activity;
        this.items = items;
        tipWindow = new TooltipWindowUtils(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_schedules_private, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.bind(items.get(position));
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        final String myFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        dateStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        final Calendar thisDate = Calendar.getInstance();
//        thisDate.add(Calendar.DAY_OF_YEAR, +3);

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

        if (items.size() == 1){
            holder.tvRemovePrice.setVisibility(View.GONE);
        } else {
            holder.tvRemovePrice.setVisibility(View.VISIBLE);
        }

        holder.etStartDate.setKeyListener(null);
        holder.etEndDate.setKeyListener(null);

        holder.etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        if (thisDate.getTime().before(newDate.getTime())){

                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                holder.etStartDate.setText(dh.getDateFormat(sdf.format(newDate.getTime())));
                                items.get(position).setStartDate(DateHelper.getTimeStampFormat(holder.etStartDate.getText().toString()));
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

                            newDate2 = Calendar.getInstance();
                            newDate2.set(year, monthOfYear, dayOfMonth);

                            if (date1.before(newDate2.getTime())){
                                try {
                                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                    holder.etEndDate.setText(dh.getDateFormat(sdf.format(newDate2.getTime())));
                                    items.get(position).setEndDate(DateHelper.getTimeStampFormat(holder.etEndDate.getText().toString()));
                                    holder.tilEndDate.setError(null);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else if(date1 == null) {
                                holder.tilEndDate.setError(activity.getString(R.string.text_dont_selected_end_date_before_start_date));
                                holder.tilEndDate.requestFocus();
                            } else {
                                holder.tilEndDate.setError(activity.getString(R.string.text_end_date_must_be_greater_than_start_date));
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
                items.remove(position);
                holder.etEndDate.getText().clear();
                holder.etStartDate.getText().clear();
                notifyDataSetChanged();
            }
        });

        holder.ivHelpSchedules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tipWindow.isTooltipShown()){
                    tipWindow.showToolTip(holder.ivHelpSchedules, activity.getString(R.string.text_you_can_set_the_same_day_for_start_and_end_date));
                } else {
                    tipWindow.dismissTooltip();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextInputEditText etStartDate, etEndDate;
        private TextView tvRemovePrice;
        private ImageView ivHelpSchedules;
        private TextInputLayout tilStartDate,
                tilEndDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            etStartDate = itemView.findViewById(R.id.etStartDate);
            etEndDate = itemView.findViewById(R.id.etEndDate);
            tvRemovePrice = itemView.findViewById(R.id.tvRemovePrice);
            etStartDate.setKeyListener(null);
            etEndDate.setKeyListener(null);
            ivHelpSchedules = itemView.findViewById(R.id.ivHelpSchedules);
            tilStartDate = itemView.findViewById(R.id.tilStartDate);
            tilEndDate = itemView.findViewById(R.id.tilEndDate);

        }

        public void bind(final SchedulesItem item) {
        }
    }

    public List<SchedulesItem> getItem(){
        return items;
    }

    public void addItem() {
        notifyItemInserted(getItemCount()-1);
    }
}
