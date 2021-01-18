package com.veggiegram;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.veggiegram.responses.otp.OTPResponse;
import com.veggiegram.responses.otp.SendOTPObject;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;
import com.veggiegram.ui.register.SigninFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class OTPFragment extends Fragment {
EditText etOTP;
Button verify;
OTPResponse otpResponse = new OTPResponse();
SharedPreferences sharedPreferences;
    public OTPFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_o_t_p, container, false);

        verify = view.findViewById(R.id.verify);
        etOTP = view.findViewById(R.id.etOTP);

        String mobile_passed = OTPFragmentArgs.fromBundle(getArguments()).getMobileNo();
        String user_id = OTPFragmentArgs.fromBundle(getArguments()).getUserId();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        retrofitIInterface.sendOtp(new SendOTPObject(mobile_passed,"")).enqueue(new Callback<OTPResponse>() {
            @Override
            public void onResponse(Call<OTPResponse> call, Response<OTPResponse> response) {
                otpResponse = response.body();
            }

            @Override
            public void onFailure(Call<OTPResponse> call, Throwable t) {

            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredOtp = etOTP.getText().toString();
                String actualOtp = otpResponse.getData().get(0).getOtp();
                if(enteredOtp.equals(actualOtp)){
                    //save userid
                    sharedPreferences.edit().putString("user_id",user_id).apply();
                    Toast.makeText(getContext(), "Correct OTP", Toast.LENGTH_SHORT).show();
                    NavHostFragment navHostFragment =(NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host);
                    navHostFragment.getNavController().navigate(OTPFragmentDirections.actionOTPFragmentToFavoriteFragment());
                }
                else{
                    Toast.makeText(getContext(), "Wrong OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}