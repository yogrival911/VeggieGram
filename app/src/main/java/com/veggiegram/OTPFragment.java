package com.veggiegram;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.veggiegram.R;
import com.veggiegram.responses.otp.OTPResponse;
import com.veggiegram.responses.otp.SendOTPObject;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;
import com.veggiegram.ui.favourite.FavouriteFragmentDirections;
import com.veggiegram.ui.register.SigninFragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class OTPFragment extends Fragment {
EditText etOTP;
Button verify;
OTPResponse otpResponse = new OTPResponse();
SharedPreferences sharedPreferences;
TextView resendCounter,enterWrong;
    public OTPFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_o_t_p, container, false);

        verify = view.findViewById(R.id.verify);
        etOTP = view.findViewById(R.id.etOTP);
        resendCounter = view.findViewById(R.id.resendCounter);
        enterWrong = view.findViewById(R.id.enterWrong);

        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.navView);


        String mobile_passed = OTPFragmentArgs.fromBundle(getArguments()).getMobileNo();
        String user_id = OTPFragmentArgs.fromBundle(getArguments()).getUserId();
        String name = OTPFragmentArgs.fromBundle(getArguments()).getName();
        String email = OTPFragmentArgs.fromBundle(getArguments()).getEmail();

        NavHostFragment navHostFragment =(NavHostFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {

                NumberFormat f = new DecimalFormat("00");

                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;

                resendCounter.setText("Resend in "+f.format(min) + ":" + f.format(sec));
            }

            public void onFinish() {
                resendCounter.setText("00:00");
            }
        }.start();

        enterWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navHostFragment.getNavController().navigate(OTPFragmentDirections.actionOTPFragmentToSigninFragment());
            }
        });

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
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user_id",user_id);
                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.putString("mobile", mobile_passed);
                    editor.apply();
                    Toast.makeText(getContext(), "Correct OTP", Toast.LENGTH_SHORT).show();
                    NavHostFragment navHostFragment =(NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host);
                    navHostFragment.getNavController().navigate(OTPFragmentDirections.actionOTPFragmentToFavoriteFragment());
                    navigationView.getMenu().findItem(R.id.signin).setTitle("Sign Out");
                }
                else{
                    Toast.makeText(getContext(), "Wrong OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}