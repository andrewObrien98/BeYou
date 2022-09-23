package com.example.bu.BUAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bu.R;
import com.example.bu.model.Notifications;
import com.example.bu.viewModel.NotificationsViewModel;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private ArrayList<String> listOfNotifications;
    private NotificationsViewModel viewModel;

    public NotificationsAdapter(ArrayList<String> listOfNotifications, NotificationsViewModel viewModel){
        this.listOfNotifications = listOfNotifications;
        this.viewModel = viewModel;
    }
    @NonNull
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_notifications, parent, false);
        return new NotificationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.ViewHolder holder, int position) {
        TextView text = holder.itemView.findViewById(R.id.text_view_notification_name);
        Button button = holder.itemView.findViewById(R.id.button_become_friends);

        text.setText(listOfNotifications.get(position));

        button.setOnClickListener((view) -> {
            button.setEnabled(false);
            button.setText("Friends");
            viewModel.addFriend(listOfNotifications.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return listOfNotifications.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
