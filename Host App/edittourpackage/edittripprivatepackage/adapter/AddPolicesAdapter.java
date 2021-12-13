package com.aniqma.lokaventour.host.modul.activity.tourmanagement.edittourpackage.edittripprivatepackage.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aniqma.lokaventour.host.R;
import com.aniqma.lokaventour.host.utils.misc.inlinehelp.TooltipWindowUtils;
import com.aniqma.lokaventour.host.model.item.tourpackageservice.CustomPolicyItem;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AddPolicesAdapter extends RecyclerView.Adapter<AddPolicesAdapter.ViewHolder> {

    private Activity activity;
    private List<CustomPolicyItem> items;
    List<CustomPolicyItem> customPolicyItems = new ArrayList<>();
    String policy = "", namePolicy = "";
    private TooltipWindowUtils tipWindow;
    public AddPolicesAdapter(Activity activity, List<CustomPolicyItem> items){
        this.activity = activity;
        this.items = items;
        tipWindow = new TooltipWindowUtils(activity);
    }

    @NonNull
    @Override
    public AddPolicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_policy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddPolicesAdapter.ViewHolder holder, final int position) {
        holder.bind(items.get(position));;

        holder.etPolicesName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                items.get(position).setPolicyName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.acThePolices.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                items.get(position).setPolicy(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.ivHelpTitlePolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tipWindow.isTooltipShown()){
                    tipWindow.showToolTip(holder.ivHelpTitlePolicy, activity.getString(R.string.text_the_title_of_rule_you_might_need_to_mention_to_guest));
                } else {
                    tipWindow.dismissTooltip();
                }
            }
        });

        holder.ivHelpPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tipWindow.isTooltipShown()){
                    tipWindow.showToolTip(holder.ivHelpPolicy, activity.getString(R.string.text_write_down_the_rule_here));
                } else {
                    tipWindow.dismissTooltip();
                }
            }
        });


        holder.tvRemovePolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items.size()==1){
                    holder.etPolicesName.setText("");
                    holder.acThePolices.setText("");
                }else {
                    items.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
    }

    public List<CustomPolicyItem> getDataPolicy() {
        return items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextInputEditText etPolicesName;
        private AutoCompleteTextView acThePolices;
        private TextView tvRemovePolicy;
        private ImageView ivHelpTitlePolicy, ivHelpPolicy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            etPolicesName = itemView.findViewById(R.id.et_policy_name);
            acThePolices = itemView.findViewById(R.id.autoComplete);
            tvRemovePolicy = itemView.findViewById(R.id.tvRemovePolicy);
            ivHelpPolicy = itemView.findViewById(R.id.ivHelpPolicy);
            ivHelpTitlePolicy = itemView.findViewById(R.id.ivHelpTitlePolicy);
            tvRemovePolicy.setVisibility(View.VISIBLE);
        }

        public void bind(final CustomPolicyItem item) {
            etPolicesName.setText(item.getPolicyName());
            acThePolices.setText(item.getPolicy());
        }
    }
}
