package com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.edittripprivatepackage;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.helper.image.CompressImageHelper;
import com.aniqma.lokaventour.host.helper.image.RealPathHelper;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.InsuranceItem;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.edittripprivatepackage.adapter.AddListVideoAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.edittripprivatepackage.adapter.AddonsImagesAdapter;
import com.aniqma.lokaventour.host.utils.dialog.LokavenDialog;
import com.aniqma.lokaventour.host.utils.animation.LoadingPage;
import com.aniqma.lokaventour.host.utils.misc.inlinehelp.TooltipWindowUtils;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.AdditionalCostItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.DataTourCategoryItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.AddonsItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.CustomPolicyItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.EditTourPackageItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.MediasItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.PricesItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.SchedulesItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.TourPackageItem;
import com.aniqma.lokaventour.host.model.item.uploadservice.UploadServiceItem;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.edittripprivatepackage.adapter.AddOnsAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.edittripprivatepackage.adapter.AddPriceAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.edittripprivatepackage.adapter.AddPolicesAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.edittripprivatepackage.adapter.AddListImageAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.edittripprivatepackage.adapter.AddSchedulesAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailprivate.PrivateTripPackageDetailActivity;
import com.aniqma.lokaventour.host.network.services.authentication.checktoken.IViewCheckToken;
import com.aniqma.lokaventour.host.network.services.authentication.checktoken.RefreshTokenServices;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.detailtourpackage.detailtourpackagebytourid.DetailTourPackageByTourIdService;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.detailtourpackage.detailtourpackagebytourid.IViewDetailTourPackageByTourIdService;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.edittourpackage.EditTourPackageService;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.edittourpackage.IViewEditTourPackageService;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.listTourPackage.tourpackagecategories.IViewTourPackageCategoriesService;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.listTourPackage.tourpackagecategories.TourPackageCategoriesService;
import com.aniqma.lokaventour.host.network.services.uploadservice.uploadservice.iViewUpload;
import com.aniqma.lokaventour.host.network.services.uploadservice.uploadservice.UploadServices;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class EditTripPackageActivity extends AppCompatActivity implements View.OnClickListener,
        IViewCheckToken, IViewEditTourPackageService, iViewUpload,
        IViewDetailTourPackageByTourIdService, IViewTourPackageCategoriesService, AddOnsAdapter.CallbackInterface, AddonsImagesAdapter.CallbackInterface{

    private LinearLayoutManager mLayoutManager, layoutManager;
    private ImageView ivHelpAdditionalKids;
    private Switch switchRefund;
    private AutoCompleteTextView autoLocation;
    String[] suggestionsTags = new String[]{"Unique", "Bandung", "Kuliner", "Culture"};
    private ArrayList<SchedulesItem> mModelListSchedulesEdit;
    private ArrayList<AddonsItem> mModelListAddOnsEdit = new ArrayList<>();
    private List<MediasItem> mModelListImage;
    private List<MediasItem> mModelListVideo;
    private ArrayList<CustomPolicyItem> customPolicesItems;
    private AddPolicesAdapter addPolicesAdapter;
    private AddSchedulesAdapter addSchedulesAdapter;
    private AddPriceAdapter addPriceAdapter;
    private ArrayList<PricesItem> mListPrices;
    private int numberPrice = 1;
    private AddOnsAdapter addOnsAdapter;
    private boolean isRefund = false;
    private UploadServiceItem dataMedia;
    private AddListImageAdapter mAdapterListImage;
    private AddListVideoAdapter mAdapterListVideo;
    private List<MediasItem> medias = new ArrayList<>();
    private GridLayoutManager managerListImage;
    private GridLayoutManager managerListVideo;
    private LinearLayout llKidsAdditionalPrice;

    private IViewEditTourPackageService iViewEditTourPackageService;
    private iViewUpload iViewUpload;
    private IViewDetailTourPackageByTourIdService iViewDetailTour;
    private IViewTourPackageCategoriesService iViewTourPackageCategoriesService;
    private EditTourPackageService presenter;
    private DetailTourPackageByTourIdService detailPrivateTourPresenter;
    private TourPackageCategoriesService tourPackageCategoriesService;
    private String tour_id = "";
    private TourPackageItem tourPackageItem;
    private ArrayAdapter<String> adapterTags;
    private ArrayAdapter<String> adapterCategory;
    private String[] suggestionsCategory = new String[]{};
    String dataCategory = "";
    private View compAppbar, compTitleTourInfo, compTitlePrice, compTitleTos, compTitleInsurance,
            compTitlePolicies, compTitleCustomPolicy, compTitleImages, compTitleSchedules, compTitleAddon, compTitleImage;
    private NachoTextView etCategory;
    private NachoTextView etTags;
    private ImageView ivBack, ivCheck;
    private TextView tvTitlePage, tvTitleTourInfo, tvDesTourInfo,
            tvTitlePrice, tvDesprice,
            tvTitleTos, tvDesTos,
            tvRemovePrice, tvAddMorePrice, tvAddMorePolicy, tvAddMoreSchedules, tvAddMoreAddOns,
            tvTitlePolicy, tvDesPolicy, tvTitleInsurance, tvDescriptionInsurance,
            tvTitleCustomPolicy, tvDesCustomPolicy,
            tvTitleImage, tvDesImage,
            tvTitleSchedules, tvDesSchedules,
            tvTitleAddon, tvDesAddon, etAddIndividuPrice,
            tvTitleVideo, tvDescriptionVideo;
    private TextInputEditText etTitle, etDuration, etAbout, etItinerary, etAgeRestriction, etAddcCostForeign, etTermOfService,
            etTitleInsurance, etDescInsurance, etAddIndividuPriceKids,
            etAddIndividuPriceAdult;;
    private TextInputLayout tilTitle, tilLocation, tilAboutTheTour, tilCategories, tilItinerary, tilTos, tilAddIndividuPriceAdult,
            tilAddIndividuPriceKids;;
    private RecyclerView rvPrice, rvCustomPolicy, rvListImage, rvSchedules, rvAddons, rvVideo;
    private List<PricesItem> prices = new ArrayList<>();
    private LoadingPage loading;
    private int PICK_IMAGES = 1;
    private LokavenDialog lokavenDialog;
    private TooltipWindowUtils tipWindow;

    public ArrayList<String> filter_category;
    public ArrayList<String> filter_tags;
    public ArrayList<String> filter_type_trip;

    public String keyword;
    public String filter_place;
    public Float filter_rating;
    public String filter_featured;
    public String sort_price;
    public String sort_rating;
    public String sort_date;
    public int filter_price_low;
    public int filter_price_max;
    private String from = "";
    private int position = 0;
    private TextInputEditText etUrlVideo;
    private TextInputLayout tilUrlVideo;
    private LinearLayout llAddVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip_private_package);
        iViewEditTourPackageService = this;
        tipWindow = new TooltipWindowUtils(this);
        iViewUpload = this;
        iViewDetailTour = this;
        loading = new LoadingPage(this);
        lokavenDialog = new LokavenDialog(this);
        presenter = new EditTourPackageService(this, iViewEditTourPackageService);
        Init();
        InitBaru();

        try {
            tour_id = getIntent().getExtras().getString("tour_id");
            keyword = getIntent().getExtras().getString("keyword");
            filter_price_low = getIntent().getExtras().getInt("filter_price_low");
            filter_price_max = getIntent().getExtras().getInt("filter_price_max");
            filter_place = getIntent().getExtras().getString("filter_place");
            filter_category = getIntent().getExtras().getStringArrayList("filter_category");
            filter_type_trip = getIntent().getExtras().getStringArrayList("filter_trip_type");
            filter_tags = getIntent().getExtras().getStringArrayList("filter_tags");
            filter_rating = getIntent().getExtras().getFloat("filter_rating");
            filter_featured = getIntent().getExtras().getString("filter_featured");
            sort_price = getIntent().getExtras().getString("sort_price");
            sort_date = getIntent().getExtras().getString("sort_date");
            sort_rating = getIntent().getExtras().getString("sort_rating");
        } catch (Exception e) {}

        detailPrivateTourPresenter = new DetailTourPackageByTourIdService(iViewDetailTour, this);
        detailPrivateTourPresenter.onSuccessGetDetail(this, tour_id);
        loading.start();
        ListTags();

        rvListImage.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(12,12,12,12);
            }
        });

        etAddIndividuPriceAdult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()){
                    llKidsAdditionalPrice.setVisibility(View.VISIBLE);
                    if (addPriceAdapter.getDataPrices() != null) {
                        if (addPriceAdapter.getDataPrices().size() != 0){
                            if (!addPriceAdapter.getDataPrices().get(0).getKidPrice().isEmpty()) {
                                if (etAddIndividuPriceKids.getText().toString().isEmpty()) {
                                    tilAddIndividuPriceKids.setError(getString(R.string.text_this_price_needs_to_be_set_if_you_set_kids_price_in_normal_pricing));
                                } else {
                                    tilAddIndividuPriceKids.setError(null);
                                }
                            }
                        }
                    }
                } else {
                    tilAddIndividuPriceKids.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ListAddons();
        ListImage();
        ListSchedule();
        ListCustomPolicy();
        ListPrice();

        String[] countriesList = getResources().getStringArray(R.array.location_array);
        ArrayAdapter countryAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, countriesList);
        autoLocation.setAdapter(countryAdapter);
        tvTitleVideo.setText(getResources().getString(R.string.text_video));
        tvDescriptionVideo.setText(getResources().getString(R.string.text_you_can_embed_videos_from_youtube));

        ivCheck.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvAddMorePrice.setOnClickListener(this);
        tvAddMoreSchedules.setOnClickListener(this);
        tvAddMoreAddOns.setOnClickListener(this);
        tvAddMorePolicy.setOnClickListener(this);
        ivHelpAdditionalKids.setOnClickListener(this);
        llAddVideo.setOnClickListener(this);
    }

    private void Init() {
        switchRefund = findViewById(R.id.switchRefund);
        autoLocation = findViewById(R.id.autoLocation);
        etTitle = findViewById(R.id.etTitle);
        etDuration = findViewById(R.id.etDuration);
        etAbout = findViewById(R.id.etAbout);
        etItinerary = findViewById(R.id.etItinerary);
        etCategory = findViewById(R.id.etCategory);
        etTags = findViewById(R.id.etTags);
        etTermOfService = findViewById(R.id.etTermOfService);
        etTitleInsurance = findViewById(R.id.etTitleInsurance);
        etDescInsurance = findViewById(R.id.etDescInsurance);
        etAgeRestriction = findViewById(R.id.etAgeRestriction);
        mListPrices = new ArrayList<>();
        mModelListSchedulesEdit = new ArrayList<>();
        mModelListAddOnsEdit = new ArrayList<>();
        customPolicesItems = new ArrayList<>();
        mModelListImage = new ArrayList<>();
        mModelListVideo = new ArrayList<>();
        adapterTags = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggestionsTags);
        adapterCategory = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, suggestionsCategory);
    }

    private void InitBaru(){
        etAddIndividuPrice = findViewById(R.id.etAddIndividuPriceAdult);
        tvAddMorePrice = findViewById(R.id.tvAddMorePrice);
        tvRemovePrice = findViewById(R.id.tvRemovePrice);
        tvAddMorePolicy = findViewById(R.id.tvAddMorePolicy);
        tvAddMoreSchedules = findViewById(R.id.tvAddMoreSchedules);
        tvAddMoreAddOns = findViewById(R.id.tvAddMoreAddOns);
        rvPrice = findViewById(R.id.rvPrice);
        rvCustomPolicy = findViewById(R.id.rvCustomPolicy);
        rvListImage = findViewById(R.id.rvListImage);
        rvSchedules = findViewById(R.id.rvSchedules);
        rvAddons = findViewById(R.id.rvAddons);
        etAddcCostForeign = findViewById(R.id.etAddcCostForeign);

        compTitleAddon = findViewById(R.id.compTitleAddon);
        tvTitleAddon = compTitleAddon.findViewById(R.id.tvTitle);
        tvTitleAddon.setText(getString(R.string.text_addons));
        tvDesAddon = compTitleAddon.findViewById(R.id.tvDescription);
        tvDesAddon.setText(getString(R.string.text_set_addons_desc));

        compTitleSchedules = findViewById(R.id.compTitleSchedules);
        tvTitleSchedules = compTitleSchedules.findViewById(R.id.tvTitle);
        tvTitleSchedules.setText(getString(R.string.text_active_schedules));
        tvDesSchedules = compTitleSchedules.findViewById(R.id.tvDescription);
        tvDesSchedules.setText(getString(R.string.text_you_can_have_more_schedule));

        compTitleImages = findViewById(R.id.compTitleImages);
        tvTitleImage = compTitleImages.findViewById(R.id.tvTitle);
        tvTitleImage.setText(getString(R.string.text_images));
        tvDesImage = compTitleImages.findViewById(R.id.tvDescription);
        tvDesImage.setText(getString(R.string.text_please_choose_images));

        compTitleCustomPolicy = findViewById(R.id.compTitleCustomPolicy);
        tvTitleCustomPolicy = compTitleCustomPolicy.findViewById(R.id.tvTitle);
        tvTitleCustomPolicy.setText(getString(R.string.text_custom_policy));
        tvDesCustomPolicy = compTitleCustomPolicy.findViewById(R.id.tvDescription);
        tvDesCustomPolicy.setText(getString(R.string.text_custom_policy_desc));

        compTitlePolicies = findViewById(R.id.compTitlePolicies);
        tvTitlePolicy = compTitlePolicies.findViewById(R.id.tvTitle);
        tvTitlePolicy.setText(getString(R.string.text_policies));
        tvDesPolicy = compTitlePolicies.findViewById(R.id.tvDescription);
        tvDesPolicy.setText(getString(R.string.text_policies_desc));

        compTitleInsurance = findViewById(R.id.compTitleInsurance);
        tvTitleInsurance = compTitleInsurance.findViewById(R.id.tvTitle);
        tvTitleInsurance.setText(getResources().getString(R.string.text_insurance));
        tvDescriptionInsurance = compTitleInsurance.findViewById(R.id.tvDescription);
        tvDescriptionInsurance.setText(getResources().getString(R.string.text_if_you_have_any_policies_related_to_insurance));

        compAppbar = findViewById(R.id.compAppbar);
        ivCheck = compAppbar.findViewById(R.id.ivPlus);
        ivCheck.setImageResource(R.drawable.icon_18dp_check_dark);
        ivBack = compAppbar.findViewById(R.id.ivBack);
        tvTitlePage = compAppbar.findViewById(R.id.tvTitle);
        tvTitlePage.setText(getString(R.string.text_edit_private_trip));

        compTitleTourInfo = findViewById(R.id.compTitleTourInfo);
        tvTitleTourInfo = compTitleTourInfo.findViewById(R.id.tvTitle);
        tvTitleTourInfo.setText(getString(R.string.text_tour_info));
        tvDesTourInfo = compTitleTourInfo.findViewById(R.id.tvDescription);
        tvDesTourInfo.setText(getString(R.string.text_basic_information));

        compTitlePrice = findViewById(R.id.compTitlePrice);
        tvTitlePrice = compTitlePrice.findViewById(R.id.tvTitle);
        tvTitlePrice.setText(getString(R.string.text_tour_price));
        tvDesprice = compTitlePrice.findViewById(R.id.tvDescription);
        tvDesprice.setText(getString(R.string.text_set_your_price));

        compTitleTos = findViewById(R.id.compTitleTos);
        tvTitleTos = compTitleTos.findViewById(R.id.tvTitle);
        tvTitleTos.setText(getString(R.string.text_term_of_service));
        tvDesTos = compTitleTos.findViewById(R.id.tvDescription);
        tvDesTos.setText(getString(R.string.text_please_write_term_of_service));

        tilTitle = findViewById(R.id.tilTitle);
        tilLocation = findViewById(R.id.tilLocation);
        tilAboutTheTour = findViewById(R.id.tilAboutTheTour);
        tilCategories = findViewById(R.id.tilCategories);
        tilItinerary = findViewById(R.id.tilItinerary);
        tilTos = findViewById(R.id.tilTos);
        tilAddIndividuPriceKids = findViewById(R.id.tilAddIndividuPriceKids);
        tilAddIndividuPriceAdult = findViewById(R.id.tilAddIndividuPriceAdult);

        etAddIndividuPriceAdult = findViewById(R.id.etAddIndividuPriceAdult);
        etAddIndividuPriceKids = findViewById(R.id.etAddIndividuPriceKids);

        llKidsAdditionalPrice = findViewById(R.id.llKidsAdditionalPrice);
        ivHelpAdditionalKids = findViewById(R.id.ivHelpAdditionalKids);

        compTitleImage = findViewById(R.id.compTitleImage);
        tvTitleVideo = compTitleImage.findViewById(R.id.tvTitle);
        tvDescriptionVideo = compTitleImage.findViewById(R.id.tvDescription);
        rvVideo = findViewById(R.id.rvVideo);
        etUrlVideo = findViewById(R.id.etUrlVideo);
        llAddVideo = findViewById(R.id.llAddVideo);
        tilUrlVideo = findViewById(R.id.tilUrlVideo);
    }

    private void ListTags() {
        etTags.setAdapter(adapterTags);
    }

    private void ListImage() {
        rvListImage.setHasFixedSize(true);
        managerListImage = new GridLayoutManager(this, 3);
        rvListImage.setLayoutManager(managerListImage);
        mAdapterListImage = new AddListImageAdapter(this, mModelListImage);
        rvListImage.setAdapter(mAdapterListImage);

        rvVideo.setHasFixedSize(true);
        managerListVideo = new GridLayoutManager(this, 3);
        rvVideo.setLayoutManager(managerListVideo);
        mAdapterListVideo = new AddListVideoAdapter(this, mModelListVideo);
        rvVideo.setAdapter(mAdapterListVideo);
    }

    private void ListCustomPolicy() {
        rvCustomPolicy.setHasFixedSize(true);
        rvCustomPolicy.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(this);
        rvCustomPolicy.setLayoutManager(mLayoutManager);
        addPolicesAdapter = new AddPolicesAdapter(this, customPolicesItems);
        rvCustomPolicy.setAdapter(addPolicesAdapter);
    }

    private void ListAddons() {
        rvAddons.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        rvAddons.setLayoutManager(mLayoutManager);
        addOnsAdapter = new AddOnsAdapter(this, mModelListAddOnsEdit);
        rvAddons.setAdapter(addOnsAdapter);
    }

    private void ListSchedule() {
        rvSchedules.setHasFixedSize(true);
        rvSchedules.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(this);
        rvSchedules.setLayoutManager(mLayoutManager);
        addSchedulesAdapter = new AddSchedulesAdapter(this, mModelListSchedulesEdit);
        rvSchedules.setAdapter(addSchedulesAdapter);
    }

    private void ListPrice() {
        rvPrice.setHasFixedSize(true);
        rvPrice.setNestedScrollingEnabled(false);
        layoutManager = new LinearLayoutManager(this);
        rvPrice.setLayoutManager(layoutManager);
        addPriceAdapter = new AddPriceAdapter(prices, this, llKidsAdditionalPrice, etAddIndividuPriceAdult, etAddIndividuPriceKids);
        rvPrice.setAdapter(addPriceAdapter);
    }

    private void addMoreCustomPolices(){
        customPolicesItems.add(new CustomPolicyItem("", ""));
        addPolicesAdapter.notifyDataSetChanged();
    }

    private void SetPrice(List<PricesItem> pc) {
        if (pc.size()!=0){
            for (int i = 0; i < pc.size(); i++) {
                prices.add(new PricesItem(pc.get(i).getMinParticipant(), pc.get(i).getMaxParticipant(), pc.get(i).getPrice(), pc.get(i).getKidPrice(), pc.get(i).getMinKidAge(), pc.get(i).getMaxKidAge()));
            }
            addPriceAdapter.notifyDataSetChanged();
        }
    }

    private void AddMorePrices() {
        prices.add(new PricesItem("", "", "", "", "", ""));
        addPriceAdapter.notifyDataSetChanged();
    }

    private void SetTags(String[] tags) {
        String sTag = "";
        if (tags.length !=0 ){
            for (int i = 0; i < tags.length; i++) {
                sTag = tags[i]+" "+sTag+" ";
            }
        }
        etTags.setText(sTag);
    }

    private void SetCategories(String[] categories) {
        String sCategory = "";
        if (categories.length !=0 ){
            for (int i = 0; i < categories.length; i++) {
                sCategory = categories[i]+';'+sCategory;
            }
        }

        etCategory.addChipTerminator(';', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_TO_TERMINATOR);
        etCategory.setText(sCategory);
    }

    private void SetMedias(List<MediasItem> medias) {
        for (int i = 0; i < medias.size(); i++) {
            if (medias.get(i).getType().trim().equals("image")){
                mModelListImage.add(new MediasItem(medias.get(i).getMediaId(), medias.get(i).getUrl(), medias.get(i).type, medias.get(i).getTourId()));
            } else {
                mModelListVideo.add(new MediasItem(medias.get(i).getMediaId(), medias.get(i).getUrl(), medias.get(i).type, medias.get(i).getTourId()));
            }
        }
        AddMoreMedias();
    }

    private void AddMoreMedias() {
        mModelListImage.add(new MediasItem("", "", "", ""));
        mAdapterListImage.notifyDataSetChanged();
        mAdapterListVideo.notifyDataSetChanged();
    }

    private void SetCustomPolicies(List<CustomPolicyItem> customPolicies) {
        if (customPolicies.size() != 0){
            for (int i = 0; i < customPolicies.size(); i++) {
                customPolicesItems.add(new CustomPolicyItem(customPolicies.get(i).getPolicyName(), customPolicies.get(i).getPolicy(), customPolicies.get(i).getTourId()));
            }
            addPolicesAdapter.notifyDataSetChanged();
        }
    }

    private void SetAddons(List<AddonsItem> addons) {
        if (addons.size() != 0){
            for (int i = 0; i < addons.size(); i++) {
                mModelListAddOnsEdit.add(new AddonsItem(
                        addons.get(i).getAddonId(),
                        addons.get(i).getTourId(),
                        addons.get(i).getAddon(),
                        addons.get(i).getPrice(),
                        addons.get(i).getPriceType(),
                        addons.get(i).getQty(),
                        addons.get(i).isRental(),
                        addons.get(i).getMediasItems()));
            }
            addOnsAdapter.notifyDataSetChanged();
        }
    }

    private void AddMoreAddons() {
        addOnsAdapter.addItem();
        rvAddons.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                rvAddons.smoothScrollToPosition(addOnsAdapter.getItemCount() - 1);
            }
        });
    }

    private void SetSchedules(List<SchedulesItem> schedules) {
        if (schedules.size() != 0){
            for (int i = 0; i < schedules.size(); i++) {
                mModelListSchedulesEdit.add(new SchedulesItem(
                        schedules.get(i).getScheduleId(),
                        schedules.get(i).getTourId(),
                        schedules.get(i).getDurations(),
                        schedules.get(i).getStartDate(),
                        schedules.get(i).getEndDate(),
                        schedules.get(i).getQuota(),
                        schedules.get(i).isActive(),
                        schedules.get(i).isBooked(),
                        schedules.get(i).getTourStatus()));
            }
            addSchedulesAdapter.notifyDataSetChanged();
        }
    }

    private void AddMoreSchedules() {
        mModelListSchedulesEdit.add(new SchedulesItem("", "", ""));
        addSchedulesAdapter.notifyDataSetChanged();
    }

    private void onEditTourPackage() {
        String category = etCategory.getText().toString();
        String[] arrCategory = category.split("\\s+");
        List<String> categoriesList = new ArrayList<>();
        for (int i = 1; i < arrCategory.length ;i++) {
            categoriesList.add(arrCategory[i].substring(1, arrCategory[i].length()-1));
        }

        // allocate memory for string array
        String[] categories = new String[categoriesList.size()];
        for (int i = 0; i < categories.length; i++)
            categories[i] = categoriesList.get(i);

        String sTags = etTags.getText().toString();
        String[] arrTags = sTags.split("\\s+");
        List<String> tagsList = new ArrayList<>();
        for (int i = 1; i < arrTags.length; i++) {
            tagsList.add(arrTags[i].substring(1, arrTags[i].length()-1));
        }

        // allocate memory for string array
        String[] tags = new String[tagsList.size()];
        for (int i = 0; i < tags.length; i++)
            tags[i] = tagsList.get(i);

        if (switchRefund.isChecked()){
            isRefund = true;
        } else {
            isRefund = false;
        }

        List<MediasItem> mediasItems = new ArrayList<>();

        for (int i = 0; i < mModelListImage.size(); i++) {
            if (!mModelListImage.get(i).getUrl().equals("")||!mModelListImage.get(i).getType().equals("")){
                mediasItems.add(new MediasItem(mModelListImage.get(i).getMediaId(), mModelListImage.get(i).getUrl(), mModelListImage.get(i).getType(), mModelListImage.get(i).getTourId()));
            }
        }

        for (int i = 0; i < mModelListVideo.size(); i++) {
            if (!mModelListVideo.get(i).getUrl().equals("")||!mModelListVideo.get(i).getType().equals("")){
                mediasItems.add(new MediasItem(mModelListVideo.get(i).getMediaId(), mModelListVideo.get(i).getUrl(), mModelListVideo.get(i).getType(), mModelListVideo.get(i).getTourId()));
            }
        }

        // filtering custom polices
        int i=0;
        while (i<customPolicesItems.size()) {
            if (customPolicesItems.get(i).getPolicy().isEmpty() && customPolicesItems.get(i).getPolicyName().isEmpty()){
                customPolicesItems.remove(i);
            } else {
                i++;
            }
        }

        List<AddonsItem> addonsItems = new ArrayList<>();
        addonsItems.addAll(addOnsAdapter.getDataAddons());
        for (int l = 0; l < addonsItems.size(); l++){
            if (addonsItems.get(i).getMediasItems() != null){
                if (addonsItems.get(i).getMediasItems().size() != 0){
                    addonsItems.get(i).getMediasItems().remove(addonsItems.get(i).getMediasItems().size() - 1);
                }
            }

        }

        AdditionalCostItem additionalCost = new AdditionalCostItem();
        additionalCost.setAdditional_adult_price(etAddIndividuPriceAdult.getText().toString().trim());
        additionalCost.setAdditional_kid_price(etAddIndividuPriceKids.getText().toString().trim());

        InsuranceItem insuranceItem = new InsuranceItem();
        insuranceItem.setTitle(etTitleInsurance.getText().toString());
        insuranceItem.setDescription(etDescInsurance.getText().toString());

        if (validationEdit(categoriesList, mediasItems, tagsList, addOnsAdapter.getDataAddons(), isRefund, addPriceAdapter.getDataPrices(), customPolicesItems, tourPackageItem.getAdditionalCost())){
            EditTourPackageItem data = new EditTourPackageItem(
                    "close",
                    etTitle.getText().toString(),
                    mediasItems,
                    etAbout.getText().toString(),
                    categories,
                    tags,
                    autoLocation.getText().toString(),
                    addonsItems,
                    true,
                    true,
                    isRefund,
                    addPriceAdapter.getDataPrices(),
                    etTermOfService.getText().toString(),
                    insuranceItem,
                    customPolicesItems,
                    etAgeRestriction.getText().toString(),
                    etAddcCostForeign.getText().toString(),
                    etItinerary.getText().toString(),
                    additionalCost);
            presenter = new EditTourPackageService(this, iViewEditTourPackageService);
            presenter.onEditPrivateTourPackage(tour_id, data);
        } else {
            loading.stop();
            Intent intent = new Intent(this, PrivateTripPackageDetailActivity.class);
            intent.putExtra("tour_id", tourPackageItem.getTourId());
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }

    private boolean validationEdit(List<String> categoriesList, List<MediasItem> mediasItems, List<String> tagsList, List<AddonsItem> dataAddons, boolean isRefund, List<PricesItem> dataPrices, ArrayList<CustomPolicyItem> customPolicesItems, AdditionalCostItem additionalCost) {
        boolean valid = false;

        if (!etItinerary.getText().toString().equals(tourPackageItem.getItinerary())){
           valid  = true;
        }

        if (!etAddcCostForeign.getText().toString().equals(tourPackageItem.getCostForeignGuest())){
            valid = true;
        }

        if (!etAgeRestriction.getText().toString().equals(tourPackageItem.getAgeRestriction())){
            valid = true;
        }

        if (!etTermOfService.getText().toString().equals(tourPackageItem.getTermOfService())){
            valid = true;
        }

        if (!autoLocation.getText().toString().equals(tourPackageItem.getLocation())){
            valid = true;
        }

        if (!etAbout.getText().toString().equals(tourPackageItem.getDescription())){
            valid = true;
        }

        if (!etTitle.getText().toString().equals(tourPackageItem.getTitle())){
            valid = true;
        }

        if (isRefund != tourPackageItem.isRefundable()){
            valid = true;
        }

        if (categoriesList.size() != 0){
            if (categoriesList.size() != tourPackageItem.getCategories().length){
                valid = true;
            } else {
                for (int i = 0; i < categoriesList.size(); i++){
                    if (!categoriesList.get(i).equals(tourPackageItem.getCategories()[i])){
                        valid = true;
                        break;
                    }
                }
            }
        }

        if (tagsList.size() != 0){
            if (tagsList.size() != tourPackageItem.getTags().length){
                valid = true;
            } else {
                for (int i = 0; i < tagsList.size(); i++){
                    if (!tagsList.get(i).equals(tourPackageItem.getTags()[i])){
                        valid = true;
                        break;
                    }
                }
            }
        }

        if (mediasItems.size() != 0){
            if (mediasItems.size() != tourPackageItem.getMedias().size()){
                valid = true;
            } else {
                for (int i = 0; i < mediasItems.size(); i++){
                    if (!mediasItems.get(i).getUrl().equals(tourPackageItem.getMedias().get(i).getUrl())){
                        valid = true;
                        break;
                    }
                }
            }
        }

        if (dataAddons.size() != 0){
            if (dataAddons.size() != tourPackageItem.getAddons().size()){
                valid = true;
            } else {
                for (int i = 0; i < dataAddons.size(); i++){
                    if (!dataAddons.get(i).getPrice().equals(tourPackageItem.getAddons().get(i).getPrice())){
                        valid = true;
                        break;
                    }

                    if (!dataAddons.get(i).getAddon().equals(tourPackageItem.getAddons().get(i).getAddon())){
                        valid = true;
                        break;
                    }

                    if (dataAddons.get(i).getQty() != tourPackageItem.getAddons().get(i).getQty()){
                        valid = true;
                        break;
                    }

                    if (dataAddons.get(i).getMediasItems().size() != tourPackageItem.getAddons().get(i).getMediasItems().size()){
                        valid = true;
                        break;
                    } else {
                        for (int j = 0; j < dataAddons.get(i).getMediasItems().size(); j++){
                            if (!dataAddons.get(i).getMediasItems().get(j).getImage_url().equals(tourPackageItem.getAddons().get(i).getMediasItems().get(j).getImage_url())){
                                valid = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (dataPrices.size() != 0){
            if (dataPrices.size() != tourPackageItem.getPrices().size()){
                valid = true;
            } else {
                for (int i = 0; i < dataPrices.size(); i++){
                    if (!dataPrices.get(i).getPrice().equals(tourPackageItem.getPrices().get(i).getPrice())){
                        valid = true;
                        break;
                    }

                    if (!dataPrices.get(i).getKidPrice().equals(tourPackageItem.getPrices().get(i).getKidPrice())){
                        valid = true;
                        break;
                    }

                    if (!dataPrices.get(i).getMaxParticipant().equals(tourPackageItem.getPrices().get(i).getMaxParticipant())){
                        valid = true;
                        break;
                    }

                    if (!dataPrices.get(i).getMinParticipant().equals(tourPackageItem.getPrices().get(i).getMinParticipant())){
                        valid = true;
                        break;
                    }

                    if (!dataPrices.get(i).getMaxKidAge().equals(tourPackageItem.getPrices().get(i).getMaxKidAge())){
                        valid = true;
                        break;
                    }

                    if (!dataPrices.get(i).getMinKidAge().equals(tourPackageItem.getPrices().get(i).getMinKidAge())){
                        valid = true;
                        break;
                    }
                }
            }
        }

        if (customPolicesItems.size() != 0){
            if (customPolicesItems.size() != tourPackageItem.getCustomePolicies().size()){
                valid = true;
            } else {
                for (int i = 0; i < customPolicesItems.size(); i++){
                    if (!customPolicesItems.get(i).getPolicyName().equals(tourPackageItem.getCustomePolicies().get(i).getPolicyName())){
                        valid = true;
                        break;
                    }

                    if (!customPolicesItems.get(i).getPolicy().equals(tourPackageItem.getCustomePolicies().get(i).getPolicy())){
                        valid = true;
                        break;
                    }
                }
            }
        }

        if (!etAddIndividuPriceAdult.getText().toString().equals(additionalCost.getAdditional_adult_price())){
            valid = true;
        }

        if (!etAddIndividuPriceKids.getText().toString().equals(additionalCost.getAdditional_kid_price())){
            valid = true;
        }

        return valid;
    }

    @Override
    public void onSuccessEditTourPackage(TourPackageItem tourPackageItem) {
        loading.stop();
        Intent i = new Intent(this, PrivateTripPackageDetailActivity.class);
        i.putExtra("tour_id", tourPackageItem.getTourId());
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
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void onErrorEditTour(String title, String message, int responseCode) {
        if (responseCode == 401) {
            onCheckToken();
        } else {
            loading.stop();
            lokavenDialog.dialogError(title, message);
        }
    }

    /**
     * After get category
     *
     * @param data*/
    @Override
    public void onSuccessGetCategory(List<DataTourCategoryItem> data) {
        loading.stop();
        // Multiple select categorygetDescription
        ArrayList<String>  mStringList= new ArrayList<String>();
        for (int i=0; i<data.size(); i++) {
            mStringList.add(data.get(i).getCategory());
            suggestionsCategory = new String[mStringList.size()];
            suggestionsCategory = mStringList.toArray(suggestionsCategory);
        }

        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggestionsCategory);
        etCategory.setAdapter(adapterCategory);
        etCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etCategory.showDropDown();
            }
        });

    }

    @Override
    public void onErrorGetCategory(String title, String message) {
        loading.stop();
        lokavenDialog.dialogError(title, message);
    }

    /**
     * After upload
     *
     * @param category
     * @param file
     * @param uploadServiceItem */
    @Override
    public void onSuccessUpload(UploadServiceItem uploadServiceItem, String category, File file) {
        loading.stop();
        if (from.trim().equals("image")){
            dataMedia = uploadServiceItem;
            mModelListImage.add(mModelListImage.size() - 1 ,new MediasItem("", uploadServiceItem.getUrl(), "image", ""));
            mAdapterListImage.notifyDataSetChanged();
        } else {
            MediasItem mediasItem = new MediasItem();
            mediasItem.setImage_url(uploadServiceItem.getUrl());
            mModelListAddOnsEdit.get(position).getMediasItems().add(mediasItem);
            mModelListAddOnsEdit.get(position).getMediasItems().add(new MediasItem());
            addOnsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onErrorData(String title, String message, int responseCode) {
        loading.stop();
        lokavenDialog.dialogError(title, message);
    }

    @Override
    public void onGetDetail(TourPackageItem getData) throws ParseException {
        tourPackageItem = getData;
        etTitle.setText(getData.getTitle());
        etAbout.setText(getData.getDescription());
        if (!getData.getAdditionalCost().getAdditional_adult_price().equals("0")){
            etAddIndividuPriceAdult.setText(getData.getAdditionalCost().getAdditional_adult_price());
        }

        if (!getData.getAdditionalCost().getAdditional_kid_price().equals("0")){
            tilAddIndividuPriceKids.setError(null);
            etAddIndividuPriceKids.setText(getData.getAdditionalCost().getAdditional_kid_price());
        }

        if (getData.getSchedules() != null){
            String str = getData.getSchedules().get(0).getDurations();
            str = str.replace(" days","");
            etDuration.setText(str);
        }

        if (!etAddIndividuPriceAdult.getText().toString().isEmpty()){
            llKidsAdditionalPrice.setVisibility(View.VISIBLE);
        } else {
            if (!tourPackageItem.getPrices().get(0).getKidPrice().isEmpty()){
                llKidsAdditionalPrice.setVisibility(View.VISIBLE);;
            } else {
                llKidsAdditionalPrice.setVisibility(View.GONE);
                etAddIndividuPriceKids.setText("");
            }
        }

        autoLocation.setText(getData.getLocation());
        etTermOfService.setText(getData.getTermOfService());
        etTitleInsurance.setText(getData.getInsuranceItem().getTitle());
        etDescInsurance.setText(getData.getInsuranceItem().getDescription());
        switchRefund.setChecked(getData.isRefundable());
        etAgeRestriction.setText(getData.getAgeRestriction());
        etAddcCostForeign.setText(getData.getCostForeignGuest());
        etItinerary.setText(getData.getItinerary());
        SetSchedules(getData.getSchedules());
        SetAddons(getData.getAddons());
        SetCustomPolicies(getData.getCustomePolicies());
        SetMedias(getData.getMedias());
        SetCategories(getData.getCategories());
        SetTags(getData.getTags());
        SetPrice(getData.getPrices());

        iViewTourPackageCategoriesService = this;
        tourPackageCategoriesService = new TourPackageCategoriesService(this, iViewTourPackageCategoriesService);
        tourPackageCategoriesService.onGetAllCategories();
    }

    @Override
    public void onError(String title, String message) {
        loading.stop();
        lokavenDialog.dialogError(title, message);
    }

    public void pickImage(){
        this.from = "image";
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
            return;
        }
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGES);
    }

    private void onCheckToken(){
        IViewCheckToken IViewCheckToken = this;
        RefreshTokenServices services = new RefreshTokenServices(this, IViewCheckToken);
        services.onInstrospectToken();
    }

    @Override
    public void isToken(boolean success) {
        onEditTourPackage();
    }

    @Override
    public void errorToken(String title, String message) {
        lokavenDialog.dialogError(title, message);
    }

    public boolean validation(){
        if (etTitle.getText().toString().isEmpty()){
            tilTitle.setError(getString(R.string.text_please_input_title));
            tilTitle.requestFocus();
            return true;
        } else {
            tilTitle.setError(null);
        }

        if(autoLocation.getText().toString().isEmpty()) {
            tilLocation.setError(getString(R.string.text_please_input_location));
            tilLocation.requestFocus();
            return true;
        } else {
            tilLocation.setError(null);
        }

//        if(etDuration.getText().toString().isEmpty()) {
//            etDuration.setError(getString(R.string.text_please_input));
//            return true;
//        }

        if(etAbout.getText().toString().isEmpty()) {
            tilAboutTheTour.setError(getString(R.string.text_please_input_about_the_tour));
            tilAboutTheTour.requestFocus();
            return true;
        } else {
            tilAboutTheTour.setError(null);
        }

        if(etCategory.getChipTextSize() == 0) {
            tilCategories.setError(getString(R.string.text_please_input_categories));
            tilCategories.requestFocus();
            return true;
        } else {
            tilCategories.setError(null);
        }

        if(etItinerary.getText().toString().isEmpty()) {
            tilItinerary.setError(getString(R.string.text_please_input_itinerary));
            tilItinerary.requestFocus();
            return true;
        } else {
            tilItinerary.setError(null);
        }

        if(addPriceAdapter.getItemCount() == 0) {
            lokavenDialog.getToast(getString(R.string.text_please_enter_the_complete_form_price));
            return true;
        } else {
            for (int i = 0; i < addPriceAdapter.getItemCount(); i++){
                if (addPriceAdapter.getDataPrices().get(i).getMinParticipant().equals("")){
                    lokavenDialog.getToast(getString(R.string.text_please_input_min_participants));
                    return true;
                }

                if (addPriceAdapter.getDataPrices().get(i).getMaxParticipant().equals("")){
                    lokavenDialog.getToast(getString(R.string.text_please_input_max_participants));
                    return true;
                }


                if (addPriceAdapter.getDataPrices().get(i).getPrice().equals("")){
                    lokavenDialog.getToast(getString(R.string.text_please_input_price));
                    return true;
                }
            }
        }

        if(etTermOfService.getText().toString().isEmpty()) {
            tilTos.setError(getString(R.string.text_please_input_term_of_service));
            tilTos.requestFocus();
            return true;
        } else {
            tilTos.setError(null);
        }

//        if (addSchedulesAdapter.getItemCount() == 0){
//            dialogHelper.getToast(getString(R.string.text_please_input_schedules));
//            return true;
//        } else {
//            for (int i = 0; i < addSchedulesAdapter.getItemCount(); i++){
//                if (addSchedulesAdapter.getSchedules().get(i).getStartDate().equals("")){
//                    dialogHelper.getToast(getString(R.string.text_please_input_start_date));
//                    return true;
//                }
//
//                if (addSchedulesAdapter.getSchedules().get(i).getEndDate().equals("")){
//                    dialogHelper.getToast(getString(R.string.text_please_input_end_date));
//                    return true;
//                }
//            }
//        }

        if (addOnsAdapter.getItemCount() > 0){
            int i = 0;
            while (i < addOnsAdapter.getItemCount()){
                if (addOnsAdapter.getDataAddons().get(i).getAddon().isEmpty() && addOnsAdapter.getDataAddons().get(i).getPrice().isEmpty()){
                    addOnsAdapter.getDataAddons().remove(i);
                } else {
                    i++;
                }
            }
        }

        if (addOnsAdapter.getItemCount() > 0){
            for (int i = 0; i < addOnsAdapter.getItemCount(); i++){
                if (addOnsAdapter.getDataAddons().get(i).getPriceType().equals("user")) {
                    if (!addOnsAdapter.getDataAddons().get(i).getAddon().isEmpty() && addOnsAdapter.getDataAddons().get(i).getPrice().isEmpty()){
                        lokavenDialog.getToast(getString(R.string.text_please_input_price_greater_than_0));
                        return true;
                    }
                } else {
                    if (!addOnsAdapter.getDataAddons().get(i).getAddon().isEmpty() && addOnsAdapter.getDataAddons().get(i).getPrice().isEmpty()){
                        lokavenDialog.getToast(getString(R.string.text_please_input_price_item_greater_than_0));
                        return true;
                    }

                    if (!addOnsAdapter.getDataAddons().get(i).getAddon().isEmpty() && addOnsAdapter.getDataAddons().get(i).getQty() <= 0){
                        lokavenDialog.getToast(getString(R.string.text_please_input_item_amount_greater_than_0));
                        return true;
                    }
                }

                if (addOnsAdapter.getDataAddons().get(i).getAddon().isEmpty() && !addOnsAdapter.getDataAddons().get(i).getPrice().isEmpty()){
                    lokavenDialog.getToast(getString(R.string.text_please_input_addons_name));
                    return true;
                }

            }
        }

        if (mModelListImage.size() <= 1){
            lokavenDialog.getToast(getString(R.string.text_please_choose_images));
            return true;
        }

        if (!etAddIndividuPriceAdult.getText().toString().isEmpty()) {
            llKidsAdditionalPrice.setVisibility(View.VISIBLE);

            if (addPriceAdapter.getDataPrices() != null) {
                if (!addPriceAdapter.getDataPrices().get(0).getKidPrice().isEmpty() || !etAddIndividuPriceKids.getText().toString().isEmpty()){
                    if (etAddIndividuPriceKids.getText().toString().isEmpty()) {
                        tilAddIndividuPriceKids.setError(getString(R.string.text_this_price_needs_to_be_set_if_you_set_kids_price_in_normal_pricing));
                        return true;
                    } else {
                        tilAddIndividuPriceKids.setError(null);
                    }

                    for (int i = 0; i < addPriceAdapter.getDataPrices().size(); i++) {

                        if (addPriceAdapter.getDataPrices().get(i).getKidPrice() != null && !addPriceAdapter.getDataPrices().get(i).getKidPrice().equals("")) {
                            if (Double.valueOf(addPriceAdapter.getDataPrices().get(i).getKidPrice().trim()) < 9999) {
                                lokavenDialog.getToast(getString(R.string.text_please_input_kids_price_more_than_IDR_9999));
                                return true;
                            }
                        } else {
                            lokavenDialog.getToast(getString(R.string.text_please_input_kids_price_more_than_IDR_9999));
                            return true;
                        }

                        if (addPriceAdapter.getDataPrices().get(i).getMinKidAge() == null || addPriceAdapter.getDataPrices().get(i).getMinKidAge().trim().equals("")) {
                            lokavenDialog.getToast(getString(R.string.text_please_input_min_kids_age));
                            return true;
                        }


                        if (addPriceAdapter.getDataPrices().get(i).getMaxKidAge() == null || addPriceAdapter.getDataPrices().get(i).getMaxKidAge().trim().equals("")) {
                            lokavenDialog.getToast(getString(R.string.text_please_input_max_kids_age));
                            return true;
                        }

                        if ((addPriceAdapter.getDataPrices().get(i).getMaxKidAge() != null && !addPriceAdapter.getDataPrices().get(i).getMaxKidAge().isEmpty()) && (addPriceAdapter.getDataPrices().get(i).getMinKidAge() != null && !addPriceAdapter.getDataPrices().get(i).getMinKidAge().isEmpty())) {
                            if (Double.valueOf(addPriceAdapter.getDataPrices().get(i).getMaxKidAge()) < Integer.valueOf(addPriceAdapter.getDataPrices().get(i).getMinKidAge())) {
                                lokavenDialog.getToast(getString(R.string.text_please_input_kids_min_age_less_than_kids_max_age));
                                return true;
                            }
                        }
                    }
                }
            }
        }

        if (!etAddIndividuPriceKids.getText().toString().isEmpty() && etAddIndividuPriceAdult.getText().toString().isEmpty()){
            tilAddIndividuPriceAdult.setError(getString(R.string.text_please_input_additional_adult_price));
            return true;
        } else {
            tilAddIndividuPriceAdult.setError(null);
        }

        if (!etAddIndividuPriceKids.getText().toString().isEmpty()){
            if (Double.parseDouble(etAddIndividuPriceKids.getText().toString().trim()) < 9999){
                tilAddIndividuPriceKids.setError(getString(R.string.text_please_input_additional_kids_price_more_than_IDR_9999));
                return true;
            } else {
                tilAddIndividuPriceKids.setError(null);
            }
        }

        if (!etAddIndividuPriceAdult.getText().toString().isEmpty()){
            if (Double.parseDouble(etAddIndividuPriceAdult.getText().toString().trim()) < 9999){
                tilAddIndividuPriceAdult.setError(getString(R.string.text_please_input_additional_adult_price_more_than_IDR_9999));
                return true;
            } else {
                tilAddIndividuPriceAdult.setError(null);
            }
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            final ArrayList<String> urlImage = new ArrayList<>();
            ClipData clipData = data.getClipData();
            if (clipData != null){
                for (int i = 0; i < clipData.getItemCount() ; i++) {
                    Uri uri = clipData.getItemAt(i).getUri();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                        urlImage.add(RealPathHelper.getRealPathFromURI_API19(this, uri));
                    } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2 && Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
                        urlImage.add(RealPathHelper.getRealPathFromURI_API11to18(this, uri));
                    } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB){
                        urlImage.add(RealPathHelper.getRealPathFromURI_BelowAPI11(this, uri));
                    }
                }
            } else {
                Uri uri = data.getData();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                    urlImage.add(RealPathHelper.getRealPathFromURI_API19(this, uri));
                } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2 && Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
                    urlImage.add(RealPathHelper.getRealPathFromURI_API11to18(this, uri));
                } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB){
                    urlImage.add(RealPathHelper.getRealPathFromURI_BelowAPI11(this, uri));
                }
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (final String b : urlImage){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                CompressImageHelper compressImageHelper = new CompressImageHelper(EditTripPackageActivity.this);
                                UploadServices uploadService = new UploadServices(EditTripPackageActivity.this, iViewUpload);
                                if (urlImage != null || !urlImage.get(0).equals("")){
                                    uploadService.onUpload(compressImageHelper.compressImage(urlImage.get(0)), "image");
                                    loading.start();
                                }
                            }
                        });
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == tvAddMorePrice) {
            AddMorePrices();
        }else if (v == ivCheck){
            if (!validation()){
                loading.start();
                onEditTourPackage();
            }
        }else if (v == ivBack){
            onBackPressed();
        }else if (v == tvAddMoreSchedules){
            AddMoreSchedules();
        }else if (v == tvAddMoreAddOns){
            AddMoreAddons();
        }else if (v == tvAddMorePolicy){
            addMoreCustomPolices();
        }else if (v == ivHelpAdditionalKids){
            if (!tipWindow.isTooltipShown()){
                tipWindow.showToolTip(ivHelpAdditionalKids, getString(R.string.text_this_price_needs_to_be_set_if_you_set_kids_price_in_normal_pricing));
            } else {
                tipWindow.dismissTooltip();
            }
        }else if (v == llAddVideo){
            if (etUrlVideo.getText().toString().trim().isEmpty()){
                tilUrlVideo.setError(getResources().getString(R.string.text_please_input_url_video_from_youtube));
                tilUrlVideo.requestFocus();
            } else {
                tilUrlVideo.setError(null);
                MediasItem mediasItem = new MediasItem();
                mediasItem.setUrl(etUrlVideo.getText().toString().trim());
                mediasItem.setType("video");
                mModelListVideo.add(mediasItem);
                rvVideo.setVisibility(View.VISIBLE);
                mAdapterListVideo.notifyDataSetChanged();
                etUrlVideo.setText("");
            }
        }
    }

    @Override
    public void onHandleSelection(int position, String text) {
        this.from = "addons";
        this.position = position;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
            return;
        }
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGES);
    }

    @Override
    public void onHandleSelection(int position) {
        this.from = "addons";
        this.position = position;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
            return;
        }
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGES);
    }
}
