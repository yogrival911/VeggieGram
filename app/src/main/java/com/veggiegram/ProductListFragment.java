package com.veggiegram;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.veggiegram.responses.AddToCartObject;
import com.veggiegram.responses.RemoveCartObject;
import com.veggiegram.responses.productlistcat.ProductListByCatResponse;
import com.veggiegram.responses.removecart.RemoveCartResponse;
import com.veggiegram.responses.subcat.SubCategoryResponse;
import com.veggiegram.responses.wishlist.GetWishListResponse;
import com.veggiegram.responses.wishlist.WishListResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;
import com.veggiegram.util.LoadWithGlide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ProductListFragment extends Fragment {
    RecyclerView recyclerViewProducts, recyclerSubCategory;
    Retrofit retrofit;
    ClickInterface clickInterface;
    ClickCartInterface clickCartInterface;
    ProductListAdapter productListAdapter;
    public ProductListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        recyclerViewProducts = view.findViewById(R.id.recyclerViewProducts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewProducts.setLayoutManager(linearLayoutManager);

        recyclerSubCategory = view.findViewById(R.id.recyclerSubCategory);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        recyclerSubCategory.setLayoutManager(linearLayoutManager1);



        int categoryPosition = ProductListFragmentArgs.fromBundle(getArguments()).getCategoryNo();
        Log.i("yog", "categoryPosition"+categoryPosition);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = sharedPreferences.getString("user_id", "");
        Log.i("yog", user_id);

        clickCartInterface = new ClickCartInterface() {
            @Override
            public void increment(int index, int cartQuantity, String productid) {
                retrofitIInterface.addToCart(new AddToCartObject(productid,String.valueOf(cartQuantity+1)),user_id).enqueue(new Callback<GetWishListResponse>() {
                    @Override
                    public void onResponse(Call<GetWishListResponse> call, Response<GetWishListResponse> response) {
                        GetWishListResponse wishListResponse = response.body();
//                        productListAdapter.productListByCatResponse.getData().get(index).setCartquantity(cartQuantity+1);
//                        productListAdapter.notifyItemChanged(index);
                    }

                    @Override
                    public void onFailure(Call<GetWishListResponse> call, Throwable t) {

                    }
                });
            }

            @Override
            public void decrement(int index, int cartQuanity, String productid) {
                AddToCartObject addToCartObject = new AddToCartObject(productid,String.valueOf(cartQuanity-1));
                retrofitIInterface.addToCart(addToCartObject,user_id).enqueue(new Callback<GetWishListResponse>() {
                    @Override
                    public void onResponse(Call<GetWishListResponse> call, Response<GetWishListResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<GetWishListResponse> call, Throwable t) {

                    }
                });
            }

            @Override
            public void clickAdd(int index, int cartQuantity) {

                Toast.makeText(getContext(), "Clicked Add", Toast.LENGTH_SHORT).show();
            }
        };

        clickInterface = new ClickInterface() {
            @Override
            public void click(int index) {
                Toast.makeText(getContext(), "position"+index, Toast.LENGTH_SHORT).show();
                retrofitIInterface.getProductbySubCatID(String.valueOf(index),user_id).enqueue(new Callback<ProductListByCatResponse>() {
                    @Override
                    public void onResponse(Call<ProductListByCatResponse> call, Response<ProductListByCatResponse> response) {
                        ProductListByCatResponse productListByCatResponse = response.body();

                        productListAdapter = new ProductListAdapter(response.body(),clickInterface, clickCartInterface);
                        productListAdapter.setProductListByCatResponse(response.body());
                        productListAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ProductListByCatResponse> call, Throwable t) {

                    }

                });
            }

            @Override
            public void clickRemoveCart(int index, String productid) {

            }

            @Override
            public void clickRemoveAddress(int index, int addressid) {

            }

        };

        retrofitIInterface.getSubCatByCatID(String.valueOf(categoryPosition)).enqueue(new Callback<SubCategoryResponse>() {
            @Override
            public void onResponse(Call<SubCategoryResponse> call, Response<SubCategoryResponse> response) {
                SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(response.body(), clickInterface);
                recyclerSubCategory.setAdapter(subCategoryAdapter);
            }

            @Override
            public void onFailure(Call<SubCategoryResponse> call, Throwable t) {

            }
        });

        retrofitIInterface.getProductslistByCatID(String.valueOf(categoryPosition), user_id).enqueue(new Callback<ProductListByCatResponse>() {
            @Override
            public void onResponse(Call<ProductListByCatResponse> call, Response<ProductListByCatResponse> response) {
                ProductListByCatResponse productListByCatResponse = response.body();
                ProductListAdapter productListAdapter = new ProductListAdapter(response.body(),clickInterface,clickCartInterface);
                recyclerViewProducts.setAdapter(productListAdapter);
            }

            @Override
            public void onFailure(Call<ProductListByCatResponse> call, Throwable t) {

            }
        });


        return view;
    }
}