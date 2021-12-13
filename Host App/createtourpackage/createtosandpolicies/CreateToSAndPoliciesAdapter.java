package com.aniqma.lokaventour.host.modul.activity.tourmanagement.createtourpackage.createtosandpolicies;

import android.content.Context;
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

public class CreateToSAndPoliciesAdapter extends RecyclerView.Adapter<CreateToSAndPoliciesAdapter.v_holder> {
    private ArrayList<CustomPolicyItem> items;
    private Context mContext;
    private TooltipWindowUtils tipWindow;

    public CreateToSAndPoliciesAdapter(ArrayList<CustomPolicyItem> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
        tipWindow = new TooltipWindowUtils(mContext);
    }

    @NonNull
    @Override
    public v_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_add_policy, parent, false);
        return new v_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final v_holder holder, final int position) {

        holder.et_policy_name.setText(items.get(position).policyName);
        holder.autoComplete.setText(items.get(position).policy);

        if (items.size() == 1){
            holder.tvRemovePolicy.setVisibility(View.GONE);
        } else {
            holder.tvRemovePolicy.setVisibility(View.VISIBLE);
        }

        holder.et_policy_name.addTextChangedListener(new TextWatcher() {
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

        holder.autoComplete.addTextChangedListener(new TextWatcher() {
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
                    tipWindow.showToolTip(holder.ivHelpTitlePolicy, mContext.getString(R.string.text_the_title_of_rule_you_might_need_to_mention_to_guest));
                } else {
                    tipWindow.dismissTooltip();
                }
            }
        });

        holder.ivHelpPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tipWindow.isTooltipShown()){
                    tipWindow.showToolTip(holder.ivHelpPolicy, mContext.getString(R.string.text_write_down_the_rule_here));
                } else {
                    tipWindow.dismissTooltip();
                }
            }
        });

        holder.tvRemovePolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class v_holder extends RecyclerView.ViewHolder {
        private TextInputEditText et_policy_name;
        private AutoCompleteTextView autoComplete;
        private TextView tvRemovePolicy;
        private ImageView ivHelpTitlePolicy, ivHelpPolicy;
        public v_holder(@NonNull View itemView) {
            super(itemView);
            init(itemView);
        }

        public void bind(final CustomPolicyItem item) {

        }
        private void init(View v) {
            et_policy_name = v.findViewById(R.id.et_policy_name);
            autoComplete = v.findViewById(R.id.autoComplete);
            tvRemovePolicy = v.findViewById(R.id.tvRemovePolicy);
            ivHelpPolicy = v.findViewById(R.id.ivHelpPolicy);
            ivHelpTitlePolicy = v.findViewById(R.id.ivHelpTitlePolicy);
        }
    }

    public ArrayList<CustomPolicyItem> getItemPosition(){
        notifyDataSetChanged();
        return items;
    }

}
