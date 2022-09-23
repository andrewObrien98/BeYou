package com.example.bu.viewModel;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bu.model.Friends;
import com.example.bu.model.Post;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileViewModel extends ViewModel {

    private FirebaseFirestore db;
    private ObservableArrayList<Post> postList = new ObservableArrayList<>();
    public ProfileViewModel(){
        db = FirebaseFirestore.getInstance();
    }

    public void getPost(String username){
        postList.clear();
        db.collection("Posts").whereEqualTo("creator", username).orderBy("date").get().addOnCompleteListener((task) -> {
            if(task.isSuccessful()){
                QuerySnapshot collection = task.getResult();
                postList.addAll(collection.toObjects(Post.class));
            }
        });
    }


    public ObservableArrayList<Post> getPostList() {
        return postList;
    }

    public void setPostList(ObservableArrayList<Post> postList) {
        this.postList = postList;
    }


}
