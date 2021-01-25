package com.veggiegram;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.veggiegram.responses.address.AddressResponse;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    AddressResponse addressResponse;
    ClickInterface clickInterface;

    public AddressAdapter(AddressResponse addressResponse, ClickInterface clickInterface) {
        this.addressResponse = addressResponse;
        this.clickInterface = clickInterface;
    }

    @NonNull
    @Override
    public AddressAdapter.AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item_layout, parent, false);

        return new AddressAdapter.AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.AddressViewHolder holder, int position) {

        holder.removeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickInterface.clickRemoveAddress(position,addressResponse.getData().get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressResponse.getData().size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout removeAddress;
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            removeAddress = itemView.findViewById(R.id.deleteAddress);
        }
    }
}
