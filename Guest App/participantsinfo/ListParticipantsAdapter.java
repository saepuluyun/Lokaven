package com.aniqma.lokaventour.modul.activity.booking.participantsinfo;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.utils.popup.LokavenDialog;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.BookingProcessItem;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.ContactInfoItem;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.ParticipantsItem;
import com.aniqma.lokaventour.model.item.tourexplorer.TourPackageItem;
import com.aniqma.lokaventour.network.local.bookingproses.BookingLite;

import java.util.List;

public class ListParticipantsAdapter extends RecyclerView.Adapter<ListParticipantsAdapter.MyViewHolder> {

    private TourPackageItem tourPackageItem;
    private BookingProcessItem bookingProcessItem;
    private List<ParticipantsItem> mModelList;
    private ContactInfoItem contactInfoItem;
    private Context context;
    private int qty_adults = 0, qty_kids = 0, qty_adults_additional = 0, qty_kids_additional = 0;
    private String minKidAge;
    private String maxKidAge;
    private LokavenDialog lokavenDialog;

    public ListParticipantsAdapter(Context context, BookingProcessItem bookingProcessItem, TourPackageItem tourPackageItem) {
        this.context = context;

        this.tourPackageItem = tourPackageItem;
        this.bookingProcessItem = bookingProcessItem;
        this.contactInfoItem = bookingProcessItem.getContact_info();
        this.mModelList = bookingProcessItem.getParticipants();
        this.qty_adults = Integer.parseInt(bookingProcessItem.getQty_adults());
        this.qty_kids = Integer.parseInt(bookingProcessItem.getQty_kids());
        this.qty_adults_additional = Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalAdultQty());
        this.qty_kids_additional = Integer.valueOf(bookingProcessItem.getAdditionalCostItem().getAdditionalKidQty());
        this.minKidAge = tourPackageItem.getPrices().get(0).getMinKidAge();
        this.maxKidAge = tourPackageItem.getPrices().get(0).getMaxKidAge();
        this.lokavenDialog = new LokavenDialog(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_participants, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tvCountNumber.setText(String.valueOf(position+1));

//        if (position != 0){
//           holder.llCb.setVisibility(View.GONE);
//        }
//
//        if (mModelList.get(position).isAs_contact_info()){
//            if (position == 0) {
//                holder.cbList.setChecked(true);
//                mModelList.get(position).setAs_contact_info(true);
//            }
//        } else {
//            if (position == 0) {
//                holder.cbList.setChecked(false);
//                mModelList.get(position).setAs_contact_info(false);
//            }
//        }

        // Checkbox contact info
        holder.llCb.setVisibility(View.GONE);
        if (position == 0) {
            holder.cbList.setChecked(true);
            mModelList.get(0).setAs_contact_info(true);
        }

        final String firstName = bookingProcessItem.getContact_info().getFirst_name();
        final String lastName = bookingProcessItem.getContact_info().getLast_name();
        final String age = bookingProcessItem.getParticipants().get(0).getAge();

        if (holder.cbList.isChecked()){

            bookingProcessItem.getParticipants().get(0).setFirst_name(firstName);
            bookingProcessItem.getParticipants().get(0).setLast_name(lastName);
            bookingProcessItem.getParticipants().get(0).setAge(age);
            bookingProcessItem.getParticipants().get(0).setAs_contact_info(true);

        } else {

            bookingProcessItem.getParticipants().get(0).setAs_contact_info(false);

        }
        bookingProcessItem.setParticipants(mModelList);
        BookingLite bookingLite = new BookingLite(context);
        bookingLite.addDB(bookingProcessItem);

        holder.etFirstName.setText(mModelList.get(position).getFirst_name());
        holder.etLastName.setText(mModelList.get(position).getLast_name());
        holder.etAge.setText(mModelList.get(position).getAge());

//        holder.cbList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (holder.cbList.isChecked()){
//
//                    bookingProcessItem.getParticipants().get(0).setFirst_name(firstName);
//                    bookingProcessItem.getParticipants().get(0).setLast_name(lastName);
//                    bookingProcessItem.getParticipants().get(0).setAge(age);
//                    bookingProcessItem.getParticipants().get(0).setAs_contact_info(true);
//
//                } else {
//
//                    bookingProcessItem.getParticipants().get(0).setAs_contact_info(false);
//
//                }
//                bookingProcessItem.setParticipants(mModelList);
//
//                notifyItemChanged(0);
//                BookingLite bookingLite = new BookingLite(context);
//                bookingLite.addDB(bookingProcessItem);
//            }
//        });

        if (position < qty_adults + qty_adults_additional){
            holder.etAge.setHint(context.getString(R.string.text_number_age_adult));
        } else {
            holder.etAge.setHint(context.getString(R.string.text_number_age_kids));
        }

        holder.etFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String firstName = holder.etFirstName.getText().toString();

                if (holder.cbList.isChecked()) {
                    bookingProcessItem.getContact_info().setFirst_name(firstName);
                    bookingProcessItem.getParticipants().get(0).setFirst_name(firstName);
                }

                bookingProcessItem.getParticipants().get(position).setFirst_name(firstName);

                BookingLite bookingLite = new BookingLite(context);
                bookingLite.addDB(bookingProcessItem);
            }
        });

        holder.etLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String lastName = holder.etLastName.getText().toString();

                if (holder.cbList.isChecked()) {
                    bookingProcessItem.getContact_info().setLast_name(lastName);
                    bookingProcessItem.getParticipants().get(0).setLast_name(lastName);
                }

                bookingProcessItem.getParticipants().get(position).setLast_name(lastName);

                BookingLite bookingLite = new BookingLite(context);
                bookingLite.addDB(bookingProcessItem);
            }
        });

        holder.etAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String age = holder.etAge.getText().toString();

                bookingProcessItem.getParticipants().get(position).setAge(age);

                BookingLite bookingLite = new BookingLite(context);
                bookingLite.addDB(bookingProcessItem);}
        });
    }

    @Override
    public int getItemCount() {
        return mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView tvCountNumber;
        private EditText etFirstName;
        private EditText etLastName;
        private EditText etAge;
        private LinearLayout llCb, llInput;
        private View component_check_box;
        private CheckBox cbList;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            tvCountNumber = view.findViewById(R.id.tvCountNumber);
            etFirstName = view.findViewById(R.id.etFirstName);
            etLastName = view.findViewById(R.id.etLastName);
            etAge = view.findViewById(R.id.etAge);
            llCb = view.findViewById(R.id.llCb);
            component_check_box = view.findViewById(R.id.component_check_box);
            cbList = component_check_box.findViewById(R.id.cbList);
            llInput = view.findViewById(R.id.llInput);
        }
    }

    public List<ParticipantsItem> getItemParticipants(){
        for (int i = 0; i < mModelList.size(); i++){
            if (i  < qty_adults + qty_adults_additional) {
                if (mModelList.get(i).getAge().trim().equals("") || Integer.valueOf(mModelList.get(i).getAge()) < 18) {
                    lokavenDialog.getToast(context.getString(R.string.text_error_please_set_adult_age));
                    return null;
                }
            } else {
                if (!maxKidAge.equals("")){
                    if (mModelList.get(i).getAge().trim().equals("") || Integer.valueOf(mModelList.get(i).getAge()) > Integer.valueOf(maxKidAge)) {
                        lokavenDialog.getToast(context.getString(R.string.text_please_set_kids_age_max) + " "+ maxKidAge);
                        return null;
                    }
                }

                if (!minKidAge.equals("")){
                    if (mModelList.get(i).getAge().trim().equals("") || Integer.valueOf(mModelList.get(i).getAge()) < Integer.valueOf(minKidAge)) {
                        lokavenDialog.getToast(context.getString(R.string.text_please_set_kids_age_min) + " " + minKidAge);
                        return null;
                    }
                }

            }

            if (mModelList.get(i).getFirst_name().trim().equals("") || mModelList.get(i).getFirst_name().trim().length() < 2){
                lokavenDialog.getToast(context.getString(R.string.text_please_input_first_name_or_first_name_less_than_2_characters));
                return null;
            }
        }

        return mModelList;
    }
}
