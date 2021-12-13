package com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailprivate.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.MediasItem;
import com.aniqma.lokaventour.host.utils.dialog.DialogVideoView;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.aniqma.lokaventour.host.helper.url.YouTubeHelper.extractVideoIdFromUrl;

public class TourPackageImageAdapter extends PagerAdapter {
    private Context mContext;
    private List<MediasItem> mListScreen;

    public TourPackageImageAdapter(Context mContext, List<MediasItem> mListScreen) {
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.item_tour_package_detail_image,null);

        ImageView imgSlide = layoutScreen.findViewById(R.id.image_tour_package);
        ImageView ivPlay = layoutScreen.findViewById(R.id.ivPlay);

        if (mListScreen.get(position).getType().trim().equals("video")){
            ivPlay.setVisibility(View.VISIBLE);
            String id = extractVideoIdFromUrl(mListScreen.get(position).getUrl());

            ivPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogVideoView dialogVideoView = new DialogVideoView(mContext, id.trim());
                    dialogVideoView.show();
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mListScreen.get(position).getUrl()));
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setPackage("com.google.android.youtube");
//                mContext.startActivity(intent);
                }
            });

            Picasso.get()
                    .load("https://img.youtube.com/vi/" + id.trim() + "/0.jpg")
                    .into(imgSlide);
        } else {
            ivPlay.setVisibility(View.GONE);
            MediasItem data = mListScreen.get(position);

            String str = data.getUrl();
            if (!str.contains("https")){
                str = str.replace("http", "https");
            }

            Picasso.get()
                    .load(str)
                    .error(R.drawable.thumbnail_default)
                    .fit()
                    .into(imgSlide);
        }

        container.addView(layoutScreen);

        return layoutScreen;
    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View)object);
    }
}
