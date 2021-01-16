package com.veggiegram.ui.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.veggiegram.R;

public class SigninFragment extends Fragment {
TextView createNewAcc;
    public SigninFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        createNewAcc = view.findViewById(R.id.createNewAcc);
        NavHostFragment navHostFragment =(NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host);

        createNewAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navHostFragment.getNavController().navigate(SigninFragmentDirections.actionSigninFragmentToSignUpFragment2());
            }
        });

        return view;
    }
}