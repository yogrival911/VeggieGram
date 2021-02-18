package com.veggiegram;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.veggiegram.R;
import com.veggiegram.responses.RemoveAddressObject;
import com.veggiegram.responses.address.AddressResponse;
import com.veggiegram.responses.removeaddress.RemoveAddressResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AddressFragment extends Fragment {
RecyclerView recyclerAddress;
Button addNewAddress, chooseDeliverySlot;
ClickInterface clickInterface;
SelectedAddressAdapter selectedAddressAdapter;
TextView tvCartTotal;
int cartTotal;
int selectedAddress_id = -1;
    public AddressFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_address, container, false);

        cartTotal = AddressFragmentArgs.fromBundle(getArguments()).getCartTotal();
        Log.i("yogcart",cartTotal+"");

        addNewAddress = view.findViewById(R.id.addNewAddress);
        chooseDeliverySlot = view.findViewById(R.id.chooseDeliverySlot);
        recyclerAddress = view.findViewById(R.id.recyclerAddress);
        tvCartTotal = view.findViewById(R.id.tvCartTotal);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerAddress.setLayoutManager(linearLayoutManager);

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = sharedPreferences.getString("user_id", "");

        tvCartTotal.setText("\u20B9"+cartTotal);

       clickInterface = new ClickInterface() {
           @Override
           public void click(int index, int id) {
               selectedAddress_id = id;
               Log.i("yogselectadd", selectedAddress_id+"");
           }

           @Override
           public void clickRemoveCart(int index, String productid) {
           }

           @Override
           public void clickRemoveAddress(int index, int addressid) {

           }

           @Override
           public void clickSelectAddress(int index, int addressid) {

           }
       };

        retrofitIInterface.getUserAddressList(user_id).enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                AddressResponse addressResponse = response.body();
                selectedAddress_id = response.body().getData().get(0).getId();
                selectedAddressAdapter = new SelectedAddressAdapter(response.body(), clickInterface);
                recyclerAddress.setAdapter(selectedAddressAdapter);
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

        chooseDeliverySlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeliverySlotDilog deliverySlotDilog = new DeliverySlotDilog();
                Bundle bundle = new Bundle();
                bundle.putInt("cart_total", cartTotal);
                bundle.putInt("address_id", selectedAddress_id);
                deliverySlotDilog.setArguments(bundle);
                deliverySlotDilog.show(getActivity().getSupportFragmentManager(),"ModelBottomSheet");
            }
        });

        return view;
    }
}