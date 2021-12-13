package com.aniqma.lokaventour.modul.activity.booking.participantsinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.BookingProcessItem;
import com.aniqma.lokaventour.model.item.tourexplorer.TourPackageItem;
import com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingdetails.opentour.OpenDetailsBookingActivity;
import com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingdetails.privatetour.PrivateDetailsBookingActivity;
import com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingsummary.SummaryBookingActivity;

public class ParticipantsInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title_toolbar;
    private ImageView back_button;
    private Button btn_save_and_back;
    private String textError = "Form can't be empty";
    private View component_btn_save_back,
            component_btn_big;
    private RecyclerView rvParticipants;
    private ListParticipantsAdapter listParticipants;
    private RecyclerView.LayoutManager layoutManager;

    private BookingProcessItem bookingProcessItem;
    private TourPackageItem tourPackageItem;
    private String typeTour;
    private String from = "";
    private boolean onclick = true;
    private String quota_left="", max="", min="";
    private BookingProcessItem bookingProcessTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants_info);

        if (getIntent() != null) {
            from = getIntent().getExtras().getString("from", "");
            bookingProcessItem = (BookingProcessItem) getIntent().getSerializableExtra("bookingProcessItem");
            bookingProcessTemp = (BookingProcessItem) getIntent().getSerializableExtra("participant");
            tourPackageItem = (TourPackageItem) getIntent().getSerializableExtra("tourPackageItem");
        }

        typeTour = tourPackageItem.getTypeTour();

        init();
        setAddParticipantsList();
        btn_save_and_back.setText(R.string.text_save_and_back);
        title_toolbar.setText(getText(R.string.text_participants_info));
        back_button.setOnClickListener(this);
        btn_save_and_back.setOnClickListener(this);
    }

    private void setAddParticipantsList() {
        if (bookingProcessItem.getParticipants().size() != 1 || bookingProcessItem.getParticipants().size() > 0){
            rvParticipants.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            rvParticipants.setLayoutManager(layoutManager);
            listParticipants = new ListParticipantsAdapter(this, bookingProcessItem, tourPackageItem);
            rvParticipants.setAdapter(listParticipants);
        }
    }

    private void init() {
        component_btn_save_back = findViewById(R.id.component_btn_save_back);
        component_btn_big = component_btn_save_back.findViewById(R.id.component_btn_big);
        btn_save_and_back = component_btn_big.findViewById(R.id.btnBig);
        title_toolbar = findViewById(R.id.tvTitle);
        back_button = findViewById(R.id.ivBack);

        rvParticipants = findViewById(R.id.rvParticipants);
    }

    public boolean isEmpty(EditText editText) {
        return TextUtils.isEmpty(editText.getText());
    }

    @Override
    public void onClick(View v) {
        if (v == back_button){
            onBackPressed();
        }else if (v == btn_save_and_back){
            if (listParticipants.getItemParticipants() != null){
                bookingProcessItem.setParticipants(listParticipants.getItemParticipants());

                if (from.equals("summary")){
                    Intent i = new Intent(this, SummaryBookingActivity.class);
                    i.putExtra("bookingProcessItem", bookingProcessItem);
                    i.putExtra("tourPackageItem", tourPackageItem);
                    setResult(RESULT_OK,i);
                    finish();
                } else {
                    Intent i;
                    if (typeTour.equals("open")) {
                        i = new Intent(this, OpenDetailsBookingActivity.class);
                    } else {
                        i = new Intent(this, PrivateDetailsBookingActivity.class);
                    }
                    i.putExtra("bookingProcessItem", bookingProcessItem);
                    i.putExtra("tourPackageItem", tourPackageItem);
                    setResult(RESULT_OK,i);
                    finish();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (from.equals("summary")){
            Intent i = new Intent(this, SummaryBookingActivity.class);
            i.putExtra("bookingProcessItem", bookingProcessTemp);
            i.putExtra("tourPackageItem", tourPackageItem);
            setResult(RESULT_CANCELED,i);
            finish();
        } else {
            Intent i;
            if (typeTour.equals("open")) {
                i = new Intent(this, OpenDetailsBookingActivity.class);
            } else {
                i = new Intent(this, PrivateDetailsBookingActivity.class);
            }
            i.putExtra("bookingProcessItem", bookingProcessTemp);
            i.putExtra("tourPackageItem", tourPackageItem);
            setResult(RESULT_CANCELED,i);
            finish();
        }
    }

}
