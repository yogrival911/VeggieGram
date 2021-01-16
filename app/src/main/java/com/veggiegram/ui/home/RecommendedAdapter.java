package com.veggiegram.ui.home;

import android.graphics.Paint;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.veggiegram.R;
import com.veggiegram.responses.recommended.RecommededProductResponse;
import com.veggiegram.util.LoadWithGlide;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder> {
    RecommededProductResponse recommededProductResponse;

    public RecommendedAdapter(RecommededProductResponse recommededProductResponse) {
        this.recommededProductResponse = recommededProductResponse;
    }

    @NonNull
    @Override
    public RecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
        return new RecommendedAdapter.RecommendedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedViewHolder holder, int position) {
        String img = recommededProductResponse.getData().get(position).getImage();
        String url = "https://admin.veggiegram.in/adminuploads/products/" + img;
        LoadWithGlide.loadImage(holder.ivProductItem,url,new CircularProgressDrawable(holder.ivProductItem.getContext()));

        holder.tvProductPrice.setPaintFlags(holder.tvProductPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvProductPrice.setText(recommededProductResponse.getData().get(position).getPrice().toString());
        holder.tvProductSellPrice.setText(recommededProductResponse.getData().get(position).getSellprice().toString());
    }

    @Override
    public int getItemCount() {
        return recommededProductResponse.getData().size();
    }

    public class RecommendedViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductItem;
        TextView tvProductName, tvProductPrice, tvProductSellPrice;
        public RecommendedViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductItem = itemView.findViewById(R.id.ivWishListItem);
            tvProductName = itemView.findViewById(R.id.tvWishListItemName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductSellPrice = itemView.findViewById(R.id.tvWishListItemPrice);

        }
    }
}
