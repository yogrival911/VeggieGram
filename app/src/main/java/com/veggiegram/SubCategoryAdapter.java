package com.veggiegram;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.veggiegram.responses.productlistcat.ProductListByCatResponse;
import com.veggiegram.responses.subcat.SubCategoryResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubViewHolder> {
    SubCategoryResponse subCategoryResponse;
    ClickInterface clickInterface;
    Boolean selectedTab = true;

    public SubCategoryAdapter(SubCategoryResponse subCategoryResponse, ClickInterface clickInterface) {
        this.subCategoryResponse = subCategoryResponse;
        this.clickInterface = clickInterface;
    }

    @NonNull
    @Override
    public SubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_layout, parent,false);
        return new SubCategoryAdapter.SubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubViewHolder holder, int position) {
        holder.subCatName.setText(subCategoryResponse.getData().get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickInterface.click(position, subCategoryResponse.getData().get(position).getId());
            }
        });


    }

    @Override
    public int getItemCount() {
        return subCategoryResponse.getData().size();
    }

    public class SubViewHolder extends RecyclerView.ViewHolder {
        TextView subCatName;
        public SubViewHolder(@NonNull View itemView) {
            super(itemView);
            subCatName = itemView.findViewById(R.id.subCatName);
        }
    }
}
