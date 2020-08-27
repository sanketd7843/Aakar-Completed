package com.example.aakar.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.aakar.models.ordersSR;

import static android.content.ContentValues.TAG;

public class orderSRAdapter extends RecyclerView.Adapter<orderSRAdapter.CustomViewHolder> {

    private Context context;
    SharedPreferences sp;
    SharedPreferences.Editor ed;

    private ordersSR[] data;

    public orderSRAdapter(Context context, ordersSR[] data) {

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
    public void onBindViewHolder(@NonNull final CustomViewHolder customViewHolder, final int i) {
        sp = context.getSharedPreferences("NurseApp", Context.MODE_PRIVATE);
        final ordersSR list = data[i];
        customViewHolder.Title.setText(list.getSpname());
        customViewHolder.price.setText(list.getPrice());
        customViewHolder.date.setText(list.getCreatedAt());
        customViewHolder.orderIdSR.setText(list.getId().toString());
        if(list.getStatus().contains("1")){
            if(sp.getBoolean("isSp", false) == true) {
                customViewHolder.paymentStat.setVisibility(View.VISIBLE);
            }
        }
        if (list.getIsCompleted().contains("1")){
            customViewHolder.completedStats.setVisibility(View.VISIBLE);
        }
        else{
            customViewHolder.completedStats.setVisibility(View.INVISIBLE);

        }

        customViewHolder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("name", list.getSrname());
                        activity_order_notification.get_name= list.getSrname();
                        activity_order_notification.get_order_id= list.getId().toString();
                        activity_order_notification.get_orderstatus = list.getOrderStatus();
                        activity_order_notification.get_phone_no = list.getReceiverMobile();
                        activity_order_notification.get_address = list.getReceiverCity();
                        activity_order_notification.getDescription = list.getDescriptionAbout();
                        activity_order_notification.getDate = list.getCreatedAt();
                        activity_order_notification.get_amount = list.getPrice();
                        if(sp.getBoolean("isSp", false) == true){
                            Intent intent = new Intent(context, activity_order_notification.class);
                            context.startActivity(intent);
                        }

            }
        });

        String status = list.getOrderStatus();
        //Log.e("order status", status);
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

        Log.e("iscompleted", list.getIsCompleted() );
        customViewHolder.status.setText(statusW);

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView Title, date, price, status, orderIdSR, paymentStat, completedStats;

        CardView card;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.tv_orderBySR);

            date = itemView.findViewById(R.id.tv_dateSR);
            price = itemView.findViewById(R.id.tv_priceSR);
            orderIdSR = itemView.findViewById(R.id.tv_orderIDSR);
            status = itemView.findViewById(R.id.newStatus);
            card = itemView.findViewById(R.id.srsp);
            paymentStat = itemView.findViewById(R.id.payStatus);
            completedStats = itemView.findViewById(R.id.completedStatus);

        }
    }

}

