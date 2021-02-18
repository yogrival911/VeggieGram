package com.veggiegram.ui.wallet;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.veggiegram.BottomSheetDialog;
import com.veggiegram.R;
import com.veggiegram.responses.wallet.WalletResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;
import com.veggiegram.ui.favourite.FavouriteFragmentDirections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class WalletFragment extends Fragment {
Button addMoney;
SharedPreferences sharedPreferences;
TextView tvWalletMoney;
ConstraintLayout minimize,more;
ImageView drop;
    public WalletFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        addMoney = view.findViewById(R.id.addMoney);
        tvWalletMoney = view.findViewById(R.id.tvWalletMoney);
        minimize = view.findViewById(R.id.minimize);
        more = view.findViewById(R.id.more);
        drop = view.findViewById(R.id.drop);

        Boolean isMore = false;

        if(isMore){
            more.setVisibility(View.VISIBLE);
        }
        else{
            more.setVisibility(View.GONE);
        }

        minimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(more.getVisibility()==View.GONE){
//                    TransitionManager.beginDelayedTransition(linearLayout,new AutoTransition());
//                    recyclerView.animate().translationY(recyclerView.getHeight());
                    more.setVisibility(View.VISIBLE);
                    drop.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                }
                else {
//                    TransitionManager.beginDelayedTransition(linearLayout,new AutoTransition());
                    more.setVisibility(View.GONE);
                    drop.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }
            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = sharedPreferences.getString("user_id", "");

        Retrofit retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);
        NavHostFragment navHostFragment =(NavHostFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host);
        if(user_id.isEmpty()){
            navHostFragment.getNavController().navigate(WalletFragmentDirections.actionWalletFragmentToSigninFragment());
        }
        else{
            retrofitIInterface.getUserWallet(user_id).enqueue(new Callback<WalletResponse>() {
                @Override
                public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                    tvWalletMoney.setText(response.body().getData().get(0).getAmount()+"");
                }

                @Override
                public void onFailure(Call<WalletResponse> call, Throwable t) {

                }
            });

            addMoney.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                    bottomSheetDialog.show(getActivity().getSupportFragmentManager(),"ModelBottomSheet");

                }
            });

        }


        return view;
    }
}