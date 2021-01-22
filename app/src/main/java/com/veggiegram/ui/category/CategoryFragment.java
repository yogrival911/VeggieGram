package com.veggiegram.ui.category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veggiegram.R;
import com.veggiegram.responses.category.CategoryResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;
import com.veggiegram.ui.home.CategoryAdapter;
import com.veggiegram.util.CircleProgressBarCustom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryFragment extends Fragment {
RecyclerView categoryRecyclerView;
CircleProgressBarCustom circlePro;
Boolean fromHome = false;
    public CategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_category, container, false);

        circlePro = view.findViewById(R.id.circlePro);
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        categoryRecyclerView.setLayoutManager(gridLayoutManager);
        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);
        retrofitIInterface.getCategoryList().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                circlePro.clearAnimation();
                circlePro.setVisibility(View.INVISIBLE);
                CategoryAdapter categoryAdapter = new CategoryAdapter(response.body(),fromHome);
                categoryRecyclerView.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });
        return view;
    }
}