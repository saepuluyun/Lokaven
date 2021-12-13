package com.aniqma.lokaventour.modul.activity.booking.bookingproses.bookingaddons;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.R;
import com.aniqma.lokaventour.model.item.bookingService.bookingprocess.AddonsImagesItem;
import com.squareup.picasso.Picasso;

import java.util.List;
import static com.aniqma.lokaventour.helper.url.UrlHelper.IsValidUrl;

public class ListAddonsImagesAdapter extends RecyclerView.Adapter<ListAddonsImagesAdapter.MyViewHolder> {

    private List<AddonsImagesItem> mModelList;
    private Context context;

    public ListAddonsImagesAdapter(Context context, List<AddonsImagesItem> mediasItem) {
        this.context = context;
        this.mModelList = mediasItem;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_image_addons, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AddonsImagesItem addonsImagesItem = mModelList.get(position);
        if (IsValidUrl(addonsImagesItem.getImageUrl())) {
            Picasso.get()
                    .load(addonsImagesItem.getImageUrl())
                    .fit()
                    .into(holder.ivImages);
        }
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImages;

        private MyViewHolder(View itemView) {
            super(itemView);
            ivImages = itemView.findViewById(R.id.ivImages);
        }
    }

}
