package com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.featuredcontectdetail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.model.item.TagsItem;
import com.aniqma.lokaventour.host.model.item.TourPackageDetailItem;
import com.aniqma.lokaventour.host.model.item.TourPackageItem;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FeaturedContentDetailActivity extends AppCompatActivity {
    private ImageView back_tour_package_detail, img_content, img_profile_writer;
    private TabLayout tabIndicator;
    private ViewPager screen_viewpager;
    private RecyclerView recyclerView;
    private ArrayList<TourPackageItem> arrayList_tour_package = new ArrayList<>();
    private RelatedTourPackageAdapter adapterList_tourPackage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_content_detail);
        Initialization();
        back_tour_package_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tabIndicator.setSelectedTabIndicatorHeight(0);
        final List<TourPackageDetailItem> mList = new ArrayList<>();
        mList.add(new TourPackageDetailItem("https://image.winudf.com/v2/image1/Y29tLnRob3JhcHBzYW5kcm9pZC5wYW5vcmFtaWNoZHNfc2NyZWVuXzBfMTU2MzY4NDAxNV8wMzk/screen-0.jpg?fakeurl=1&type=.jpg"));
        mList.add(new TourPackageDetailItem("https://image.winudf.com/v2/image1/Y29tLnRob3JhcHBzYW5kcm9pZC5wYW5vcmFtaWNoZHNfc2NyZWVuXzBfMTU2MzY4NDAxNV8wMzk/screen-0.jpg?fakeurl=1&type=.jpg"));
        mList.add(new TourPackageDetailItem("https://image.winudf.com/v2/image1/Y29tLnRob3JhcHBzYW5kcm9pZC5wYW5vcmFtaWNoZHNfc2NyZWVuXzBfMTU2MzY4NDAxNV8wMzk/screen-0.jpg?fakeurl=1&type=.jpg"));


        final List<TagsItem> tagsList = new ArrayList<>();
        tagsList.add(new TagsItem("1", "Bandung", false));
        tagsList.add(new TagsItem("2", "unique", false));
        TourPackageDetailAdapter tourPackageDetailAdapter = new TourPackageDetailAdapter(this,mList);
        screen_viewpager.setAdapter(tourPackageDetailAdapter);
        tabIndicator.setupWithViewPager(screen_viewpager);

        FillData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapterList_tourPackage);

        Picasso.get()
                .load("https://media.travelingyuk.com/wp-content/uploads/2018/12/10-Pilihan-Wisata-Bandung-Selatan-Lokasi-Nyaman-untuk-Nikmati-Kota-Kembang-5.jpg")
                .fit()
                .into(img_content);
        Picasso.get()
                .load("https://www.wgtn.ac.nz/images/staffpics/guy-sinclair.jpg")
                .fit()
                .into(img_profile_writer);
    }

    private void FillData() {
        for (int i = 0; i <5 ; i++) {
            arrayList_tour_package.add(new TourPackageItem(R.drawable.profile_user_default,
                    R.drawable.the_wonderful_andung,
                    "Wisata Horror",
                    "3.000.000",
                    "2",
                    "Bandung"));
            arrayList_tour_package.add(new TourPackageItem(R.drawable.profile_user_default,
                    R.drawable.the_wonderful_andung,
                    "Wisata Anak",
                    "4.000.000",
                    "3",
                    "Bandung"));
        }
    }

    private void Initialization() {
        tabIndicator = findViewById(R.id.tab_indicator);
        screen_viewpager = findViewById(R.id.screen_viewpager);
        back_tour_package_detail = findViewById(R.id.back_tour_package_detail);
        recyclerView = findViewById(R.id.recyclerview_related_tour);
        img_content = findViewById(R.id.img_content);
        img_profile_writer = findViewById(R.id.img_profile_writer);
        adapterList_tourPackage = new RelatedTourPackageAdapter(FeaturedContentDetailActivity.this, arrayList_tour_package);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
