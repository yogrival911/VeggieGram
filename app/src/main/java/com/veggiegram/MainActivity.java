package com.veggiegram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.veggiegram.R;
import com.veggiegram.responses.cartlist.GetCartListResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;
import com.veggiegram.ui.home.HomeFragmentDirections;
import com.veggiegram.ui.register.SigninFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextView textCartItemCount;
    int mCartItemCount;
    NavHostFragment navHostFragment;
    NavigationView navigationView;

    public int getmCartItemCount() {
        return mCartItemCount;
    }

    public void setmCartItemCount(int mCartItemCount) {
        this.mCartItemCount = mCartItemCount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user_id = sharedPreferences.getString("user_id","");
        String name = sharedPreferences.getString("name", "");

        navigationView = findViewById(R.id.navView);
        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.signin);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.headerUserName);

        if(user_id.isEmpty()){
            navUsername.setText("Welcome user");
        }
        else{
            navUsername.setText(name);
        }
        if(user_id.isEmpty()){
            navigationView.getMenu().findItem(R.id.signin).setTitle("Sign In");
        }
        else{
            navigationView.getMenu().findItem(R.id.signin).setTitle("Sign Out");
        }

        navHostFragment =(NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host);
        NavigationUI.setupWithNavController(bottomNavigationView,navHostFragment.getNavController());
//
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // hide the current title from the Toolbar
//        toolbar.setLogo(R.mipmap.logotext); // setting a logo in toolbar

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);
        retrofitIInterface.getusercartlistproducts(user_id).enqueue(new Callback<GetCartListResponse>() {
            @Override
            public void onResponse(Call<GetCartListResponse> call, Response<GetCartListResponse> response) {
                mCartItemCount = response.body().getData().size();
                setupBadge();
            }

            @Override
            public void onFailure(Call<GetCartListResponse> call, Throwable t) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.address:
                        drawerLayout.closeDrawers();
                        Intent intent = new Intent(getApplicationContext(), MyAddressActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.order:
                        Intent orderIntent = new Intent(getApplicationContext(), OrderActivity.class);
                        startActivity(orderIntent);
                        break;

                    case R.id.share:
                        drawerLayout.closeDrawers();
                        Intent sharingIntent = new Intent();
                        sharingIntent.setAction(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "Check out \"Veggiegram - Buy Fruits & Vegetables in Gurugram\"  " +
                                "https://play.google.com/store/apps/details?id=com.veggiegram";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "VeggieGram");
                        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        break;

                    case R.id.contact:
                        drawerLayout.closeDrawers();
                        Intent intent1 = new Intent(getApplicationContext(), ContactActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.review:
                       createRatingBox();
                       break;

                    case R.id.whatsapp:
                        drawerLayout.closeDrawers();
                        Uri uri = Uri.parse("smsto:" + "919999999999");
                        Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "I want to order");
                        sendIntent.setPackage("com.whatsapp");
                        if (sendIntent.resolveActivity(getPackageManager()) == null) {
                           Toast.makeText(getApplicationContext(),"Please install whatsapp", Toast.LENGTH_SHORT).show();
                           break;
                        }
                        startActivity(sendIntent);
                        break;
                    case R.id.signin:
                        drawerLayout.closeDrawers();
                        if(navigationView.getMenu().findItem(R.id.signin).getTitle() == "Sign In"){
                            Toast.makeText(MainActivity.this, "Sign in", Toast.LENGTH_SHORT).show();
//                            Intent intentSignIn = new Intent(getApplicationContext(), SigninActivity.class);
//                            startActivity(intentSignIn);
                            View view = bottomNavigationView.findViewById(R.id.favoriteFragment);
                            view.performClick();

                        }
                        else{
                            Toast.makeText(MainActivity.this, "Sign Out", Toast.LENGTH_SHORT).show();
                            sharedPreferences.edit().putString("user_id", "").apply();
                            navigationView.getMenu().findItem(R.id.signin).setTitle("Sign in");

                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        setupBadge();
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }
    public void setupBadge() {
        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_cart:

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                String user_id = sharedPreferences.getString("user_id", "");
                if(user_id.isEmpty()){
                    //login
                }
                else{
                    Intent intent = new Intent(this, CartActivity.class);
                    startActivity(intent);
                }

                break;
        }
        return true;
    }

    public void createRatingBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View layout= null;
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.rating_layout, null);
        final RatingBar ratingBar = (RatingBar)layout.findViewById(R.id.ratingBar);
        Button submitButton = (Button)layout.findViewById(R.id.button) ;
        builder.setView(layout);
        builder.setCancelable(true);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.ratingStar), PorterDuff.Mode.SRC_ATOP);
        builder.show();
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                submitButton.setVisibility(View.VISIBLE);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final String appPackageName = getPackageName();
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.veggiegram")));
                        }
                        catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                });
            }
        });
    }

}