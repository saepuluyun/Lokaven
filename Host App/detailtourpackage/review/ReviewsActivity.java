package com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.review;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.model.item.rateandreview.DataRatesItem;
import com.aniqma.lokaventour.host.model.item.rateandreview.DataReviewItem;
import com.aniqma.lokaventour.host.model.response.review.RateReviewDetailTourResponse;
import com.aniqma.lokaventour.host.network.services.reviews.rateandreviewdetail.RateAndReviewDetailServices;
import com.aniqma.lokaventour.host.network.services.reviews.rateandreviewdetail.iViewRateAndReviewDetail;
import com.aniqma.lokaventour.host.utils.animation.LoadingPage;
import com.aniqma.lokaventour.host.utils.dialog.LokavenDialog;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity implements iViewRateAndReviewDetail, View.OnClickListener {

    private TextView tvTitle;
    private ImageView ivBack;

    private RecyclerView
            mRecyclerViewRatingBar,
            rvItem;

    private LinearLayout llContent;

    private TextView tvRatingValue, tvReview;

    private ReviewsAdapter mAdapterReviews;
    private RatingBarAdapter mAdapterRatingBar;
    private LinearLayoutManager layoutManagerReview,
            lmRatingBar;

    private LoadingPage loadingPage;
    private LokavenDialog lokavenDialog;

    private String tourId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        init();
        tvTitle.setText(getString(R.string.text_reviews));

        loadingPage = new LoadingPage(this);
        lokavenDialog = new LokavenDialog(this);

        if (getIntent() != null) {
            tourId = getIntent().getStringExtra("tour_id");
        }

        loadingPage.start();
        llContent.setVisibility(View.GONE);
        iViewRateAndReviewDetail iViewRateAndReviewDetail = this;
        RateAndReviewDetailServices rateAndReviewDetailServices = new RateAndReviewDetailServices(this, iViewRateAndReviewDetail);
        rateAndReviewDetailServices.onGetRateReviewDetailList(tourId);

        ivBack.setOnClickListener(this);
    }

    private void init() {
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);

        llContent = findViewById(R.id.llContent);
        tvRatingValue = findViewById(R.id.tvRatingValue);
        tvReview = findViewById(R.id.tvReview);
        rvItem = findViewById(R.id.recycleview_reviews);
        mRecyclerViewRatingBar = findViewById(R.id.rvRatingBar);
    }

    @Override
    public void onSuccessGetReviewDetail(RateReviewDetailTourResponse response) {
        ArrayList<DataReviewItem> data = response.getData();
        ArrayList<DataRatesItem> data_rates = response.getData_rates();
        int total_reviewer = response.getTotalReviewer();
        float avg_rating = response.getAvgRating();

        tvRatingValue.setText(String.valueOf(avg_rating));
        tvReview.setText(String.valueOf(total_reviewer));

        if (data_rates.size() > 0) {
            lmRatingBar = new LinearLayoutManager(this);
            mAdapterRatingBar = new RatingBarAdapter(this, data_rates);
            mRecyclerViewRatingBar.setAdapter(mAdapterRatingBar);
            mRecyclerViewRatingBar.setLayoutManager(lmRatingBar);
        } else {
            mRecyclerViewRatingBar.setVisibility(View.GONE);
        }

        if (data.size() > 0) {
            layoutManagerReview = new LinearLayoutManager(this);
            mAdapterReviews = new ReviewsAdapter(this, data);
            rvItem.setAdapter(mAdapterReviews);
            rvItem.setLayoutManager(layoutManagerReview);
        } else {
            rvItem.setVisibility(View.GONE);
        }

        loadingPage.stop();
        llContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void onErrorGetReviewDetail(String title, String message, int responseCode) {
        loadingPage.stop();
        llContent.setVisibility(View.GONE);
        lokavenDialog.dialogError(title, message);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == ivBack){
            onBackPressed();
        }
    }
}
