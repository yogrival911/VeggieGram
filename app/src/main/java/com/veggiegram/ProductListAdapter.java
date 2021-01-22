package com.veggiegram;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.veggiegram.responses.RemoveWishListResponse;
import com.veggiegram.responses.WishListObject;
import com.veggiegram.responses.productlistcat.ProductListByCatResponse;
import com.veggiegram.responses.wishlist.WishListResponse;
import com.veggiegram.retrofit.RetrofitClientInstance;
import com.veggiegram.retrofit.RetrofitIInterface;
import com.veggiegram.util.LoadWithGlide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.PLViewHolder> {
ProductListByCatResponse productListByCatResponse;
ClickInterface clickInterface;
ClickCartInterface clickCartInterface;
int wishlisted=0;
int updatedCart=0;

    public ProductListAdapter(ProductListByCatResponse productListByCatResponse, ClickInterface clickInterface, ClickCartInterface clickCartInterface) {
        this.productListByCatResponse = productListByCatResponse;
        this.clickInterface = clickInterface;
        this.clickCartInterface = clickCartInterface;
    }

    public void setProductListByCatResponse(ProductListByCatResponse productListByCatResponse) {
        this.productListByCatResponse = productListByCatResponse;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductListAdapter.PLViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);

        return new ProductListAdapter.PLViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.PLViewHolder holder, int position) {

        holder.productName.setText(productListByCatResponse.getData().get(position).getName());
        holder.quantity.setText(productListByCatResponse.getData().get(position).getUnit()+" "+ productListByCatResponse.getData().get(position).getUnitname());

        int price = productListByCatResponse.getData().get(position).getPrice();
        int sellPrice = productListByCatResponse.getData().get(position).getSellprice();

        int saving = price-sellPrice;
        if(saving>0){
            holder.pSave.setText("You Save " + "\u20B9"+ saving);
        }

        holder.price.setText("\u20B9" + sellPrice);
        holder.pActualPrice.setText("\u20B9"+ price);
        holder.pActualPrice.setPaintFlags(holder.pActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        wishlisted = productListByCatResponse.getData().get(position).getWhishlisted();
        if(wishlisted>0){
            holder.fav.setImageResource(R.drawable.red_heart);
        }

        String img = productListByCatResponse.getData().get(position).getImage();
        String url = "https://admin.veggiegram.in/adminuploads/products/" + img;

        LoadWithGlide.loadImage(holder.ivProductImage,url,new CircularProgressDrawable(holder.itemView.getContext()));

        int cartQuantity = productListByCatResponse.getData().get(position).getCartquantity();
        if(cartQuantity>0){
            holder.addCartButton.setVisibility(View.GONE);
            holder.quantityLayout.setVisibility(View.VISIBLE);
            holder.cartQuantity.setText(String.valueOf(productListByCatResponse.getData().get(position).getCartquantity()));
        }


        final int[] clickcount = {0};
        holder.incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cartQuantity = productListByCatResponse.getData().get(position).getCartquantity();
                String productId = productListByCatResponse.getData().get(position).getProductid().toString();

//                updatedCart = cartQuantity+1;
//                holder.cartQuantity.setText(String.valueOf(updatedCart));
//                productListByCatResponse.getData().get(position).setCartquantity(updatedCart);

                clickCartInterface.increment(position,cartQuantity, productId);

                clickcount[0] = clickcount[0] +1;
                if(clickcount[0] ==1)
                {
                    //first time clicked to do this
                    Toast.makeText(holder.itemView.getContext(),"Button clicked first time!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //check how many times clicked and so on
                    Toast.makeText(holder.itemView.getContext(),"Button clicked count is"+ clickcount[0], Toast.LENGTH_LONG).show();

                }

            }
        });

        holder.decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cartQuantity = productListByCatResponse.getData().get(position).getCartquantity();
                String productId = productListByCatResponse.getData().get(position).getProductid().toString();
//                updatedCart = cartQuantity-1;
//                if(updatedCart<1){
//                    holder.quantityLayout.setVisibility(View.GONE);
//                    holder.addCartButton.setVisibility(View.VISIBLE);
//                }
//                holder.cartQuantity.setText(String.valueOf(updatedCart));
//                productListByCatResponse.getData().get(position).setCartquantity(updatedCart);
                clickCartInterface.decrement(position, cartQuantity,productId);
            }
        });

        holder.addCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.addCartButton.setVisibility(View.GONE);
                holder.quantityLayout.setVisibility(View.VISIBLE);
                updatedCart = updatedCart+1;
                holder.cartQuantity.setText(String.valueOf(updatedCart));
                clickCartInterface.clickAdd(position,productListByCatResponse.getData().get(position).getCartquantity());
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickInterface.click(position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(holder.itemView);
                navController.navigate(ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(productListByCatResponse.getData().get(position).getProductid().toString()));
            }
        });
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int wishlisted_in = productListByCatResponse.getData().get(position).getWhishlisted();
                Log.i("yog", String.valueOf(wishlisted));


               if(wishlisted_in>0){
                   holder.fav.setImageResource(R.drawable.add_to_fav);
                   String product_id = productListByCatResponse.getData().get(position).getProductid().toString();
                   SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(holder.itemView.getContext());
                   String user_id = sharedPreferences.getString("user_id","");

                   Retrofit retrofit = RetrofitClientInstance.getInstance();
                   RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);

                   retrofitIInterface.removeWishList(new WishListObject(product_id),user_id).enqueue(new Callback<RemoveWishListResponse>() {
                       @Override
                       public void onResponse(Call<RemoveWishListResponse> call, Response<RemoveWishListResponse> response) {
                           if(response.body().getSuccess()){

                               Toast.makeText(holder.itemView.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       }

                       @Override
                       public void onFailure(Call<RemoveWishListResponse> call, Throwable t) {

                       }
                   });
               }
               else{
                   holder.fav.setImageResource(R.drawable.red_heart);

                   String product_id = productListByCatResponse.getData().get(position).getProductid().toString();
                   SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(holder.itemView.getContext());
                   String user_id = sharedPreferences.getString("user_id","");

                   if(user_id.isEmpty()){
                       NavController navController = Navigation.findNavController(holder.itemView);
                       navController.navigate(ProductListFragmentDirections.actionProductListFragmentToSigninFragment());

                   }
                   else{
                       Retrofit retrofit = RetrofitClientInstance.getInstance();
                       RetrofitIInterface retrofitIInterface = retrofit.create(RetrofitIInterface.class);
                       retrofitIInterface.addToWishList(new WishListObject(product_id),user_id).enqueue(new Callback<WishListResponse>() {
                           @Override
                           public void onResponse(Call<WishListResponse> call, Response<WishListResponse> response) {
                               WishListResponse wishListResponse = response.body();
                               Toast.makeText(holder.itemView.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                           }

                           @Override
                           public void onFailure(Call<WishListResponse> call, Throwable t) {

                           }
                       });
                   }
               }

            }
        });
    }

    @Override
    public int getItemCount() {
        return productListByCatResponse.getData().size();
    }

    public class PLViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage,fav;
        TextView productName, quantity, price, pActualPrice,pSave;
        Button addCartButton;
        ImageView incrementButton, decrementButton;
        ConstraintLayout quantityLayout;
        EditText cartQuantity;
        public PLViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.pImage);
            productName = itemView.findViewById(R.id.pName);
            quantity = itemView.findViewById(R.id.pQuantity);
            price = itemView.findViewById(R.id.pSellPrice);
            pActualPrice = itemView.findViewById(R.id.pPrice);
            fav = itemView.findViewById(R.id.fav);
            pSave = itemView.findViewById(R.id.pSave);

            addCartButton = itemView.findViewById(R.id.cartButton);
            quantityLayout = itemView.findViewById(R.id.quanity_layout);
            incrementButton = itemView.findViewById(R.id.increment);
            decrementButton = itemView.findViewById(R.id.decrement);
            cartQuantity = itemView.findViewById(R.id.cartQuant);

        }
    }
}
