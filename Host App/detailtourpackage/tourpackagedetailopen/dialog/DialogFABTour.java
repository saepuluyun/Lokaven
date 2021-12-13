package com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailopen.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.google.android.material.appbar.AppBarLayout;

public class DialogFABTour extends Dialog {

    private Activity activity;
    private AppBarLayout appbar;
    private TextView
            tvTop,
            tvAvailableSchedule,
            tvAboutTheTour,
            tvItinerary,
            tvAddOns,
            tvTermsOfServices,
            tvReturnPolicy,
            tvOtherPolicies,
            tvReviews,
            tvDiscussions,

    tvDescriptionItinerary,
            tvMoreAbout,
            tvMoreTermOfSevice,
            descRefundPolicy,
            tvMoreReview,
            tvMoreDiscussion;

    private NestedScrollView nestedScrollView;

    private RecyclerView
            rvSchedule,
            rvAddons,
            rvOtherPolicies;

    public DialogFABTour(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.item_list_menu);
        init();

        tvTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appbar.setExpanded(true);
                nestedScrollView.smoothScrollTo(0, 0);
                dismiss();
            }
        });

        tvAvailableSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar.setExpanded(false);
                nestedScrollView.smoothScrollTo(0, 1300);
                dismiss();
            }
        });

        tvAboutTheTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar.setExpanded(false);
                nestedScrollView.smoothScrollTo(0, 1900);
                dismiss();
            }
        });

        tvItinerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar.setExpanded(false);
                nestedScrollView.smoothScrollTo(0, 2700);
                dismiss();
            }
        });

        tvAddOns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar.setExpanded(false);
                nestedScrollView.smoothScrollTo(0, 3200);
                dismiss();
            }
        });

        tvTermsOfServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar.setExpanded(false);
                nestedScrollView.smoothScrollTo(0, 3800);
                dismiss();
            }
        });

        tvReturnPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar.setExpanded(false);
                nestedScrollView.smoothScrollTo(0, 4100);
                dismiss();
            }
        });

        tvOtherPolicies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar.setExpanded(false);
                nestedScrollView.smoothScrollTo(0, 4400);
                dismiss();
            }
        });

        tvReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar.setExpanded(false);
                nestedScrollView.smoothScrollTo(0, 5600);
                dismiss();
            }
        });

        tvDiscussions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appbar.setExpanded(false);
                nestedScrollView.smoothScrollTo(0, 6800);
                dismiss();
            }
        });

    }

    private void init() {
        // From Dialog
        tvTop = findViewById(R.id.tvTop);
        tvAvailableSchedule = findViewById(R.id.tvAvailableSchedule);
        tvAboutTheTour = findViewById(R.id.tvAboutTheTour);
        tvItinerary = findViewById(R.id.tvItinerary);
        tvAddOns = findViewById(R.id.tvAddOns);
        tvTermsOfServices = findViewById(R.id.tvTermsOfServices);
        tvReturnPolicy = findViewById(R.id.tvReturnPolicy);
        tvOtherPolicies = findViewById(R.id.tvOtherPolicies);
        tvReviews = findViewById(R.id.tvReviews);
        tvDiscussions = findViewById(R.id.tvDiscussions);

        // From Activity
        nestedScrollView = activity.findViewById(R.id.nestedScrollView);
        appbar = activity.findViewById(R.id.appbar);
        rvSchedule = activity.findViewById(R.id.rvSchedule);
        tvMoreAbout = activity.findViewById(R.id.tv_more_about);
        tvDescriptionItinerary = activity.findViewById(R.id.tvDescription);
        rvAddons = activity.findViewById(R.id.rvAddons);
        tvMoreTermOfSevice = activity.findViewById(R.id.tv_more_term_of_sevice);
        descRefundPolicy = activity.findViewById(R.id.btn_more_refund_policy);
        rvOtherPolicies = activity.findViewById(R.id.rvCustomPolicy);
        tvMoreReview = activity.findViewById(R.id.tv_more_review);
        tvMoreDiscussion = activity.findViewById(R.id.tv_more_discussion);

    }
}
