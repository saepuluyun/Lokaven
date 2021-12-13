package com.aniqma.lokaventour.modul.activity.booking.bookingreceipt;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.modul.activity.general.notification.NotificationsActivity;
import com.aniqma.lokaventour.network.services.bookingservice.bookingprocess.bookingreceipt.IViewBookingReceipt;
import com.aniqma.lokaventour.utils.animation.ShimmerEffect;
import com.aniqma.lokaventour.utils.popup.LokavenDialog;
import com.aniqma.lokaventour.helper.date.DateHelper;
import com.aniqma.lokaventour.model.response.bookingpending.DataBookingReceiptResponse;
import com.aniqma.lokaventour.model.item.tourandbookings.TourAndBookingsListItem;
import com.aniqma.lokaventour.model.response.bookingpending.BookingReceiptResponse;
import com.aniqma.lokaventour.modul.activity.tourandbookings.pasttoursandbooking.PastToursAndBookingsActivity;
import com.aniqma.lokaventour.network.services.authentication.refreshtokenandintrospect.RefreshTokenServices;
import com.aniqma.lokaventour.network.servicesutils.LokavenSession;
import com.aniqma.lokaventour.network.services.authentication.refreshtokenandintrospect.IViewCheckToken;
import com.aniqma.lokaventour.network.services.bookingservice.bookingprocess.bookingreceipt.BookingReceiptService;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

import static com.aniqma.lokaventour.helper.format.NumberHelper.formatIdr;
import static com.aniqma.lokaventour.helper.url.UrlHelper.IsValidUrl;

public class CancelledBookingOrderActivity extends AppCompatActivity implements View.OnClickListener, IViewCheckToken, IViewBookingReceipt {

    private CollapsingToolbarLayout collapseActionView;
    private RelativeLayout rlClose, rlContent, rlNoDataFound;;

    private TextView tvChipsStatus,
            tvDescTitle,
            tvNamePackage,
            tvLocationHost,
            tvDuration,
            tvStartDate,
            tvEndDate,
            tvName,
            tvEmail,
            tvPhoneNumber,
            tvNumberParticipants,
            tvNumberAdult,
            tvNumberChild,
            tvOrderId,
            tvPaidAt,
            tvAmount,
            tvServiceFailed;

    private ImageView imgThumbnail, ivEditParticipants, ivEditInfo, imgServiceFailed;
    private DataBookingReceiptResponse cancelled;
    private View cardview_status_booking,
            component_status_booking;

    private ShimmerFrameLayout shimmerContainer;

    private LinearLayout llContent, llInfoPaidDate, llToolbarServiceError, llInfoBot;
    private TourAndBookingsListItem decline;
    private DataBookingReceiptResponse dataPending;
    private String booking_uid = "";
    private String order_id = "";
    private String orderNumber = "";
    private String tour_id = "";
    private boolean notif = false;

    private LokavenSession lokavenSession;
    private LokavenDialog lokavenDialog;
    private ShimmerEffect shimmerEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelled_booking_order);
        init();

        lokavenSession = new LokavenSession(this);
        lokavenDialog = new LokavenDialog(this);
        shimmerEffect = new ShimmerEffect(this, shimmerContainer, llContent);

        ivEditParticipants.setVisibility(View.GONE);
        ivEditInfo.setVisibility(View.GONE);
        llToolbarServiceError.setVisibility(View.GONE);

        tvDescTitle.setText(getString(R.string.text_your_booking_order_is_cancelled));
        setChipTitle();
        collapseActionView.setContentScrimColor(this.getResources().getColor(R.color.LOK_White));

        rlClose.setOnClickListener(this);
        imgServiceFailed.setOnClickListener(this);

    }

    private void init() {
        shimmerContainer = findViewById(R.id.shimmerContainer);
        llContent = findViewById(R.id.llContent);

        collapseActionView = findViewById(R.id.collapseActionView);
        rlClose = findViewById(R.id.rlClose);
        tvDescTitle = findViewById(R.id.tvDescTitle);
        ivEditInfo = findViewById(R.id.ivEditInfo);
        ivEditParticipants = findViewById(R.id.ivEditParticipants);
        cardview_status_booking = findViewById(R.id.cardview_status_booking);
        imgThumbnail = cardview_status_booking.findViewById(R.id.imgThumbnail);
        tvNamePackage = cardview_status_booking.findViewById(R.id.tvTitleTour);
        tvLocationHost = cardview_status_booking.findViewById(R.id.tvLocationTour);
        tvDuration = cardview_status_booking.findViewById(R.id.tvDuration);
        tvStartDate = cardview_status_booking.findViewById(R.id.tvStartDate);
        tvEndDate= cardview_status_booking.findViewById(R.id.tvEndDate);
        tvName = cardview_status_booking.findViewById(R.id.tvName);
        tvEmail = cardview_status_booking.findViewById(R.id.tvEmail);
        tvPhoneNumber = cardview_status_booking.findViewById(R.id.tvPhoneNumber);
        tvNumberParticipants = cardview_status_booking.findViewById(R.id.tvNumberParticipants);
        tvNumberAdult = cardview_status_booking.findViewById(R.id.tvNumberAdult);
        tvNumberChild = cardview_status_booking.findViewById(R.id.tvNumberChild);
        tvOrderId = cardview_status_booking.findViewById(R.id.tvOrderId);
        tvPaidAt = cardview_status_booking.findViewById(R.id.tvPaidAt);
        tvAmount = cardview_status_booking.findViewById(R.id.tvAmount);
        llInfoPaidDate = cardview_status_booking.findViewById(R.id.llInfoPaidDate);
        llInfoBot = findViewById(R.id.llInfoBot);

        component_status_booking = cardview_status_booking.findViewById(R.id.component_status_booking);
        tvChipsStatus = component_status_booking.findViewById(R.id.tvChips);

        rlContent = cardview_status_booking.findViewById(R.id.rlContent);
        rlNoDataFound = cardview_status_booking.findViewById(R.id.rlNoDataFound);
        llToolbarServiceError = rlNoDataFound.findViewById(R.id.llToolbarServiceError);
        imgServiceFailed = rlNoDataFound.findViewById(R.id.imgServiceFailed);
        tvServiceFailed = rlNoDataFound.findViewById(R.id.tvServiceFailed);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void onInitView(DataBookingReceiptResponse response) throws ParseException {

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

        tvNamePackage.setText(response.getTitle());
        tvLocationHost.setText(response.getLocation());

        if (response.getSchedules().get(0).getDuration().equals("0")){
            tvDuration.setText("1");
        } else {
            tvDuration.setText(response.getSchedules().get(0).getDuration());
        }

        tvStartDate.setText(DateHelper.getDateFormatMonthandYear(response.getSchedules().get(0).getStartDate()));
        tvEndDate.setText(DateHelper.getDateFormatMonthandYear(response.getSchedules().get(0).getEndDate()));

        if (response.getContactInfo() != null){
            tvName.setText(response.getContactInfo().getFirst_name() + " " + response.getContactInfo().getLast_name());
            tvEmail.setText(response.getContactInfo().getEmail());
            tvPhoneNumber.setText(response.getContactInfo().getPhone_number());
        }

        tvNumberParticipants.setText(String.valueOf(response.getParticipants().size()));
        tvNumberAdult.setText(response.getQtyAdults());
        tvNumberChild.setText(response.getQtyKids());
        tvOrderId.setText(response.getOrderNumber());

        if (response.getPaymentDate() != null){
            tvPaidAt.setText(DateHelper.getDateFormatMonthandYear(String.valueOf(response.getPaymentDate())));
        } else {
            llInfoPaidDate.setVisibility(View.GONE);
        }

        double totalPrice = Double.parseDouble(response.getTotalPrice());

        tvAmount.setText(formatIdr(totalPrice));

    }

    private void setChipTitle() {
        tvChipsStatus.setText(getString(R.string.text_cancelled));
        tvChipsStatus.setTextColor(getResources().getColor(R.color.LOK_White));
        tvChipsStatus.setPadding(8, 4, 8, 4);
        tvChipsStatus.setBackground(getResources().getDrawable(R.drawable.custom_button_radius_red));
    }

    private void onPendingBooking() {
        IViewBookingReceipt iViewPendingBookingOrder = this;
        BookingReceiptService presenter = new BookingReceiptService(this, iViewPendingBookingOrder);
        presenter.getBookingReceipt(orderNumber);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void getSuccessReceipt(BookingReceiptResponse response) {
        dataPending = response.getData();
        try {
            onInitView(dataPending);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        shimmerEffect.stopShimmer();
        rlNoDataFound.setVisibility(View.GONE);
        llInfoBot.setVisibility(View.VISIBLE);

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

            rlNoDataFound.setVisibility(View.VISIBLE);
            llInfoBot.setVisibility(View.GONE);
        }
    }

    @Override
    public void isToken(boolean success) {
        onPendingBooking();
    }

    @Override
    public void errorToken(String title, String message) {
        shimmerEffect.stopShimmer();
        rlNoDataFound.setVisibility(View.VISIBLE);
        llInfoBot.setVisibility(View.GONE);
        lokavenDialog.dialogError(title, message);
    }

    @Override
    public void onClick(View v) {
        if (v == rlClose){
            onBackPressed();
        } else if (v == imgServiceFailed) {
            rlNoDataFound.setVisibility(View.GONE);
            llInfoBot.setVisibility(View.GONE);

            shimmerEffect.startShimmer();
            onPendingBooking();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent() != null) {
            rlNoDataFound.setVisibility(View.GONE);
            llInfoBot.setVisibility(View.GONE);
            booking_uid = lokavenSession.getUid();

            if (getIntent().getData() != null) {
                notif = true;
                orderNumber = getIntent().getData().getQueryParameter("order_number");
                tour_id = getIntent().getData().getQueryParameter("tour_id");
                order_id = getIntent().getData().getQueryParameter("order_id");
            } else {
                notif = getIntent().getBooleanExtra("notif", false);
                orderNumber = getIntent().getStringExtra("orderNumber");
                tour_id = getIntent().getStringExtra("tour_id");
                order_id = getIntent().getStringExtra("order_id");
            }

            shimmerEffect.startShimmer();
            onPendingBooking();
        }
    }

    @Override
    public void onBackPressed() {
        if (notif) {
            Intent intent = new Intent(this, NotificationsActivity.class);
            intent.putExtra("notif", true);
            startActivity(intent);
            finish();
        } else {
            super.onBackPressed();
            Intent intent = new Intent(this, PastToursAndBookingsActivity.class);
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }
}
