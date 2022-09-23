package com.example.bu.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.bu.model.BUuser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LoginViewModel extends ViewModel {
    FirebaseAuth auth;
    FirebaseUser currAuthUser;
    FirebaseFirestore db;
    private MutableLiveData<Boolean> login = new MutableLiveData<>();
    private MutableLiveData<Boolean> invalidEmailOrPassword = new MutableLiveData<>();
    private MutableLiveData<String> status = new MutableLiveData<>();
    private ArrayList<BUuser> currentUser = new ArrayList<>();
    private MutableLiveData<Boolean> signout = new MutableLiveData<>();


    public LoginViewModel(){
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        login.setValue(false);
        invalidEmailOrPassword.setValue(false);
    }

    public void login(String email, String password){
        setStatus("");
        if(email.equals("") || password.equals("")){
            setStatus("Invalid Email or Password");
            invalidEmailOrPassword.setValue(true);
        } else {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    makeSureUserWithCorrespondingEmailExists();
                } else {
                    setStatus(task.getException().getMessage());
                    invalidEmailOrPassword.setValue(true);
                }
            });
        }
    }

    public void alreadySignedIn(){
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            makeSureUserWithCorrespondingEmailExists();
        }
    }

    public void signUpUser(String email, String password){
        status.setValue("");
        if(email.equals("")){
            status.setValue("Cannot have a blank email field");
            invalidEmailOrPassword.setValue(true);}
        else if (password.length() < 8){
            status.setValue("Passwords must be 8 characters");
            invalidEmailOrPassword.setValue(true);}
        else {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            login.setValue(true);
                        } else {
                            status.setValue(task.getException().getMessage());
                            invalidEmailOrPassword.setValue(true);
                        }
                    });
        }
    }

    public void makeSureUserWithCorrespondingEmailExists(){
        currAuthUser = auth.getCurrentUser();
        db.collection("BU users 2.0").whereEqualTo("email", currAuthUser.getEmail()).
                get().addOnCompleteListener((task) -> {
                    if(task.isSuccessful()){
                        QuerySnapshot collection = task.getResult();
                        if(!collection.isEmpty()){
                            Log.d("__LOGIN", "I am making it here");
                            currentUser.addAll(collection.toObjects(BUuser.class));
                            login.setValue(true);
                        } else {
                            status.setValue("Email does not match any existing users");
                            invalidEmailOrPassword.setValue(true);
                        }
                    } else {
                        status.setValue(task.getException().toString());
                        invalidEmailOrPassword.setValue(true);
                    }
        });

    }

    public void logOut(){
        FirebaseUser currAuthUser = null;
        currentUser = new ArrayList<>();
        auth.signOut();
        signout.setValue(true);
    }

    public BUuser getCurrentUser(){
        return currentUser.get(currentUser.size() - 1);
    }

    public MutableLiveData<String> getStatus(){
        return status;
    }

    public void setStatus(String message){
        status.setValue(message);
    }


    public MutableLiveData<Boolean> getLogin() {
        return login;
    }

    public void setLogin(boolean value) {
        this.login.setValue(value);
    }

    public MutableLiveData<Boolean> getInvalidEmailOrPassword() {
        return invalidEmailOrPassword;
    }

    public void setInvalidEmailOrPassword(boolean value) {
        this.invalidEmailOrPassword.setValue(value);
    }

    public MutableLiveData<Boolean> getSignout() {
        return signout;
    }

    public void setSignout(boolean value) {
        this.signout.setValue(value);
    }
}