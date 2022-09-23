package com.example.bu.viewModel;

import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.ViewModel;

import com.example.bu.model.Friends;
import com.example.bu.model.Post;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;

public class FeedViewModel extends ViewModel {

    private FirebaseFirestore db;
    private Friends friends;
    private ArrayList<Post> allPosts = new ArrayList<>();
    private ObservableArrayList<Post> posts = new ObservableArrayList<>();
    public FeedViewModel(){
        db = FirebaseFirestore.getInstance();
    }

    public void getFriendList(String username){
        db.collection("Friends").whereEqualTo("username", username).get().addOnCompleteListener((task) -> {
           if(task.isSuccessful()){
               QuerySnapshot collection = task.getResult();
               friends = collection.toObjects(Friends.class).get(0);
               getFriendsPosts();
           }
        });
    }

    public void getFriendsPosts(){
        allPosts.clear();
        ArrayList<String> gotPostsFromFriend = new ArrayList<>();
        Log.d("__FEED", "I have this many friends " + friends.getFriends().size());
        for(int i = 0; i < friends.getFriends().size(); i++){
            db.collection("Posts").whereEqualTo("creator", friends.getFriends().get(i)).get().addOnCompleteListener((task) -> {
                if(task.isSuccessful()){
                    QuerySnapshot collection = task.getResult();
                    allPosts.addAll(collection.toObjects(Post.class));
                    if(allPosts.size() > 0) {
                        gotPostsFromFriend.add(allPosts.get(allPosts.size() - 1).getCreator());
                        if (gotPostsFromFriend.size() == friends.getFriends().size()) {
                            showPosts();
                        }
                    }
                }

            });
        }
    }

    public void showPosts() {
        posts.clear();
        allPosts.sort((o1, o2) -> {
            if (o1.getDate() > o2.getDate()) {
                return -1;
            } else if (o1.getDate() == o2.getDate()) {
                return 0;
            }
            return 1;
        });
        posts.addAll(allPosts);
        Log.d("__FEED", "I have this many Posts " + posts.size());
    }

    public ObservableArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ObservableArrayList<Post> posts) {
        this.posts = posts;
    }


}
