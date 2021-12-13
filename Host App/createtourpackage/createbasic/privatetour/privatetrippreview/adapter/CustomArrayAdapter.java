package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.privatetour.privatetrippreview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.helper.format.NumberHelper;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.PricesItem;

import java.util.List;

public class CustomArrayAdapter extends ArrayAdapter<String>{

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final List<PricesItem> items;
    private final int mResource;
    private double taxNominal = 0;
    private double taxPercentage = 0;

    public CustomArrayAdapter(Context context, int resource,
                              List objects, double taxNominal, double taxPercentage) {
        super(context, resource, 0, objects);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        items = objects;
        this.taxNominal = taxNominal;
        this.taxPercentage = taxPercentage;

    }
    @Override
    public View getDropDownView(int position,  View convertView,
                                 ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public  View getView(int position,  View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        final View view = mInflater.inflate(mResource, parent, false);

        TextView tvPrice = (TextView) view.findViewById(R.id.tvPrice);
        TextView tvMinQuota = (TextView) view.findViewById(R.id.tvMinQuota);
        TextView tvMaxQuota = (TextView) view.findViewById(R.id.tvMaxQuota);

        PricesItem pricesItem = items.get(position);

        double tax = Double.parseDouble(pricesItem.getPrice()) * taxPercentage;
        tvPrice.setText(NumberHelper.formatIdr(Double.parseDouble(pricesItem.getPrice()) + tax + taxNominal));
        tvMinQuota.setText(pricesItem.getMinParticipant());
        tvMaxQuota.setText(pricesItem.getMaxParticipant());

        return view;
    }
}