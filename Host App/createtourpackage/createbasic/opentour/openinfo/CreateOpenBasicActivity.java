package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.opentour.openinfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.utils.dialog.LokavenDialog;
import com.aniqma.lokaventour.host.utils.animation.LoadingPage;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.AdditionalCostItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.CreateTourPackageItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.PricesItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.SchedulesItem;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.categorieslist.CategoriesActivity;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.choosepackagetype.ChoosePackageTypeActivity;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createtosandpolicies.CreateToSAndPoliciesActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hootsuite.nachos.NachoTextView;

import java.util.ArrayList;
import java.util.List;

public class CreateOpenBasicActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivBack;
    private TextView tvTitle;
    private TextInputEditText etTitle,
            etDuration,
            etAbout,
            etItinerary,
            etTripPrice,
            etKidsPrice,
            etMinAge,
            etMaxAge,
            etAddIndividuPriceKids,
            etAddIndividuPriceAdult;
    private TextInputLayout tilTitle,
            tilLocation,
            tilAboutTheTour,
            tilCategories,
            tilItinerary,
            tilPrice,
            tilKidPrice,
            tilMinAge,
            tilMaxAge,
            tilAddIndividuPriceAdult,
            tilAddIndividuPriceKids;
    private Button btn_next;
    private AutoCompleteTextView autoLocation;
    private String[] location = {"Jakarta", "Bandung", "Surabaya", "Yogyakarta", "Surabaya", "Semarang", "Sulawesi", "Kalimantan", "Medan"};
    private NachoTextView multiTags, multiCategory;

    private CreateTourPackageItem createTourPackageItem;
    private PricesItem pricesItem;
    private SchedulesItem scheduleItem;
    private View compAppbar, compBtn, compTitlePage, compTitleTripPrices;
    private TextView tvNumberOfStep, tvNameOfPage, tvDetailPage,
            tvTitlePrice, tvLink, tvDescription;
    private RelativeLayout rlStep1, rlStep2, rlStep3, rlStep4, rlStep5, rlStep6;
    private LoadingPage loading;
    private LinearLayout container;
    private ArrayList<String> urlImage = null;
    private LokavenDialog lokavenDialog;
    private LinearLayout llKidsAdditionalPrice;
    private AdditionalCostItem additionalCost;
    private List<SchedulesItem> schedulesItems = new ArrayList<>();

    private void Initialization() {
        autoLocation = findViewById(R.id.autoLocation);
        multiCategory = findViewById(R.id.multiCategory);
        multiTags = findViewById(R.id.multiTags);
        etTitle = findViewById(R.id.etTitle);
        etDuration = findViewById(R.id.etDuration);
        etAbout = findViewById(R.id.etAbout);
        etTripPrice = findViewById(R.id.etTripPrice);
        etKidsPrice = findViewById(R.id.etKidsPrice);
        etMinAge = findViewById(R.id.etMinAge);
        etMaxAge = findViewById(R.id.etMaxAge);
        etItinerary = findViewById(R.id.etItinerary);

        compBtn = findViewById(R.id.compBtn);
        btn_next = compBtn.findViewById(R.id.btnBig);

        compAppbar = findViewById(R.id.compAppbar);
        ivBack = compAppbar.findViewById(R.id.ivBack);
        tvTitle = compAppbar.findViewById(R.id.tvTitle);

        compTitlePage = findViewById(R.id.compTitlePage);
        tvNumberOfStep = compTitlePage.findViewById(R.id.tvNumberOfStep);
        tvDetailPage = compTitlePage.findViewById(R.id.tvDetailPage);

        etAddIndividuPriceAdult = findViewById(R.id.etAddIndividuPriceAdult);
        etAddIndividuPriceKids = findViewById(R.id.etAddIndividuPriceKids);

        llKidsAdditionalPrice = findViewById(R.id.llKidsAdditionalPrice);
        tilTitle = findViewById(R.id.tilTitle);
        tilLocation = findViewById(R.id.tilLocation);
        tilAboutTheTour = findViewById(R.id.tilAboutTheTour);
        tilCategories = findViewById(R.id.tilCategories);
        tilItinerary = findViewById(R.id.tilItinerary);
        tilPrice = findViewById(R.id.tilPrice);
        tilKidPrice = findViewById(R.id.tilKidPrice);
        tilMinAge = findViewById(R.id.tilMinAge);
        tilMaxAge = findViewById(R.id.tilMaxAge);
        tilAddIndividuPriceAdult = findViewById(R.id.tilAddIndividuPriceAdult);
        tilAddIndividuPriceKids = findViewById(R.id.tilAddIndividuPriceKids);

        rlStep1 = compTitlePage.findViewById(R.id.rlStep1);
        rlStep2 = compTitlePage.findViewById(R.id.rlStep2);
        rlStep3 = compTitlePage.findViewById(R.id.rlStep3);
        rlStep4 = compTitlePage.findViewById(R.id.rlStep4);
        rlStep5 = compTitlePage.findViewById(R.id.rlStep5);
        rlStep6 = compTitlePage.findViewById(R.id.rlStep6);
        SetLinePage();

        compTitleTripPrices = findViewById(R.id.compTitleTripPrices);
        tvTitlePrice = compTitleTripPrices.findViewById(R.id.tvTitle);
        tvLink = compTitleTripPrices.findViewById(R.id.tvLink);
        tvDescription = compTitleTripPrices.findViewById(R.id.tvDescription);
        container = findViewById(R.id.container);
    }

    private void SetLinePage() {
        tvNumberOfStep.setText(Html.fromHtml(getResources().getString(R.string.text_step_6)));
        rlStep1.setBackgroundColor(getResources().getColor(R.color.LOK_Blue));
        rlStep2.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
        rlStep3.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
        rlStep4.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
        rlStep5.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
        rlStep6.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
        tvNumberOfStep.setText("1 "+getString(R.string.text_of)+" 6");
        tvNameOfPage.setText(getString(R.string.text_tour_info));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour_package_basic_info);
        Initialization();

        createTourPackageItem = new CreateTourPackageItem();
        loading = new LoadingPage(this);
        lokavenDialog = new LokavenDialog(this);

        if (getIntent().getSerializableExtra("tourpackage") != null){
            createTourPackageItem = (CreateTourPackageItem) getIntent().getSerializableExtra("tourpackage");

            etTitle.setText(createTourPackageItem.getTitle());
            autoLocation.setText(createTourPackageItem.getLocation());
            etAbout.setText(createTourPackageItem.getDescription());
            multiCategory.setText(createTourPackageItem.getCategories());
            multiTags.setText(createTourPackageItem.getTags());
            etItinerary.setText(createTourPackageItem.getItinerary());
            schedulesItems.addAll(createTourPackageItem.getSchedules());

            if (createTourPackageItem.getPrices() != null){
                etTripPrice.setText(createTourPackageItem.getPrices().get(0).getPrice());
                etKidsPrice.setText(createTourPackageItem.getPrices().get(0).getKidPrice());
                etMinAge.setText(createTourPackageItem.getPrices().get(0).getMinKidAge());
                etMaxAge.setText(createTourPackageItem.getPrices().get(0).getMaxKidAge());
                etAddIndividuPriceAdult.setText(createTourPackageItem.getAdditional_cost().getAdditional_adult_price());
                etAddIndividuPriceKids.setText(createTourPackageItem.getAdditional_cost().getAdditional_kid_price());
            }
        }

        if (getIntent().getStringArrayListExtra("imageList") != null){
            urlImage = getIntent().getStringArrayListExtra("imageList");
        }

        SetContent();
        ivBack.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        multiCategory.setOnClickListener(this);
        SetSugestionLocation();
        SetSugestionTags();

    }

    private void SetSugestionTags() {
        String[] suggestionsTags = new String[]{"Unique", "Bandung", "Kuliner", "Culture", "Culinary", "Forest", "Beach"};
        ArrayAdapter<String> adapterTags = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, suggestionsTags);
        multiTags.setAdapter(adapterTags);
    }

    private void SetSugestionLocation() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, location);
        autoLocation.setThreshold(1); //will start working from first character
        autoLocation.setAdapter(adapter);
    }

    private void SetContent() {
        tvTitle.setText(getResources().getString(R.string.text_create_open_trip));
        btn_next.setText(getString(R.string.btn_next));
        tvTitlePrice.setText(getResources().getString(R.string.text_pricing_and_participants_quota));
        tvDescription.setText(getResources().getString(R.string.text_set_your_price));
        tvLink.setVisibility(View.GONE);
        etAddIndividuPriceKids.setVisibility(View.GONE);
    }

    public boolean validation(){
        boolean failed = false;

        if (etTitle.getText().toString().trim().isEmpty()){
            tilTitle.setError(getString(R.string.text_please_input_title));
            tilTitle.requestFocus();
            failed = true;
        } else {
            tilTitle.setError(null);
        }

        if (etTitle.getText().length() <= 5){
            tilTitle.setError(getString(R.string.text_please_input_title_length));
            tilTitle.requestFocus();
            failed = true;
        } else {
            tilTitle.setError(null);
        }

        if (autoLocation.getText().toString().length() < 2){
            tilLocation.setError(getString(R.string.text_please_input_location));
            tilLocation.requestFocus();
            failed = true;
        } else {
            tilLocation.setError(null);
        }

//        if (etDuration.getText().toString().trim().isEmpty()){
//            etDuration.setError("Please input duration");
//            etDuration.requestFocus();
//            failed = true;
//        }

        if (etAbout.getText().toString().trim().isEmpty()){
            tilAboutTheTour.setError(getString(R.string.text_please_input_about_the_tour));
            tilAboutTheTour.requestFocus();
            failed = true;
        } else {
            tilAboutTheTour.setError(null);
        }

        if (etAbout.getText().toString().trim().length() < 8){
            tilAboutTheTour.setError(getString(R.string.text_about_the_tour_must_be_more_than_8_characters));
            tilAboutTheTour.requestFocus();
            failed = true;
        } else {
            tilAboutTheTour.setError(null);
        }

        if (multiCategory.getChipValues().size() == 0){
            tilCategories.setError(getString(R.string.text_please_input_categories));
            tilCategories.requestFocus();
            failed = true;
        } else {
            tilCategories.setError(null);
        }

        if (etItinerary.getText().toString().trim().isEmpty()){
            tilItinerary.setError(getString(R.string.text_please_input_itinerary));
            tilItinerary.requestFocus();
            failed = true;
        } else {
            tilItinerary.setError(null);
        }

        if (!etTripPrice.getText().toString().trim().isEmpty()){
            int price = Integer.valueOf(etTripPrice.getText().toString().trim());
            if (price <= 9999){
                tilPrice.setError(getString(R.string.text_please_input_price_more_than_IDR_9999));
                tilPrice.requestFocus();
                failed = true;
            } else {
                tilPrice.setError(null);
            }
        } else {
            tilPrice.setError(getString(R.string.text_please_input_price));
            tilPrice.requestFocus();
            failed = true;
        }

        if (!etKidsPrice.getText().toString().trim().isEmpty()){
            if (Integer.valueOf(etKidsPrice.getText().toString().trim()) <= 9999){
                tilKidPrice.setError(getString(R.string.text_please_input_kids_price_more_than_IDR_9999));
                tilKidPrice.requestFocus();
                failed = true;
            } else {
                tilKidPrice.setError(null);
            }

            if (etMinAge.getText().toString().trim().isEmpty()){
                tilMinAge.setError(getString(R.string.text_please_input_min_kids_age));
                tilMinAge.requestFocus();
                failed = true;
            } else {
                tilMinAge.setError(null);
            }

            if (etMaxAge.getText().toString().trim().isEmpty()){
                tilMaxAge.setError(getString(R.string.text_please_input_max_kids_age));
                tilMaxAge.requestFocus();
                failed = true;
            } else {
                tilMaxAge.setError(null);
            }
        }

        if (!etMaxAge.getText().toString().trim().isEmpty()){

            if (etKidsPrice.getText().toString().trim().isEmpty()){
                tilKidPrice.setError(getString(R.string.text_please_input_kids_price));
                tilKidPrice.requestFocus();
                failed = true;
            } else {
                tilKidPrice.setError(null);
            }

            if (etMinAge.getText().toString() != null && !etMinAge.getText().toString().trim().equals("")) {
                if (Integer.valueOf(etMinAge.getText().toString().trim()) > Integer.valueOf(etMaxAge.getText().toString().trim())){
                    tilMaxAge.setError(getString(R.string.text_please_input_max_age_greater_than_min_age));
                    tilMaxAge.requestFocus();
                    failed = true;
                } else {
                    tilMaxAge.setError(null);
                }
            }

            if (Integer.valueOf(etMinAge.getText().toString().trim()) < Integer.valueOf(etMaxAge.getText().toString().trim())){
                tilMaxAge.setError(null);
            }
        }

        if (!etMinAge.getText().toString().trim().isEmpty()){

            if (etKidsPrice.getText().toString().trim().isEmpty()){
                tilKidPrice.setError(getString(R.string.text_please_input_kids_price));
                tilKidPrice.requestFocus();
                failed = true;
            } else {
                tilKidPrice.setError(null);
            }

            if (etMaxAge.getText().toString() != null && !etMaxAge.getText().toString().trim().equals("")) {
                if (Integer.valueOf(etMinAge.getText().toString().trim()) > Integer.valueOf(etMaxAge.getText().toString().trim())){
                    tilMaxAge.setError(getString(R.string.text_please_input_max_age_greater_than_min_age));
                    tilMaxAge.requestFocus();
                    failed = true;
                } else {
                    tilMaxAge.setError(null);
                }
            }
        }

        if (etKidsPrice.getText().toString().trim().isEmpty()){
            tilMaxAge.setError(null);
            tilMinAge.setError(null);
        }

        if (!etAddIndividuPriceAdult.getText().toString().isEmpty()){
            if (etAddIndividuPriceKids.getText().toString().isEmpty()){
                tilAddIndividuPriceKids.setError(getString(R.string.text_this_price_needs_to_be_set_if_you_set_kids_price_in_normal_pricing));
                tilAddIndividuPriceKids.requestFocus();
                failed = true;
            } else {
                tilAddIndividuPriceKids.setError(null);
            }
        }

        etKidsPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!etKidsPrice.getText().toString().trim().isEmpty() && !etMaxAge.getText().toString().trim().isEmpty() && !etMinAge.getText().toString().trim().isEmpty()){
                    llKidsAdditionalPrice.setVisibility(View.VISIBLE);
                } else {
                    llKidsAdditionalPrice.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etMinAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!etKidsPrice.getText().toString().trim().isEmpty() && !etMaxAge.getText().toString().trim().isEmpty() && !etMinAge.getText().toString().trim().isEmpty()){
                    llKidsAdditionalPrice.setVisibility(View.VISIBLE);
                } else {
                    llKidsAdditionalPrice.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etMaxAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!etKidsPrice.getText().toString().trim().isEmpty() && !etMaxAge.getText().toString().trim().isEmpty() && !etMinAge.getText().toString().trim().isEmpty()){
                    llKidsAdditionalPrice.setVisibility(View.VISIBLE);
                } else {
                    llKidsAdditionalPrice.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (failed == true){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == ivBack){
            onBackPressed();
        } else if (v == btn_next) {
            if (!validation()){
                List<PricesItem> prices = new ArrayList<>();
                pricesItem = new PricesItem();
                List<SchedulesItem> schedule = new ArrayList<>();
                if (schedulesItems.size() != 0){
                    schedule.addAll(schedulesItems);
                } else {
                    scheduleItem = new SchedulesItem();
                    createTourPackageItem = new CreateTourPackageItem();
                    scheduleItem.setDurations(etDuration.getText().toString().trim());
                    schedule.add(scheduleItem);
                }

                pricesItem.setKidPrice(etKidsPrice.getText().toString().trim());
                pricesItem.setMinKidAge(etMinAge.getText().toString().trim());
                pricesItem.setMaxKidAge(etMaxAge.getText().toString().trim());
                pricesItem.setPrice(etTripPrice.getText().toString().trim());
                prices.add(pricesItem);
                additionalCost = new AdditionalCostItem();
                additionalCost.setAdditional_adult_price(etAddIndividuPriceAdult.getText().toString().trim());
                additionalCost.setAdditional_kid_price(etAddIndividuPriceKids.getText().toString().trim());
                createTourPackageItem.setTitle(etTitle.getText().toString().trim());
                createTourPackageItem.setPrices(prices);
                createTourPackageItem.setLocation(autoLocation.getText().toString().trim());
                createTourPackageItem.setSchedules(schedule);
                createTourPackageItem.setDescription(etAbout.getText().toString().trim());
                createTourPackageItem.setTags(multiTags.getChipValues());
                createTourPackageItem.setCategories(multiCategory.getChipValues());
                createTourPackageItem.setItinerary(etItinerary.getText().toString().trim());
                createTourPackageItem.setAdditional_cost(additionalCost);
                createTourPackageItem.setType_tour("open");

                Intent i = new Intent(this, CreateToSAndPoliciesActivity.class);
                i.putExtra("tourpackage", createTourPackageItem);
                i.putStringArrayListExtra("imageList", urlImage);
                startActivity(i);
                finish();
            }
        } else if (v == multiCategory){
            List<PricesItem> prices = new ArrayList<>();
            pricesItem = new PricesItem();
            List<SchedulesItem> schedule = new ArrayList<>();
            if (schedulesItems.size() != 0){
                schedule.addAll(schedulesItems);
            } else {
                scheduleItem = new SchedulesItem();
                createTourPackageItem = new CreateTourPackageItem();
                scheduleItem.setDurations(etDuration.getText().toString().trim());
                schedule.add(scheduleItem);
            }

            pricesItem.setKidPrice(etKidsPrice.getText().toString().trim());
            pricesItem.setMinKidAge(etMinAge.getText().toString().trim());
            pricesItem.setMaxKidAge(etMaxAge.getText().toString().trim());
            pricesItem.setPrice(etTripPrice.getText().toString().trim());
            prices.add(pricesItem);
            additionalCost = new AdditionalCostItem();
            additionalCost.setAdditional_adult_price(etAddIndividuPriceAdult.getText().toString().trim());
            additionalCost.setAdditional_kid_price(etAddIndividuPriceKids.getText().toString().trim());
            createTourPackageItem.setTitle(etTitle.getText().toString().trim());
            createTourPackageItem.setPrices(prices);
            createTourPackageItem.setLocation(autoLocation.getText().toString().trim());
            createTourPackageItem.setSchedules(schedule);
            createTourPackageItem.setDescription(etAbout.getText().toString().trim());
            createTourPackageItem.setTags(multiTags.getChipValues());
            createTourPackageItem.setCategories(multiCategory.getChipValues());
            createTourPackageItem.setItinerary(etItinerary.getText().toString().trim());
            createTourPackageItem.setAdditional_cost(additionalCost);
            createTourPackageItem.setType_tour("open");

            Intent intent = new Intent(this, CategoriesActivity.class);
            intent.putExtra("tourpackage", createTourPackageItem);
            intent.putStringArrayListExtra("imageList", urlImage);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, ChoosePackageTypeActivity.class);
        startActivity(intent);
        finish();
    }
}
