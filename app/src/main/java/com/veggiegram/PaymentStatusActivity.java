package com.veggiegram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PaymentStatusActivity extends AppCompatActivity {
TextView statusAmount,paymentMode;
Button takeMeHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_status);

        statusAmount = findViewById(R.id.statusAmount);
        paymentMode = findViewById(R.id.paymentMode);
        takeMeHome = findViewById(R.id.takeMeHome);

        takeMeHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}