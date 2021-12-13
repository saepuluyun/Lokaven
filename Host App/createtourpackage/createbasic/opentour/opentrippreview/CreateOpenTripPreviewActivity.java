package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.opentour.opentrippreview;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.helper.image.CompressImageHelper;
import com.aniqma.lokaventour.host.helper.format.NumberHelper;
import com.aniqma.lokaventour.host.utils.dialog.LokavenDialog;
import com.aniqma.lokaventour.host.utils.animation.LoadingPage;
import com.aniqma.lokaventour.host.helper.format.TextFormatHelper;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.AddonsItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.CreateTourPackageItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.CustomPolicyItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.MediasItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.SchedulesItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.TourPackageItem;
import com.aniqma.lokaventour.host.model.response.UploadServiceResponse;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.opentour.opentrippreview.adapter.AddOnsAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.opentour.opentrippreview.adapter.AvailableScheduleOpenAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.opentour.opentrippreview.adapter.CategoryAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.opentour.opentrippreview.adapter.OtherPoliceAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.opentour.opentrippreview.adapter.TagsAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.opentour.opentrippreview.adapter.TourPackageImageAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createaddons.CreateAddOnsActivity;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailopen.OpenTripPackageDetailActivity;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.opentour.opentrippreview.dialog.DialogRefundPolicy;
import com.aniqma.lokaventour.host.network.local.createtourpackage.TourPackageLite;
import com.aniqma.lokaventour.host.network.services.authentication.checktoken.IViewCheckToken;
import com.aniqma.lokaventour.host.network.services.authentication.checktoken.RefreshTokenServices;

import com.aniqma.lokaventour.host.network.services.tourpackageservice.createtourpackage.CreateOpenTripPreviewPresenter;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.createtourpackage.IViewCreateOpenTripPreview;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.aniqma.lokaventour.host.helper.date.DateHelper.compareDate;
import static com.aniqma.lokaventour.host.helper.date.DateHelper.isoStringToDate;

public class CreateOpenTripPreviewActivity extends AppCompatActivity
        implements View.OnClickListener, IViewCreateOpenTripPreview, IViewCheckToken {

    private TabLayout tabIndicator;
    private ViewPager screen_viewpager;
    private RecyclerView.LayoutManager layoutManager,
            layoutManagerSchedule,
            layoutManagerAddons,
            layoutManagerOtherPolice;

    private AddOnsAdapter addOnsAdapter;

    private OtherPoliceAdapter otherPoliceAdapter;

    private TextView tv_more_about,
            tv_more_itinerary,
            tv_more_addons,
            tv_more_term_of_sevice,
            tv_more_insurance,
            btn_more_refund_policy,
            tv_back,
            tvTitleAppbar;
    private Button btn_publish;
    private RelativeLayout relative_host;
    private CreateTourPackageItem tourPackageItem;
    private IViewCreateOpenTripPreview iViewCreateOpenTripPreview;
    private CreateOpenTripPreviewPresenter presenter;
    private ArrayList<String> urlImage = null;
    IViewCheckToken iViewCheckToken = this;
    private LokavenDialog lokavenDialog;

    private View compTitlePage, compAppbar, compBtn, compTypeTour,
            CompDuration, CompLocation,
            compSchedule, compAbout, compItinerary, compAddons, ComponentInsurance,
            compTos, compRefundPolicy, compOtherPolicy;
    private ImageView ivBack, iconDuration, ivIconSchedule, ivIconAbout, ivIconItinerary, iviconAddons;
    private TextView tvNumberOfStep, tvNameOfPage, tvDetailPage;
    private RelativeLayout rlStep1, rlStep2, rlStep3, rlStep4, rlStep5, rlStep6;
    private TextView tvTitleTour, tvPrice, tvTypeTour, textDuration, tvDuration, textLocation, tvLocation,
            tvTitleSchedule, tvDescriptionSchedule,
            tvTitleAbout, tvDescriptionAbout,
            tvTitleItinerary, tvDescriptionItinerary,
            tvTitleAddons, tvDescriptionAddons,
            tvTitleTermOfSercive, tvDescriptionTermOfService,
            tvTitleInsurance, tvSubtitleInsurance, tvDescriptionInsurance,
            tvTitleRefundPolicy, tvDescriptionRefundPolicy,
            tvTitleOtherPolicy, tvDescriptionOtherPolicy,
            tv_age_restraction, tv_additional_cash, tvPriceChild;
    private RecyclerView rvTags, rvSchedule, rvAddons, rvCustomPolicy, rvCategory;
    private LinearLayout llRating, llAddons, llAditionalCost, llAgeRetriction, llOtherPolicy;
    private List<MediasItem> array;
    private MediasItem mediasItem;
    int mode = 0;
    private LoadingPage loadingPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour_package_preview);
        init();
        loadingPage = new LoadingPage(this);
        lokavenDialog = new LokavenDialog(this);
        array = new ArrayList<>();
        tourPackageItem = new CreateTourPackageItem();
        if (getIntent().getStringArrayListExtra("imageList") != null){
            urlImage = getIntent().getStringArrayListExtra("imageList");
            urlImage.remove(urlImage.size()-1);
        }

        if (getIntent().getSerializableExtra("tourpackage") != null){
            tourPackageItem = (CreateTourPackageItem) getIntent().getSerializableExtra("tourpackage");
            mode = getIntent().getIntExtra("pagePrivate", 0);
        }
        tvTitleAppbar.setText(getResources().getString(R.string.text_create_open_trip));
        tvTitleTour.setText(tourPackageItem.getTitle());
        tabIndicator.setSelectedTabIndicatorHeight(0);

        TourPackageImageAdapter adapter = new TourPackageImageAdapter(this, urlImage);
        screen_viewpager.setAdapter(adapter);
        tabIndicator.setupWithViewPager(screen_viewpager);

        SetListTags(tourPackageItem.getTags());
        SetListSchedules(tourPackageItem.getSchedules());

        if (tourPackageItem.getAddons() != null){
            int i = 0;
            while (i < tourPackageItem.getAddons().size()){
                if (!tourPackageItem.getAddons().get(i).getAddon().isEmpty()){
                    if (!tourPackageItem.getAddons().get(i).getAddon().isEmpty() && tourPackageItem.getAddons().get(i).getPrice().isEmpty()){
                        tourPackageItem.getAddons().get(i).setPrice("0");
                    }
                    i++;
                } else {
                    tourPackageItem.getAddons().remove(i);
                }
            }

            SetListAddons(tourPackageItem.getAddons());

            if (tourPackageItem.getAddons().size() == 0){
                llAddons.setVisibility(View.GONE);
            } else {
                llAddons.setVisibility(View.VISIBLE);
            }
        }

        SetListCustomPolicy(tourPackageItem.getCustom_policies());
        SetListCategory(tourPackageItem.getCategories());

        tvDescriptionItinerary.setText(tourPackageItem.getItinerary());

        try {
            Date startDate = isoStringToDate(tourPackageItem.getSchedules().get(0).getStartDate());
            Date endDate = isoStringToDate(tourPackageItem.getSchedules().get(0).getEndDate());
            int countDate = Integer.valueOf(Long.toString(compareDate(startDate, endDate)));

            tvDuration.setText(String.valueOf(countDate) + " " + getString(R.string.text_days));
            if (countDate == 0) {
                tvDuration.setText("1 " + getString(R.string.text_days));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (tourPackageItem.getCustom_policies().size() == 0 && tourPackageItem.getAge_restriction().isEmpty() && tourPackageItem.getCost_foreign_guest().isEmpty()){
            llOtherPolicy.setVisibility(View.GONE);
        } else {
            llOtherPolicy.setVisibility(View.VISIBLE);
        }

        if (tourPackageItem.getAge_restriction().isEmpty()){
            llAgeRetriction.setVisibility(View.GONE);
        } else {
            llAgeRetriction.setVisibility(View.VISIBLE);
        }

        tv_age_restraction.setText(tourPackageItem.getAge_restriction() + "+ Years");

        if (!tourPackageItem.getCost_foreign_guest().isEmpty()){
            llAditionalCost.setVisibility(View.VISIBLE);
            tv_additional_cash.setText(TextFormatHelper.getDecimalFormat(tourPackageItem.getCost_foreign_guest()));
        } else {
            llAditionalCost.setVisibility(View.GONE);
        }

        tvLocation.setText(tourPackageItem.getLocation());
        tvDescriptionTermOfService.setText(tourPackageItem.getTerms_of_service());
        tvDescriptionAbout.setText(tourPackageItem.getDescription());

        if (tourPackageItem.getPrices() != null){
            int price = 0;
            for (int i = 0; i < tourPackageItem.getPrices().size(); i++){
                if (price < Integer.valueOf(tourPackageItem.getPrices().get(i).getPrice())){
                    price = Integer.valueOf(tourPackageItem.getPrices().get(i).getPrice());
                }
            }

//            double tax = Double.parseDouble(String.valueOf(price)) * 0.02;
//            double laba = Double.parseDouble(String.valueOf(price)) * 0.1;

            tvPrice.setText(NumberHelper.formatIdr(Double.parseDouble(String.valueOf(price))));
            if (tourPackageItem.getPrices().get(0).getKidPrice() != null && !tourPackageItem.getPrices().get(0).getKidPrice().isEmpty()){
                tvPriceChild.setText(NumberHelper.formatIdr(Double.parseDouble(tourPackageItem.getPrices().get(0).getKidPrice())));
            }
        }

        if (tvDescriptionTermOfService.getLineCount() > 3){
            tvDescriptionTermOfService.setMaxLines(3);
        } else {
            tv_more_term_of_sevice.setVisibility(View.GONE);
        }

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

        if (tvDescriptionAbout.getLineCount() > 3){
            tvDescriptionAbout.setMaxLines(3);
        } else {
            tv_more_about.setVisibility(View.GONE);
        }

        if (tvTitleItinerary.getLineCount() > 3){
            tvTitleItinerary.setMaxLines(3);
        } else {
            tv_more_itinerary.setVisibility(View.GONE);
        }

        tvDescriptionRefundPolicy.setText(getString(R.string.text_refund_policy_desc_true));
//        if (createTourPackageItem.isIs_refundable()){
//            tvDescriptionRefundPolicy.setText(getString(R.string.text_refund_policy_desc_true));
//        } else {
//            tvDescriptionRefundPolicy.setText(getString(R.string.text_refund_policy_desc));
//        }

//        if (tvDescriptionRefundPolicy.getLineCount() > 3){
//            tvDescriptionRefundPolicy.setMaxLines(3);
//        } else {
//            btn_more_refund_policy.setVisibility(View.GONE);
//        }

        btn_more_refund_policy.setOnClickListener(this);
        btn_publish.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btn_more_refund_policy.setOnClickListener(this);
        tv_more_about.setOnClickListener(this);
        tv_more_itinerary.setOnClickListener(this);
        tv_more_term_of_sevice.setOnClickListener(this);
        tv_more_insurance.setOnClickListener(this);
    }

    private void SetListCategory(List<String> categories) {
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, this);
        rvCategory.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvCategory.setLayoutManager(layoutManager);
        rvCategory.setAdapter(categoryAdapter);
        rvCategory.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(3,3,3,3);
            }
        });
    }

    private void SetListCustomPolicy(List<CustomPolicyItem> custom_policies) {
        rvCustomPolicy.setHasFixedSize(true);
        layoutManagerOtherPolice = new LinearLayoutManager(this);
        rvCustomPolicy.setLayoutManager(layoutManagerOtherPolice);
        otherPoliceAdapter = new OtherPoliceAdapter(this, custom_policies);
        rvCustomPolicy.setAdapter(otherPoliceAdapter);
    }

    private void SetListAddons(List<AddonsItem> addons) {
        rvAddons.setHasFixedSize(true);
        layoutManagerAddons = new LinearLayoutManager(this);
        rvAddons.setLayoutManager(layoutManagerAddons);
        addOnsAdapter = new AddOnsAdapter(addons, this, "open");
        rvAddons.setAdapter(addOnsAdapter);
    }

    private void SetListSchedules(List<SchedulesItem> schedules) {
        rvSchedule.setHasFixedSize(true);
        layoutManagerSchedule = new LinearLayoutManager(this);
        rvSchedule.setLayoutManager(layoutManagerSchedule);
        AvailableScheduleOpenAdapter scheduleAdapter = new AvailableScheduleOpenAdapter(schedules, this);
        rvSchedule.setAdapter(scheduleAdapter);
    }

    private void SetListTags(List<String> tags) {
        TagsAdapter tagsAdapter = new TagsAdapter(this, tags);
        rvTags.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvTags.setLayoutManager(layoutManager);
        rvTags.setAdapter(tagsAdapter);
        rvTags.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(3,3,3,3);
            }
        });
    }

    private void SetLinePage() {
        rlStep1.setBackgroundColor(getResources().getColor(R.color.LOK_Blue));
        rlStep2.setBackgroundColor(getResources().getColor(R.color.LOK_Navi_Blue));
        rlStep3.setBackgroundColor(getResources().getColor(R.color.LOK_Red));
        rlStep4.setBackgroundColor(getResources().getColor(R.color.LOK_Teal));
        rlStep5.setBackgroundColor(getResources().getColor(R.color.LOK_Gold));
        rlStep6.setBackgroundColor(getResources().getColor(R.color.LOK_Info));
    }

    private void init() {
        tabIndicator = findViewById(R.id.tab_indicator_tour_package);
        screen_viewpager = findViewById(R.id.screen_viewpager_tour_package);
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
        tvDescriptionRefundPolicy.setText(getString(R.string.text_refund_policy_desc));

        compTos = findViewById(R.id.compTos);
        tvTitleTermOfSercive = compTos.findViewById(R.id.tvTitle);
        tvTitleTermOfSercive.setText(getString(R.string.text_term_of_service));
        tvDescriptionTermOfService = compTos.findViewById(R.id.tvDescription);

        ComponentInsurance = findViewById(R.id.ComponentInsurance);
        tvTitleInsurance = ComponentInsurance.findViewById(R.id.tvTitle);
        tvSubtitleInsurance = ComponentInsurance.findViewById(R.id.tvSubTitle);
        tvDescriptionInsurance = ComponentInsurance.findViewById(R.id.tvDescription);
        tv_more_insurance = findViewById(R.id.tv_more_insurance);

        rvAddons = findViewById(R.id.rvAddons);
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

        CompDuration = findViewById(R.id.CompDuration);
        iconDuration = CompDuration.findViewById(R.id.ivIconLocation);
        iconDuration.setImageResource(R.drawable.icon_18dp_duration_blue);
        textDuration = CompDuration.findViewById(R.id.tvTitle);
        textDuration.setText(getString(R.string.text_duration));
        tvDuration = CompDuration.findViewById(R.id.tvDescription);

        CompLocation = findViewById(R.id.CompLocation);
        textLocation = CompLocation.findViewById(R.id.tvTitle);
        textLocation.setText(getString(R.string.text_location));
        tvLocation = CompLocation.findViewById(R.id.tvDescription);

        compTitlePage = findViewById(R.id.compTitlePage);
        tvNumberOfStep = compTitlePage.findViewById(R.id.tvNumberOfStep);
        tvNumberOfStep.setText(Html.fromHtml(getResources().getString(R.string.text_step_6)));
        tvDetailPage.setText(getResources().getString(R.string.text_preview_desc));

        rlStep1 = compTitlePage.findViewById(R.id.rlStep1);
        rlStep2 = compTitlePage.findViewById(R.id.rlStep2);
        rlStep3 = compTitlePage.findViewById(R.id.rlStep3);
        rlStep4 = compTitlePage.findViewById(R.id.rlStep4);
        rlStep5 = compTitlePage.findViewById(R.id.rlStep5);
        rlStep6 = compTitlePage.findViewById(R.id.rlStep6);
        SetLinePage();

        compBtn = findViewById(R.id.compBtn);
        btn_publish = compBtn.findViewById(R.id.btnBig);
        btn_publish.setText(getString(R.string.text_create_and_publish));
        tv_back = compBtn.findViewById(R.id.tvTextButton);
        tv_back.setText(getString(R.string.text_back));

        tvTitleTour = findViewById(R.id.tvTitleTour);
        tvPrice = findViewById(R.id.tvPrice);
        tvPriceChild = findViewById(R.id.tvPriceChild);

        compAppbar = findViewById(R.id.compAppbar);
        ivBack = compAppbar.findViewById(R.id.ivBack);
        tvTitleAppbar = compAppbar.findViewById(R.id.tvTitle);

        compTypeTour = findViewById(R.id.compTypeTour);
        tvTypeTour = compTypeTour.findViewById(R.id.tvTitle);
        tvTypeTour.setBackground(getResources().getDrawable(R.drawable.custom_button_blue));
        tvTypeTour.setText(R.string.text_open_tour);
        llRating = compTypeTour.findViewById(R.id.llRating);
        llRating.setVisibility(View.GONE);

        tvTitleTour = findViewById(R.id.tvTitleTour);
        tv_more_about = findViewById(R.id.tv_more_about);
        tv_more_itinerary = findViewById(R.id.tv_more_itinerary);
        btn_more_refund_policy = findViewById(R.id.btn_more_refund_policy);
        tv_more_term_of_sevice = findViewById(R.id.tv_more_term_of_sevice);

        llAddons = findViewById(R.id.llAddons);
        llAgeRetriction = findViewById(R.id.llAgeRetriction);
        llAditionalCost = findViewById(R.id.llAditionalCost);
        llOtherPolicy = findViewById(R.id.llOtherPolicy);
    }

    private void uploadImage() {
        CompressImageHelper compressImageHelper = new CompressImageHelper(this);
        iViewCreateOpenTripPreview = this;
        presenter = new CreateOpenTripPreviewPresenter(this, iViewCreateOpenTripPreview);
        presenter.onUploadImage(compressImageHelper.compressImage(urlImage.get(0)), "tour_package");
    }

    @Override
    public void onSuccessCreateTourPackage(TourPackageItem tourPackageItem) {
        TourPackageItem tour = new TourPackageItem();
        tour = tourPackageItem;
        TourPackageLite tourPackageLite = new TourPackageLite(this);
        tourPackageLite.deleteDB();
        loadingPage.stop();
        Intent i = new Intent(this, OpenTripPackageDetailActivity.class);
        i.putExtra("tour_id", tour.getTourId());
        startActivity(i);
        finish();
    }

    @Override
    public void onSuccessUpload(UploadServiceResponse uploadServiceItem) {
        if (uploadServiceItem.getCode().equals("200")){
            mediasItem = new MediasItem();
            mediasItem.setUrl(uploadServiceItem.getData().getUrl());
            array.add(mediasItem);
            mediasItem.setType("image");
            tourPackageItem.setMedias(array);
            urlImage.remove(0);
            if (urlImage != null && urlImage.size() != 0){
                CompressImageHelper compressImageHelper = new CompressImageHelper(this);
                for (int i = 0; i < urlImage.size(); i++){
                    iViewCreateOpenTripPreview = this;
                    presenter = new CreateOpenTripPreviewPresenter(this, iViewCreateOpenTripPreview);
                    presenter.onUploadImage(compressImageHelper.compressImage(urlImage.get(i)), "tour_package");
                }
            } else {
                presenter.onCreateTourPackage(tourPackageItem);
            }
        }
    }

    @Override
    public void onErrorData(String title, String message, int responseCode) {
        if (responseCode == 401) {
            RefreshTokenServices services = new RefreshTokenServices(this, iViewCheckToken);
            services.onInstrospectToken();
        } else {
            loadingPage.stop();
            lokavenDialog.dialogError(title, message);
        }
    }

    @Override
    public void isToken(boolean success) {
        uploadImage();
    }

    @Override
    public void errorToken(String title, String message) {
        loadingPage.stop();
        lokavenDialog.dialogError(title, message);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_publish){
            tourPackageItem.setType_tour("open");
            loadingPage.start();
            uploadImage();
        }else if (v == tv_back || v == ivBack){
            onBackPressed();
            finish();
        } else if (v == tv_more_about){
            tvDescriptionAbout.setMaxLines(50);
        } else if (v == tv_more_itinerary){
            tvDescriptionItinerary.setMaxLines(50);
        } else if (v == btn_more_refund_policy){
            DialogRefundPolicy dialog = new DialogRefundPolicy(this);
            dialog.show();
        } else if (v == tv_more_term_of_sevice){
            tvDescriptionTermOfService.setMaxLines(50);
        } else if (v == tv_more_insurance){
            tvDescriptionInsurance.setMaxLines(50);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        urlImage.add("");
        Intent intent = new Intent(this, CreateAddOnsActivity.class);
        intent.putStringArrayListExtra("imageList", urlImage);
        intent.putExtra("tourpackage", tourPackageItem);
        startActivity(intent);
        finish();
    }
}
