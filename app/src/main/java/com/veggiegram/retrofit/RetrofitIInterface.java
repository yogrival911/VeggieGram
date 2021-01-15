package com.veggiegram.retrofit;

import com.veggiegram.responses.banner.BannerResponse;
import com.veggiegram.responses.category.CategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitIInterface {
    @GET("getbannerlist")
    Call<BannerResponse> getBannerList();

    @GET("getcategorylist")
    Call<CategoryResponse> getCategoryList();
}
