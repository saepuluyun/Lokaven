package com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.helper.url.UrlHelper;
import com.aniqma.lokaventour.host.model.item.rateandreview.DataReviewItem;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;

import static com.aniqma.lokaventour.host.helper.date.DateHelper.getDateFormat1;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ItemViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<DataReviewItem> itemList;
    private Context context;

    public ReviewsAdapter(Context context, ArrayList<DataReviewItem> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_review, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, int i) {
        DataReviewItem reviewsDetailItem = itemList.get(i);

        try {
            String date = "";
            String edited = "";
            String createdAt = reviewsDetailItem.getCreated_at();
            String updatedAt = reviewsDetailItem.getUpdated_at();

            if (updatedAt.equals("0001-01-01T00:00:00Z")) {
                edited = "";
                date = createdAt;
            } else {
                edited = context.getString(R.string.text_edited)+" ";
                date = updatedAt;
            }

            itemViewHolder.recycleView_sub.setVisibility(View.GONE);
            itemViewHolder.nameReviews.setText(reviewsDetailItem.getUserInfoItem().getFullname());
            itemViewHolder.dateReviews.setText(edited+getDateFormat1(date));
            itemViewHolder.descReviews.setText(reviewsDetailItem.getReview());

            String urlPhoto = reviewsDetailItem.getUserInfoItem().getUrl_photo();
            if (UrlHelper.IsValidUrl(urlPhoto)){
                String str = urlPhoto.replace("http://", "https://");

                Picasso.get()
                        .load(str)
                        .fit()
                        .into(itemViewHolder.imgReviews);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private ImageView imgReviews;
        private TextView nameReviews;
        private TextView dateReviews;
        private TextView descReviews;
        private RecyclerView recycleView_sub;

        ItemViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imgReviews = view.findViewById(R.id.ivProfile);
            nameReviews = view.findViewById(R.id.tvName);
            dateReviews = view.findViewById(R.id.tvStatus);
            descReviews = view.findViewById(R.id.tvMsgReview);
            recycleView_sub = view.findViewById(R.id.recycleview_reviews_sub);
        }
    }
}
