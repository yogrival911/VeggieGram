package com.veggiegram;

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

import com.veggiegram.responses.productlistcat.ProductListByCatResponse;
import com.veggiegram.ui.home.HomeFragmentDirections;
import com.veggiegram.util.LoadWithGlide;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.PLViewHolder> {
ProductListByCatResponse productListByCatResponse;

    public ProductListAdapter(ProductListByCatResponse productListByCatResponse) {
        this.productListByCatResponse = productListByCatResponse;
    }

    @NonNull
    @Override
    public ProductListAdapter.PLViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);

        return new ProductListAdapter.PLViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.PLViewHolder holder, int position) {

        holder.productName.setText(productListByCatResponse.getData().get(position).getName());
        holder.quantity.setText(productListByCatResponse.getData().get(position).getUnit()+" "+ productListByCatResponse.getData().get(position).getUnitname());
        holder.price.setText(" " +productListByCatResponse.getData().get(position).getPrice());

        String img = productListByCatResponse.getData().get(position).getImage();
        String url = "https://admin.veggiegram.in/adminuploads/products/" + img;

        LoadWithGlide.loadImage(holder.ivProductImage,url,new CircularProgressDrawable(holder.itemView.getContext()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(holder.itemView);
                navController.navigate(ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(productListByCatResponse.getData().get(position).getProductid().toString()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return productListByCatResponse.getData().size();
    }

    public class PLViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;
        TextView productName, quantity, price;
        public PLViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            productName = itemView.findViewById(R.id.productName);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
        }
    }
}
