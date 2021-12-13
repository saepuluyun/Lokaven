package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createtosandpolicies;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.InsuranceItem;
import com.aniqma.lokaventour.host.utils.dialog.LokavenDialog;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.CreateTourPackageItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.CustomPolicyItem;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.opentour.openinfo.CreateOpenBasicActivity;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privateinfo.CreatePrivateBasicActivity;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createimages.CreateImagesActivity;
import com.aniqma.lokaventour.host.network.local.createtourpackage.TourPackageLite;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class CreateToSAndPoliciesActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_add_more_policy, tv_back, tvDescriptionTerm;
    private RecyclerView recyclerView_policy;
    private ArrayList<CustomPolicyItem> item_policy;
    private CreateToSAndPoliciesAdapter policyAdapter;
    private int numberPolicy = 1, mode;
    private Button btn_next;
    private ArrayList<String> urlImage = null;
    private TextInputEditText
            etTermOfService,
            etTitleInsurance,
            etDescInsurance,
            etAgeRestriction,
            etAditionalCost;
    private LinearLayoutManager mLayoutManager;
    private Switch switchRefund;
    private CreateTourPackageItem createTourPackageItem;
    private View compTitleTOS, compTitleInsurance, compAppbar, compTitlePolicies, compBtn,
            compTitlePage, compTitleCustomPolicy, component_info_tos;
    private ImageView ivBack;
    private TextView tvTitle, tvTitleTos, tvDescriptionTos, tvTitleInsurance, tvDescriptionInsurance,
            tvTitlePolicies, tvDescriptionPolicies, tvTitleCustomPls, tvDescPls, tvReadPolicy;
    private TextView tvNumberOfStep, tvNameOfPage, tvDetailPage;
    private RelativeLayout rlStep1, rlStep2, rlStep3, rlStep4, rlStep5, rlStep6;
    private TextInputLayout tilTos;
    private LokavenDialog lokavenDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_private_trip_to_s_and_policies);
        init();
        createTourPackageItem = new CreateTourPackageItem();
        lokavenDialog = new LokavenDialog(this);
        mode = getIntent().getIntExtra("pagePrivate", 0);
        if (getIntent().getStringArrayListExtra("imageList") != null){
            urlImage = getIntent().getStringArrayListExtra("imageList");
        }

        if (getIntent().getExtras().getSerializable("tourpackage") != null){
            createTourPackageItem = (CreateTourPackageItem) getIntent().getExtras().getSerializable("tourpackage");
            if (createTourPackageItem.getCustom_policies() != null){
                if (createTourPackageItem.getCustom_policies().size() == 0){
                    addMorePolicy();
                } else {

                    item_policy.addAll(createTourPackageItem.getCustom_policies());
                }
            } else {
                addMorePolicy();
            }
        }

        if (getIntent().getStringArrayListExtra("imageList") != null){
            urlImage = getIntent().getStringArrayListExtra("imageList");
        }


        SetContent();
        SetListCustomPolicy();
        tv_add_more_policy.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        tvReadPolicy.setOnClickListener(this);

    }
    private void init() {
        tvReadPolicy = findViewById(R.id.tvReadPolicy);

        compAppbar = findViewById(R.id.compAppbar);
        ivBack = compAppbar.findViewById(R.id.ivBack);
        tvTitle = compAppbar.findViewById(R.id.tvTitle);

        compBtn = findViewById(R.id.compBtn);
        btn_next = compBtn.findViewById(R.id.btnBig);
        tv_back = compBtn.findViewById(R.id.tvTextButton);

        compTitleTOS = findViewById(R.id.compTitleTOS);
        tvTitleTos = compTitleTOS.findViewById(R.id.tvTitle);
        tvDescriptionTos = compTitleTOS.findViewById(R.id.tvDescription);

        compTitleInsurance = findViewById(R.id.compTitleInsurance);
        tvTitleInsurance = compTitleInsurance.findViewById(R.id.tvTitle);
        tvDescriptionInsurance = compTitleInsurance.findViewById(R.id.tvDescription);

        compTitlePolicies = findViewById(R.id.compTitlePolicies);
        tvTitlePolicies = compTitlePolicies.findViewById(R.id.tvTitle);
        tvDescriptionPolicies = compTitlePolicies.findViewById(R.id.tvDescription);

        compTitleCustomPolicy = findViewById(R.id.compTitleCustomPolicy);
        tvTitleCustomPls = compTitleCustomPolicy.findViewById(R.id.tvTitle);
        tvDescPls = compTitleCustomPolicy.findViewById(R.id.tvDescription);

        recyclerView_policy = findViewById(R.id.recyclerview_policy);
        tv_add_more_policy = findViewById(R.id.tv_add_more_policy);
        item_policy = new ArrayList<>();
        etTermOfService = findViewById(R.id.etTermOfService);
        etTitleInsurance = findViewById(R.id.etTitleInsurance);
        etDescInsurance = findViewById(R.id.etDescInsurance);
        etAgeRestriction = findViewById(R.id.etAgeRestriction);
        etAditionalCost = findViewById(R.id.etAditionalCost);
        switchRefund = findViewById(R.id.switchRefund);

        compTitlePage = findViewById(R.id.compTitlePage);
        tvNumberOfStep = compTitlePage.findViewById(R.id.tvNumberOfStep);
        tvDetailPage = compTitlePage.findViewById(R.id.tvDetailPage);

        component_info_tos = findViewById(R.id.component_info_tos);
        tvDescriptionTerm = component_info_tos.findViewById(R.id.tvDescription);

        tilTos = findViewById(R.id.tilTos);

        rlStep1 = compTitlePage.findViewById(R.id.rlStep1);
        rlStep2 = compTitlePage.findViewById(R.id.rlStep2);
        rlStep3 = compTitlePage.findViewById(R.id.rlStep3);
        rlStep4 = compTitlePage.findViewById(R.id.rlStep4);
        rlStep5 = compTitlePage.findViewById(R.id.rlStep5);
        rlStep6 = compTitlePage.findViewById(R.id.rlStep6);
        SetLinePage();
    }

    private void SetContent(){
        if (mode == 1){
            tvTitle.setText(getResources().getString(R.string.text_private_trip_title));
        }else {
            tvTitle.setText(getResources().getString(R.string.text_create_open_trip));
        }

        tvTitleTos.setText(getResources().getString(R.string.text_term_of_service));
        tvDescriptionTos.setText(getResources().getString(R.string.text_please_write_term_of_service));

        tvTitleInsurance.setText(getResources().getString(R.string.text_insurance));
        tvDescriptionInsurance.setText(getResources().getString(R.string.text_if_you_have_any_policies_related_to_insurance));

        tvTitlePolicies.setText(getResources().getString(R.string.text_policies));
        tvDescriptionPolicies.setText(getResources().getString(R.string.text_policies_desc));

        tvDescPls.setText(getResources().getString(R.string.text_policies_desc));
        tvDescriptionTerm.setText(getString(R.string.text_write_down_any_rules_or_terms_you));

        tvTitleCustomPls.setText(getResources().getString(R.string.text_policies));
        tvDetailPage.setText(getResources().getString(R.string.text_desc_tos_page));

        btn_next.setText(getResources().getString(R.string.btn_next));
        tv_back.setText(getResources().getString(R.string.text_back));

        etTermOfService.setText(createTourPackageItem.getTerms_of_service());
        switchRefund.setChecked(createTourPackageItem.isIs_refundable());
        etAgeRestriction.setText(createTourPackageItem.getAge_restriction());
        etAditionalCost.setText(createTourPackageItem.getCost_foreign_guest());
    }

    private void SetLinePage() {
        tvNumberOfStep.setText(Html.fromHtml(getResources().getString(R.string.text_step_2)));
        rlStep1.setBackgroundColor(getResources().getColor(R.color.LOK_Blue));
        rlStep2.setBackgroundColor(getResources().getColor(R.color.LOK_Navi_Blue));
        rlStep3.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
        rlStep4.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
        rlStep5.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
        rlStep6.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
    }

    private void SetListCustomPolicy() {
        recyclerView_policy.setHasFixedSize(true);
        recyclerView_policy.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView_policy.setLayoutManager(mLayoutManager);
        policyAdapter = new CreateToSAndPoliciesAdapter(item_policy, this);
        recyclerView_policy.setAdapter(policyAdapter);
    }

    private void addMorePolicy() {
        item_policy.add(new CustomPolicyItem());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v == ivBack) {
            onBackPressed();
        } else if (v == btn_next) {
            if (!validation()){
                List<CustomPolicyItem> policies = new ArrayList<>();
                for (int i= 0; i < item_policy.size(); i++){
                    if (!item_policy.get(i).getPolicy().isEmpty() && !item_policy.get(i).getPolicyName().isEmpty()){
                        policies.add(policyAdapter.getItemPosition().get(i));
                    }
                }

                InsuranceItem insuranceItem = new InsuranceItem();
                insuranceItem.setTitle(etTitleInsurance.getText().toString());
                insuranceItem.setDescription(etDescInsurance.getText().toString());

                createTourPackageItem.setTerms_of_service(etTermOfService.getText().toString());
                createTourPackageItem.setInsuranceItem(insuranceItem);
                createTourPackageItem.setAge_restriction(etAgeRestriction.getText().toString());
                createTourPackageItem.setCost_foreign_guest(etAditionalCost.getText().toString());
                createTourPackageItem.setIs_refundable(switchRefund.isChecked());
                createTourPackageItem.setCustom_policies(policies);

                TourPackageLite tourPackageLite = new TourPackageLite(this);
                tourPackageLite.addDB(createTourPackageItem, urlImage);

                Intent intent = new Intent(this, CreateImagesActivity.class);
                if (mode == 1){
                    intent.putExtra("tourpackage", createTourPackageItem);
                    intent.putExtra("pagePrivate", mode);
                    intent.putStringArrayListExtra("imageList", urlImage);
                    startActivity(intent);
                    finish();
                } else {
                    intent.putExtra("tourpackage", createTourPackageItem);
                    intent.putStringArrayListExtra("imageList", urlImage);
                    startActivity(intent);
                    finish();
                }
            }
        } else if (v == tv_add_more_policy) {
            numberPolicy++;
            addMorePolicy();
            policyAdapter.notifyDataSetChanged();
        }else if (v == tv_back){
            onBackPressed();
            finish();
        }else if (v == tvReadPolicy){
            lokavenDialog.getToast(getString(R.string.text_read_policy));
        }
    }

    public boolean validation(){
        boolean failed = false;
        if (etTermOfService.getText().toString().trim().isEmpty()){
            tilTos.setError(getString(R.string.text_please_input_term_of_service));
            tilTos.requestFocus();
            failed = true;
        } else {
            tilTos.setError(null);
        }

//        if (etAgeRestriction.getText().toString().trim().isEmpty()){
//            etAgeRestriction.setError("Please input age restricion");
//            etAgeRestriction.requestFocus();
//            failed = true;
//        }
//
//        if (etAditionalCost.getText().toString().trim().isEmpty()){
//            etAditionalCost.setError("Please input additional cost for foreign cost");
//            etAditionalCost.requestFocus();
//            failed = true;
//        }

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
            Intent intent = new Intent(this, CreatePrivateBasicActivity.class);
            intent.putStringArrayListExtra("imageList", urlImage);
            intent.putExtra("pagePrivate", 1);
            intent.putExtra("tourpackage", createTourPackageItem);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, CreateOpenBasicActivity.class);
            intent.putStringArrayListExtra("imageList", urlImage);
            intent.putExtra("tourpackage", createTourPackageItem);
            startActivity(intent);
            finish();
        }
    }
}
