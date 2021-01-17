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


        retrofitIInterface.getWishList(user_id).enqueue(new Callback<GetWishListResponse>() {
            @Override
            public void onResponse(Call<GetWishListResponse> call, Response<GetWishListResponse> response) {
                if(response.body().getSuccess()){
                    WishListAdapter wishListAdapter = new WishListAdapter(response.body());
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