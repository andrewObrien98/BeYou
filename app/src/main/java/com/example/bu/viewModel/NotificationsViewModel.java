package com.example.bu.viewModel;

import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bu.model.Friends;
import com.example.bu.model.Notifications;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class NotificationsViewModel extends ViewModel {
    private Friends currentUserFriends;
    private Friends fromFriends;
    private FirebaseFirestore db;
    private MutableLiveData<Boolean> loadedFriendList = new MutableLiveData<>();
    private MutableLiveData<Boolean> getUsersNotifications = new MutableLiveData<>();
    private ObservableArrayList<String> listOfFriendRequests = new ObservableArrayList<>();
    private ArrayList<Notifications> listOfNotifications = new ArrayList<>();
    public NotificationsViewModel(){
        db = FirebaseFirestore.getInstance();
        loadedFriendList.setValue(false);
        getUsersNotifications.setValue(false);
    }

    public void loadCurrentUserFriends(String username){
        listOfFriendRequests.clear();
        listOfNotifications.clear();
        db.collection("Friends").whereEqualTo("username", username).get().addOnCompleteListener((task) -> {
            if(task.isSuccessful()){
                QuerySnapshot collection = task.getResult();
                currentUserFriends = collection.toObjects(Friends.class).get(0);
                loadedFriendList.setValue(true);
            }
        });
    }

    public void loadUsersNotifications(){
        db.collection("Notifications 2.0").whereEqualTo("to", currentUserFriends.getUsername()).get().addOnCompleteListener((task) -> {
            if(task.isSuccessful()){
                QuerySnapshot collection = task.getResult();
                listOfNotifications.addAll(collection.toObjects(Notifications.class));
                getUsersNotifications.setValue(true);
            }
        });
    }

    public void loadNotificationsList(){
        ArrayList<String> friendRequests = new ArrayList<>();
        for(Notifications notification : listOfNotifications){
            if(!currentUserFriends.hasFriend(notification.getFrom())){
                friendRequests.add(notification.getFrom());
                Log.d("__Notifications", notification.getFrom() + " sent a request");
            }
        }
        listOfFriendRequests.addAll(friendRequests);
        Log.d("__Notifications", " sent a thing to the recycler view");
    }

    public void addFriend(String username){
        db.collection("Friends").whereEqualTo("username", username).get().addOnCompleteListener((task) -> {
            if(task.isSuccessful()){
                QuerySnapshot collection = task.getResult();
                fromFriends = collection.toObjects(Friends.class).get(0);
                addFriendToFriendList(username);
            }
        });
    }

    public void addFriendToFriendList(String fromUsername){
        fromFriends.addFriends(currentUserFriends.getUsername());
        currentUserFriends.addFriends(fromUsername);

        db.collection("Friends").document(currentUserFriends.getUsername()).delete().addOnCompleteListener((task) -> {
            db.collection("Friends").document(currentUserFriends.getUsername()).set(currentUserFriends).addOnCompleteListener((task2 -> {

            }));
        });
        db.collection("Friends").document(fromUsername).delete().addOnCompleteListener((task) -> {
            db.collection("Friends").document(fromUsername).set(fromFriends).addOnCompleteListener((task2 -> {

            }));
        });

        db.collection("Notifications 2.0").document(fromUsername + currentUserFriends.getUsername()).delete().addOnCompleteListener((task -> {

        }));
    }


    public MutableLiveData<Boolean> getLoadedFriendList() {
        return loadedFriendList;
    }

    public void setLoadedFriendList(Boolean value) {
        this.loadedFriendList.setValue(value);
    }

    public MutableLiveData<Boolean> getGetUsersNotifications() {
        return getUsersNotifications;
    }

    public void setGetUsersNotifications(Boolean value) {
        this.getUsersNotifications.setValue(value);
    }

    public ObservableArrayList<String> getListOfFriendRequests() {
        return listOfFriendRequests;
    }

    public void setListOfFriendRequests(ObservableArrayList<String> listOfFriendRequests) {
        this.listOfFriendRequests = listOfFriendRequests;
    }
}
