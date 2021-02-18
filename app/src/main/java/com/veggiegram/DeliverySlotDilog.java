package com.veggiegram;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.veggiegram.responses.SlotAdapter;
import com.veggiegram.responses.addorder.AddOrderResponse;
import com.veggiegram.responses.cartlist.GetCartListResponse;
import com.veggiegram.responses.slot.SlotResponse;
import com.veggiegram.responses.wallet.WalletResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DeliverySlotDilog extends BottomSheetDialogFragment {
Button setDeliverySlotButton;
RecyclerView recyclerViewSlot;
SlotAdapter slotAdapter;
int cartTotal, address_id;
ClickInterface clickInterface;
int selectedSlotId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delivery_slot_layout, container, false);

        recyclerViewSlot = view.findViewById(R.id.recyclerViewSlot);
        recyclerViewSlot.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSlot.setNestedScrollingEnabled(true);

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = sharedPreferences.getString("user_id","");

        cartTotal = getArguments().getInt("cart_total");
        address_id = getArguments().getInt("address_id");
        Log.i("yogadd", ""+cartTotal);
        Log.i("yogadd", ""+address_id);
        cartTotal = 1;

        clickInterface = new ClickInterface() {
            @Override
            public void click(int index, int id) {
                selectedSlotId = id;
                Log.i("slotyo", selectedSlotId+"");
            }

            @Override
            public void clickRemoveCart(int index, String productid) {

            }

            @Override
            public void clickRemoveAddress(int index, int addressid) {

            }

            @Override
            public void clickSelectAddress(int index, int addressid) {

            }
        };

        retrofitIInterface.getDeliverySlot(user_id).enqueue(new Callback<SlotResponse>() {
            @Override
            public void onResponse(Call<SlotResponse> call, Response<SlotResponse> response) {
                selectedSlotId = response.body().getData().get(0).getId();
                slotAdapter = new SlotAdapter(response.body(), clickInterface);
                recyclerViewSlot.setAdapter(slotAdapter);
            }

            @Override
            public void onFailure(Call<SlotResponse> call, Throwable t) {

            }
        });

        setDeliverySlotButton = view.findViewById(R.id.setDeliverySlotButton);
        setDeliverySlotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("yogselect", selectedSlotId+"");
                createRatingBox();
            }
        });
        return view;
    }

    public void createRatingBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View layout= null;
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.payment_mode_layout, null);

        Button payWallet = layout.findViewById(R.id.payWallet);
        Button addMoneyWallet = layout.findViewById(R.id.addMoneyWallet);
        TextView tvBalance = layout.findViewById(R.id.tvBalance);
        Button cod = layout.findViewById(R.id.cod);
        Button payViaRazorpay = layout.findViewById(R.id.payViaRazorpay);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = sharedPreferences.getString("user_id", "");

        Log.i("yogall","address:" + address_id + "slot:"+ selectedSlotId + "cartTotal:" + cartTotal);

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        retrofitIInterface.getUserWallet(user_id).enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {

                if(response.body().getData().size() == 0){
                    payWallet.setEnabled(false);
                    tvBalance.setText("Insufficient balance(0)");
                }
                else{
                    int walletBalance = response.body().getData().get(0).getAmount();
                    if(cartTotal >= walletBalance){
                        //insufficient balance
                        payWallet.setEnabled(false);
                        tvBalance.setText("Insufficient balance("+walletBalance+")");
                    }
                    else{
                        tvBalance.setText("Balance("+ walletBalance+")");
                        payWallet.setEnabled(true);
                        payWallet.setBackgroundColor(getContext().getResources().getColor(R.color.purple_500));
                    }
                }
            }

            @Override
            public void onFailure(Call<WalletResponse> call, Throwable t) {

            }
        });

        payWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                retrofitIInterface.getUserWallet(user_id).enqueue(new Callback<WalletResponse>() {
                    @Override
                    public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                        int walletAmount = response.body().getData().get(0).getAmount();


                        retrofitIInterface.getusercartlistproducts(user_id).enqueue(new Callback<GetCartListResponse>() {
                            @Override
                            public void onResponse(Call<GetCartListResponse> call, Response<GetCartListResponse> response) {

                                Log.i("yogintent", cartTotal+"");
                                Log.i("yogintent", address_id+"");
                                Log.i("yogintent", selectedSlotId+"");

                                List<HashMap> hashMapListFor = new ArrayList<>();
                                for(int i=0; i < response.body().getData().size(); i++){
                                    HashMap<String, Integer> mapFor = new HashMap<>();
                                    mapFor.put("id", response.body().getData().get(i).getProductid());
                                    mapFor.put("qty", response.body().getData().get(i).getCartquantity());
                                    mapFor.put("price", response.body().getData().get(i).getPrice());

                                    hashMapListFor.add(mapFor);
                                }
                                Gson gson = new Gson();
                                String jsonStringFor = gson.toJson(hashMapListFor);
                                Log.i("yogjsonarray", jsonStringFor);

                                JsonObject jsonObject = new JsonObject();
                                jsonObject.addProperty("payment_order_id", "");
                                jsonObject.addProperty( "transaction_id", "");
                                jsonObject.addProperty("total", String.valueOf(cartTotal));
                                jsonObject.addProperty("final_total", String.valueOf(cartTotal));
                                jsonObject.addProperty("shipping_cost", "0");
                                jsonObject.addProperty("discount", "");
                                jsonObject.addProperty("deliver_address_Id", String.valueOf(address_id));
                                jsonObject.addProperty("slot", String.valueOf(selectedSlotId));
                                jsonObject.addProperty("wallet", String.valueOf(walletAmount-cartTotal));
                                jsonObject.addProperty("orderData", jsonStringFor);

                                retrofitIInterface.addOrder(jsonObject, user_id).enqueue(new Callback<AddOrderResponse>() {
                                    @Override
                                    public void onResponse(Call<AddOrderResponse> call, Response<AddOrderResponse> response) {
                                        Log.i("yogjsonobject", response.body().getMessage());
                                        Intent intent = new Intent(getContext(), PaymentStatusActivity.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFailure(Call<AddOrderResponse> call, Throwable t) {

                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<GetCartListResponse> call, Throwable t) {

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<WalletResponse> call, Throwable t) {

                    }
                });

            }
        });

        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PaymentStatusActivity.class);
                intent.putExtra("cart_total", cartTotal);
                intent.putExtra("payment_mode", "COD");
                intent.putExtra("address_id", address_id);
                intent.putExtra("slot_id", selectedSlotId);
                intent.putExtra("mode","cod");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().startActivity(intent);
            }
        });

        payViaRazorpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RazorpayActivity.class);
                intent.putExtra("entered_amount", cartTotal+"");
                intent.putExtra("description", "Paying");
                intent.putExtra("cart_total", cartTotal);
                intent.putExtra("payment_mode", "COD");
                intent.putExtra("address_id", address_id);
                intent.putExtra("slot_id", selectedSlotId);
                intent.putExtra("fromWallet", false);
                intent.putExtra("mode", "razorpay");
                getActivity().startActivity(intent);
            }
        });

        addMoneyWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WalletActivity.class);
                getActivity().startActivity(intent);
            }
        });

        builder.setView(layout);
        builder.setCancelable(true);
        builder.show();

    }
}
