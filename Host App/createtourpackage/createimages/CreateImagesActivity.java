package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createimages;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.helper.image.RealPathHelper;
import com.aniqma.lokaventour.host.utils.dialog.LokavenDialog;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.CreateTourPackageItem;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.MediasItem;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createtosandpolicies.CreateToSAndPoliciesActivity;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createschedules.CreateTripPackageAddSchedulesActivity;
import com.aniqma.lokaventour.host.network.local.createtourpackage.TourPackageLite;
import com.aniqma.lokaventour.host.network.servicesutils.LokavenSession;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class CreateImagesActivity extends AppCompatActivity implements View.OnClickListener, CreateImagesAdapter.CallbackInterface {

    private static final String TAG = "";
    private ArrayList<MediasItem> arrayList;
    private RecyclerView recyclerView,
            rvVideo;
    private CreateImagesAdapter adapter;
    private CreateVideosAdapter adapterVideo;
    private RelativeLayout relAddImages;
    private ArrayList<String> urlImage = null;
    private Button btn_next;
    private TextView tv_back, tvTitleVideo, tvDescriptionVideo;
    private int mode ;
    private LokavenSession lokavenSession;
    private View compTitlePage, compAppbar, compBtn, compTitle, compTitleImage;
    private ImageView ivBack;
    private TextView tvTitle, tvNumberOfStep, tvNameOfPage, tvDetailPage, tvTitleImage, tvDescriptionImage;
    private RelativeLayout rlStep1, rlStep2, rlStep3, rlStep4, rlStep5, rlStep6;
    private CreateTourPackageItem createTourPackageItem;
    private RecyclerView.LayoutManager layoutManager;
    private int PICK_IMAGES = 1;
    private LokavenDialog lokavenDialog;
    private TextInputEditText etUrlVideo;
    private TextInputLayout tilUrlVideo;
    private LinearLayout llAddVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour_package_choose_image);
        Init();
        urlImage = new ArrayList<>();
        createTourPackageItem = new CreateTourPackageItem();
        lokavenDialog = new LokavenDialog(this);
        lokavenSession = new LokavenSession(this);
        mode = getIntent().getIntExtra("pagePrivate", 0);
        SetContent();

        if (getIntent().getSerializableExtra("tourpackage") != null){
            createTourPackageItem = (CreateTourPackageItem) getIntent().getSerializableExtra("tourpackage");
            isStoragePermissionGranted();

        }

        if (getIntent().getStringArrayListExtra("imageList") != null){
            urlImage = getIntent().getStringArrayListExtra("imageList");

            if (urlImage == null || urlImage.size() == 0) {
                recyclerView.setVisibility(View.GONE);
                rvVideo.setVisibility(View.GONE);
                relAddImages.setVisibility(View.VISIBLE);
            } else {
                relAddImages.setVisibility(View.GONE);
                rvVideo.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            SetListImage();

        }

        relAddImages.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        llAddVideo.setOnClickListener(this);
    }

    private void SetListImage() {
        recyclerView.setHasFixedSize(true);
        layoutManager =  new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CreateImagesAdapter(this, urlImage);
        recyclerView.setAdapter(adapter);

        rvVideo.setHasFixedSize(true);
        layoutManager =  new GridLayoutManager(this, 3);
        rvVideo.setLayoutManager(layoutManager);
        adapterVideo = new CreateVideosAdapter(this, createTourPackageItem.getMedias());
        rvVideo.setAdapter(adapterVideo);
    }

    private void SetContent() {
        tvTitleVideo.setText(getResources().getString(R.string.text_video));
        tvDescriptionVideo.setText(getResources().getString(R.string.text_you_can_embed_videos_from_youtube));
        tvNumberOfStep.setText(Html.fromHtml(getResources().getString(R.string.text_step_3)));
        tvDetailPage.setText(getString(R.string.text_choose_image_desc));
        tvTitleImage.setText(getString(R.string.text_images));
        tvDescriptionImage.setText(getString(R.string.text_please_choose_images));
        if (mode == 1){
            tvTitle.setText(getResources().getString(R.string.text_private_trip_title));
        }else {
            tvTitle.setText(getResources().getString(R.string.text_create_open_trip));
        }
        btn_next.setText(getResources().getString(R.string.btn_next));
        tv_back.setText(getResources().getString(R.string.text_back));
    }

    private void Init() {
        compTitle = findViewById(R.id.compTitle);
        tvTitleImage = compTitle.findViewById(R.id.tvTitle);
        tvDescriptionImage = compTitle.findViewById(R.id.tvDescription);

        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        relAddImages = findViewById(R.id.relAddImages);

        compAppbar = findViewById(R.id.compAppbar);
        ivBack = compAppbar.findViewById(R.id.ivBack);
        tvTitle = compAppbar.findViewById(R.id.tvTitle);

        compTitlePage = findViewById(R.id.compTitlePage);
        tvNumberOfStep = compTitlePage.findViewById(R.id.tvNumberOfStep);
        tvDetailPage = compTitlePage.findViewById(R.id.tvDetailPage);

        compTitleImage = findViewById(R.id.compTitleImage);
        tvTitleVideo = compTitleImage.findViewById(R.id.tvTitle);
        tvDescriptionVideo = compTitleImage.findViewById(R.id.tvDescription);
        rvVideo = findViewById(R.id.rvVideo);
        etUrlVideo = findViewById(R.id.etUrlVideo);
        llAddVideo = findViewById(R.id.llAddVideo);
        tilUrlVideo = findViewById(R.id.tilUrlVideo);

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
    }

    private void SetLinePage() {
        rlStep1.setBackgroundColor(getResources().getColor(R.color.LOK_Blue));
        rlStep2.setBackgroundColor(getResources().getColor(R.color.LOK_Navi_Blue));
        rlStep3.setBackgroundColor(getResources().getColor(R.color.LOK_Red));
        rlStep4.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
        rlStep5.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
        rlStep6.setBackgroundColor(getResources().getColor(R.color.LOK_Light_Grey));
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGES) {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        boolean img = false;
                        for ( int j = 0; j < urlImage.size(); j ++){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                                if (urlImage.get(j).equals(RealPathHelper.getRealPathFromURI_API19(this, uri))){
                                    img = true;
                                }
                            } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2 && Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
                                if (urlImage.get(j).equals(RealPathHelper.getRealPathFromURI_API11to18(this, uri))){
                                    img = true;
                                }
                            } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB){
                                if (urlImage.get(j).equals(RealPathHelper.getRealPathFromURI_BelowAPI11(this, uri))){
                                    img = true;
                                }
                            }
                        }

                        if (img == false){
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                                urlImage.add(RealPathHelper.getRealPathFromURI_API19(this, uri));
                            } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2 && Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
                                urlImage.add(RealPathHelper.getRealPathFromURI_API11to18(this, uri));
                            } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB){
                                urlImage.add(RealPathHelper.getRealPathFromURI_BelowAPI11(this, uri));
                            }
                        }
                    }
                } else if (data.getData() != null) {
                    Uri uri = data.getData();
                    boolean img = false;
                    for ( int j = 0; j < urlImage.size(); j ++){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                            if (urlImage.get(j).equals(RealPathHelper.getRealPathFromURI_API19(this, uri))){
                                img = true;
                            }
                        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2 && Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
                            if (urlImage.get(j).equals(RealPathHelper.getRealPathFromURI_API11to18(this, uri))){
                                img = true;
                            }
                        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB){
                            if (urlImage.get(j).equals(RealPathHelper.getRealPathFromURI_BelowAPI11(this, uri))){
                                img = true;
                            }
                        }
                    }

                    if (img == false){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                            urlImage.add(RealPathHelper.getRealPathFromURI_API19(this, uri));
                        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2 && Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
                            urlImage.add(RealPathHelper.getRealPathFromURI_API11to18(this, uri));
                        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB){
                            urlImage.add(RealPathHelper.getRealPathFromURI_BelowAPI11(this, uri));
                        }
                    }
                }
            }
        }

        SetListImage();

        if (urlImage == null || urlImage.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            relAddImages.setVisibility(View.VISIBLE);
        } else {
            urlImage.add("");
            relAddImages.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onHandleSelection(int position, String text) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.text_select_picture)), PICK_IMAGES);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

    @Override
    public void onClick(View v) {
        if (v == relAddImages) {
            if (mode == 1){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.text_select_picture)), PICK_IMAGES);
            } else {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.text_select_picture)), PICK_IMAGES);
            }
        }
        else if (v == btn_next) {
            if (urlImage == null || urlImage.size() < 2){
                lokavenDialog.getToast(getString(R.string.text_please_input_image));
            } else {
                if (mode == 1){
                    TourPackageLite tourPackageLite = new TourPackageLite(this);
                    tourPackageLite.addDB(createTourPackageItem, urlImage);
                    Intent intent = new Intent(this, CreateTripPackageAddSchedulesActivity.class);
                    intent.putExtra("pagePrivate", 1);
                    intent.putStringArrayListExtra("imageList", urlImage);
                    intent.putExtra("tourpackage", createTourPackageItem);
                    startActivity(intent);
                    finish();
                }else {
                    TourPackageLite tourPackageLite = new TourPackageLite(this);
                    tourPackageLite.addDB(createTourPackageItem, urlImage);
                    Intent intent = new Intent(this, CreateTripPackageAddSchedulesActivity.class);
                    intent.putExtra("tourpackage", createTourPackageItem);
                    intent.putStringArrayListExtra("imageList", urlImage);
                    startActivity(intent);
                    finish();
                }
            }
        }
        else if (v == ivBack)
        {
            onBackPressed();
        }
        else if (v == tv_back)
        {
            onBackPressed();
            finish();
        }
        else if (v == llAddVideo)
        {
            if (etUrlVideo.getText().toString().trim().isEmpty()){
                tilUrlVideo.setError(getResources().getString(R.string.text_please_input_url_video_from_youtube));
                tilUrlVideo.requestFocus();
            } else {
                tilUrlVideo.setError(null);
                MediasItem mediasItem = new MediasItem();
                mediasItem.setUrl(etUrlVideo.getText().toString().trim());
                mediasItem.setType("video");
                createTourPackageItem.getMedias().add(mediasItem);
                rvVideo.setVisibility(View.VISIBLE);
                adapterVideo.notifyDataSetChanged();
                etUrlVideo.setText("");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isStoragePermissionGranted();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mode == 1){
            Intent intent = new Intent(this, CreateToSAndPoliciesActivity.class);
            intent.putStringArrayListExtra("imageList", urlImage);
            intent.putExtra("pagePrivate", 1);
            intent.putExtra("tourpackage", createTourPackageItem);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, CreateToSAndPoliciesActivity.class);
            intent.putStringArrayListExtra("imageList", urlImage);
            intent.putExtra("tourpackage", createTourPackageItem);
            startActivity(intent);
            finish();
        }
    }

}
