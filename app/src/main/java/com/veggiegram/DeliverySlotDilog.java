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

        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PaymentStatusActivity.class);
                intent.putExtra("cart_total", cartTotal);
                intent.putExtra("payment_mode", "COD");
                getActivity().startActivity(intent);
            }
        });

        payViaRazorpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RazorpayActivity.class);
                intent.putExtra("entered_amount", cartTotal+"");
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
