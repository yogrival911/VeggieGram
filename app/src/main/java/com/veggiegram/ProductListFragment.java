package com.veggiegram;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.veggiegram.responses.AddToCartObject;
import com.veggiegram.responses.RemoveCartObject;
import com.veggiegram.responses.RemoveWishListResponse;
import com.veggiegram.responses.WishListObject;
import com.veggiegram.responses.cartlist.GetCartListResponse;
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
    ClickSubCatInterface clickSubCatInterface;
    TextView textCartItemCount;
    int mCartItemCount;
    int cartCount;
    public ProductListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        setHasOptionsMenu(true);

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
        Log.i("yog", "user_id "+user_id);

        retrofitIInterface.getusercartlistproducts(user_id).enqueue(new Callback<GetCartListResponse>() {
            @Override
            public void onResponse(Call<GetCartListResponse> call, Response<GetCartListResponse> response) {
                mCartItemCount = response.body().getData().size();
                setupBadge();
            }

            @Override
            public void onFailure(Call<GetCartListResponse> call, Throwable t) {

            }
        });

        clickCartInterface = new ClickCartInterface() {
            @Override
            public void increment(int index, int cartQuantity, String productid, int sellPrice) {

                productListAdapter.productListByCatResponse.getData().get(index).setCartquantity(cartQuantity+1);
                productListAdapter.notifyItemChanged(index);
                retrofitIInterface.addToCart(new AddToCartObject(productid,String.valueOf(cartQuantity+1)),user_id).enqueue(new Callback<GetWishListResponse>() {
                    @Override
                    public void onResponse(Call<GetWishListResponse> call, Response<GetWishListResponse> response) {
                        GetWishListResponse wishListResponse = response.body();
                        if (response.body().getSuccess()){

                            Toast.makeText(getContext(), "Cart Updated", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetWishListResponse> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void decrement(int index, int cartQuanity, String productid, int sellPrice) {

                if(cartQuanity==1){
                    productListAdapter.productListByCatResponse.getData().get(index).setCartquantity(cartQuanity-1);
                    productListAdapter.notifyItemChanged(index);

                    mCartItemCount--;
                    setupBadge();
                    retrofitIInterface.removeCartProduct(new RemoveCartObject(productid),user_id).enqueue(new Callback<RemoveCartResponse>() {
                        @Override
                        public void onResponse(Call<RemoveCartResponse> call, Response<RemoveCartResponse> response) {

                        }

                        @Override
                        public void onFailure(Call<RemoveCartResponse> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                else{
                    productListAdapter.productListByCatResponse.getData().get(index).setCartquantity(cartQuanity-1);
                    productListAdapter.notifyItemChanged(index);

                    AddToCartObject addToCartObject = new AddToCartObject(productid,String.valueOf(cartQuanity-1));
                    retrofitIInterface.addToCart(addToCartObject,user_id).enqueue(new Callback<GetWishListResponse>() {
                        @Override
                        public void onResponse(Call<GetWishListResponse> call, Response<GetWishListResponse> response) {
                            if (response.body().getSuccess()){
                                Toast.makeText(getContext(), "Cart Updated", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<GetWishListResponse> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void clickAdd(int index, int cartQuantity, String productid) {
                mCartItemCount = mCartItemCount+1;
                setupBadge();
//                MainActivity mainActivity = new MainActivity();
//                mainActivity.setmCartItemCount(mCartItemCount);
//                mainActivity.setupBadge();
                productListAdapter.productListByCatResponse.getData().get(index).setCartquantity(cartQuantity+1);
                productListAdapter.notifyItemChanged(index);

                AddToCartObject addToCartObject = new AddToCartObject(productid,String.valueOf(cartQuantity+1));
                retrofitIInterface.addToCart(addToCartObject,user_id).enqueue(new Callback<GetWishListResponse>() {
                    @Override
                    public void onResponse(Call<GetWishListResponse> call, Response<GetWishListResponse> response) {
                        if (response.body().getSuccess()){
                            Toast.makeText(getContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<GetWishListResponse> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void clickWishList(int index, String productid) {
                productListAdapter.productListByCatResponse.getData().get(index).setWhishlisted(1);
                productListAdapter.notifyItemChanged(index);
                retrofitIInterface.addToWishList(new WishListObject(productid),user_id).enqueue(new Callback<WishListResponse>() {
                    @Override
                    public void onResponse(Call<WishListResponse> call, Response<WishListResponse> response) {
                        if(response.body().getSuccess()){
                            Toast.makeText(getContext(), response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), response.errorBody().toString(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WishListResponse> call, Throwable t) {

                    }
                });
            }

            @Override
            public void clickRemoveWishList(int index, String productid) {
                productListAdapter.productListByCatResponse.getData().get(index).setWhishlisted(0);
                productListAdapter.notifyItemChanged(index);
                retrofitIInterface.removeWishList(new WishListObject(productid),user_id).enqueue(new Callback<RemoveWishListResponse>() {
                    @Override
                    public void onResponse(Call<RemoveWishListResponse> call, Response<RemoveWishListResponse> response) {
                        if(response.body().getSuccess()){
                            Toast.makeText(getContext(), response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getContext(), response.errorBody().toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<RemoveWishListResponse> call, Throwable t) {

                    }
                });
            }
        };

        clickInterface = new ClickInterface() {
            @Override
            public void clickSelectAddress(int index, int addressid) {

            }

            @Override
            public void click(int index) {
                Toast.makeText(getContext(), "position"+index, Toast.LENGTH_SHORT).show();
                retrofitIInterface.getProductbySubCatID(String.valueOf(index+1),user_id).enqueue(new Callback<ProductListByCatResponse>() {
                    @Override
                    public void onResponse(Call<ProductListByCatResponse> call, Response<ProductListByCatResponse> response) {
                        ProductListByCatResponse productListByCatResponse = response.body();

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
                productListAdapter = new ProductListAdapter(response.body(),clickInterface,clickCartInterface);
                recyclerViewProducts.setAdapter(productListAdapter);
            }

            @Override
            public void onFailure(Call<ProductListByCatResponse> call, Throwable t) {

            }
        });

        return view;
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