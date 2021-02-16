package com.veggiegram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.veggiegram.responses.addorder.AddOrderObject;
import com.veggiegram.responses.addorder.AddOrderResponse;
import com.veggiegram.responses.addorder.OrderDatum;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PaymentStatusActivity extends AppCompatActivity {
TextView statusAmount,paymentMode;
Button takeMeHome;
Toolbar toolbarPaymentStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_status);

        statusAmount = findViewById(R.id.statusAmount);
        paymentMode = findViewById(R.id.paymentMode);
        takeMeHome = findViewById(R.id.takeMeHome);
        toolbarPaymentStatus = findViewById(R.id.toolbarPaymentStatus);
        setSupportActionBar(toolbarPaymentStatus);
        getSupportActionBar().setTitle("Payment Status");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user_id = sharedPreferences.getString("user_id","");
        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        List<OrderDatum> orderData = new ArrayList<>();
        orderData.add(new OrderDatum(2, 4, 4));



        takeMeHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}