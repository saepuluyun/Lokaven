package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.categorieslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.createTour.CategoriesItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.vholder> {
    private List<CategoriesItem> items;
    private List<CategoriesItem>filteredData;
    private Context mContext;
    private ItemFilter mFilter = new ItemFilter();

    public CategoriesAdapter(List<CategoriesItem> items, Context mContext) {
        this.items = items;
        this.filteredData = items;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categories, parent, false);
        return new vholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final vholder holder, final int position) {
        if (items.get(position).isCliked()){
            holder.ivCheck.setVisibility(View.VISIBLE);
        } else {
            holder.ivCheck.setVisibility(View.GONE);
        }

//        String thumbnail = items.get(position).getCategory_image_url();
//
//        if (IsValidUrl(thumbnail)) {
//            if (!thumbnail.contains("https")){
//                thumbnail = thumbnail.replace("http", "https");
//            }
//
//            Picasso.get()
//                    .load(thumbnail)
//                    .fit()
//                    .centerCrop()
//                    .into(holder.ivCategories);
//        }


        Picasso.get()
                .load(items.get(position).getCategory_image_drawable())
                .fit()
                .into(holder.ivCategories);

        holder.tvTitle.setText(items.get(position).getCategories());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (items.get(position).isCliked()){
                    items.get(position).setCliked(false);
                    holder.ivCheck.setVisibility(View.GONE);
                } else {
                    items.get(position).setCliked(true);
                    holder.ivCheck.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<CategoriesItem> getData(){
        return filteredData;
    }

    public class vholder extends RecyclerView.ViewHolder {

        View view;
        TextView tvTitle;
        ImageView ivCheck;
        ImageView ivCategories;

        public vholder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tvTitle = view.findViewById(R.id.tvTitle);
            ivCheck = view.findViewById(R.id.ivCheck);
            ivCategories = view.findViewById(R.id.ivCategories);
        }
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<CategoriesItem> list = filteredData;

            int count = list.size();
            final List<CategoriesItem> nlist = new ArrayList<>();

            CategoriesItem filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.getCategories().toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items = (ArrayList<CategoriesItem>) results.values;
            notifyDataSetChanged();
        }

    }
}

