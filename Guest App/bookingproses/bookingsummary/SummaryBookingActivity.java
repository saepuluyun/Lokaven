package com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingsummary;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.model.item.listpromo.PromoItem;
import com.aniqma.lokaventour.model.response.bookingprocess.DataBooking;
import com.aniqma.lokaventour.modul.activity.booking.promo.promolist.PromoListActivity;
import com.aniqma.lokaventour.utils.popup.LokavenDialog;
import com.aniqma.lokaventour.helper.date.DateHelper;
import com.aniqma.lokaventour.utils.animation.LoadingPage;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.SummaryTotalItem;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.BookingProcessItem;
import com.aniqma.lokaventour.model.response.bookingprocess.BookingProcessResponse;
import com.aniqma.lokaventour.model.item.tourexplorer.TourPackageItem;
import com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingaddons.AddonsBookingActivity;
import com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingdetails.opentour.OpenDetailsBookingActivity;
import com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingdetails.privatetour.PrivateDetailsBookingActivity;
import com.aniqma.lokaventour.modul.activity.booking.bookingreceipt.PendingBookingOrderActivity;
import com.aniqma.lokaventour.modul.activity.booking.editcontactinfo.EditContactInfoActivity;
import com.aniqma.lokaventour.modul.activity.booking.participantsinfo.ParticipantsInfoActivity;
import com.aniqma.lokaventour.modul.activity.booking.bookingreceipt.UnpaidBookingReceiptActivity;
import com.aniqma.lokaventour.network.local.bookingproses.BookingLite;
import com.aniqma.lokaventour.network.local.tourmanagement.TourPackageLite;
import com.aniqma.lokaventour.network.services.authentication.refreshtokenandintrospect.RefreshTokenServices;
import com.aniqma.lokaventour.network.services.bookingservice.bookingprocess.opentourbookingprocess.OpenTourBookingProcessService;
import com.aniqma.lokaventour.network.services.bookingservice.bookingprocess.opentourbookingprocess.IViewOpenTourBookingProcessService;
import com.aniqma.lokaventour.network.services.authentication.refreshtokenandintrospect.IViewCheckToken;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.aniqma.lokaventour.helper.format.NumberHelper.formatIdr;
import static com.aniqma.lokaventour.helper.url.BitmapHelper.validationPicasso;

public class SummaryBookingActivity extends AppCompatActivity implements View.OnClickListener, IViewOpenTourBookingProcessService, IViewCheckToken {

    public final static int RESULT_PROMO = 99;
    private BookingProcessItem bookingProcessItem;
    private TourPackageItem tourPackageItem;
    private String typeTour;

    private TextView tv_edit_info,
            tvDetail,
            tvDescription,
            tvTotalPrice,
            tvNamePackage,
            tvLocation,
            tvDuration,
            tvStartDate,
            tvEndDate,
            tvName,
            tvEmail,
            tvPhoneNumber,
            tvNumberParticipants,
            tvTotalAdult,
            tvTotalChild,
            tvTotal,
            tvTitle,
            tvNumberLunch,
            tvNumberParticipant,
            tvTitleBank,
            tvNumberBank,
            tvAdd,
            tvNamePromo,
            tvPromo,
            tvRemove,
            text_view_1;

    private ImageView
            thumbnailTour,
            ivEditContactInfo,
            ivEditParticipants,
            ivBack,
            ivPlus,
            ivPackage;
    private LinearLayout llContactInfo, llParticipant, llListConversation,
            llAddons;
    private boolean bca_clicked = false;
    private boolean credit_card_cliked = true;
    private double total = 0;
    private RecyclerView recyclerview_summary;
    private SummaryTotalAdapter summaryTotalAdapter;
    private RecyclerView.LayoutManager layoutSummaryTotal;
    private View component_attention,
            ComponentButton,
            btnSheet,
            ComponentAppbarDefault,
            component_cards_package_ticket_summary;
    private List<SummaryTotalItem> summaryTotalItems;
    private PromoItem promoData;
    private Button btnNext,
            button_3;

    private CardView cvProfile;
    private RadioButton rbList1, rbList2;

    private IViewOpenTourBookingProcessService iViewOpenTourBookingProcessService;
    private OpenTourBookingProcessService presenter;
    private IViewCheckToken iViewCheckToken;

    private LokavenDialog lokavenDialog;
    private LoadingPage loadingPage;
    private String text = "";
    private int discount = 0;
    private String code_promo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_active_order_checkout);

        loadingPage = new LoadingPage(this);
        lokavenDialog = new LokavenDialog(this);

        if (getIntent() != null) {
            bookingProcessItem = (BookingProcessItem) getIntent().getSerializableExtra("bookingProcessItem");
            tourPackageItem = (TourPackageItem) getIntent().getSerializableExtra("tourPackageItem");
        }

        typeTour = tourPackageItem.getTypeTour();

        iViewCheckToken = this;
        Initialization();

        String thumbnail = tourPackageItem.getMedias().get(0).getUrl();
        String duration = "";

        if (bookingProcessItem.getSchedules().get(0).getDuration().equals("0")){
            duration = "1";
        } else {
            duration = bookingProcessItem.getSchedules().get(0).getDuration();
        }

        if (tourPackageItem.getAddons() != null){
            if (tourPackageItem.getAddons().size() == 0 ){
                llAddons.setVisibility(View.GONE);
                button_3.setText("2");
                ViewGroup.LayoutParams layoutParams = text_view_1.getLayoutParams();
                layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                text_view_1.setLayoutParams(layoutParams);
            } else {
                llAddons.setVisibility(View.VISIBLE);
            }
        }

        String startDate = bookingProcessItem.getSchedules().get(0).getStartDate();
        String endDate = bookingProcessItem.getSchedules().get(0).getEndDate();

        int qtyAdult = Integer.valueOf(bookingProcessItem.getQty_adults());
        int qtyKids = Integer.valueOf(bookingProcessItem.getQty_kids());
        int qtyAdultAdditional = Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalAdultQty());
        int qtyKidsAdditional = Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalKidQty());

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
        tvDuration.setText(duration);

        try {
            tvStartDate.setText(DateHelper.getDateFormatMonthandYear(startDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            tvEndDate.setText(DateHelper.getDateFormatMonthandYear(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        getContactInfo();

        tvNumberParticipants.setText(String.valueOf(qtyAdult + qtyKids + qtyAdultAdditional + qtyKidsAdditional));
        tvTotalAdult.setText(String.valueOf(qtyAdult + qtyAdultAdditional));
        tvTotalChild.setText(String.valueOf(qtyKids + qtyKidsAdditional));

        String countParticipan = String.valueOf(qtyAdult + qtyKids + qtyAdultAdditional + qtyKidsAdditional);
        if (bookingProcessItem.getAddons().size() != 0){
            text = getResources().getString(R.string.text_price_for) + " " + countParticipan + " " + getResources().getString(R.string.text_participants) + " " +
                    getResources().getString(R.string.text_and) + " " + String.valueOf(bookingProcessItem.getAddons().size()) + " " + getResources().getString(R.string.text_addons) +  " " + getResources().getString(R.string.text_excluding_admin_fee);

        } else {
            text = getResources().getString(R.string.text_price_for) + " " + countParticipan + " " + getResources().getString(R.string.text_participants) + getResources().getString(R.string.text_excluding_admin_fee);

        }

        tvNumberParticipant.setText(text);

        tvDetail.setText(R.string.text_sub_total_including_tax);
        btnNext.setText(R.string.btn_next);
        tvTitle.setText(R.string.text_booking);
        ivPlus.setVisibility(View.GONE);
        rbList1.setChecked(true);

        if (typeTour.equals("open")){
            component_attention.setVisibility(View.GONE); // sementara di gone dulu
            String textQuota = "You will only need to pay once the bookings filled the min quota. <b>Currently needs "+ bookingProcessItem.getQuota_left() +" more bookings.</b>";
            tvDescription.setText(Html.fromHtml(textQuota));
        }else {
            component_attention.setVisibility(View.GONE);
        }

        setList();
        setTotal();

        llParticipant.setOnClickListener(this);
        llContactInfo.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        rbList1.setOnClickListener(this);
        rbList2.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        tvRemove.setOnClickListener(this);
    }

    private void Initialization() {
        recyclerview_summary = findViewById(R.id.recyclerview_summary);
        summaryTotalItems = new ArrayList<>();
        llContactInfo = findViewById(R.id.llContactInfo);
        llParticipant = findViewById(R.id.llParticipant);
        component_attention = findViewById(R.id.component_attention);
        tvDescription = component_attention.findViewById(R.id.tvDescription);

        rbList1 = findViewById(R.id.rbList1);
        rbList2 = findViewById(R.id.rbList2);

        component_cards_package_ticket_summary = findViewById(R.id.component_cards_package_ticket_summary);

        thumbnailTour = component_cards_package_ticket_summary.findViewById(R.id.imgThumbnail);
        tvNamePackage = component_cards_package_ticket_summary.findViewById(R.id.tvTitleTour);
        tvLocation = component_cards_package_ticket_summary.findViewById(R.id.tvLocationTour);
        tvDuration = component_cards_package_ticket_summary.findViewById(R.id.tvDuration);
        tvStartDate = component_cards_package_ticket_summary.findViewById(R.id.tvStartDate);
        tvEndDate = component_cards_package_ticket_summary.findViewById(R.id.tvEndDate);

        tvName = component_cards_package_ticket_summary.findViewById(R.id.tvName);
        tvEmail = component_cards_package_ticket_summary.findViewById(R.id.tvEmail);
        tvPhoneNumber = component_cards_package_ticket_summary.findViewById(R.id.tvPhoneNumber);
        ivEditContactInfo = component_cards_package_ticket_summary.findViewById(R.id.ivEditContactInfo);

        tvNumberParticipants = component_cards_package_ticket_summary.findViewById(R.id.tvNumberParticipants);
        tvTotalAdult = component_cards_package_ticket_summary.findViewById(R.id.tvTotalAdult);
        tvTotalChild = component_cards_package_ticket_summary.findViewById(R.id.tvTotalChild);
        ivEditParticipants = component_cards_package_ticket_summary.findViewById(R.id.ivEditParticipants);

        ComponentButton = findViewById(R.id.ComponentButton);
        btnSheet = ComponentButton.findViewById(R.id.btnSheet);
        tvDetail = ComponentButton.findViewById(R.id.tvDetail);
        btnNext = btnSheet.findViewById(R.id.component_btn_big);
        tvTotalPrice = ComponentButton.findViewById(R.id.tvTotalPrice);

        ComponentAppbarDefault = findViewById(R.id.ComponentAppbarDefault);
        ivBack = ComponentAppbarDefault.findViewById(R.id.ivBack);
        ivPlus = ComponentAppbarDefault.findViewById(R.id.ivPlus);
        tvTitle = ComponentAppbarDefault.findViewById(R.id.tvTitle);

        tvTotal = findViewById(R.id.tvTotal);

        tvNumberParticipant = findViewById(R.id.tvNumberParticipant);
        tvNumberLunch = findViewById(R.id.tvNumberLunch);

        tvTitleBank = findViewById(R.id.tvTitleBank);
        tvNumberBank = findViewById(R.id.tvNumberBank);

        tvAdd = findViewById(R.id.tvAdd);
        tvRemove = findViewById(R.id.tvRemove);
        cvProfile = findViewById(R.id.cvProfile);
        ivPackage = findViewById(R.id.ivPackage);
        tvNamePromo = findViewById(R.id.tvNamePromo);
        tvPromo = findViewById(R.id.tvPromo);

        llListConversation = findViewById(R.id.llListConversation);

        llAddons = findViewById(R.id.llAddons);
        button_3 = findViewById(R.id.button_3);
        text_view_1 = findViewById(R.id.text_view_1);
    }

    private void getContactInfo() {
        String firstName = bookingProcessItem.getContact_info().getFirst_name();
        String lastName = bookingProcessItem.getContact_info().getLast_name();
        String email = bookingProcessItem.getContact_info().getEmail();
        String strCodePhone = bookingProcessItem.getContact_info().getCode_phone();
        String phoneNumber = bookingProcessItem.getContact_info().getPhone_number();
        String codePhone = strCodePhone.replace("+", "");

        tvName.setText(firstName + " " + lastName);
        tvEmail.setText(email);
        tvPhoneNumber.setText(codePhone+phoneNumber);
    }

    private void setList() {
        recyclerview_summary.setHasFixedSize(true);
        layoutSummaryTotal = new LinearLayoutManager(this);
        recyclerview_summary.setLayoutManager(layoutSummaryTotal);
        fillListItem();
        summaryTotalAdapter = new SummaryTotalAdapter(this, summaryTotalItems);
        recyclerview_summary.setAdapter(summaryTotalAdapter);
    }

    private void fillListItem() {
        int qtyAdult = Integer.valueOf(bookingProcessItem.getQty_adults());
        int qtyKids = Integer.valueOf(bookingProcessItem.getQty_kids());
        int qtyAdultAdditional = Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalAdultQty());
        int qtyKidsAdditional = Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalKidQty());

        if (qtyAdult>0) {
            SummaryTotalItem summaryTotalItem = new SummaryTotalItem();
            summaryTotalItem.setCount(qtyAdult);
            summaryTotalItem.setPrice(Integer.valueOf(tourPackageItem.getPrices().get(0).getPrice()));
            summaryTotalItem.setDescription(getString(R.string.text_adults));
            summaryTotalItems.add(summaryTotalItem);
        }

        if (qtyKids>0) {
            SummaryTotalItem summaryTotalItem = new SummaryTotalItem();
            summaryTotalItem.setCount(qtyKids);
            summaryTotalItem.setPrice(Integer.valueOf(tourPackageItem.getPrices().get(0).getKidPrice()));
            summaryTotalItem.setDescription(getString(R.string.text_children));
            summaryTotalItems.add(summaryTotalItem);
        }

        if (qtyAdultAdditional>0){
            SummaryTotalItem summaryTotalItem = new SummaryTotalItem();
            summaryTotalItem.setCount(qtyAdultAdditional);
            summaryTotalItem.setPrice(Integer.valueOf(tourPackageItem.getAdditionalCost().getAdditional_adult_price()));
            summaryTotalItem.setDescription(getString(R.string.text_additional_adult));
            summaryTotalItems.add(summaryTotalItem);
        }

        if (qtyKidsAdditional>0){
            SummaryTotalItem summaryTotalItem = new SummaryTotalItem();
            summaryTotalItem.setCount(qtyKidsAdditional);
            summaryTotalItem.setPrice(Integer.valueOf(tourPackageItem.getAdditionalCost().getAdditional_kid_price()));
            summaryTotalItem.setDescription(getString(R.string.text_additional_children));
            summaryTotalItems.add(summaryTotalItem);
        }

        if (bookingProcessItem.getAddons().size() > 0){
            for (int i = 0; i < bookingProcessItem.getAddons().size(); i++){
                SummaryTotalItem summaryTotalItem = new SummaryTotalItem();
                if (!bookingProcessItem.getAddons().get(i).getPriceType().equals("item")) {
                    summaryTotalItem.setCount(Integer.valueOf(bookingProcessItem.getQty_adults()) + Integer.valueOf(bookingProcessItem.getQty_kids()) + Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalKidQty()) + Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalAdultQty()));
                } else {
                    summaryTotalItem.setCount(bookingProcessItem.getAddons().get(i).getTotalQty());
                }
                summaryTotalItem.setPrice(Integer.valueOf(bookingProcessItem.getAddons().get(i).getPrice()));
                summaryTotalItem.setDescription(bookingProcessItem.getAddons().get(i).getAddon());
                summaryTotalItem.setTypeAddons(bookingProcessItem.getAddons().get(i).getPriceType());
                summaryTotalItem.setQtyItemAddons(bookingProcessItem.getAddons().get(i).getQty());
                summaryTotalItems.add(summaryTotalItem);
            }
        }
    }

    private void setTotal() {
        int adultPrice = Integer.valueOf(bookingProcessItem.getTemp_adult_price());
        int kidPrice =  Integer.valueOf(bookingProcessItem.getTemp_kid_price());
        int adultPriceAdditonal = Integer.valueOf(bookingProcessItem.getTemp_adult_price_additional());
        int kidPriceAdditional = Integer.valueOf(bookingProcessItem.getTemp_kid_price_additional());
        int subPriceTotal = adultPrice + kidPrice + adultPriceAdditonal + kidPriceAdditional;
        int subPriceAddons = Integer.valueOf(bookingProcessItem.getSub_price_addons());
        int totalPrice = subPriceTotal+subPriceAddons;

        total = Double.parseDouble(String.valueOf(totalPrice));

        tvTotal.setText(formatIdr(total));
        tvTotalPrice.setText(formatIdr(total));
    }

    private void onBooking() {
        iViewOpenTourBookingProcessService = this;
        presenter = new OpenTourBookingProcessService(this, iViewOpenTourBookingProcessService, bookingProcessItem, tourPackageItem);
        presenter.onOpenBookingProcess();
    }

    @Override
    public void onSuccessBoooking(BookingProcessResponse bookingProcessResponse) {
        DataBooking data = bookingProcessResponse.getData();

        BookingLite bookingLite = new BookingLite(this);
        bookingLite.deleteDB();

        TourPackageLite tourPackageLite = new TourPackageLite(this);
        tourPackageLite.deleteDB();

        loadingPage.stop();

        if (data.getStatus().equals("unpaid")){
            String orderNumber = data.getOrderNumber();
            Intent i = new Intent(this, UnpaidBookingReceiptActivity.class);
            i.putExtra("tour_id", tourPackageItem.getTourId());
            i.putExtra("orderNumber", orderNumber);
            i.putExtra("order_id", data.getOrderId());
            startActivityForResult(i, 1);
        } else if (data.getStatus().equals("awaiting more bookings")){
            String orderNumber = data.getOrderNumber();
            Intent i = new Intent(this, PendingBookingOrderActivity.class);
            i.putExtra("tour_id", tourPackageItem.getTourId());
            i.putExtra("orderNumber", orderNumber);
            startActivityForResult(i, 1);
        }
    }

    @Override
    public void onErrorBooking(String message, String status, int responseCode) {
        if (responseCode == 401) {
            IViewCheckToken iViewCheckToken = this;
            RefreshTokenServices refreshTokenServices = new RefreshTokenServices(this, iViewCheckToken);
            refreshTokenServices.onInstrospectToken();
        } else {
            loadingPage.stop();
            lokavenDialog.dialogError(message, status);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_PROMO:
                promoData = (PromoItem) data.getSerializableExtra("data");
                tvAdd.setVisibility(View.GONE);
                tvRemove.setVisibility(View.VISIBLE);
                cvProfile.setVisibility(View.VISIBLE);
                llListConversation.setVisibility(View.VISIBLE);
                if (!promoData.getImage().isEmpty()){
                    Picasso.get()
                            .load(validationPicasso(promoData.getImage()))
                            .placeholder(R.drawable.thumbnail_default)
                            .centerCrop()
                            .fit()
                            .into(ivPackage);
                }

                tvNamePromo.setText(promoData.getTitle_promo());
                tvNumberParticipant.setText(text + getResources().getString(R.string.text_after_discount));

                code_promo = promoData.getCode_promo();

                if (promoData.getDiscount() < 1){
                    double discountTotal = total * promoData.getDiscount();
                    int totalDiscount = Integer.parseInt(String.valueOf(Math.round(discountTotal)));
                    tvPromo.setText("-" + Math.round(promoData.getDiscount())*100 + "%");
                    tvTotal.setText(formatIdr(total - discountTotal));
                    tvTotalPrice.setText(formatIdr(total - discountTotal));

                    SummaryTotalItem summaryTotalItem = new SummaryTotalItem();
                    summaryTotalItem.setCount(1);
                    summaryTotalItem.setPrice(totalDiscount);
                    summaryTotalItem.setDescription(getResources().getString(R.string.text_promo));
                    summaryTotalItems.add(summaryTotalItem);

                    summaryTotalAdapter.notifyDataSetChanged();
                    discount = totalDiscount;
                } else {
                    double price = Double.parseDouble(String.valueOf(Math.round(promoData.getDiscount())));
                    int totalDiscount = Integer.parseInt(String.valueOf(Math.round(promoData.getDiscount())));
                    tvPromo.setText("-" + formatIdr(price));
                    tvTotal.setText(formatIdr(total - price));
                    tvTotalPrice.setText(formatIdr(total - price));

                    SummaryTotalItem summaryTotalItem = new SummaryTotalItem();
                    summaryTotalItem.setCount(1);
                    summaryTotalItem.setPrice(totalDiscount);
                    summaryTotalItem.setDescription(getResources().getString(R.string.text_promo));
                    summaryTotalItems.add(summaryTotalItem);

                    summaryTotalAdapter.notifyDataSetChanged();
                    discount = totalDiscount;
                }

                break;

            case RESULT_CANCELED:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (view == llParticipant){

            Intent i = new Intent(this, ParticipantsInfoActivity.class);
            i.putExtra("from", "summary");
            i.putExtra("bookingProcessItem", bookingProcessItem);
            i.putExtra("participant", bookingProcessItem);
            i.putExtra("tourPackageItem", tourPackageItem);
            startActivityForResult(i, 1);

        } else if (view == llContactInfo){

            Intent i = new Intent(this, EditContactInfoActivity.class);
            i.putExtra("from", "summary");
            i.putExtra("bookingProcessItem", bookingProcessItem);
            i.putExtra("tourPackageItem", tourPackageItem);
            startActivityForResult(i, 1);

        } else if (view == btnNext){
            loadingPage.start();
            String strCodePhone = bookingProcessItem.getContact_info().getCode_phone();
            String phoneNumber = bookingProcessItem.getContact_info().getPhone_number();
            String codePhone = strCodePhone.replace("+", "");
            bookingProcessItem.getContact_info().setPhone_number(codePhone+phoneNumber);
            bookingProcessItem.setPromo_code(code_promo);
            bookingProcessItem.setTotal_price(String.valueOf(Integer.parseInt(bookingProcessItem.getTotal_price()) - discount));
            bookingProcessItem.setTerms_of_service(true);
            bookingProcessItem.setPrivacy_policy(true);
            bookingProcessItem.setRefund_policy(true);

            onBooking();

        } else if (view == ivBack){

            onBackPressed();

        } else if (view == rbList1){

            rbList1.setChecked(true);
            rbList2.setChecked(false);

        } else if (view == rbList2){

            rbList1.setChecked(false);
            rbList2.setChecked(true);

        } else if (view == tvAdd){
            Intent intent = new Intent(this, PromoListActivity.class);
            intent.putExtra("tourId", tourPackageItem.getTourId());
            startActivityForResult(intent, 1);
        } else if (view == tvRemove){
            tvAdd.setVisibility(View.VISIBLE);
            tvRemove.setVisibility(View.GONE);
            cvProfile.setVisibility(View.GONE);
            llListConversation.setVisibility(View.GONE);
            promoData = null;
            summaryTotalItems.clear();
            total = 0;
            fillListItem();
            setTotal();
            summaryTotalAdapter.notifyDataSetChanged();
            tvNumberParticipant.setText(text);
            code_promo = "";
            discount = 0;
        }
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
        if (tourPackageItem.getAddons().size() > 0) {
            Intent i = new Intent(this, AddonsBookingActivity.class);
            i.putExtra("bookingProcessItem", bookingProcessItem);
            i.putExtra("tourPackageItem", tourPackageItem);
            setResult(RESULT_OK, i);
            finish();
        } else {
            String str = bookingProcessItem.getParticipants().get(0).getPhone_number();
            str = str.replace("+62","");

            bookingProcessItem.getParticipants().get(0).setPhone_number(str);

            Intent i;
            if (typeTour.equals("open")) {
                i = new Intent(this, OpenDetailsBookingActivity.class);
            } else {
                i = new Intent(this, PrivateDetailsBookingActivity.class);
            }
            i.putExtra("bookingProcessItem", bookingProcessItem);
            i.putExtra("tourPackageItem", tourPackageItem);
            setResult(RESULT_OK, i);
            finish();
        }
    }
}
