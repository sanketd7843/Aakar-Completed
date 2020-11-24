package com.akar.aakar.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.akar.aakar.R;
import com.akar.aakar.activityBookFinalSP;
import com.akar.aakar.models.getSP;

public class allSPAdapter extends RecyclerView.Adapter<allSPAdapter.CustomViewHolder> {

    private Context context;
    private getSP[] data;

    public allSPAdapter(Context context, getSP[] data){

        this.context = context;
        this.data = data;

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.all_sp, viewGroup, false);

        return new CustomViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, final int i) {

        getSP list = data[i];
        customViewHolder.Title.setText(list.getFullName().toUpperCase());
        customViewHolder.city.setText(list.getCity().toString());
        customViewHolder.price.setText(list.getHourlyRate().toString());
        customViewHolder.card.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getSP list = data[i];
                activityBookFinalSP.get_name = list.getFullName();
                activityBookFinalSP.get_address = list.getCity();
                activityBookFinalSP.get_email = list.getEmail();
                activityBookFinalSP.get_phono = list.getMobileNo().toString();
                activityBookFinalSP.get_id = list.getId().toString();
                activityBookFinalSP.get_price = list.getHourlyRate().toString();
                activityBookFinalSP.get_city = list.getCity();
                activityBookFinalSP.getGet_hourlyrate = list.getHourlyRate().toString();
                activityBookFinalSP.get_dailyRate = list.getDailyRate();
                activityBookFinalSP.get_weeklyRate = list.getWeeklyRate();
                activityBookFinalSP.get_monthlyRate = list.getMonthlyRate();
                activityBookFinalSP.getQualification = list.getQualification();
                activityBookFinalSP.getTypeOfService = list.getTypeOfService();

                Intent intent = new Intent(context, activityBookFinalSP.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView Title, price, city;
        CardView card;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.tv_nameAllSP);
            price = itemView.findViewById(R.id.tv_priceAllSP);
            city = itemView.findViewById(R.id.tv_cityAllSP);
            card = itemView.findViewById(R.id.cardallsp);
        }
    }

}

