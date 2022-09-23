package com.example.bu.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bu.model.BUuser;
import com.example.bu.model.Friends;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class CreateUserViewModel extends ViewModel {
    FirebaseFirestore db;
    FirebaseUser currAuthUser;
    private ArrayList<BUuser> currentUser = new ArrayList<>();
    private MutableLiveData<String> status = new MutableLiveData<>();
    private MutableLiveData<Boolean> invalidFields = new MutableLiveData<>();
    private MutableLiveData<Boolean> userCreated = new MutableLiveData<>();


    public CreateUserViewModel(){
        db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        currAuthUser = auth.getCurrentUser();
    }

    public void createUser(String firstName, String lastName, String username, String date){
        if (firstName.equals("") || lastName.equals("") || username.equals("") || date.equals("")){
            status.setValue("Cannot leave fields blank");
            invalidFields.setValue(true);
        } else if (date.length() != 10){
            status.setValue("Incorrect Date format: Please try again");
            invalidFields.setValue(true);
        }
        else {
            userNameAlreadyTaken(firstName, lastName, username, date);
        }
    }

    public void userNameAlreadyTaken(String firstName, String lastName, String username, String date){
        db.collection("BU users 2.0").whereEqualTo("username", username)
                .get().addOnCompleteListener((task) -> {
            if(task.isSuccessful()) {
                QuerySnapshot collection = task.getResult();
                if (collection.isEmpty()) {
                    finishCreatingUser(firstName, lastName, username, date);
                } else {
                    status.setValue("Username " + username + " is already taken");
                    invalidFields.setValue(true);
                }
            } else {
                status.setValue(task.getException().toString());
                invalidFields.setValue(true);
            }
        });

    }

    public void finishCreatingUser(String firstName, String lastName, String username, String date){
        BUuser newUser = new BUuser(firstName, lastName, username, date, currAuthUser.getUid(), currAuthUser.getEmail());
        db.collection("BU users 2.0").document(newUser.getEmail()).set(newUser).addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                currentUser.add(newUser); //this is mainly here for if we update so that way you can update and delete
                //this is to create for this user his friend group
                createFriendGroup(username);
                userCreated.setValue(true);
            } else {
                status.setValue(Objects.requireNonNull(task.getException()).toString());
                invalidFields.setValue(true);
            }
        });
    }

    public BUuser getCurrentUser(){
        return currentUser.get(currentUser.size() - 1);
    }

    public MutableLiveData<String> getStatus() {
        return status;
    }

    public void setStatus(String message) {
        this.status.setValue(message);
    }

    public MutableLiveData<Boolean> getInvalidFields() {
        return invalidFields;
    }

    public void setInvalidFields(boolean value) {
        this.invalidFields.setValue(value);
    }

    public MutableLiveData<Boolean> getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(boolean value) {
        this.userCreated.setValue(value);
    }

    public void createFriendGroup(String username){
        Friends friend = new Friends(username);
        friend.addFriends(username);
        db.collection("Friends").document(username).set(friend).addOnCompleteListener((task -> {
            Log.d("__friends", "Friend added to database");
        }));
    }
}
