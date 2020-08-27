package com.example.aakar.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aakar.R;
import com.example.aakar.activity_order_notification;
import com.example.aakar.models.ordersSP;

public class orderSPAdapter extends RecyclerView.Adapter<orderSPAdapter.CustomViewHolder> {

    private Context context;
    private ordersSP[] data;

    public orderSPAdapter(Context context, ordersSP[] data) {

        this.context = context;
        this.data = data;

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.order_sr_final, viewGroup, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, final int i) {

        ordersSP list = data[i];
        customViewHolder.Title.setText(list.getSpname());
        customViewHolder.price.setText(list.getPrice());
        customViewHolder.date.setText(list.getCreatedAt());
        customViewHolder.orderIdSR.setText(list.getId().toString());

        String status = list.getStatus();
        Log.e("order status", status);
        String statusW = "";
        if(status.contains("1")){
            statusW = "Accepted";
        }
        if(status.contains("2")){
            statusW = "Declined";
        }
        if(status.contains("0")){
            statusW = "Pending";
        }
        customViewHolder.status.setText(statusW);

        customViewHolder.card.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, activity_order_notification.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView Title, date, price, status, orderIdSR;
        CardView card;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.tv_orderBySR);
            date = itemView.findViewById(R.id.tv_dateSR);
            price = itemView.findViewById(R.id.tv_priceSR);
            orderIdSR = itemView.findViewById(R.id.tv_orderIDSR);
            status = itemView.findViewById(R.id.newStatus);
            card = itemView.findViewById(R.id.srsp);

        }
    }

}

