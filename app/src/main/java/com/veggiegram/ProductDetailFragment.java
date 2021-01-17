package com.veggiegram;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.veggiegram.responses.RemoveWishListResponse;
import com.veggiegram.responses.WishListObject;
import com.veggiegram.responses.productdetail.ProductDetailResponse;
import com.veggiegram.responses.wishlist.WishListResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;
import com.veggiegram.util.LoadWithGlide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ProductDetailFragment extends Fragment {
ImageView productImage,fav_icon;
LinearLayout addToWishList;
SharedPreferences sharedPreferences;
LinearLayout addToCart;
int wishlisted_in;
    public ProductDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = sharedPreferences.getString("user_id","");

        NavHostFragment navHostFragment =(NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host);

        productImage = view.findViewById(R.id.productImage);
        addToWishList = view.findViewById(R.id.addToWishList);
        fav_icon = view.findViewById(R.id.fav_icon);
        addToCart = view.findViewById(R.id.addToCart);

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        String product_id = ProductDetailFragmentArgs.fromBundle(getArguments()).getProductId();
        Log.i("yog", "user id "+user_id);

        retrofitIInterface.getproductpetailsbyid(product_id, user_id).enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                String imgUrl = "https://admin.veggiegram.in/adminuploads/products/" + response.body().getData().get(0).getImage();
                LoadWithGlide.loadImage(productImage,imgUrl, new CircularProgressDrawable(getContext()));
                wishlisted_in = response.body().getData().get(0).getWhishlisted();
                if(wishlisted_in>0){
                    fav_icon.setImageResource(R.drawable.red_heart);
                }
            }

            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {

            }
        });

        addToWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user_id.isEmpty()){
                    navHostFragment.getNavController().navigate(ProductDetailFragmentDirections.actionProductDetailFragmentToSigninFragment());
                }
                else{
                   if(wishlisted_in>0){
                       //proceed to remove wishlist
                       fav_icon.setImageResource(R.drawable.ic_baseline_favorite_border_24);

                       Retrofit retrofit = RetrofitClientInstance.getInstance();
                       RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

                       retrofitIInterface.removeWishList(new WishListObject(product_id),user_id).enqueue(new Callback<RemoveWishListResponse>() {
                           @Override
                           public void onResponse(Call<RemoveWishListResponse> call, Response<RemoveWishListResponse> response) {
                               if(response.body().getSuccess()){

                                   Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }

                           @Override
                           public void onFailure(Call<RemoveWishListResponse> call, Throwable t) {

                           }
                       });

                   }
                   else{
                       //proceed to add to wishlist
                       fav_icon.setImageResource(R.drawable.red_heart);
                       Retrofit retrofit = RetrofitClientInstance.getInstance();
                       RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);
                       retrofitIInterface.addToWishList(new WishListObject(product_id),user_id).enqueue(new Callback<WishListResponse>() {
                           @Override
                           public void onResponse(Call<WishListResponse> call, Response<WishListResponse> response) {
                               WishListResponse wishListResponse = response.body();
                               Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                           }

                           @Override
                           public void onFailure(Call<WishListResponse> call, Throwable t) {

                           }
                       });
                   }

                }
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment navHostFragment =(NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host);
                navHostFragment.getNavController().navigate(ProductDetailFragmentDirections.actionProductDetailFragmentToCartFragment());
            }
        });

        return  view;
    }
}