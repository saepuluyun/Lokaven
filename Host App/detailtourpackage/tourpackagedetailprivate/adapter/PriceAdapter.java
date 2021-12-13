package com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailprivate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.PricesItem;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.MyViewHolder> {

    private List<PricesItem> mModelList;
    private Context context;

    public PriceAdapter(Context context, List<PricesItem> modelList) {
        mModelList = modelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_price, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final PricesItem priceItem = mModelList.get(position);
        NumberFormat formatter = new DecimalFormat("#,###");
        double adultPrice = Double.parseDouble(priceItem.getPrice());
        double childPrice = Double.parseDouble(priceItem.getKidPrice());

        if (childPrice <= 0){
            holder.llKidsPrice.setVisibility(View.GONE);
        } else {
            holder.llKidsPrice.setVisibility(View.VISIBLE);
        }

        holder.price_person.setText(String.valueOf(formatter.format(adultPrice)));
        holder.price_child.setText(String.valueOf(formatter.format(childPrice)));

        if (Integer.valueOf(priceItem.getMinParticipant()) == 0){
            holder.relativeLayout_person_or_less.setVisibility(View.VISIBLE);
            holder.relativeLayout_persons.setVisibility(View.GONE);
            holder.relativeLayout_person_or_more.setVisibility(View.GONE);
            holder.max_num_person_or_less.setText(String.valueOf(priceItem.getMaxParticipant()));
        } else if (Integer.valueOf(priceItem.getMaxParticipant()) >= 999){
            holder.relativeLayout_person_or_less.setVisibility(View.GONE);
            holder.relativeLayout_persons.setVisibility(View.GONE);
            holder.relativeLayout_person_or_more.setVisibility(View.VISIBLE);
            holder.min_num_person_or_more.setText(String.valueOf(priceItem.getMinParticipant()));
        } else {
            holder.relativeLayout_person_or_less.setVisibility(View.GONE);
            holder.relativeLayout_persons.setVisibility(View.VISIBLE);
            holder.relativeLayout_person_or_more.setVisibility(View.GONE);
            holder.min_person.setText(String.valueOf(priceItem.getMinParticipant()));
            holder.max_person.setText(String.valueOf(priceItem.getMaxParticipant()));
        }


    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView price_person;
        private TextView price_child;
        private TextView max_num_person_or_less;
        private TextView max_person;
        private TextView min_person;
        private TextView min_num_person_or_more;

        private RelativeLayout relativeLayout_person_or_less;
        private RelativeLayout relativeLayout_persons;
        private RelativeLayout relativeLayout_person_or_more;
        private LinearLayout llKidsPrice;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            price_person = itemView.findViewById(R.id.price_person);
            price_child = itemView.findViewById(R.id.price_child);
            max_num_person_or_less = itemView.findViewById(R.id.max_num_person_or_less);
            max_person = itemView.findViewById(R.id.max_person);
            min_person = itemView.findViewById(R.id.min_person);
            min_num_person_or_more = itemView.findViewById(R.id.min_num_person_or_more);
            relativeLayout_person_or_less = itemView.findViewById(R.id.relativeLayout_person_or_less);
            relativeLayout_persons = itemView.findViewById(R.id.relativeLayout_persons);
            relativeLayout_person_or_more = itemView.findViewById(R.id.relativeLayout_person_or_more);
            llKidsPrice = itemView.findViewById(R.id.llKidsPrice);
        }
    }
}
