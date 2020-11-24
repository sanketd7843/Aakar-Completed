package com.akar.aakar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.akar.aakar.R;
import com.akar.aakar.models.NotificationModel;

public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.CustomViewHolder> {

    private Context context;
    private NotificationModel[] data;

    public notificationAdapter(Context context, NotificationModel[] data) {

        this.context = context;
        this.data = data;

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.notificationfinal, viewGroup, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, final int i) {

        NotificationModel list = data[i];
        customViewHolder.title.setText(list.getNotificationTitle());
        customViewHolder.body.setText(list.getNotificationBody());

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView title, body;
        CardView card;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_titleNotification);
            body = itemView.findViewById(R.id.tv_bodyNotification);
            card = itemView.findViewById(R.id.noti);

        }
    }

}

