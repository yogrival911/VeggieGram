package com.veggiegram.responses.wishlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.veggiegram.R;
import com.veggiegram.util.LoadWithGlide;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WLViewHolder> {
    GetWishListResponse getWishListResponse;

    public WishListAdapter(GetWishListResponse getWishListResponse) {
        this.getWishListResponse = getWishListResponse;
    }

    @NonNull
    @Override
    public WLViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout, parent, false);

        return new WishListAdapter.WLViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WLViewHolder holder, int position) {
        String imgUrl = "https://admin.veggiegram.in/adminuploads/products/" + getWishListResponse.getData().get(position).getImage();
        LoadWithGlide.loadImage(holder.wishListItem,imgUrl, new CircularProgressDrawable(holder.itemView.getContext()));

    }

    @Override
    public int getItemCount() {
        return getWishListResponse.getData().size();
    }

    public class WLViewHolder extends RecyclerView.ViewHolder {
        ImageView wishListItem;
        public WLViewHolder(@NonNull View itemView) {
            super(itemView);
            wishListItem = itemView.findViewById(R.id.wishListItem);
        }
    }
}
