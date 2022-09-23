package com.example.bu.viewModel;

import android.util.MutableBoolean;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bu.model.Post;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostViewModel extends ViewModel {
    FirebaseFirestore db;
    private MutableLiveData<Boolean> postCreated = new MutableLiveData<>();

    public PostViewModel(){
        db = FirebaseFirestore.getInstance();
        postCreated.setValue(false);
    }

    public void createPost(String username, String message){
        Post newPost = new Post(username ,message);

        db.collection("Posts").document(newPost.getFormattedDate()).set(newPost).addOnCompleteListener((task) -> {
           if(task.isSuccessful()){
               postCreated.setValue(true);
           }
        });

    }

    public MutableLiveData<Boolean> getPostCreated() {
        return postCreated;
    }

    public void setPostCreated(Boolean value) {
        this.postCreated.setValue(value);
    }
}
