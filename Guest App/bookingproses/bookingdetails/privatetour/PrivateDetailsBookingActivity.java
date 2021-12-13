package com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingdetails.privatetour;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.network.servicesutils.LokavenSession;
import com.aniqma.lokaventour.utils.popup.LokavenDialog;
import com.aniqma.lokaventour.helper.date.DateHelper;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.AdditionalCostItem;
import com.aniqma.lokaventour.utils.animation.LoadingPage;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.BookingProcessItem;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.ParticipantsItem;
import com.aniqma.lokaventour.model.item.tourexplorer.TourPackageItem;
import com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingaddons.AddonsBookingActivity;
import com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingdetails.DialogOrderPriceList;
import com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingsummary.SummaryBookingActivity;
import com.aniqma.lokaventour.modul.activity.booking.bookingproses.contactbooking.BookingByContactActivity;
import com.aniqma.lokaventour.modul.activity.booking.participantsinfo.ParticipantsInfoActivity;
import com.aniqma.lokaventour.network.local.bookingproses.BookingLite;
import com.aniqma.lokaventour.network.local.tourmanagement.TourPackageLite;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.aniqma.lokaventour.helper.format.NumberHelper.formatIdr;
import static com.aniqma.lokaventour.helper.url.BitmapHelper.validationPicasso;

public class PrivateDetailsBookingActivity extends AppCompatActivity implements View.OnClickListener {

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
            component_contact_booking,
            ComponentAdditionalAdult,
            ComponentAdditionalChild,
            component_add_remove_child_additional,
            component_add_remove_adult_additional,
            component_info_participant,
            component_info_contact;

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
            tvQuotaLeftDown,
            tvMaxParticipant,
            tvListItemChildAdditional,
            tvListItemAdultAdditional,
            tvNumberChildAdditional,
            tvNumberAdultAdditional,
            tvDescriptionParticipant,
            tvDescriptionContact;

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
            ivPlus,
            ibMinusChildAdditional,
            ibMinusAdultAdditional,
            ibPlusChildAdditional,
            ibPlusAdultAdditional;

    private Button btnNext,
            button_3;

    private Spinner spnPhoneNumber;

    private CheckBox cbAdditionalPrice;

    private LinearLayout llAditionalPrice,
            llAddons;

    private TextInputLayout tilEmail,
            tilPhoneNumber,
            tilFirstName;

    private int childPrice = 0, adultPrice = 0, totalAdult = 0, totalChild = 0, quotaLeft = 0, totalQuota = 0,
            minParticipant = 0, maxParticipant = 0 ,totalAdultAditional = 0, totalChildAditional = 0,
            adultPriceAdditional = 0, childPriceAdditional = 0, additionalCostAdult = 0, additionalCostChild = 0;
    private String typeTour="", emailPattern="";

    private LokavenDialog lokavenDialog;
    private LokavenSession lokavenSession;
    private LoadingPage loadingPage;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_active_order_book);

        lokavenDialog = new LokavenDialog(this);
        lokavenSession = new LokavenSession(this);
        loadingPage = new LoadingPage(this);

        if (getIntent() != null) {
            bookingProcessItem = (BookingProcessItem) getIntent().getSerializableExtra("bookingProcessItem");
            tourPackageItem = (TourPackageItem) getIntent().getSerializableExtra("tourPackageItem");
        }

        typeTour = tourPackageItem.getTypeTour();

        Initializaton();
        init();

        ivPlus.setVisibility(View.GONE);

        tvDetail.setText(R.string.text_sub_total_including_tax);
        btnNext.setText(R.string.btn_next);

        tvListItemAdult.setText(R.string.text_adults);
        tvListItemChild.setText(R.string.text_child);
        tvListItemAdultAdditional.setText(R.string.text_adults);
        tvListItemChildAdditional.setText(R.string.text_child);

        etFirstName.setHint(R.string.text_your_real_name);
        etLastName.setHint(R.string.text_optional_last_name);
        etPhoneNumber.setHint(R.string.text_your_number_here);
        etEmail.setHint(R.string.text_valid_email_address);

        //onChangeContactInfo();

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
        ibPlusAdultAdditional.setOnClickListener(this);
        ibMinusAdultAdditional.setOnClickListener(this);
        ibMinusChildAdditional.setOnClickListener(this);
        ibPlusChildAdditional.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        tvBtnInfo.setOnClickListener(this);
        rlImgMail.setOnClickListener(this);
        cbAdditionalPrice.setOnClickListener(this);
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
        btnNext = btnSheet.findViewById(R.id.component_btn_big);
        tvTotalPrice = ComponentButton.findViewById(R.id.tvTotalPrice);

        ComponentAppbarDefault = findViewById(R.id.ComponentAppbarDefault);
        ivBack = ComponentAppbarDefault.findViewById(R.id.ivBack);
        ivPlus = ComponentAppbarDefault.findViewById(R.id.ivPlus);
        tvTitle = ComponentAppbarDefault.findViewById(R.id.tvTitle);

        spnPhoneNumber = findViewById(R.id.spnPhoneNumber);

        component_contact_booking = findViewById(R.id.component_contact_booking);
        tvBtnInfo = component_contact_booking.findViewById(R.id.tvBtnInfo);
        rlImgMail = component_contact_booking.findViewById(R.id.rlImgMail);

        tvMaxParticipant = findViewById(R.id.tvMaxParticipant);
        cbAdditionalPrice = findViewById(R.id.cbAdditionalPrice);

        ComponentAdditionalChild = findViewById(R.id.ComponentAdditionalChild);
        tvListItemChildAdditional = ComponentAdditionalChild.findViewById(R.id.tvListItem);
        component_add_remove_child_additional = ComponentAdditionalChild.findViewById(R.id.component_add_remove);
        ibMinusChildAdditional = component_add_remove_child_additional.findViewById(R.id.ibMinus);
        tvNumberChildAdditional = component_add_remove_child_additional.findViewById(R.id.tvNumber);
        ibPlusChildAdditional = component_add_remove_child_additional.findViewById(R.id.ibPlus);

        ComponentAdditionalAdult = findViewById(R.id.ComponentAdditionalAdult);
        tvListItemAdultAdditional = ComponentAdditionalAdult.findViewById(R.id.tvListItem);
        component_add_remove_adult_additional = ComponentAdditionalAdult.findViewById(R.id.component_add_remove);
        ibMinusAdultAdditional = component_add_remove_adult_additional.findViewById(R.id.ibMinus);
        tvNumberAdultAdditional = component_add_remove_adult_additional.findViewById(R.id.tvNumber);
        ibPlusAdultAdditional = component_add_remove_adult_additional.findViewById(R.id.ibPlus);

        llAditionalPrice = findViewById(R.id.llAditionalPrice);
        component_info_participant = findViewById(R.id.component_info_participant);
        tvDescriptionParticipant = component_info_participant.findViewById(R.id.tvDescription);

        component_info_contact = findViewById(R.id.component_info_contact);
        tvDescriptionContact = component_info_contact.findViewById(R.id.tvDescription);

        tilEmail = findViewById(R.id.tilEmail);
        tilPhoneNumber = findViewById(R.id.tilPhoneNumber);
        tilFirstName = findViewById(R.id.tilFirstName);

        button_3 = findViewById(R.id.button_3);
        llAddons = findViewById(R.id.llAddons);
    }

    /**
     * Validation
     * Information from service
     **/
    @RequiresApi(api = Build.VERSION_CODES.M)
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

        if (!bookingProcessItem.getTotal_quota().isEmpty()){
            totalQuota = Integer.valueOf(bookingProcessItem.getTotal_quota());
        }

        if (tourPackageItem.getPrices().get(0).getKidPrice() != null){
            if (!tourPackageItem.getPrices().get(0).getKidPrice().isEmpty()){
                if (tourPackageItem.getPrices().get(0).getKidPrice().trim().equals("0")){
                    ComponentChild.setVisibility(View.GONE);
                    ComponentAdditionalChild.setVisibility(View.GONE);
                }
            } else {
                ComponentChild.setVisibility(View.GONE);
                ComponentAdditionalChild.setVisibility(View.GONE);
            }
        } else {
            ComponentChild.setVisibility(View.GONE);
            ComponentAdditionalChild.setVisibility(View.GONE);
        }

        /**
         * Validation
         * Additional Cost
         **/
        additionalCostAdult = Integer.parseInt(tourPackageItem.getAdditionalCost().getAdditional_adult_price());
        additionalCostChild = Integer.parseInt(tourPackageItem.getAdditionalCost().getAdditional_kid_price());

        if (!bookingProcessItem.getAdditionalCostItem().getAdditionalAdultQty().equals("0") || !bookingProcessItem.getAdditionalCostItem().getAdditionalKidQty().equals("0")){
            //cbAdditionalPrice.setEnabled(true);
            cbAdditionalPrice.setChecked(true);

            totalChildAditional = Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalKidQty());
            totalAdultAditional = Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalAdultQty());

            tvNumberChildAdditional.setText(String.valueOf(totalChildAditional));
            tvNumberAdultAdditional.setText(String.valueOf(totalAdultAditional));

            llAditionalPrice.setVisibility(View.VISIBLE);
            tvMaxParticipant.setVisibility(View.VISIBLE);
            cbAdditionalPrice.setVisibility(View.VISIBLE);

        } else if (bookingProcessItem.getAdditionalCostItem().getAdditionalAdultQty().equals("0") && bookingProcessItem.getAdditionalCostItem().getAdditionalKidQty().equals("0")){
            //cbAdditionalPrice.setEnabled(false);
            cbAdditionalPrice.setChecked(false);

            totalChildAditional = Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalKidQty());
            totalAdultAditional = Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalAdultQty());

            tvNumberChildAdditional.setText(String.valueOf(totalChildAditional));
            tvNumberAdultAdditional.setText(String.valueOf(totalAdultAditional));

            llAditionalPrice.setVisibility(View.GONE);
            tvMaxParticipant.setVisibility(View.GONE);
            cbAdditionalPrice.setVisibility(View.GONE);
        }

        String str1 = getString(R.string.text_you_have_reached_max_participant_for_this_tour) + " " + tourPackageItem.getPrices().get(tourPackageItem.getPrices().size()-1).getMaxParticipant() + " " + getString(R.string.text_person);
        String str2 = ", "+ getString(R.string.text_you_can_add_additional_participants_if_you_need_more);
        String str3 = str1 + str2;

        SpannableString spannable = new SpannableString(str3);
        ForegroundColorSpan mRed = new ForegroundColorSpan(getColor(R.color.LOK_Red));
        spannable.setSpan(mRed, 0, str1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString spannable2 = new SpannableString(str1);
        ForegroundColorSpan mRed2 = new ForegroundColorSpan(getColor(R.color.LOK_Red));
        spannable2.setSpan(mRed2, 0, str1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (additionalCostAdult > 0 || additionalCostChild > 0) tvMaxParticipant.setText(spannable);
        if (additionalCostAdult <= 0 && additionalCostChild <= 0) tvMaxParticipant.setText(spannable2);

        /**
         * Validation
         * Get contact info
         **/
        getContactInfo();

        /**
         * Information quota left
         **/
        tvQuotaLeft.setText(getString(R.string.text_with_yours)+" "+String.valueOf(tourPackageItem.getPrices().get(0).getMinParticipant())+ " "+getString(R.string.text_more_bookings_to_fill_min));
        tvQuotaLeftDown.setText(getString(R.string.text_with_yours)+" "+String.valueOf(tourPackageItem.getPrices().get(0).getMinParticipant())+" "+getString(R.string.text_more_bookings_to_fill_min));

        /**
         * Information quota left
         **/
        tvDescriptionParticipant.setText(getString(R.string.text_max_participant_is) + " " + tourPackageItem.getPrices().get(tourPackageItem.getPrices().size()-1).getMaxParticipant() + " " + getString(R.string.text_person) + ", " + Html.fromHtml(getString(R.string.text_read_more_about_pricing_here)));
        tvDescriptionContact.setText(getString(R.string.text_you_can_put_anyone_as_contact_info_it_will_be_used_as_contact_person_throughout_the_tour));

        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    }

    private void getContactInfo() {
        String firstName = bookingProcessItem.getContact_info().getFirst_name();
        String lastName = bookingProcessItem.getContact_info().getLast_name();
        String phoneNumber = bookingProcessItem.getContact_info().getPhone_number();
        String email = bookingProcessItem.getContact_info().getEmail();

        if (firstName.equals("-")) firstName = lokavenSession.getFirstName();
        if (lastName.equals("-")) lastName = lokavenSession.getLastName();
        if (phoneNumber.equals("-")) phoneNumber = lokavenSession.getPhoneNumber();
        if (email.equals("-")) email = lokavenSession.getEmail();

        if (bookingProcessItem.getContact_info() != null){
            etFirstName.setText(firstName);
            etLastName.setText(lastName);
            etPhoneNumber.setText(phoneNumber);
            etEmail.setText(email);
        } else {
            etFirstName.setText(firstName);
            etLastName.setText(lastName);
            etPhoneNumber.setText(phoneNumber);
            etEmail.setText(email);
        }
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
            lokavenDialog.getToast(getString(R.string.text_wrong_number_adult_participant));
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
            resetContent();
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
            lokavenDialog.getToast(getString(R.string.text_wrong_number_child_participant));
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
            resetContent();
        }
    }

    private void managePrice() {
        int i=0;
        while (i < tourPackageItem.getPrices().size()){
            int priceAdult = Integer.valueOf(tourPackageItem.getPrices().get(i).getPrice());
            int priceChild = Integer.valueOf(tourPackageItem.getPrices().get(i).getKidPrice());
            int priceAdultAdditional = Integer.valueOf(tourPackageItem.getAdditionalCost().getAdditional_adult_price());
            int priceChildAdditional = Integer.valueOf(tourPackageItem.getAdditionalCost().getAdditional_kid_price());
            int totalQuota = Integer.valueOf(bookingProcessItem.getTotal_quota());

            minParticipant = Integer.valueOf(tourPackageItem.getPrices().get(i).getMinParticipant());
            maxParticipant = Integer.valueOf(tourPackageItem.getPrices().get(i).getMaxParticipant());

            if (minParticipant <= totalQuota && totalQuota <= maxParticipant){
                adultPrice = totalAdult * priceAdult;
                childPrice = totalChild * priceChild;
                adultPriceAdditional = totalAdultAditional * priceAdultAdditional;
                childPriceAdditional = totalChildAditional * priceChildAdditional;
                break;
            }
            i++;
        }

        if (totalQuota >= minParticipant) {
            quotaLeft = maxParticipant-totalQuota;
            tvQuotaLeft.setText(getString(R.string.text_with_yours)+" "+String.valueOf(quotaLeft)+ " "+getString(R.string.text_more_bookings_to_fill_max));
            tvQuotaLeftDown.setText(getString(R.string.text_with_yours)+" "+String.valueOf(quotaLeft)+" "+getString(R.string.text_more_bookings_to_fill_max));
        } else {
            /**
             * Set default adult & child price
             * **/
            adultPrice = 0;
            childPrice = 0;

            /**
             * Set default additional cost
             * **/
            adultPriceAdditional = 0;
            childPriceAdditional = 0;

            /**
             * Set default max - min participant
             * **/
            minParticipant = Integer.valueOf(tourPackageItem.getPrices().get(0).getMinParticipant());
            maxParticipant = Integer.valueOf(tourPackageItem.getPrices().get(0).getMaxParticipant());

            lokavenDialog.getToast(getString(R.string.toast_min_participant)+" "+Integer.valueOf(tourPackageItem.getPrices().get(0).getMinParticipant()));

            quotaLeft = minParticipant+totalQuota;
            tvQuotaLeft.setText(getString(R.string.text_with_yours)+" "+String.valueOf(quotaLeft)+ " "+getString(R.string.text_more_bookings_to_fill_min));
            tvQuotaLeftDown.setText(getString(R.string.text_with_yours)+" "+String.valueOf(quotaLeft)+" "+getString(R.string.text_more_bookings_to_fill_min));
        }

        int totalAddons = Integer.valueOf(bookingProcessItem.getTemp_price_addons()) * totalQuota;

        bookingProcessItem.setQuota_left(quotaLeft);
        bookingProcessItem.setTemp_adult_price(String.valueOf(adultPrice));
        bookingProcessItem.setTemp_kid_price(String.valueOf(childPrice));
        bookingProcessItem.setTemp_adult_price_additional(String.valueOf(adultPriceAdditional));
        bookingProcessItem.setTemp_kid_price_additional(String.valueOf(childPriceAdditional));
        bookingProcessItem.setSub_price_addons(String.valueOf(totalAddons));

        tvInfoParticipant.setText(String.valueOf(maxParticipant));

        setTotalPrice();
    }

    private void setTotalPrice() {
        int adultPrice = Integer.valueOf(bookingProcessItem.getTemp_adult_price());
        int kidPrice =  Integer.valueOf(bookingProcessItem.getTemp_kid_price());
        int adultPriceAdditional = Integer.valueOf(bookingProcessItem.getTemp_adult_price_additional());
        int kidsPriceAdditional = Integer.valueOf(bookingProcessItem.getTemp_kid_price_additional());
        int subTotalAdditional = adultPriceAdditional + kidsPriceAdditional;
        int subPriceTotal = adultPrice + kidPrice;
        int subPriceAddons = Integer.valueOf(bookingProcessItem.getSub_price_addons());
        int totalPrice = subPriceTotal+subPriceAddons+subTotalAdditional;

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

    private void resetContent() {
        totalAdultAditional = 0;
        adultPriceAdditional = 0;
        totalChildAditional = 0;
        childPriceAdditional = 0;

        tvNumberChildAdditional.setText(String.valueOf(totalChildAditional));
        tvNumberAdultAdditional.setText(String.valueOf(totalAdultAditional));

        tvMaxParticipant.setVisibility(View.GONE);
        llAditionalPrice.setVisibility(View.GONE);
        cbAdditionalPrice.setVisibility(View.GONE);

        cbAdditionalPrice.setChecked(false);
        tvNumberAdultAdditional.setText("0");
        tvNumberChildAdditional.setText("0");
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
                if (totalAdult != 0){
                    setContactInfo();
                } else {
                    lokavenDialog.getToast(getResources().getString(R.string.text_please_input_participant_adult));
                }

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
        if (bookingProcessItem.getParticipants().size() != 0){
            if (bookingProcessItem.getParticipants().get(0).isAs_contact_info()) {
                bookingProcessItem.getParticipants().get(0).setFirst_name(etFirstName.getText().toString());
                bookingProcessItem.getParticipants().get(0).setLast_name(etLastName.getText().toString());
            }
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
            for (int i = 0; i < Integer.valueOf(bookingProcessItem.getTotal_quota()) + Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalAdultQty())  + Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalKidQty()); i++){
                list.add(new ParticipantsItem());
            }

            bookingProcessItem.setParticipants(list);
        }

        // ADD HISTORY BOOKING
        BookingLite bookingLite = new BookingLite(this);
        bookingLite.addDB(bookingProcessItem);
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
            lokavenDialog.getToast(getString(R.string.text_please_input_participant_min) + " " + tourPackageItem.getPrices().get(0).getMinParticipant());
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
    public void onClick(View v) {
        if (v == tvTotalParticipants){

            /**
             * Information total participant
             **/
            if (!clicked){
                ComponentSubTotalBreakDown.setVisibility(View.VISIBLE);
                ComponentSubTotal.setVisibility(View.GONE);
                clicked =true;
            }

        } else if (v == tvSubTotal){

            /**
             * Information detail subtotal
             **/
            if (clicked){
                ComponentSubTotal.setVisibility(View.VISIBLE);
                ComponentSubTotalBreakDown.setVisibility(View.GONE);
                clicked =false;
            }

        } else if (v == ComponentContactInfo || v == tvBtnInfo || v == rlImgMail){

            /**
             * Redirect to page contact booking
             **/
            Intent i = new Intent(this, BookingByContactActivity.class);
            i.putExtra("tourPackageItem", tourPackageItem);
            startActivityForResult(i, 1);

        }  else if (v == rlParticipantInfo){

            /**
             * Check validation if contact info is not filled in
             **/
            if (validationInfo()){

            }  else {

                /**
                 * Check the validation if the contact info is filled in
                 * Switch to the participant info page and fill in the participant data
                 **/
                setContactInfo();
                setParticipants();

                Intent i = new Intent(this, ParticipantsInfoActivity.class);
                i.putExtra("from", "");
                i.putExtra("bookingProcessItem", bookingProcessItem);
                i.putExtra("participant", bookingProcessItem);
                i.putExtra("tourPackageItem", tourPackageItem);
                startActivityForResult(i, 1);
            }

        } else if (v == tvClickHereSubTotal || v == tvClickHereSubTotalBreakDown){

            /**
             * Display the price list details dialog
             **/
            DialogOrderPriceList dialog = new DialogOrderPriceList(this, tourPackageItem, bookingProcessItem);
            dialog.show();

        } else if (v == ibPlusAdult){

            /**
             * Increase the number of adult participants
             * Check if this tour has an additional cost
             **/
            if(Integer.valueOf(bookingProcessItem.getTotal_quota()) < Integer.valueOf(tourPackageItem.getPrices().get(tourPackageItem.getPrices().size()-1).getMaxParticipant())){

                addAdult();

                /**
                 * If don't have additional costs, hide a message and a checkbox
                 **/
                tvMaxParticipant.setVisibility(View.GONE);
                llAditionalPrice.setVisibility(View.GONE);
                cbAdditionalPrice.setVisibility(View.GONE);

            } else {

                /**
                 * If have additional costs, display a message and a checkbox
                 **/
                tvMaxParticipant.setVisibility(View.VISIBLE);

                if (additionalCostAdult > 0 || additionalCostChild > 0) {
                    cbAdditionalPrice.setVisibility(View.VISIBLE);
                } else if (additionalCostAdult <= 0 && additionalCostChild <= 0) {
                    cbAdditionalPrice.setVisibility(View.GONE);
                }

            }

        } else if (v == ibMinusAdult){

            deltAdult();

        } else if (v == ibPlusChild){

            if (Integer.valueOf(bookingProcessItem.getTotal_quota()) < Integer.valueOf(tourPackageItem.getPrices().get(tourPackageItem.getPrices().size()-1).getMaxParticipant())){

                addChild();

                tvMaxParticipant.setVisibility(View.GONE);
                llAditionalPrice.setVisibility(View.GONE);
                cbAdditionalPrice.setVisibility(View.GONE);

            } else {

                tvMaxParticipant.setVisibility(View.VISIBLE);

                if (additionalCostAdult > 0 || additionalCostChild > 0) {
                    cbAdditionalPrice.setVisibility(View.VISIBLE);
                } else if (additionalCostAdult <= 0 && additionalCostChild <= 0) {
                    cbAdditionalPrice.setVisibility(View.GONE);
                }

            }

        } else if (v == ibMinusChild){

            delChild();

        } else if (v == ivBack){

            onBackPressed();

        } else if (v == btnNext){

            if (validation()){

            } else {

                // ADD HISTORY BOOKING
                BookingLite bookingLite = new BookingLite(this);
                bookingLite.addDB(bookingProcessItem);

                if (tourPackageItem.getAddons().size() > 0) {
                    Intent i = new Intent(this, AddonsBookingActivity.class);
                    i.putExtra("bookingProcessItem", bookingProcessItem);
                    i.putExtra("tourPackageItem", tourPackageItem);
                    startActivityForResult(i, 1);
                } else {
                    Intent i = new Intent(this, SummaryBookingActivity.class);
                    i.putExtra("bookingProcessItem", bookingProcessItem);
                    i.putExtra("tourPackageItem", tourPackageItem);
                    startActivityForResult(i, 1);
                }

            }
        } else if (v == ibPlusAdultAdditional){

            totalAdultAditional = Integer.valueOf(tvNumberAdultAdditional.getText().toString()) + 1;
            tvNumberAdultAdditional.setText(String.valueOf(totalAdultAditional));

            if (bookingProcessItem.getParticipants().size() > 0){

                int qtyAdult = Integer.valueOf(bookingProcessItem.getQty_adults()) + Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalAdultQty());

                if(totalAdult + totalAdultAditional > qtyAdult){

                    int i = qtyAdult;
                    while (i < totalAdult + totalAdultAditional){
                        bookingProcessItem.getParticipants().add(i, new ParticipantsItem());
                        i++;
                    }

                }

            }

            AdditionalCostItem additionalCostItem = new AdditionalCostItem();
            additionalCostItem.setAdditionalAdultQty(tvNumberAdultAdditional.getText().toString());
            additionalCostItem.setAdditionalKidQty(tvNumberChildAdditional.getText().toString());
            bookingProcessItem.setAdditionalCostItem(additionalCostItem);

            managePrice();

            // ADD HISTORY BOOKING
            BookingLite bookingLite = new BookingLite(this);
            bookingLite.addDB(bookingProcessItem);

        } else if (v == ibMinusAdultAdditional){

            if (Integer.valueOf(tvNumberAdultAdditional.getText().toString()) -1 == -1){

                lokavenDialog.getToast(getString(R.string.text_wrong_number_adult_participant));

            } else {

                totalAdultAditional = Integer.valueOf(tvNumberAdultAdditional.getText().toString()) - 1;
                tvNumberAdultAdditional.setText(String.valueOf(totalAdultAditional));

                if (bookingProcessItem.getParticipants().size() > 0){

                    int qtyAdult = Integer.valueOf(bookingProcessItem.getQty_adults()) + Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalAdultQty());

                    if (totalAdult + totalAdultAditional < qtyAdult){

                        int i= qtyAdult;
                        while (i > totalAdult + totalAdultAditional){
                            bookingProcessItem.getParticipants().remove(i-1);
                            i--;
                        }

                    }

                }

                AdditionalCostItem additionalCostItem = new AdditionalCostItem();
                additionalCostItem.setAdditionalAdultQty(tvNumberAdultAdditional.getText().toString());
                additionalCostItem.setAdditionalKidQty(tvNumberChildAdditional.getText().toString());
                bookingProcessItem.setAdditionalCostItem(additionalCostItem);

            }

            managePrice();

            // ADD HISTORY BOOKING
            BookingLite bookingLite = new BookingLite(this);
            bookingLite.addDB(bookingProcessItem);

        } else if (v == ibPlusChildAdditional){

            totalChildAditional = Integer.valueOf(tvNumberChildAdditional.getText().toString()) + 1;
            tvNumberChildAdditional.setText(String.valueOf(totalChildAditional));

            if (bookingProcessItem.getParticipants().size() > 0){

                int qtyAdult = Integer.valueOf(bookingProcessItem.getQty_adults()) + Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalAdultQty());
                int qtyKids = Integer.valueOf(bookingProcessItem.getQty_kids()) + Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalKidQty());
                int totalAdditonal = totalAdultAditional + totalChildAditional;

                if (totalChild + totalChildAditional > qtyKids){

                    int i = qtyAdult+qtyKids;
                    while (i < totalQuota+totalAdditonal){
                        bookingProcessItem.getParticipants().add(i, new ParticipantsItem());
                        i++;
                    }

                }
            }

            AdditionalCostItem additionalCostItem = new AdditionalCostItem();
            additionalCostItem.setAdditionalAdultQty(tvNumberAdultAdditional.getText().toString());
            additionalCostItem.setAdditionalKidQty(tvNumberChildAdditional.getText().toString());
            bookingProcessItem.setAdditionalCostItem(additionalCostItem);

            managePrice();

            // ADD HISTORY BOOKING
            BookingLite bookingLite = new BookingLite(this);
            bookingLite.addDB(bookingProcessItem);

        } else if (v == ibMinusChildAdditional){

            if (Integer.valueOf(tvNumberChildAdditional.getText().toString()) -1 == -1){

                lokavenDialog.getToast(getString(R.string.text_wrong_number_child_participant));

            } else {

                totalChildAditional = Integer.valueOf(tvNumberChildAdditional.getText().toString()) - 1;
                tvNumberChildAdditional.setText(String.valueOf(totalChildAditional));

                if (bookingProcessItem.getParticipants().size() > 0){

                    int qtyAdult = Integer.valueOf(bookingProcessItem.getQty_adults()) + Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalAdultQty());
                    int qtyKids = Integer.valueOf(bookingProcessItem.getQty_kids()) + Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalKidQty());
                    int totalAdditonal = totalAdultAditional + totalChildAditional;

                    if (totalChild + totalChildAditional < qtyKids){

                        int i = qtyAdult+qtyKids;
                        while (i > totalQuota + totalAdditonal){
                            bookingProcessItem.getParticipants().remove(i-1);
                            i--;
                        }

                    }
                }

                AdditionalCostItem additionalCostItem = new AdditionalCostItem();
                additionalCostItem.setAdditionalAdultQty(tvNumberAdultAdditional.getText().toString());
                additionalCostItem.setAdditionalKidQty(tvNumberChildAdditional.getText().toString());
                bookingProcessItem.setAdditionalCostItem(additionalCostItem);
            }

            managePrice();

            // ADD HISTORY BOOKING
            BookingLite bookingLite = new BookingLite(this);
            bookingLite.addDB(bookingProcessItem);

        } else if (v == cbAdditionalPrice){

            if (cbAdditionalPrice.isChecked()){

                llAditionalPrice.setVisibility(View.VISIBLE);

            } else {

                if (bookingProcessItem.getParticipants().size() > 0){

                    int qtyAdult = Integer.valueOf(bookingProcessItem.getQty_adults()) + Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalAdultQty());
                    int qtyKids = Integer.valueOf(bookingProcessItem.getQty_kids()) + Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalKidQty());

                    if (totalChild < qtyKids){

                        int i = qtyAdult+qtyKids;
                        while (i > totalQuota + totalAdultAditional){
                            bookingProcessItem.getParticipants().remove(i-1);
                            i--;
                        }

                    }
                }

                if (bookingProcessItem.getParticipants().size() > 0){

                    int qtyAdult = Integer.valueOf(bookingProcessItem.getQty_adults()) + Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalAdultQty());

                    if (totalAdult < qtyAdult){

                        int i= qtyAdult;
                        while (i > totalAdult){
                            bookingProcessItem.getParticipants().remove(i-1);
                            i--;
                        }

                    }
                }

                llAditionalPrice.setVisibility(View.GONE);
                totalAdultAditional = 0;
                adultPriceAdditional = 0;
                totalChildAditional = 0;
                childPriceAdditional = 0;
                tvNumberChildAdditional.setText(String.valueOf(totalChildAditional));
                tvNumberAdultAdditional.setText(String.valueOf(totalAdultAditional));

                managePrice();

                AdditionalCostItem additionalCostItem = new AdditionalCostItem();
                additionalCostItem.setAdditionalAdultQty(tvNumberAdultAdditional.getText().toString());
                additionalCostItem.setAdditionalKidQty(tvNumberChildAdditional.getText().toString());
                bookingProcessItem.setAdditionalCostItem(additionalCostItem);

                // ADD HISTORY BOOKING
                BookingLite bookingLite = new BookingLite(this);
                bookingLite.addDB(bookingProcessItem);

            }
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
                getContactInfo();

                break;

            case RESULT_CANCELED:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // ADD HISTORY BOOKING
        BookingLite bookingLite = new BookingLite(this);
        bookingLite.addDB(bookingProcessItem);

        // ADD HISTORY TOUR PACKAGE
        TourPackageLite tourPackageLite = new TourPackageLite(this);
        tourPackageLite.addDB(tourPackageItem);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}