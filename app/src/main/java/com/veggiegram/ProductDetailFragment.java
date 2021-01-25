package com.veggiegram;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.veggiegram.responses.AddToCartObject;
import com.veggiegram.responses.RemoveCartObject;
import com.veggiegram.responses.RemoveWishListResponse;
import com.veggiegram.responses.WishListObject;
import com.veggiegram.responses.productdetail.ProductDetailResponse;
import com.veggiegram.responses.removecart.RemoveCartResponse;
import com.veggiegram.responses.wishlist.GetWishListResponse;
import com.veggiegram.responses.wishlist.WishListResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;
import com.veggiegram.util.LoadWithGlide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ProductDetailFragment extends Fragment {
ImageView productImage,ivDec,ivIncrement;
LinearLayout addToWishList;
String productID;
SharedPreferences sharedPreferences;
LinearLayout addToCart;
TextView tvDetailName,tvDetailQuantity,tvDetailSellPrice,tvDetailPrice,tvDetailSaving,tvCount;
Button addButton,viewCart;
int cartQuantity;
int wishlisted_in;
ConstraintLayout increDecreLayout;
TextView tvAddToCart, textCartItemCount;
    int mCartItemCount = 0;
    public ProductDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        tvDetailName = view.findViewById(R.id.tvDetailName);
        tvDetailQuantity = view.findViewById(R.id.tvDetailQuantity);
        tvDetailSellPrice = view.findViewById(R.id.tvDetailSellPrice);
        tvDetailPrice = view.findViewById(R.id.tvDetailPrice);
        tvDetailSaving = view.findViewById(R.id.tvDetailSaving);
        tvCount = view.findViewById(R.id.tvCount);
        ivDec = view.findViewById(R.id.ivDec);
        ivIncrement = view.findViewById(R.id.ivIncrement);
        increDecreLayout = view.findViewById(R.id.increDecreLayout);
        addButton = view.findViewById(R.id.addButton);
        viewCart = view.findViewById(R.id.viewCart);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = sharedPreferences.getString("user_id","");

        productImage = view.findViewById(R.id.productImage);

        setHasOptionsMenu(true);

        mCartItemCount = 3;

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        String product_id = ProductDetailFragmentArgs.fromBundle(getArguments()).getProductId();
        Log.i("yog", "user id "+user_id);

        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });

        ivDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartQuantity = cartQuantity-1;
                tvCount.setText(String.valueOf(cartQuantity));
                if(cartQuantity<1){
                    increDecreLayout.setVisibility(View.GONE);
                    addButton.setVisibility(View.VISIBLE);
                    retrofitIInterface.removeCartProduct(new RemoveCartObject(product_id),user_id).enqueue(new Callback<RemoveCartResponse>() {
                        @Override
                        public void onResponse(Call<RemoveCartResponse> call, Response<RemoveCartResponse> response) {

                        }

                        @Override
                        public void onFailure(Call<RemoveCartResponse> call, Throwable t) {

                        }
                    });
                }
                else{
                    tvCount.setText(String.valueOf(cartQuantity));
                    retrofitIInterface.addToCart(new AddToCartObject(product_id,String.valueOf(cartQuantity)),user_id).enqueue(new Callback<GetWishListResponse>() {
                        @Override
                        public void onResponse(Call<GetWishListResponse> call, Response<GetWishListResponse> response) {

                        }

                        @Override
                        public void onFailure(Call<GetWishListResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });

        ivIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartQuantity = cartQuantity+1;
                tvCount.setText(String.valueOf(cartQuantity));
                retrofitIInterface.addToCart(new AddToCartObject(product_id, String.valueOf(cartQuantity)),user_id).enqueue(new Callback<GetWishListResponse>() {
                    @Override
                    public void onResponse(Call<GetWishListResponse> call, Response<GetWishListResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<GetWishListResponse> call, Throwable t) {

                    }
                });
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(user_id.equals("")){
                    //user not signed in
                    NavHostFragment navHostFragment =(NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host);
                    navHostFragment.getNavController().navigate(ProductDetailFragmentDirections.actionProductDetailFragmentToSigninFragment());

                }
                else{
                    addButton.setVisibility(View.GONE);
                    increDecreLayout.setVisibility(View.VISIBLE);
                    tvCount.setText("1");
                    cartQuantity = 1;
                    retrofitIInterface.addToCart(new AddToCartObject(product_id,String.valueOf(cartQuantity)),user_id).enqueue(new Callback<GetWishListResponse>() {
                        @Override
                        public void onResponse(Call<GetWishListResponse> call, Response<GetWishListResponse> response) {
                            Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<GetWishListResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });

        retrofitIInterface.getproductpetailsbyid(product_id, user_id).enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {

                int price = response.body().getData().get(0).getPrice();
                int sellPrice = response.body().getData().get(0).getSellprice();
                cartQuantity = response.body().getData().get(0).getCartquantity();
                tvDetailName.setText(response.body().getData().get(0).getName());
                tvDetailQuantity.setText(response.body().getData().get(0).getUnit() + response.body().getData().get(0).getUnitname());
                tvDetailSellPrice.setText("\u20B9"+sellPrice);
                tvDetailPrice.setText("\u20B9"+price);
                tvDetailPrice.setPaintFlags(tvDetailPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                int saving = price - sellPrice;

                if(saving>0){
                    tvDetailSaving.setText("You save "+"\u20B9" + saving);
                }

                if(cartQuantity>0){
                    increDecreLayout.setVisibility(View.VISIBLE);
                    addButton.setVisibility(View.GONE);
                    tvCount.setText(cartQuantity+"");
                }

                String imgUrl = "https://admin.veggiegram.in/adminuploads/products/" + response.body().getData().get(0).getImage();
                LoadWithGlide.loadImage(productImage,imgUrl, new CircularProgressDrawable(getContext()));
                wishlisted_in = response.body().getData().get(0).getWhishlisted();
            }

            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {

            }
        });
        return  view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.getItem(0);
        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        textCartItemCount.setText(String.valueOf(mCartItemCount));

    }

    public void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}