package com.veggiegram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.veggiegram.responses.productlistcat.ProductListByCatResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity {
Toolbar toolbarSearch;
RecyclerView recyclerSearchItems;
Retrofit retrofit;
SharedPreferences sharedPreferences;
android.widget.SearchView searchQuery;
ClickCartInterface clickCartInterface;
ClickInterface clickInterface;
TextView searchResultFor;
ConstraintLayout nothingToShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbarSearch = findViewById(R.id.toolbarSearch);
        recyclerSearchItems = findViewById(R.id.recyclerSearchItems);
        searchQuery =  findViewById(R.id.searchQuery);
        searchResultFor = findViewById(R.id.searchResultFor);
        nothingToShow = findViewById(R.id.nothingToShow);

        searchResultFor.setVisibility(View.GONE);
        nothingToShow.setVisibility(View.GONE);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user_id = sharedPreferences.getString("user_id", "");

        setSupportActionBar(toolbarSearch);
        getSupportActionBar().setTitle("Search");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerSearchItems.setLayoutManager(linearLayoutManager);

        retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        searchQuery.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                recyclerSearchItems.setVisibility(View.GONE);
                nothingToShow.setVisibility(View.GONE);
                retrofitIInterface.getSearchResult(new SearchObject(s), user_id).enqueue(new Callback<ProductListByCatResponse>() {
                    @Override
                    public void onResponse(Call<ProductListByCatResponse> call, Response<ProductListByCatResponse> response) {
                        searchResultFor.setText("Search result for " + s);
                        searchResultFor.setVisibility(View.VISIBLE);
                        if(response.body().getData().size()==0){
                            nothingToShow.setVisibility(View.VISIBLE);
                        }
                        else{
                            recyclerSearchItems.setVisibility(View.VISIBLE);
                            SearchAdapter searchAdapter = new SearchAdapter(response.body());
                            recyclerSearchItems.setAdapter(searchAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductListByCatResponse> call, Throwable t) {
                        Toast.makeText(SearchActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}