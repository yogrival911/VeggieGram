package com.veggiegram;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.razorpay.Checkout;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    private  Button proceed;
    private String RAZORPAY_KEY = "rzp_live_iI3vVE2JZRTuxg";
    SharedPreferences sharedPreferences;
    EditText etEnteredAmount;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = sharedPreferences.getString("user_id","");

        etEnteredAmount = view.findViewById(R.id.etEnteredAmount);
        proceed = view.findViewById(R.id.proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etEnteredAmount.length() != 0) {
                    String enteredAmount = etEnteredAmount.getText().toString();
                    Intent intent = new Intent(getActivity(), RazorpayActivity.class);
                    intent.putExtra("entered_amount", enteredAmount);
                    intent.putExtra("description", "Adding money to wallet");
                    intent.putExtra("fromWallet", true);
                    getActivity().startActivity(intent);
                }
                else{
                    Toast.makeText(getContext(), "Please enter amount.", Toast.LENGTH_SHORT).show();              }
            }
        });
        return view;
    }
}
