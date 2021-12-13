package com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingaddons;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.BookingProcessItem;
import com.aniqma.lokaventour.model.item.tourexplorer.AddonsItem;
import com.aniqma.lokaventour.model.item.tourexplorer.TourPackageItem;
import com.aniqma.lokaventour.network.local.tourmanagement.TourPackageLite;

import java.util.List;

import static com.aniqma.lokaventour.helper.format.AlphabetHelper.firstCapitalize;
import static com.aniqma.lokaventour.helper.format.NumberHelper.formatIdr;

public class ListAddonsAdapter extends RecyclerView.Adapter<ListAddonsAdapter.MyViewHolder> {

    private List<AddonsItem> mModelList;
    private Context context;
    private TextView tvTotalPrice;
    private int totalParticipants;

    private BookingProcessItem bookingProcessItem;
    private ListAddonsImagesAdapter listAddonsImagesAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public ListAddonsAdapter(Context context, TourPackageItem tourPackageItem, TextView tvTotalPrice, BookingProcessItem bookingProcessItem) {
        this.context = context;
        this.mModelList = tourPackageItem.getAddons();
        this.tvTotalPrice = tvTotalPrice;
        this.bookingProcessItem = bookingProcessItem;
        this.totalParticipants = bookingProcessItem.getParticipants().size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_addons, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AddonsItem addonsItem = mModelList.get(position);

        holder.sbList.setChecked(false);

        if (addonsItem.getPriceType().equals("user")) {
            holder.component_switch.setVisibility(View.VISIBLE);
            holder.component_item.setVisibility(View.GONE);
            holder.tvPerson.setVisibility(View.VISIBLE);
            holder.llItem.setVisibility(View.GONE);

            if (mModelList.get(position).isCheked()) {
                holder.sbList.setChecked(true);
            }

            holder.tvDesc.setText(context.getString(R.string.text_provided_with_breakfast));

        } else if (addonsItem.getPriceType().equals("item")){
            holder.component_switch.setVisibility(View.GONE);
            holder.component_item.setVisibility(View.VISIBLE);
            holder.tvPerson.setVisibility(View.GONE);
            holder.llItem.setVisibility(View.VISIBLE);

            String qty = String.valueOf(addonsItem.getQty());
            String totalQty = String.valueOf(addonsItem.getTotalQty());

            holder.tvItemAmount.setText(qty);
            holder.tvNumber.setText(totalQty);

            String desc = context.getString(R.string.text_you_can_choose_per)+" "+qty+" "+
                    context.getString(R.string.text_item_small);
            holder.tvDesc.setText(desc);
        }

        double price = Double.parseDouble(addonsItem.getPrice());

        holder.tvAddonsName.setText(firstCapitalize(addonsItem.getAddon()));
        holder.tvPrice.setText(formatIdr(price));

        holder.sbList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str = tvTotalPrice.getText().toString().trim();
                str = str.replace(".","");
                str = str.replace("IDR ","");

                if (holder.sbList.isChecked()){
//                    int subTotal = Integer.valueOf(mModelList.get(position).getPrice()) * totalParticipants;
//                    int total = Integer.valueOf(bookingProcessItem.getTotal_price()) + subTotal;

                    mModelList.get(position).setCheked(true);

                    TourPackageLite tourPackageLite = new TourPackageLite(context);
                    tourPackageLite.updateCheckAddons(true, addonsItem.getAddonId());

//                    double totalPrice = Double.parseDouble(String.valueOf(total));
//                    tvTotalPrice.setText(formatIdr(totalPrice));

                } else {
//                    int subTotal = Integer.valueOf(mModelList.get(position).getPrice()) * totalParticipants;
//                    int total = Integer.valueOf(bookingProcessItem.getTotal_price()) - subTotal;

                    mModelList.get(position).setCheked(false);

                    TourPackageLite tourPackageLite = new TourPackageLite(context);
                    tourPackageLite.updateCheckAddons(false, addonsItem.getAddonId());

//                    double totalPrice = Double.parseDouble(String.valueOf(total));
//                    tvTotalPrice.setText(formatIdr(totalPrice));
                }

                ((AddonsBookingActivity)context).setSubTotalPrice();
            }
        });

        holder.ibPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtyNow = Integer.valueOf(holder.tvNumber.getText().toString());
//                int priceItem = Integer.valueOf(mModelList.get(position).getPrice());
//                int qtyItem = mModelList.get(position).getQty();
                int number = qtyNow + mModelList.get(position).getQty();

//                int subTotal = (number/qtyItem) * priceItem;
//                int total = Integer.valueOf(bookingProcessItem.getTotal_price()) + (subTotal);

                mModelList.get(position).setTotalQty(number);
                holder.tvNumber.setText(String.valueOf(number));

                TourPackageLite tourPackageLite = new TourPackageLite(context);
                tourPackageLite.updateQtyOrderAddons(number, addonsItem.getAddonId());

//                double totalPrice = Double.parseDouble(String.valueOf(total));
//                tvTotalPrice.setText(formatIdr(totalPrice));

                ((AddonsBookingActivity)context).setSubTotalPrice();
            }
        });

        holder.ibMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qtyNow = Integer.valueOf(holder.tvNumber.getText().toString());
                if (qtyNow > 0) {
//                    int priceItem = Integer.valueOf(mModelList.get(position).getPrice());
//                    int qtyItem = mModelList.get(position).getQty();
                    int number = qtyNow - mModelList.get(position).getQty();

//                    int subTotal = (number/qtyItem) * priceItem;
//                    int total = Integer.valueOf(bookingProcessItem.getTotal_price()) - (subTotal);

                    mModelList.get(position).setTotalQty(number);
                    holder.tvNumber.setText(String.valueOf(number));

                    TourPackageLite tourPackageLite = new TourPackageLite(context);
                    tourPackageLite.updateQtyOrderAddons(number, addonsItem.getAddonId());

//                    double totalPrice = Double.parseDouble(String.valueOf(total));
//                    tvTotalPrice.setText(formatIdr(totalPrice));

                    ((AddonsBookingActivity)context).setSubTotalPrice();
                }
            }
        });

        holder.rvImageList.setVisibility(View.GONE);
        if (addonsItem.getMediasItem() != null) {
            if (addonsItem.getMediasItem().size() > 0) {
                holder.rvImageList.setVisibility(View.VISIBLE);

                holder.rvImageList.setHasFixedSize(true);
                layoutManager =  new GridLayoutManager(context, 3);
                holder.rvImageList.setLayoutManager(layoutManager);
                listAddonsImagesAdapter = new ListAddonsImagesAdapter(context, addonsItem.getMediasItem());
                holder.rvImageList.setAdapter(listAddonsImagesAdapter);

            }
        }
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view, component_switch, component_item;
        private TextView tvAddonsName, tvPrice, tvPerson, tvItemAmount, tvDesc, tvNumber;
        private LinearLayout llItem;
        private RecyclerView rvImageList;
        private Switch sbList;
        private ImageButton ibMinus, ibPlus;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvAddonsName = view.findViewById(R.id.tvAddonsName);
            tvPrice = view.findViewById(R.id.tvPrice);
            tvDesc = view.findViewById(R.id.tvDesc);
            tvPerson = view.findViewById(R.id.tvPerson);
            llItem = view.findViewById(R.id.llItem);
            tvItemAmount = view.findViewById(R.id.tvItemAmount);
            rvImageList = view.findViewById(R.id.rvImageList);

            component_switch = view.findViewById(R.id.component_switch);
            sbList = component_switch.findViewById(R.id.sbList);

            component_item = view.findViewById(R.id.component_item);
            ibMinus = component_item.findViewById(R.id.ibMinus);
            tvNumber = component_item.findViewById(R.id.tvNumber);
            ibPlus = component_item.findViewById(R.id.ibPlus);
        }
    }

    public List<AddonsItem> getItemAddons(){
        notifyDataSetChanged();
        return mModelList;
    }
}
