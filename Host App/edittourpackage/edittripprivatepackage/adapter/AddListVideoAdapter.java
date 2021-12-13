package com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.edittripprivatepackage.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import java.util.List;

import static com.aniqma.lokaventour.host.helper.url.YouTubeHelper.extractVideoIdFromUrl;

public class AddListVideoAdapter extends RecyclerView.Adapter<AddListVideoAdapter.v_holder> {
    private Context mContext;
    private List<MediasItem> arrayList;
    private CallbackInterface mCallback;

    public interface CallbackInterface{

        void onHandleSelection(int position, String text);
    }

    public AddListVideoAdapter(Context mContext, List<MediasItem> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;

        try{
            mCallback = (CallbackInterface) mContext;
        }catch(ClassCastException ex){

        }
    }

    @NonNull
    @Override
    public v_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_custom_choose_videos, parent, false);
        return new v_holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull v_holder holder, final int position) {
        final MediasItem data = arrayList.get(position);
        holder.last_image.setVisibility(View.GONE);
        String id = extractVideoIdFromUrl(data.getUrl());

        Picasso.get()
                .load("https://img.youtube.com/vi/" + id.trim() + "/0.jpg")
                .fit()
                .into(holder.imageView);


        holder.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getUrl()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.youtube");
                mContext.startActivity(intent);
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
        private ImageView ivPlay;
        private RelativeLayout close;
        private ImageView img_close;
        private ImageView last_image;

        public v_holder(@NonNull View itemView) {
            super(itemView);
            Init(itemView);
        }

        private void Init(View v) {
            imageView = v.findViewById(R.id.img_choose);
            last_image = v.findViewById(R.id.last_image);
            ivPlay = v.findViewById(R.id.ivPlay);
            close = v.findViewById(R.id.relativelayout);
            img_close = v.findViewById(R.id.img_close);
        }
    }
}
