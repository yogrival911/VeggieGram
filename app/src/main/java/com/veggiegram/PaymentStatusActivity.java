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
import com.google.gson.Gson;
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
        Gson gson = new Gson();


        HashMap<String, Integer> map = new HashMap<>();
        HashMap<String, Integer> map2 = new HashMap<>();
        // Add elements to the map
        map.put("id", 10);
        map.put("qty", 30);
        map.put("price", 20);

        map2.put("id", 102);
        map2.put("qty", 302);
        map2.put("price", 202);

        List<HashMap> hashMapList = new ArrayList<>();
        hashMapList.add(map);
        hashMapList.add(map2);

        String jsonString = gson.toJson(hashMapList);
        Log.i("yogjsonarray", jsonString);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("payment_order_id", "");
        jsonObject.addProperty( "transaction_id", "");
        jsonObject.addProperty("total", "223");
        jsonObject.addProperty("final_total", "2332");
        jsonObject.addProperty("shipping_cost", "0");
        jsonObject.addProperty("discount", "2");
        jsonObject.addProperty("deliver_address_Id", "1");
        jsonObject.addProperty("slot", "8 AM to 10 AM");
        jsonObject.addProperty("wallet", "0.00");
        jsonObject.addProperty("orderData", jsonString);

        retrofitIInterface.addOrder(jsonObject, user_id).enqueue(new Callback<AddOrderResponse>() {
            @Override
            public void onResponse(Call<AddOrderResponse> call, Response<AddOrderResponse> response) {
                Log.i("yogjsonobject", response.body().getMessage());
            }

            @Override
            public void onFailure(Call<AddOrderResponse> call, Throwable t) {

            }
        });
        takeMeHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}