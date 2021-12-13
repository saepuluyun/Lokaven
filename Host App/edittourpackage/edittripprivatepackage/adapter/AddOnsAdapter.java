package com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.edittripprivatepackage.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createaddons.CreateAddOnsAdapter;
import com.aniqma.lokaventour.host.utils.misc.inlinehelp.TooltipWindowUtils;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.AddonsItem;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AddOnsAdapter extends RecyclerView.Adapter<AddOnsAdapter.MyViewHolder> {

    private Activity context;
    private List<AddonsItem> items;
    private List<AddonsItem> addonsItems = new ArrayList<>();
    private TooltipWindowUtils tipWindow;
    private CallbackInterface mCallback;
    private RecyclerView.LayoutManager layoutManager;
    private AddonsImagesAdapter adapter;


    public AddOnsAdapter(Activity context, List<AddonsItem> modelList) {
        this.context = context;
        this.items = modelList;
        this.tipWindow = new TooltipWindowUtils(context);
        // .. Attach the interface
        try {
            mCallback = (CallbackInterface) context;
        } catch(ClassCastException ex){
            //.. should log the error or throw and exception
            Log.e("MyAdapter","Must implement the CallbackInterface in the Activity", ex);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_ons, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        viewHolder.etAddOnsName.setText(items.get(position).getAddon());
        viewHolder.etPricePerson.setText(items.get(position).getPrice());
        viewHolder.etPriceItem.setText(items.get(position).getPrice());
        viewHolder.etQtyItem.setText(String.valueOf(items.get(position).getQty()));

        viewHolder.etAddOnsName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (position < items.size()){
                    if (!s.toString().trim().isEmpty()){
                        items.get(position).setAddon(s.toString().trim());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (items.get(position).getPriceType().equals("")) items.get(position).setPriceType("user");

        if (items.get(position).getPriceType().equals("user")) {
            typeCheckbox(viewHolder, position, "user");
        }
        if (items.get(position).getPriceType().equals("item")) {
            typeCheckbox(viewHolder, position, "item");
        }

        // Checkbox Per Person & Per Item
        viewHolder.cbPricePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.etPriceItem.setText("");
                viewHolder.etPricePerson.setText("");
                typeCheckbox(viewHolder, position, "user");
            }
        });

        viewHolder.cbPriceItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.etPricePerson.setText("");
                viewHolder.etPriceItem.setText("");
                typeCheckbox(viewHolder, position, "item");
            }
        });

        // Form input price
        viewHolder.etPricePerson.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (position < items.size()){
                    if (!s.toString().trim().isEmpty()){
                        items.get(position).setPrice(s.toString().trim());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        viewHolder.etPriceItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (position < items.size()){
                    if (!s.toString().trim().isEmpty()){
                        items.get(position).setPrice(s.toString().trim());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Form input Qty item
        viewHolder.etQtyItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (position < items.size()){
                    if (s.toString() != null){
                        if (!s.toString().trim().equals("")){
                            items.get(position).setQty(Integer.parseInt(s.toString().trim()));
                        } else {
                            items.get(position).setQty(0);
                        }
                    } else {
                        items.get(position).setQty(0);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Help
        viewHolder.ivHelpAddonsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tipWindow.isTooltipShown()){
                    tipWindow.showToolTip(viewHolder.ivHelpAddonsName, context.getString(R.string.text_write_down_the_rule_here));
                } else {
                    tipWindow.dismissTooltip();
                }
            }
        });

        // Switch rental addons
        if (items.get(position).isRental()) viewHolder.sbRental.setChecked(true);
        if (!items.get(position).isRental()) viewHolder.sbRental.setChecked(false);

        viewHolder.sbRental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.sbRental.isChecked()) {
                    items.get(position).setRental(true);
                } else {
                    items.get(position).setRental(false);
                }
            }
        });

        // Remove addons
        viewHolder.tvRemoveAddons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addonsItems.size() == 1){
                    viewHolder.etPricePerson.setText("");
                    viewHolder.etPriceItem.setText("");
                    viewHolder.etQtyItem.setText("0");
                    viewHolder.etAddOnsName.setText("");
                }else {
                    items.remove(position);
                    notifyDataSetChanged();
                }
            }
        });

        if (items.get(position).getMediasItems() != null){
            if (items.get(position).getMediasItems().size() != 0){
                viewHolder.relAddImages.setVisibility(View.GONE);
                viewHolder.rvImageList.setVisibility(View.VISIBLE);
            } else {
                viewHolder.relAddImages.setVisibility(View.VISIBLE);
                viewHolder.rvImageList.setVisibility(View.GONE);
            }
        } else {
            viewHolder.relAddImages.setVisibility(View.VISIBLE);
            viewHolder.rvImageList.setVisibility(View.GONE);
        }

        viewHolder.relAddImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCallback != null){
                    mCallback.onHandleSelection(position);
                }
            }
        });

        viewHolder.rvImageList.setHasFixedSize(true);
        layoutManager =  new GridLayoutManager(context, 3);
        viewHolder.rvImageList.setLayoutManager(layoutManager);
        adapter = new AddonsImagesAdapter(context, items.get(position).getMediasItems(), position);
        viewHolder.rvImageList.setAdapter(adapter);
    }

    private void typeCheckbox(MyViewHolder viewHolder, int position, String type) {
        if (type.equals("user")) {
            viewHolder.cbPricePerson.setChecked(true);
            viewHolder.cbPriceItem.setChecked(false);

            items.get(position).setPriceType("user");
            items.get(position).setPrice(items.get(position).getPrice());
            items.get(position).setQty(items.get(position).getQty());

            viewHolder.etPriceItem.setText(items.get(position).getPrice());
            viewHolder.etQtyItem.setText(String.valueOf(items.get(position).getQty()));

            viewHolder.rlPricePerson.setVisibility(View.VISIBLE);
            viewHolder.rlPriceItem.setVisibility(View.GONE);
        } else {
            viewHolder.cbPricePerson.setChecked(false);
            viewHolder.cbPriceItem.setChecked(true);

            items.get(position).setPriceType("item");
            items.get(position).setPrice(items.get(position).getPrice());

            viewHolder.etPricePerson.setText(items.get(position).getPrice());

            viewHolder.rlPricePerson.setVisibility(View.GONE);
            viewHolder.rlPriceItem.setVisibility(View.VISIBLE);
        }
    }

    public List<AddonsItem> getDataAddons() {
        return items;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View componentSwitch;
        private TextInputEditText etPricePerson, etPriceItem, etQtyItem, etAddOnsName;
        private TextView tvRemoveAddons;
        private ImageView ivHelpAddonsName, ivHelpPricePerson, ivHelpPriceItem;
        private RelativeLayout rlPricePerson, rlPriceItem, relAddImages;
        private RecyclerView rvImageList;
        private CheckBox cbPricePerson, cbPriceItem;
        private Switch sbRental;

        private MyViewHolder(View itemView) {
            super(itemView);

            etAddOnsName = itemView.findViewById(R.id.etAddOnsName);
            etPricePerson = itemView.findViewById(R.id.etPricePerson);
            etPriceItem = itemView.findViewById(R.id.etPriceItem);
            etQtyItem = itemView.findViewById(R.id.etQtyItem);

            ivHelpAddonsName = itemView.findViewById(R.id.ivHelpAddonsName);
            ivHelpPricePerson = itemView.findViewById(R.id.ivHelpPricePerson);
            ivHelpPriceItem = itemView.findViewById(R.id.ivHelpPriceItem);

            rlPricePerson = itemView.findViewById(R.id.rlPricePerson);
            cbPricePerson = itemView.findViewById(R.id.cbPricePerson);
            rlPriceItem = itemView.findViewById(R.id.rlPriceItem);
            cbPriceItem = itemView.findViewById(R.id.cbPriceItem);

            componentSwitch = itemView.findViewById(R.id.componentSwitch);
            sbRental = componentSwitch.findViewById(R.id.sbList);

            relAddImages = itemView.findViewById(R.id.relAddImages);
            rvImageList = itemView.findViewById(R.id.rvImageList);
            tvRemoveAddons = itemView.findViewById(R.id.tvRemoveAddons);
            tvRemoveAddons.setVisibility(View.VISIBLE);
        }
    }

    public interface CallbackInterface{
        void onHandleSelection(int position);
    }

    public void addItem() {
        items.add(new AddonsItem());
        notifyItemInserted(getItemCount()-1);
    }
}
