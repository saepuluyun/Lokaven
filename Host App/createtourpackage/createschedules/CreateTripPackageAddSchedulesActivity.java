package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createschedules;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.utils.dialog.LokavenDialog;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.CreateTourPackageItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.SchedulesItem;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createimages.CreateImagesActivity;
import com.aniqma.lokaventour.host.network.local.createtourpackage.TourPackageLite;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.createtourpackage.CreateOpenTripPreviewPresenter;
import com.aniqma.lokaventour.host.network.services.tourpackageservice.createtourpackage.IViewCreateOpenTripPreview;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createaddons.CreateAddOnsActivity;

import java.util.ArrayList;
import java.util.List;

public class CreateTripPackageAddSchedulesActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView listSchedules;
    private TextView tvAddMoreSchedules;
    private int numberSchedules = 1;

    private List<SchedulesItem> schedulesItems;
    private CreateTourSchedulesPrivateAdapter createTourSchedulesPrivateAdapter;
    private AddScheduleOpenAdapter addScheduleOpenAdapter;
    private LinearLayoutManager mLayoutManager;

    private TextView tv_back;
    private Button btn_next;
    private int mode;

    private CreateTourPackageItem createTourPackageItem;
    private boolean cliked = false;
    private ArrayList<String> urlImage = null;
    private CreateOpenTripPreviewPresenter presenter;
    private IViewCreateOpenTripPreview iViewCreateOpenTripPreview;
    private View compTitlePage, compAppbar, compBtn, compTitle, component_info_schedules;
    private ImageView ivBack;
    private TextView tvTitle, tvNumberOfStep, tvNameOfPage, tvDetailPage, tvTitleSchedules, tvDescScheduels, tvDescriptionSchedules;
    private RelativeLayout rlStep1, rlStep2, rlStep3, rlStep4, rlStep5, rlStep6;
    private LokavenDialog lokavenDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour_package_add_schedules);
        Init();
        createTourPackageItem = new CreateTourPackageItem();
        lokavenDialog = new LokavenDialog(this);
        mode = getIntent().getIntExtra("pagePrivate", 0);
        if (getIntent().getStringArrayListExtra("imageList") != null){
            urlImage = getIntent().getStringArrayListExtra("imageList");
        }

        if (getIntent().getSerializableExtra("tourpackage") != null){
            createTourPackageItem = (CreateTourPackageItem) getIntent().getSerializableExtra("tourpackage");
            SetListSchedules();
            if (createTourPackageItem.getSchedules().size() != 0){
                if (createTourPackageItem.getSchedules().size() == 0){
                    AddMoreSchedules();
                }
            } else {
                AddMoreSchedules();
            }
        }

        SetContent();

        ivBack.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        tvAddMoreSchedules.setOnClickListener(this);

    }

    private void SetListSchedules() {
        mLayoutManager = new LinearLayoutManager(this);
        listSchedules.setLayoutManager(mLayoutManager);
        createTourSchedulesPrivateAdapter = new CreateTourSchedulesPrivateAdapter(this, createTourPackageItem.getSchedules());
        addScheduleOpenAdapter = new AddScheduleOpenAdapter(createTourPackageItem.getSchedules(), this);
        if (mode == 1){
            listSchedules.setAdapter(createTourSchedulesPrivateAdapter);
        }else {
            listSchedules.setAdapter(addScheduleOpenAdapter);
        }
    }

    private void SetContent() {
        tvNumberOfStep.setText(Html.fromHtml(getResources().getString(R.string.text_step_4)));
        tvDetailPage.setText(getString(R.string.text_add_schedule_desc));
        tvTitleSchedules.setText(getString(R.string.text_active_schedules));
        tvDescScheduels.setText(getString(R.string.text_schedules_desc));
        btn_next.setText(getResources().getString(R.string.btn_next));
        tv_back.setText(getResources().getString(R.string.text_back));
        if (mode == 1){
            tvTitle.setText(getResources().getString(R.string.text_private_trip_title));
        }else {
            tvTitle.setText(getResources().getString(R.string.text_create_open_trip));
        }
        tvDescriptionSchedules.setText(getString(R.string.text_you_can_have_as_many_schedules_as_you_like));
    }

    private void addMoreSchedules() {
        SchedulesItem schedulesItem = new SchedulesItem();
        createTourPackageItem.getSchedules().add(schedulesItem);
    }

    private void Init() {
        schedulesItems = new ArrayList<>();
        listSchedules = findViewById(R.id.listSchedules);
        tvAddMoreSchedules = findViewById(R.id.tvAddMoreSchedules);

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
        tvTitleSchedules = compTitle.findViewById(R.id.tvTitle);
        tvDescScheduels = compTitle.findViewById(R.id.tvDescription);

        component_info_schedules = findViewById(R.id.component_info_schedules);
        tvDescriptionSchedules = component_info_schedules.findViewById(R.id.tvDescription);
    }

    private void SetLinePage() {
        rlStep1.setBackgroundColor(getResources().getColor(R.color.LOK_Blue));
        rlStep2.setBackgroundColor(getResources().getColor(R.color.LOK_Navi_Blue));
        rlStep3.setBackgroundColor(getResources().getColor(R.color.LOK_Red));
        rlStep4.setBackgroundColor(getResources().getColor(R.color.LOK_Teal));
        rlStep5.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
        rlStep6.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
    }

    @Override
    public void onClick(View v) {
        if (v == ivBack)
        {
            onBackPressed();
            finish();
        }
        else if (v == btn_next) {
            if (mode == 1){
                if (!validationPrivate()){
                    createTourPackageItem.setSchedules(createTourSchedulesPrivateAdapter.getItem());
                    TourPackageLite tourPackageLite = new TourPackageLite(this);
                    tourPackageLite.addDB(createTourPackageItem, urlImage);
                    Intent intent = new Intent(this, CreateAddOnsActivity.class);
                    intent.putExtra("tourpackage", createTourPackageItem);
                    intent.putStringArrayListExtra("imageList", urlImage);
                    intent.putExtra("pagePrivate", 1);
                    startActivity(intent);
                    finish();
                }
            }else {
                if (!validationOpen()){
                    createTourPackageItem.setSchedules(addScheduleOpenAdapter.getItem());
                    TourPackageLite tourPackageLite = new TourPackageLite(this);
                    tourPackageLite.addDB(createTourPackageItem, urlImage);
                    Intent intent = new Intent(this, CreateAddOnsActivity.class);
                    intent.putExtra("tourpackage", createTourPackageItem);
                    intent.putStringArrayListExtra("imageList", urlImage);
                    startActivity(intent);
                    finish();
                }

            }
        }
        else if (v == tv_back)
        {
            onBackPressed();
            finish();
        }
        else if (v == tvAddMoreSchedules)
        {
            AddMoreSchedules();
        }
    }

    private void AddMoreSchedules(){
        numberSchedules++;
        addMoreSchedules();
        createTourSchedulesPrivateAdapter.addItem();
        addScheduleOpenAdapter.addItem();
    }

    public boolean validationOpen(){
        boolean failed= false;
        for (int i = 0; i <addScheduleOpenAdapter.getItem().size(); i++) {
            if (addScheduleOpenAdapter.getItem().get(i).getStartDate() == null || addScheduleOpenAdapter.getItem().get(i).getStartDate().isEmpty()) {
                lokavenDialog.getToast(getString(R.string.text_please_input_start_date));
                failed = true;
                break;
            }

            if (addScheduleOpenAdapter.getItem().get(i).getEndDate() == null || addScheduleOpenAdapter.getItem().get(i).getEndDate().isEmpty()) {
                lokavenDialog.getToast(getString(R.string.text_please_input_end_date));
                failed = true;
                break;
            }

            if (addScheduleOpenAdapter.getItem().get(i).getMinQuota() == null || addScheduleOpenAdapter.getItem().get(i).getMinQuota().isEmpty()) {
                lokavenDialog.getToast(getString(R.string.text_please_input_min_quota));
                failed = true;
                break;
            }

            if ((addScheduleOpenAdapter.getItem().get(i).getMinQuota() != null && addScheduleOpenAdapter.getItem().get(i).getMaxQuota() != null) && (!addScheduleOpenAdapter.getItem().get(i).getMinQuota().isEmpty() && !addScheduleOpenAdapter.getItem().get(i).getMaxQuota().isEmpty())) {
                if (Integer.valueOf(addScheduleOpenAdapter.getItem().get(i).getMinQuota().trim()) > Integer.valueOf(addScheduleOpenAdapter.getItem().get(i).getMaxQuota().trim())) {
                    lokavenDialog.getToast(getString(R.string.text_please_input_max_quota_greater_than_min_quota));
                        failed = true;
                }
            }
        }

        if (failed == true){
            return true;
        } else {
            return false;
        }
    }

    public boolean validationPrivate(){
        boolean failed= false;
        for (int i = 0; i <addScheduleOpenAdapter.getItem().size(); i++) {
            if (addScheduleOpenAdapter.getItem().get(i).getStartDate().isEmpty()) {
                lokavenDialog.getToast(getString(R.string.text_please_input_start_date));
                failed = true;
            }

            if (addScheduleOpenAdapter.getItem().get(i).getEndDate().isEmpty()) {
                lokavenDialog.getToast(getString(R.string.text_please_input_end_date));
                failed = true;
            }

        }

        if (failed == true){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mode == 1){
            Intent intent = new Intent(this, CreateImagesActivity.class);
            intent.putStringArrayListExtra("imageList", urlImage);
            intent.putExtra("pagePrivate", 1);
            intent.putExtra("tourpackage", createTourPackageItem);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, CreateImagesActivity.class);
            intent.putStringArrayListExtra("imageList", urlImage);
            intent.putExtra("tourpackage", createTourPackageItem);
            startActivity(intent);
            finish();
        }
    }
}
