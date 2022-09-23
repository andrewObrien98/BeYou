package com.example.bu.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.bu.model.BUuser;

public class CurrentUserViewModel extends ViewModel {
    private BUuser currentUser;

    public CurrentUserViewModel(){
    }

    public void setCurrentUser(BUuser curUser){
        currentUser = curUser;
    }

    public BUuser getCurrentUser(){
        return currentUser;
    }
}
