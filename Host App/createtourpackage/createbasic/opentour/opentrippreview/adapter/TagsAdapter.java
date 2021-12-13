package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createbasic.opentour.opentrippreview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;

import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.MyViewHolder> {

    private List<String> mModelList;
    private Context context;

    public TagsAdapter(Context context, List<String> modelList) {
        mModelList = modelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tags, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.title_tags.setText(mModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView title_tags;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            title_tags = itemView.findViewById(R.id.title_tags);
        }
    }
}
