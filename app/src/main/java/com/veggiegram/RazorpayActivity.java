package com.veggiegram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class RazorpayActivity extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay);

        Checkout checkout = new Checkout();

        String enteredAmount = getIntent().getStringExtra("entered_amount");
        String description = getIntent().getStringExtra("description");
        Log.i("yogdes", description);

        Log.i("yogenter", enteredAmount);
        int checkoutAmount = Integer.parseInt(enteredAmount);
        int checkAmoutRazor = checkoutAmount*100;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sharedPreferences.getString("name", "");
        String mobile = sharedPreferences.getString("mobile","");
        String email = sharedPreferences.getString("email","");


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
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        Log.i("yogpayerror", s);

    }
}