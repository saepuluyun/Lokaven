package com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.model.item.tourexplorer.PricesItem;

import java.util.List;

import static com.aniqma.lokaventour.helper.format.NumberHelper.formatIdr;

public class ListPriceAdapter extends RecyclerView.Adapter<ListPriceAdapter.MyViewHolder> {

    private List<PricesItem> mModelList;
    private Context context;
    private String typeTour, minQuota, maxQuota;

    public ListPriceAdapter(Context context, List<PricesItem> modelList, String typeTour, String minQuota, String maxQuota) {
        mModelList = modelList;
        this.context = context;
        this.typeTour = typeTour;
        this.minQuota = minQuota;
        this.maxQuota = maxQuota;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_price, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final PricesItem priceItem = mModelList.get(position);

        double price = Double.parseDouble(priceItem.getPrice());
        double kidPrice = Double.parseDouble(priceItem.getKidPrice());

        if (kidPrice <= 0){
            holder.llKidsPrice.setVisibility(View.GONE);
        } else {
            holder.llKidsPrice.setVisibility(View.VISIBLE);
        }

        holder.price_person.setText(formatIdr(price));
        holder.price_child.setText(formatIdr(kidPrice));

        String minParticipant = priceItem.getMinParticipant();
        String maxParticipant = priceItem.getMaxParticipant();

        if (typeTour.equals("open")) {
            if (minQuota.equals("0") || minQuota.equals("")){
                holder.relativeLayout_person_or_less.setVisibility(View.VISIBLE);
                holder.relativeLayout_persons.setVisibility(View.GONE);
                holder.relativeLayout_person_or_more.setVisibility(View.GONE);
                holder.max_num_person_or_less.setText(maxQuota);
            } else if (maxQuota.equals("0") || maxQuota.equals("")){
                holder.relativeLayout_person_or_less.setVisibility(View.GONE);
                holder.relativeLayout_persons.setVisibility(View.GONE);
                holder.relativeLayout_person_or_more.setVisibility(View.VISIBLE);
                holder.min_num_person_or_more.setText(String.valueOf(minQuota));
            } else {
                holder.relativeLayout_person_or_less.setVisibility(View.GONE);
                holder.relativeLayout_persons.setVisibility(View.VISIBLE);
                holder.relativeLayout_person_or_more.setVisibility(View.GONE);
                holder.min_person.setText(String.valueOf(minQuota));
                holder.max_person.setText(String.valueOf(maxQuota));
            }
        } else {
            if (minParticipant.equals("0") || minParticipant.equals("")){
                holder.relativeLayout_person_or_less.setVisibility(View.VISIBLE);
                holder.relativeLayout_persons.setVisibility(View.GONE);
                holder.relativeLayout_person_or_more.setVisibility(View.GONE);
                holder.max_num_person_or_less.setText(maxQuota);
            } else if (maxParticipant.equals("0") || maxParticipant.equals("")){
                holder.relativeLayout_person_or_less.setVisibility(View.GONE);
                holder.relativeLayout_persons.setVisibility(View.GONE);
                holder.relativeLayout_person_or_more.setVisibility(View.VISIBLE);
                holder.min_num_person_or_more.setText(String.valueOf(minQuota));
            } else {
                holder.relativeLayout_person_or_less.setVisibility(View.GONE);
                holder.relativeLayout_persons.setVisibility(View.VISIBLE);
                holder.relativeLayout_person_or_more.setVisibility(View.GONE);
                holder.min_person.setText(minParticipant);
                holder.max_person.setText(maxParticipant);
            }
        }

    }

    void deleteItem(int index) {
        mModelList.remove(index);
        notifyItemRemoved(index);
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
