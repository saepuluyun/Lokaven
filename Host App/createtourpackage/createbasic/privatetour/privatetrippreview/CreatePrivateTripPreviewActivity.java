package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privatetrippreview;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.helper.image.CompressImageHelper;
import com.aniqma.lokaventour.host.model.item.wallet.DetailFeesItem;
import com.aniqma.lokaventour.host.network.services.wallet.detailfees.DetailFeesService;
import com.aniqma.lokaventour.host.network.services.wallet.detailfees.IViewDetailFees;
import com.aniqma.lokaventour.host.utils.dialog.LokavenDialog;
import com.aniqma.lokaventour.host.utils.animation.LoadingPage;
import com.aniqma.lokaventour.host.helper.format.TextFormatHelper;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.AddonsItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.CreateTourPackageItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.CustomPolicyItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.MediasItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.PricesItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.SchedulesItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.TourPackageItem;
import com.aniqma.lokaventour.host.model.response.UploadServiceResponse;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privatetrippreview.adapter.AddOnsAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privatetrippreview.adapter.AvailableSchedulePrivateAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privatetrippreview.adapter.CategoryAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privatetrippreview.adapter.CustomArrayAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privatetrippreview.adapter.OtherPoliceAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privatetrippreview.adapter.TagsAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privatetrippreview.adapter.TourPackageImageAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privatetrippreview.dialog.DialogRefundPolicy;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createaddons.CreateAddOnsActivity;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailprivate.PrivateTripPackageDetailActivity;
import com.aniqma.lokaventour.host.network.local.createtourpackage.TourPackageLite;
import com.aniqma.lokaventour.host.network.services.authentication.checktoken.IViewCheckToken;
import com.aniqma.lokaventour.host.network.services.authentication.checktoken.RefreshTokenServices;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.createtourpackage.CreatePrivateTripPreviewPresenter;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.createtourpackage.IViewCreatePrivateTripPreview;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.aniqma.lokaventour.host.helper.date.DateHelper.compareDate;
import static com.aniqma.lokaventour.host.helper.date.DateHelper.isoStringToDate;

public class CreatePrivateTripPreviewActivity extends AppCompatActivity
        implements View.OnClickListener, IViewCreatePrivateTripPreview, IViewCheckToken, IViewDetailFees {

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
            tv_title,
            tv_back;
    private Button btn_publish;
    private CreateTourPackageItem tourPackageItem;
    private IViewCreatePrivateTripPreview iViewCreatePrivateTripPreview;
    private CreatePrivateTripPreviewPresenter presenter;
    private ArrayList<String> urlImage = null;

    private View compTitlePage, compAppbar, compBtn, compTypeTour,
            CompDuration, CompLocation,
            compSchedule, compAbout, compItinerary, compAddons, ComponentInsurance,
            compTos, compRefundPolicy, compOtherPolicy, compDivider;
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
            tv_age_restraction, tv_additional_cash;
    IViewCheckToken iViewCheckToken = this;

    private RecyclerView rvTags, rvSchedule, rvAddons, rvCustomPolicy, rvCategory;
    private LinearLayout llRating, llAddons, llAgeRetriction, llAditionalCost, llOtherPolicy;
    private Spinner spPrice;
    private List<MediasItem> array;
    private List<MediasItem> ArrayMedias = new ArrayList<>();
    private MediasItem mediasItem;
    int mode = 0, image = 0, addons = 0;
    private LoadingPage loadingPage;
    private LokavenDialog lokavenDialog;
    private DetailFeesItem detailFeesItem;
    private boolean ImagePackage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_private_trip_preview);
        init();
        loadingPage = new LoadingPage(this);
        tourPackageItem = new CreateTourPackageItem();
        lokavenDialog = new LokavenDialog(this);

        IViewDetailFees IView = this;
        DetailFeesService detailFeesService = new DetailFeesService(this, IView);
        detailFeesService.onGetDetailFees();

        array = new ArrayList<>();

        if (getIntent().getStringArrayListExtra("imageList") != null){
            urlImage = getIntent().getStringArrayListExtra("imageList");
            urlImage.remove(urlImage.size()-1);
        }

        if (getIntent().getSerializableExtra("tourpackage") != null){
            tourPackageItem = (CreateTourPackageItem) getIntent().getSerializableExtra("tourpackage");

            mode = getIntent().getIntExtra("pagePrivate", 0);
        }

        for (int i = 0; i < urlImage.size(); i++){
            MediasItem mediasItem = new MediasItem();
            mediasItem.setUrl(urlImage.get(i));
            mediasItem.setType("image");
            tourPackageItem.getMedias().add(mediasItem);
        }

        SetContent();
        tabIndicator.setSelectedTabIndicatorHeight(0);
        TourPackageImageAdapter adapter = new TourPackageImageAdapter(this, tourPackageItem.getMedias());
        screen_viewpager.setAdapter(adapter);
        tabIndicator.setupWithViewPager(screen_viewpager);

        SetListCategory(tourPackageItem.getCategories());
        SetListTags(tourPackageItem.getTags());
        SetListSchedules(tourPackageItem.getSchedules());

        if (tourPackageItem.getAddons() != null){
            int i = 0;
            while (i < tourPackageItem.getAddons().size()){
                if (!tourPackageItem.getAddons().get(i).getAddon().isEmpty() || !tourPackageItem.getAddons().get(i).getPrice().isEmpty()){
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

        if (tourPackageItem.getCustom_policies().size() == 0 && tourPackageItem.getAge_restriction().isEmpty() && tourPackageItem.getCost_foreign_guest().isEmpty()){
            llOtherPolicy.setVisibility(View.GONE);
        } else {
            llOtherPolicy.setVisibility(View.VISIBLE);
        }

        SetListCustomPolicy(tourPackageItem.getCustom_policies());

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

        tvDescriptionTermOfService.setText(tourPackageItem.getTerms_of_service());
        tvDescriptionAbout.setText(tourPackageItem.getDescription());
        tvDescriptionItinerary.setText(tourPackageItem.getItinerary());

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
        tv_more_about.setOnClickListener(this);
        tv_more_itinerary.setOnClickListener(this);
        tv_more_term_of_sevice.setOnClickListener(this);
        tv_more_insurance.setOnClickListener(this);
        btn_more_refund_policy.setOnClickListener(this);
    }

    private void SetListPrice(List<PricesItem> priceList, double taxNominal, double taxPercentage) {

        CustomArrayAdapter adapter = new CustomArrayAdapter(this,
                R.layout.component_info_list_price, priceList, taxNominal, taxPercentage);

        adapter.setDropDownViewResource(R.layout.component_info_list_price_dropdown);
        spPrice.setAdapter(adapter);
        spPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = spPrice.getItemAtPosition(position).toString();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        addOnsAdapter = new AddOnsAdapter(addons, this, "private");
        rvAddons.setAdapter(addOnsAdapter);

    }

    private void SetListSchedules(List<SchedulesItem> schedules) {
        rvSchedule.setHasFixedSize(true);
        layoutManagerSchedule = new LinearLayoutManager(this);
        rvSchedule.setLayoutManager(layoutManagerSchedule);
        AvailableSchedulePrivateAdapter scheduleAdapter = new AvailableSchedulePrivateAdapter(schedules, this);
        rvSchedule.setAdapter(scheduleAdapter);
    }

    private void SetListTags(List<String> tags) {
        TagsAdapter tagsAdapter = new TagsAdapter(this, tags);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvTags.setHasFixedSize(true);
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


    private void SetContent() {
        tv_title.setText(getResources().getString(R.string.text_private_trip_title));
        tvTitleTour.setText(tourPackageItem.getTitle());
        tvNumberOfStep.setText(Html.fromHtml(getResources().getString(R.string.text_step_6)));
        tvDetailPage.setText(getResources().getString(R.string.text_preview_desc));
    }

    private void init() {
        spPrice = findViewById(R.id.spPrice);
        compDivider = findViewById(R.id.compDivider);
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
        tvDetailPage = compTitlePage.findViewById(R.id.tvDetailPage);

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

        compAppbar = findViewById(R.id.compAppbar);
        ivBack = compAppbar.findViewById(R.id.ivBack);
        tv_title = compAppbar.findViewById(R.id.tvTitle);

        compTypeTour = findViewById(R.id.compTypeTour);
        tvTypeTour = compTypeTour.findViewById(R.id.tvTitle);
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

    private void SetLinePage() {
        rlStep1.setBackgroundColor(getResources().getColor(R.color.LOK_Blue));
        rlStep2.setBackgroundColor(getResources().getColor(R.color.LOK_Navi_Blue));
        rlStep3.setBackgroundColor(getResources().getColor(R.color.LOK_Red));
        rlStep4.setBackgroundColor(getResources().getColor(R.color.LOK_Teal));
        rlStep5.setBackgroundColor(getResources().getColor(R.color.LOK_Gold));
        rlStep6.setBackgroundColor(getResources().getColor(R.color.LOK_Info));
    }

    private void onUploadImage() {
        CompressImageHelper compressImageHelper = new CompressImageHelper(this);
        iViewCreatePrivateTripPreview = this;
        presenter = new CreatePrivateTripPreviewPresenter(this, iViewCreatePrivateTripPreview);
        presenter.onUploadImage(compressImageHelper.compressImage(urlImage.get(0)), "tour_package");
    }

    @Override
    public void onSuccessUpload(UploadServiceResponse uploadServiceItem) {
        if (uploadServiceItem.getCode().equals("200")){
            if (ImagePackage){
                mediasItem = new MediasItem();
                mediasItem.setUrl(uploadServiceItem.getData().getUrl());
                mediasItem.setType("image");
                tourPackageItem.getMedias().add(mediasItem);
                urlImage.remove(0);
            } else {
                mediasItem = new MediasItem();
                mediasItem.setImage_url(uploadServiceItem.getData().getUrl());
                tourPackageItem.getAddons().get(addons).getMediasItems().add(0, mediasItem);
                tourPackageItem.getAddons().get(addons).getImage().remove(tourPackageItem.getAddons().get(addons).getImage().size()-1);
            }

            if (urlImage != null && urlImage.size() != 0){
                CompressImageHelper compressImageHelper = new CompressImageHelper(this);
                for (int i = 0; i < urlImage.size(); i++){
                    presenter = new CreatePrivateTripPreviewPresenter(this, iViewCreatePrivateTripPreview);
                    presenter.onUploadImage(compressImageHelper.compressImage(urlImage.get(i)), "tour_package");
                }
            } else {
                ImagePackage = false;
                if (tourPackageItem.getAddons() != null){
                    if (tourPackageItem.getAddons().size() != 0){
                        while (addons < tourPackageItem.getAddons().size()){
                            if (tourPackageItem.getAddons().get(addons).getImage().size() != 0){
                                CompressImageHelper compressImageHelper = new CompressImageHelper(this);
                                presenter = new CreatePrivateTripPreviewPresenter(this, iViewCreatePrivateTripPreview);
                                presenter.onUploadImage(compressImageHelper.compressImage(tourPackageItem.getAddons().get(addons).getImage().get(tourPackageItem.getAddons().get(addons).getImage().size()-1)), "addons");
                                break;
                            }
                            addons++;
                        }

                        if (addons == tourPackageItem.getAddons().size()){
                            presenter.onCreateTourPackage(tourPackageItem);
                        }
                    } else {
                        presenter.onCreateTourPackage(tourPackageItem);
                    }
                } else {
                    presenter.onCreateTourPackage(tourPackageItem);
                }

            }
        }
    }

    @Override
    public void onSuccessCreateTourPackage(TourPackageItem tourPackageItem) {
        TourPackageItem tour = new TourPackageItem();
        tour = tourPackageItem;
        TourPackageLite tourPackageLite = new TourPackageLite(this);
        tourPackageLite.deleteDB();
        loadingPage.stop();
        Intent i = new Intent(this, PrivateTripPackageDetailActivity.class);
        i.putExtra("tour_id", tour.getTourId());
        i.putExtra("uniqid", "addPackage");
        startActivity(i);
        finish();
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
        onUploadImage();
    }

    @Override
    public void errorToken(String title, String message) {
        loadingPage.stop();
        lokavenDialog.dialogError(title, message);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_publish){
            for (int i = 0; i < tourPackageItem.getAddons().size(); i ++){
                if (tourPackageItem.getAddons().get(i).getImage().size() != 0){
                    tourPackageItem.getAddons().get(i).getImage().remove(tourPackageItem.getAddons().get(i).getImage().size()-1);
                }
            }
            tourPackageItem.setType_tour("close");
            int i = 0;
            while (i < tourPackageItem.getMedias().size()){
                if (tourPackageItem.getMedias().get(i).getType().trim().equals("image")){
                    tourPackageItem.getMedias().remove(i);
                } else {
                    i++;
                }
            }
            loadingPage.start();
            onUploadImage();
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
        int i = 0;
        while (i < tourPackageItem.getMedias().size()){
            if (tourPackageItem.getMedias().get(i).getType().trim().equals("image")){
                tourPackageItem.getMedias().remove(i);
            } else {
                i++;
            }
        }
        urlImage.add("");
        Intent intent = new Intent(this, CreateAddOnsActivity.class);
        intent.putStringArrayListExtra("imageList", urlImage);
        intent.putExtra("pagePrivate", 1);
        intent.putExtra("tourpackage", tourPackageItem);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccess(DetailFeesItem data) {
        detailFeesItem = data;
        double taxNominal = 0;
        double taxPercentage = 0;

        for (int i = 0; i < detailFeesItem.getCommissions().size(); i++){
            if (detailFeesItem.getCommissions().get(i).isIs_active() && detailFeesItem.getCommissions().get(i).isMark_up()){
                taxNominal = detailFeesItem.getCommissions().get(i).getValue_nominal();
                taxPercentage = detailFeesItem.getCommissions().get(i).getValue_percentage();
            }
        }

        if (tourPackageItem.getPrices() != null){
            int price = 0;
            ArrayList<PricesItem> priceList = new ArrayList<>();
            for (int i = 0; i < tourPackageItem.getPrices().size(); i++){
                if (price < Integer.valueOf(tourPackageItem.getPrices().get(i).getPrice())){
                    price = Integer.valueOf(tourPackageItem.getPrices().get(i).getPrice());
                    priceList.add(tourPackageItem.getPrices().get(i));
                }
            }

            SetListPrice(priceList, taxNominal, taxPercentage);

        }
    }

    @Override
    public void onError(String title, String message, int code) {

    }
}
