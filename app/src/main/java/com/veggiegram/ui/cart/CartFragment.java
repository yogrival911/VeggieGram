package com.veggiegram.ui.cart;

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
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.veggiegram.R;
import com.veggiegram.responses.cartlist.GetCartListResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class CartFragment extends Fragment {
RecyclerView recyclerViewCart;
SharedPreferences sharedPreferences;
TextView cartTotal;
    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_cart, container, false);

        cartTotal = view.findViewById(R.id.cartTotal);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = sharedPreferences.getString("user_id","");

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        recyclerViewCart = view.findViewById(R.id.recyclerViewCart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewCart.setLayoutManager(linearLayoutManager);
        Log.i("yogid",user_id);

        retrofitIInterface.getusercartlistproducts(user_id).enqueue(new Callback<GetCartListResponse>() {
            @Override
            public void onResponse(Call<GetCartListResponse> call, Response<GetCartListResponse> response) {
                Log.i("yog", response.body().toString());
                if(response.body().getSuccess()){
                    String productTotal = response.body().getData().get(response.body().getData().size()-1).getProducttotal().toString();
                    cartTotal.setText(productTotal);
                    CartAdapter cartAdapter = new CartAdapter(response.body());
                    recyclerViewCart.setAdapter(cartAdapter);
                }
            }

            @Override
            public void onFailure(Call<GetCartListResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}