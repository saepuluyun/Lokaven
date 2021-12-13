package com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailprivate.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.helper.format.TextFormatHelper;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.AddonsItem;

import java.util.List;

import static com.aniqma.lokaventour.host.helper.format.AlphabetHelper.firstCapitalize;

public class AddonsPrivateTripPackageAdapter extends RecyclerView.Adapter<AddonsPrivateTripPackageAdapter.ViewHolder> {

    private Activity activity;
    private List<AddonsItem> items;

    public AddonsPrivateTripPackageAdapter(Activity activity, List<AddonsItem> items) {
        this.activity = activity;
        this.items = items;
    }

    @NonNull
    @Override
    public AddonsPrivateTripPackageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_addons, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddonsPrivateTripPackageAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position), position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvPrice;
        private TextView tvPerson, tvItemAmount;
        private LinearLayout llPriceItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvPerson = itemView.findViewById(R.id.tvPerson);
            tvItemAmount = itemView.findViewById(R.id.tvItemAmount);
            llPriceItem = itemView.findViewById(R.id.llPriceItem);
        }

        public void bind(final AddonsItem item, int position) {
            tvTitle.setText(firstCapitalize(item.getAddon()));
            if (item.getPriceType().equals("user")) {
                tvPerson.setVisibility(View.VISIBLE);
                llPriceItem.setVisibility(View.GONE);

                if (item.getPrice() != null && !item.getPrice().isEmpty()){
                    tvPrice.setText(TextFormatHelper.getDecimalFormat(item.getPrice()));
                }
            }

            if (item.getPriceType().equals("item")) {
                tvPerson.setVisibility(View.GONE);
                llPriceItem.setVisibility(View.VISIBLE);

                if (item.getPrice() != null && item.getQty() != 0){
                    tvPrice.setText(TextFormatHelper.getDecimalFormat(item.getPrice()));
                    tvItemAmount.setText(String.valueOf(item.getQty()));
                }
            }
        }
    }
}
