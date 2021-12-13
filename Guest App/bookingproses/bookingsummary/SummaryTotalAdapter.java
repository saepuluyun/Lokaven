package com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingsummary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.SummaryTotalItem;

import java.util.List;

import static com.aniqma.lokaventour.helper.format.AlphabetHelper.firstCapitalize;
import static com.aniqma.lokaventour.helper.format.NumberHelper.formatIdr;

public class SummaryTotalAdapter extends RecyclerView.Adapter<SummaryTotalAdapter.MyViewHolder> {

    private List<SummaryTotalItem> mModelList;
    private Context context;

    public SummaryTotalAdapter(Context context, List<SummaryTotalItem> modelList) {
        mModelList = modelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_summary_total, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final SummaryTotalItem summaryTotalItem = mModelList.get(position);

        double price = Double.parseDouble(String.valueOf(summaryTotalItem.getPrice()));

        holder.count.setText(String.valueOf(summaryTotalItem.getCount())+" x ");
        holder.description.setText(firstCapitalize(summaryTotalItem.getDescription()));
        holder.price.setText(formatIdr(price));

        if (summaryTotalItem.getDescription().equals(context.getResources().getString(R.string.text_promo))){
            holder.total.setText(formatIdr(Double.parseDouble(String.valueOf(summaryTotalItem.getPrice() * -1))));
            holder.price.setVisibility(View.GONE);
            holder.count.setVisibility(View.GONE);
            holder.tvPersons.setVisibility(View.GONE);
            holder.tvSlash.setVisibility(View.GONE);
        } else {
            int total = summaryTotalItem.getCount() * summaryTotalItem.getPrice();
            holder.total.setText(formatIdr(Double.parseDouble(String.valueOf(total))));
        }

        if (summaryTotalItem.getTypeAddons() != null) {
            if (summaryTotalItem.getTypeAddons().equals("item")) {
                String textPerson = String.valueOf(summaryTotalItem.getQtyItemAddons()) +" "+ context.getString(R.string.text_item_small);
                holder.tvPersons.setText(textPerson);

                int priceItem = Integer.valueOf(summaryTotalItem.getPrice());
                int qtyItem = summaryTotalItem.getQtyItemAddons();
                int number = summaryTotalItem.getCount();
                int total = (number/qtyItem) * priceItem;

                holder.total.setText(formatIdr(Double.parseDouble(String.valueOf(total))));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView description;
        private TextView count;
        private TextView price;
        private TextView total;
        private TextView tvPersons;
        private TextView tvSlash;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            count = itemView.findViewById(R.id.count);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            total = itemView.findViewById(R.id.total);
            tvSlash = itemView.findViewById(R.id.tvSlash);
            tvPersons = itemView.findViewById(R.id.tvPersons);
        }
    }
}
