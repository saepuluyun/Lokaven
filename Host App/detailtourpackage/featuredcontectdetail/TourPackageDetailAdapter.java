package com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.featuredcontectdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.model.item.TourPackageDetailItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TourPackageDetailAdapter extends PagerAdapter {

    Context mContext;
    List<TourPackageDetailItem> mListScreen;

    public TourPackageDetailAdapter(Context mContext, List<TourPackageDetailItem> mListScreen) {
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.item_tour_package_detail_image,null);

        ImageView imgSlide = layoutScreen.findViewById(R.id.image_tour_package);

        Picasso.get()
                .load(mListScreen.get(position).getImage())
                .fit()
                .into(imgSlide);

        container.addView(layoutScreen);

        return layoutScreen;
    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View)object);
    }
}
