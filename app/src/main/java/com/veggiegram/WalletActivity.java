package com.veggiegram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class WalletActivity extends AppCompatActivity {
Toolbar toolbarWallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        toolbarWallet = findViewById(R.id.toolbarWallet);
        setSupportActionBar(toolbarWallet);
        getSupportActionBar().setTitle("Wallet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}