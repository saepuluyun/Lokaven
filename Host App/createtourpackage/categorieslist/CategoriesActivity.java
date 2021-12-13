package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.categorieslist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.utils.animation.ShimmerEffect;
import com.aniqma.lokaventour.host.utils.dialog.LokavenDialog;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.CreateTourPackageItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.DataTourCategoryItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.createTour.CategoriesItem;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.opentour.openinfo.CreateOpenBasicActivity;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privateinfo.CreatePrivateBasicActivity;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.createtourpackage.CreatePrivateTripBasicInfoPresenter;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.createtourpackage.IViewCreatePrivateTripBasicInfo;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity implements IViewCreatePrivateTripBasicInfo, View.OnClickListener {

    private RecyclerView rvCategories;
    private View component_appbar;
    private ImageView ivBack,
            ivPlus;
    private TextView tvTitle;
    private TextInputEditText etSearch;

    private ShimmerFrameLayout shimmerContainer;
    private LinearLayout llContent;

    private IViewCreatePrivateTripBasicInfo iViewCreatePrivateTripBasicInfo;
    private CreatePrivateTripBasicInfoPresenter presenter;

    private ShimmerEffect shimmerEffect;
    private LokavenDialog lokavenDialog;

    private int mode = 0;
    private ArrayList<String> urlImage = null;
    private CreateTourPackageItem createTourPackageItem;
    private CategoriesAdapter categoriesAdapter;
    private List<Integer> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        init();

        shimmerEffect = new ShimmerEffect(this, shimmerContainer, llContent);
        lokavenDialog = new LokavenDialog(this);

        mode = getIntent().getIntExtra("pagePrivate", 0);
        if (getIntent().getStringArrayListExtra("imageList") != null){
            urlImage = getIntent().getStringArrayListExtra("imageList");
        }

        if (getIntent().getSerializableExtra("tourpackage") != null){
            createTourPackageItem = (CreateTourPackageItem) getIntent().getSerializableExtra("tourpackage");
        }

        shimmerEffect.startShimmer();
        iViewCreatePrivateTripBasicInfo = this;
        presenter = new CreatePrivateTripBasicInfoPresenter(this, iViewCreatePrivateTripBasicInfo);
        presenter.onGetDataCategoryTour();

        contentView();

        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                categoriesAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ivBack.setOnClickListener(this);
        ivPlus.setOnClickListener(this);
    }

    private void contentView() {
        tvTitle.setText(getResources().getString(R.string.text_list_categories));
    }

    private void init() {
        shimmerContainer = findViewById(R.id.shimmerContainer);
        llContent = findViewById(R.id.llContent);

        rvCategories = findViewById(R.id.rvCategories);
        component_appbar = findViewById(R.id.component_appbar);
        ivBack = component_appbar.findViewById(R.id.ivBack);
        tvTitle = component_appbar.findViewById(R.id.tvTitle);
        ivPlus = component_appbar.findViewById(R.id.ivPlus);

        etSearch = findViewById(R.id.etSearch);
    }

    private void addImage() {
        categories.add(R.drawable.image_0);
        categories.add(R.drawable.image_1);
        categories.add(R.drawable.image_2);
        categories.add(R.drawable.image_3);
        categories.add(R.drawable.image_4);
        categories.add(R.drawable.image_5);
        categories.add(R.drawable.image_6);
        categories.add(R.drawable.image_7);
        categories.add(R.drawable.image_8);
        categories.add(R.drawable.image_9);
        categories.add(R.drawable.image_10);
        categories.add(R.drawable.image_11);
        categories.add(R.drawable.image_12);
        categories.add(R.drawable.image_13);
        categories.add(R.drawable.image_14);
        categories.add(R.drawable.image_15);
        categories.add(R.drawable.image_16);
        categories.add(R.drawable.image_17);
        categories.add(R.drawable.image_18);
        categories.add(R.drawable.image_19);
        categories.add(R.drawable.image_20);
        categories.add(R.drawable.image_21);
        categories.add(R.drawable.image_22);
        categories.add(R.drawable.image_23);
        categories.add(R.drawable.image_24);
        categories.add(R.drawable.image_25);
        categories.add(R.drawable.image_26);
        categories.add(R.drawable.image_27);
        categories.add(R.drawable.image_28);
        categories.add(R.drawable.image_29);
        categories.add(R.drawable.image_30);
        categories.add(R.drawable.image_31);
        categories.add(R.drawable.image_32);
        categories.add(R.drawable.image_33);
        categories.add(R.drawable.image_34);
        categories.add(R.drawable.image_35);
    }

    @Override
    public void onSuccessListCategoty(List<DataTourCategoryItem> data) {
        // Multiple select category

        addImage();

        List<CategoriesItem> categoriesItems= new ArrayList<>();
        for (int i=0; i<data.size(); i++) {
            Collections.shuffle(categories);
            CategoriesItem categoriesItem = new CategoriesItem();
            categoriesItem.setCategories(data.get(i).getCategory());
            categoriesItem.setCategory_image_drawable(categories.get(i));
            categoriesItems.add(categoriesItem);
        }

        if (createTourPackageItem.getCategories().size() != 0){
            for (int i=0; i < categoriesItems.size(); i++) {
                for (int j = 0; j < createTourPackageItem.getCategories().size(); j++){
                    if (categoriesItems.get(i).getCategories().equals(createTourPackageItem.getCategories().get(j))){
                        categoriesItems.get(i).setCliked(true);
                        break;
                    }
                }
            }
        }

        rvCategories.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCategories.setLayoutManager(layoutManager);
        categoriesAdapter = new CategoriesAdapter(categoriesItems, this);
        rvCategories.setAdapter(categoriesAdapter);
        shimmerEffect.stopShimmer();

    }

    @Override
    public void onErrorData(String title, String message) {
        shimmerEffect.stopShimmer();
        lokavenDialog.dialogError(title, message);
    }

    @Override
    public void onClick(View view) {
        if (view == ivBack){
            if(mode == 1){
                Intent intent = new Intent(this, CreatePrivateBasicActivity.class);
                intent.putExtra("tourpackage", createTourPackageItem);
                intent.putExtra("pagePrivate", 1);
                intent.putStringArrayListExtra("imageList", urlImage);
                startActivity(intent);
                finish();
            }  else {
                Intent intent = new Intent(this, CreateOpenBasicActivity.class);
                intent.putExtra("tourpackage", createTourPackageItem);
                intent.putStringArrayListExtra("imageList", urlImage);
                startActivity(intent);
                finish();
            }
        } else if (view == ivPlus){
            List<String> list = new ArrayList<>();
            for (int i = 0; i < categoriesAdapter.getData().size(); i++){
                if (categoriesAdapter.getData().get(i).isCliked()){
                    list.add(categoriesAdapter.getData().get(i).getCategories());
                }
            }

            if (list.size() != 0){
                createTourPackageItem.setCategories(list);

                if(mode == 1){
                    Intent intent = new Intent(this, CreatePrivateBasicActivity.class);
                    intent.putExtra("tourpackage", createTourPackageItem);
                    intent.putExtra("pagePrivate", 1);
                    intent.putStringArrayListExtra("imageList", urlImage);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(this, CreateOpenBasicActivity.class);
                    intent.putExtra("tourpackage", createTourPackageItem);
                    intent.putStringArrayListExtra("imageList", urlImage);
                    startActivity(intent);
                    finish();
                }
            } else {
                lokavenDialog.getToast(getResources().getString(R.string.text_please_input_categories));
            }

        }
    }
}