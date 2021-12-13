package com.aniqma.lokaventour.modul.activity.booking.bookingreceipt;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.helper.date.DateHelper;
import com.aniqma.lokaventour.modul.activity.general.notification.NotificationsActivity;
import com.aniqma.lokaventour.utils.animation.ShimmerEffect;
import com.aniqma.lokaventour.utils.popup.LokavenDialog;
import com.aniqma.lokaventour.utils.animation.LoadingPage;
import com.aniqma.lokaventour.utils.misc.successsnackbars.CheckSnackbars;
import com.aniqma.lokaventour.model.item.bookingService.bookingpayment.GetDataInvoicesItem;
import com.aniqma.lokaventour.model.response.bookingpending.DataBookingReceiptResponse;
import com.aniqma.lokaventour.model.response.bookingpending.BookingReceiptResponse;
import com.aniqma.lokaventour.model.response.invoice.DataInvoice;
import com.aniqma.lokaventour.modul.activity.general.home.HomeActivity;
import com.aniqma.lokaventour.network.services.authentication.refreshtokenandintrospect.RefreshTokenServices;
import com.aniqma.lokaventour.network.servicesutils.LokavenSession;
import com.aniqma.lokaventour.network.services.bookingservice.bookingprocess.bookingreceipt.BookingReceiptService;
import com.aniqma.lokaventour.network.services.authentication.refreshtokenandintrospect.IViewCheckToken;
import com.aniqma.lokaventour.network.services.bookingservice.bookingprocess.bookingreceipt.IViewBookingReceipt;
import com.aniqma.lokaventour.network.services.bookingservice.bookingprocess.payment.GetInvoicesServices;
import com.aniqma.lokaventour.network.services.bookingservice.bookingprocess.payment.iViewGetInvoices;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.aniqma.lokaventour.helper.format.AlphabetHelper.firstCapitalize;
import static com.aniqma.lokaventour.helper.format.NumberHelper.formatIdr;
import static com.aniqma.lokaventour.helper.url.UrlHelper.IsValidUrl;
import static com.aniqma.lokaventour.modul.activity.general.home.HomeActivity.RESULT_BACK;

public class UnpaidBookingReceiptActivity extends AppCompatActivity
        implements View.OnClickListener, IViewCheckToken,
        IViewBookingReceipt, iViewGetInvoices {

    private TextView tv_placed_and_waiting,
            tv_follow_instuction,
            tvChipsStatus,
            tvNamePackage,
            tvDuration,
            tvLocationHost,
            tvStartDate,
            tvEndDate,
            tvName,
            tvEmail,
            tvPhoneNumber,
            tvNumberParticipant,
            tvNumberAdult,
            tvNumberChild,
            tvOrderNumber,
            tvAmount,
            tvCopyAmount,
            tvCreditCardNumber,
            tvCopyNumber,
            tvChangePayment,
            tvDateExp,
            tvCountDown,
            tvServiceFailed,
            tvAdminFee;

    private CollapsingToolbarLayout collapseActionView;
    private LinearLayout
            llContent,
            llPaidAt,
            llInformation,
            llToolbarServiceError;

    private RelativeLayout
            rlContent,
            rlNoDataFound,
            rlTitleATM,
            rlAccordionATM,
            rlTitleMobile,
            rlAccordionMobile,
            rlTitleIBangking,
            rlAccordionIBangking,
            rlTitleOffice,
            rlAccordionOffice,
            rlClose;

    private ImageView ivEditInfo,
            ivEditParticipants,
            ivArrowATM,
            ivArrowMobile,
            ivArrowBanking,
            ivArrowOffice,
            imgProfile,
            imgServiceFailed;

    private ShimmerFrameLayout shimmerContainer;

    private View btnSheet;
    private Button component_btn_big;

    private String tour_id = "", orderNumber = "", order_id = "", booking_uid = "", urlInvoices = "";
    private int from = 0;
    private boolean notif = false;

    private View cardview_status_booking;
    private DataInvoice dataInvoice;
    private DataBookingReceiptResponse dataPending;

    private LokavenSession lokavenSession;
    private LokavenDialog lokavenDialog;
    private ShimmerEffect shimmerEffect;
    private LoadingPage loadingPage;

    private long hour = 0;
    private long min = 0;
    private long second = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unpaid_booking_receipt);
        init();
        SetChipStatus();

        lokavenSession = new LokavenSession(this);
        lokavenDialog = new LokavenDialog(this);
        shimmerEffect = new ShimmerEffect(this, shimmerContainer, llContent);
        loadingPage = new LoadingPage(this);

        ivEditParticipants.setVisibility(View.GONE);
        ivEditInfo.setVisibility(View.GONE);
        llPaidAt.setVisibility(View.GONE);
        llToolbarServiceError.setVisibility(View.GONE);

        String instuctionToPay = "Follow instuctions bellow on how to make a payment via <b>BCA Virtual Account</b>";

        tv_placed_and_waiting.setText(getString(R.string.text_placed_and_waiting_for_your_payment));
        //tv_follow_instuction.setText(Html.fromHtml(instuctionToPay));
        collapseActionView.setContentScrimColor(this.getResources().getColor(R.color.LOK_White));
        rlTitleATM.setOnClickListener(this);
        rlTitleIBangking.setOnClickListener(this);
        rlTitleMobile.setOnClickListener(this);
        rlTitleOffice.setOnClickListener(this);
        rlClose.setOnClickListener(this);
        tvCopyAmount.setOnClickListener(this);
        tvCopyNumber.setOnClickListener(this);
        imgServiceFailed.setOnClickListener(this);
        component_btn_big.setOnClickListener(this);
    }

    private void init() {
        shimmerContainer = findViewById(R.id.shimmerContainer);
        llContent = findViewById(R.id.llContent);
        llInformation = findViewById(R.id.llInformation);
        tv_placed_and_waiting = findViewById(R.id.tv_placed_and_waiting);
        tv_follow_instuction = findViewById(R.id.tv_follow_instuction);
        collapseActionView = findViewById(R.id.collapseActionView);
        rlTitleATM = findViewById(R.id.rlTitleATM);
        rlAccordionATM = findViewById(R.id.rlAccordionATM);
        rlTitleMobile = findViewById(R.id.rlTitleMobile);
        rlAccordionMobile = findViewById(R.id.rlAccordionMobile);
        rlTitleIBangking = findViewById(R.id.rlTitleIBangking);
        rlAccordionIBangking = findViewById(R.id.rlAccordionIBangking);
        rlTitleOffice = findViewById(R.id.rlTitleOffice);
        rlAccordionOffice = findViewById(R.id.rlAccordionOffice);
        rlClose = findViewById(R.id.rlClose);
        tvChipsStatus = findViewById(R.id.tvChips);
        ivEditInfo = findViewById(R.id.ivEditInfo);
        ivEditParticipants = findViewById(R.id.ivEditParticipants);
        ivArrowOffice = findViewById(R.id.ivArrowOffice);
        ivArrowATM = findViewById(R.id.ivArrowATM);
        ivArrowBanking = findViewById(R.id.ivArrowBanking);
        ivArrowMobile = findViewById(R.id.ivArrowMobile);

        cardview_status_booking = findViewById(R.id.cardview_status_booking);
        rlContent = cardview_status_booking.findViewById(R.id.rlContent);
        rlNoDataFound = cardview_status_booking.findViewById(R.id.rlNoDataFound);
        imgProfile = cardview_status_booking.findViewById(R.id.imgThumbnail);
        tvNamePackage = cardview_status_booking.findViewById(R.id.tvTitleTour);
        tvLocationHost = cardview_status_booking.findViewById(R.id.tvLocationTour);
        tvDuration = cardview_status_booking.findViewById(R.id.tvDuration);
        tvStartDate = cardview_status_booking.findViewById(R.id.tvStartDate);
        tvEndDate = cardview_status_booking.findViewById(R.id.tvEndDate);
        tvName = cardview_status_booking.findViewById(R.id.tvName);
        tvEmail = cardview_status_booking.findViewById(R.id.tvEmail);
        tvPhoneNumber = cardview_status_booking.findViewById(R.id.tvPhoneNumber);
        tvNumberParticipant = cardview_status_booking.findViewById(R.id.tvNumberParticipant);
        tvNumberAdult = cardview_status_booking.findViewById(R.id.tvNumberAdult);
        tvNumberChild = cardview_status_booking.findViewById(R.id.tvNumberChild);
        tvOrderNumber = cardview_status_booking.findViewById(R.id.tvOrderNumber);
        tvAmount = cardview_status_booking.findViewById(R.id.tvAmount);
        tvCopyAmount = cardview_status_booking.findViewById(R.id.tvCopyAmount);
        tvAdminFee = cardview_status_booking.findViewById(R.id.tvAdminFee);
        tvCreditCardNumber = cardview_status_booking.findViewById(R.id.tvCreditCardNumber);
        tvCopyNumber = cardview_status_booking.findViewById(R.id.tvCopyNumber);
        tvChangePayment = cardview_status_booking.findViewById(R.id.tvChangePayment);
        llPaidAt = cardview_status_booking.findViewById(R.id.llPaidAt);
        tvCountDown = cardview_status_booking.findViewById(R.id.tvCountDown);
        tvDateExp = cardview_status_booking.findViewById(R.id.tvDateExp);

        llToolbarServiceError = rlNoDataFound.findViewById(R.id.llToolbarServiceError);
        imgServiceFailed = rlNoDataFound.findViewById(R.id.imgServiceFailed);
        tvServiceFailed = rlNoDataFound.findViewById(R.id.tvServiceFailed);

        btnSheet = findViewById(R.id.btnSheet);
        component_btn_big = btnSheet.findViewById(R.id.component_btn_big);
        component_btn_big.setText("Pay Booking");
    }

    private void SetChipStatus() {
        tvChipsStatus.setText(getResources().getString(R.string.text_unpaid));
        tvChipsStatus.setBackground(getResources().getDrawable(R.drawable.custom_button_radius_gold));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onInitView(DataBookingReceiptResponse response) {

        String image = response.getMedias().get(0).getUrl();

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

        tvNamePackage.setText(response.getTitle());
        tvLocationHost.setText(response.getLocation());

        try {
            tvStartDate.setText(DateHelper.getDateFormatMonthandYear(response.getSchedules().get(0).getStartDate()));
            tvEndDate.setText(DateHelper.getDateFormatMonthandYear(response.getSchedules().get(0).getEndDate()));
            tvDateExp.setText(DateHelper.getDateFormatWithZone(response.getEndDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        double totalPrice = Double.parseDouble(response.getTotalPrice());
        double adminFee = Double.parseDouble(String.valueOf(response.getAdminFee()));

        if (response.getSchedules().get(0).getDuration().equals("0")){
            tvDuration.setText("1");
        } else {
            tvDuration.setText(response.getSchedules().get(0).getDuration());
        }

        if (response.getContactInfo() != null && !response.getContactInfo().getFirst_name().isEmpty()){
            tvName.setText(firstCapitalize(response.getContactInfo().getFirst_name() + " " + response.getContactInfo().getLast_name()));
            tvEmail.setText(response.getContactInfo().getEmail());
            tvPhoneNumber.setText(response.getContactInfo().getPhone_number());
        }
        tvNumberParticipant.setText(String.valueOf(response.getParticipants().size()));
        tvNumberAdult.setText(response.getQtyAdults());
        tvNumberChild.setText(response.getQtyKids());
        tvOrderNumber.setText(response.getOrderNumber());
        tvAmount.setText(formatIdr(totalPrice));
        tvAdminFee.setText(formatIdr(adminFee));

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

        LocalDateTime remainingDate = LocalDateTime.parse(response.getEndDate(), format);
        long millis = System.currentTimeMillis();
        long timeInMilliseconds = remainingDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli();
        long millisInFuture = timeInMilliseconds - millis;
        new CountDownTimer(millisInFuture, 500) {
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                String time = hours % 24 + ":" + minutes % 60 + ":" + seconds % 60;
                tvCountDown.setText(time);
                hour = hours  % 24;
                min = minutes  % 60;
                second = seconds  % 60;
            }

            @Override
            public void onFinish() {
                String time = 0 + ":" + 0 + ":" + 0 ;
                tvCountDown.setText(time);
                hour = 0;
                min = 0;
                second = 0;
            }
        }.start();
    }

    // GET PENDING BOOKING
    private void onPendingBooking() {
        IViewBookingReceipt iViewPendingBookingOrder = this;
        BookingReceiptService presenter = new BookingReceiptService(this, iViewPendingBookingOrder);
        presenter.getBookingReceipt(orderNumber);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void getSuccessReceipt(BookingReceiptResponse response) {
        dataPending = response.getData();
        onInitView(dataPending);

        if (dataPending.getStatus().equals("paid")) {
            shimmerEffect.stopShimmer();

            Intent ii = new Intent(this, PaidBookingReceiptActivity.class);
            ii.putExtra("orderNumber", orderNumber);
            startActivity(ii);
            finish();
        } else {
            shimmerEffect.stopShimmer();

            llInformation.setVisibility(View.VISIBLE);
            rlContent.setVisibility(View.VISIBLE);
            rlNoDataFound.setVisibility(View.GONE);
            btnSheet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onErrorReceipt(String message, String status, int responseCode) {
        if (responseCode == 401) {
            IViewCheckToken iViewCheckToken = this;
            RefreshTokenServices refreshTokenServices = new RefreshTokenServices(this, iViewCheckToken);
            refreshTokenServices.onInstrospectToken();
        } else {
            shimmerEffect.stopShimmer();

            Picasso.get()
                    .load(R.drawable.ic_failed_service)
                    .fit()
                    .into(imgServiceFailed);
            tvServiceFailed.setText(message);

            llInformation.setVisibility(View.GONE);
            rlContent.setVisibility(View.INVISIBLE);
            rlNoDataFound.setVisibility(View.VISIBLE);
            btnSheet.setVisibility(View.INVISIBLE);
        }
    }

    // GET INVOICES
    @Override
    public void onGetInvoices(GetDataInvoicesItem data) {
        urlInvoices = data.getUrl();

        loadingPage.stop();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(urlInvoices));
        startActivity(i);
    }

    @Override
    public void onErrorInvoices(String title, String message) {
        loadingPage.stop();
        lokavenDialog.dialogError(title, message);
    }

    // CHECK TOKEN
    @Override
    public void isToken(boolean success) {
        onPendingBooking();
    }

    @Override
    public void errorToken(String title, String message) {
        shimmerEffect.stopShimmer();

        Picasso.get()
                .load(R.drawable.ic_failed_service)
                .fit()
                .into(imgServiceFailed);
        tvServiceFailed.setText(title);

        llInformation.setVisibility(View.GONE);
        rlContent.setVisibility(View.INVISIBLE);
        rlNoDataFound.setVisibility(View.VISIBLE);
        btnSheet.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v == rlTitleATM){

            if (rlAccordionATM.getVisibility() == View.GONE){
                rlAccordionATM.setVisibility(View.VISIBLE);
                ivArrowATM.setRotation(180);
            }else {
                rlAccordionATM.setVisibility(View.GONE);
                ivArrowATM.setRotation(0);
            }

        }else if (v == rlTitleIBangking){

            if (rlAccordionIBangking.getVisibility() == View.GONE){
                rlAccordionIBangking.setVisibility(View.VISIBLE);
                ivArrowBanking.setRotation(180);
            }else {
                rlAccordionIBangking.setVisibility(View.GONE);
                ivArrowBanking.setRotation(0);
            }

        }else if (v == rlTitleMobile){

            if (rlAccordionMobile.getVisibility() == View.GONE){
                rlAccordionMobile.setVisibility(View.VISIBLE);
                ivArrowMobile.setRotation(180);
            }else {
                ivArrowMobile.setRotation(0);
                rlAccordionMobile.setVisibility(View.GONE);
            }

        }else if (v == rlTitleOffice){

            if (rlAccordionOffice.getVisibility() == View.GONE){
                rlAccordionOffice.setVisibility(View.VISIBLE);
                ivArrowOffice.setRotation(180);
            }else {
                ivArrowOffice.setRotation(0);
                rlAccordionOffice.setVisibility(View.GONE);
            }

        }else if (v == rlClose){
            onBackPressed();
        } else if (v == tvCopyAmount){

            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("", dataPending.getTotalPrice());
            if (manager != null) {
                manager.setPrimaryClip(clipData);
                lokavenDialog.getToast(getString(R.string.toast_copy_amount));
            }

        } else if (v == tvCopyNumber){

            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("", tvCreditCardNumber.getText());
            manager.setPrimaryClip(clipData);

            CheckSnackbars snackbarsHelpers = new CheckSnackbars(this);
            snackbarsHelpers.onCheckWithOutIntent("clipboard");

        } else if (v == imgServiceFailed) {

            llInformation.setVisibility(View.GONE);
            rlContent.setVisibility(View.INVISIBLE);
            rlNoDataFound.setVisibility(View.GONE);

            shimmerEffect.startShimmer();
            onPendingBooking();

        } else if (v == component_btn_big) {

            if (hour <= 0 && min <= 00 && second <= 0){
                Toast.makeText(this, getResources().getString(R.string.text_payment_has_expired), Toast.LENGTH_SHORT).show();
            } else {
                loadingPage.start();
                iViewGetInvoices iViewGetInvoices = this;
                GetInvoicesServices getInvoicesServices = new GetInvoicesServices(this, iViewGetInvoices);
                getInvoicesServices.onGetQuotaLeft("po", order_id);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        booking_uid = lokavenSession.getUid();

        if (getIntent() != null) {
            if (getIntent().getData() != null) {
                notif = true;
                orderNumber = getIntent().getData().getQueryParameter("order_number");
                tour_id = getIntent().getData().getQueryParameter("tour_id");
                order_id = getIntent().getData().getQueryParameter("order_id");
                from = 0;
            } else {
                notif = getIntent().getBooleanExtra("notif", false);
                orderNumber = getIntent().getStringExtra("orderNumber");
                tour_id = getIntent().getStringExtra("tour_id");
                order_id = getIntent().getStringExtra("order_id");
                from = getIntent().getIntExtra("from", 0);
            }
        }

        llInformation.setVisibility(View.GONE);
        rlContent.setVisibility(View.INVISIBLE);
        rlNoDataFound.setVisibility(View.GONE);
        btnSheet.setVisibility(View.INVISIBLE);

        shimmerEffect.startShimmer();
        onPendingBooking();
    }

    public void onBackPressed() {
        if (notif) {
            Intent intent = new Intent(this, NotificationsActivity.class);
            intent.putExtra("notif", true);
            startActivity(intent);
            finish();
        } else {
            if (from == 4){
                Intent i = new Intent();
                i.putExtra("pageSlider", 4);
                setResult(RESULT_BACK, i);
                finish();
            } else {
                Intent i = new Intent(this, HomeActivity.class);
                i.putExtra("pageSlider", 4);
                startActivity(i);
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        booking_uid = lokavenSession.getUid();

        if (getIntent() != null) {
            if (getIntent().getData() != null) {
                notif = true;
                orderNumber = getIntent().getData().getQueryParameter("order_number");
                tour_id = getIntent().getData().getQueryParameter("tour_id");
                order_id = getIntent().getData().getQueryParameter("order_id");
                from = 0;
            } else {
                notif = getIntent().getBooleanExtra("notif", false);
                orderNumber = getIntent().getStringExtra("orderNumber");
                tour_id = getIntent().getStringExtra("tour_id");
                order_id = getIntent().getStringExtra("order_id");
                from = getIntent().getIntExtra("from", 0);
            }
        }

        llInformation.setVisibility(View.GONE);
        rlContent.setVisibility(View.INVISIBLE);
        rlNoDataFound.setVisibility(View.GONE);
        btnSheet.setVisibility(View.INVISIBLE);

        shimmerEffect.startShimmer();
        onPendingBooking();
    }

    @Override
    protected void onPause() {
        super.onPause();
        booking_uid = lokavenSession.getUid();

        if (getIntent() != null) {
            if (getIntent().getData() != null) {
                notif = true;
                orderNumber = getIntent().getData().getQueryParameter("order_number");
                tour_id = getIntent().getData().getQueryParameter("tour_id");
                order_id = getIntent().getData().getQueryParameter("order_id");
                from = 0;
            } else {
                notif = getIntent().getBooleanExtra("notif", false);
                orderNumber = getIntent().getStringExtra("orderNumber");
                tour_id = getIntent().getStringExtra("tour_id");
                order_id = getIntent().getStringExtra("order_id");
                from = getIntent().getIntExtra("from", 0);
            }
        }

        llInformation.setVisibility(View.GONE);
        rlContent.setVisibility(View.INVISIBLE);
        rlNoDataFound.setVisibility(View.GONE);
        btnSheet.setVisibility(View.INVISIBLE);

        shimmerEffect.startShimmer();
        onPendingBooking();
    }
}
