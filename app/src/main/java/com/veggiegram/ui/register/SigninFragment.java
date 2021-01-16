package com.veggiegram.ui.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.veggiegram.R;
import com.veggiegram.responses.SigninObject;
import com.veggiegram.responses.login.LoginResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SigninFragment extends Fragment {
TextView createNewAcc;
EditText etLoginMobile;
Button sendOTP;
    public SigninFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        createNewAcc = view.findViewById(R.id.createNewAcc);
        etLoginMobile = view.findViewById(R.id.etLoginMobile);
        sendOTP = view.findViewById(R.id.sendOTP);

        NavHostFragment navHostFragment =(NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host);

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = etLoginMobile.getText().toString();
                retrofitIInterface.signin(new SigninObject(mobile)).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                       LoginResponse f = response.body();
                       String user_id = response.body().getData().get(0).getUserid().toString();
                       if(response.body().getSuccess()){
                           navHostFragment.getNavController().navigate(SigninFragmentDirections.actionSigninFragmentToOTPFragment(mobile, user_id));
                       }
                       else{
                           Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                       }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });
            }
        });

        createNewAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navHostFragment.getNavController().navigate(SigninFragmentDirections.actionSigninFragmentToSignUpFragment2());
            }
        });

        return view;
    }
}