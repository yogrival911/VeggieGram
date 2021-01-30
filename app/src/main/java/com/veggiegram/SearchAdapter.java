package com.veggiegram;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.veggiegram.responses.productlistcat.ProductListByCatResponse;
import com.veggiegram.util.LoadWithGlide;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    ProductListByCatResponse productListByCatResponse;
    int wishlisted=0;
    public SearchAdapter(ProductListByCatResponse productListByCatResponse) {
        this.productListByCatResponse = productListByCatResponse;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.productName.setText(productListByCatResponse.getData().get(position).getName());
        holder.quantity.setText(productListByCatResponse.getData().get(position).getUnit()+" "+ productListByCatResponse.getData().get(position).getUnitname());

        float price = productListByCatResponse.getData().get(position).getPrice();
        float sellPrice = productListByCatResponse.getData().get(position).getSellprice();

        float saving = price-sellPrice;
        if(saving>0){
            holder.pSave.setText("You Save " + "\u20B9"+ Math.round(saving));
        }

        holder.price.setText("\u20B9" + Math.round(sellPrice));
        holder.pActualPrice.setText("\u20B9"+ Math.round(price));
        holder.pActualPrice.setPaintFlags(holder.pActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        wishlisted = productListByCatResponse.getData().get(position).getWhishlisted();
        if(wishlisted>0){
            holder.fav.setImageResource(R.drawable.ic_baseline_favorite_24);
        }
        else{
            holder.fav.setImageResource(R.drawable.grey_fav);
        }

        String img = productListByCatResponse.getData().get(position).getImage();
        String url = "https://admin.veggiegram.in/adminuploads/products/" + img;

        LoadWithGlide.loadImage(holder.ivProductImage,url,new CircularProgressDrawable(holder.itemView.getContext()));

        int cartQuantity = productListByCatResponse.getData().get(position).getCartquantity();
        String productId = productListByCatResponse.getData().get(position).getProductid().toString();
        int sellPriceInt = Math.round(sellPrice);
        if(cartQuantity>0){
            holder.addCartButton.setVisibility(View.GONE);
            holder.quantityLayout.setVisibility(View.VISIBLE);
            holder.cartQuantity.setText(String.valueOf(productListByCatResponse.getData().get(position).getCartquantity()));
        }
        else{
            holder.addCartButton.setVisibility(View.VISIBLE);
            holder.quantityLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return productListByCatResponse.getData().size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage,fav;
        TextView productName, quantity, price, pActualPrice,pSave;
        Button addCartButton;
        ImageView incrementButton, decrementButton;
        ConstraintLayout quantityLayout;
        TextView cartQuantity;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.pImage);
            productName = itemView.findViewById(R.id.pName);
            quantity = itemView.findViewById(R.id.pQuantity);
            price = itemView.findViewById(R.id.pSellPrice);
            pActualPrice = itemView.findViewById(R.id.pPrice);
            fav = itemView.findViewById(R.id.fav);
            pSave = itemView.findViewById(R.id.pSave);

            addCartButton = itemView.findViewById(R.id.cartButton);
            quantityLayout = itemView.findViewById(R.id.quanity_layout);
            incrementButton = itemView.findViewById(R.id.increment);
            decrementButton = itemView.findViewById(R.id.decrement);
            cartQuantity = itemView.findViewById(R.id.cartQua);

        }
    }
}
