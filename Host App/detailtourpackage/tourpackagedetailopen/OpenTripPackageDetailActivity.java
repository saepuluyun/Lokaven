package com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailopen;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.helper.date.DateHelper;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.review.ReviewsActivity;
import com.aniqma.lokaventour.host.utils.animation.ShimmerEffect;
import com.aniqma.lokaventour.host.utils.dialog.LokavenDialog;
import com.aniqma.lokaventour.host.utils.animation.LoadingPage;
import com.aniqma.lokaventour.host.utils.misc.successsnackbars.CheckSnackbarsUtils;
import com.aniqma.lokaventour.host.model.item.inbox.discussion.DiscussionReplyItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.AddonsItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.CustomPolicyItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.MediasItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.SchedulesItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.TourPackageItem;
import com.aniqma.lokaventour.host.model.response.review.ReviewsResponse;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.editopentrippackage.EditOpenTripPackageActivity;
import com.aniqma.lokaventour.host.modul.activity.inbox.discussion.alldiscussion.GetAllDiscussionActivity;
import com.aniqma.lokaventour.host.modul.activity.general.home.HomeActivity;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.schedulemanagement.opentourschedule.OpenTourScheduleActivity;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailopen.adapter.AddonsAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailopen.adapter.CategoryAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailopen.adapter.CustomPolicyAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailopen.adapter.ListScheduleOpenTripDetailAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailopen.adapter.TourPackageImageAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailopen.adapter.DiscussionAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailopen.adapter.TagsAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailopen.dialog.DialogRefundPolicy;
import com.aniqma.lokaventour.host.network.services.authentication.checktoken.RefreshTokenServices;
import com.aniqma.lokaventour.host.network.services.discussionservice.viewer.getnewestdiscussionfromtourid.GetNewestDiscussionFromTourIdService;
import com.aniqma.lokaventour.host.network.services.discussionservice.viewer.getnewestdiscussionfromtourid.IViewGetNewestDiscussionFromTourIdService;
import com.aniqma.lokaventour.host.network.services.authentication.checktoken.IViewCheckToken;

import com.aniqma.lokaventour.host.network.services.reviews.rateandreviewnewest.GetNewestReviewService;
import com.aniqma.lokaventour.host.network.services.reviews.rateandreviewnewest.IViewGetNewestReview;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.deletetourpaclage.DeleteTourService;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.deletetourpaclage.iViewDeleteTour;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.detailtourpackage.DetailTourService;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.detailtourpackage.IViewDetailTour;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.aniqma.lokaventour.host.modul.activity.general.home.HomeActivity.RESULT_DELETE;

public class OpenTripPackageDetailActivity extends AppCompatActivity implements View.OnClickListener,
        IViewDetailTour, IViewGetNewestDiscussionFromTourIdService, iViewDeleteTour, IViewCheckToken, IViewGetNewestReview {

    private TabLayout tabIndicator;
    private ViewPager screen_viewpager;
    private Toolbar toolbar;
    private String tour_id = "";
    private DetailTourService presenter;
    private IViewDetailTour iView;
    private TourPackageItem tourDetail;
    private AppBarLayout appbar;

    private FloatingActionButton floating_menu;
    private RelativeLayout rlBack, rlMore;
    private ImageView iconDuration, ivIconSchedule, ivIconAbout, ivIconItinerary, iviconAddons, ivProfileReview;
    private TextView tvTitleTour, tvPrice, tvTypeTour, textDuration, tvDuration, textLocation, tvLocation,
            tvTitleSchedule, tvDescriptionSchedule,
            tvTitleAbout, tvDescriptionAbout, tvStatusSchedules,
            tvTitleItinerary, tvDescriptionItinerary,
            tvTitleAddons, tvDescriptionAddons,
            tvTitleTermOfSercive, tvDescriptionTermOfService,
            tvTitleInsurance, tvSubtitleInsurance, tvDescriptionInsurance,
            tvTitleRefundPolicy, tvDescriptionRefundPolicy,
            tvTitleOtherPolicy, tvDescriptionOtherPolicy,
            tv_age_restraction, tv_additional_cash, tvPriceChild,
            tvViewSchedules, tvManageSchedules,
            tv_more_about, tv_more_itinerary, tv_more_term_of_sevice, tv_more_insurance, tv_more_refund_policy, tv_more_review, tv_more_discussion,
            tv_reviewx,
            tvRatingValue,
            tvNumberRating,
            tvName,
            tvDate,
            tvMsgReview;
    private Button btnEditTour;
    private RecyclerView rvTags, rvSchedule, rvAddons, rvCustomPolicy, rvCategory, rvDiscussion;
    private View compAppbar, compBtn, compTypeTour, compPrice,
            CompDuration, CompLocation,
            compSchedule, compAbout, compItinerary, compAddons, ComponentInsurance,
            compTos, compRefundPolicy, compOtherPolicy, ComponentReviews;

    private ShimmerFrameLayout shimmerContainer;

    private boolean expandAbout=true, expandItinerary=true, expandTermOfService=true, expandInsurance=false, expandRefundPolicy=true;

    private ShimmerEffect shimmerEffect;
    private LoadingPage loadingPage;
    private RelativeLayout container;
    private LinearLayout llContent, llError, llAddons, llAgeRetriction, llAditionalCost, llOtherPolicy, llKidsPrice;
    private NestedScrollView nestedScrollView;
    private RelativeLayout rlButton;
    private LokavenDialog lokavenDialog;

    public ArrayList<String> filter_category;
    public ArrayList<String> filter_tags;
    public ArrayList<String> filter_type_trip;

    public String keyword;
    public String filter_place;
    public Float filter_rating;
    public boolean filter_featured;
    public String sort_price;
    public String sort_rating;
    public String sort_date;
    public int filter_price_low;
    public int filter_price_max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_trip_package_detail);
        Initialization();

        loadingPage = new LoadingPage(this);
        shimmerEffect = new ShimmerEffect(this, shimmerContainer, llContent);
        lokavenDialog = new LokavenDialog(this);

        if (getIntent() != null){
            if (getIntent().getExtras().getString("tour_id") != null){
                tour_id = getIntent().getExtras().getString("tour_id");
                keyword = getIntent().getExtras().getString("keyword");
                filter_price_low = getIntent().getExtras().getInt("filter_price_low");
                filter_price_max = getIntent().getExtras().getInt("filter_price_max");
                filter_place = getIntent().getExtras().getString("filter_place");
                filter_category = getIntent().getExtras().getStringArrayList("filter_category");
                filter_type_trip = getIntent().getExtras().getStringArrayList("filter_trip_type");
                filter_tags = getIntent().getExtras().getStringArrayList("filter_tags");
                filter_rating = getIntent().getExtras().getFloat("filter_rating");
                filter_featured = getIntent().getExtras().getBoolean("filter_featured");
                sort_price = getIntent().getExtras().getString("sort_price");
                sort_date = getIntent().getExtras().getString("sort_date");
                sort_rating = getIntent().getExtras().getString("sort_rating");

                container.setVisibility(View.GONE);
                nestedScrollView.setVisibility(View.GONE);
                rlButton.setVisibility(View.GONE);
                llError.setVisibility(View.GONE);

                shimmerEffect.startShimmer();
                iView = this;
                presenter = new DetailTourService(this, iView);
                presenter.onGetDataDetailTour(tour_id);

                CheckSnackbarsUtils checkSnackbarsUtils = new CheckSnackbarsUtils(this);
                checkSnackbarsUtils.onCheck(getIntent());
            } else {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("pageHome", 4);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        }

        setSupportActionBar(toolbar);
        rlBack.setOnClickListener(this);
        rlMore.setOnClickListener(this);
        tvViewSchedules.setOnClickListener(this);
        tvManageSchedules.setOnClickListener(this);
        tv_more_about.setOnClickListener(this);
        tv_more_term_of_sevice.setOnClickListener(this);
        tv_more_insurance.setOnClickListener(this);
        tv_more_refund_policy.setOnClickListener(this);
        btnEditTour.setOnClickListener(this);
        tv_more_review.setOnClickListener(this);
        tv_more_discussion.setOnClickListener(this);
    }

    private void Initialization() {
        shimmerContainer = findViewById(R.id.shimmerContainer);
        llContent = findViewById(R.id.llContent);

        nestedScrollView = findViewById(R.id.nestedScrollView);
        llError = findViewById(R.id.llError);
        rlButton = findViewById(R.id.rlButton);
        tabIndicator = findViewById(R.id.tab_indicator_tour_package);
        screen_viewpager = findViewById(R.id.screen_viewpager_tour_package);
        rvDiscussion= findViewById(R.id.rvDiscussion);

        compAppbar = findViewById(R.id.compAppbar);
        rlBack = compAppbar.findViewById(R.id.rlBack);
        rlMore = compAppbar.findViewById(R.id.rlMore);
        toolbar = compAppbar.findViewById(R.id.toolbar);
        tvTitleTour = findViewById(R.id.tvTitleTour);

        compTypeTour = findViewById(R.id.compTypeTour);
        tvTypeTour = compTypeTour.findViewById(R.id.tvTitle);
        tvTypeTour.setBackground(getResources().getDrawable(R.drawable.custom_button_blue));
        tvTypeTour.setText(R.string.text_open_tour);

        compPrice = findViewById(R.id.compPrice);
        tvPrice = compPrice.findViewById(R.id.tvPrice);
        tvPriceChild = compPrice.findViewById(R.id.tvPriceChild);
        llKidsPrice = compPrice.findViewById(R.id.llKidsPrice);

        compBtn = findViewById(R.id.compBtn);
        btnEditTour = compBtn.findViewById(R.id.btnBig);
        btnEditTour.setText(getString(R.string.text_edit_tour));
        tvViewSchedules = compBtn.findViewById(R.id.tvTextButton);
        tvViewSchedules.setText(getString(R.string.text_view_schedules));

        CompDuration = findViewById(R.id.CompDuration);
        iconDuration = CompDuration.findViewById(R.id.ivIconLocation);
        iconDuration.setImageResource(R.drawable.icon_18dp_duration_blue);
        textDuration = CompDuration.findViewById(R.id.tvTitle);
        textDuration.setText(getString(R.string.text_duration));
        tvDuration = CompDuration.findViewById(R.id.tvDescription);
        tvDuration.setText("-");

        CompLocation = findViewById(R.id.CompLocation);
        textLocation = CompLocation.findViewById(R.id.tvTitle);
        textLocation.setText(getString(R.string.text_location));
        tvLocation = CompLocation.findViewById(R.id.tvDescription);

        rvAddons = findViewById(R.id.rvAddons);
        llAddons = findViewById(R.id.llAddons);
        compAddons = findViewById(R.id.compAddons);
        tvTitleAddons = compAddons.findViewById(R.id.tvTitle);
        tvTitleAddons.setText(getString(R.string.text_addons));
        tvDescriptionAddons = compAddons.findViewById(R.id.tvDescription);
        tvDescriptionAddons.setVisibility(View.GONE);
        iviconAddons = compAddons.findViewById(R.id.ivTitle);
        iviconAddons.setImageResource(R.drawable.icon_24dp_addons_blue);

        compItinerary = findViewById(R.id.compItinerary);
        tvTitleItinerary = compItinerary.findViewById(R.id.tvTitle);
        tvTitleItinerary.setText(getString(R.string.text_itinerary));
        tvDescriptionItinerary= compItinerary.findViewById(R.id.tvDescription);
        ivIconItinerary = compItinerary.findViewById(R.id.ivTitle);
        ivIconItinerary.setImageResource(R.drawable.icon_24dp_itinerary_blue);

        compAbout = findViewById(R.id.compAbout);
        tvTitleAbout = compAbout.findViewById(R.id.tvTitle);
        tvTitleAbout.setText(getString(R.string.text_about_the_tour));
        tvDescriptionAbout = compAbout.findViewById(R.id.tvDescription);
        ivIconAbout = compAbout.findViewById(R.id.ivTitle);
        ivIconAbout.setImageResource(R.drawable.icon_24dp_about_blue);

        rvSchedule = findViewById(R.id.rvSchedule);
        compSchedule = findViewById(R.id.compSchedule);
        tvTitleSchedule = compSchedule.findViewById(R.id.tvTitle);
        tvTitleSchedule.setText(getString(R.string.text_active_schedules));
        tvDescriptionSchedule= compSchedule.findViewById(R.id.tvDescription);
        tvDescriptionSchedule.setVisibility(View.GONE);
        ivIconSchedule = compSchedule.findViewById(R.id.ivTitle);
        tvStatusSchedules = findViewById(R.id.tvStatusSchedules);
        tvManageSchedules = findViewById(R.id.tvManageSchedules);

        llOtherPolicy = findViewById(R.id.llOtherPolicy);
        llAgeRetriction = findViewById(R.id.llAgeRetriction);
        llAditionalCost = findViewById(R.id.llAditionalCost);
        tv_age_restraction = findViewById(R.id.tv_age_restraction);
        tv_additional_cash = findViewById(R.id.tv_additional_cash);
        rvCategory = findViewById(R.id.rvCategory);

        rvTags = findViewById(R.id.rvTags);
        rvCustomPolicy = findViewById(R.id.rvCustomPolicy);
        compOtherPolicy = findViewById(R.id.compOtherPolicy);
        tvTitleOtherPolicy = compOtherPolicy.findViewById(R.id.tvTitle);
        tvTitleOtherPolicy.setText(getString(R.string.text_other_policies));
        tvDescriptionOtherPolicy = compOtherPolicy.findViewById(R.id.tvDescription);
        tvDescriptionOtherPolicy.setVisibility(View.GONE);

        compRefundPolicy = findViewById(R.id.compRefundPolicy);
        tvTitleRefundPolicy = compRefundPolicy.findViewById(R.id.tvTitle);
        tvTitleRefundPolicy.setText(getString(R.string.text_refund_policy));
        tvDescriptionRefundPolicy = compRefundPolicy.findViewById(R.id.tvDescription);

        compTos = findViewById(R.id.compTos);
        tvTitleTermOfSercive = compTos.findViewById(R.id.tvTitle);
        tvTitleTermOfSercive.setText(getString(R.string.text_term_of_service));
        tvDescriptionTermOfService = compTos.findViewById(R.id.tvDescription);

        ComponentInsurance = findViewById(R.id.ComponentInsurance);
        tvTitleInsurance = ComponentInsurance.findViewById(R.id.tvTitle);
        tvSubtitleInsurance = ComponentInsurance.findViewById(R.id.tvSubTitle);
        tvDescriptionInsurance = ComponentInsurance.findViewById(R.id.tvDescription);
        tv_more_insurance = findViewById(R.id.tv_more_insurance);

        floating_menu = findViewById(R.id.floating_menu);

        tv_more_about = findViewById(R.id.tv_more_about);
        tv_more_itinerary = findViewById(R.id.tv_more_itinerary);
        tv_more_term_of_sevice = findViewById(R.id.tv_more_term_of_sevice);
        tv_more_refund_policy = findViewById(R.id.btn_more_refund_policy);
        tv_more_review = findViewById(R.id.tv_more_review);
        tv_more_discussion = findViewById(R.id.tv_more_discussion);
        container = findViewById(R.id.container);

        tv_reviewx = findViewById(R.id.tv_reviewx);
        tvRatingValue = findViewById(R.id.tvRatingValue);
        tvNumberRating = findViewById(R.id.tvNumberRating);

        ComponentReviews = findViewById(R.id.ComponentReviews);
        ivProfileReview = ComponentReviews.findViewById(R.id.ivProfileReview);
        tvName = ComponentReviews.findViewById(R.id.tvName);
        tvDate = ComponentReviews.findViewById(R.id.tvDateReview);
        tvMsgReview = ComponentReviews.findViewById(R.id.tvMsgReview);

        tv_more_review = findViewById(R.id.tv_more_review);
        appbar = findViewById(R.id.appbar);
    }

    private void onInitView(TourPackageItem tourPackageItem){
        tvTitleTour.setText(tourPackageItem.getTitle());
        tvLocation.setText(tourPackageItem.getLocation());

        if (tourPackageItem.getSchedules().size() != 0){
            String duration = tourPackageItem.getSchedules().get(0).getDurations();

            tvDuration.setText(duration+" "+getString(R.string.text_day));
            if (duration.equals("0")) {
                tvDuration.setText("1 "+getString(R.string.text_day));
            }
        }

        if (!tourPackageItem.getRate().equals("")){
            tvNumberRating.setText(tourPackageItem.getRate());
            tvRatingValue.setText(tourPackageItem.getRate());
        }

        if (!tourPackageItem.getReview().equals("")) {
            tv_reviewx.setText(String.valueOf(tourPackageItem.getReview()));
        }

        NumberFormat formatter = new DecimalFormat("#,###");
        double price = Double.parseDouble(String.valueOf(tourPackageItem.getPrices().get(0).getPrice()));
        double priceChild = Double.parseDouble(String.valueOf(tourPackageItem.getPrices().get(0).getKidPrice()));

        if (priceChild <= 0){
            llKidsPrice.setVisibility(View.GONE);
        } else {
            llKidsPrice.setVisibility(View.VISIBLE);
        }

        tvPrice.setText(String.valueOf(formatter.format(price)));
        tvPriceChild.setText(String.valueOf(formatter.format(priceChild)));

        tvDescriptionAbout.setText(tourPackageItem.getDescription());
        tvDescriptionAbout.post(new Runnable() {
            @Override
            public void run() {
                int lengCount = tvDescriptionAbout.getText().length();
                String[] lines = tvDescriptionAbout.getText().toString().split("\r\n|\r|\n");
                int lineCount =   lines.length;
                if (lineCount < 5) {
                    if (lengCount < 200){
                        tv_more_about.setVisibility(View.GONE);
                    } else {
                        tvDescriptionAbout.setMaxLines(5);
                        tv_more_about.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvDescriptionAbout.setMaxLines(5);
                    tv_more_about.setVisibility(View.VISIBLE);
                }
            }
        });

        tvDescriptionItinerary.setText(tourPackageItem.getItinerary());
        tvDescriptionItinerary.post(new Runnable() {
            @Override
            public void run() {
                int lengCount = tvDescriptionItinerary.getText().length();
                String[] lines = tvDescriptionItinerary.getText().toString().split("\r\n|\r|\n");
                int lineCount =   lines.length;
                if (lineCount < 5) {
                    if (lengCount < 200){
                        tv_more_itinerary.setVisibility(View.GONE);
                    } else {
                        tvDescriptionItinerary.setMaxLines(5);
                        tv_more_itinerary.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvDescriptionItinerary.setMaxLines(5);
                    tv_more_itinerary.setVisibility(View.VISIBLE);
                }
            }
        });

        tvDescriptionTermOfService.setText(tourPackageItem.getTermOfService());
        tvDescriptionTermOfService.post(new Runnable() {
            @Override
            public void run() {
                int lengCount = tvDescriptionTermOfService.getText().length();
                String[] lines = tvDescriptionTermOfService.getText().toString().split("\r\n|\r|\n");
                int lineCount =   lines.length;
                if (lineCount < 5) {
                    if (lengCount < 200){
                        tv_more_term_of_sevice.setVisibility(View.GONE);
                    } else {
                        tvDescriptionTermOfService.setMaxLines(5);
                        tv_more_term_of_sevice.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvDescriptionTermOfService.setMaxLines(5);
                    tv_more_term_of_sevice.setVisibility(View.VISIBLE);
                }
            }
        });

        tvTitleInsurance.setText(R.string.text_insurance);
        tvSubtitleInsurance.setText(tourPackageItem.getInsuranceItem().getTitle());
        tvDescriptionInsurance.setText(tourPackageItem.getInsuranceItem().getDescription());
        tvDescriptionInsurance.post(new Runnable() {
            @Override
            public void run() {
                int lengCount = tvDescriptionInsurance.getText().length();
                String[] lines = tvDescriptionInsurance.getText().toString().split("\r\n|\r|\n");
                int lineCount =   lines.length;
                if (lineCount < 5) {
                    if (lengCount < 200){
                        tv_more_insurance.setVisibility(View.GONE);
                    } else {
                        tvDescriptionInsurance.setMaxLines(5);
                        tv_more_insurance.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvDescriptionInsurance.setMaxLines(5);
                    tv_more_insurance.setVisibility(View.VISIBLE);
                }
            }
        });

        tvDescriptionRefundPolicy.setText(getString(R.string.text_refund_policy_desc_true));
        tvDescriptionRefundPolicy.post(new Runnable() {
            @Override
            public void run() {
                int lengCount = tvDescriptionRefundPolicy.getText().length();
                String[] lines = tvDescriptionRefundPolicy.getText().toString().split("\r\n|\r|\n");
                int lineCount =   lines.length;
                if (lineCount < 5) {
                    if (lengCount < 200){
                        tv_more_refund_policy.setVisibility(View.GONE);
                    } else {
                        tvDescriptionRefundPolicy.setMaxLines(5);
                        tv_more_refund_policy.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvDescriptionRefundPolicy.setMaxLines(5);
                    tv_more_refund_policy.setVisibility(View.VISIBLE);
                }
            }
        });

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    floating_menu.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if(isShow) {
                    floating_menu.setVisibility(View.INVISIBLE);
                    isShow = false;
                }
            }
        });

        SetSlideImage(tourPackageItem.getMedias());
        SetListCategory(tourPackageItem.getCategories());
        SetListTags(tourPackageItem.getTags());
        SetListSchedules(tourPackageItem.getSchedules());
        SetAddons(tourPackageItem.getAddons());
        SetListOtherPolicies(tourPackageItem, tourPackageItem.getCustomePolicies());
    }

    private void SetListSchedules(List<SchedulesItem> schedules) {
        int total=0;
        int idle=0;
        int ongoing=0;
        int ended=0;

        for (int i=0; i<schedules.size(); i++) {
            total = schedules.size();
            String status = schedules.get(i).getTourStatus();

            if (status.equals("Idle")) {
                idle++;
            }
            if (status.equals("ongoing")) {
                ongoing++;
            }
            if (status.equals("ended")) {
                ended++;
            }
        }

        int booked=0;
        int active=0;
        for (int i=0; i<schedules.size(); i++) {
            boolean isBooked = schedules.get(i).isBooked();
            boolean isActive = schedules.get(i).isActive();

            if (isBooked) {
                booked++;
            }

            if (isActive) {
                active++;
            }
        }

        String scheduleInfo = getString(R.string.text_this_tour_has) +
                total + getString(R.string.text_schedules) +
                ended + getString(R.string.text_already_finished) +
                ongoing + getString(R.string.text_ongoing_and) +
                idle + getString(R.string.text_is_not_started_available);

        tvStatusSchedules.setText(scheduleInfo);

        if (booked > 0 || active > 0) {
            btnEditTour.setVisibility(View.GONE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tvViewSchedules.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }

        RecyclerView.Adapter mAdapterNewOrder = new ListScheduleOpenTripDetailAdapter(this, schedules);
        rvSchedule.setHasFixedSize(true);
        mAdapterNewOrder.notifyDataSetChanged();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvSchedule.setLayoutManager(layoutManager);
        rvSchedule.setAdapter(mAdapterNewOrder);
    }

    private void SetAddons(List<AddonsItem> addons) {
        int i=0;
        while (i < addons.size()) {
            String name = addons.get(i).getAddon();
            String price = addons.get(i).getPrice();
            if (price.equals("") || price.equals("0") && name.equals("")) {
                addons.remove(i);
            } else {
                i++;
            }
        }

        if (addons.size() > 0) {
            rvAddons.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            AddonsAdapter addonsAdapter = new AddonsAdapter(this, addons);
            addonsAdapter.notifyDataSetChanged();
            rvAddons.setLayoutManager(mLayoutManager);
            rvAddons.setAdapter(addonsAdapter);
        } else {
            llAddons.setVisibility(View.GONE);
        }
    }

    private void SetListTags(String[] tags) {
        TagsAdapter tagsAdapter = new TagsAdapter(tags, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rvTags.setLayoutManager(layoutManager);
        rvTags.setAdapter(tagsAdapter);
        rvTags.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(2,2,2,2);
            }
        });
    }

    private void SetListCategory(String[] categories) {
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rvCategory.setLayoutManager(layoutManager);
        rvCategory.setAdapter(categoryAdapter);
        rvCategory.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(2,2,2,2);
            }
        });
    }

    private void SetListOtherPolicies(TourPackageItem tourPackage, List<CustomPolicyItem> customPolicies) {
        String ageRestriction = tourPackage.getAgeRestriction();
        String costFereignGuest = tourPackage.getCostForeignGuest();

        if (ageRestriction.equals("")) llAgeRetriction.setVisibility(View.GONE);
        if (costFereignGuest.equals("")) llAditionalCost.setVisibility(View.GONE);
        if (customPolicies.size() == 0) rvCustomPolicy.setVisibility(View.GONE);
        if (ageRestriction.equals("") && costFereignGuest.equals("") && customPolicies.size() == 0) {
            llOtherPolicy.setVisibility(View.GONE);
        }

        if (!costFereignGuest.equals("")) {
            NumberFormat formatter = new DecimalFormat("#,###");
            double price = Double.parseDouble(costFereignGuest);

            tv_additional_cash.setText(String.valueOf(formatter.format(price)));
        }

        tv_age_restraction.setText(ageRestriction);

        rvCustomPolicy.setHasFixedSize(true);
        rvCustomPolicy.setNestedScrollingEnabled(false);
        CustomPolicyAdapter policiesAdapter = new CustomPolicyAdapter(this, customPolicies);
        policiesAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvCustomPolicy.setLayoutManager(mLayoutManager);
        rvCustomPolicy.setAdapter(policiesAdapter);
    }

    private void SetSlideImage(List<MediasItem> medias) {
        tabIndicator.setSelectedTabIndicatorHeight(0);
        TourPackageImageAdapter adapter = new TourPackageImageAdapter(this, medias);
        screen_viewpager.setAdapter(adapter);
        tabIndicator.setupWithViewPager(screen_viewpager);
    }

    private void SetlistDisccussion(List<DiscussionReplyItem> discussionReplyItem) {
        if (discussionReplyItem.size() == 0){
            tv_more_discussion.setVisibility(View.GONE);
        } else {
            tv_more_discussion.setVisibility(View.VISIBLE);
        }

        DiscussionAdapter mAdapterDiscussion = new DiscussionAdapter(discussionReplyItem, this, tour_id);
        GridLayoutManager managerNewTour = new GridLayoutManager(this, 1);
        rvDiscussion.setHasFixedSize(true);
        rvDiscussion.setLayoutManager(managerNewTour);
        rvDiscussion.setAdapter(mAdapterDiscussion);
    }

    private void setListReview(ReviewsResponse response) {
        if (response.getTotal_reviewer() <= 0){
            ComponentReviews.setVisibility(View.GONE);
            tv_more_review.setVisibility(View.GONE);
        } else {
            ComponentReviews.setVisibility(View.VISIBLE);
            tv_more_review.setVisibility(View.VISIBLE);
        }

        if (!response.getData().getUser_info().getUrl_photo().equals("")){
            String str = response.getData().getUser_info().getUrl_photo();
            str = str.replace("http://", "https://");

            Picasso.get()
                    .load(str)
                    .fit()
                    .into(ivProfileReview);
        }

        String createdAt = response.getData().getCreated_at();
        String updatedAt = response.getData().getUpdated_at();

        if (updatedAt.equals("0001-01-01T00:00:00Z")) {
            if (response.getData().getCreated_at() != null && !response.getData().getCreated_at().equals("")){
                try {
                    tvDate.setText(DateHelper.getDateFormat1(createdAt));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            String editedReview = getString(R.string.text_edited)+" ";

            if (response.getData().getCreated_at() != null && !response.getData().getCreated_at().equals("")){
                try {
                    tvDate.setText(editedReview+ DateHelper.getDateFormat1(updatedAt));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        tvName.setText(response.getData().getUser_info().getFullname());
        tvMsgReview.setText(response.getData().getReview());
    }

    @Override
    public void onSuccessGetDetailTourPackage(TourPackageItem tourPackageItem) {
        onInitView(tourPackageItem);
        tourDetail = tourPackageItem;
        GetDiscussion();

        container.setVisibility(View.VISIBLE);
        nestedScrollView.setVisibility(View.VISIBLE);
        rlButton.setVisibility(View.VISIBLE);
        llError.setVisibility(View.GONE);
    }

    @Override
    public void onErrorDetailTour(String title, String message) {
        shimmerEffect.stopShimmer();

        lokavenDialog.dialogError(title, message);
        container.setVisibility(View.GONE);
        nestedScrollView.setVisibility(View.GONE);
        rlButton.setVisibility(View.GONE);
        llError.setVisibility(View.VISIBLE);
    }

    private void GetDiscussion() {
        IViewGetNewestDiscussionFromTourIdService iViewGetNewestDiscussionFromTourIdService = this;
        GetNewestDiscussionFromTourIdService getNewestDiscussionFromTourIdService = new GetNewestDiscussionFromTourIdService(this, iViewGetNewestDiscussionFromTourIdService);
        getNewestDiscussionFromTourIdService.onGetDataDiscussion(tour_id);
    }

    @Override
    public void onSuccessGetDataDiscussion(List<DiscussionReplyItem> discussionReplyItem) {
        SetlistDisccussion(discussionReplyItem);

        IViewGetNewestReview view = this;
        GetNewestReviewService presenter = new GetNewestReviewService(view, this);
        presenter.onGetNewestReview(tour_id);

    }

    @Override
    public void onErrorDiscussion(String title, String message) {
        shimmerEffect.stopShimmer();
        lokavenDialog.dialogError(title, message);
    }

    @Override
    public void onSuccessReviews(ReviewsResponse response) {
        shimmerEffect.stopShimmer();
        setListReview(response);
    }

    @Override
    public void onErrorReview(String title, String message) {
        shimmerEffect.stopShimmer();
        lokavenDialog.dialogError(title, message);
    }

    private void onDeleteTour() {
        iViewDeleteTour iViewDeleteTour = this;
        DeleteTourService deleteTourService = new DeleteTourService(this, iViewDeleteTour);
        deleteTourService.onDeleteTour(tour_id);
    }

    @Override
    public void onSuccessDeleteTour(String data) {
        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra("pageHome", 4);
        i.putExtra("uniqid", "deletePackage");
        setResult(RESULT_DELETE, i);
        finish();
    }

    @Override
    public void onErrorDeleteTour(String title, String message, int responseCode) {
        if (responseCode == 401) {
            IViewCheckToken iViewCheckToken = this;
            RefreshTokenServices services = new RefreshTokenServices(this, iViewCheckToken);
            services.onInstrospectToken();
        } else {
            if (!title.isEmpty()){
                lokavenDialog.dialogError(title, message);
            }
        }
    }

    @Override
    public void isToken(boolean success) {
        onDeleteTour();
    }

    @Override
    public void errorToken(String title, String message) {
        loadingPage.stop();
        lokavenDialog.dialogError(title, message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                if (getIntent() != null){
                    if (getIntent().getExtras().getString("tour_id") != null){
                        tour_id = getIntent().getExtras().getString("tour_id");
                        keyword = getIntent().getExtras().getString("keyword");
                        filter_price_low = getIntent().getExtras().getInt("filter_price_low");
                        filter_price_max = getIntent().getExtras().getInt("filter_price_max");
                        filter_place = getIntent().getExtras().getString("filter_place");
                        filter_category = getIntent().getExtras().getStringArrayList("filter_category");
                        filter_type_trip = getIntent().getExtras().getStringArrayList("filter_trip_type");
                        filter_tags = getIntent().getExtras().getStringArrayList("filter_tags");
                        filter_rating = getIntent().getExtras().getFloat("filter_rating");
                        filter_featured = getIntent().getExtras().getBoolean("filter_featured");
                        sort_price = getIntent().getExtras().getString("sort_price");
                        sort_date = getIntent().getExtras().getString("sort_date");
                        sort_rating = getIntent().getExtras().getString("sort_rating");

                        container.setVisibility(View.GONE);
                        nestedScrollView.setVisibility(View.GONE);
                        rlButton.setVisibility(View.GONE);
                        llError.setVisibility(View.GONE);

                        shimmerEffect.startShimmer();
                        iView = this;
                        presenter = new DetailTourService(this, iView);
                        presenter.onGetDataDetailTour(tour_id);

                        CheckSnackbarsUtils checkSnackbarsUtils = new CheckSnackbarsUtils(this);
                        checkSnackbarsUtils.onCheck(getIntent());
                    } else {
                        Intent intent = new Intent(this, HomeActivity.class);
                        intent.putExtra("pageHome", 4);
                        setResult(RESULT_CANCELED, intent);
                        finish();
                    }
                }

                break;

            case RESULT_CANCELED:
                GetDiscussion();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == tvViewSchedules || v == tvManageSchedules) {

            Intent i = new Intent(this, OpenTourScheduleActivity.class);
            i.putExtra("tour_id", tour_id);
            i.putExtra("pageHome", 4);
            i.putExtra("filter_price_low", filter_price_low);
            i.putExtra("filter_price_max", filter_price_max);
            i.putExtra("filter_place", filter_place);
            i.putStringArrayListExtra("filter_category", filter_category);
            i.putStringArrayListExtra("filter_trip_type", filter_type_trip);
            i.putStringArrayListExtra("filter_tags", filter_tags);
            i.putExtra("filter_rating", filter_rating);
            i.putExtra("filter_featured", filter_featured);
            i.putExtra("keyword", keyword);
            i.putExtra("sort_price", sort_price);
            i.putExtra("sort_date", sort_date);
            i.putExtra("sort_rating", sort_rating);
            startActivityForResult(i, 1);

        } else if (v == tv_more_about) {

            if (!expandAbout) {
                expandAbout = true;
                tvDescriptionAbout.setMaxLines(Integer.MAX_VALUE);
                tv_more_about.setText(R.string.text_less);
            } else {
                expandAbout = false;
                tvDescriptionAbout.setMaxLines(5);
                tv_more_about.setText(R.string.text_more);
            }

        } else if (v == tv_more_itinerary) {

            if (!expandItinerary) {
                expandItinerary = true;
                tvDescriptionItinerary.setMaxLines(Integer.MAX_VALUE);
                tv_more_itinerary.setText(R.string.text_less);
            } else {
                expandItinerary = false;
                tvDescriptionItinerary.setMaxLines(5);
                tv_more_itinerary.setText(R.string.text_more);
            }

        } else if (v == tv_more_refund_policy) {

            DialogRefundPolicy dialog = new DialogRefundPolicy(this);
            dialog.show();

//            if (!expandRefundPolicy) {
//                expandRefundPolicy = true;
//                tvDescriptionRefundPolicy.setMaxLines(Integer.MAX_VALUE);
//                tv_more_refund_policy.setText(R.string.text_less);
//            } else {
//                expandRefundPolicy = false;
//                tvDescriptionRefundPolicy.setMaxLines(5);
//                tv_more_refund_policy.setText(R.string.text_more);
//            }

        } else if (v == tv_more_term_of_sevice) {

            if (!expandTermOfService) {
                expandTermOfService = true;
                tvDescriptionTermOfService.setMaxLines(Integer.MAX_VALUE);
                tv_more_term_of_sevice.setText(R.string.text_less);
            } else {
                expandTermOfService = false;
                tvDescriptionTermOfService.setMaxLines(5);
                tv_more_term_of_sevice.setText(R.string.text_more);
            }

        } else if (v == tv_more_insurance) {

            if (!expandInsurance) {
                expandInsurance = true;
                tvDescriptionInsurance.setMaxLines(Integer.MAX_VALUE);
                tv_more_insurance.setText(R.string.text_less);
            } else {
                expandInsurance = false;
                tvDescriptionInsurance.setMaxLines(5);
                tv_more_insurance.setText(R.string.text_more);
            }

        } else if (v == tv_more_review) {

            Intent intent = new Intent(this, ReviewsActivity.class);
            intent.putExtra("tour_id", tour_id);
            startActivityForResult(intent, 1);

        } else if (v == btnEditTour) {

            Intent i = new Intent(OpenTripPackageDetailActivity.this, EditOpenTripPackageActivity.class);
            i.putExtra("tour_id", tour_id);
            i.putExtra("pageHome", 4);
            i.putExtra("filter_price_low", filter_price_low);
            i.putExtra("filter_price_max", filter_price_max);
            i.putExtra("filter_place", filter_place);
            i.putStringArrayListExtra("filter_category", filter_category);
            i.putStringArrayListExtra("filter_trip_type", filter_type_trip);
            i.putStringArrayListExtra("filter_tags", filter_tags);
            i.putExtra("filter_rating", filter_rating);
            i.putExtra("filter_featured", filter_featured);
            i.putExtra("keyword", keyword);
            i.putExtra("sort_price", sort_price);
            i.putExtra("sort_date", sort_date);
            i.putExtra("sort_rating", sort_rating);
            startActivityForResult(i, 1);

        } else if (v == tv_more_discussion) {

            Intent intent = new Intent(this, GetAllDiscussionActivity.class);
            intent.putExtra("tour_id", tour_id);
            intent.putExtra("typeTour", tourDetail.getTypeTour());
            intent.putExtra("titleTour", tourDetail.getTitle());
            intent.putExtra("locationTour", tourDetail.getLocation());
            intent.putExtra("thumbnailTour", tourDetail.getMedias().get(0).getUrl());
            startActivityForResult(intent, 1);

        } else if(v == rlMore) {

            PopupMenu popupMenu = new PopupMenu(getApplicationContext(), rlMore);
            popupMenu.getMenuInflater().inflate(R.menu.delete_navigation, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    iViewDeleteTour iViewDeleteTour = OpenTripPackageDetailActivity.this;
                    DeleteTourService deleteTourService = new DeleteTourService(OpenTripPackageDetailActivity.this, iViewDeleteTour);
                    deleteTourService.DialogDeleteTourConfirm(tour_id);
                    return true;
                }
            });
            popupMenu.show();

        } else if (v == floating_menu){

            appbar.setExpanded(true);
            nestedScrollView.smoothScrollTo(0, 0);

//            DialogFABTour dialog = new DialogFABTour(this);
//            dialog.show();

        } else if (v == rlBack){
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(OpenTripPackageDetailActivity.this, HomeActivity.class);
        i.putExtra("pageHome", 4);
        i.putExtra("filter_price_low", filter_price_low);
        i.putExtra("filter_price_max", filter_price_max);
        i.putExtra("filter_place", filter_place);
        i.putStringArrayListExtra("filter_category", filter_category);
        i.putStringArrayListExtra("filter_trip_type", filter_type_trip);
        i.putStringArrayListExtra("filter_tags", filter_tags);
        i.putExtra("filter_rating", filter_rating);
        i.putExtra("filter_featured", filter_featured);
        i.putExtra("keyword", keyword);
        i.putExtra("sort_price", sort_price);
        i.putExtra("sort_date", sort_date);
        i.putExtra("sort_rating", sort_rating);
        setResult(RESULT_CANCELED, i);
        finish();
    }
}
