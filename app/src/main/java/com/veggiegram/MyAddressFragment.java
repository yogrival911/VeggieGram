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

import com.veggiegram.responses.address.AddressResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;
import com.veggiegram.ui.cart.CartFragment;
import com.veggiegram.ui.home.HomeFragmentDirections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MyAddressFragment extends Fragment {
RecyclerView recyclerAddress;
SharedPreferences sharedPreferences;
Button addAddress;
    public MyAddressFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_address, container, false);
        recyclerAddress = view.findViewById(R.id.recyclerAddress);
        addAddress = view.findViewById(R.id.addAddress);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = sharedPreferences.getString("user_id", "");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerAddress.setLayoutManager(linearLayoutManager);


        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

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

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.address_host_frag);
                navHostFragment.getNavController().navigate(MyAddressFragmentDirections.actionMyAddressFragmentToNewAddressFragment());
            }
        });

        return view;
    }
}