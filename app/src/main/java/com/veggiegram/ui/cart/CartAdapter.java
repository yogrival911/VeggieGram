package com.veggiegram.ui.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.veggiegram.R;
import com.veggiegram.responses.cartlist.GetCartListResponse;
import com.veggiegram.util.LoadWithGlide;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    GetCartListResponse cartListResponse;

    public CartAdapter(GetCartListResponse cartListResponse) {
        this.cartListResponse = cartListResponse;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent,false);
        return new CartAdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        String imgUrl = "https://admin.veggiegram.in/adminuploads/products/" + cartListResponse.getData().get(position).getImage();
        LoadWithGlide.loadImage(holder.cartImage,imgUrl, new CircularProgressDrawable(holder.cartImage.getContext()));

        holder.cartItemName.setText(cartListResponse.getData().get(position).getName());
        holder.sellPrice.setText(cartListResponse.getData().get(position).getSellprice().toString());
        holder.etQuantity.setText(cartListResponse.getData().get(position).getCartquantity().toString());

    }

    @Override
    public int getItemCount() {
        return cartListResponse.getData().size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView cartImage;
        TextView cartItemName,sellPrice;
        EditText etQuantity;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartImage = itemView.findViewById(R.id.proImage);
            cartItemName = itemView.findViewById(R.id.proName);
            sellPrice = itemView.findViewById(R.id.sellPrice);
            etQuantity = itemView.findViewById(R.id.etQuantity);
        }
    }
}
