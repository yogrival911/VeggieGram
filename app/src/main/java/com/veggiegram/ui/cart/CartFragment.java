package com.veggiegram.ui.cart;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.veggiegram.CartActivity;
import com.veggiegram.ClickInterface;
import com.veggiegram.R;
import com.veggiegram.responses.RemoveCartObject;
import com.veggiegram.responses.cartlist.GetCartListResponse;
import com.veggiegram.responses.removecart.RemoveCartResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;
import com.veggiegram.ui.home.HomeFragmentDirections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.lang.Integer.parseInt;


public class CartFragment extends Fragment {
RecyclerView recyclerViewCart;
SharedPreferences sharedPreferences;
TextView cartTotal;
Button checkOut;
ClickInterface clickInterface;
CartAdapter cartAdapter;
    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_cart, container, false);
        checkOut = view.findViewById(R.id.checkOut);

        cartTotal = view.findViewById(R.id.cartTotal);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = sharedPreferences.getString("user_id","");

        NavHostFragment navHostFragment =(NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host);

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        recyclerViewCart = view.findViewById(R.id.recyclerViewCart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewCart.setLayoutManager(linearLayoutManager);
        Log.i("yogid",user_id);

        clickInterface = new ClickInterface() {
            @Override
            public void click(int index) {

            }

            @Override
            public void clickRemoveCart(int index, String productid) {
                retrofitIInterface.removeCartProduct(new RemoveCartObject(productid),user_id).enqueue(new Callback<RemoveCartResponse>() {
                    @Override
                    public void onResponse(Call<RemoveCartResponse> call, Response<RemoveCartResponse> response) {
                        RemoveCartResponse removeCartResponse = response.body();
                        cartAdapter.cartListResponse.getData().remove(index);
                        cartAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<RemoveCartResponse> call, Throwable t) {

                    }
                });

            }

            @Override
            public void clickRemoveAddress(int index, int addressid) {

            }

        };

        retrofitIInterface.getusercartlistproducts(user_id).enqueue(new Callback<GetCartListResponse>() {
            @Override
            public void onResponse(Call<GetCartListResponse> call, Response<GetCartListResponse> response) {
                Log.i("yog", response.body().toString());
                if(response.body().getSuccess()){
                    int totalPrice=0;
                    int totalQuantity = 0;
                    int grandTotal=0;
                    for(int i=0; i<response.body().getData().size();i++){
                        String quant = response.body().getData().get(i).getCartquantity().toString();
                        String price = response.body().getData().get(i).getSellprice().toString();
                        int quantity = parseInt(quant);
                        int sellPrice = Integer.parseInt(price);
//
//                        totalQuantity = totalQuantity+quantity;
//                        totalPrice = totalPrice+sellPrice;

                        grandTotal = grandTotal + quantity * sellPrice;
                    }
                    cartTotal.setText("\u20B9 "+ grandTotal);
                    cartAdapter = new CartAdapter(response.body(), clickInterface);
                    recyclerViewCart.setAdapter(cartAdapter);
                }
            }

            @Override
            public void onFailure(Call<GetCartListResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment navHostFragment1 = (NavHostFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.cart_navigation_host);
                navHostFragment1.getNavController().navigate(CartFragmentDirections.actionCartFragmentToAddressFragment());
            }
        });
        return view;
    }
}