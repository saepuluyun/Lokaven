package com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.edittripprivatepackage.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.edittripprivatepackage.EditTripPackageActivity;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.MediasItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.aniqma.lokaventour.host.helper.url.UrlHelper.IsValidUrl;

public class AddListImageAdapter extends RecyclerView.Adapter<AddListImageAdapter.MyViewHolder> {

    private List<MediasItem> mModelList;
    private List<Integer> addModelList;
    private Context context;
    Activity activity;
    EditTripPackageActivity editTripPrivatePackageActivity;
    public AddListImageAdapter(Context context, List<MediasItem> modelList) {
        mModelList = modelList;
        this.context = context;
        editTripPrivatePackageActivity = (EditTripPackageActivity) context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_choose_image, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.item_image.setVisibility(View.VISIBLE);
        holder.close.setVisibility(View.VISIBLE);
        final MediasItem mediasItem = mModelList.get(position);

        String thumbnail = mediasItem.getUrl();

        if (IsValidUrl(thumbnail)) {
            if (!thumbnail.contains("https")){
                thumbnail = thumbnail.replace("http", "https");
            }

            Picasso.get()
                    .load(thumbnail)
                    .fit()
                    .centerCrop()
                    .into(holder.item_image);
        }

        if (position == mModelList.size()-1){
            holder.item_image.setVisibility(View.GONE);
            holder.close.setVisibility(View.GONE);
            holder.last_image.setVisibility(View.VISIBLE);
        }else {
            holder.last_image.setVisibility(View.GONE);
        }

        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mModelList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.last_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTripPrivatePackageActivity.pickImage();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private ImageView last_image;
        private ImageView item_image;
        private RelativeLayout close;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            item_image = itemView.findViewById(R.id.img_choose);
            close = itemView.findViewById(R.id.relativelayout);
            last_image = itemView.findViewById(R.id.last_image);
        }
    }
}
