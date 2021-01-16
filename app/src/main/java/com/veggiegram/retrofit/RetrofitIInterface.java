package com.veggiegram.retrofit;

import com.veggiegram.responses.SigninObject;
import com.veggiegram.responses.SignupObject;
import com.veggiegram.responses.banner.BannerResponse;
import com.veggiegram.responses.category.CategoryResponse;
import com.veggiegram.responses.login.LoginResponse;
import com.veggiegram.responses.otp.OTPResponse;
import com.veggiegram.responses.otp.SendOTPObject;
import com.veggiegram.responses.productlistcat.ProductListByCatResponse;
import com.veggiegram.responses.recommended.RecommededProductResponse;
import com.veggiegram.responses.signup.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitIInterface {
    @GET("getbannerlist")
    Call<BannerResponse> getBannerList();

    @GET("getcategorylist")
    Call<CategoryResponse> getCategoryList();

    @POST("signin")
    Call<LoginResponse> signin(@Body SigninObject signinObject);

    @POST("sendotp")
    Call<OTPResponse> sendOtp(@Body SendOTPObject sendOTPObject);

    @POST("signup")
    Call<SignupResponse> signup(@Body SignupObject signupObject);

    @GET("getrecommendedproductslist")
    Call<RecommededProductResponse> getrecommendedproductslist();

    @GET("getproductslistbycatid/{position}")
    Call<ProductListByCatResponse> getProductslistByCatID(@Path("position") String position);


}
