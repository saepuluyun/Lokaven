package com.aniqma.lokaventour.modul.activity.booking.bookingproses.contactbooking;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.model.item.bookingService.bookingByContact.TourRecomendationItem;
import com.aniqma.lokaventour.model.item.profileandsettings.profile.detailHost.DataDetailHostItem;
import com.aniqma.lokaventour.modul.activity.tourpackagedetails.detailopen.TourPackageDetailOpenActivity;
import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.modul.activity.tourpackagedetails.detailprivate.TourPackageDetailPrivateActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.aniqma.lokaventour.helper.url.UrlHelper.IsValidUrl;

public class ContactBookingAfterSubmitAdapter extends RecyclerView.Adapter<ContactBookingAfterSubmitAdapter.MyViewHolder> {

    private List<TourRecomendationItem> mModelList;
    private Activity context;
    private DataDetailHostItem dataHost;

    public ContactBookingAfterSubmitAdapter(Activity context, List<TourRecomendationItem> modelList, DataDetailHostItem dataHost) {
        mModelList = modelList;
        this.context = context;
        this.dataHost = dataHost;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_cards_tour_package_thumb, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final TourRecomendationItem packageTourItem = mModelList.get(position);

        holder.tvTitlePackage.setText(packageTourItem.getTitle());
        try {
            holder.tvPrice.setText(packageTourItem.getPrices().get(0).getPrice());
            holder.tvDuration.setText(packageTourItem.getSchedules().get(0).getDuration());
            if (packageTourItem.getSchedules().get(0).getDuration().trim().equals("0")){
                holder.tvDuration.setText("1");
            }
        }catch (Exception e){
            holder.tvPrice.setText("-");
            holder.tvDuration.setText("-");
        }
        holder.tvLocation.setText(packageTourItem.getLocation());
        if (packageTourItem.getTypeTour().equalsIgnoreCase("open")){
            holder.tvTypeTour.setBackgroundResource(R.drawable.custom_bg_radius_ocean_blue);
            holder.tvTypeTour.setText(context.getResources().getString(R.string.text_open_tour));
            holder.tvFrom.setVisibility(View.GONE);
        } else{
            holder.tvTypeTour.setBackgroundResource(R.drawable.custom_bg_radius_light_blue);
            holder.tvTypeTour.setText(context.getResources().getString(R.string.text_private_tour));
            holder.tvFrom.setVisibility(View.VISIBLE);
        }

        String urlPhoto = dataHost.getProfilePictureUrl();
        if (IsValidUrl(urlPhoto)) {
            if (urlPhoto.contains("http://")) {
                urlPhoto = urlPhoto.replace("http://", "https://");
            }

            Picasso.get()
                    .load(urlPhoto)
                    .fit()
                    .placeholder(R.drawable.profile_user_default)
                    .into(holder.imgPhotoProfile);
        }

        String thumbnail = packageTourItem.getMedias().get(0).getUrl();
        if (IsValidUrl(thumbnail)) {
            if (thumbnail.contains("http://")) {
                thumbnail = thumbnail.replace("http://", "https://");
            }

            Picasso.get()
                    .load(thumbnail)
                    .fit()
                    .placeholder(R.drawable.image_placeholder)
                    .into(holder.imgPackageTour);
        }


        holder.cvTourPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (packageTourItem.getTypeTour().equalsIgnoreCase("open")){
                    Intent i = new Intent(context, TourPackageDetailPrivateActivity.class);
                    i.putExtra("tourId",packageTourItem.getTourId());
                    i.putExtra("hostId", packageTourItem.getHostId());
                    i.putExtra("typeTour", packageTourItem.getTypeTour());
                    context.startActivity(i);
                    context.finish();
                }else {
                    Intent i = new Intent(context, TourPackageDetailOpenActivity.class);
                    i.putExtra("tourId", packageTourItem.getTourId());
                    i.putExtra("hostId", packageTourItem.getHostId());
                    i.putExtra("typeTour", packageTourItem.getTypeTour());
                    context.startActivity(i);
                    context.finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView tvTitlePackage, tvPrice, tvLocation, tvDuration;
        private ImageView imgPackageTour, imgPhotoProfile;
        private TextView tvFrom;
        private TextView tvTypeTour;
        private View tvStatus;
        private RelativeLayout cvTourPackage;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            cvTourPackage = itemView.findViewById(R.id.rlTourPackage);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvTypeTour = tvStatus.findViewById(R.id.tvChip);
            tvFrom = itemView.findViewById(R.id.tvFrom);
            tvTitlePackage = itemView.findViewById(R.id.tvTitlePackage);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvLocation = itemView.findViewById(R.id.tvLocationTour);
            imgPackageTour = itemView.findViewById(R.id.imgPackageTour);
            imgPhotoProfile = itemView.findViewById(R.id.imgPhotoProfile);
        }
    }
}
