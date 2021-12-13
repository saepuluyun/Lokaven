package com.aniqma.lokaventour.modul.activity.booking.bookingreceipt;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aniqma.lokaventour.modul.activity.general.notification.NotificationsActivity;
import com.aniqma.lokaventour.network.services.bookingservice.bookingprocess.bookingreceipt.IViewBookingReceipt;
import com.aniqma.lokaventour.utils.animation.ShimmerEffect;
import com.aniqma.lokaventour.helper.date.DateHelper;
import com.aniqma.lokaventour.model.response.bookingpending.DataBookingReceiptResponse;
import com.aniqma.lokaventour.model.response.bookingpending.BookingReceiptResponse;
import com.aniqma.lokaventour.modul.activity.general.home.HomeActivity;
import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.network.services.authentication.refreshtokenandintrospect.IViewCheckToken;
import com.aniqma.lokaventour.network.services.authentication.refreshtokenandintrospect.RefreshTokenServices;
import com.aniqma.lokaventour.network.services.bookingservice.bookingprocess.bookingreceipt.BookingReceiptService;
import com.aniqma.lokaventour.utils.popup.LokavenDialog;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static com.aniqma.lokaventour.helper.format.AlphabetHelper.firstCapitalize;
import static com.aniqma.lokaventour.helper.format.NumberHelper.formatIdr;
import static com.aniqma.lokaventour.helper.url.UrlHelper.IsValidUrl;
import static com.aniqma.lokaventour.modul.activity.tourandbookings.activetour.ActiveTourActivity.RESULT_ACTIVE_TOUR;

public class PaidBookingReceiptActivity extends AppCompatActivity
        implements View.OnClickListener, IViewBookingReceipt, IViewCheckToken {

    private ImageView
            imgThumbnail,
            ivEditInfo,
            ivEditParticipants,
            imgServiceFailed,
            ivQrCode;

    private TextView
            tvNamePackage,
            tvLocationHost,
            tvDuration,
            tvEndDate,
            tvStartDate,
            tvNameUser,
            tvEmailUser,
            tvPhoneNumber,
            tvNumberOrder,
            tvTotalParticipant,
            tvTotalAdult,
            tvTotalChild,
            tvChipsStatus,
            tvPrice,
            tvBookedAt,
            tvGoToYourPage,
            tvServiceFailed,
            tvPay,
            tvTitleQrCode,
            tvDescQrCode,
            tvQrCode,
            tvCopyQrCode;

    private RelativeLayout
            rlClose,
            rlContent,
            rlNoDataFound,
            rlQrCodeSignIn;

    private CollapsingToolbarLayout collapseActionView;
    private LinearLayout llContent, llInfoPayment, llInfoBot, llToolbarServiceError, llQrCode;
    private View compCard, componentAppbar, compInfo;
    private ShimmerFrameLayout shimmerContainer;

    private DataBookingReceiptResponse response;

    private String orderNumber="", statusBooking="", thumbnail="", titleTour="",
            locationTour="", duration="", startDate="", endDate="", nameContact="", emailContact="", phoneContact="", totalAdult="0", totalChildren="0", bookedAt="", amount="";
    private int  totalParticipant= 0, from = 0;
    private boolean notif = false;

    private LokavenDialog lokavenDialog;
    private ShimmerEffect shimmerEffect;

    @RequiresApi(api = LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_booking_receipt);
        init();

        lokavenDialog = new LokavenDialog(this);
        shimmerEffect = new ShimmerEffect(this, shimmerContainer, llContent);

        llToolbarServiceError.setVisibility(View.GONE);

        llInfoPayment.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.LOK_Muted_Teal)));
        String text_goToTourPackage = "Go to your <b>active tour page</b>, wait and communicate with the host";
        tvGoToYourPage.setText(Html.fromHtml(text_goToTourPackage));

        rlClose.setOnClickListener(this);
        compInfo.setOnClickListener(this);
        imgServiceFailed.setOnClickListener(this);
        tvCopyQrCode.setOnClickListener(this);
    }

    private void init() {
        shimmerContainer = findViewById(R.id.shimmerContainer);
        llContent = findViewById(R.id.llContent);
        compCard = findViewById(R.id.compCard);

        llInfoPayment = compCard.findViewById(R.id.llInfoPayment);
        imgThumbnail = compCard.findViewById(R.id.imgThumbnail);
        tvNamePackage = compCard.findViewById(R.id.tvTitleTour);
        tvLocationHost = compCard.findViewById(R.id.tvLocationTour);
        tvDuration = compCard.findViewById(R.id.tvDuration);
        tvStartDate = compCard.findViewById(R.id.tvStartDate);
        tvEndDate = compCard.findViewById(R.id.tvEndDate);

        tvNameUser = compCard.findViewById(R.id.tvNameUser);
        tvEmailUser = compCard.findViewById(R.id.tvEmailUser);
        tvPhoneNumber = compCard.findViewById(R.id.tvPhoneNumber);

        tvTotalParticipant = compCard.findViewById(R.id.tvTotalParticipant);
        tvTotalAdult = compCard.findViewById(R.id.tvTotalAdult);
        tvTotalChild = compCard.findViewById(R.id.tvTotalChild);
        tvChipsStatus = compCard.findViewById(R.id.tvChips);

        tvNumberOrder = compCard.findViewById(R.id.tvNumberOrder);
        tvPay = compCard.findViewById(R.id.tvPay);
        tvPrice = compCard.findViewById(R.id.tvPrice);
        tvBookedAt = compCard.findViewById(R.id.tvBookedAt);

        rlContent = compCard.findViewById(R.id.rlContent);
        rlNoDataFound = compCard.findViewById(R.id.rlNoDataFound);

        componentAppbar = findViewById(R.id.componentAppbar);
        rlClose = componentAppbar.findViewById(R.id.rlClose);

        collapseActionView = findViewById(R.id.collapseActionView);
        ivEditInfo = compCard.findViewById(R.id.ivEditInfo);
        ivEditParticipants = compCard.findViewById(R.id.ivEditParticipants);

        compInfo = findViewById(R.id.compInfo);
        tvGoToYourPage = compInfo.findViewById(R.id.tvBtnInfo);

        llToolbarServiceError = rlNoDataFound.findViewById(R.id.llToolbarServiceError);
        imgServiceFailed = rlNoDataFound.findViewById(R.id.imgServiceFailed);
        tvServiceFailed = rlNoDataFound.findViewById(R.id.tvServiceFailed);

        llInfoBot = findViewById(R.id.llInfoBot);

        llQrCode = findViewById(R.id.llQrCode);
        ivQrCode = findViewById(R.id.ivQrCode);
        rlQrCodeSignIn = findViewById(R.id.rlQrCodeSignIn);
        tvTitleQrCode = findViewById(R.id.tvTitleQrCode);
        tvDescQrCode = findViewById(R.id.tvDescQrCode);
        tvQrCode = findViewById(R.id.tvQrCode);
        tvCopyQrCode = findViewById(R.id.tvCopyQrCode);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onInitView(BookingReceiptResponse bookingReceiptResponse) {
        String[] durationStr = new String[0];
        try {
            response = bookingReceiptResponse.getData();

            if (response.getMedias() != null) {
                if (response.getMedias().size() > 0) {
                    thumbnail = response.getMedias().get(0).getUrl();
                }
            }

            titleTour = response.getTitle();
            locationTour = response.getLocation();

            if (response.getSchedules().get(0).getDuration().equals("0")){
                duration = "1";
            } else {
                duration = response.getSchedules().get(0).getDuration();
            }
           
            durationStr = duration.split(" ");
            startDate = DateHelper.getDateFormatMonthandYear(response.getSchedules().get(0).getStartDate());
            endDate = DateHelper.getDateFormatMonthandYear(response.getSchedules().get(0).getEndDate());
            nameContact = firstCapitalize(response.getContactInfo().getFirst_name()+" "+response.getContactInfo().getLast_name());
            emailContact = response.getContactInfo().getEmail();
            phoneContact = response.getContactInfo().getPhone_number();
            totalParticipant = Integer.valueOf(response.getQtyAdults()) + Integer.valueOf(response.getQtyKids());
            totalAdult = response.getQtyAdults();
            totalChildren = response.getQtyKids();
            statusBooking = firstCapitalize(response.getStatus());
            bookedAt = DateHelper.getDateFormatMonthandYear(response.getCreatedAt());
            amount = formatIdr(Double.parseDouble(response.getTotalPrice()));
            tvPay.setText("Virtual Account");

            ivEditParticipants.setVisibility(View.GONE);
            ivEditInfo.setVisibility(View.GONE);

            if (IsValidUrl(thumbnail)) {
                if (thumbnail.contains("http://")) {
                    thumbnail = thumbnail.replace("http://", "https://");
                }

                Picasso.get()
                        .load(thumbnail)
                        .fit()
                        .placeholder(R.drawable.thumbnail_default)
                        .into(imgThumbnail);
            }

            tvNamePackage.setText(titleTour);
            tvLocationHost.setText(locationTour);
            tvDuration.setText(durationStr[0]);
            tvStartDate.setText(startDate);
            tvEndDate.setText(endDate);

            tvNameUser.setText(nameContact);
            tvEmailUser.setText(emailContact);
            tvPhoneNumber.setText(phoneContact);

            tvTotalParticipant.setText(String.valueOf(totalParticipant));
            tvTotalAdult.setText(totalAdult);
            tvTotalChild.setText(totalChildren);

            tvChipsStatus.setText(getString(R.string.text_paid));

            llInfoPayment.setBackgroundResource(R.color.LOK_Muted_Teal);
            tvNumberOrder.setText(orderNumber);
            tvBookedAt.setText(bookedAt);
            tvPrice.setText(amount);

            if (response.getSchedules().get(0).getTourStatus().equals("awaiting")){

                tvTitleQrCode.setText(getString(R.string.text_use_the_qr_code_below_to_sign_in_with_your_host_when_the_activity_is_started));
                tvDescQrCode.setVisibility(View.VISIBLE);
                tvQrCode.setTextColor(getResources().getColor(R.color.LOK_Dark_Blue));
                tvQrCode.setEnabled(true);
                tvCopyQrCode.setVisibility(View.VISIBLE);
                rlQrCodeSignIn.setVisibility(View.GONE);

            } else if (response.getSchedules().get(0).getTourStatus().equals("ongoing")) {

                tvTitleQrCode.setText(getString(R.string.text_host_already_signed_you_into_this_activity));
                tvDescQrCode.setVisibility(View.GONE);
                tvQrCode.setTextColor(getResources().getColor(R.color.LOK_Grey));
                ivQrCode.setAlpha((float) 0.2);
                tvQrCode.setEnabled(false);
                tvCopyQrCode.setVisibility(View.GONE);
                rlQrCodeSignIn.setVisibility(View.VISIBLE);

            }

            String imgQrCode = response.getQrCode();
            String codeUnique = response.getCodeUnique();

            llQrCode.setVisibility(View.GONE);
            if (!codeUnique.equals("")) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(codeUnique, BarcodeFormat.QR_CODE, 250 ,250);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    ivQrCode.setImageBitmap(bitmap);
                    tvQrCode.setText(codeUnique);

                    llQrCode.setVisibility(View.VISIBLE);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void onBookingReciept() {
        IViewBookingReceipt iViewBookingReceipt = this;
        BookingReceiptService presenter = new BookingReceiptService(this, iViewBookingReceipt);
        presenter.getBookingReceipt(orderNumber);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void getSuccessReceipt(BookingReceiptResponse response) {
        onInitView(response);

        shimmerEffect.stopShimmer();
        llInfoBot.setVisibility(View.VISIBLE);
        rlContent.setVisibility(View.VISIBLE);
        rlNoDataFound.setVisibility(View.GONE);
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

            llInfoBot.setVisibility(View.GONE);
            rlContent.setVisibility(View.INVISIBLE);
            rlNoDataFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void isToken(boolean success) {
        onBookingReciept();
    }

    @Override
    public void errorToken(String title, String message) {
        shimmerEffect.stopShimmer();

        Picasso.get()
                .load(R.drawable.ic_failed_service)
                .fit()
                .into(imgServiceFailed);
        tvServiceFailed.setText(title);

        llInfoBot.setVisibility(View.GONE);
        rlContent.setVisibility(View.INVISIBLE);
        rlNoDataFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v == rlClose){
            onBackPressed();
        } else if (v == compInfo){
            onBackPressed();
        } else if (v == imgServiceFailed) {
            llInfoBot.setVisibility(View.GONE);
            rlContent.setVisibility(View.INVISIBLE);
            rlNoDataFound.setVisibility(View.GONE);

            shimmerEffect.startShimmer();
            onBookingReciept();
        } else if (v == tvCopyQrCode) {
            ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("", response.getCodeUnique());
            if (manager != null) {
                manager.setPrimaryClip(clipData);
                lokavenDialog.getToast(getString(R.string.toast_copy_qr_code));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent() != null) {
            if (getIntent().getData() != null) {
                notif = true;
                orderNumber = getIntent().getData().getQueryParameter("order_number");
                from = 0;
            } else {
                notif = getIntent().getBooleanExtra("notif", false);
                orderNumber = getIntent().getStringExtra("orderNumber");
                from = getIntent().getIntExtra("from", 0);
            }

            llInfoBot.setVisibility(View.GONE);
            rlContent.setVisibility(View.INVISIBLE);
            rlNoDataFound.setVisibility(View.GONE);

            shimmerEffect.startShimmer();
            onBookingReciept();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            if (notif) {
                Intent intent = new Intent(this, NotificationsActivity.class);
                intent.putExtra("notif", true);
                startActivity(intent);
                finish();
            } else {
                if (from == 4){
                    Intent i = new Intent();
                    i.putExtra("pageSlider", 4);
                    setResult(RESULT_ACTIVE_TOUR, i);
                    finish();
                } else {
                    Intent i = new Intent(this, HomeActivity.class);
                    i.putExtra("pageSlider", 4);
                    startActivity(i);
                    finish();
                }
            }
        } catch (Exception e) {
            Intent i = new Intent(this, HomeActivity.class);
            i.putExtra("pageSlider", 4);
            startActivity(i);
            finish();
        }
    }
}
