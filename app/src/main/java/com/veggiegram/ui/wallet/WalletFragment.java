package com.veggiegram.ui.wallet;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.preference.PreferenceManager;
import android.util.Log;
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
import com.veggiegram.responses.wallethistory.WalletHistoryResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;
import com.veggiegram.ui.favourite.FavouriteFragmentDirections;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class WalletFragment extends Fragment {
Button addMoney;
SharedPreferences sharedPreferences;
TextView tvWalletMoney,transactionID,amountWallet,walletDes,walletDate;
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
        transactionID = view.findViewById(R.id.transactionID);
        amountWallet = view.findViewById(R.id.amountWallet);
        walletDes = view.findViewById(R.id.walletDes);
        walletDate = view.findViewById(R.id.walletDate);

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
                   if(response.body().getData().size() != 0){
                       tvWalletMoney.setText(response.body().getData().get(0).getAmount()+"");
                   }
                   else{
                       tvWalletMoney.setText("0");
                   }
                    retrofitIInterface.walletHistory(user_id).enqueue(new Callback<WalletHistoryResponse>() {
                        @Override
                        public void onResponse(Call<WalletHistoryResponse> call, Response<WalletHistoryResponse> response) {
                           if(response.body().getData().size() != 0){
                               Log.i("yogwallet", response.body().getData().get(0).getDescription());
                               transactionID.setText(response.body().getData().get(0).getTransectionId()+"");
                               amountWallet.setText("\u20B9"+ response.body().getData().get(0).getAmount()+"");
                               walletDes.setText(response.body().getData().get(0).getDescription());

                               SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                               SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM dd,yyyy");
                               Date date = null;
                               try {
                                   date = inputFormat.parse(response.body().getData().get(0).getDatetime());
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }
                               String formattedDate = outputFormat.format(date);
                               System.out.println(formattedDate);
                               Log.i("yogdate", formattedDate);
                               walletDate.setText(formattedDate+"");
                           }
                        }

                        @Override
                        public void onFailure(Call<WalletHistoryResponse> call, Throwable t) {

                        }
                    });
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