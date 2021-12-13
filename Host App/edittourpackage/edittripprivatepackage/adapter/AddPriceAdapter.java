package com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.edittripprivatepackage.adapter;

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
import com.aniqma.lokaventour.host.model.item.tourpackageservice.PricesItem;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class AddPriceAdapter extends RecyclerView.Adapter<AddPriceAdapter.vholder> {
    private List<PricesItem> items;
    private List<PricesItem> pricesItems = new ArrayList<>();
    private Context mContext;
    String minQuota= "", maxQuota = "", minAge = "", maxAge = "", price = "", kidsPrice = "";
    private TooltipWindowUtils tipWindow;
    private LinearLayout llKidsAdditionalPrice;
    private TextInputEditText etAddIndividuPriceAdult, etAddIndividuPriceKids;

    public AddPriceAdapter(List<PricesItem> items, Context mContext, LinearLayout llKidsAdditionalPrice, TextInputEditText etAddIndividuPriceAdult, TextInputEditText etAddIndividuPriceKids) {
        this.items = items;
        this.mContext = mContext;
        this.llKidsAdditionalPrice = llKidsAdditionalPrice;
        tipWindow = new TooltipWindowUtils(mContext);
        this.etAddIndividuPriceAdult = this.etAddIndividuPriceAdult;
        this.etAddIndividuPriceKids = this.etAddIndividuPriceKids;
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
        final PricesItem pricesItem = items.get(position);
        holder.SetButtonRemove(items.get(position));

        if (pricesItem.getKidPrice().equals("0")){
            pricesItem.setKidPrice("");
        }

        holder.etMinQuota.setText(pricesItem.getMinParticipant());
        holder.etMaxQuota.setText(pricesItem.getMaxParticipant());
        holder.etMinAge.setText(pricesItem.getMinKidAge());
        holder.etMaxAge.setText(pricesItem.getMaxKidAge());
        holder.etKidsPrice.setText(pricesItem.getKidPrice());
//        double price = Integer.parseInt(pricesItem.getPrice()) / 1.10;
//        long round = Math.round(price);
        holder.etPrice.setText(pricesItem.getPrice());
        items.get(position).setPrice(pricesItem.getPrice());

        holder.etMinQuota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    if (items.get(position).getMaxParticipant() != null && !items.get(position).getMaxParticipant().isEmpty()){
                        if (Integer.valueOf(items.get(position).getMaxParticipant().trim()) < Integer.valueOf(s.toString().trim())){
                            items.get(position).setMinParticipant("");
                            holder.tilMaxQuota.setError(mContext.getString(R.string.text_please_input_max_participant_greater_than_min_participant));
                        } else {
                            items.get(position).setMinParticipant(s.toString().trim());
                            holder.tilMaxQuota.setError(null);
                        }
                    }

                    if (position != 0 && items.get(position-1).getMaxParticipant() != null){
                        if (!items.get(position-1).getMaxParticipant().equals("")){
                            if (Integer.valueOf(items.get(position-1).getMaxParticipant().trim()) + 1 > Integer.valueOf(s.toString().trim())){
                                items.get(position).setMinParticipant("");
                                holder.tilMinQuota.setError(mContext.getString(R.string.text_please_input_min_quota_age_greater_than) + " " + String.valueOf(Integer.valueOf(items.get(position-1).getMaxParticipant()) + 1));
                            } else {
                                items.get(position).setMinParticipant(s.toString().trim());
                                holder.tilMinQuota.setError(null);
                            }
                        }
                    }
                }

                if (s.toString().trim().isEmpty()){
                    items.get(position).setMinParticipant("");
                    holder.tilMinQuota.setError(mContext.getString(R.string.text_please_input_min_participants));
                } else {
                    items.get(position).setMinParticipant(s.toString().trim());
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
                    if (items.get(position).getMinParticipant() != null && !items.get(position).getMinParticipant().isEmpty()){
                        if (items.get(position).getMinParticipant().trim().equals("") || Integer.valueOf(items.get(position).getMinParticipant().trim()) > Integer.valueOf(s.toString().trim())){
                            items.get(position).setMaxParticipant("");
                            holder.tilMaxQuota.setError(mContext.getString(R.string.text_please_input_max_participant_less_than_min_participant));
                        }
                    }

                    if (items.size() > 1){
                        if (position == 0 && items.get(position+1).getMinParticipant() != null){
                            if (!items.get(position+1).getMinParticipant().equals("")){
                                if (Integer.valueOf(items.get(position+1).getMinParticipant().trim()) - 1 < Integer.valueOf(s.toString().trim())){
                                    items.get(position).setMaxParticipant("");
                                    holder.tilMaxQuota.setError(mContext.getString(R.string.text_please_input_max_quota_age_less_than) + " " + String.valueOf(Integer.valueOf(items.get(position+1).getMinParticipant()) -1));
                                } else {
                                    items.get(position).setMaxParticipant(s.toString().trim());
                                    holder.tilMaxQuota.setError(null);
                                }
                            }
                        }
                    }
                }

                if (s.toString().trim().isEmpty()){
                    items.get(position).setMaxParticipant("");
                    holder.tilMaxQuota.setError(mContext.getString(R.string.text_please_input_max_participants));
                } else {
                    items.get(position).setMaxParticipant(s.toString().trim());
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
                    holder.etPrice.setError(mContext.getString(R.string.text_please_input_price));
                } else {
                    items.get(position).setPrice(s.toString().trim());
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
                items.get(position).setKidPrice(s.toString().trim());
                if (!s.toString().trim().isEmpty()){
                    if (items.get(position).getMaxKidAge() == null || items.get(position).getMaxKidAge().isEmpty()){
                        holder.tilMaxAge.setError(mContext.getString(R.string.text_please_input_max_kids_age));
                    }

                    if (items.get(position).getMinKidAge() == null || items.get(position).getMinKidAge().isEmpty()){
                        holder.tilMaxAge.setError(mContext.getString(R.string.text_please_input_min_kids_age));
                    }
                } else {
                    holder.tilMaxAge.setError(null);
                    holder.tilMaxAge.setError(null);
                }

                if (!items.get(0).getKidPrice().isEmpty() && !items.get(0).getMaxKidAge().isEmpty() && !items.get(0).getMinKidAge().isEmpty()){
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
                items.get(position).setMinKidAge(s.toString().trim());
                if (items.get(position).getKidPrice() != null && !items.get(position).getKidPrice().trim().isEmpty()){
                    if (items.get(position).getKidPrice() == null && items.get(position).getKidPrice().isEmpty()){
                        holder.tilKidPrice.setError(mContext.getString(R.string.text_please_input_kids_price_more_than_IDR_9999));
                    }

                    if (!s.toString().trim().isEmpty()){
                        if (items.get(position).getMaxKidAge() != null && !items.get(position).getMaxKidAge().trim().isEmpty()){
                            if (Integer.valueOf(items.get(position).getMaxKidAge().trim()) < Integer.valueOf(s.toString().trim())){
                                items.get(position).setMaxKidAge("");
                                holder.tilMaxAge.setError(mContext.getString(R.string.text_please_input_max_age_greater_than_min_age));
                            } else {
                                holder.tilMaxAge.setError(null);
                            }

                        }
                    }


                    if (!items.get(0).getKidPrice().isEmpty() && !items.get(0).getMaxKidAge().isEmpty() && !items.get(0).getMinKidAge().isEmpty()){
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
                        items.get(position).setMinKidAge("");
                        holder.tilMinAge.setError(mContext.getString(R.string.text_please_input_min_kids_age));
                    } else {
                        items.get(position).setMinKidAge(s.toString().trim());
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
                items.get(position).setMaxKidAge(s.toString().trim());
                if (items.get(position).getKidPrice() != null && !items.get(position).getKidPrice().trim().isEmpty()){
                    if (items.get(position).getKidPrice() == null && items.get(position).getKidPrice().isEmpty()){
                        holder.tilKidPrice.setError(mContext.getString(R.string.text_please_input_kids_price_more_than_IDR_9999));
                    }

                    if (!s.toString().trim().isEmpty()){
                        if (items.get(position).getMinKidAge() != null && !items.get(position).getMinKidAge().trim().isEmpty()){
                            if (items.get(position).getMinKidAge().trim().isEmpty() || Integer.valueOf(items.get(position).getMinKidAge().trim()) > Integer.valueOf(s.toString().trim())){
                                items.get(position).setMaxKidAge("");
                                holder.tilMaxAge.setError(mContext.getString(R.string.text_please_input_max_age_greater_than_min_age));
                            }
                        }
                    }


                    if (!items.get(0).getKidPrice().isEmpty() && !items.get(0).getMaxKidAge().isEmpty() && !items.get(0).getMinKidAge().isEmpty()){
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
                        items.get(position).setMaxKidAge("");
                        holder.tilMaxAge.setError(mContext.getString(R.string.text_please_input_max_kids_age));
                    } else {
                        items.get(position).setMaxKidAge(s.toString().trim());
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
                notifyDataSetChanged();
            }
        });
    }

    public List<PricesItem> getDataPrices() {
        return items;
    }

    @Override
    public int getItemCount() {
        if (items == null){
            return 0;
        } else {
            return items.size();
        }
    }

    public class vholder extends RecyclerView.ViewHolder {
        private TextInputEditText etMinQuota, etMaxQuota, etPrice, etKidsPrice, etMinAge, etMaxAge;
        private TextInputLayout tilMinQuota, tilMaxQuota, tilPrice, tilKidPrice, tilMinAge, tilMaxAge;
        private TextView tvRemoveSchedule;
        private ImageView ivHelpParticipant, ivHelpKidAge;

        public vholder(@NonNull View itemView) {
            super(itemView);
            Init(itemView);
            tvRemoveSchedule.setVisibility(View.VISIBLE);
        }

        private void Init(View view) {
            ivHelpParticipant= itemView.findViewById(R.id.ivHelpParticipant);
            ivHelpKidAge = itemView.findViewById(R.id.ivHelpKidAge);
            etMinQuota = view.findViewById(R.id.etMinQuota);
            etMaxQuota = view.findViewById(R.id.etMaxQuota);
            etPrice = view.findViewById(R.id.etPrice);
            etKidsPrice = view.findViewById(R.id.etKidsPrice);
            etMinAge = view.findViewById(R.id.etMinAge);
            etMaxAge = view.findViewById(R.id.etMaxAge);
            tvRemoveSchedule = view.findViewById(R.id.tvRemoveSchedule);
            tilMinQuota = view.findViewById(R.id.tilMinQuota);
            tilMaxQuota = view.findViewById(R.id.tilMaxQuota);
            tilPrice = view.findViewById(R.id.tilPrice);
            tilKidPrice = view.findViewById(R.id.tilKidPrice);
            tilMinAge = view.findViewById(R.id.tilMinAge);
            tilMaxAge = view.findViewById(R.id.tilMaxAge);
        }


        public void SetButtonRemove(PricesItem pricesItem) {
            if (items.size()==1){
                tvRemoveSchedule.setVisibility(View.GONE);
            }else {
                tvRemoveSchedule.setVisibility(View.VISIBLE);
            }
        }
    }
}
