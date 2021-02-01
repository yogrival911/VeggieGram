package com.veggiegram.responses;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.razorpay.Checkout;
import com.veggiegram.R;
import com.veggiegram.responses.slot.SlotResponse;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.SlotViewHolder> {
    SlotResponse slotResponse;
    int checkedPosition = 0;
    public SlotAdapter(SlotResponse slotResponse) {
        this.slotResponse = slotResponse;
    }

    @NonNull
    @Override
    public SlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slot_layout, parent, false);
        return new SlotAdapter.SlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlotViewHolder holder, int position) {
        holder.timeSlot.setText(slotResponse.getData().get(position).getSlot()+"");

        if (checkedPosition == -1) {
            holder.conLayout.setBackgroundColor(Color.WHITE);
            holder.checkBoxTime.setChecked(false);
        } else {
            if (checkedPosition == holder.getAdapterPosition()) {
                holder.conLayout.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.addressColor));
                holder.checkBoxTime.setChecked(true);
            } else {
                holder.conLayout.setBackgroundColor(Color.WHITE);
                holder.checkBoxTime.setChecked(false);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.conLayout.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.addressColor));
                holder.checkBoxTime.setChecked(true);
                if (checkedPosition != holder.getAdapterPosition()) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = holder.getAdapterPosition();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return slotResponse.getData().size();
    }

    public class SlotViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxTime;
        ConstraintLayout conLayout;
        TextView timeSlot;
        public SlotViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBoxTime = itemView.findViewById(R.id.checkBoxTime);
            timeSlot = itemView.findViewById(R.id.timeSlot);
            conLayout = itemView.findViewById(R.id.conLayout);
        }
    }
}
