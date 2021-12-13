package com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.edittripprivatepackage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.MediasItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AddonsImagesAdapter extends RecyclerView.Adapter<AddonsImagesAdapter.v_holder> {
    private List<MediasItem> arrayList;
    private CallbackInterface mCallback;
    private int positionAddons = 0;

    public interface CallbackInterface{

        void onHandleSelection(int position, String text);
    }

    public AddonsImagesAdapter(Context mContext, List<MediasItem> arrayList, int position) {
        this.arrayList = arrayList;
        this.positionAddons = position;

        try{
            mCallback = (CallbackInterface) mContext;
        }catch(ClassCastException ex){

        }
    }

    @NonNull
    @Override
    public v_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_custom_choose_image, parent, false);
        return new v_holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull v_holder holder, final int position) {
        final MediasItem data = arrayList.get(position);
        if ((arrayList.size()-1) == position){
            holder.imageView.setImageResource(R.drawable.icon_18dp_plus_dark);
            holder.imageView.setPadding(50, 50, 50, 50);
            holder.imageView.setPadding(55, 65, 55, 55);
            holder.close.setVisibility(View.GONE);
        } else {
            String thumbnail = data.getImage_url();
            if (!data.getImage_url().contains("https")){
                thumbnail = thumbnail.replace("http", "https");
            }

            if (!thumbnail.trim().isEmpty()){
                Picasso.get()
                        .load(thumbnail)
                        .resize(110, 110)
                        .into(holder.imageView);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == (arrayList.size()-1)) {
                    if(mCallback != null){
                        arrayList.remove(position);
                        mCallback.onHandleSelection(positionAddons, data.image_url);
                    }
                }
            }
        });

        holder.img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrayList == null){
            return 0;
        } else {
            return arrayList.size();
        }
    }

    public class v_holder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private RelativeLayout close;
        private ImageView img_close;

        public v_holder(@NonNull View itemView) {
            super(itemView);
            Init(itemView);
        }

        private void Init(View v) {
            imageView = v.findViewById(R.id.img_choose);
            close = v.findViewById(R.id.relativelayout);
            img_close = v.findViewById(R.id.img_close);
        }
    }
}
