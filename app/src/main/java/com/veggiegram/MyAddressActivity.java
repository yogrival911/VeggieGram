package com.veggiegram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class MyAddressActivity extends AppCompatActivity {
Toolbar toolbarMyAdrress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        toolbarMyAdrress = findViewById(R.id.toolbarMyAdrress);

        toolbarMyAdrress = findViewById(R.id.toolbarMyAdrress);
        setSupportActionBar(toolbarMyAdrress);
        getSupportActionBar().setTitle("Addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}