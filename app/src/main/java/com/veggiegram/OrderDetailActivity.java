package com.veggiegram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class OrderDetailActivity extends AppCompatActivity {
Toolbar toolbarODetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        toolbarODetail = findViewById(R.id.toolbarODetail);

        setSupportActionBar(toolbarODetail);
        getSupportActionBar().setTitle("Order Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}