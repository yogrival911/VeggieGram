package com.veggiegram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class AddressActivity extends AppCompatActivity {
Toolbar toolbarAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        toolbarAddress = findViewById(R.id.toolbarAddress);
        setSupportActionBar(toolbarAddress);
        getSupportActionBar().setTitle("Addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}