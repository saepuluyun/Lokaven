package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privatetrippreview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.helper.format.TextFormatHelper;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.AddonsItem;

import java.util.List;

public class AddOnsAdapter extends RecyclerView.Adapter<AddOnsAdapter.MyViewHolder> {

    private List<AddonsItem> mModelList;
    private Context context;
    private String type;

    public AddOnsAdapter(List<AddonsItem> mModelList, Context context, String type) {
        this.mModelList = mModelList;
        this.context = context;
        this.type = type;
    }

    public AddOnsAdapter(Context context, List<AddonsItem> modelList) {
        mModelList = modelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_addons, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AddonsItem addOnsItem = mModelList.get(position);
        holder.tvTitle.setText(addOnsItem.getAddon());

        if (addOnsItem.getPriceType().equals("user")) {
            holder.tvPerson.setVisibility(View.VISIBLE);
            holder.llPriceItem.setVisibility(View.GONE);

            if (addOnsItem.getPrice() != null && !addOnsItem.getPrice().isEmpty()){
                holder.tvPrice.setText(TextFormatHelper.getDecimalFormat(addOnsItem.getPrice()));
            }
        }

        if (addOnsItem.getPriceType().equals("item")) {
            holder.tvPerson.setVisibility(View.GONE);
            holder.llPriceItem.setVisibility(View.VISIBLE);

            if (addOnsItem.getPrice() != null && addOnsItem.getQty() != 0){
                holder.tvPrice.setText(TextFormatHelper.getDecimalFormat(addOnsItem.getPrice()));
                holder.tvItemAmount.setText(String.valueOf(addOnsItem.getQty()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public int getItemCount(boolean isScheduleClicked) {
        if(isScheduleClicked == false){
            return 1;
        } else {
            return mModelList.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView tvTitle, tvPrice, tvPerson, tvItemAmount;
        private LinearLayout llPriceItem;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvTitle = view.findViewById(R.id.tvTitle);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvPerson = view.findViewById(R.id.tvPerson);
            tvItemAmount = view.findViewById(R.id.tvItemAmount);
            llPriceItem = view.findViewById(R.id.llPriceItem);
        }
    }

}

