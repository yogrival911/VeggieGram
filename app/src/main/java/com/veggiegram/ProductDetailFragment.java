package com.veggiegram;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.veggiegram.responses.productdetail.ProductDetailResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;
import com.veggiegram.util.LoadWithGlide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ProductDetailFragment extends Fragment {
ImageView productImage;
    public ProductDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        productImage = view.findViewById(R.id.productImage);

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        String product_id = ProductDetailFragmentArgs.fromBundle(getArguments()).getProductId();
        Log.i("yog", "product_id"+product_id);

        retrofitIInterface.getproductpetailsbyid(product_id).enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                String imgUrl = "https://admin.veggiegram.in/adminuploads/products/" + response.body().getData().get(0).getImage();
                LoadWithGlide.loadImage(productImage,imgUrl, new CircularProgressDrawable(getContext()));

            }

            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {

            }
        });

        return  view;
    }
}