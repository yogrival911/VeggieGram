package com.veggiegram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DeliverySlotDilog extends BottomSheetDialogFragment {
Button setDeliverySlotButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delivery_slot_layout, container, false);

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
