package com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.editopentrippackage;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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
import com.aniqma.lokaventour.host.utils.dialog.LokavenDialog;
import com.aniqma.lokaventour.host.utils.animation.LoadingPage;
import com.aniqma.lokaventour.host.model.item.schedulesmanagement.AddEditSchedulesItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.DataTourCategoryItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.AddonsItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.CustomPolicyItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.EditTourPackageItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.MediasItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.PricesItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.SchedulesItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.TourPackageItem;
import com.aniqma.lokaventour.host.model.item.uploadservice.UploadServiceItem;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.editopentrippackage.adapter.AddOnsAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.editopentrippackage.adapter.AddPolicesAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.editopentrippackage.adapter.AddListImageAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.editopentrippackage.adapter.AddSchedulesAdapter;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailopen.OpenTripPackageDetailActivity;
import com.aniqma.lokaventour.host.network.services.authentication.checktoken.IViewCheckToken;
import com.aniqma.lokaventour.host.network.services.authentication.checktoken.RefreshTokenServices;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.detailtourpackage.detailtourpackagebytourid.DetailTourPackageByTourIdService;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.detailtourpackage.detailtourpackagebytourid.IViewDetailTourPackageByTourIdService;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.edittourpackage.EditTourPackageService;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.edittourpackage.IViewEditTourPackageService;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.listTourPackage.tourpackagecategories.IViewTourPackageCategoriesService;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.listTourPackage.tourpackagecategories.TourPackageCategoriesService;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.schedulesmanagement.addschedules.AddSchedulesService;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.schedulesmanagement.addschedules.IViewAddSchedulesService;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.schedulesmanagement.deleteschedules.DeleteSchedulesService;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.schedulesmanagement.deleteschedules.IViewDeleteSchedulesService;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.schedulesmanagement.editschedules.EditSchedulesService;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.schedulesmanagement.editschedules.IViewEditSchedulesService;
import com.aniqma.lokaventour.host.network.services.uploadservice.uploadservice.iViewUpload;
import com.aniqma.lokaventour.host.network.services.uploadservice.uploadservice.UploadServices;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditOpenTripPackageActivity extends AppCompatActivity implements View.OnClickListener,
        IViewCheckToken, IViewEditTourPackageService, iViewUpload, IViewDetailTourPackageByTourIdService, IViewTourPackageCategoriesService,
        IViewAddSchedulesService, IViewDeleteSchedulesService, IViewEditSchedulesService {

    private LinearLayoutManager mLayoutManager;
    private Switch switchRefund;
    private AutoCompleteTextView autoLocation;
    private final Calendar myCalendar = Calendar.getInstance();
    private List<SchedulesItem> mModelListSchedulesEdit = new ArrayList<>();;
    private List<AddonsItem> mModelListAddOnsEdit = new ArrayList<>();
    private List<MediasItem> mModelListImage = new ArrayList<>();
    private List<CustomPolicyItem> customPolicesItems = new ArrayList<>();
    List<PricesItem> prices = new ArrayList<>();
    private AddPolicesAdapter addPolicesAdapter;
    private AddSchedulesAdapter schedulesEditAdapter;
    private ArrayAdapter countryAdapter;
    private AddListImageAdapter mAdapterListImage;
    private GridLayoutManager managerListImage;
    private List<MediasItem> medias = new ArrayList<>();;
    private List<SchedulesItem> dataSchedule = new ArrayList<>();;
    private List<SchedulesItem> schedules = new ArrayList<>();
    private AddOnsAdapter addOnsAdapter;
    private int numberMore = 1;

    private iViewUpload iViewUpload;
    private IViewTourPackageCategoriesService iViewTourPackageCategoriesService;
    private String tour_id = "";
    private DetailTourPackageByTourIdService detailOpenTourPresenter;
    private IViewDetailTourPackageByTourIdService iViewDetailTour;
    private TourPackageCategoriesService tourPackageCategoriesService;
    private TourPackageItem tourPackagedetail;

    private View compAppbar, compTitleTourInfo, compTitlePrice, compTitleTos, compTitleInsurance,
            compTitlePolicies, compTitleCustomPolicy, compTitleImages, compTitleSchedules, compTitleAddon;
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
            tvTitleAddon, tvDesAddon;
    private TextInputEditText etTitle, etDuration, etAbout, etItinerary, etAgeRestriction, etAddcCostForeign, etTermOfService,
            etTitleInsurance, etDescInsurance, etTripPrice, etKidsPrice, etMinAge, etMaxAge;
    private TextInputLayout tilTitle, tilLocation, tilAboutTheTour, tilCategories, tilItinerary, tilTos;
    private RecyclerView rvCustomPolicy, rvListImage, rvSchedules, rvAddons;
    private LoadingPage loading;
    private int PICK_IMAGES = 1;
    private LokavenDialog lokavenDialog;
    private List<SchedulesItem> listSchedules;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tour_package_open);

        iViewUpload = this;
        iViewDetailTour = this;
        loading = new LoadingPage(this);
        lokavenDialog = new LokavenDialog(this);
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
        } catch (Exception e) {
        }

        detailOpenTourPresenter = new DetailTourPackageByTourIdService(iViewDetailTour, this);
        detailOpenTourPresenter.onSuccessGetDetail(this, tour_id);

        loading.start();
        ivCheck.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(this);
        ivCheck.setOnClickListener(this);

        String[] suggestionsTags = new String[]{"Unique", "Bandung", "Kuliner", "Culture"};
        ArrayAdapter<String> adapterTags = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, suggestionsTags);

        etTags.setAdapter(adapterTags);
//        etTags.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);
//        etTags.addChipTerminator(' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_TO_TERMINATOR);

        ListPickImage();
        ListSchedule();
        ListAddons();
        ListCustomPolicy();
        switchRefund.setChecked(true);

        // Autocomplate location
        String[] countriesList = getResources().getStringArray(R.array.location_array);
        countryAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, countriesList);
        autoLocation.setAdapter(countryAdapter);

        tvAddMoreSchedules.setOnClickListener(this);
        tvAddMoreAddOns.setOnClickListener(this);
        tvAddMorePolicy.setOnClickListener(this);

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
        mModelListSchedulesEdit = new ArrayList<>();
        medias = new ArrayList<>();
        mModelListAddOnsEdit = new ArrayList<>();
        customPolicesItems = new ArrayList<>();
    }

    private void InitBaru(){
        etTripPrice = findViewById(R.id.etTripPrice);
        etKidsPrice = findViewById(R.id.etKidsPrice);
        etMinAge = findViewById(R.id.etMinAge);
        etMaxAge = findViewById(R.id.etMaxAge);
        
        tvAddMorePrice = findViewById(R.id.tvAddMorePrice);
        tvRemovePrice = findViewById(R.id.tvRemovePrice);
        tvAddMorePolicy = findViewById(R.id.tvAddMorePolicy);
        tvAddMoreSchedules = findViewById(R.id.tvAddMoreSchedules);
        tvAddMoreAddOns = findViewById(R.id.tvAddMoreAddOns);
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
        tvTitlePage.setText(getString(R.string.text_edit_open_tour_package));

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
    }

    private void SetDataPrices(List<PricesItem> prices) {
        if (prices.get(0).getKidPrice().equals("0")){
            prices.get(0).setKidPrice("");
        }

        etKidsPrice.setText(prices.get(0).getKidPrice());
//        double price = Integer.parseInt(prices.get(0).getPrice()) / 1.10;
//        long round = Math.round(price);
        etTripPrice.setText(prices.get(0).getPrice());
        etMinAge.setText(prices.get(0).getMinKidAge());
        etMaxAge.setText(prices.get(0).getMaxKidAge());
    }

    private void SetDataCustomPolicy(List<CustomPolicyItem> dataPolicy) {
        if (dataPolicy.size()!= 0){
            for (int i = 0; i < dataPolicy.size(); i++) {
                customPolicesItems.add(new CustomPolicyItem(dataPolicy.get(i).getPolicyName(), dataPolicy.get(i).getPolicy(), dataPolicy.get(i).getTourId()));
            }
        }else {
            AddMoreCustomPolicy();
        }
        addPolicesAdapter.notifyDataSetChanged();
    }

    private void AddMoreCustomPolicy() {
        customPolicesItems.add(new CustomPolicyItem("", ""));
        addPolicesAdapter.notifyDataSetChanged();
    }

    private void SetDataMedia(List<MediasItem> medias) {
        if (medias.size()!=0){
            for (int i = 0; i < medias.size(); i++) {
                mModelListImage.add(new MediasItem(medias.get(i).getMediaId(),
                        medias.get(i).getUrl(),
                        medias.get(i).getType(),
                        medias.get(i).getTourId()));
            }
            AddMoreMedia();
        }else {
            AddMoreMedia();
        }
        mAdapterListImage.notifyDataSetChanged();
    }

    private void AddMoreMedia() {
        mModelListImage.add(new MediasItem("", ""));
        mAdapterListImage.notifyDataSetChanged();
    }

    private void SetDataAddons(List<AddonsItem> addons) {
        if (addons.size()!=0){
            for (int i = 0; i < addons.size() ; i++) {
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
        }else {
            AddMoreAddon();
        }
        addOnsAdapter.notifyDataSetChanged();
    }

    private void AddMoreAddon() {
        mModelListAddOnsEdit.add(new AddonsItem("","","", "", "", 0, false, null));
        addOnsAdapter.notifyDataSetChanged();
    }

    private void SetCategories(String[] categories) {
        String sCategory = " ";
        if (categories.length !=0 ){
            for (int i = 0; i < categories.length; i++) {
                sCategory = categories[i]+';'+" "+sCategory+" ";
            }
        }

        etCategory.addChipTerminator(';', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_TO_TERMINATOR);
        etCategory.setText(sCategory);
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

    private void SetDataSchedule(List<SchedulesItem> schedulesItems) {
        if (schedulesItems.size() != 0){
            for (int i = 0; i < tourPackagedetail.getSchedules().size(); i++) {
                mModelListSchedulesEdit.add(new SchedulesItem(
                        schedulesItems.get(i).getScheduleId(),
                        schedulesItems.get(i).getTourId(),
                        schedulesItems.get(i).getDurations(),
                        schedulesItems.get(i).getStartDate(),
                        schedulesItems.get(i).getEndDate(),
                        schedulesItems.get(i).getQuota(),
                        false,
                        schedulesItems.get(i).getMinQuota(),
                        schedulesItems.get(i).getMaxQuota(),
                        false,
                        "",
                        "0"
                ));
            }
        }else {
            AddMoreSchedule();
        }
        schedulesEditAdapter.notifyDataSetChanged();
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
        rvAddons.setNestedScrollingEnabled(false);
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
        schedulesEditAdapter = new AddSchedulesAdapter(this, mModelListSchedulesEdit);
        rvSchedules.setAdapter(schedulesEditAdapter);
    }

    private void ListPickImage() {
        rvListImage.setHasFixedSize(true);
        managerListImage = new GridLayoutManager(EditOpenTripPackageActivity.this, 3);
        mAdapterListImage = new AddListImageAdapter(this, mModelListImage);
        rvListImage.setLayoutManager(managerListImage);
        rvListImage.setAdapter(mAdapterListImage);
        rvListImage.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.set(12, 12, 12, 12);
            }
        });
    }

    private void AddMoreSchedule() {
        mModelListSchedulesEdit.add(new SchedulesItem(
                "",
                "",
                ""
        ));
        schedulesEditAdapter.notifyDataSetChanged();
    }

    private void onEditTourPackage() {
        String category = etCategory.getText().toString();
        String[] arrCategory = category.split("\\s+");
        List<String> categoriesList = new ArrayList<>();
        for (int i = 1; i < arrCategory.length; i++) {
            categoriesList.add(arrCategory[i].substring(1, arrCategory[i].length() - 1));
        }

        // allocate memory for string array
        String[] categories = new String[categoriesList.size()];
        for (int i = 0; i < categories.length; i++)
            categories[i] = categoriesList.get(i);

        String sTags = etTags.getText().toString();
        String[] arrTags = sTags.split("\\s+");
        List<String> tagsList = new ArrayList<>();
        for (int i = 1; i < arrTags.length; i++) {
            tagsList.add(arrTags[i].substring(1, arrTags[i].length() - 1));
        }

        // allocate memory for string array
        String[] tags = new String[tagsList.size()];
        for (int i = 0; i < tags.length; i++)
            tags[i] = tagsList.get(i);

        // filtering data Media
        List<MediasItem> dataMedia = new ArrayList<>();
        for (int i = 0; i <mModelListImage.size() ; i++) {
            if (!mModelListImage.get(i).getUrl().equals("")){
                if (!mModelListImage.get(i).getType().equals("")){
                    dataMedia.add(new MediasItem(mModelListImage.get(i).getMediaId(), mModelListImage.get(i).getUrl(), mModelListImage.get(i).getType(), mModelListImage.get(i).getTourId()));
                }
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

        List<PricesItem> prices = new ArrayList<>();
        PricesItem pricesItem = new PricesItem();
        pricesItem.setKidPrice(etKidsPrice.getText().toString().trim());
        pricesItem.setPrice(etTripPrice.getText().toString().trim());
        pricesItem.setMinKidAge(etMinAge.getText().toString().trim());
        pricesItem.setMaxKidAge(etMaxAge.getText().toString().trim());
        prices.add(pricesItem);

        InsuranceItem insuranceItem = new InsuranceItem();
        insuranceItem.setTitle(etTitleInsurance.getText().toString());
        insuranceItem.setDescription(etDescInsurance.getText().toString());

        EditTourPackageItem data = new EditTourPackageItem("open",
                etTitle.getText().toString(),
                dataMedia,
                etAbout.getText().toString(),
                categories,
                tags, autoLocation.getText().toString(),
                addOnsAdapter.getAddons(),
                true,
                true,
                true,
                prices,
                etTermOfService.getText().toString(),
                insuranceItem,
                customPolicesItems,
                etAgeRestriction.getText().toString(),
                etAddcCostForeign.getText().toString(),
                etItinerary.getText().toString(),
                null);

        IViewEditTourPackageService iViewEditOpenTripPackage = this;
        EditTourPackageService presenter = new EditTourPackageService(this, iViewEditOpenTripPackage);
        presenter.onEditPrivateTourPackage(tour_id, data);
    }

    @Override
    public void onSuccessEditTourPackage(TourPackageItem tourPackageItem) {
        listSchedules = schedulesEditAdapter.getOpenSchedule();
        if (listSchedules.get(0).getScheduleId().isEmpty()){

            onAddSchedule(tourPackageItem);

        } else if (!listSchedules.get(0).getScheduleId().isEmpty()){

            onEditSchedule(tourPackageItem);

        } else if (schedulesEditAdapter.getDeleteSchedules().size() != 0){

            onDeleteSchedule(tourPackageItem);

        }

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
     * Add schedule
     *
     * @param tourPackageItem*/
    private void onAddSchedule(TourPackageItem tourPackageItem) {
        AddEditSchedulesItem addEditItem = new AddEditSchedulesItem();
        addEditItem.setStart_date(listSchedules.get(0).getStartDate());
        addEditItem.setEnd_date(listSchedules.get(0).getEndDate());
        addEditItem.setMax_quota(listSchedules.get(0).getMaxQuota());
        addEditItem.setMin_quota(listSchedules.get(0).getMinQuota());
        addEditItem.setQuota(listSchedules.get(0).getQuota());

        IViewAddSchedulesService addSchedule = this;
        AddSchedulesService presenter = new AddSchedulesService(this, addSchedule);
        presenter.onAddSchedules(tourPackageItem.getTourId(), addEditItem);
    }

    @Override
    public void onSuccessAddSchedule(TourPackageItem data) {
        onSetSchedule(data);
    }

    @Override
    public void onErrorAddSchedule(String title, String message, int responseCode) {
        if (responseCode == 401) {
            onCheckToken();
        } else {
            loading.stop();
            lokavenDialog.dialogError(title, message);
        }
    }

    /**
     * Edit schedule
     *
     * @param tourPackageItem*/
    private void onEditSchedule(TourPackageItem tourPackageItem) {
        AddEditSchedulesItem addEditItem = new AddEditSchedulesItem();
        addEditItem.setStart_date(listSchedules.get(0).getStartDate());
        addEditItem.setEnd_date(listSchedules.get(0).getEndDate());
        addEditItem.setMax_quota(listSchedules.get(0).getMaxQuota());
        addEditItem.setMin_quota(listSchedules.get(0).getMinQuota());
        addEditItem.setQuota(listSchedules.get(0).getQuota());

        IViewEditSchedulesService editSchedules = this;
        EditSchedulesService presenter = new EditSchedulesService(this, editSchedules);
        presenter.onEditSchedules(tourPackageItem.getTourId(), listSchedules.get(0).getScheduleId(), addEditItem);
    }

    /**
     * After edit schedule
     *
     * @param data*/
    @Override
    public void onSuccessEditSchedule(TourPackageItem data) {
        onSetSchedule(data);
    }

    @Override
    public void onErrorEditSchedule(String title, String message, int responseCode) {
        if (responseCode == 401) {
            onCheckToken();
        } else {
            loading.stop();
            lokavenDialog.dialogError(title, message);
        }
    }

    /**
     * Delete schedule
     *
     * @param tourPackageItem */
    private void onDeleteSchedule(TourPackageItem tourPackageItem) {
        IViewDeleteSchedulesService deleteSchedules = this;
        DeleteSchedulesService presenter = new DeleteSchedulesService(this, deleteSchedules);
        presenter.onDeleteSchedules(tourPackageItem.getTourId(), schedulesEditAdapter.getDeleteSchedules().get(0));
    }

    /**
     * After delete schedule
     *
     * @param data */
    @Override
    public void onSuccessDeleteSchedule(TourPackageItem data) {
        schedulesEditAdapter.removeDeleteSchedules();
        if (schedulesEditAdapter.getDeleteSchedules().size() != 0){
            IViewDeleteSchedulesService deleteSchedules = this;
            DeleteSchedulesService presenter = new DeleteSchedulesService(this, deleteSchedules);
            presenter.onDeleteSchedules(data.getTourId(), schedulesEditAdapter.getDeleteSchedules().get(0));
        } else {
            loading.stop();
            Intent i = new Intent(this, OpenTripPackageDetailActivity.class);
            i.putExtra("tour_id", data.getTourId());
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
            setResult(RESULT_OK,i);
            finish();
        }
    }

    @Override
    public void onErrorDeleteSchedule(String title, String message, int responseCode) {
        loading.stop();
        lokavenDialog.dialogError(title, message);
    }

    /**
     * Set schedule
     *
     * @param data*/
    private void onSetSchedule(TourPackageItem data) {
        listSchedules.remove(0);
        if (listSchedules.size() != 0){
            if (listSchedules.get(0).getScheduleId().isEmpty()){
                AddEditSchedulesItem addEditItem = new AddEditSchedulesItem();
                addEditItem.setStart_date(listSchedules.get(0).getStartDate());
                addEditItem.setEnd_date(listSchedules.get(0).getEndDate());
                addEditItem.setMax_quota(listSchedules.get(0).getMaxQuota());
                addEditItem.setMin_quota(listSchedules.get(0).getMinQuota());
                addEditItem.setQuota(listSchedules.get(0).getQuota());
                IViewAddSchedulesService addSchedule = this;
                AddSchedulesService presenter = new AddSchedulesService(this, addSchedule);
                presenter.onAddSchedules(data.getTourId(), addEditItem);
            } else if (!listSchedules.get(0).getScheduleId().isEmpty()){
                AddEditSchedulesItem addEditItem = new AddEditSchedulesItem();
                addEditItem.setStart_date(listSchedules.get(0).getStartDate());
                addEditItem.setEnd_date(listSchedules.get(0).getEndDate());
                addEditItem.setMax_quota(listSchedules.get(0).getMaxQuota());
                addEditItem.setMin_quota(listSchedules.get(0).getMinQuota());
                addEditItem.setQuota(listSchedules.get(0).getQuota());
                IViewEditSchedulesService editSchedules = this;
                EditSchedulesService presenter = new EditSchedulesService(this, editSchedules);
                presenter.onEditSchedules(data.getTourId(), listSchedules.get(0).getScheduleId(), addEditItem);
            }
        } else if (schedulesEditAdapter.getDeleteSchedules().size() != 0){
            IViewDeleteSchedulesService deleteSchedules = this;
            DeleteSchedulesService presenter = new DeleteSchedulesService(this, deleteSchedules);
            presenter.onDeleteSchedules(data.getTourId(), schedulesEditAdapter.getDeleteSchedules().get(0));
        } else {
            loading.stop();
            Intent intent = new Intent(this, OpenTripPackageDetailActivity.class);
            intent.putExtra("tour_id", data.getTourId());
            intent.putExtra("uniqid", "editPackage");
            startActivity(intent);
            finish();
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
        ArrayList<String> mStringList = new ArrayList<String>();
        String[] suggestionsCategory = null;
        for (int i = 0; i < data.size(); i++) {
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
        mModelListImage.add(new MediasItem("",
                uploadServiceItem.getUrl(),
                "image",
                ""));
        mAdapterListImage.notifyDataSetChanged();
    }

    @Override
    public void onErrorData(String title, String message, int responseCode) {
        if (responseCode == 401) {
            onCheckToken();
        } else {
            loading.stop();
            lokavenDialog.dialogError(title, message);
        }
    }

    @Override
    public void onGetDetail(TourPackageItem getData) throws ParseException {
        tourPackagedetail = getData;
        SetDataSchedule(tourPackagedetail.getSchedules());
        SetDataAddons(tourPackagedetail.getAddons());
        SetDataMedia(tourPackagedetail.getMedias());
        SetDataCustomPolicy(tourPackagedetail.getCustomePolicies());
        SetDataPrices(tourPackagedetail.getPrices());

        etTitle.setText(getData.getTitle());
        etAbout.setText(getData.getDescription());
        if (getData.getSchedules() != null){
            String str = getData.getSchedules().get(0).getDurations();
            str = str.replace(" days","");
            etDuration.setText(str);
        }
        autoLocation.setText(getData.getLocation());
        etTermOfService.setText(getData.getTermOfService());
        switchRefund.setChecked(getData.isRefundable());
        etAgeRestriction.setText(getData.getAgeRestriction());
        etAddcCostForeign.setText(getData.getCostForeignGuest());
        etItinerary.setText(getData.getItinerary());
        SetCategories(getData.getCategories());
        SetTags(getData.getTags());

        iViewTourPackageCategoriesService = this;
        tourPackageCategoriesService = new TourPackageCategoriesService(this, iViewTourPackageCategoriesService);
        tourPackageCategoriesService.onGetAllCategories();

    }

    @Override
    public void onError(String title, String message) {
        loading.stop();
        lokavenDialog.dialogError(title, message);
    }

    public void pickImage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
            return;
        }
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.text_select_picture)), PICK_IMAGES);
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
        loading.stop();
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
//            etDuration.setError(getString(R.string.text_please_input_min_participants));
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

        if(etTermOfService.getText().toString().isEmpty()) {
            tilTos.setError(getString(R.string.text_please_input_term_of_service));
            tilTos.requestFocus();
            return true;
        } else {
            tilTos.setError(null);
        }

        if (schedulesEditAdapter.getItemCount() == 0){
            lokavenDialog.getToast(getString(R.string.text_please_input_schedules));
            return true;
        } else {
            for (int i = 0; i < schedulesEditAdapter.getOpenSchedule().size(); i++){
                if (schedulesEditAdapter.getOpenSchedule().get(i).getStartDate().trim().equals("")){
                    lokavenDialog.getToast(getString(R.string.text_please_input_start_date));
                    return true;
                }

                if (schedulesEditAdapter.getOpenSchedule().get(i).getEndDate().trim().equals("")){
                    lokavenDialog.getToast(getString(R.string.text_please_input_end_date));
                    return true;
                }

                if (schedulesEditAdapter.getOpenSchedule().get(i).getMinQuota().trim().equals("")){
                    lokavenDialog.getToast(getString(R.string.text_please_input_min_quota));
                    return true;
                }
            }
        }

        if (addOnsAdapter.getItemCount() > 0){
            int i = 0;
            while (i < addOnsAdapter.getItemCount()){
                if (addOnsAdapter.getAddons().get(i).getAddon().isEmpty() && addOnsAdapter.getAddons().get(i).getPrice().isEmpty()){
                    addOnsAdapter.getAddons().remove(i);
                } else {
                    i++;
                }
            }
        }

        if (addOnsAdapter.getItemCount() > 0){
            for (int i = 0; i < addOnsAdapter.getItemCount(); i++){
                if (!addOnsAdapter.getAddons().get(i).getAddon().isEmpty() && addOnsAdapter.getAddons().get(i).getPrice().isEmpty()){
                    lokavenDialog.getToast(getString(R.string.text_please_input_price_greater_than_0));
                    return true;
                }

                if (addOnsAdapter.getAddons().get(i).getAddon().isEmpty() && !addOnsAdapter.getAddons().get(i).getPrice().isEmpty()){
                    lokavenDialog.getToast(getString(R.string.text_please_input_addons_name));
                    return true;
                }
            }
        }

        if (mModelListImage.size() <= 1){
            lokavenDialog.getToast(getString(R.string.text_please_choose_images));
            return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            final ArrayList<String> urlImage = new ArrayList<>();
            ClipData clipData = data.getClipData();
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
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
                    for (final String b : urlImage) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                CompressImageHelper compressImageHelper = new CompressImageHelper(EditOpenTripPackageActivity.this);
                                UploadServices uploadService = new UploadServices(EditOpenTripPackageActivity.this, iViewUpload);
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
        if (v == ivBack) {
            onBackPressed();
            finish();
        } else if (v == tvAddMoreSchedules) {
            AddMoreSchedule();
        } else if (v == tvAddMorePolicy) {
            AddMoreCustomPolicy();
        } else if (v == tvAddMoreAddOns) {
            AddMoreAddon();
        } else if (v == ivCheck) {
            if (validation()){
                lokavenDialog.getToast(getString(R.string.text_please_input_start_date));
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            } else {
                loading.start();
                onEditTourPackage();
            }
        }
    }
}
