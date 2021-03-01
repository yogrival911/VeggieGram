package com.veggiegram.ui.favourite;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veggiegram.R;

public class FavouriteFragment extends Fragment {
    SharedPreferences sharedPreferences;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = sharedPreferences.getString("user_id","");

        NavHostFragment navHostFragment =(NavHostFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host);
//        navHostFragment.getNavController().navigate(FavouriteFragmentDirections.actionFavoriteFragmentToSignUpFragment2());

        if(user_id.isEmpty()){
            navHostFragment.getNavController().navigate(FavouriteFragmentDirections.actionFavoriteFragmentToSigninFragment());
        }
        else{
            navHostFragment.getNavController().navigate(FavouriteFragmentDirections.actionFavoriteFragmentToWishListFragment());
        }
       return view;
    }


}