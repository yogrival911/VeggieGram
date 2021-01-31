package com.veggiegram.ui.wallet;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.veggiegram.BottomSheetDialog;
import com.veggiegram.R;


public class WalletFragment extends Fragment {
Button addMoney;
SharedPreferences sharedPreferences;
    public WalletFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        addMoney = view.findViewById(R.id.addMoney);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                bottomSheetDialog.show(getActivity().getSupportFragmentManager(),"ModelBottomSheet");
            }
        });

        return view;
    }
}