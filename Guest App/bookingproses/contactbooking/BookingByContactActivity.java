package com.aniqma.lokaventour.modul.activity.booking.bookingproses.contactbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aniqma.lokaventour.utils.popup.LokavenDialog;
import com.aniqma.lokaventour.utils.animation.LoadingPage;
import com.aniqma.lokaventour.utils.misc.inlinehelp.TooltipWindow;
import com.aniqma.lokaventour.model.item.tourexplorer.TourPackageItem;
import com.aniqma.lokaventour.model.item.bookingService.bookingByContact.BodyBookingByContact;
import com.aniqma.lokaventour.model.item.bookingService.bookingByContact.BookingByContactResponse;
import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.modul.activity.tourpackagedetails.detailopen.TourPackageDetailOpenActivity;
import com.aniqma.lokaventour.modul.activity.tourpackagedetails.detailprivate.TourPackageDetailPrivateActivity;
import com.aniqma.lokaventour.network.services.authentication.refreshtokenandintrospect.IViewCheckToken;
import com.aniqma.lokaventour.network.services.authentication.refreshtokenandintrospect.RefreshTokenServices;
import com.aniqma.lokaventour.network.services.bookingservice.bookingbycontact.BookingByServiceByContactService;
import com.aniqma.lokaventour.network.services.bookingservice.bookingbycontact.IViewBookingByContactService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import static com.aniqma.lokaventour.helper.url.UrlHelper.IsValidUrl;

public class BookingByContactActivity extends AppCompatActivity implements View.OnClickListener, IViewBookingByContactService, IViewCheckToken {

    private Spinner spnPhoneNumber;
    private TextView titleToolbar, tvNameHost, tvLocationHost, tvDescription;
    private ImageView backButton, imgBack, imgProfile, imgTooltip;
    private Button btnBig;
    private View compBtn, compCard, component_info;
    private String lastName = "",
            tour_id = "",
            location = "",
            host_id = "",
            title = "",
            typeTour="",
            emailPattern="";
    private IViewBookingByContactService iView;
    private TextInputEditText etFirstName,
            etLastName,
            etPhoneNumber,
            etEmail,
            tvNumberParticipants;

    private TextInputLayout tilFirstName,
            tilLastName,
            tilPhoneNumber,
            tilEmail,
            tilNumberParticipant;

    private TourPackageItem tourPackageItem;
    private String from = "";

    private LokavenDialog lokavenDialog;
    private LoadingPage loadingPage;
    private TooltipWindow tipWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_booking);

        loadingPage = new LoadingPage(this);
        lokavenDialog = new LokavenDialog(this);
        tipWindow = new TooltipWindow(this);

        init();

        titleToolbar.setText(getText(R.string.text_edit_contact_info));
        btnBig.setText(R.string.text_send_booking);
        iView = this;

        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (getIntent() != null){
            tourPackageItem = (TourPackageItem) getIntent().getSerializableExtra("tourPackageItem");
            tour_id = tourPackageItem.getTourId();
            location = tourPackageItem.getLocation();
            host_id = tourPackageItem.getHostId();
            title = tourPackageItem.getTitle();
            typeTour = tourPackageItem.getTypeTour();
        }

        init();
        SetProfileTour();

        imgBack.setVisibility(View.GONE);
        btnBig.setText(R.string.text_send_booking);
        backButton.setOnClickListener(this);
        btnBig.setOnClickListener(this);
        compCard.setOnClickListener(this);
        imgTooltip.setOnClickListener(this);
    }

    private void SetProfileTour() {
        titleToolbar.setText(getResources().getString(R.string.text_contact_booking));
        tvNameHost.setText(tourPackageItem.getTitle());
        tvLocationHost.setText(tourPackageItem.getLocation());
        tvDescription.setText(getString(R.string.text_you_can_use_this_form_to_book_by_contact_Lokaven_directly));

        String image = tourPackageItem.getMedias().get(0).getUrl();

        if (IsValidUrl(image)) {
            if (image.contains("http://")) {
                image = image.replace("http://", "https://");
            }

            Picasso.get()
                    .load(image)
                    .fit()
                    .placeholder(R.drawable.thumbnail_default)
                    .into(imgProfile);
        }
    }

    private void init() {
        compBtn = findViewById(R.id.compBtn);
        btnBig = compBtn.findViewById(R.id.btnBig);
        titleToolbar = findViewById(R.id.tvTitle);
        backButton = findViewById(R.id.ivBack);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etEmail = findViewById(R.id.etEmail);
        tvNumberParticipants = findViewById(R.id.etNumberParticipant);
        imgBack = findViewById(R.id.ivBackTour);
        spnPhoneNumber = findViewById(R.id.spnPhoneNumber);
        compCard = findViewById(R.id.compCard);
        tvNameHost = compCard.findViewById(R.id.tvTitleTour);
        tvLocationHost = compCard.findViewById(R.id.tvLocationTour);
        imgProfile = compCard.findViewById(R.id.imgThumbnail);
        imgBack = compCard.findViewById(R.id.ivBackTour);
        imgTooltip = findViewById(R.id.imgTooltip);
        component_info = findViewById(R.id.component_info);
        tvDescription = component_info.findViewById(R.id.tvDescription);

        tilFirstName = findViewById(R.id.tilFirstName);
        tilLastName = findViewById(R.id.tilLastName);
        tilPhoneNumber = findViewById(R.id.tilPhoneNumber);
        tilEmail = findViewById(R.id.tilEmail);
        tilNumberParticipant = findViewById(R.id.tilNumberParticipant);
    }

    private BodyBookingByContact getData(){

        String str = spnPhoneNumber.getSelectedItem().toString();
        str = str.replace("+", "");
        BodyBookingByContact data = new BodyBookingByContact(
                etFirstName.getText().toString().trim(),
                etLastName.getText().toString().trim(),
                etEmail.getText().toString().trim(),
                str+etPhoneNumber.getText().toString().trim(),
                tvNumberParticipants.getText().toString().trim()
        );
        return data;
    }

    private void onBooking() {
        BookingByServiceByContactService presenter = new BookingByServiceByContactService(iView, this);
        presenter.onBookingByContact(tour_id, getData());
    }

    @Override
    public void onSuccessBooking(BookingByContactResponse response) {
        BookingByContactResponse dataResponse = new BookingByContactResponse();
        dataResponse.setData(response.getData());
        dataResponse.setTourRecommendation(response.getTourRecommendation());
        dataResponse.setTitle(response.getTitle());

        loadingPage.stop();
        Intent i = new Intent(this, ContactBookingAfterSubmitActivity.class);
        i.putExtra("BookingByContactResponse", dataResponse);
        i.putExtra("hostId", host_id);
        i.putExtra("type_tour", typeTour);
        startActivity(i);
        finish();
    }

    @Override
    public void onErrorMsg(String title, String message, int responseCode) {
        if (responseCode == 401) {
            IViewCheckToken iViewCheckToken = this;
            RefreshTokenServices refreshTokenServices = new RefreshTokenServices(this, iViewCheckToken);
            refreshTokenServices.onInstrospectToken();
        } else {
            loadingPage.stop();
            lokavenDialog.dialogError(title, message);
        }
    }

    @Override
    public void isToken(boolean success) {
        onBooking();
    }

    @Override
    public void errorToken(String title, String message) {
        loadingPage.stop();
        lokavenDialog.dialogError(title, message);
    }

    private boolean Validation(){
        boolean valid = true;
        if (etFirstName.getText().toString().trim().equalsIgnoreCase("")){
            tilFirstName.setError(getString(R.string.alert_first_name));
            tilFirstName.requestFocus();
            valid = false;
        } else {
            tilFirstName.setError(null);
        }

        if (etPhoneNumber.getText().toString().trim().equalsIgnoreCase("")){
            tilPhoneNumber.setError(getString(R.string.alert_phone_number));
            tilPhoneNumber.requestFocus();
            valid = false;
        } else {
            tilPhoneNumber.setError(null);
        }

        if (etEmail.getText().toString().trim().equalsIgnoreCase("")){
            tilEmail.setError(getString(R.string.text_please_input_email_or_email_format_is_wrong));
            tilEmail.requestFocus();
            valid = false;
        } else {
            tilEmail.setError(null);
        }

        if (etPhoneNumber.getText().toString().trim().isEmpty() || (etPhoneNumber.getText().toString().trim().length() < 8 || etPhoneNumber.getText().toString().trim().length() > 15)){
            tilPhoneNumber.setError(getString(R.string.text_please_input_phone_number_or_phone_number_length_must_be_between_8_to_15_length));
            tilPhoneNumber.requestFocus();
            valid = false;
        } else {
            tilPhoneNumber.setError(null);
        }

        if (etEmail.getText().toString().trim().isEmpty() || !etEmail.getText().toString().trim().matches(emailPattern)){
            tilEmail.setError(getString(R.string.text_please_input_email_or_email_format_is_wrong));
            tilEmail.requestFocus();
            valid = false;
        } else {
            tilEmail.setError(null);
        }

        if (tvNumberParticipants.getText().toString().trim().equalsIgnoreCase("")){
            tilNumberParticipant.setError(getString(R.string.text_please_input_number_of_participants));
            tilNumberParticipant.requestFocus();
            valid = false;
        } else if (Integer.parseInt(tvNumberParticipants.getText().toString().trim()) < 1){
            tilNumberParticipant.setError(getString(R.string.text_participants_must_be_more_than_a_person));
            tilNumberParticipant.requestFocus();
            valid = false;
        } else {
            tilNumberParticipant.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        if (v == btnBig){
            if (Validation()){
                loadingPage.start();
                onBooking();
            }
        } else if (v == backButton){
            onBackPressed();
        } else if (v == compCard){
            if (typeTour.equals("open")){
                Intent i = new Intent(this, TourPackageDetailOpenActivity.class);
                i.putExtra("tourId", tour_id);
                i.putExtra("hostId", host_id);
                i.putExtra("typeTour", typeTour);
                setResult(RESULT_CANCELED, i);
                finish();
            } if (typeTour.equals("close")){
                Intent i = new Intent(this, TourPackageDetailPrivateActivity.class);
                i.putExtra("tourId", tour_id);
                i.putExtra("hostId", host_id);
                i.putExtra("typeTour", typeTour);
                setResult(RESULT_CANCELED, i);
                finish();
            }
        } else if (v == imgTooltip) {

            if (!tipWindow.isTooltipShown()) {
                tipWindow.showToolTip(imgTooltip, getString(R.string.text_the_number_of_participants));
            } else {
                tipWindow.dismissTooltip();
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
