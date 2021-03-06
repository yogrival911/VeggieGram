package com.veggiegram.retrofit;

import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.veggiegram.SearchObject;
import com.veggiegram.responses.AddAddressObject;
import com.veggiegram.responses.AddToCartObject;
import com.veggiegram.responses.RemoveAddressObject;
import com.veggiegram.responses.RemoveCartObject;
import com.veggiegram.responses.RemoveWishListResponse;
import com.veggiegram.responses.SigninObject;
import com.veggiegram.responses.SignupObject;
import com.veggiegram.responses.WishListObject;
import com.veggiegram.responses.addaddress.AddAddressResponse;
import com.veggiegram.responses.addorder.AddOrderObject;
import com.veggiegram.responses.addorder.AddOrderResponse;
import com.veggiegram.responses.address.AddressResponse;
import com.veggiegram.responses.addtocart.AddToCartResponse;
import com.veggiegram.responses.addwallet.AddWalletObject;
import com.veggiegram.responses.addwallet.AddWalletResponse;
import com.veggiegram.responses.banner.BannerResponse;
import com.veggiegram.responses.cartlist.GetCartListResponse;
import com.veggiegram.responses.category.CategoryResponse;
import com.veggiegram.responses.login.LoginResponse;
import com.veggiegram.responses.namelist.ProductNameResponse;
import com.veggiegram.responses.order.OrderResponse;
import com.veggiegram.responses.otp.OTPResponse;
import com.veggiegram.responses.otp.SendOTPObject;
import com.veggiegram.responses.productdetail.ProductDetailResponse;
import com.veggiegram.responses.productlistcat.ProductListByCatResponse;
import com.veggiegram.responses.recommended.RecommededProductResponse;
import com.veggiegram.responses.removeaddress.RemoveAddressResponse;
import com.veggiegram.responses.removecart.RemoveCartResponse;
import com.veggiegram.responses.signup.SignupResponse;
import com.veggiegram.responses.slot.SlotResponse;
import com.veggiegram.responses.subcat.SubCategoryResponse;
import com.veggiegram.responses.wallet.WalletResponse;
import com.veggiegram.responses.wallethistory.WalletHistoryResponse;
import com.veggiegram.responses.wishlist.GetWishListResponse;
import com.veggiegram.responses.wishlist.WishListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    Call<ProductListByCatResponse> getProductslistByCatID(@Path("position") String position,
                                                          @Header("token") String token);

    @GET("getproductpetailsbyid/{product_id}")
    Call<ProductDetailResponse> getproductpetailsbyid(@Path("product_id") String product_id,
                                                      @Header("token") String token);

    @POST("addtowishlist")
    Call<WishListResponse> addToWishList(
            @Body WishListObject wishListObject,
            @Header("token") String token);

    @GET("getuserwishlistproducts")
    Call<GetWishListResponse> getWishList(@Header("token") String token);

    @POST("removewishlist")
    Call<RemoveWishListResponse> removeWishList(@Body WishListObject wishListObject,
                                                @Header("token") String token);

    @GET("getusercartlistproducts")
    Call<GetCartListResponse> getusercartlistproducts(@Header("token") String token);

    @POST("addtocart")
    Call<AddToCartResponse> addToCart(@Body AddToCartObject addToCartObject,
                                      @Header("token") String token);

    @GET("getuseraddresslist")
    Call<AddressResponse> getUserAddressList(@Header("token") String token);

    @POST("removecart")
    Call<RemoveCartResponse> removeCartProduct(@Body RemoveCartObject removeCartObject,
                                               @Header("token") String token);

    @GET("Getsubcategorylistbycatid/{position}")
    Call<SubCategoryResponse> getSubCatByCatID(@Path("position") String position);

    @GET("getproductslistbysubcatid/{position}")
    Call<ProductListByCatResponse> getProductbySubCatID(@Path("position") int position,
                                                        @Header("token") String token);

    @POST("addaddress")
    Call<AddAddressResponse> addNewAddress(@Body AddAddressObject addAddressObject,
                                           @Header("token") String token);

    @POST("removeaddress")
    Call<RemoveAddressResponse> removeAddress(@Body RemoveAddressObject removeAddressObject,
                                              @Header("token") String token);

    @GET("getroductsnamelist")
    Call<ProductNameResponse> getProductNameList();

    @POST("searchproduct")
    Call<ProductListByCatResponse> getSearchResult(@Body SearchObject searchObject,
                                                   @Header("token") String token);

    @GET("getuserorderlist")
    Call<OrderResponse> getUserOrderList(@Header("token") String token);

    @GET("getdeliveryslot")
    Call<SlotResponse> getDeliverySlot(@Header("token") String token);

    @GET("getuserwallet")
    Call<WalletResponse> getUserWallet(@Header("token") String token);

    @POST("addorder")
    Call<AddOrderResponse> addOrder(@Body JsonObject jsonObject,
                                    @Header("token") String token);

    @POST("addwallet")
    Call<AddWalletResponse> addWallet(@Body AddWalletObject addWalletObject,
                                    @Header("token") String token);

    @GET("getuserwallethistory")
    Call<WalletHistoryResponse> walletHistory(@Header("token") String token);


}
