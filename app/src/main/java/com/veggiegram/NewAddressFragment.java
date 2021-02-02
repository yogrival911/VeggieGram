package com.veggiegram;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.veggiegram.responses.AddAddressObject;
import com.veggiegram.responses.addaddress.AddAddressResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class NewAddressFragment extends Fragment {
Button saveAddress;
EditText etFirstName,etLastName,etHouse,etStreet,etPin,etLandmark,etMob;
    public NewAddressFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_address, container, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = sharedPreferences.getString("user_id", "");

        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etHouse = view.findViewById(R.id.etHouse);
        etStreet = view.findViewById(R.id.etStreet);
        etPin = view.findViewById(R.id.etPin);
        etLandmark = view.findViewById(R.id.etLandmark);
        etMob = view.findViewById(R.id.etMob);


        saveAddress = view.findViewById(R.id.saveAddress);

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        saveAddress.setOnClickListener(new View.OnClickListener() {
            String firstName = etFirstName.getText().toString();
            String lastName = etLastName.getText().toString();
            String house = etHouse.getText().toString();
            String street = etStreet.getText().toString();
            String pin = etPin.getText().toString();
            String landmark = etLandmark.getText().toString();
            String mob = etMob.getText().toString();

            @Override
            public void onClick(View view) {
                retrofitIInterface.addNewAddress(new AddAddressObject("123456", "34", "55",user_id,house,street,"city","punjab","punjab",mob,pin,firstName, lastName,landmark), user_id)
                        .enqueue(new Callback<AddAddressResponse>() {
                            @Override
                            public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {

                            }

                            @Override
                            public void onFailure(Call<AddAddressResponse> call, Throwable t) {

                            }
                        });
            }
        });


        return view;
    }
}