package com.aniqma.lokaventour.modul.activity.booking.editcontactinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.BookingProcessItem;
import com.aniqma.lokaventour.model.item.tourexplorer.TourPackageItem;
import com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingsummary.SummaryBookingActivity;
import com.aniqma.lokaventour.network.local.bookingproses.BookingLite;

public class EditContactInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvTitle;
    private ImageView ivBack;
    private Button btnBig;
    private EditText etEmail,
            etFirstName,
            etLastName,
            etPhoneNumber;
    private Spinner spnPhoneNumber;
    String textError = "Form can't be empty";
    private String emailPattern;
    private View component_sheet_button_1columns,
            component_btn_big;

    private BookingProcessItem bookingProcessItem;
    private TourPackageItem tourPackageItem;
    private String from = "";
    private String typeTour = "", quota_left="", min="", max="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact_info);

        if (getIntent() != null) {
            from = getIntent().getExtras().getString("from", "");
            bookingProcessItem = (BookingProcessItem) getIntent().getSerializableExtra("bookingProcessItem");
            tourPackageItem = (TourPackageItem) getIntent().getSerializableExtra("tourPackageItem");
        }
        typeTour = tourPackageItem.getTypeTour();

        init();

        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        etFirstName.setText(bookingProcessItem.getContact_info().getFirst_name());
        etLastName.setText(bookingProcessItem.getContact_info().getLast_name());
        etPhoneNumber.setText(bookingProcessItem.getContact_info().getPhone_number());
        etEmail.setText(bookingProcessItem.getContact_info().getEmail());

        onChangeParticipants();

        tvTitle.setText(getText(R.string.text_edit_contact_info));
        btnBig.setText(getResources().getString(R.string.text_save_and_back));
        ivBack.setOnClickListener(this);
        btnBig.setOnClickListener(this);
    }

    private void init() {
        tvTitle = findViewById(R.id.tvTitle);
        ivBack = findViewById(R.id.ivBack);
        component_sheet_button_1columns = findViewById(R.id.component_sheet_button_1columns);
        component_btn_big = component_sheet_button_1columns.findViewById(R.id.component_btn_big);
        btnBig = component_btn_big.findViewById(R.id.btnBig);
        etEmail = findViewById(R.id.etEmail);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        spnPhoneNumber = findViewById(R.id.spnPhoneNumber);
    }

    private void onChangeParticipants() {

        etFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setContactInfo();
            }

        });

        etLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setContactInfo();
            }
        });

        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setContactInfo();
            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setContactInfo();
            }
        });

    }

    private void setContactInfo() {
        if (bookingProcessItem.getParticipants().get(0).isAs_contact_info()) {
            bookingProcessItem.getParticipants().get(0).setFirst_name(etFirstName.getText().toString());
            bookingProcessItem.getParticipants().get(0).setLast_name(etLastName.getText().toString());
        }
        bookingProcessItem.getContact_info().setFirst_name(etFirstName.getText().toString());
        bookingProcessItem.getContact_info().setLast_name(etLastName.getText().toString());
        bookingProcessItem.getContact_info().setEmail(etEmail.getText().toString());
        bookingProcessItem.getContact_info().setCode_phone(spnPhoneNumber.getSelectedItem().toString());
        bookingProcessItem.getContact_info().setPhone_number(etPhoneNumber.getText().toString());

        // ADD HISTORY BOOKING
        BookingLite bookingLite = new BookingLite(this);
        bookingLite.addDB(bookingProcessItem);
    }

    @Override
    public void onClick(View v) {
        if (v == ivBack){
            Intent i = new Intent(this, SummaryBookingActivity.class);
            i.putExtra("from", "summary");
            i.putExtra("bookingProcessItem", bookingProcessItem);
            i.putExtra("tourPackageItem", tourPackageItem);
            startActivity(i);
            finish();
        }else if (v == btnBig){
            if (!validation()){

                setContactInfo();

                Intent i = new Intent(this, SummaryBookingActivity.class);
                i.putExtra("from", "summary");
                i.putExtra("bookingProcessItem", bookingProcessItem);
                i.putExtra("tourPackageItem", tourPackageItem);
                startActivity(i);
                finish();
            }
        }
    }

    public boolean validation(){
        if (etFirstName.getText().toString().trim().isEmpty() || etFirstName.getText().toString().trim().length() < 2){
            etFirstName.setError(getString(R.string.text_please_input_first_name_or_first_name_less_than_2_characters));
            return true;
        }

        if (etPhoneNumber.getText().toString().trim().isEmpty() || (etPhoneNumber.getText().toString().trim().length() < 8 || etPhoneNumber.getText().toString().trim().length() > 15)){
            etPhoneNumber.setError(getString(R.string.text_please_input_phone_number_or_phone_number_length_must_be_between_8_to_15_length));
            return true;
        }

        if (etEmail.getText().toString().trim().isEmpty() || !etEmail.getText().toString().trim().matches(emailPattern)){
            etEmail.setError(getString(R.string.text_please_input_email_or_email_format_is_wrong));
            return true;
        }

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // ADD HISTORY BOOKING
        BookingLite bookingLite = new BookingLite(this);
        bookingLite.addDB(bookingProcessItem);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, SummaryBookingActivity.class);
        i.putExtra("from", "summary");
        i.putExtra("bookingProcessItem", bookingProcessItem);
        i.putExtra("tourPackageItem", tourPackageItem);
        startActivity(i);
        finish();
    }
}
