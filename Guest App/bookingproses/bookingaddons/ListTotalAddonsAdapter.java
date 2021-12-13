package com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingaddons;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.AddonsTotalItem;

import java.util.List;

import static com.aniqma.lokaventour.helper.format.NumberHelper.formatIdr;

public class ListTotalAddonsAdapter extends RecyclerView.Adapter<ListTotalAddonsAdapter.MyViewHolder>{


    private Activity activity;
    private List<AddonsTotalItem> listAddonsItem;

    public ListTotalAddonsAdapter(Activity activity, List<AddonsTotalItem> listAddonsItem) {
        this.activity = activity;
        this.listAddonsItem = listAddonsItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_total_addons, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AddonsTotalItem addonsItem = listAddonsItem.get(position);
        int qtyAdult = addonsItem.getQtyAdult();
        int qtyKids = addonsItem.getQtyKids();
        int qtyAdditionalAdult = addonsItem.getAdditionalAdult();
        int qtyAdditionalKids = addonsItem.getAdditionalKids();
        int totalParticipant = qtyAdult+qtyKids+qtyAdditionalAdult+qtyAdditionalKids;
        int priceUser = addonsItem.getPrice()*totalParticipant;

        if (addonsItem.getPriceType().equals("user")) {

            holder.llSubUser.setVisibility(View.VISIBLE);
            holder.llSubItem.setVisibility(View.GONE);

            holder.tvAddonsUserName.setText(addonsItem.getAddon());
            holder.tvTotalParticipant.setText(String.valueOf(totalParticipant));
            holder.tvPriceUser.setText(formatIdr(Double.parseDouble(String.valueOf(priceUser))));

            String priceFor = activity.getString(R.string.text_price_for)+" "+
                    qtyAdult+" "+activity.getString(R.string.text_adult)+" "+
                    activity.getString(R.string.text_and)+" "+qtyKids+" "+activity.getString(R.string.text_children_small);

            holder.tvPriceForUser.setText(priceFor);

        } else if (addonsItem.getPriceType().equals("item")) {

            int qtyItem = addonsItem.getQty();
            int number = addonsItem.getQtyOrder();
            int subTotal = addonsItem.getPrice();
            int priceItem = (number/qtyItem) * subTotal;

            holder.llSubUser.setVisibility(View.GONE);
            holder.llSubItem.setVisibility(View.VISIBLE);

            holder.tvAddonItemName.setText(addonsItem.getAddon());
            holder.tvTotalItem.setText(String.valueOf(addonsItem.getQtyOrder()));
            holder.tvPriceItem.setText(formatIdr(Double.parseDouble(String.valueOf(priceItem))));

            String priceFor = activity.getString(R.string.text_price_for)+" "+
                    addonsItem.getQtyOrder()+" "+activity.getString(R.string.text_item_of)+" "+addonsItem.getAddon();

            holder.tvPriceForItem.setText(priceFor);
        }
    }

    public List<AddonsTotalItem> getData() {
        return listAddonsItem;
    }

    @Override
    public int getItemCount() {
        return listAddonsItem == null ? 0 : listAddonsItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout llSubUser, llSubItem;
        private TextView tvTotalParticipant, tvAddonsUserName, tvPriceUser, tvPriceForUser;
        private TextView tvTotalItem, tvAddonItemName, tvPriceItem, tvPriceForItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            llSubUser = itemView.findViewById(R.id.llSubUser);
            tvTotalParticipant = itemView.findViewById(R.id.tvTotalParticipant);
            tvAddonsUserName = itemView.findViewById(R.id.tvAddonsUserName);
            tvPriceUser = itemView.findViewById(R.id.tvPriceUser);
            tvPriceForUser = itemView.findViewById(R.id.tvPriceForUser);

            llSubItem = itemView.findViewById(R.id.llSubItem);
            tvTotalItem = itemView.findViewById(R.id.tvTotalItem);
            tvAddonItemName = itemView.findViewById(R.id.tvAddonItemName);
            tvPriceItem = itemView.findViewById(R.id.tvPriceItem);
            tvPriceForItem = itemView.findViewById(R.id.tvPriceForItem);
        }
    }
}
