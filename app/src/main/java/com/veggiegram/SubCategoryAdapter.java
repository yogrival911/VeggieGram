package com.veggiegram;

import android.content.SharedPreferences;
import android.graphics.Color;
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
    private int checkedPosition = 0;

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

        if (checkedPosition == -1) {
            holder.subCatName.setBackgroundColor(Color.WHITE);
        } else {
            if (checkedPosition == holder.getAdapterPosition()) {
                holder.subCatName.setBackgroundResource(R.drawable.subcategory_background);
            } else {
                holder.subCatName.setBackgroundColor(Color.WHITE);
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.subCatName.setBackgroundResource(R.drawable.subcategory_background);
                if (checkedPosition != holder.getAdapterPosition()) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = holder.getAdapterPosition();
                }

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
