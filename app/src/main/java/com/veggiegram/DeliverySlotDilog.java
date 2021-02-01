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
import com.veggiegram.responses.SlotAdapter;
import com.veggiegram.responses.slot.SlotResponse;
import com.veggiegram.responses.wallet.WalletResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DeliverySlotDilog extends BottomSheetDialogFragment {
Button setDeliverySlotButton;
RecyclerView recyclerViewSlot;
SlotAdapter slotAdapter;
int cartTotal;
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
        Log.i("yogadd", ""+cartTotal);

        retrofitIInterface.getDeliverySlot(user_id).enqueue(new Callback<SlotResponse>() {
            @Override
            public void onResponse(Call<SlotResponse> call, Response<SlotResponse> response) {
                slotAdapter = new SlotAdapter(response.body());
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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = sharedPreferences.getString("user_id", "");

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        retrofitIInterface.getUserWallet(user_id).enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                int walletBalance = response.body().getData().get(0).getAmount();
                if(response.body().getData().size() == 0){

                }
                else{
                    if(cartTotal >= walletBalance){
                        //insufficient balance
                        tvBalance.setText("Insufficient balance("+walletBalance+")");
                    }
                    else{

                    }
                }
            }

            @Override
            public void onFailure(Call<WalletResponse> call, Throwable t) {

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
