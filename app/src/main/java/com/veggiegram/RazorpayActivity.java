package com.veggiegram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.veggiegram.responses.addorder.AddOrderResponse;
import com.veggiegram.responses.addwallet.AddWalletObject;
import com.veggiegram.responses.addwallet.AddWalletResponse;
import com.veggiegram.responses.cartlist.GetCartListResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RazorpayActivity extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    Boolean fromWallet;
    String enteredAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay);

        Checkout checkout = new Checkout();

        enteredAmount = getIntent().getStringExtra("entered_amount");
        String description = getIntent().getStringExtra("description");
        int cartTotal = getIntent().getIntExtra("cart_total",0);
        int addressID = getIntent().getIntExtra("address_id", 0);
        int slotID = getIntent().getIntExtra("slot_id", 0);
        Log.i("yogdes", String.valueOf(addressID));

        fromWallet = getIntent().getBooleanExtra("fromWallet",false);
        Log.i("fromWallet", fromWallet.toString());

        Log.i("yogenter", enteredAmount);
        int checkoutAmount = Integer.parseInt(enteredAmount);
        int checkAmoutRazor = checkoutAmount*100;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sharedPreferences.getString("name", "");
        String mobile = sharedPreferences.getString("mobile","");
        String email = sharedPreferences.getString("email","");

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);


        checkout.setKeyID("rzp_live_iI3vVE2JZRTuxg");
        /**
         * Instantiate Checkout
         */

        /**
         * Set your logo here
         */

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Veggiegram");
            options.put("description", description);
//            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "90909090");//from response of step 3.
            options.put("theme.color", "#73B440");
            options.put("currency", "INR");
            options.put("amount", checkAmoutRazor+"");//pass amount in currency subunits
            options.put("prefill.email", email);
            options.put("prefill.contact",mobile);
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        Log.i("yogpaysuccess", s);
        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String user_id = sharedPreferences.getString("user_id","");
        if(fromWallet){
            //from wallet no order creation

            retrofitIInterface.addWallet(new AddWalletObject(enteredAmount, s), user_id).enqueue(new Callback<AddWalletResponse>() {
                @Override
                public void onResponse(Call<AddWalletResponse> call, Response<AddWalletResponse> response) {
                    if (response.body().getSuccess()){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<AddWalletResponse> call, Throwable t) {

                }
            });
        }
        else{
            //from pay with razorpay create order


            retrofitIInterface.getusercartlistproducts(user_id).enqueue(new Callback<GetCartListResponse>() {
                @Override
                public void onResponse(Call<GetCartListResponse> call, Response<GetCartListResponse> response) {

                    int cartTotal = getIntent().getIntExtra("cart_total",0);
                    int addressID = getIntent().getIntExtra("address_id", 0);
                    int slotID = getIntent().getIntExtra("slot_id", 0);

                    Log.i("yogintent", cartTotal+"");
                    Log.i("yogintent", addressID+"");
                    Log.i("yogintent", slotID+"");

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
                    jsonObject.addProperty( "transaction_id", s);
                    jsonObject.addProperty("total", String.valueOf(cartTotal));
                    jsonObject.addProperty("final_total", String.valueOf(cartTotal));
                    jsonObject.addProperty("shipping_cost", "0");
                    jsonObject.addProperty("discount", "");
                    jsonObject.addProperty("deliver_address_Id", String.valueOf(addressID));
                    jsonObject.addProperty("slot", String.valueOf(slotID));
                    jsonObject.addProperty("wallet", "0.00");
                    jsonObject.addProperty("orderData", jsonStringFor);

                    retrofitIInterface.addOrder(jsonObject, user_id).enqueue(new Callback<AddOrderResponse>() {
                        @Override
                        public void onResponse(Call<AddOrderResponse> call, Response<AddOrderResponse> response) {
                            Log.i("yogjsonobject", response.body().getMessage());
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



    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        Log.i("yogpayerror", s +" "+ i);

    }
}