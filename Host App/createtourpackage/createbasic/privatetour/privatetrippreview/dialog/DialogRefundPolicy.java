package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privatetrippreview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.aniqma.lokaventour.host.R;

public class DialogRefundPolicy extends Dialog {

    private Context context;
    private TextView tvRefund;


    public DialogRefundPolicy(@NonNull Context context) {
        super(context);
        this.context = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.component_dialog_informations);
        init();

        tvRefund.setText("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
                "when an unknown printer took a galley of type and scrambled it to make a type specimen book. " +
                "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.");
    }

    private void init() {
        tvRefund = findViewById(R.id.tvDescription);
    }
}
