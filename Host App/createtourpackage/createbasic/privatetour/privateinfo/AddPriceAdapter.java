package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privateinfo;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.utils.misc.inlinehelp.TooltipWindowUtils;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.createTour.SchedulesAndPriceItem;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class AddPriceAdapter extends RecyclerView.Adapter<AddPriceAdapter.vholder> {
    private List<SchedulesAndPriceItem> items;
    private Context mContext;
    private LinearLayout llKidsAdditionalPrice;
    private TooltipWindowUtils tipWindow;
    private TextInputEditText etAddIndividuPriceAdult, etAddIndividuPriceKids;

    public AddPriceAdapter(List<SchedulesAndPriceItem> items, Context mContext, LinearLayout llKidsAdditionalPrice, TextInputEditText etAddIndividuPriceAdult, TextInputEditText etAddIndividuPriceKids) {
        this.items = items;
        this.mContext = mContext;
        this.llKidsAdditionalPrice = llKidsAdditionalPrice;
        this.etAddIndividuPriceAdult = etAddIndividuPriceAdult;
        this.etAddIndividuPriceKids = etAddIndividuPriceKids;
        tipWindow = new TooltipWindowUtils(mContext);
    }

    @NonNull
    @Override
    public vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_price_kid, parent, false);
        return new vholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final vholder holder, final int position) {
        final SchedulesAndPriceItem schedulesAndPriceItem = items.get(position);
        holder.etMinQuota.setText(schedulesAndPriceItem.getPricesItem().getMinParticipant());
        holder.etMaxQuota.setText(schedulesAndPriceItem.getPricesItem().getMaxParticipant());
        holder.etMinAge.setText(schedulesAndPriceItem.getPricesItem().getMinKidAge());
        holder.etMaxAge.setText(schedulesAndPriceItem.getPricesItem().getMaxKidAge());
        holder.etKidsPrice.setText(schedulesAndPriceItem.getPricesItem().getKidPrice());
        holder.etPrice.setText(schedulesAndPriceItem.getPricesItem().getPrice());

        if (items.size() == 1){
            holder.tvRemoveSchedule.setVisibility(View.GONE);
        } else {
            holder.tvRemoveSchedule.setVisibility(View.VISIBLE);
        }

        holder.etMinQuota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    if (items.size() > 1){
                        if (items.get(position).getPricesItem().getMaxParticipant() != null && !items.get(position).getPricesItem().getMaxParticipant().isEmpty()){
                            if (Integer.valueOf(items.get(position).getPricesItem().getMaxParticipant().trim()) < Integer.valueOf(s.toString().trim())){
                                holder.tilMaxQuota.setError(mContext.getString(R.string.text_please_input_max_participant_greater_than_min_participant));
                            } else {
                                holder.tilMaxQuota.setError(null);
                            }

                        }
                    }

                    if (position != 0 && items.get(position-1).getPricesItem().getMaxParticipant() != null){
                        if (!items.get(position-1).getPricesItem().getMaxParticipant().equals("")){
                            if (Integer.valueOf(items.get(position-1).getPricesItem().getMaxParticipant().trim()) + 1 > Integer.valueOf(s.toString().trim())){
                                holder.tilMinQuota.setError(mContext.getString(R.string.text_please_input_min_quota_has_to_be) + " " + String.valueOf(Integer.valueOf(items.get(position-1).getPricesItem().getMaxParticipant()) + 1));
                            } else {
                                holder.tilMinQuota.setError(null);
                            }
                        }
                    }
                }

                if (s.toString().trim().isEmpty()){
                    holder.tilMinQuota.setError(mContext.getString(R.string.text_please_input_min_participants));
                } else {
                    items.get(position).getPricesItem().setMinParticipant(s.toString().trim());
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
                if (!s.toString().trim().isEmpty()){
                    if (items.get(position).getPricesItem().getMinParticipant() != null && !items.get(position).getPricesItem().getMinParticipant().isEmpty()){
                        if (items.get(position).getPricesItem().getMinParticipant().trim().equals("") || Integer.valueOf(items.get(position).getPricesItem().getMinParticipant().trim()) > Integer.valueOf(s.toString().trim())){
                            holder.tilMaxQuota.setError(mContext.getString(R.string.text_please_input_max_quota_less_than_min_participants));
                        } else {
                            holder.tilMaxQuota.setError(null);
                        }
                    }

                    if (items.size() > 1){
                        if (position == 0 && items.get(position+1).getPricesItem().getMinParticipant() != null){
                            if (!items.get(position+1).getPricesItem().getMinParticipant().equals("")){
                                if (Integer.valueOf(items.get(position+1).getPricesItem().getMinParticipant().trim()) - 1 < Integer.valueOf(s.toString().trim())){
                                    holder.tilMaxQuota.setError(mContext.getString(R.string.text_please_input_max_quota_has_to_be) + " " + String.valueOf(Integer.valueOf(items.get(position+1).getPricesItem().getMinParticipant()) - 1));
                                } else {
                                    holder.tilMaxQuota.setError(null);
                                }
                            }
                        }
                    }
                }

                if (s.toString().trim().isEmpty()){
                    holder.tilMaxQuota.setError(mContext.getString(R.string.text_please_input_max_participants));
                } else {
                    items.get(position).getPricesItem().setMaxParticipant(s.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()){
                    holder.tilPrice.setError(mContext.getString(R.string.text_please_input_price_more_than_IDR_9999));
                } else {
                    items.get(position).getPricesItem().setPrice(s.toString().trim());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.etKidsPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                items.get(position).getPricesItem().setKidPrice(s.toString().trim());
                if (!s.toString().trim().isEmpty()){
                    if (items.get(position).getPricesItem().getMaxKidAge() == null || items.get(position).getPricesItem().getMaxKidAge().isEmpty()){
                        holder.tilMaxAge.setError(mContext.getString(R.string.text_please_input_max_kids_age));
                    }

                    if (items.get(position).getPricesItem().getMinKidAge() == null || items.get(position).getPricesItem().getMinKidAge().isEmpty()){
                        holder.tilMinAge.setError(mContext.getString(R.string.text_please_input_min_kids_age));
                    }
                } else {
                    holder.tilMaxAge.setError(null);
                    holder.tilMinAge.setError(null);
                }

                if (!items.get(0).getPricesItem().getKidPrice().isEmpty() && !items.get(0).getPricesItem().getMaxKidAge().isEmpty() && !items.get(0).getPricesItem().getMinKidAge().isEmpty()){
                    llKidsAdditionalPrice.setVisibility(View.VISIBLE);
                } else {
                    if (etAddIndividuPriceAdult.getText().toString().isEmpty()){
                        llKidsAdditionalPrice.setVisibility(View.GONE);
                        etAddIndividuPriceKids.setText("");
                    } else {
                        llKidsAdditionalPrice.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.etMinAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                items.get(position).getPricesItem().setMinKidAge(s.toString().trim());
                if (items.get(position).getPricesItem().getKidPrice() != null && !items.get(position).getPricesItem().getKidPrice().trim().isEmpty()){
                    if (items.get(position).getPricesItem().getKidPrice() == null && items.get(position).getPricesItem().getKidPrice().isEmpty()){
                        holder.tilKidPrice.setError(mContext.getString(R.string.text_please_input_kids_price_more_than_IDR_9999));
                    }

                    if (!s.toString().trim().isEmpty()){
                        if (items.get(position).getPricesItem().getMaxKidAge() != null && !items.get(position).getPricesItem().getMaxKidAge().trim().isEmpty()){
                            if (Integer.valueOf(items.get(position).getPricesItem().getMaxKidAge().trim()) < Integer.valueOf(s.toString().trim())){
                                holder.tilMaxAge.setError(mContext.getString(R.string.text_please_input_max_kids_age_greater_than_min_kids_age));
                            } else {
                                holder.tilMaxAge.setError(null);
                            }

                            if (position != 0){
                                if (Integer.valueOf(items.get(position-1).getPricesItem().getMaxKidAge().trim()) > Integer.valueOf(items.get(position).getPricesItem().getMaxKidAge().trim())){
                                    items.get(position).getPricesItem().setMinKidAge("");
                                    holder.tilMinAge.setError(mContext.getString(R.string.text_please_input_min_kids_age) + " " + String.valueOf(position-1) + " " + mContext.getString(R.string.text_greater_than_min_kids_age) + " " + String.valueOf(position));
                                } else {
                                    holder.tilMinAge.setError(null);
                                }
                            }

                        }
                    }

                    if (!items.get(0).getPricesItem().getKidPrice().isEmpty() && !items.get(0).getPricesItem().getMaxKidAge().isEmpty() && !items.get(0).getPricesItem().getMinKidAge().isEmpty()){
                        llKidsAdditionalPrice.setVisibility(View.VISIBLE);
                    } else {
                        if (etAddIndividuPriceAdult.getText().toString().isEmpty()){
                            llKidsAdditionalPrice.setVisibility(View.GONE);
                            etAddIndividuPriceKids.setText("");
                        } else {
                            llKidsAdditionalPrice.setVisibility(View.VISIBLE);
                        }
                    }

                    if (s.toString().trim().isEmpty()){
                        items.get(position).getPricesItem().setMinKidAge("");
                        holder.tilMinAge.setError(mContext.getString(R.string.text_please_input_min_kids_age));
                    } else {
                        items.get(position).getPricesItem().setMinKidAge(s.toString().trim());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.etMaxAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                items.get(position).getPricesItem().setMaxKidAge(s.toString().trim());
                if (items.get(position).getPricesItem().getKidPrice() != null && !items.get(position).getPricesItem().getKidPrice().trim().isEmpty()){
                    if (items.get(position).getPricesItem().getKidPrice() == null && items.get(position).getPricesItem().getKidPrice().isEmpty()){
                        holder.tilKidPrice.setError(mContext.getString(R.string.text_please_input_kids_price_more_than_IDR_9999));
                    }

                    if (!s.toString().trim().isEmpty()){
                        if (items.get(position).getPricesItem().getMinKidAge() != null && !items.get(position).getPricesItem().getMinKidAge().trim().isEmpty()){
                            if (items.get(position).getPricesItem().getMinKidAge().trim().isEmpty() || Integer.valueOf(items.get(position).getPricesItem().getMinKidAge().trim()) > Integer.valueOf(s.toString().trim())){
                                items.get(position).getPricesItem().setMaxKidAge("");
                                holder.tilMaxAge.setError(mContext.getString(R.string.text_please_input_max_kids_age_greater_than_min_kids_age));
                            }
                        }
                    }

                    if (!items.get(0).getPricesItem().getKidPrice().isEmpty() && !items.get(0).getPricesItem().getMaxKidAge().isEmpty() && !items.get(0).getPricesItem().getMinKidAge().isEmpty()){
                        llKidsAdditionalPrice.setVisibility(View.VISIBLE);
                    } else {
                        if (etAddIndividuPriceAdult.getText().toString().isEmpty()){
                            llKidsAdditionalPrice.setVisibility(View.GONE);
                            etAddIndividuPriceKids.setText("");
                        } else {
                            llKidsAdditionalPrice.setVisibility(View.VISIBLE);
                        }
                    }

                    if (s.toString().trim().isEmpty()){
                        items.get(position).getPricesItem().setMaxKidAge("");
                        holder.tilMaxAge.setError(mContext.getString(R.string.text_please_input_kids_prices));
                    } else {
                        items.get(position).getPricesItem().setMaxKidAge(s.toString().trim());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.ivHelpParticipant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tipWindow.isTooltipShown()){
                    tipWindow.showToolTip(holder.ivHelpParticipant, mContext.getString(R.string.text_the_min_participant_need_to_be_bigger));
                } else {
                    tipWindow.dismissTooltip();
                }
            }
        });

        holder.ivHelpKidAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tipWindow.isTooltipShown()){
                    tipWindow.showToolTip(holder.ivHelpKidAge, mContext.getString(R.string.text_you_have_to_set_the_kids_min_and_max_age_if_you_set_the_kids_price));
                } else {
                    tipWindow.dismissTooltip();
                }
            }
        });

        holder.tvRemoveSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class vholder extends RecyclerView.ViewHolder {

        TextInputEditText etMinQuota, etMaxQuota, etPrice, etKidsPrice, etMinAge, etMaxAge;
        View view;
        TextView tvRemoveSchedule;
        ImageView ivHelpParticipant, ivHelpKidAge;
        TextInputLayout tilMinQuota,
                tilMaxQuota,
                tilPrice,
                tilKidPrice,
                tilMinAge,
                tilMaxAge;

        public vholder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ivHelpParticipant= itemView.findViewById(R.id.ivHelpParticipant);
            ivHelpKidAge = itemView.findViewById(R.id.ivHelpKidAge);
            etMinQuota = itemView.findViewById(R.id.etMinQuota);
            etMaxQuota = itemView.findViewById(R.id.etMaxQuota);
            etPrice = itemView.findViewById(R.id.etPrice);
            etKidsPrice = itemView.findViewById(R.id.etKidsPrice);
            etMinAge = itemView.findViewById(R.id.etMinAge);
            etMaxAge = itemView.findViewById(R.id.etMaxAge);
            tvRemoveSchedule = itemView.findViewById(R.id.tvRemoveSchedule);
            tilMinQuota = itemView.findViewById(R.id.tilMinQuota);
            tilMaxQuota = itemView.findViewById(R.id.tilMaxQuota);
            tilPrice = itemView.findViewById(R.id.tilPrice);
            tilKidPrice = itemView.findViewById(R.id.tilKidPrice);
            tilMinAge = itemView.findViewById(R.id.tilMinAge);
            tilMaxAge = itemView.findViewById(R.id.tilMaxAge);
        }
    }

    public List<SchedulesAndPriceItem> getItem(){
        return items;
    }

    public void addItem() {
        SchedulesAndPriceItem schedulesAndPriceItem = new SchedulesAndPriceItem();
        items.add(getItemCount(), schedulesAndPriceItem);
        notifyItemInserted(getItemCount()-1);
    }
}
