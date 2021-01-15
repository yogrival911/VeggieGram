package com.veggiegram.ui.home;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.veggiegram.R;
import com.veggiegram.responses.banner.BannerResponse;
import com.veggiegram.responses.category.CategoryResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;
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
    RecyclerView recyclerCategoryHome;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SliderView sliderView = view.findViewById(R.id.imageSlider);
        recyclerCategoryHome = view.findViewById(R.id.recyclerCategoryHome);

        retrofit = RetrofitClientInstance.getInstance();
        RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerCategoryHome.setLayoutManager(gridLayoutManager);


        SliderAdapterExample sliderAdapterExamples =  new SliderAdapterExample(getContext());
        sliderView.setSliderAdapter(sliderAdapterExamples);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
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

        retrofitIInterface.getCategoryList().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                Log.i("yog", response.body().toString());
                CategoryAdapter categoryAdapter = new CategoryAdapter(response.body());
                recyclerCategoryHome.setAdapter(categoryAdapter);
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });

        return view;
    }
}