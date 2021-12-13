package com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingdetails.opentour;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.utils.popup.LokavenDialog;
import com.aniqma.lokaventour.helper.date.DateHelper;
import com.aniqma.lokaventour.utils.animation.LoadingPage;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.BookingProcessItem;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.ParticipantsItem;
import com.aniqma.lokaventour.model.item.bookingService.quotaLeft.QuotaLeftDataItem;
import com.aniqma.lokaventour.model.item.tourexplorer.TourPackageItem;
import com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingaddons.AddonsBookingActivity;
import com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingdetails.DialogOrderPriceList;
import com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingsummary.SummaryBookingActivity;
import com.aniqma.lokaventour.modul.activity.booking.bookingproses.contactbooking.BookingByContactActivity;
import com.aniqma.lokaventour.modul.activity.booking.participantsinfo.ParticipantsInfoActivity;
import com.aniqma.lokaventour.modul.activity.tourpackagedetails.detailopen.TourPackageDetailOpenActivity;
import com.aniqma.lokaventour.network.local.bookingproses.BookingLite;
import com.aniqma.lokaventour.network.local.tourmanagement.TourPackageLite;
import com.aniqma.lokaventour.network.services.bookingservice.bookingprocess.bookingquotaleft.BookingQuotaLeftService;
import com.aniqma.lokaventour.network.services.bookingservice.bookingprocess.bookingquotaleft.iViewQuotaLeft;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.aniqma.lokaventour.helper.format.NumberHelper.formatIdr;
import static com.aniqma.lokaventour.helper.url.BitmapHelper.validationPicasso;

public class OpenDetailsBookingActivity extends AppCompatActivity implements View.OnClickListener, iViewQuotaLeft {

    private BookingProcessItem bookingProcessItem;
    private TourPackageItem tourPackageItem;

    private RelativeLayout rlParticipantInfo, rlImgMail;

    boolean clicked = false;

    private View ComponentAdult,
            ComponentChild,
            ComponentSubTotal,
            ComponentSubTotalBreakDown,
            ComponentContactInfo,
            component_cards_package_ticket_initial,
            component_add_remove_adult,
            component_add_remove_child,
            ComponentButton,
            btnSheet,
            ComponentAppbarDefault,
            component_contact_booking;

    private TextView tvTotalParticipants,
            tvSubTotal,
            tvClickHereSubTotal,
            tvClickHereSubTotalBreakDown,
            tvListItemAdult,
            tvListItemChild,
            tvNamePackage,
            tvLocation,
            tvDuration,
            tvStartDate,
            tvEndDate,
            tvNumberAdult,
            tvNumberChild,
            tvPriceSubTotal,
            tvAdultCount,
            tvPriceAdult,
            tvChildCount,
            tvPriceChildren,
            tvPriceBreakDown,
            tvDetail,
            tvTotalPrice,
            tvTitle,
            tvBtnInfo,
            tvQuotaLeft,
            tvInfoParticipant,
            tvQuotaLeftDown;

    private TextInputEditText etEmail,
            etFirstName,
            etLastName,
            etPhoneNumber;

    private ImageView
            thumbnailTour,
            ibMinusAdult,
            ibPlusAdult,
            ibMinusChild,
            ibPlusChild,
            ivBack,
            ivPlus;

    private Button btnBig,
            button_3;

    private LinearLayout llAddons;
    private Spinner spnPhoneNumber;

    private TextInputLayout tilEmail,
            tilPhoneNumber,
            tilFirstName;

    private int childPrice = 0, adultPrice = 0, totalAdult = 0, totalChild = 0, quotaLeft = 0, totalQuota=0;
    private String typeTour="", emailPattern="", maxQuota="", minQuota="";

    private LokavenDialog lokavenDialog;
    private LoadingPage loadingPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_active_order_book);

        lokavenDialog = new LokavenDialog(this);
        loadingPage = new LoadingPage(this);

        if (getIntent() != null) {
            tourPackageItem = (TourPackageItem) getIntent().getSerializableExtra("tourPackageItem");
            bookingProcessItem = (BookingProcessItem) getIntent().getSerializableExtra("bookingProcessItem");
        }

        typeTour = tourPackageItem.getTypeTour();
        minQuota = bookingProcessItem.getSchedules().get(0).getMinQuota();
        maxQuota = bookingProcessItem.getSchedules().get(0).getMaxQuota();

        Initializaton();
        init();

        ivPlus.setVisibility(View.GONE);

        tvDetail.setText(R.string.text_sub_total_including_tax);
        btnBig.setText(R.string.btn_next);

        tvListItemAdult.setText(R.string.text_adults);
        tvListItemChild.setText(R.string.text_child);

        etFirstName.setHint(R.string.text_your_real_name);
        etLastName.setHint(R.string.text_optional_last_name);
        etPhoneNumber.setHint(R.string.text_your_number_here);
        etEmail.setHint(R.string.text_valid_email_address);

        onChangeContactInfo();

        tvTotalParticipants.setOnClickListener(this);
        tvSubTotal.setOnClickListener(this);
        ComponentContactInfo.setOnClickListener(this);
        rlParticipantInfo.setOnClickListener(this);
        tvClickHereSubTotal.setOnClickListener(this);
        tvClickHereSubTotalBreakDown.setOnClickListener(this);
        ibPlusAdult.setOnClickListener(this);
        ibMinusAdult.setOnClickListener(this);
        ibMinusChild.setOnClickListener(this);
        ibPlusChild.setOnClickListener(this);
        btnBig.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btnBig.setOnClickListener(this);
        tvBtnInfo.setOnClickListener(this);
        rlImgMail.setOnClickListener(this);
    }

    private void init() {

        if (tourPackageItem.getAddons() != null){
            if (tourPackageItem.getAddons().size() == 0 ){
                llAddons.setVisibility(View.GONE);
                button_3.setText("2");
            } else {
                llAddons.setVisibility(View.VISIBLE);
            }
        }

        if (tourPackageItem.getMedias() != null){
            if (tourPackageItem.getMedias().size() != 0){
                if (!tourPackageItem.getMedias().get(0).getUrl().isEmpty()){
                    Picasso.get()
                            .load(validationPicasso(tourPackageItem.getMedias().get(0).getUrl()))
                            .placeholder(R.drawable.thumbnail_default)
                            .centerCrop()
                            .fit()
                            .into(thumbnailTour);
                }
            }
        }

        tvNamePackage.setText(tourPackageItem.getTitle());
        tvLocation.setText(tourPackageItem.getLocation());

        if (bookingProcessItem.getSchedules().get(0).getDuration().equals("0")){
            tvDuration.setText("1 "+ getString(R.string.text_days));
        } else {
            tvDuration.setText(bookingProcessItem.getSchedules().get(0).getDuration() +" "+ getString(R.string.text_days));
        }

        try {
            tvStartDate.setText(DateHelper.getDateFormatMonthandYear(bookingProcessItem.getSchedules().get(0).getStartDate()));
            tvEndDate.setText(DateHelper.getDateFormatMonthandYear(bookingProcessItem.getSchedules().get(0).getEndDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvTitle.setText(R.string.text_booking);
        if (bookingProcessItem.getQty_adults() != ""){
            totalAdult = Integer.valueOf(bookingProcessItem.getQty_adults());
            tvNumberAdult.setText(String.valueOf(totalAdult));
        }

        if (bookingProcessItem.getQty_kids() != ""){
            totalChild = Integer.valueOf(bookingProcessItem.getQty_kids());
            tvNumberChild.setText(String.valueOf(totalChild));
        }

        if (bookingProcessItem.getSub_price_participants() != ""){
            setTotalPrice();
        }

        if (bookingProcessItem.getContact_info() != null){
            etFirstName.setText(bookingProcessItem.getContact_info().getFirst_name());
            etLastName.setText(bookingProcessItem.getContact_info().getLast_name());
            etEmail.setText(bookingProcessItem.getContact_info().getEmail());
            etPhoneNumber.setText(bookingProcessItem.getContact_info().getPhone_number());
        }

        if (!maxQuota.equals("")){
            if (maxQuota.isEmpty()){
                tvInfoParticipant.setText("999");
            }
            tvInfoParticipant.setText(maxQuota);
        }

        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    }

    private void Initializaton() {
        component_cards_package_ticket_initial = findViewById(R.id.component_cards_package_ticket_initial);

        thumbnailTour = component_cards_package_ticket_initial.findViewById(R.id.imgThumbnail);
        tvNamePackage = component_cards_package_ticket_initial.findViewById(R.id.tvTitleTour);
        tvLocation = component_cards_package_ticket_initial.findViewById(R.id.tvLocationTour);
        tvDuration = component_cards_package_ticket_initial.findViewById(R.id.tvDuration);
        tvStartDate = component_cards_package_ticket_initial.findViewById(R.id.tvStartDate);
        tvEndDate = component_cards_package_ticket_initial.findViewById(R.id.tvEndDate);

        rlParticipantInfo = findViewById(R.id.rlParticipantInfo);
        ComponentAdult = findViewById(R.id.ComponentAdult);
        tvListItemAdult = ComponentAdult.findViewById(R.id.tvListItem);
        component_add_remove_adult = ComponentAdult.findViewById(R.id.component_add_remove);
        ibMinusAdult = component_add_remove_adult.findViewById(R.id.ibMinus);
        tvNumberAdult = component_add_remove_adult.findViewById(R.id.tvNumber);
        ibPlusAdult = component_add_remove_adult.findViewById(R.id.ibPlus);

        ComponentChild = findViewById(R.id.ComponentChild);
        tvListItemChild = ComponentChild.findViewById(R.id.tvListItem);
        component_add_remove_child = ComponentChild.findViewById(R.id.component_add_remove);
        ibMinusChild = component_add_remove_child.findViewById(R.id.ibMinus);
        tvNumberChild = component_add_remove_child.findViewById(R.id.tvNumber);
        ibPlusChild = component_add_remove_child.findViewById(R.id.ibPlus);

        ComponentSubTotal = findViewById(R.id.ComponentSubTotal);
        tvTotalParticipants = ComponentSubTotal.findViewById(R.id.tvTotalParticipants);
        tvClickHereSubTotal = ComponentSubTotal.findViewById(R.id.tvClickHere);
        tvPriceSubTotal = ComponentSubTotal.findViewById(R.id.tvPrice);
        tvQuotaLeft = ComponentSubTotal.findViewById(R.id.tvStatus);

        ComponentSubTotalBreakDown = findViewById(R.id.ComponentSubTotalBreakDown);
        tvSubTotal = ComponentSubTotalBreakDown.findViewById(R.id.tvSubTotal);
        tvClickHereSubTotalBreakDown = ComponentSubTotalBreakDown.findViewById(R.id.tvClickHere);
        tvPriceBreakDown = ComponentSubTotalBreakDown.findViewById(R.id.tvPrice);
        tvAdultCount = ComponentSubTotalBreakDown.findViewById(R.id.tvAdultCount);
        tvPriceAdult = ComponentSubTotalBreakDown.findViewById(R.id.tvPriceAdult);
        tvChildCount = ComponentSubTotalBreakDown.findViewById(R.id.tvChildCount);
        tvPriceChildren = ComponentSubTotalBreakDown.findViewById(R.id.tvPriceChildren);
        tvInfoParticipant = ComponentSubTotalBreakDown.findViewById(R.id.tvInfoParticipant);
        tvQuotaLeftDown = ComponentSubTotalBreakDown.findViewById(R.id.tvStatus);

        ComponentContactInfo = findViewById(R.id.ComponentContactInfo);

        etEmail = findViewById(R.id.etEmail);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);

        ComponentButton = findViewById(R.id.ComponentButton);
        btnSheet = ComponentButton.findViewById(R.id.btnSheet);
        tvDetail = ComponentButton.findViewById(R.id.tvDetail);
        btnBig = btnSheet.findViewById(R.id.component_btn_big);
        tvTotalPrice = ComponentButton.findViewById(R.id.tvTotalPrice);

        ComponentAppbarDefault = findViewById(R.id.ComponentAppbarDefault);
        ivBack = ComponentAppbarDefault.findViewById(R.id.ivBack);
        ivPlus = ComponentAppbarDefault.findViewById(R.id.ivPlus);
        tvTitle = ComponentAppbarDefault.findViewById(R.id.tvTitle);

        spnPhoneNumber = findViewById(R.id.spnPhoneNumber);

        component_contact_booking = findViewById(R.id.component_contact_booking);
        tvBtnInfo = component_contact_booking.findViewById(R.id.tvBtnInfo);
        rlImgMail = component_contact_booking.findViewById(R.id.rlImgMail);

        tilEmail = findViewById(R.id.tilEmail);
        tilPhoneNumber = findViewById(R.id.tilPhoneNumber);
        tilFirstName = findViewById(R.id.tilFirstName);

        button_3 = findViewById(R.id.button_3);
        llAddons = findViewById(R.id.llAddons);
    }

    public void addAdult() {
        totalAdult = Integer.valueOf(tvNumberAdult.getText().toString()) + 1;
        totalQuota = totalAdult + totalChild;

        tvNumberAdult.setText(String.valueOf(totalAdult));

        if (bookingProcessItem.getParticipants().size() > 0){
            int qtyAdult = Integer.valueOf(bookingProcessItem.getQty_adults());

            if(totalAdult > qtyAdult){
                int i = qtyAdult;
                while (i < totalAdult){
                    bookingProcessItem.getParticipants().add(i, new ParticipantsItem());
                    i++;
                }
            }
        }

        bookingProcessItem.setQty_adults(String.valueOf(totalAdult));
        bookingProcessItem.setTotal_quota(String.valueOf(totalQuota));

        managePrice();
    }

    public void deltAdult() {
        if (Integer.valueOf(tvNumberAdult.getText().toString()) -1 == -1){
            lokavenDialog.getToast("wrong number adult participant");
        } else {

            totalAdult = Integer.valueOf(tvNumberAdult.getText().toString()) - 1;
            totalQuota = totalAdult + totalChild;

            tvNumberAdult.setText(String.valueOf(totalAdult));

            if (bookingProcessItem.getParticipants().size() > 0){
                int qtyAdult = Integer.valueOf(bookingProcessItem.getQty_adults());

                if (totalAdult < qtyAdult){
                    int i= qtyAdult;
                    while (i > totalAdult){
                        bookingProcessItem.getParticipants().remove(i-1);
                        i--;
                    }
                }
            }

            bookingProcessItem.setQty_adults(String.valueOf(totalAdult));
            bookingProcessItem.setTotal_quota(String.valueOf(totalQuota));

            managePrice();

        }
    }

    private void addChild() {
        totalChild = Integer.valueOf(tvNumberChild.getText().toString()) + 1;
        totalQuota = totalAdult + totalChild;

        tvNumberChild.setText(String.valueOf(totalChild));

        if (bookingProcessItem.getParticipants().size() > 0){
            int qtyAdult = Integer.valueOf(bookingProcessItem.getQty_adults());
            int qtyKids = Integer.valueOf(bookingProcessItem.getQty_kids());

            if (totalChild > qtyKids){
                int i = qtyAdult+qtyKids;
                while (i < totalQuota){
                    bookingProcessItem.getParticipants().add(i, new ParticipantsItem());
                    i++;
                }
            }
        }

        bookingProcessItem.setQty_kids(String.valueOf(totalChild));
        bookingProcessItem.setTotal_quota(String.valueOf(totalQuota));

        managePrice();
    }

    private void delChild() {
        if (Integer.valueOf(tvNumberChild.getText().toString()) -1 == -1){
            lokavenDialog.getToast("wrong number child participant");
        } else {

            totalChild = Integer.valueOf(tvNumberChild.getText().toString()) - 1;
            totalQuota = totalAdult + totalChild;

            tvNumberChild.setText(String.valueOf(totalChild));

            if (bookingProcessItem.getParticipants().size() > 0){
                int qtyAdult = Integer.valueOf(bookingProcessItem.getQty_adults());
                int qtyKids = Integer.valueOf(bookingProcessItem.getQty_kids());

                if (totalChild < qtyKids){
                    int i = qtyAdult+qtyKids;
                    while (i > totalQuota){
                        bookingProcessItem.getParticipants().remove(i-1);
                        i--;
                    }
                }
            }

            bookingProcessItem.setQty_kids(String.valueOf(totalChild));
            bookingProcessItem.setTotal_quota(String.valueOf(totalQuota));

            managePrice();
        }
    }

    private void managePrice() {

        adultPrice = totalAdult * Integer.valueOf(tourPackageItem.getPrices().get(0).getPrice());
        childPrice = totalChild * Integer.valueOf(tourPackageItem.getPrices().get(0).getKidPrice());

        int totalAddons = Integer.valueOf(bookingProcessItem.getTemp_price_addons()) * totalQuota;

        bookingProcessItem.setTemp_adult_price(String.valueOf(adultPrice));
        bookingProcessItem.setTemp_kid_price(String.valueOf(childPrice));
        bookingProcessItem.setSub_price_addons(String.valueOf(totalAddons));

        setTotalPrice();
    }

    private void setTotalPrice() {
        int adultPrice = Integer.valueOf(bookingProcessItem.getTemp_adult_price());
        int kidPrice =  Integer.valueOf(bookingProcessItem.getTemp_kid_price());
        int subPriceTotal = adultPrice + kidPrice;
        int subPriceAddons = Integer.valueOf(bookingProcessItem.getSub_price_addons());
        int totalPrice = subPriceTotal+subPriceAddons;

        bookingProcessItem.setQuota_left(quotaLeft);
        bookingProcessItem.setSub_price_participants(String.valueOf(subPriceTotal));
        bookingProcessItem.setTotal_price(String.valueOf(totalPrice));

        double totalAdultPrice = Double.parseDouble(String.valueOf(adultPrice));
        double totalChildPrice = Double.parseDouble(String.valueOf(kidPrice));
        double subTotalParticipant = Double.parseDouble(String.valueOf(adultPrice+kidPrice));
        double total = Double.parseDouble(String.valueOf(totalPrice));

        tvAdultCount.setText(tvNumberAdult.getText().toString());
        tvPriceAdult.setText(formatIdr(totalAdultPrice));

        tvChildCount.setText(tvNumberChild.getText().toString());
        tvPriceChildren.setText(formatIdr(totalChildPrice));

        tvPriceSubTotal.setText(formatIdr(subTotalParticipant));
        tvPriceBreakDown.setText(formatIdr(subTotalParticipant));
        tvTotalPrice.setText(formatIdr(total));

        setParticipants();

        // ADD HISTORY BOOKING
        BookingLite bookingLite = new BookingLite(this);
        bookingLite.addDB(bookingProcessItem);
    }

    private void onChangeContactInfo() {

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

    private void setContactInfo(){
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

    private void setParticipants() {
        if (bookingProcessItem.getParticipants().size() == 0) {
            List<ParticipantsItem> list = new ArrayList<>();
            for (int i = 0; i < Integer.valueOf(bookingProcessItem.getTotal_quota()); i++){
                list.add(new ParticipantsItem());
            }

            bookingProcessItem.setParticipants(list);
        }

        // ADD HISTORY BOOKING
        BookingLite bookingLite = new BookingLite(this);
        bookingLite.addDB(bookingProcessItem);
    }

    @Override
    public void onSuccessQuotaLeft(QuotaLeftDataItem response) {
        int min = Integer.valueOf(minQuota);
        int max = Integer.valueOf(maxQuota);
        int total = Integer.valueOf(response.getTotal_quota());

        quotaLeft = Integer.valueOf(response.getQuota_left());

        int quotaLeftView=0;
        if (total >= min) {
            quotaLeftView = max - total;
            tvQuotaLeft.setText(getString(R.string.text_with_yours)+" "+ String.valueOf(quotaLeftView) + " " + getString(R.string.text_more_bookings_to_fill_max));
            tvQuotaLeftDown.setText(getString(R.string.text_with_yours)+" "+String.valueOf(quotaLeftView) + " "+ getString(R.string.text_more_bookings_to_fill_max));
        } else {
            quotaLeftView = min - total;
            tvQuotaLeft.setText(getString(R.string.text_with_yours)+" "+ String.valueOf(quotaLeftView) + " " + getString(R.string.text_more_bookings_to_fill_min));
            tvQuotaLeftDown.setText(getString(R.string.text_with_yours)+" "+String.valueOf(quotaLeftView) + " " + getString(R.string.text_more_bookings_to_fill_min));
        }

        bookingProcessItem.setQuota_left(quotaLeft);
        loadingPage.stop();

        // ADD HISTORY BOOKING
        BookingLite bookingLite = new BookingLite(this);
        bookingLite.addDB(bookingProcessItem);
    }

    @Override
    public void onErrorQuotaLeft(String title, String message) {
        quotaLeft = Integer.valueOf(maxQuota);
        bookingProcessItem.setQuota_left(quotaLeft);

        tvQuotaLeft.setText(getString(R.string.text_with_yours)+" "+ String.valueOf(minQuota) + " " + getString(R.string.text_more_bookings_to_fill_min));
        tvQuotaLeftDown.setText(getString(R.string.text_with_yours)+" "+String.valueOf(minQuota) + " " + getString(R.string.text_more_bookings_to_fill_min));
        loadingPage.stop();

        // ADD HISTORY BOOKING
        BookingLite bookingLite = new BookingLite(this);
        bookingLite.addDB(bookingProcessItem);
    }

    @Override
    public void onClick(View v) {
        if (v == tvTotalParticipants){

            if (!clicked){
                ComponentSubTotalBreakDown.setVisibility(View.VISIBLE);
                ComponentSubTotal.setVisibility(View.GONE);
                clicked =true;
            }

        } else if (v == tvSubTotal){

            if (clicked){
                ComponentSubTotal.setVisibility(View.VISIBLE);
                ComponentSubTotalBreakDown.setVisibility(View.GONE);
                clicked =false;
            }

        } else if (v == ComponentContactInfo || v == tvBtnInfo || v == rlImgMail){

            Intent i = new Intent(this, BookingByContactActivity.class);
            i.putExtra("tourPackageItem", tourPackageItem);
            startActivity(i);

        } else if (v == rlParticipantInfo){

            if (validationInfo()){

            }  else {

                setParticipants();

                Intent i = new Intent(this, ParticipantsInfoActivity.class);
                i.putExtra("from", "");
                i.putExtra("bookingProcessItem", bookingProcessItem);
                i.putExtra("participant", bookingProcessItem);
                i.putExtra("tourPackageItem", tourPackageItem);
                startActivity(i);
                finish();

            }

        } else if (v == tvClickHereSubTotal || v == tvClickHereSubTotalBreakDown){

            DialogOrderPriceList dialog = new DialogOrderPriceList(this, tourPackageItem, bookingProcessItem);
            dialog.show();

        } else if (v == ibPlusAdult){

            if (totalQuota < quotaLeft) {
                addAdult();
            } else {
                lokavenDialog.getToast("You can only book "+ quotaLeft +" participants");
            }

        } else if (v == ibMinusAdult){

            deltAdult();

        } else if (v == ibPlusChild){

            if (totalQuota < quotaLeft) {
                addChild();
            } else {
                lokavenDialog.getToast("You can only book "+ quotaLeft +" participants");
            }

        } else if (v == ibMinusChild){

            delChild();

        } else if (v == ivBack){

            onBackPressed();

        } else if (v == btnBig){
            if (validation()){

            } else {

                // ADD HISTORY BOOKING
                BookingLite bookingLite = new BookingLite(this);
                bookingLite.addDB(bookingProcessItem);

                if (tourPackageItem.getAddons().size() > 0) {
                    Intent i = new Intent(this, AddonsBookingActivity.class);
                    i.putExtra("bookingProcessItem", bookingProcessItem);
                    i.putExtra("tourPackageItem", tourPackageItem);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(this, SummaryBookingActivity.class);
                    i.putExtra("bookingProcessItem", bookingProcessItem);
                    i.putExtra("tourPackageItem", tourPackageItem);
                    startActivity(i);
                    finish();
                }

            }
        }
    }

    public boolean validation(){

        boolean failed = false;
        if (!bookingProcessItem.getQty_adults().equals(String.valueOf(totalAdult))){
            lokavenDialog.getToast(getString(R.string.text_please_complete_the_list_of_participants));
            failed = true;
        }

        if (!bookingProcessItem.getQty_kids().equals(String.valueOf(totalChild))){
            lokavenDialog.getToast(getString(R.string.text_please_complete_the_list_of_participants));
            failed = true;
        }

        if (totalAdult == 0){
            lokavenDialog.getToast(getString(R.string.text_please_input_participant_adult));
            failed = true;
        }

        if (etFirstName.getText().toString().trim().isEmpty() || etFirstName.getText().toString().trim().length() < 2){
            tilFirstName.setError(getString(R.string.text_please_input_first_name_or_first_name_less_than_2_characters));
            tilFirstName.requestFocus();
            failed = true;
        } else {
            tilFirstName.setError(null);
        }

        if (etPhoneNumber.getText().toString().trim().isEmpty() || (etPhoneNumber.getText().toString().trim().length() < 8 || etPhoneNumber.getText().toString().trim().length() > 15)){
            tilPhoneNumber.setError(getString(R.string.text_please_input_phone_number_or_phone_number_length_must_be_between_8_to_15_length));
            tilPhoneNumber.requestFocus();
            failed = true;
        } else {
            tilPhoneNumber.setError(null);
        }

        if (etEmail.getText().toString().trim().isEmpty() || !etEmail.getText().toString().trim().matches(emailPattern)){
            tilEmail.setError(getString(R.string.text_please_input_email_or_email_format_is_wrong));
            tilEmail.requestFocus();
            failed = true;
        } else {
            tilEmail.setError(null);
        }

        if (bookingProcessItem.getParticipants() == null){
            lokavenDialog.getToast(getString(R.string.text_please_complete_the_list_of_participants));
            failed = true;
        }

        if (bookingProcessItem.getParticipants().size() > 0){
            for (int i = 0; i < bookingProcessItem.getParticipants().size(); i++){
                if (bookingProcessItem.getParticipants().get(i).getAge().trim().equals("")){
                    lokavenDialog.getToast(getString(R.string.text_please_complete_the_list_of_participants));
                    failed = true;
                }
            }
        }

        if (bookingProcessItem.getTotal_price().equals("0")){
            if (typeTour.equals("close")) {
                lokavenDialog.getToast(getString(R.string.text_please_input_participant_min) + " " + tourPackageItem.getPrices().get(0).getMinParticipant());
            } else {
                lokavenDialog.getToast(getString(R.string.text_please_complete_the_list_of_participants) + " " + quotaLeft);
            }
            failed = true;
        }

        if (failed == true){
            return true;
        } else {
            return false;
        }
    }

    public boolean validationInfo(){

        boolean failed = false;
        if (totalAdult == 0){
            lokavenDialog.getToast(getString(R.string.text_please_input_participant_adult));
            failed = true;
        }

        if (etFirstName.getText().toString().trim().isEmpty() || etFirstName.getText().toString().trim().length() < 2){
            tilFirstName.setError(getString(R.string.text_please_input_first_name_or_first_name_less_than_2_characters));
            tilFirstName.requestFocus();
            failed = true;
        } else {
            tilFirstName.setError(null);
        }

        if (etPhoneNumber.getText().toString().trim().isEmpty() || (etPhoneNumber.getText().toString().trim().length() < 8 || etPhoneNumber.getText().toString().trim().length() > 15)){
            tilPhoneNumber.setError(getString(R.string.text_please_input_phone_number_or_phone_number_length_must_be_between_8_to_15_length));
            tilPhoneNumber.requestFocus();
            failed = true;
        } else {
            tilPhoneNumber.setError(null);
        }

        if (etEmail.getText().toString().trim().isEmpty() || !etEmail.getText().toString().trim().matches(emailPattern)){
            tilEmail.setError(getString(R.string.text_please_input_email_or_email_format_is_wrong));
            tilEmail.requestFocus();
            failed = true;
        } else {
            tilEmail.setError(null);
        }

        if (tvTotalPrice.getText().toString().equals("IDR 0")){
            lokavenDialog.getToast(getString(R.string.text_please_input_participant_min) + " " + tourPackageItem.getPrices().get(0).getMinParticipant());
            failed = true;
        }

        if (failed == true){
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:

                if (getIntent() != null) {
                    bookingProcessItem = (BookingProcessItem) data.getSerializableExtra("bookingProcessItem");
                    tourPackageItem = (TourPackageItem) data.getSerializableExtra("tourPackageItem");
                }
                /**
                 * Validation
                 * Get contact info
                 **/
                managePrice();

                break;

            case RESULT_CANCELED:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // OPEN TOUR GET QUOTA LEFT
        loadingPage.start();
        iViewQuotaLeft iViewQuotaLeft = this;
        BookingQuotaLeftService bookingQuotaLeftService = new BookingQuotaLeftService(this, iViewQuotaLeft);
        bookingQuotaLeftService.onGetQuotaLeft(tourPackageItem.getTourId(), bookingProcessItem.getSchedules().get(0).getScheduleId());

        // ADD HISTORY BOOKING
        BookingLite bookingLite = new BookingLite(this);
        bookingLite.addDB(bookingProcessItem);

        // ADD HISTORY TOUR PACKAGE
        TourPackageLite tourPackageLite = new TourPackageLite(this);
        tourPackageLite.addDB(tourPackageItem);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, TourPackageDetailOpenActivity.class);
        i.putExtra("tourId", tourPackageItem.getTourId());
        i.putExtra("hostId", tourPackageItem.getHostId());
        i.putExtra("typeTour", tourPackageItem.getTypeTour());
        setResult(RESULT_CANCELED, i);
        finish();
    }
}