package com.veggiegram.ui.home;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_layout, parent, false);
        return new RecommendedAdapter.RecommendedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedViewHolder holder, int position) {
        String img = recommededProductResponse.getData().get(position).getImage();
        String url = "https://admin.veggiegram.in/adminuploads/products/" + img;
        LoadWithGlide.loadImage(holder.ivRec,url,new CircularProgressDrawable(holder.itemView.getContext()));

        holder.tvName.setText(recommededProductResponse.getData().get(position).getName());
        int price = recommededProductResponse.getData().get(position).getPrice();
        int sellPrice = recommededProductResponse.getData().get(position).getSellprice();
        int saving = price - sellPrice;

        if(saving>0){
            holder.tvSave.setText("You Save " + "\u20B9"+ saving);
        }

        holder.tvPrice.setText("\u20B9" + price);
        holder.tvSellPrice.setText("\u20B9" + sellPrice);
        holder.tvQuantity.setText(recommededProductResponse.getData().get(position).getUnit()+recommededProductResponse.getData().get(position).getUnitname());
        holder.tvPrice.setPaintFlags(holder.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String product_id = recommededProductResponse.getData().get(position).getProductid().toString();
                NavController navController = Navigation.findNavController(holder.itemView);
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(product_id));
            }
        });
    }

    @Override
    public int getItemCount() {
        return recommededProductResponse.getData().size();
    }

    public class RecommendedViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRec;
        TextView tvName, tvSave, tvQuantity,tvSellPrice, tvPrice;
        public RecommendedViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRec = itemView.findViewById(R.id.pImage);
            tvName = itemView.findViewById(R.id.pName);
            tvSave = itemView.findViewById(R.id.tvSave);
            tvQuantity = itemView.findViewById(R.id.pQuantity);
            tvSellPrice = itemView.findViewById(R.id.pSellPrice);
            tvPrice = itemView.findViewById(R.id.tvPrice);

        }
    }
}
