package com.aniqma.lokaventour.modul.activity.booking.promo.promolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.model.item.listpromo.PromoItem;
import com.aniqma.lokaventour.model.response.listpromo.PromoResponse;
import com.aniqma.lokaventour.network.services.promo.listpromo.GetListPromoService;
import com.aniqma.lokaventour.network.services.promo.listpromo.IViewGetListPromo;
import com.aniqma.lokaventour.utils.animation.ShimmerEffect;
import com.aniqma.lokaventour.utils.popup.LokavenDialog;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.text.ParseException;

import static com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingsummary.SummaryBookingActivity.RESULT_PROMO;

public class PromoListActivity extends AppCompatActivity implements View.OnClickListener, IViewGetListPromo, SwipeRefreshLayout.OnRefreshListener{

    private View component_appbar,
            component_content,
            compServiceFailed;

    private RecyclerView rvPromo;

    private TextView tvTitleAppbar,
            tvTitleDescription,
            tvLink,
            tvDescription,
            tvServiceFailed;

    private ImageView ivBack,
            imgServiceFailed;
    private LinearLayout llContent,
            llError;

    private SwipeRefreshLayout swipeRefresh;
    private ShimmerFrameLayout shimmerContainer;
    private ShimmerEffect shimmerEffect;

    private String from = "", tourId = "";
    private PromoResponse promo = new PromoResponse();
    private LokavenDialog lokavenDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_list);

        lokavenDialog = new LokavenDialog(this);
        if (getIntent() != null){
            tourId = getIntent().getStringExtra("tourId");
        }

        init();

        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setRefreshing(false);

        // Scheme colors for animation
        swipeRefresh.setColorSchemeColors(
                getResources().getColor(R.color.LOK_Teal),
                getResources().getColor(R.color.LOK_Blue),
                getResources().getColor(R.color.LOK_Gold),
                getResources().getColor(R.color.LOK_Red)
        );

        llContent.setVisibility(View.GONE);
        shimmerContainer.setVisibility(View.VISIBLE);

        shimmerEffect = new ShimmerEffect(this, shimmerContainer, llContent);
        shimmerEffect.startShimmer();

        IViewGetListPromo iViewGetListPromo = this;
        GetListPromoService getListPromoService = new GetListPromoService(iViewGetListPromo, this);
        getListPromoService.onGetPromo(tourId);

        ivBack.setOnClickListener(this);
        imgServiceFailed.setOnClickListener(this);
    }

    private void init() {
        component_appbar = findViewById(R.id.component_appbar);
        tvTitleAppbar = component_appbar.findViewById(R.id.tvTitle);
        ivBack = component_appbar.findViewById(R.id.ivBack);

        component_content = findViewById(R.id.component_content);
        tvTitleDescription = component_content.findViewById(R.id.tvTitle);
        tvLink = component_content.findViewById(R.id.tvLink);
        tvDescription = component_content.findViewById(R.id.tvDescription);
        swipeRefresh = findViewById(R.id.swipeRefresh);

        rvPromo = findViewById(R.id.rvPromo);
        shimmerContainer = findViewById(R.id.shimmerContainer);
        llContent = findViewById(R.id.llContent);

        llError = findViewById(R.id.llError);
        compServiceFailed = findViewById(R.id.compServiceFailed);
        imgServiceFailed = compServiceFailed.findViewById(R.id.imgServiceFailed);
        tvServiceFailed = compServiceFailed.findViewById(R.id.tvServiceFailed);
    }

    private void setContent() {
        tvLink.setVisibility(View.GONE);
        tvTitleAppbar.setText(getResources().getString(R.string.text_promo));
        tvTitleDescription.setText(String.valueOf(promo.getAvailable_promos()) + " " + getResources().getString(R.string.text_available_promos));
        tvDescription.setText(getResources().getString(R.string.text_the_following_are_promos_that_can_be_used_in_the_package));

        ListPromoAdapter adapter = new ListPromoAdapter(this, promo.getData());
        rvPromo.setAdapter(adapter);
        rvPromo.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvPromo.setLayoutManager(mLayoutManager);

        if (promo.getData().size() == 0){
            llError.setVisibility(View.VISIBLE);
            rvPromo.setVisibility(View.GONE);
        } else {
            llError.setVisibility(View.GONE);
            rvPromo.setVisibility(View.VISIBLE);
        }

        swipeRefresh.setRefreshing(false);
        llContent.setVisibility(View.VISIBLE);
        shimmerContainer.setVisibility(View.GONE);
        shimmerEffect.stopShimmer();

    }

    @Override
    public void onClick(View view) {
        if (view == ivBack){
            onBackPressed();
        } else if (view == imgServiceFailed){
            shimmerEffect.startShimmer();

            IViewGetListPromo iViewGetListPromo = this;
            GetListPromoService getListPromoService = new GetListPromoService(iViewGetListPromo, this);
            getListPromoService.onGetPromo(tourId);
        }
    }

    @Override
    public void onSuccessPromo(PromoResponse response) throws ParseException {
        promo = response;
        setContent();
    }

    @Override
    public void onErrorMsg(String title, String message) {
        tvServiceFailed.setText(message);
        llContent.setVisibility(View.VISIBLE);
        swipeRefresh.setRefreshing(false);
        shimmerContainer.setVisibility(View.GONE);
        shimmerEffect.stopShimmer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_PROMO:
                PromoItem promoData = (PromoItem) data.getSerializableExtra("data");

                Intent i = new Intent();
                i.putExtra("data", promoData);
                setResult(RESULT_PROMO, i);
                finish();
                break;

            case RESULT_CANCELED:
                break;
        }
    }

    @Override
    public void onRefresh() {
        IViewGetListPromo iViewGetListPromo = this;
        GetListPromoService getListPromoService = new GetListPromoService(iViewGetListPromo, this);
        getListPromoService.onGetPromo(tourId);
    }
}