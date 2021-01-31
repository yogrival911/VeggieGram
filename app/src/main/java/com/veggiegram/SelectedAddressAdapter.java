package com.veggiegram;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.veggiegram.responses.address.AddressResponse;

public class SelectedAddressAdapter extends RecyclerView.Adapter<SelectedAddressAdapter.SViewHolder> {
    AddressResponse addressResponse;
    ClickInterface clickInterface;
    private int checkedPosition = -1;

    public SelectedAddressAdapter(AddressResponse addressResponse, ClickInterface clickInterface) {
        this.addressResponse = addressResponse;
        this.clickInterface = clickInterface;
    }

    @NonNull
    @Override
    public SViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_address_layout, parent, false);
        return new SelectedAddressAdapter.SViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SViewHolder holder, int position) {
        if (checkedPosition == -1) {
            holder.constraintLayout.setBackgroundColor(Color.WHITE);
            holder.selectedAddress.setChecked(false);
        } else {
            if (checkedPosition == holder.getAdapterPosition()) {
                holder.constraintLayout.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.addressColor));
                holder.selectedAddress.setChecked(true);
            } else {
                holder.constraintLayout.setBackgroundColor(Color.WHITE);
                holder.selectedAddress.setChecked(false);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.constraintLayout.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.addressColor));
                holder.selectedAddress.setChecked(true);
                if (checkedPosition != holder.getAdapterPosition()) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = holder.getAdapterPosition();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressResponse.getData().size();
    }

    public class SViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvAddress,tvLand;
        CheckBox selectedAddress;
        ConstraintLayout constraintLayout;
        public SViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvLand = itemView.findViewById(R.id.tvLand);
            selectedAddress = itemView.findViewById(R.id.selectedAddress);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
}
