package com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailopen.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.AddonsItem;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import static com.aniqma.lokaventour.host.helper.format.AlphabetHelper.firstCapitalize;

public class AddonsAdapter extends RecyclerView.Adapter<AddonsAdapter.viewHolder> {
    private Activity activity;
    private List<AddonsItem> items;

    public AddonsAdapter(Activity activity, List<AddonsItem> items) {
        this.activity = activity;
        this.items = items;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_addons, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.bind(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
        public void bind(final AddonsItem item, int position) {
            NumberFormat formatter = new DecimalFormat("#,###");

            String price = "";
            if (item.getPrice().equals("")) {
                price = "0";
            } else {
                price = formatter.format((double) Integer.parseInt(item.getPrice()));
            }

            tvTitle.setText(firstCapitalize(item.getAddon()));
            tvPrice.setText(String.valueOf(price));

        }
    }
}
