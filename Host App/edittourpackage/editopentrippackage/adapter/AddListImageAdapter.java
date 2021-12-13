package com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.editopentrippackage.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.editopentrippackage.EditOpenTripPackageActivity;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.MediasItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AddListImageAdapter extends RecyclerView.Adapter<AddListImageAdapter.MyViewHolder> {

    private List<MediasItem> mModelList;
    private List<Integer> addModelList;
    private Context context;
    Activity activity;
    EditOpenTripPackageActivity editOpenTripPackageActivity;
    public AddListImageAdapter(Context context, List<MediasItem> modelList) {
        mModelList = modelList;
        this.context = context;
        editOpenTripPackageActivity = (EditOpenTripPackageActivity) context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_choose_image, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.close.setVisibility(View.VISIBLE);
        holder.item_image.setVisibility(View.VISIBLE);
        if (position == mModelList.size()-1){
            holder.item_image.setVisibility(View.GONE);
            holder.close.setVisibility(View.GONE);
            holder.last_image.setVisibility(View.VISIBLE);
        }else {
            holder.last_image.setVisibility(View.GONE);
        }
        final MediasItem listImageEditItem = mModelList.get(position);

        String str = listImageEditItem.getUrl();
        if (!str.contains("https")){
            str = str.replace("http", "https");
        }

        try {
            Picasso.get()
                    .load(str)
                    .fit()
                    .centerCrop()
                    .into(holder.item_image);
        }catch (Exception e){}
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
                editOpenTripPackageActivity.pickImage();
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
