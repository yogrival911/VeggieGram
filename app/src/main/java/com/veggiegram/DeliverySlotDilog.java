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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.veggiegram.responses.SlotAdapter;
import com.veggiegram.responses.slot.SlotResponse;
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

        builder.setView(layout);
        builder.setCancelable(true);
        builder.show();

    }
}
