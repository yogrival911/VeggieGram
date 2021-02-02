package com.veggiegram;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.veggiegram.responses.order.OrderResponse;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
OrderResponse orderResponse;

    public OrderAdapter(OrderResponse orderResponse) {
        this.orderResponse = orderResponse;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.orderID.setText(orderResponse.getData().get(position).getOrderid()+"");
//        holder.orderStatus.setText(orderResponse.getData().get(position).getDeliveryStatus()+"");
        holder.paymentMethod.setText(orderResponse.getData().get(position).getPaymentMethod()+"");
        holder.amount.setText("\u20B9"+orderResponse.getData().get(position).getTotal()+"");
//        holder.tvDay.setText(orderResponse.getData().get(position).getDeliveredDate().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), OrderDetailActivity.class);
                intent.putExtra("order_id", orderResponse.getData().get(position).getOrderId()+"");
                intent.putExtra("date", orderResponse.getData().get(position).getDeliveredDate()+"");
                intent.putExtra("total_amount", orderResponse.getData().get(position).getTotal()+"");
                intent.putExtra("payment_method", orderResponse.getData().get(position).getPaymentMethod()+"");
                intent.putExtra("payment_status", orderResponse.getData().get(position).getPaymentStatusText()+"");
                intent.putExtra("address", orderResponse.getData().get(position).getAddress()+"");
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderResponse.getData().size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderID,orderStatus,paymentMethod,amount, tvDay;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderID = itemView.findViewById(R.id.orderID);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            paymentMethod = itemView.findViewById(R.id.orderStatus);
            amount = itemView.findViewById(R.id.amount);
            tvDay = itemView.findViewById(R.id.tvDay);
        }
    }
}
