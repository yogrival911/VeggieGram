package com.veggiegram.ui.wallet;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.veggiegram.R;


public class WalletFragment extends Fragment {

SharedPreferences sharedPreferences;
    public WalletFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());


        return view;
    }
}