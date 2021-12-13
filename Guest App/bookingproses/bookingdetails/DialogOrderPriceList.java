package com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingdetails;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.BookingProcessItem;
import com.aniqma.lokaventour.model.item.tourexplorer.PricesItem;
import com.aniqma.lokaventour.model.item.tourexplorer.TourPackageItem;

import java.util.List;

public class DialogOrderPriceList extends Dialog {

    private Context context;
    private RecyclerView.LayoutManager layoutManagerPrice;
    private RecyclerView recyclerViewPrice;
    private ListPriceAdapter listPriceAdapter;
    private List<PricesItem> pricesItems;
    private BookingProcessItem bookingProcessItem;
    private TourPackageItem tourPackageItem;

    private String typeTour, minQuota, maxQuota;

    public DialogOrderPriceList(@NonNull Context context, TourPackageItem tourPackageItem, BookingProcessItem bookingProcessItem) {
        super(context);
        this.context = context;
        this.tourPackageItem = tourPackageItem;
        this.bookingProcessItem = bookingProcessItem;

        pricesItems = tourPackageItem.getPrices();
        typeTour = tourPackageItem.getTypeTour();
        minQuota = bookingProcessItem.getSchedules().get(0).getMinQuota();
        maxQuota = bookingProcessItem.getSchedules().get(0).getMaxQuota();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_price_list);
        init();
        setPriceList();
    }

    private void setPriceList() {
        recyclerViewPrice.setHasFixedSize(true);
        layoutManagerPrice = new LinearLayoutManager(getContext());
        recyclerViewPrice.setLayoutManager(layoutManagerPrice);
        listPriceAdapter = new ListPriceAdapter(getContext(), pricesItems, typeTour, minQuota, maxQuota);
        recyclerViewPrice.setAdapter(listPriceAdapter);
    }

    private void init() {
        recyclerViewPrice = findViewById(R.id.price_list);
    }
}
