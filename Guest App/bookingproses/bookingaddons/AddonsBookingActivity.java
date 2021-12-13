package com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingaddons;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.helper.date.DateHelper;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.AddonsTotalItem;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.AddonsItem;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.BookingProcessItem;
import com.aniqma.lokaventour.model.item.tourexplorer.TourPackageItem;
import com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingdetails.opentour.OpenDetailsBookingActivity;
import com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingdetails.privatetour.PrivateDetailsBookingActivity;
import com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingsummary.SummaryBookingActivity;
import com.aniqma.lokaventour.network.local.bookingproses.BookingLite;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.aniqma.lokaventour.helper.format.NumberHelper.formatIdr;
import static com.aniqma.lokaventour.helper.url.BitmapHelper.validationPicasso;

public class AddonsBookingActivity extends AppCompatActivity implements View.OnClickListener {

    private BookingProcessItem bookingProcessItem;
    private TourPackageItem tourPackageItem;
    private String typeTour;

    private View ComponentSectionTitleDefault,
            component_cards_package_ticket_initial,
            component_info;

    private TextView tvTitle,
            tvLink,
            tvDescription,
            tvDetail,
            tvTotalPrice,
            tvNamePackage,
            tvLocation,
            tvDuration,
            tvStartDate,
            tvEndDate,
            tvTitleBooking,
            tvDescriptionAddons,
            tvPriceSubTotal,
            tvPriceSubTotalBreakDown,
            tvPriceFor;

    private View breakfast,
            lunch,
            ComponentSubTotal,
            ComponentSubTotalBreakDown,
            ComponentButton,
            btnSheet,
            ComponentAppbarDefault;

    private LinearLayout llTotalAddons, llSubTotalAddons;

    private RecyclerView rvAddOns, rvSubTotalAddons;
    private RecyclerView.LayoutManager layoutManager;
    private ListAddonsAdapter listAddons;
    private ListTotalAddonsAdapter listTotalAddonsAdapter;
    private List<AddonsTotalItem> addonsTotalItems = new ArrayList<>();

    private Button btnNext;

    private ImageView
            thumbnailTour,
            ivBack,
            ivPlus;
    private int sub_total_addons, total_addons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_active_order_choose_addon);

        if (getIntent() != null) {
            bookingProcessItem = (BookingProcessItem) getIntent().getSerializableExtra("bookingProcessItem");
            tourPackageItem = (TourPackageItem) getIntent().getSerializableExtra("tourPackageItem");
        }

        typeTour = tourPackageItem.getTypeTour();

        init();

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
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            tvEndDate.setText(DateHelper.getDateFormatMonthandYear(bookingProcessItem.getSchedules().get(0).getEndDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        ivPlus.setVisibility(View.GONE);
        tvTitleBooking.setText(R.string.text_booking);
        tvDetail.setText(R.string.text_sub_total_including_tax);
        btnNext.setText(R.string.btn_continue);

        tvDescriptionAddons.setText(getString(R.string.text_the_price_for_each_addons_is_for_per_person));

        setListAddons();
        setListTotalAddons();
        setSubTotalPrice();

        // ADD HISTORY BOOKING
        BookingLite bookingLite = new BookingLite(this);
        bookingLite.addDB(bookingProcessItem);

        tvTitle.setText(R.string.text_add_ons);
        tvLink.setVisibility(View.GONE);
        tvDescription.setText(R.string.text_please_choose_how_many_participants_you_wish_to_include);

        llTotalAddons.setOnClickListener(this);
        llSubTotalAddons.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    private void init() {
        component_cards_package_ticket_initial = findViewById(R.id.component_cards_package_ticket_initial);

        thumbnailTour = component_cards_package_ticket_initial.findViewById(R.id.imgThumbnail);
        tvNamePackage = component_cards_package_ticket_initial.findViewById(R.id.tvTitleTour);
        tvLocation = component_cards_package_ticket_initial.findViewById(R.id.tvLocationTour);
        tvDuration = component_cards_package_ticket_initial.findViewById(R.id.tvDuration);
        tvStartDate = component_cards_package_ticket_initial.findViewById(R.id.tvStartDate);
        tvEndDate = component_cards_package_ticket_initial.findViewById(R.id.tvEndDate);

        ComponentSectionTitleDefault = findViewById(R.id.ComponentSectionTitleDefault);
        tvTitle = ComponentSectionTitleDefault.findViewById(R.id.tvTitle);
        tvLink = ComponentSectionTitleDefault.findViewById(R.id.tvLink);
        tvDescription = ComponentSectionTitleDefault.findViewById(R.id.tvDescription);
        rvAddOns = findViewById(R.id.rvAddOns);

        ComponentSubTotal = findViewById(R.id.ComponentSubTotal);
        llTotalAddons = ComponentSubTotal.findViewById(R.id.llTotalAddons);
        tvPriceSubTotal = ComponentSubTotal.findViewById(R.id.tvPriceSubTotal);
        tvPriceFor = ComponentSubTotal.findViewById(R.id.tvPriceFor);

        ComponentSubTotalBreakDown = findViewById(R.id.ComponentSubTotalBreakDown);
        llSubTotalAddons = ComponentSubTotalBreakDown.findViewById(R.id.llSubTotalAddons);
        tvPriceSubTotalBreakDown = ComponentSubTotalBreakDown.findViewById(R.id.tvPriceSubTotalBreakDown);
        rvSubTotalAddons = ComponentSubTotalBreakDown.findViewById(R.id.rvSubTotalAddons);

        ComponentButton = findViewById(R.id.ComponentButton);
        btnSheet = ComponentButton.findViewById(R.id.btnSheet);
        tvDetail = ComponentButton.findViewById(R.id.tvDetail);
        btnNext = btnSheet.findViewById(R.id.component_btn_big);
        tvTotalPrice = ComponentButton.findViewById(R.id.tvTotalPrice);

        ComponentAppbarDefault = findViewById(R.id.ComponentAppbarDefault);
        ivBack = ComponentAppbarDefault.findViewById(R.id.ivBack);
        ivPlus = ComponentAppbarDefault.findViewById(R.id.ivPlus);
        tvTitleBooking = ComponentAppbarDefault.findViewById(R.id.tvTitle);
        component_info = findViewById(R.id.component_info);
        tvDescriptionAddons = component_info.findViewById(R.id.tvDescription);
    }

    private void setListAddons() {
        listAddons = new ListAddonsAdapter(this, tourPackageItem, tvTotalPrice, bookingProcessItem);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvAddOns.setLayoutManager(layoutManager);
        rvAddOns.setAdapter(listAddons);
    }

    private void setListTotalAddons() {
        getAddonsList();

        listTotalAddonsAdapter = new ListTotalAddonsAdapter(this, addonsTotalItems);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvSubTotalAddons.setLayoutManager(layoutManager);
        rvSubTotalAddons.setAdapter(listTotalAddonsAdapter);
    }

    private void getAddonsList() {
        addonsTotalItems.clear();
        for (int i = 0; i < bookingProcessItem.getAddons().size(); i++){
            AddonsTotalItem addonsTotalItem = new AddonsTotalItem();
            // For count participant
            addonsTotalItem.setQtyAdult(Integer.valueOf(bookingProcessItem.getQty_adults()));
            addonsTotalItem.setQtyKids(Integer.valueOf(bookingProcessItem.getQty_kids()));
            addonsTotalItem.setAdditionalAdult(Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalAdultQty()));
            addonsTotalItem.setAdditionalKids(Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalKidQty()));

            // Addons
            addonsTotalItem.setPrice(Integer.valueOf(bookingProcessItem.getAddons().get(i).getPrice()));
            addonsTotalItem.setAddon(bookingProcessItem.getAddons().get(i).getAddon());
            addonsTotalItem.setPriceType(bookingProcessItem.getAddons().get(i).getPriceType());
            addonsTotalItem.setQty(bookingProcessItem.getAddons().get(i).getQty());
            addonsTotalItem.setQtyOrder(bookingProcessItem.getAddons().get(i).getTotalQty());
            addonsTotalItems.add(addonsTotalItem);
        }
    }

    public void setSubTotalPrice() {
        List<AddonsItem> listAddonsItem = new ArrayList<>();
        sub_total_addons = 0;
        total_addons = 0;
        for (int i = 0; i < listAddons.getItemCount(); i++){
            if (listAddons.getItemAddons().get(i).isCheked() || listAddons.getItemAddons().get(i).getTotalQty() > 0){
                AddonsItem addonsItem = new AddonsItem();

                int qtyAdult = Integer.valueOf(bookingProcessItem.getQty_adults());
                int qtyChild = Integer.valueOf(bookingProcessItem.getQty_kids());
                int qtyAdultAdditional = Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalAdultQty());
                int qtyChildAdditional = Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalKidQty());

                int addonsPrice = 0;
                if (listAddons.getItemAddons().get(i).getTotalQty() > 0) {
                    int priceItem = Integer.valueOf(listAddons.getItemAddons().get(i).getPrice());
                    int qtyItem = listAddons.getItemAddons().get(i).getQty();
                    int number = listAddons.getItemAddons().get(i).getTotalQty();
                    addonsPrice = (number/qtyItem) * priceItem;

                    sub_total_addons = sub_total_addons + addonsPrice;
                    total_addons = total_addons + addonsPrice;

                } else if (listAddons.getItemAddons().get(i).isCheked()){
                    addonsPrice = Integer.valueOf(listAddons.getItemAddons().get(i).getPrice());

                    sub_total_addons = sub_total_addons + addonsPrice;
                    total_addons = total_addons + (addonsPrice * (qtyAdult + qtyChild + qtyAdultAdditional +qtyChildAdditional));
                }

                addonsItem.setAddonId(listAddons.getItemAddons().get(i).getAddonId());
                addonsItem.setTourId(listAddons.getItemAddons().get(i).getTourId());
                addonsItem.setAddon(listAddons.getItemAddons().get(i).getAddon());
                addonsItem.setPrice(listAddons.getItemAddons().get(i).getPrice());
                addonsItem.setPriceType(listAddons.getItemAddons().get(i).getPriceType());
                addonsItem.setQty(listAddons.getItemAddons().get(i).getQty());
                addonsItem.setTotalQty(listAddons.getItemAddons().get(i).getTotalQty());
                addonsItem.setRental(listAddons.getItemAddons().get(i).isRental());
                addonsItem.setMediasItem(listAddons.getItemAddons().get(i).getMediasItem());
                listAddonsItem.add(addonsItem);
            }
        }

        int adultPrice = Integer.valueOf(bookingProcessItem.getTemp_adult_price());
        int adultPriceAdditonal = Integer.valueOf(bookingProcessItem.getTemp_adult_price_additional());
        int kidPriceAdditional = Integer.valueOf(bookingProcessItem.getTemp_kid_price_additional());
        int kidPrice =  Integer.valueOf(bookingProcessItem.getTemp_kid_price());
        int subPriceTotal = adultPrice + kidPrice + adultPriceAdditonal + kidPriceAdditional;
        int totalPrice = subPriceTotal+total_addons;

        bookingProcessItem.setAddons(listAddonsItem);
        bookingProcessItem.setTemp_price_addons(String.valueOf(sub_total_addons));
        bookingProcessItem.setSub_price_addons(String.valueOf(total_addons));
        bookingProcessItem.setTotal_price(String.valueOf(totalPrice));

        String priceFor = getString(R.string.text_price_for)+" "+
                bookingProcessItem.getQty_adults()+" "+getString(R.string.text_adult)+" "+
                getString(R.string.text_and)+" "+bookingProcessItem.getQty_kids()+" "+getString(R.string.text_children_small);

        tvPriceFor.setText(priceFor);
        tvPriceSubTotal.setText(formatIdr(Double.parseDouble(bookingProcessItem.getSub_price_addons())));
        tvPriceSubTotalBreakDown.setText(formatIdr(Double.parseDouble(bookingProcessItem.getSub_price_addons())));
        tvTotalPrice.setText(formatIdr(Double.parseDouble(String.valueOf(totalPrice))));

        reloadSubTotalAddons();

        // ADD HISTORY BOOKING
        BookingLite bookingLite = new BookingLite(this);
        bookingLite.addDB(bookingProcessItem);
    }

    public void reloadSubTotalAddons(){
        listTotalAddonsAdapter.getData().clear();

        getAddonsList();
        listTotalAddonsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:

                if (getIntent() != null) {
                    bookingProcessItem = (BookingProcessItem) data.getSerializableExtra("bookingProcessItem");
                    tourPackageItem = (TourPackageItem) data.getSerializableExtra("tourPackageItem");
                }

                break;

            case RESULT_CANCELED:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (view == llTotalAddons) {

            ComponentSubTotal.setVisibility(View.GONE);
            ComponentSubTotalBreakDown.setVisibility(View.VISIBLE);

        } else if (view == llSubTotalAddons) {

            ComponentSubTotal.setVisibility(View.VISIBLE);
            ComponentSubTotalBreakDown.setVisibility(View.GONE);

        } else if (view == btnNext){

            setSubTotalPrice();

            Intent i = new Intent(this, SummaryBookingActivity.class);
            i.putExtra("bookingProcessItem", bookingProcessItem);
            i.putExtra("tourPackageItem", tourPackageItem);
            startActivityForResult(i, 1);

        } else if (view == ivBack){
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        String str = bookingProcessItem.getParticipants().get(0).getPhone_number();
        str = str.replace("+62","");

        setSubTotalPrice();
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
