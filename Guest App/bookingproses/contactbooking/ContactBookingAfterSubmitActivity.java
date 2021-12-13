package com.aniqma.lokaventour.modul.activity.booking.bookingproses.contactbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.model.item.bookingService.bookingByContact.BookingByContactResponse;
import com.aniqma.lokaventour.model.item.bookingService.bookingByContact.TourRecomendationItem;
import com.aniqma.lokaventour.model.item.profileandsettings.profile.detailHost.DataDetailHostItem;
import com.aniqma.lokaventour.modul.activity.general.home.HomeActivity;
import com.aniqma.lokaventour.modul.activity.tourpackagedetails.detailopen.TourPackageDetailOpenActivity;
import com.aniqma.lokaventour.modul.activity.tourpackagedetails.detailprivate.TourPackageDetailPrivateActivity;
import com.aniqma.lokaventour.network.services.profile.detailhost.GetHostProfileService;
import com.aniqma.lokaventour.network.services.profile.detailhost.IViewDetailHostService;
import com.aniqma.lokaventour.utils.animation.ShimmerEffect;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class ContactBookingAfterSubmitActivity extends AppCompatActivity implements IViewDetailHostService {

    private RecyclerView.Adapter adapterList;
    private RecyclerView rvSimiliarTour;
    private RelativeLayout back_button;
    private LinearLayoutManager managerPackageTour;
    private String tour_id = "", typeTour = "", host_id = "";
    private List<TourRecomendationItem> recomendationItem = new ArrayList<>();
    private BookingByContactResponse response;
    private ShimmerFrameLayout shimmerContainer;
    private LinearLayout llContent;
    private TextView tvViewAll;

    private ShimmerEffect shimmerEffect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_booking_after_submit);
        init();

        try {
            response = (BookingByContactResponse) getIntent().getSerializableExtra("BookingByContactResponse");
            recomendationItem = response.getTourRecommendation();
            host_id = getIntent().getExtras().getString("hostId");
            typeTour = getIntent().getExtras().getString("type_tour");
        }catch (Exception e){

        }

        shimmerEffect = new ShimmerEffect(this, shimmerContainer, llContent);
        shimmerEffect.startShimmer();

        IViewDetailHostService iView = this;
        GetHostProfileService getHostProfileService = new GetHostProfileService(iView, this);
        getHostProfileService.getDetailHost(host_id);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ContactBookingAfterSubmitActivity.this, TourPackageDetailPrivateActivity.class);
                i.putExtra("tourId", response.getData().getTourId());
                i.putExtra("hostId", response.getData().getHostId());
                i.putExtra("typeTour", typeTour);
                setResult(RESULT_CANCELED, i);
                finish();
            }
        });

        tvViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ContactBookingAfterSubmitActivity.this, HomeActivity.class);
                i.putExtra("pageSlider", 2);
                i.putExtra("filter_include_active", true);
                i.putExtra("host_id", host_id);
                startActivity(i);
                finish();
            }
        });
    }

    private void init() {
        rvSimiliarTour = findViewById(R.id.rvSimiliarTour);
        back_button = findViewById(R.id.rlClose);
        tvViewAll = findViewById(R.id.tvViewAll);
        shimmerContainer = findViewById(R.id.shimmerContainer);
        llContent = findViewById(R.id.llContent);
    }

    @Override
    public void onSuccessGetHostDetail(DataDetailHostItem detailHost) {
        ListTourRecomendation(detailHost);
        shimmerEffect.stopShimmer();
    }

    private void ListTourRecomendation(DataDetailHostItem detailHost) {
        adapterList = new ContactBookingAfterSubmitAdapter(this,recomendationItem, detailHost);
        managerPackageTour = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvSimiliarTour.setLayoutManager(managerPackageTour);
        rvSimiliarTour.setAdapter(adapterList);
        rvSimiliarTour.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(8,8,8,8);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (typeTour.equalsIgnoreCase("open")){
            Intent i = new Intent(this, TourPackageDetailOpenActivity.class);
            i.putExtra("tourId",response.getData().getTourId());
            i.putExtra("hostId", response.getData().getHostId());
            i.putExtra("typeTour", typeTour);
            setResult(RESULT_CANCELED, i);
            finish();
        }else {
            Intent i = new Intent(this, TourPackageDetailPrivateActivity.class);
            i.putExtra("tourId",response.getData().getTourId());
            i.putExtra("hostId", response.getData().getHostId());
            i.putExtra("typeTour", typeTour);
            setResult(RESULT_CANCELED, i);
            finish();
        }
    }
}
