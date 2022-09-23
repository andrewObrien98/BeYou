package com.example.bu.BUAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bu.R;
import com.example.bu.model.Post;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder>{
    private ObservableList<Post> list;

    public ProfileAdapter(){}
    public ProfileAdapter(ObservableList<Post> posts){
        list = posts;
    }
    @NonNull
    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_profile_posts, parent, false);
        return new ProfileAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.ViewHolder holder, int position) {
        TextView date = holder.itemView.findViewById(R.id.dateText);
        TextView message = holder.itemView.findViewById(R.id.text_view_profile_message);
        date.setText(list.get(list.size() - 1 - position).getFormattedDate());
        message.setText(list.get(list.size() - position - 1).getPost());
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
