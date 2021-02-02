package com.veggiegram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class OrderDetailActivity extends AppCompatActivity {
Toolbar toolbarODetail;
TextView paymentStatusDetail, tvorder_id, tvdate, tvtotalAmount, tvpaymentMethod, tvpaymentStatus, tvaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        toolbarODetail = findViewById(R.id.toolbarODetail);
        paymentStatusDetail = findViewById(R.id.paymentStatusDetail);
        tvorder_id = findViewById(R.id.order_id);
        tvdate = findViewById(R.id.date);
        tvtotalAmount = findViewById(R.id.totalAmount);
        tvpaymentMethod = findViewById(R.id.paymentMethod);
        tvpaymentStatus = findViewById(R.id.paymentStatus);
        tvaddress = findViewById(R.id.address);


        setSupportActionBar(toolbarODetail);
        getSupportActionBar().setTitle("Order Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String order_id = getIntent().getStringExtra("order_id");
        String date = getIntent().getStringExtra("date");
        String total_amount = getIntent().getStringExtra("total_amount");
        String payment_method = getIntent().getStringExtra("payment_method");
        String payment_status = getIntent().getStringExtra("payment_status");
        String address = getIntent().getStringExtra("address");
        Log.i("yogorder", order_id+date+total_amount+payment_method+payment_status+address);

        tvorder_id.setText(order_id);
        tvdate.setText(date);
        tvaddress.setText(address);
        tvpaymentMethod.setText(payment_method);
        tvtotalAmount.setText(total_amount);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}