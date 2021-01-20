package com.veggiegram;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.veggiegram.R;
import com.veggiegram.responses.address.AddressResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AddressFragment extends Fragment {
RecyclerView recyclerAddress;
Button addNewAddress;
    public AddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_address, container, false);


        addNewAddress = view.findViewById(R.id.addNewAddress);
        recyclerAddress = view.findViewById(R.id.recyclerAddress);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerAddress.setLayoutManager(linearLayoutManager);

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = sharedPreferences.getString("user_id", "");

        retrofitIInterface.getUserAddressList(user_id).enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                AddressResponse addressResponse = response.body();
                AddressAdapter addressAdapter = new AddressAdapter(response.body());
                recyclerAddress.setAdapter(addressAdapter);
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {

            }
        });

        addNewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.cart_navigation_host);
                navHostFragment.getNavController().navigate(AddressFragmentDirections.actionAddressFragmentToNewAddressFragment2());
            }
        });

        return view;
    }
}