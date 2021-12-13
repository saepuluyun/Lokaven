package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.choosepackagetype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.utils.dialog.LokavenDialog;
import com.aniqma.lokaventour.host.utils.animation.LoadingPage;
import com.aniqma.lokaventour.host.model.item.tourhostservice.DataDetailHostItem;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.opentour.openinfo.CreateOpenBasicActivity;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privateinfo.CreatePrivateBasicActivity;
import com.aniqma.lokaventour.host.network.servicesutils.LokavenSession;
import com.aniqma.lokaventour.host.network.services.hostprofile.detailhost.DetailHostService;
import com.aniqma.lokaventour.host.network.services.hostprofile.detailhost.IViewDetailHostService;

public class ChoosePackageTypeActivity extends AppCompatActivity implements View.OnClickListener, IViewDetailHostService {
    private ImageView ivBack, ivPrivateTour, ivOpenTour;
    private View compAppbar;
    private LinearLayout layoutOpen, layoutPrivate;
    private TextView tvTitle;
    private DataDetailHostItem dataDetailHostItem;
    private LokavenDialog lokavenDialog;
    private LoadingPage loadingPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_package_type);
        loadingPage = new LoadingPage(this);
        init();
        setContent();
        ivBack.setOnClickListener(this);
        layoutOpen.setOnClickListener(this);
        layoutPrivate.setOnClickListener(this);
    }

    private void setContent() {
        lokavenDialog = new LokavenDialog(this);
        LokavenSession lokavenSession = new LokavenSession(this);

        tvTitle.setText(getString(R.string.text_choose_package_tyoe));

        IViewDetailHostService iViewDetailHostService = this;
        DetailHostService detailHostService = new DetailHostService(iViewDetailHostService, this);
        detailHostService.getDetailHost(lokavenSession.getHostId());
        loadingPage.start();
    }

    private void init() {
        tvTitle = findViewById(R.id.titleToolbar);
        layoutOpen = findViewById(R.id.layoutOpen);
        layoutPrivate = findViewById(R.id.layoutPrivate);
        compAppbar = findViewById(R.id.compAppbar);
        ivBack = compAppbar.findViewById(R.id.ivBack);
        tvTitle = compAppbar.findViewById(R.id.tvTitle);
        ivPrivateTour = findViewById(R.id.ivPrivateTour);
        ivOpenTour = findViewById(R.id.ivOpenTour);
    }

    @Override
    public void onClick(View v) {
        if (v == ivBack) {
            onBackPressed();
            finish();
        }else if (v == layoutOpen){
            if (dataDetailHostItem.isVerified()){
                startActivity(new Intent(this, CreateOpenBasicActivity.class));
                finish();
            } else {
                lokavenDialog.getToast(getString(R.string.text_your_account_not_been_verify));
            }

        }else if (v == layoutPrivate){
            if (dataDetailHostItem.isVerified()){
                startActivity(new Intent(this, CreatePrivateBasicActivity.class));
                finish();
            } else {
                lokavenDialog.getToast(getString(R.string.text_your_account_not_been_verify));
            }
        }
    }

    @Override
    public void onSuccessGetHostDetail(DataDetailHostItem detailHost) {
        dataDetailHostItem = detailHost;
        loadingPage.stop();
    }
}
