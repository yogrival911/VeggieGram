package com.veggiegram;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.veggiegram.responses.RemoveWishListResponse;
import com.veggiegram.responses.WishListObject;
import com.veggiegram.responses.wishlist.GetWishListResponse;
import com.veggiegram.responses.wishlist.WishListAdapter;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class WishListFragment extends Fragment {
RecyclerView recyclerViewWishList;
SharedPreferences sharedPreferences;
ClickCartInterface clickCartInterface;
WishListAdapter wishListAdapter;
    public WishListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = sharedPreferences.getString("user_id","");

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        recyclerViewWishList = view.findViewById(R.id.recyclerViewWishList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewWishList.setLayoutManager(gridLayoutManager);

        clickCartInterface = new ClickCartInterface() {
            @Override
            public void increment(int index, int cartQuantity, String productid, int sellPrice) {

            }

            @Override
            public void decrement(int index, int cartQuanity, String productid, int sellPrice) {

            }

            @Override
            public void clickAdd(int index, int cartQuantity, String productid) {

            }

            @Override
            public void clickWishList(int index, String productid) {

            }

            @Override
            public void clickRemoveWishList(int index, String productid) {
                wishListAdapter.getWishListResponse.getData().remove(index);
                wishListAdapter.notifyItemRemoved(index);
                retrofitIInterface.removeWishList(new WishListObject(productid),user_id).enqueue(new Callback<RemoveWishListResponse>() {
                    @Override
                    public void onResponse(Call<RemoveWishListResponse> call, Response<RemoveWishListResponse> response) {
                        if (response.body().getSuccess()){
                            Snackbar.make(getView(), response.body().getMessage(),Snackbar.LENGTH_SHORT).show();
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

        retrofitIInterface.getWishList(user_id).enqueue(new Callback<GetWishListResponse>() {
            @Override
            public void onResponse(Call<GetWishListResponse> call, Response<GetWishListResponse> response) {
                if(response.body().getSuccess()){
                    wishListAdapter = new WishListAdapter(response.body(), clickCartInterface);
                    recyclerViewWishList.setAdapter(wishListAdapter);
                }
            }

            @Override
            public void onFailure(Call<GetWishListResponse> call, Throwable t) {

            }
        });

        return  view;
    }
}