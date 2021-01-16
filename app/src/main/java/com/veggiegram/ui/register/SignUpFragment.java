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

import com.veggiegram.R;
import com.veggiegram.responses.SignupObject;
import com.veggiegram.responses.signup.SignupResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpFragment extends Fragment {
TextView alreadyUser;
EditText etName,etEmail,etMobile;
Button signUp;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        alreadyUser = view.findViewById(R.id.alreadyUser);
        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etMobile = view.findViewById(R.id.etMobile);
        signUp = view.findViewById(R.id.signUp);

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

        NavHostFragment navHostFragment =(NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String number = etMobile.getText().toString();
                retrofitIInterface.signup(new SignupObject(name, email, number)).enqueue(new Callback<SignupResponse>() {
                    @Override
                    public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                        SignupResponse signupResponse = response.body();
                        String user_id = response.body().getData().toString();
                        navHostFragment.getNavController().navigate(SignUpFragmentDirections.actionSignUpFragment2ToOTPFragment(number,user_id));
                    }

                    @Override
                    public void onFailure(Call<SignupResponse> call, Throwable t) {

                    }
                });
            }
        });

        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navHostFragment.getNavController().navigate(SignUpFragmentDirections.actionSignUpFragment2ToSigninFragment());

            }
        });

    return view;
    }
}