package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createaddons;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.helper.image.RealPathHelper;
import com.aniqma.lokaventour.host.utils.dialog.LokavenDialog;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.AddonsItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.CreateTourPackageItem;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.opentour.opentrippreview.CreateOpenTripPreviewActivity;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privatetrippreview.CreatePrivateTripPreviewActivity;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createschedules.CreateTripPackageAddSchedulesActivity;
import com.aniqma.lokaventour.host.network.local.createtourpackage.TourPackageLite;

import java.util.ArrayList;
import java.util.List;

public class CreateAddOnsActivity extends AppCompatActivity implements View.OnClickListener, CreateAddOnsAdapter.CallbackInterface, AddonsImagesAdapter.CallbackInterface {

    private RecyclerView listAddOns;
    private TextView tvAddMoreAddOns, tv_back;
    private List<AddonsItem> addOnsItems;
    private CreateAddOnsAdapter createAddOnsAdapter;
    private int numberSchedules = 1;
    private LinearLayoutManager mLayoutManager;
    private Button btn_next;
    private int mode;
    private CreateTourPackageItem createTourPackageItem;
    private boolean clicked = false;
    private ArrayList<String> urlImage = null;
    private View compTitlePage, compAppbar, compBtn, compTitle, component_info_addons;
    private ImageView ivBack;
    private TextView tvTitle, tvNumberOfStep, tvNameOfPage, tvDetailPage, tvTitleAddons, tvDescAddons, tvDescriptionAddons;
    private RelativeLayout rlStep1, rlStep2, rlStep3, rlStep4, rlStep5, rlStep6;
    private LokavenDialog lokavenDialog;
    private int PICK_IMAGES = 1;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour_package_add_addons);
        init();

        createTourPackageItem = new CreateTourPackageItem();
        lokavenDialog = new LokavenDialog(this);

        if (getIntent().getStringArrayListExtra("imageList") != null){
            urlImage = getIntent().getStringArrayListExtra("imageList");
        }

        if (getIntent().getSerializableExtra("tourpackage") != null){
            createTourPackageItem = (CreateTourPackageItem) getIntent().getSerializableExtra("tourpackage");
        }
        mode = getIntent().getIntExtra("pagePrivate", 0);

        setContent();
        setListAddons();

        tvAddMoreAddOns.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tv_back.setOnClickListener(this);
    }

    private void setContent() {
        if (mode == 1){
            tvTitle.setText(getResources().getString(R.string.text_private_trip_title));
        }else {
            tvTitle.setText(getResources().getString(R.string.text_create_open_trip));
        }
        tvNumberOfStep.setText(Html.fromHtml(getResources().getString(R.string.text_step_5)));
        tvDetailPage.setText(getString(R.string.text_page_addons_desc));
        tvTitleAddons.setText(getString(R.string.text_addons));
        tvDescAddons.setText(getString(R.string.text_set_addons_desc));
        btn_next.setText(getResources().getString(R.string.btn_next));
        tv_back.setText(getResources().getString(R.string.text_back));
        tvDescriptionAddons.setText(getString(R.string.text_addons_are_service_product_that_you_could_offer_to_guest_as_an_extra));
    }

    private void setListAddons() {
        createAddOnsAdapter = new CreateAddOnsAdapter(this, addOnsItems);
        mLayoutManager = new LinearLayoutManager(this);
        listAddOns.setLayoutManager(mLayoutManager);
        listAddOns.setAdapter(createAddOnsAdapter);
        if (createTourPackageItem.getAddons() != null){
            for (int i = 0; i < createTourPackageItem.getAddons().size(); i++){
                addOnsItems.add(createTourPackageItem.getAddons().get(i));
            }
        } else {
            addMoreAddOns();
        }
    }

    private void addMoreAddOns() {
        createAddOnsAdapter.addItem();
        listAddOns.post(new Runnable() {
            @Override
            public void run() {
                // Call smooth scroll
                listAddOns.smoothScrollToPosition(createAddOnsAdapter.getItemCount() - 1);
            }
        });
    }

    private void init() {
        listAddOns = findViewById(R.id.listAddOns);
        tvAddMoreAddOns = findViewById(R.id.tvAddMoreAddOns);

        compAppbar = findViewById(R.id.compAppbar);
        ivBack = compAppbar.findViewById(R.id.ivBack);
        tvTitle = compAppbar.findViewById(R.id.tvTitle);

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
        btn_next = compBtn.findViewById(R.id.btnBig);
        tv_back = compBtn.findViewById(R.id.tvTextButton);

        compTitle = findViewById(R.id.compTitle);
        tvTitleAddons = compTitle.findViewById(R.id.tvTitle);
        tvDescAddons = compTitle.findViewById(R.id.tvDescription);

        component_info_addons = findViewById(R.id.component_info_addons);
        tvDescriptionAddons = component_info_addons.findViewById(R.id.tvDescription);

        addOnsItems = new ArrayList<>();
    }

    private void SetLinePage() {
        rlStep1.setBackgroundColor(getResources().getColor(R.color.LOK_Blue));
        rlStep2.setBackgroundColor(getResources().getColor(R.color.LOK_Navi_Blue));
        rlStep3.setBackgroundColor(getResources().getColor(R.color.LOK_Red));
        rlStep4.setBackgroundColor(getResources().getColor(R.color.LOK_Teal));
        rlStep5.setBackgroundColor(getResources().getColor(R.color.LOK_Gold));
        rlStep6.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
    }

    public boolean validation(){
        final List<AddonsItem> addonsItems = createAddOnsAdapter.getItem();

        if (createAddOnsAdapter.getItemCount() > 0){
            int i = 0;
            while (i < createAddOnsAdapter.getItemCount()){
                if (addonsItems.get(i).getAddon().isEmpty() && addonsItems.get(i).getPrice().isEmpty()){
                    addonsItems.remove(i);
                } else {
                    i++;
                }
            }
        }

        if (createAddOnsAdapter.getItemCount() > 0){
            for (int i = 0; i < createAddOnsAdapter.getItemCount(); i++){
                if (addonsItems.get(i).getPriceType().equals("user")) {
                    if (!addonsItems.get(i).getAddon().isEmpty() && addonsItems.get(i).getPrice().isEmpty()){
                        lokavenDialog.getToast(getString(R.string.text_please_input_price_greater_than_0));
                        return true;
                    }
                } else {
                    if (!addonsItems.get(i).getAddon().isEmpty() && addonsItems.get(i).getPrice().isEmpty()){
                        lokavenDialog.getToast(getString(R.string.text_please_input_price_item_greater_than_0));
                        return true;
                    }

                    if (!addonsItems.get(i).getAddon().isEmpty() && addonsItems.get(i).getQty() <= 0){
                        lokavenDialog.getToast(getString(R.string.text_please_input_item_amount_greater_than_0));
                        return true;
                    }
                }

                if (addonsItems.get(i).getAddon().isEmpty() && !addonsItems.get(i).getPrice().isEmpty()){
                    lokavenDialog.getToast(getString(R.string.text_please_input_addons_name));
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == btn_next) {
            createTourPackageItem.setAddons(createAddOnsAdapter.getItem());
            TourPackageLite tourPackageLite = new TourPackageLite(this);
            tourPackageLite.addDB(createTourPackageItem, urlImage);
            if (!validation()){
                if (mode == 1){
                    Intent intent = new Intent(this, CreatePrivateTripPreviewActivity.class);
                    intent.putStringArrayListExtra("imageList", urlImage);
                    intent.putExtra("tourpackage", createTourPackageItem);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(this, CreateOpenTripPreviewActivity.class);
                    intent.putStringArrayListExtra("imageList", urlImage);
                    intent.putExtra("tourpackage", createTourPackageItem);
                    startActivity(intent);
                    finish();
                }
            }
        } else if (v == ivBack) {
            onBackPressed();
            finish();
        } else if (v == tv_back) {
            onBackPressed();
            finish();
        } else if (v == tvAddMoreAddOns){
            addMoreAddOns();
            numberSchedules++;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mode == 1){
            Intent intent = new Intent(this, CreateTripPackageAddSchedulesActivity.class);
            intent.putStringArrayListExtra("imageList", urlImage);
            intent.putExtra("pagePrivate", 1);
            intent.putExtra("tourpackage", createTourPackageItem);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(this, CreateTripPackageAddSchedulesActivity.class);
            intent.putStringArrayListExtra("imageList", urlImage);
            intent.putExtra("tourpackage", createTourPackageItem);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onHandleSelection(int position) {
        this.position = position;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.text_select_picture)), PICK_IMAGES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGES) {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        boolean img = false;
                        for (int j = 0; j <  addOnsItems.get(position).getImage().size(); j++) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                if (addOnsItems.get(position).getImage().get(j).equals(RealPathHelper.getRealPathFromURI_API19(this, uri))) {
                                    img = true;
                                }
                            } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2 && Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                                if (addOnsItems.get(position).getImage().get(j).equals(RealPathHelper.getRealPathFromURI_API11to18(this, uri))) {
                                    img = true;
                                }
                            } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                                if (addOnsItems.get(position).getImage().get(j).equals(RealPathHelper.getRealPathFromURI_BelowAPI11(this, uri))) {
                                    img = true;
                                }
                            }
                        }

                        if (img == false) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                addOnsItems.get(position).getImage().add(RealPathHelper.getRealPathFromURI_API19(this, uri));
                            } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2 && Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                                addOnsItems.get(position).getImage().add(RealPathHelper.getRealPathFromURI_API11to18(this, uri));
                            } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                                addOnsItems.get(position).getImage().add(RealPathHelper.getRealPathFromURI_BelowAPI11(this, uri));
                            }
                        }
                    }
                } else if (data.getData() != null) {
                    Uri uri = data.getData();
                    boolean img = false;
                    if (addOnsItems != null){
                        if (addOnsItems.get(position).getImage() != null){
                            for (int j = 0; j <  addOnsItems.get(position).getImage().size(); j++) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    if (addOnsItems.get(position).getImage().get(j).equals(RealPathHelper.getRealPathFromURI_API19(this, uri))) {
                                        img = true;
                                    }
                                } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2 && Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                                    if (addOnsItems.get(position).getImage().get(j).equals(RealPathHelper.getRealPathFromURI_API11to18(this, uri))) {
                                        img = true;
                                    }
                                } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                                    if (addOnsItems.get(position).getImage().get(j).equals(RealPathHelper.getRealPathFromURI_BelowAPI11(this, uri))) {
                                        img = true;
                                    }
                                }
                            }
                        }
                    }

                    if (img == false) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            addOnsItems.get(position).getImage().add(RealPathHelper.getRealPathFromURI_API19(this, uri));
                        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2 && Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                            addOnsItems.get(position).getImage().add(RealPathHelper.getRealPathFromURI_API11to18(this, uri));
                        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                            addOnsItems.get(position).getImage().add(RealPathHelper.getRealPathFromURI_BelowAPI11(this, uri));
                        }
                    }

                    if (addOnsItems.get(position).getImage() != null ||  addOnsItems.get(position).getImage().size() != 0) {
                        addOnsItems.get(position).getImage().add("");
                    }

                    createAddOnsAdapter.notifyItemChanged(position);
                }
            }
        }
    }

    @Override
    public void onHandleSelection(int position, String text) {
        this.position = position;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.text_select_picture)), PICK_IMAGES);
    }
}
