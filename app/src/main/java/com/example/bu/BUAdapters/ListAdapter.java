package com.example.bu.BUAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bu.R;
import com.example.bu.viewModel.FriendsViewModel;
import com.example.bu.viewModel.NotificationsViewModel;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<String> friends = new ArrayList<>();
    private FriendsViewModel friendModel;
    private NotificationsViewModel notificationsViewModel;
    public ListAdapter(ArrayList<String> friends, FriendsViewModel friendsViewModel, NotificationsViewModel notificationsViewModel){
        this.friends = friends;
        this.friendModel = friendsViewModel;
        this.notificationsViewModel = notificationsViewModel;

    }
    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_friends, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        TextView text = holder.itemView.findViewById(R.id.usernameText);
        Button button = holder.itemView.findViewById(R.id.friendsButton);
        text.setText(friends.get(position));

//        //this will check to see if they already are friends
        if(friendModel.contains(position)){
            button.setEnabled(false);
            button.setText("Friends");
        }

        if(friendModel.alreadyClicked(position)){
            button.setEnabled(false);
            button.setText("Waiting to be friends");
        }
//
        //this will be if clicked to be friends
        button.setOnClickListener((view) -> {
            button.setEnabled(false);
            button.setText("Waiting to be friends");
            friendModel.sendFriendRequest(position);
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
