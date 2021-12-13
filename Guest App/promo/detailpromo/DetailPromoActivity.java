package com.aniqma.lokaventour.modul.activity.booking.promo.detailpromo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.helper.date.DateHelper;
import com.aniqma.lokaventour.model.item.listpromo.PromoItem;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.aniqma.lokaventour.helper.date.DateHelper.compareDate;
import static com.aniqma.lokaventour.helper.date.DateHelper.compareHour;
import static com.aniqma.lokaventour.helper.date.DateHelper.getDateFormat5;
import static com.aniqma.lokaventour.helper.date.DateHelper.getDateNow;
import static com.aniqma.lokaventour.helper.format.NumberHelper.formatIdr;
import static com.aniqma.lokaventour.helper.url.BitmapHelper.validationPicasso;
import static com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingsummary.SummaryBookingActivity.RESULT_PROMO;

public class DetailPromoActivity extends AppCompatActivity implements View.OnClickListener {

    private View component_price,
            component_button_submit;
    private ImageView ivImageTitle,
            ivBack;

    private TextView tvTitle,
            tvExpired,
            tvDescription,
            tvMinBooking,
            tvMinUsage,
            tvUsageTime;

    private Button component_btn_big,
            btnBigSubmit;
    private PromoItem promo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_promo);

        if (getIntent() != null){
            promo = (PromoItem) getIntent().getSerializableExtra("data");
        }

        init();
        setContent();

        btnBigSubmit.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    private void setContent() {
        if (!promo.getImage().isEmpty()){
            Picasso.get()
                    .load(validationPicasso(promo.getImage()))
                    .placeholder(R.drawable.thumbnail_default)
                    .centerCrop()
                    .fit()
                    .into(ivImageTitle);
        }

        tvTitle.setText(promo.getTitle_promo());

        try {
            tvExpired.setText(getDateFormat5(promo.getExpired()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvDescription.setText(promo.getDescription());
        tvMinBooking.setText(String.valueOf(promo.getMin_booking()));
        tvMinUsage.setText(promo.getPromotion_used() + " " + getResources().getString(R.string.text_time_purchase));
        tvUsageTime.setText(getResources().getString(R.string.text_from) + " " + promo.getTime_start() + " " + getResources().getString(R.string.text_to) + " " + promo.getTime_end());

        if (promo.getDiscount() <= 1){
            component_btn_big.setText("-" + Math.round(promo.getDiscount())*100 + "%");
        } else {
            double price = Double.parseDouble(String.valueOf(promo.getDiscount()));
            component_btn_big.setText("-" + formatIdr(price));
        }

        btnBigSubmit.setText(getResources().getString(R.string.text_use_promo));
    }

    private void init() {
        component_price = findViewById(R.id.component_price);
        component_btn_big = component_price.findViewById(R.id.component_btn_big);

        component_button_submit = findViewById(R.id.component_button_submit);
        btnBigSubmit = component_button_submit.findViewById(R.id.btnBig);


        ivImageTitle = findViewById(R.id.ivImageTitle);
        ivBack = findViewById(R.id.ivBack);

        tvTitle = findViewById(R.id.tvTitle);
        tvExpired = findViewById(R.id.tvExpired);
        tvDescription = findViewById(R.id.tvDescription);

        tvMinBooking = findViewById(R.id.tvMinBooking);
        tvMinUsage = findViewById(R.id.tvMinUsage);
        tvUsageTime = findViewById(R.id.tvUsageTime);

    }

    @Override
    public void onClick(View view) {
        if (view == ivBack){
            onBackPressed();
        } else if (view == btnBigSubmit){
            Date currentDate = null;
            try {
                currentDate = getDateNow();
                java.text.DateFormat df = new SimpleDateFormat("HH:mm");
                String timeHour = df.format(currentDate);
                Date exp = DateHelper.isoStringToDate(promo.getExpired());
                Date time_start = df.parse(promo.getTime_start());
                Date time_end = df.parse(promo.getTime_end());
                Date timeNow = df.parse(timeHour);

                int countDate = Integer.valueOf(Long.toString(compareDate(currentDate, exp)));
                int countStart = Integer.valueOf(Long.toString(compareHour(time_start, timeNow)));
                int countEnd = Integer.valueOf(Long.toString(compareHour(time_end, timeNow)));

                if (countDate < 0){
                    Toast.makeText(this, getResources().getString(R.string.text_promo_cannot_be_used), Toast.LENGTH_SHORT).show();
                } else {
                    if (countStart > 0 && countEnd < 0){
                        Intent i = new Intent();
                        i.putExtra("data", promo);
                        setResult(RESULT_PROMO, i);
                        finish();
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.text_promo_cannot_be_used), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}