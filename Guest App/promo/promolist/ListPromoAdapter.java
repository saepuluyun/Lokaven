package com.aniqma.lokaventour.modul.activity.booking.promo.promolist;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.helper.date.DateHelper;
import com.aniqma.lokaventour.model.item.listpromo.PromoItem;
import com.aniqma.lokaventour.modul.activity.booking.promo.detailpromo.DetailPromoActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.aniqma.lokaventour.helper.date.DateHelper.compareDate;
import static com.aniqma.lokaventour.helper.date.DateHelper.compareHour;
import static com.aniqma.lokaventour.helper.date.DateHelper.getDateFormat5;
import static com.aniqma.lokaventour.helper.date.DateHelper.getDateNow;
import static com.aniqma.lokaventour.helper.format.NumberHelper.formatIdr;
import static com.aniqma.lokaventour.helper.url.BitmapHelper.validationPicasso;
import static com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingsummary.SummaryBookingActivity.RESULT_PROMO;

public class ListPromoAdapter extends RecyclerView.Adapter<ListPromoAdapter.MyViewHolder> {

    private List<PromoItem> mModelList;
    private Activity activity;

    public ListPromoAdapter(Activity activity, List<PromoItem> data) {
        this.activity = activity;
        this.mModelList = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_list_promo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        PromoItem promoItem = mModelList.get(position);

        if (!promoItem.getImage().isEmpty()){
            Picasso.get()
                    .load(validationPicasso(promoItem.getImage()))
                    .placeholder(R.drawable.thumbnail_default)
                    .centerCrop()
                    .fit()
                    .into(holder.ivPackage);
        }

        holder.tvNamePromo.setText(promoItem.getTitle_promo());

        if (promoItem.getDiscount() <= 1){
            holder.tvPromo.setText("-" + Math.round(promoItem.getDiscount())*100 + "%");
        } else {
            double price = Double.parseDouble(String.valueOf(promoItem.getDiscount()));
            holder.tvPromo.setText("-" + formatIdr(price));
        }

        try {
            holder.tvValid.setText(getDateFormat5(promoItem.getExpired()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, DetailPromoActivity.class);
                i.putExtra("data", promoItem);
                activity.startActivityForResult(i, 1);
            }
        });

        holder.tvUsePromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date currentDate = null;
                try {
                    currentDate = getDateNow();
                    java.text.DateFormat df = new SimpleDateFormat("HH:mm");
                    String timeHour = df.format(currentDate);
                    Date exp = DateHelper.isoStringToDate(promoItem.getExpired());
                    Date time_start = df.parse(promoItem.getTime_start());
                    Date time_end = df.parse(promoItem.getTime_end());
                    Date timeNow = df.parse(timeHour);

                    int countDate = Integer.valueOf(Long.toString(compareDate(currentDate, exp)));
                    int countStart = Integer.valueOf(Long.toString(compareHour(time_start, timeNow)));
                    int countEnd = Integer.valueOf(Long.toString(compareHour(time_end, timeNow)));

                    if (countDate < 0){
                        Toast.makeText(activity, activity.getResources().getString(R.string.text_promo_cannot_be_used), Toast.LENGTH_SHORT).show();
                    } else {
                        if (countStart > 0 && countEnd < 0){
                            Intent i = new Intent();
                            i.putExtra("data", promoItem);
                            activity.setResult(RESULT_PROMO, i);
                            activity.finish();
                        } else {
                            Toast.makeText(activity, activity.getResources().getString(R.string.text_promo_cannot_be_used), Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private ImageView ivPackage;

        private TextView tvPromo,
                tvFromBooking,
                tvValid,
                tvUsePromo,
                tvNamePromo;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            tvNamePromo = view.findViewById(R.id.tvNamePromo);
            ivPackage = view.findViewById(R.id.ivPackage);
            tvPromo = view.findViewById(R.id.tvPromo);
            tvFromBooking = view.findViewById(R.id.tvFromBooking);
            tvValid = view.findViewById(R.id.tvValid);
            tvUsePromo = view.findViewById(R.id.tvUsePromo);

        }
    }
}
