package com.veggiegram.ui.home;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.veggiegram.R;
import com.veggiegram.responses.banner.BannerResponse;
import com.veggiegram.responses.cartlist.GetCartListResponse;
import com.veggiegram.responses.category.CategoryResponse;
import com.veggiegram.responses.recommended.RecommededProductResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;
import com.veggiegram.util.CircleProgressBarCustom;
import com.veggiegram.util.SliderAdapterExample;
import com.veggiegram.util.SliderItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    Retrofit retrofit;
    RecyclerView recyclerCategoryHome,recyclerRecommended;
    CircleProgressBarCustom circularProgressBar;
    Boolean fromHome = true;
    TextView textCartItemCount;
    int mCartItemCount;
    FrameLayout frameLayout;
    LinearLayout linearLayout;
    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setHasOptionsMenu(true);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user_id = sharedPreferences.getString("user_id","");

        SliderView sliderView = view.findViewById(R.id.imageSlider);
        recyclerCategoryHome = view.findViewById(R.id.recyclerCategoryHome);
        recyclerRecommended = view.findViewById(R.id.recyclerRecommended);
        circularProgressBar = view.findViewById(R.id.circularProgressBar);
        frameLayout = view.findViewById(R.id.frameLayout);
        linearLayout = view.findViewById(R.id.linearLayout);

        linearLayout.setVisibility(View.INVISIBLE);
        retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerCategoryHome.setLayoutManager(gridLayoutManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerRecommended.setNestedScrollingEnabled(false);
        recyclerRecommended.setLayoutManager(linearLayoutManager);



        SliderAdapterExample sliderAdapterExamples =  new SliderAdapterExample(getContext());
        sliderView.setSliderAdapter(sliderAdapterExamples);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(getResources().getColor(R.color.purple_500));
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(5); //set scroll delay in seconds :
        sliderView.startAutoCycle();

        List<SliderItem> sliderItems = new ArrayList<>();
        retrofitIInterface.getBannerList().enqueue(new Callback<BannerResponse>() {
            @Override
            public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response) {
                Log.i("yog", response.body().toString());
                if(response.isSuccessful()){

                    for(int i = 0; i<response.body().getData().size(); i++){
                        String imgUrl = response.body().getData().get(i).getImage();
                        String actualUrl = "https://admin.veggiegram.in/adminuploads/banner/" + imgUrl;
//                        String v = "https://image.freepik.com/free-vector/online-shopping-banner-template-business-concept-sale-e-commerce_72460-168.jpg";
                        sliderItems.add(new SliderItem(actualUrl));
                    }
                    sliderAdapterExamples.renewItems(sliderItems);
                    sliderView.startAutoCycle();
                }
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        retrofitIInterface.getusercartlistproducts(user_id).enqueue(new Callback<GetCartListResponse>() {
            @Override
            public void onResponse(Call<GetCartListResponse> call, Response<GetCartListResponse> response) {
                mCartItemCount = response.body().getData().size();
                setupBadge();
            }

            @Override
            public void onFailure(Call<GetCartListResponse> call, Throwable t) {

            }
        });

        retrofitIInterface.getCategoryList().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                Log.i("yog", response.body().toString());
                circularProgressBar.clearAnimation();
                circularProgressBar.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
                CategoryAdapter categoryAdapter = new CategoryAdapter(response.body(), fromHome);
                recyclerCategoryHome.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });

        retrofitIInterface.getrecommendedproductslist().enqueue(new Callback<RecommededProductResponse>() {
            @Override
            public void onResponse(Call<RecommededProductResponse> call, Response<RecommededProductResponse> response) {
                if(response.body().getSuccess()){
                    RecommededProductResponse recommededProductResponse = response.body();
                    RecommendedAdapter recommendedAdapter = new RecommendedAdapter(response.body());
                    recyclerRecommended.setAdapter(recommendedAdapter);
                }
                else{
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RecommededProductResponse> call, Throwable t) {

            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.getItem(0);
        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        textCartItemCount.setText(String.valueOf(mCartItemCount));
    }

    public void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}