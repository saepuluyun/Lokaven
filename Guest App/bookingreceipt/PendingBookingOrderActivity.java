package com.aniqma.lokaventour.modul.activity.booking.bookingreceipt;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.network.services.bookingservice.bookingprocess.bookingreceipt.IViewBookingReceipt;
import com.aniqma.lokaventour.utils.animation.ShimmerEffect;
import com.aniqma.lokaventour.utils.popup.LokavenDialog;
import com.aniqma.lokaventour.helper.date.DateHelper;
import com.aniqma.lokaventour.utils.animation.LoadingPage;
import com.aniqma.lokaventour.model.item.bookingService.quotaLeft.QuotaLeftDataItem;
import com.aniqma.lokaventour.model.response.bookingcancellation.CancellationByGuestResponse;
import com.aniqma.lokaventour.model.response.bookingpending.DataBookingReceiptResponse;
import com.aniqma.lokaventour.model.response.bookingpending.BookingReceiptResponse;
import com.aniqma.lokaventour.modul.activity.booking.bookingreceipt.CancelledBookingOrderActivity;
import com.aniqma.lokaventour.modul.activity.general.home.HomeActivity;
import com.aniqma.lokaventour.network.services.authentication.refreshtokenandintrospect.RefreshTokenServices;
import com.aniqma.lokaventour.network.servicesutils.LokavenSession;
import com.aniqma.lokaventour.network.services.authentication.refreshtokenandintrospect.IViewCheckToken;
import com.aniqma.lokaventour.network.services.bookingservice.bookingprocess.bookingquotaleft.BookingQuotaLeftService;
import com.aniqma.lokaventour.network.services.bookingservice.bookingprocess.bookingquotaleft.iViewQuotaLeft;
import com.aniqma.lokaventour.network.services.bookingservice.bookingprocess.bookingreceipt.BookingReceiptService;
import com.aniqma.lokaventour.network.services.cancellationservice.BookingCancellationByGuestService;
import com.aniqma.lokaventour.network.services.cancellationservice.IViewBookingCancellationByGuest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

import static com.aniqma.lokaventour.helper.format.AlphabetHelper.firstCapitalize;
import static com.aniqma.lokaventour.helper.format.NumberHelper.formatIdr;
import static com.aniqma.lokaventour.helper.url.UrlHelper.IsValidUrl;
import static com.aniqma.lokaventour.modul.activity.general.home.HomeActivity.RESULT_BACK;

public class PendingBookingOrderActivity extends AppCompatActivity implements
        View.OnClickListener, IViewBookingReceipt, IViewCheckToken,
        IViewBookingCancellationByGuest, iViewQuotaLeft {

    private TextView tvDescTitile,
            tvChipsStatus,
            tvLink,
            tvTitle,
            tvDescription,
            tvCancleBook,
            tvPay,
            tvContent,
            tvOrderNumber,
            tvPrice,
            tvNamePackage,
            tvLocationHost,
            tvDuration,
            tvStartDate,
            tvEndDate,
            tvNameUser,
            tvEmailUser,
            tvPhoneNumber,
            tvTotalParticipant,
            tvTotalAdult,
            tvTotalChild,
            tvNumberOrder,
            tvBookedAtTitle,
            tvBookedAt,
            tvServiceFailed;

    private CollapsingToolbarLayout collapseActionView;
    private ImageView
            ivEditInfo,
            ivEditParticipants,
            imgThumbnail,
            imgServiceFailed;

    private View
            componentExplaination,
            componentAppbar;

    private RelativeLayout rlClose, rlContent, rlNoDataFound;
    private LinearLayout llContent, llInfoPayment, llToolbarServiceError, linInfo;
    private View compCard, componentOrderNumber;
    private ShimmerFrameLayout shimmerContainer;

    private String tour_id = "", schedule_id="", order_id = "", orderNumber="", statusBooking="", quotLeft="0", thumbnail="", titleTour="",
            locationTour="", duration="", startDate="", endDate="", nameContact="", emailContact="", phoneContact="", totalAdult="0", totalChildren="0", bookedAt="", amount="", booking_uid="";
    private int  totalParticipant= 0, from = 0;

    private DataBookingReceiptResponse response;
    private LokavenDialog lokavenDialog;
    private ShimmerEffect shimmerEffect;
    private LoadingPage loadingPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_booking_order);
        init();

        lokavenDialog = new LokavenDialog(this);
        shimmerEffect = new ShimmerEffect(this, shimmerContainer, llContent);
        loadingPage = new LoadingPage(this);

        llToolbarServiceError.setVisibility(View.GONE);

        rlClose.setOnClickListener(this);
        tvCancleBook.setOnClickListener(this);
        imgServiceFailed.setOnClickListener(this);
    }

    private void init() {
        shimmerContainer = findViewById(R.id.shimmerContainer);
        llContent = findViewById(R.id.llContent);

        componentExplaination = findViewById(R.id.componentExplaination);
        tvTitle = componentExplaination.findViewById(R.id.tvTitle);
        tvLink = componentExplaination.findViewById(R.id.tvLink);
        tvDescription = componentExplaination.findViewById(R.id.tvDescription);

        compCard = findViewById(R.id.compCard);
        llInfoPayment = compCard.findViewById(R.id.llInfoPayment);
        tvBookedAtTitle = compCard.findViewById(R.id.tvBookedAtTitle);
        tvBookedAt = compCard.findViewById(R.id.tvBookedAt);
        tvPay = compCard.findViewById(R.id.tvPay);

        componentOrderNumber = findViewById(R.id.componentOrderNumber);
        linInfo = findViewById(R.id.linInfo);
        tvContent = componentOrderNumber.findViewById(R.id.tvContent);
        tvOrderNumber = componentOrderNumber.findViewById(R.id.tvOrderNumber);

        componentAppbar = findViewById(R.id.componentAppbar);
        rlClose = componentAppbar.findViewById(R.id.rlClose);

        tvDescTitile = findViewById(R.id.tvDescTitile);
        collapseActionView = findViewById(R.id.collapseActionView);
        ivEditInfo = findViewById(R.id.ivEditInfo);
        ivEditParticipants = findViewById(R.id.ivEditParticipants);
        tvCancleBook = findViewById(R.id.tvCancleBook);

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
        tvBookedAt = compCard.findViewById(R.id.tvBookedAt);
        tvPrice = compCard.findViewById(R.id.tvPrice);

        rlContent = compCard.findViewById(R.id.rlContent);
        rlNoDataFound = compCard.findViewById(R.id.rlNoDataFound);
        llToolbarServiceError = rlNoDataFound.findViewById(R.id.llToolbarServiceError);
        imgServiceFailed = rlNoDataFound.findViewById(R.id.imgServiceFailed);
        tvServiceFailed = rlNoDataFound.findViewById(R.id.tvServiceFailed);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onInitView(BookingReceiptResponse bookingReceiptResponse) {
        String[] durationStr = new String[0];
        try {
            response = bookingReceiptResponse.getData();
            schedule_id = response.getSchedules().get(0).getScheduleId();
            thumbnail = response.getMedias().get(0).getUrl();
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

            ivEditParticipants.setVisibility(View.GONE);
            ivEditInfo.setVisibility(View.GONE);
            tvDescTitile.setText(Html.fromHtml(getResources().getString(R.string.text_waiting_for_more_bookings_desc)));
            collapseActionView.setContentScrimColor(this.getResources().getColor(R.color.LOK_White));

            tvLink.setVisibility(View.GONE);
            tvTitle.setText(getResources().getString(R.string.text_what_does_it_mean));
            tvDescription.setText(getResources().getString(R.string.text_you_are_currently_waiting_for_more_booking));

            String image = response.getMedias().get(0).getUrl();

            if (IsValidUrl(image)) {
                if (image.contains("http://")) {
                    image = image.replace("http://", "https://");
                }

                Picasso.get()
                        .load(image)
                        .fit()
                        .placeholder(R.drawable.thumbnail_default)
                        .into(imgThumbnail);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        iViewQuotaLeft iViewQuotaLeft = this;
        BookingQuotaLeftService bookingQuotaLeftService = new BookingQuotaLeftService(this, iViewQuotaLeft);
        bookingQuotaLeftService.onGetQuotaLeft(tour_id, schedule_id);

        tvContent.setText(getResources().getString(R.string.text_you_will_have_wait));
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

        tvChipsStatus.setText(getString(R.string.text_awaiting));
        tvChipsStatus.setBackground(getResources().getDrawable(R.drawable.custom_button_radius_lok_info));

        llInfoPayment.setBackgroundResource(R.color.LOK_Light_Grey);
        tvNumberOrder.setText(orderNumber);
        tvBookedAtTitle.setText(getResources().getString(R.string.text_booked_at));
        tvBookedAt.setText(bookedAt);
        tvPay.setVisibility(View.GONE);
        tvPrice.setText(amount);
    }

    // GET PENDING BOOKING
    private void onPendingBooking() {
        IViewBookingReceipt iViewBookingReceipt = this;
        BookingReceiptService presenter = new BookingReceiptService(this, iViewBookingReceipt);
        presenter.getBookingReceipt(orderNumber);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void getSuccessReceipt(BookingReceiptResponse response) {
        onInitView(response);
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

            linInfo.setVisibility(View.INVISIBLE);
            tvCancleBook.setVisibility(View.INVISIBLE);
            rlContent.setVisibility(View.INVISIBLE);
            rlNoDataFound.setVisibility(View.VISIBLE);
        }
    }

    // GET QUOTA LEFT
    @Override
    public void onSuccessQuotaLeft(QuotaLeftDataItem response) {
        int minQuota = Integer.valueOf(response.getMin_quota());
        int totalQuota = Integer.valueOf(response.getTotal_quota());
        quotLeft = String.valueOf(minQuota - totalQuota);
        tvOrderNumber.setText(quotLeft+" "+getResources().getString(R.string.text_more_booking));

        shimmerEffect.stopShimmer();
        linInfo.setVisibility(View.VISIBLE);
        tvCancleBook.setVisibility(View.VISIBLE);
        rlContent.setVisibility(View.VISIBLE);
        rlNoDataFound.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onErrorQuotaLeft(String title, String message) {
        shimmerEffect.stopShimmer();
        Picasso.get()
                .load(R.drawable.ic_failed_service)
                .fit()
                .into(imgServiceFailed);
        tvServiceFailed.setText(message);

        linInfo.setVisibility(View.INVISIBLE);
        tvCancleBook.setVisibility(View.INVISIBLE);
        rlContent.setVisibility(View.INVISIBLE);
        rlNoDataFound.setVisibility(View.VISIBLE);
    }

    // CANCEL BOOKING
    @Override
    public void getSuccessCancelled(CancellationByGuestResponse cancellationByGuestResponse) {
        loadingPage.stop();
        Intent intent = new Intent(this, CancelledBookingOrderActivity.class);
        intent.putExtra("tour_id", response.getTourId());
        intent.putExtra("orderNumber", response.getOrderNumber());
        intent.putExtra("order_id", response.getOrderId());
        startActivity(intent);
        finish();
    }

    @Override
    public void onErrorCancel(String message, String status) {
        loadingPage.stop();
        lokavenDialog.dialogError(message, status);
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

        linInfo.setVisibility(View.INVISIBLE);
        tvCancleBook.setVisibility(View.INVISIBLE);
        rlContent.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v == rlClose){
            onBackPressed();
        }else if (v == tvCancleBook){
            loadingPage.start();
            IViewBookingCancellationByGuest iViewBookingCancellationByGuest = this;
            BookingCancellationByGuestService bookingCancellationByGuestService = new BookingCancellationByGuestService(this, iViewBookingCancellationByGuest);
            bookingCancellationByGuestService.cancelledBookingByGuest(response.getOrderId());
        } else if (v == imgServiceFailed) {
            linInfo.setVisibility(View.INVISIBLE);
            tvCancleBook.setVisibility(View.INVISIBLE);
            rlContent.setVisibility(View.INVISIBLE);
            rlNoDataFound.setVisibility(View.GONE);

            shimmerEffect.startShimmer();
            onPendingBooking();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent() != null) {
            LokavenSession lokavenSession = new LokavenSession(this);
            booking_uid = lokavenSession.getUid();
            orderNumber = getIntent().getStringExtra("orderNumber");
            tour_id = getIntent().getStringExtra("tour_id");
            from = getIntent().getIntExtra("from", 0);

            linInfo.setVisibility(View.INVISIBLE);
            tvCancleBook.setVisibility(View.INVISIBLE);
            rlContent.setVisibility(View.INVISIBLE);
            rlNoDataFound.setVisibility(View.GONE);

            shimmerEffect.startShimmer();
            onPendingBooking();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (from == 4){
            Intent i = new Intent(this, HomeActivity.class);
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

