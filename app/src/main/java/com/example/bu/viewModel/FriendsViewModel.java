package com.example.bu.viewModel;

import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bu.model.BUuser;
import com.example.bu.model.Friends;
import com.example.bu.model.Notifications;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class FriendsViewModel extends ViewModel {
    private FirebaseAuth auth;
    private FirebaseUser currAuthUser;
    private FirebaseFirestore db;
    private ObservableArrayList<BUuser> listOfPeopleWithThatName = new ObservableArrayList<>();
    private MutableLiveData<Boolean> noPeopleWithThatName = new MutableLiveData<>();
    private Friends currentFriendList;
    private MutableLiveData<Boolean> getFriendList = new MutableLiveData<>();
    private MutableLiveData<Boolean> successfullyGotFriendList = new MutableLiveData<>();
    private MutableLiveData<Boolean> didntGetFriendList = new MutableLiveData<>();
    private ArrayList<String> listOfFriendRequestsMade = new ArrayList<>();



    public FriendsViewModel(){
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
    }

    public void loadCurrentFriendList(String username){
        db.collection("Friends").whereEqualTo("username", username).get().addOnCompleteListener((task) -> {
            if(task.isSuccessful()){
                QuerySnapshot collection = task.getResult();
                currentFriendList = collection.toObjects(Friends.class).get(0);
                successfullyGotFriendList.setValue(true);
            } else {
                didntGetFriendList.setValue(true);
            }
        });
    }

    public void findPeopleWithName(String firstName){
        //this is where we get all the users with this username
        if(!listOfPeopleWithThatName.isEmpty()){
            listOfPeopleWithThatName.remove(0);
        }
        db.collection("BU users 2.0").whereEqualTo("firstName", firstName)
                .get().addOnCompleteListener((task) -> {
            if(task.isSuccessful()) {
                QuerySnapshot collection = task.getResult();
                if(!collection.isEmpty()){
                    Log.d("__friends", "Do I make it to here 1?");
                    listOfPeopleWithThatName.addAll(collection.toObjects(BUuser.class));
                } else {
                    Log.d("__friends", "Do I make it to here 3?");
                    setNoPeopleWithThatName(true);
                }
            }
        });
    }

    public boolean contains(int position){
        return currentFriendList.hasFriend(listOfPeopleWithThatName.get(position).getUsername());
    }

    public void sendFriendRequest(int position){
        listOfFriendRequestsMade.add(listOfPeopleWithThatName.get(position).getUsername());
        Notifications notification = new Notifications(listOfPeopleWithThatName.get(position).getUsername(), currentFriendList.getUsername());
        db.collection("Notifications 2.0").document(notification.getFrom() + notification.getTo()).set(notification).addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                Log.d("__friends", "friend request was sent");
            }
        });
    }

    public boolean alreadyClicked(int position){
        return listOfFriendRequestsMade.contains(listOfPeopleWithThatName.get(position).getUsername());
    }


    public ObservableArrayList<BUuser> getListOfPeopleWithThatName() {
        return listOfPeopleWithThatName;
    }

    public ArrayList<String> getListOfPeople(){
        ArrayList<String> list = new ArrayList<>();
        for(BUuser user : listOfPeopleWithThatName){
            list.add(user.getFirstName());
        }
        return list;
    }

    public void resetListOfFriendRequestsMade(){
        listOfFriendRequestsMade = new ArrayList<>();
    }

    public MutableLiveData<Boolean> getNoPeopleWithThatName() {
        return noPeopleWithThatName;
    }

    public void setNoPeopleWithThatName(Boolean value) {
        this.noPeopleWithThatName.setValue(value);
    }

    public MutableLiveData<Boolean> getGetFriendList() {
        return getFriendList;
    }

    public void setGetFriendList(Boolean value) {
        this.getFriendList.setValue(value);
    }

    public MutableLiveData<Boolean> getSuccessfullyGotFriendList() {
        return successfullyGotFriendList;
    }

    public void setSuccessfullyGotFriendList(Boolean value) {
        this.successfullyGotFriendList.setValue(value);
    }

    public MutableLiveData<Boolean> getDidntGetFriendList() {
        return didntGetFriendList;
    }

    public void setDidntGetFriendList(Boolean value) {
        this.didntGetFriendList.setValue(value);
    }
}
