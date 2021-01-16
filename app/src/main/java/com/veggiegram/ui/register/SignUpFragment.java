package com.veggiegram.ui.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veggiegram.R;

public class SignUpFragment extends Fragment {
TextView alreadyUser;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        alreadyUser = view.findViewById(R.id.alreadyUser);
        NavHostFragment navHostFragment =(NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host);

        alreadyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navHostFragment.getNavController().navigate(SignUpFragmentDirections.actionSignUpFragment2ToSigninFragment());

            }
        });

    return view;
    }
}