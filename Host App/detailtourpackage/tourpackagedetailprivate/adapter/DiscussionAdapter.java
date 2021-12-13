package com.aniqma.lokaventour.host.modul.activity.tourmanagement.detailtourpackage.tourpackagedetailprivate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.helper.date.DateHelper;
import com.aniqma.lokaventour.host.model.item.inbox.discussion.DiscussionReplyItem;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.List;

import static com.aniqma.lokaventour.host.helper.url.UrlHelper.IsValidUrl;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.viewHolder>{
    private List<DiscussionReplyItem> discussionItems;
    private Context context;
    private List<DiscussionReplyItem> originalList;
    private String tour_id;

    public DiscussionAdapter(List<DiscussionReplyItem> listDiscussion, Context context, String tour_id) {
        this.discussionItems = listDiscussion;
        this.context = context;
        this.originalList = listDiscussion;
        this.tour_id = tour_id;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discussion, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final DiscussionReplyItem discussionItem = discussionItems.get(position);

        if (discussionItem.getDiscussion() != null){

            String date="";
            try {
                date = DateHelper.getDateFormat(discussionItem.getDiscussion().getCreated_at());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            holder.nameDiscussion.setText(discussionItem.getDiscussion().getUserInfoItem().getFullname());
            holder.dateDiscussion.setText(date);
            holder.descDiscussion.setText(discussionItem.getDiscussion().getBody());

            String images = discussionItem.getDiscussion().getUserInfoItem().getPhoto();
            if (IsValidUrl(images)) {
                if (images.contains("http://")) {
                    images = images.replace("http://", "https://");
                }

                Picasso.get()
                        .load(images)
                        .fit()
                        .placeholder(R.drawable.profile_user_default)
                        .into(holder.imgDiscussion);
            }
        } else {
            holder.descDiscussion.setVisibility(View.GONE);
            holder.dateDiscussion.setVisibility(View.GONE);
            holder.descDiscussion.setVisibility(View.GONE);
            holder.imgDiscussion.setVisibility(View.GONE);
        }

        if (discussionItem.getReply() != null){

            String date="";
            try {
                date = DateHelper.getDateFormat(discussionItem.getReply().getCreated_at());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            holder.nameDiscussionsub.setText(discussionItem.getReply().getUserInfoItem().getFullname());
            holder.dateDiscussionsub.setText(date);
            holder.descDiscussionsub.setText(discussionItem.getReply().getBody());

            String images = discussionItem.getReply().getUserInfoItem().getPhoto();
            if (IsValidUrl(images)) {
                if (images.contains("http://")) {
                    images = images.replace("http://", "https://");
                }

                Picasso.get()
                        .load(images)
                        .fit()
                        .placeholder(R.drawable.profile_user_default)
                        .into(holder.imgDiscussionsub);
            }
        } else {
            holder.nameDiscussionsub.setVisibility(View.GONE);
            holder.dateDiscussionsub.setVisibility(View.GONE);
            holder.descDiscussionsub.setVisibility(View.GONE);
            holder.imgDiscussionsub.setVisibility(View.GONE);
        }

        holder.linReply.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return discussionItems == null ? 0 : discussionItems.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private View view, host, guest;
        private ImageView imgDiscussion, imgDiscussionsub;
        private TextView nameDiscussion, nameDiscussionsub;
        private TextView dateDiscussion, dateDiscussionsub;
        private TextView descDiscussion, descDiscussionsub;
        private TextView btn_replay;
        private LinearLayout linReply;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            init(view);
        }

        private void init(View view) {
            host = view.findViewById(R.id.host);
            guest = view.findViewById(R.id.guest);
            imgDiscussion = host.findViewById(R.id.ivProfile);
            imgDiscussionsub = guest.findViewById(R.id.ivProfile);
            nameDiscussion = host.findViewById(R.id.tvName);
            nameDiscussionsub = guest.findViewById(R.id.tvName);
            dateDiscussion = host.findViewById(R.id.tvStatus);
            dateDiscussionsub = guest.findViewById(R.id.tvStatus);
            descDiscussion = host.findViewById(R.id.tvMsgReview);
            descDiscussionsub = guest.findViewById(R.id.tvMsgReview);
            btn_replay = view.findViewById(R.id.btn_reply);
            linReply = view.findViewById(R.id.linReply);
        }
    }
}
