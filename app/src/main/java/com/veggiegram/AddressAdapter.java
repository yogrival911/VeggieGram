package com.veggiegram;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.veggiegram.responses.address.AddressResponse;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    AddressResponse addressResponse;

    public AddressAdapter(AddressResponse addressResponse) {
        this.addressResponse = addressResponse;
    }

    @NonNull
    @Override
    public AddressAdapter.AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item_layout, parent, false);

        return new AddressAdapter.AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.AddressViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return addressResponse.getData().size();
    }

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
