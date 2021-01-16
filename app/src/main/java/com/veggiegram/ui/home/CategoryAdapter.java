package com.veggiegram.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.veggiegram.R;
import com.veggiegram.responses.category.CategoryResponse;
import com.veggiegram.util.LoadWithGlide;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    CategoryResponse categoryResponse;


    public CategoryAdapter(CategoryResponse categoryResponse) {
        this.categoryResponse = categoryResponse;

    }

    public void setResponse(CategoryResponse categoryResponse){
        this.categoryResponse = categoryResponse;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_thumbnail, parent,false);

        return new CategoryAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        String imgUrl = "https://admin.veggiegram.in/adminuploads/category/" + categoryResponse.getData().get(position).getImage();
        LoadWithGlide.loadImage(holder.categoryThubnail,imgUrl, new CircularProgressDrawable(holder.categoryThubnail.getContext()));

        Log.i("yogesh", imgUrl);
        holder.categoryName.setText(categoryResponse.getData().get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(holder.itemView);
                navController.navigate(HomeFragmentDirections.actionHomeFragmentToProductListFragment(position));
            }
        });


//        holder.itemView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_productListFragment));
    }

    @Override
    public int getItemCount() {
        return categoryResponse.getData().size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryThubnail;
        TextView categoryName;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryThubnail = itemView.findViewById(R.id.categoryThubnail);
            categoryName = itemView.findViewById(R.id.categoryName);
        }
    }
}
