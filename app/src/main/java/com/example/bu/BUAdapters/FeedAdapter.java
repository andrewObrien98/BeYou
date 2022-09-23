package com.example.bu.BUAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bu.R;
import com.example.bu.model.Post;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>{
    private ObservableList<Post> posts;
    private Context context;
    private int max = 0;
    private AdView ads;
    private ArrayList<Integer> positionList = new ArrayList<>();
    private ArrayList<String> adList = new ArrayList<>();


    public FeedAdapter(){}
    public FeedAdapter(ObservableList<Post> posts, Context context){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_feed, parent, false);
        return new FeedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        TextView date = holder.itemView.findViewById(R.id.text_view_feed_date);
        TextView username = holder.itemView.findViewById(R.id.text_view_feed_username);
        TextView message = holder.itemView.findViewById(R.id.text_view_feed_post);
        date.setText(post.getFormattedDate());
        username.setText(post.getCreator());
        message.setText(post.getPost());
        Log.d("__Position_Value", position + "");
        boolean value = false;

//        if(!positionList.contains(position) && (position % 7 == 0 && position != 0)){
//            positionList.add(position);
//            adList.add(post.getFormattedDate());
//            Log.d("__Position_Value", "add for " + post.getFormattedDate());
//            value = true;
//        }
        AdView ads = holder.itemView.findViewById(R.id.banner_ad_recycler_view);
//        if (value && adList.contains(post.getFormattedDate())){
//
//        } else {
//
//        }
//        else {
//            ConstraintLayout layout = holder.itemView.findViewById(R.id.constraint_layout_feed);
//            LinearLayout linearLayout = holder.itemView.findViewById(R.id.linear_layout_feed);
//            layout.removeView(linearLayout);
//        }

        if(position % 7 == 0 && position != 0) {
            Log.d("__Position_Value", "I made it to the add loading place at ad " + post.getFormattedDate());
            MobileAds.initialize(context);
            ads.setVisibility(View.VISIBLE);
            ads.loadAd(new AdRequest.Builder().build());
        } else {
            ads.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        if(posts == null){
            return 0;
        }
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
