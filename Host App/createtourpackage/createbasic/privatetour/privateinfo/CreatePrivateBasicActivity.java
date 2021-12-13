package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privateinfo;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.utils.dialog.LokavenDialog;
import com.aniqma.lokaventour.host.utils.misc.inlinehelp.TooltipWindowUtils;
import com.aniqma.lokaventour.host.utils.animation.LoadingPage;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.AdditionalCostItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.CreateTourPackageItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.PricesItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.SchedulesItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.createTour.SchedulesAndPriceItem;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.categorieslist.CategoriesActivity;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createtosandpolicies.CreateToSAndPoliciesActivity;
import com.aniqma.lokaventour.host.network.local.createtourpackage.TourPackageLite;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hootsuite.nachos.NachoTextView;

import java.util.ArrayList;
import java.util.List;

public class CreatePrivateBasicActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivBack, ivHelpAdditionalKids;
    private TextView tv_add_more_price, tvTitle;
    private Button btn_next;
    private AutoCompleteTextView autoLocation;
    private String[] location = {"Jakarta", "Bandung", "Surabaya", "Yogyakarta", "Surabaya", "Semarang", "Sulawesi", "Kalimantan", "Medan"};
    private NachoTextView multiTags, multiCategory;
    private RecyclerView recyclerview_price;
    private List<SchedulesAndPriceItem> arrayList;
    private RecyclerView.LayoutManager layoutManager;
    private AddPriceAdapter addPriceAdapter;
    private TextInputEditText etTitle,
            etDuration,
            etAbout,
            etItinerary,
            etAddIndividuPriceKids,
            etAddIndividuPriceAdult;
    private TextInputLayout tilTitle,
            tilDuration,
            tilAboutTheTour,
            tilItinerary,
            tilLocation,
            tilAddIndividuPriceAdult,
            tilAddIndividuPriceKids,
            tilCategories;
    private CreateTourPackageItem createTourPackageItem;
    private PricesItem pricesItem;

    private SchedulesAndPriceItem schedulesAndPriceItem;
    private View compAppbar, compBtn, compTitleTripPrices, component_info_additional;
    private ArrayAdapter<String> adapterLocation, adapterTags;
    private String[] suggestionsTags;
    private TextView tvTitlePrice, tvLink, tvDescription, tvDescriptionAdditional;
    private View compTitlePage,component_info_price;
    private TextView tvNumberOfStep, tvNameOfPage, tvDetailPage, tvDescriptionPrice;
    private RelativeLayout rlStep1, rlStep2, rlStep3, rlStep4, rlStep5, rlStep6;
    private ArrayList<String> urlImage = null;
    private LoadingPage loadingPage;
    private LinearLayout llKidsAdditionalPrice;
    private AdditionalCostItem additionalCost;
    private List<SchedulesItem> schedulesItems = new ArrayList<>();
    private TooltipWindowUtils tipWindow;
    private LokavenDialog lokavenDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_private_trip_basic_info);
        tipWindow = new TooltipWindowUtils(this);
        loadingPage = new LoadingPage(this);
        lokavenDialog = new LokavenDialog(this);
        init();
        createTourPackageItem = new CreateTourPackageItem();
        if (getIntent().getSerializableExtra("tourpackage") != null){
            createTourPackageItem = (CreateTourPackageItem) getIntent().getSerializableExtra("tourpackage");

            etTitle.setText(createTourPackageItem.getTitle());
            autoLocation.setText(createTourPackageItem.getLocation());
            etAbout.setText(createTourPackageItem.getDescription());
            multiCategory.setText(createTourPackageItem.getCategories());
            multiTags.setText(createTourPackageItem.getTags());
            etItinerary.setText(createTourPackageItem.getItinerary());
            etAddIndividuPriceAdult.setText(createTourPackageItem.getAdditional_cost().getAdditional_adult_price());
            etAddIndividuPriceKids.setText(createTourPackageItem.getAdditional_cost().getAdditional_kid_price());
            schedulesItems.addAll(createTourPackageItem.getSchedules());
        }

        if (getIntent().getStringArrayListExtra("imageList") != null){
            urlImage = getIntent().getStringArrayListExtra("imageList");
        }

        if (createTourPackageItem.getPrices() != null){
            if (createTourPackageItem.getPrices().size() != 0){
                for (int i = 0; i < createTourPackageItem.getPrices().size(); i++){
                    schedulesAndPriceItem = new SchedulesAndPriceItem();
                    schedulesAndPriceItem.setPricesItem(createTourPackageItem.getPrices().get(i));
                    arrayList.add(schedulesAndPriceItem);
                }
            } else {
                schedulesAndPriceItem = new SchedulesAndPriceItem();
                PricesItem pricesItem = new PricesItem();
                schedulesAndPriceItem.setPricesItem(pricesItem);
                SchedulesItem schedulesItem = new SchedulesItem();
                schedulesAndPriceItem.setSchedulesItem(schedulesItem);
                arrayList.add(schedulesAndPriceItem);
            }
        } else {
            schedulesAndPriceItem = new SchedulesAndPriceItem();
            PricesItem pricesItem = new PricesItem();
            schedulesAndPriceItem.setPricesItem(pricesItem);
            SchedulesItem schedulesItem = new SchedulesItem();
            schedulesAndPriceItem.setSchedulesItem(schedulesItem);
            arrayList.add(schedulesAndPriceItem);
        }

        schedulesAndPriceItem = new SchedulesAndPriceItem();

        etAddIndividuPriceAdult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()){
                    llKidsAdditionalPrice.setVisibility(View.VISIBLE);
                    if (addPriceAdapter.getItem() != null) {
                        if (!addPriceAdapter.getItem().get(0).getPricesItem().getKidPrice().isEmpty()) {
                            if (etAddIndividuPriceKids.getText().toString().isEmpty()) {
                                tilAddIndividuPriceKids.setError(getString(R.string.text_this_price_needs_to_be_set_if_you_set_kids_price_in_normal_pricing));
                            } else {
                                tilAddIndividuPriceKids.setError(null);
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

        SetContent();
        SetSugestionLocation();
        SetSugestionTags();
        SetListPrice();

        compAppbar.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        tv_add_more_price.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivHelpAdditionalKids.setOnClickListener(this);
        multiCategory.setOnClickListener(this);
    }

    private void SetListPrice() {
        recyclerview_price.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerview_price.setLayoutManager(layoutManager);
        addPriceAdapter = new AddPriceAdapter(arrayList, this, llKidsAdditionalPrice, etAddIndividuPriceAdult, etAddIndividuPriceKids);
        recyclerview_price.setAdapter(addPriceAdapter);
    }

    private void SetSugestionLocation() {
        adapterLocation = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, location);
        autoLocation.setThreshold(1); //will start working from first character
        autoLocation.setAdapter(adapterLocation);
    }

    private void SetSugestionTags() {
        suggestionsTags = new String[]{"Unique", "Bandung", "Kuliner", "Culture", "Culinary", "Forest", "Beach"};
        adapterTags = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, suggestionsTags);
        multiTags.setAdapter(adapterTags);
    }

    private void SetContent() {
        tvTitle.setText(getResources().getString(R.string.text_private_trip_title));
        btn_next.setText(getResources().getString(R.string.text_button_next));
        tvTitlePrice.setText(getResources().getString(R.string.text_pricing_and_participants_quota));
        tvDescription.setText(getResources().getString(R.string.text_set_your_price));
        tvLink.setVisibility(View.GONE);
        if (!etAddIndividuPriceAdult.getText().toString().isEmpty()){
            llKidsAdditionalPrice.setVisibility(View.VISIBLE);
        } else {
            if(createTourPackageItem.getPrices() != null){
                if (!createTourPackageItem.getPrices().get(0).getKidPrice().isEmpty()){
                    llKidsAdditionalPrice.setVisibility(View.VISIBLE);;
                } else {
                    llKidsAdditionalPrice.setVisibility(View.GONE);
                    etAddIndividuPriceKids.setText("");
                }
            } else {
                llKidsAdditionalPrice.setVisibility(View.GONE);
                etAddIndividuPriceKids.setText("");
            }
        }
        tvDescriptionPrice.setText(getString(R.string.text_you_can_set_as_many_pricing_as_you_like));
        tvDescriptionAdditional.setText(getString(R.string.text_additional_participants_price_can_be_used));
    }

    private void init() {
        autoLocation = findViewById(R.id.autoLocation);
        multiCategory = findViewById(R.id.multiCategory);
        multiTags = findViewById(R.id.multiTags);
        tv_add_more_price = findViewById(R.id.tv_add_more_price);
        recyclerview_price = findViewById(R.id.recyclerview_price);
        arrayList = new ArrayList<>();
        etTitle = findViewById(R.id.etTitle);
        etDuration = findViewById(R.id.etDuration);
        etAbout = findViewById(R.id.etAbout);
        etItinerary = findViewById(R.id.etItinerary);
        tilTitle = findViewById(R.id.tilTitle);
        tilDuration = findViewById(R.id.tilDuration);
        tilAboutTheTour = findViewById(R.id.tilAboutTheTour);
        tilItinerary = findViewById(R.id.tilItinerary);

        compAppbar = findViewById(R.id.compAppbar);
        ivBack = compAppbar.findViewById(R.id.ivBack);
        tvTitle = compAppbar.findViewById(R.id.tvTitle);

        compBtn = findViewById(R.id.compBtn);
        btn_next = compBtn.findViewById(R.id.btnBig);

        compTitleTripPrices = findViewById(R.id.compTitleTripPrices);
        tvTitlePrice = compTitleTripPrices.findViewById(R.id.tvTitle);
        tvLink = compTitleTripPrices.findViewById(R.id.tvLink);
        tvDescription = compTitleTripPrices.findViewById(R.id.tvDescription);

        compTitlePage = findViewById(R.id.compTitlePage);
        tvNumberOfStep = compTitlePage.findViewById(R.id.tvNumberOfStep);
        tvDetailPage = compTitlePage.findViewById(R.id.tvDetailPage);

        etAddIndividuPriceAdult = findViewById(R.id.etAddIndividuPriceAdult);
        etAddIndividuPriceKids = findViewById(R.id.etAddIndividuPriceKids);

        llKidsAdditionalPrice = findViewById(R.id.llKidsAdditionalPrice);
        ivHelpAdditionalKids = findViewById(R.id.ivHelpAdditionalKids);

        component_info_price = findViewById(R.id.component_info_price);
        tvDescriptionPrice = component_info_price.findViewById(R.id.tvDescription);

        component_info_additional = findViewById(R.id.component_info_additional);
        tvDescriptionAdditional = component_info_additional.findViewById(R.id.tvDescription);

        tilLocation = findViewById(R.id.tilLocation);
        tilAddIndividuPriceKids = findViewById(R.id.tilAddIndividuPriceKids);
        tilAddIndividuPriceAdult = findViewById(R.id.tilAddIndividuPriceAdult);
        tilCategories = findViewById(R.id.tilCategories);

        rlStep1 = compTitlePage.findViewById(R.id.rlStep1);
        rlStep2 = compTitlePage.findViewById(R.id.rlStep2);
        rlStep3 = compTitlePage.findViewById(R.id.rlStep3);
        rlStep4 = compTitlePage.findViewById(R.id.rlStep4);
        rlStep5 = compTitlePage.findViewById(R.id.rlStep5);
        rlStep6 = compTitlePage.findViewById(R.id.rlStep6);
        SetLinePage();
    }

    private void SetLinePage() {
        tvNumberOfStep.setText(Html.fromHtml(getResources().getString(R.string.text_step_1)));
        rlStep1.setBackgroundColor(getResources().getColor(R.color.LOK_Blue));
        rlStep2.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
        rlStep3.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
        rlStep4.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
        rlStep5.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
        rlStep6.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
    }

    private void AddPrice() {
        PricesItem pricesItem = new PricesItem();
        SchedulesItem schedulesItem = new SchedulesItem();
        SchedulesAndPriceItem schedulesAndPriceItem = new SchedulesAndPriceItem();
        schedulesAndPriceItem.setPricesItem(pricesItem);
        schedulesAndPriceItem.setSchedulesItem(schedulesItem);
        arrayList.add(addPriceAdapter.getItemCount(), schedulesAndPriceItem);
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
        }  else {
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

        if (addPriceAdapter.getItem() == null){
            lokavenDialog.getToast(getString(R.string.text_please_enter_the_complete_form_price));
            failed = true;
        }

        if (addPriceAdapter.getItem() != null){
            for (int i = 0; i < addPriceAdapter.getItem().size(); i++){
                if (addPriceAdapter.getItem().get(i).getPricesItem().getMinParticipant() == null || addPriceAdapter.getItem().get(i).getPricesItem().getMinParticipant().trim().equals("")){
                    lokavenDialog.getToast(getString(R.string.text_please_input_min_participants));
                    failed = true;
                    break;
                }

                if (addPriceAdapter.getItem().get(i).getPricesItem().getMinParticipant() == null || addPriceAdapter.getItem().get(i).getPricesItem().getMinParticipant().trim().equals("")){
                    lokavenDialog.getToast(getString(R.string.text_please_input_max_participants));
                    failed = true;
                    break;
                }

                if ((addPriceAdapter.getItem().get(i).getPricesItem().getMaxParticipant() != null && !addPriceAdapter.getItem().get(i).getPricesItem().getMaxParticipant().isEmpty()) && (addPriceAdapter.getItem().get(i).getPricesItem().getMinParticipant() != null && !addPriceAdapter.getItem().get(i).getPricesItem().getMaxParticipant().isEmpty())){
                    if (Integer.valueOf(addPriceAdapter.getItem().get(i).getPricesItem().getMaxParticipant()) < Integer.valueOf(addPriceAdapter.getItem().get(i).getPricesItem().getMinParticipant())){
                        lokavenDialog.getToast(getString(R.string.text_please_input_min_participant_less_than_max_participants));
                        failed = true;
                        break;
                    }
                }

                if (addPriceAdapter.getItem().get(i).getPricesItem().getPrice() == null || addPriceAdapter.getItem().get(i).getPricesItem().getPrice().trim().equals("")){
                    lokavenDialog.getToast(getString(R.string.text_please_input_price));
                    failed = true;
                    break;
                }

                if (addPriceAdapter.getItem().get(i).getPricesItem().getPrice() != null && !addPriceAdapter.getItem().get(i).getPricesItem().getPrice().trim().equals("")){
                    if (Double.valueOf(addPriceAdapter.getItem().get(i).getPricesItem().getPrice().trim()) < 9999){
                        lokavenDialog.getToast(getString(R.string.text_please_input_price_more_than_IDR_9999));
                        failed = true;
                        break;
                    }
                }

                if (addPriceAdapter.getItem().get(i).getPricesItem().getKidPrice() != null && !addPriceAdapter.getItem().get(i).getPricesItem().getKidPrice().equals("")){

                    if (Double.valueOf(addPriceAdapter.getItem().get(i).getPricesItem().getKidPrice().trim()) < 9999){
                        lokavenDialog.getToast(getString(R.string.text_please_input_kids_price_more_than_IDR_9999));
                        failed = true;
                        break;
                    }

                    if (addPriceAdapter.getItem().get(i).getPricesItem().getMinKidAge() == null || addPriceAdapter.getItem().get(i).getPricesItem().getMinKidAge().trim().equals("")){
                        lokavenDialog.getToast(getString(R.string.text_please_input_min_kids_age));
                        failed = true;
                        break;
                    }


                    if (addPriceAdapter.getItem().get(i).getPricesItem().getMaxKidAge() == null || addPriceAdapter.getItem().get(i).getPricesItem().getMaxKidAge().trim().equals("")){
                        lokavenDialog.getToast(getString(R.string.text_please_input_max_kids_age));
                        failed = true;
                        break;
                    }

                    if ((addPriceAdapter.getItem().get(i).getPricesItem().getMaxKidAge() != null && !addPriceAdapter.getItem().get(i).getPricesItem().getMaxKidAge().isEmpty()) && (addPriceAdapter.getItem().get(i).getPricesItem().getMinKidAge() != null && !addPriceAdapter.getItem().get(i).getPricesItem().getMinKidAge().isEmpty())){
                        if (Double.valueOf(addPriceAdapter.getItem().get(i).getPricesItem().getMaxKidAge()) < Integer.valueOf(addPriceAdapter.getItem().get(i).getPricesItem().getMinKidAge())){
                            lokavenDialog.getToast(getString(R.string.text_please_input_kids_min_age_less_than_kids_max_age));
                            failed = true;
                            break;
                        }
                    }
                }

                if ((addPriceAdapter.getItem().get(i).getPricesItem().getMinKidAge() != null && !addPriceAdapter.getItem().get(i).getPricesItem().getMinKidAge().trim().equals("")) || (addPriceAdapter.getItem().get(i).getPricesItem().getMaxKidAge() != null && !addPriceAdapter.getItem().get(i).getPricesItem().getMaxKidAge().trim().equals(""))){
                    if (addPriceAdapter.getItem().get(i).getPricesItem().getKidPrice() == null || addPriceAdapter.getItem().get(i).getPricesItem().getKidPrice().trim().equals("")){
                        lokavenDialog.getToast(getString(R.string.text_please_input_kids_price));
                        failed = true;
                        break;
                    }
                }

                if (addPriceAdapter.getItem().get(0).getPricesItem().getKidPrice() != null && !addPriceAdapter.getItem().get(0).getPricesItem().getKidPrice().equals("")){
                    if (addPriceAdapter.getItem().get(i).getPricesItem().getMinKidAge() == null || addPriceAdapter.getItem().get(i).getPricesItem().getMinKidAge().trim().equals("")){
                        Toast.makeText(this, getString(R.string.text_please_input_min_kids_age), Toast.LENGTH_SHORT).show();
                        failed = true;
                        break;
                    }


                    if (addPriceAdapter.getItem().get(i).getPricesItem().getMaxKidAge() == null || addPriceAdapter.getItem().get(i).getPricesItem().getMaxKidAge().trim().equals("")){
                        lokavenDialog.getToast(getString(R.string.text_please_input_max_kids_age));
                        failed = true;
                        break;
                    }

                    if ((addPriceAdapter.getItem().get(i).getPricesItem().getMaxKidAge() != null && !addPriceAdapter.getItem().get(i).getPricesItem().getMaxKidAge().isEmpty()) && (addPriceAdapter.getItem().get(i).getPricesItem().getMinKidAge() != null && !addPriceAdapter.getItem().get(i).getPricesItem().getMinKidAge().isEmpty())){
                        if (Double.valueOf(addPriceAdapter.getItem().get(i).getPricesItem().getMaxKidAge()) < Integer.valueOf(addPriceAdapter.getItem().get(i).getPricesItem().getMinKidAge())){
                            lokavenDialog.getToast(getString(R.string.text_please_input_kids_min_age_less_than_kids_max_age));
                            failed = true;
                            break;
                        }
                    }
                }
            }
        }

        if (!etAddIndividuPriceAdult.getText().toString().isEmpty()){
            llKidsAdditionalPrice.setVisibility(View.VISIBLE);

            if (addPriceAdapter.getItem() != null){
                if (!addPriceAdapter.getItem().get(0).getPricesItem().getKidPrice().isEmpty() || !etAddIndividuPriceKids.getText().toString().isEmpty()){

                    if (etAddIndividuPriceKids.getText().toString().isEmpty()){
                        tilAddIndividuPriceKids.setError(getString(R.string.text_this_price_needs_to_be_set_if_you_set_kids_price_in_normal_pricing));
                        failed = true;
                    } else {
                        tilAddIndividuPriceKids.setError(null);
                    }

                    for (int i = 0; i < addPriceAdapter.getItem().size(); i++){

                        if (addPriceAdapter.getItem().get(i).getPricesItem().getKidPrice() != null && !addPriceAdapter.getItem().get(i).getPricesItem().getKidPrice().equals("")){
                            if (Double.valueOf(addPriceAdapter.getItem().get(i).getPricesItem().getKidPrice().trim()) < 9999){
                                lokavenDialog.getToast(getString(R.string.text_please_input_kids_price_more_than_IDR_9999));
                                failed = true;
                            }
                        } else {
                            lokavenDialog.getToast(getString(R.string.text_please_input_kids_price_more_than_IDR_9999));
                            failed = true;
                            break;
                        }

                        if (addPriceAdapter.getItem().get(i).getPricesItem().getMinKidAge() == null || addPriceAdapter.getItem().get(i).getPricesItem().getMinKidAge().trim().equals("")){
                            lokavenDialog.getToast(getString(R.string.text_please_input_min_kids_age));
                            failed = true;
                        }


                        if (addPriceAdapter.getItem().get(i).getPricesItem().getMaxKidAge() == null || addPriceAdapter.getItem().get(i).getPricesItem().getMaxKidAge().trim().equals("")){
                            lokavenDialog.getToast(getString(R.string.text_please_input_max_kids_age));
                            failed = true;
                        }

                        if ((addPriceAdapter.getItem().get(i).getPricesItem().getMaxKidAge() != null && !addPriceAdapter.getItem().get(i).getPricesItem().getMaxKidAge().isEmpty()) && (addPriceAdapter.getItem().get(i).getPricesItem().getMinKidAge() != null && !addPriceAdapter.getItem().get(i).getPricesItem().getMinKidAge().isEmpty())){
                            if (Double.valueOf(addPriceAdapter.getItem().get(i).getPricesItem().getMaxKidAge()) < Integer.valueOf(addPriceAdapter.getItem().get(i).getPricesItem().getMinKidAge())){
                                lokavenDialog.getToast(getString(R.string.text_please_input_kids_min_age_less_than_kids_max_age));
                                failed = true;
                            }
                        }
                    }
                }
            }
        }

        if (!etAddIndividuPriceKids.getText().toString().isEmpty() && etAddIndividuPriceAdult.getText().toString().isEmpty()){
            tilAddIndividuPriceAdult.setError(getString(R.string.text_please_input_additional_adult_price));
            failed = true;
        } else {
            tilAddIndividuPriceAdult.setError(null);
        }

        if (!etAddIndividuPriceKids.getText().toString().isEmpty()){
            if (Double.parseDouble(etAddIndividuPriceKids.getText().toString().trim()) < 9999){
                tilAddIndividuPriceKids.setError(getString(R.string.text_please_input_additional_kids_price_more_than_IDR_9999));
                failed = true;
            } else {
                tilAddIndividuPriceKids.setError(null);
            }
        }

        if (!etAddIndividuPriceAdult.getText().toString().isEmpty()){
            if (Double.parseDouble(etAddIndividuPriceAdult.getText().toString().trim()) < 9999){
                tilAddIndividuPriceAdult.setError(getString(R.string.text_please_input_additional_adult_price_more_than_IDR_9999));
                failed = true;
            } else {
                tilAddIndividuPriceAdult.setError(null);
            }
        }

        if (failed == true){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == ivBack) {
            onBackPressed();
        } else if (v == btn_next) {
            if (!validation()){
                List<PricesItem> prices = new ArrayList<>();
                pricesItem = new PricesItem();
                for (int i = 0; i < addPriceAdapter.getItem().size(); i++){
                    prices.add(addPriceAdapter.getItem().get(i).getPricesItem());
                }
                prices.add(pricesItem);
                prices.remove(prices.size()-1);
                additionalCost = new AdditionalCostItem();
                additionalCost.setAdditional_adult_price(etAddIndividuPriceAdult.getText().toString().trim());
                additionalCost.setAdditional_kid_price(etAddIndividuPriceKids.getText().toString().trim());
                createTourPackageItem.setTitle(etTitle.getText().toString().trim());
                createTourPackageItem.setPrices(prices);
                createTourPackageItem.setLocation(autoLocation.getText().toString());
                if (schedulesItems.size() != 0){
                    createTourPackageItem.setSchedules(schedulesItems);
                }
                createTourPackageItem.setDescription(etAbout.getText().toString().trim());
                createTourPackageItem.setTags(multiTags.getChipValues());
                createTourPackageItem.setCategories(multiCategory.getChipValues());
                createTourPackageItem.setItinerary(etItinerary.getText().toString().trim());
                createTourPackageItem.setAdditional_cost(additionalCost);
                createTourPackageItem.setType_tour("close");

                TourPackageLite tourPackageLite = new TourPackageLite(this);
                tourPackageLite.addDB(createTourPackageItem, urlImage);

                Intent intent = new Intent(this, CreateToSAndPoliciesActivity.class);
                intent.putExtra("tourpackage", createTourPackageItem);
                intent.putExtra("pagePrivate", 1);
                intent.putStringArrayListExtra("imageList", urlImage);
                startActivity(intent);
                finish();
            }
        }else if (v == tv_add_more_price){
//            AddPrice();
            addPriceAdapter.addItem();
            recyclerview_price.post(new Runnable() {
                @Override
                public void run() {
                    // Call smooth scroll
                    recyclerview_price.smoothScrollToPosition(addPriceAdapter.getItemCount() - 1);
                }
            });
        } else if (v == ivHelpAdditionalKids){
            if (!tipWindow.isTooltipShown()){
                tipWindow.showToolTip(ivHelpAdditionalKids, getString(R.string.text_this_price_needs_to_be_set_if_you_set_kids_price_in_normal_pricing));
            } else {
                tipWindow.dismissTooltip();
            }
        } else if (v == multiCategory){
            List<PricesItem> prices = new ArrayList<>();
            pricesItem = new PricesItem();
            for (int i = 0; i < addPriceAdapter.getItem().size(); i++){
                prices.add(addPriceAdapter.getItem().get(i).getPricesItem());
            }
            prices.add(pricesItem);
            prices.remove(prices.size()-1);
            additionalCost = new AdditionalCostItem();
            additionalCost.setAdditional_adult_price(etAddIndividuPriceAdult.getText().toString().trim());
            additionalCost.setAdditional_kid_price(etAddIndividuPriceKids.getText().toString().trim());
            createTourPackageItem.setTitle(etTitle.getText().toString().trim());
            createTourPackageItem.setPrices(prices);
            createTourPackageItem.setLocation(autoLocation.getText().toString());
            if (schedulesItems.size() != 0){
                createTourPackageItem.setSchedules(schedulesItems);
            }
            createTourPackageItem.setDescription(etAbout.getText().toString().trim());
            createTourPackageItem.setTags(multiTags.getChipValues());
            createTourPackageItem.setCategories(multiCategory.getChipValues());
            createTourPackageItem.setItinerary(etItinerary.getText().toString().trim());
            createTourPackageItem.setAdditional_cost(additionalCost);
            createTourPackageItem.setType_tour("close");

            TourPackageLite tourPackageLite = new TourPackageLite(this);
            tourPackageLite.addDB(createTourPackageItem, urlImage);

            Intent intent = new Intent(this, CategoriesActivity.class);
            intent.putExtra("tourpackage", createTourPackageItem);
            intent.putExtra("pagePrivate", 1);
            intent.putStringArrayListExtra("imageList", urlImage);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
