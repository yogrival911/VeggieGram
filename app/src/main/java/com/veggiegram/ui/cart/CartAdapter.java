package com.veggiegram.ui.cart;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.veggiegram.ClickCartInterface;
import com.veggiegram.R;
import com.veggiegram.responses.cartlist.GetCartListResponse;
import com.veggiegram.util.LoadWithGlide;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    GetCartListResponse cartListResponse;
    ClickCartInterface clickCartInterface;

    public CartAdapter(GetCartListResponse cartListResponse, ClickCartInterface clickCartInterface) {
        this.cartListResponse = cartListResponse;
        this.clickCartInterface = clickCartInterface;
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
        holder.sellPrice.setText("\u20B9"+cartListResponse.getData().get(position).getSellprice().toString());
        holder.etQuantity.setText(cartListResponse.getData().get(position).getCartquantity().toString());
        holder.quantity.setText(cartListResponse.getData().get(position).getUnit()+ " " + cartListResponse.getData().get(position).getUnitname());
        holder.tvPrice.setText("\u20B9"+cartListResponse.getData().get(position).getPrice());
        holder.tvPrice.setPaintFlags(holder.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        int cartQuantity = cartListResponse.getData().get(position).getCartquantity();
        int productid = cartListResponse.getData().get(position).getProductid();
        int sellPrice = cartListResponse.getData().get(position).getSellprice();
        holder.cartIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCartInterface.increment(position,cartQuantity,String.valueOf(productid), sellPrice);
            }
        });
        holder.cartDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCartInterface.decrement(position,cartQuantity,String.valueOf(productid), sellPrice);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartListResponse.getData().size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView cartImage,removePro,cartIncrement,cartDecrement;
        TextView cartItemName,sellPrice;
        TextView etQuantity,tvPrice,quantity;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartImage = itemView.findViewById(R.id.proImage);
            cartItemName = itemView.findViewById(R.id.proName);
            sellPrice = itemView.findViewById(R.id.sellPrice);
            etQuantity = itemView.findViewById(R.id.tvQuanity);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            quantity = itemView.findViewById(R.id.tvQuanityUnit);
            cartIncrement = itemView.findViewById(R.id.cartIncrement);
            cartDecrement = itemView.findViewById(R.id.cartDecrement);

//            removePro = itemView.findViewById(R.id.removePro);
        }
    }
}
