package com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.featuredcontectdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.model.item.TourPackageItem;

import java.util.ArrayList;

public class RelatedTourPackageAdapter extends RecyclerView.Adapter<RelatedTourPackageAdapter.v_holder> {

    private Context mContext;
    private ArrayList<TourPackageItem> arrayList;

    public RelatedTourPackageAdapter(Context mContext, ArrayList<TourPackageItem> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public v_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_related_tour_package, parent, false);
        return new v_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull v_holder holder, int position) {
        TourPackageItem data = arrayList.get(position);
        holder.img_package_tour.setImageResource(data.getImg_tour());
        holder.img_profile_package_tour.setImageResource(data.getImg_profile());
        holder.tv_title_package_tour.setText(data.getTv_title());
        holder.tv_day_package_tour.setText(data.getTv_day()+" hari");
        holder.tv_idr_package_tour.setText("IDR "+data.getTv_price());
        holder.tv_place_package_tour.setText(data.getTv_place());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class v_holder extends RecyclerView.ViewHolder {
        ImageView img_package_tour,
                img_profile_package_tour;
        TextView tv_title_package_tour,
                tv_idr_package_tour,
                tv_day_package_tour,
                tv_place_package_tour;
        public v_holder(@NonNull View itemView) {
            super(itemView);
            Initialization(itemView);
        }

        private void Initialization(View v) {
            img_package_tour = v.findViewById(R.id.img_package_tour);
            img_profile_package_tour = v.findViewById(R.id.img_profile_package_tour);
            tv_title_package_tour = v.findViewById(R.id.tv_title_package_tour);
            tv_idr_package_tour = v.findViewById(R.id.tv_idr_package_tour);
            tv_day_package_tour = v.findViewById(R.id.tv_day_package_tour);
            tv_place_package_tour = v.findViewById(R.id.tv_place_package_tour);
        }
    }
}
