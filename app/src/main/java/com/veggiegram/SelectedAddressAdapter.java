package com.veggiegram;

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
    private int selectedPosition = -1;

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
        holder.selectedAddress.setChecked(selectedPosition == position);
        holder.selectedAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.selectedAddress.setChecked(true);
                clickInterface.click(position, position);
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

        public SViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvLand = itemView.findViewById(R.id.tvLand);
            selectedAddress = itemView.findViewById(R.id.selectedAddress);

        }
    }
}
