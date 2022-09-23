package com.example.bu;

import android.os.Bundle;

import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bu.BUAdapters.ListAdapter;
import com.example.bu.databinding.FragmentFriendBinding;
import com.example.bu.databinding.FragmentLoginBinding;
import com.example.bu.model.BUuser;
import com.example.bu.viewModel.CurrentUserViewModel;
import com.example.bu.viewModel.FriendsViewModel;
import com.example.bu.viewModel.NotificationsViewModel;

import java.util.ArrayList;

public class FriendFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentFriendBinding binding = FragmentFriendBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);
        CurrentUserViewModel userViewModel = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(CurrentUserViewModel.class);
        FriendsViewModel friendsVM = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(FriendsViewModel.class);
        NotificationsViewModel notificationsVM = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(NotificationsViewModel.class);

        friendsVM.loadCurrentFriendList(userViewModel.getCurrentUser().getUsername());


        binding.searchButton.setOnClickListener((view) -> {
            friendsVM.findPeopleWithName(binding.editTextTextPersonName.getText().toString());
        });

        friendsVM.getNoPeopleWithThatName().observe(getViewLifecycleOwner(), dontExist -> {
            if(dontExist){
                binding.findYourFriendsText.setText("Sorry, but nobody with that name exists");
                friendsVM.setNoPeopleWithThatName(false);
            }
        });

        friendsVM.getSuccessfullyGotFriendList().observe(getViewLifecycleOwner(), couldLoad -> {
            if(couldLoad){
                binding.searchButton.setEnabled(true);
                friendsVM.setSuccessfullyGotFriendList(false);
            }
        });

        friendsVM.getDidntGetFriendList().observe(getViewLifecycleOwner(), couldntLoad -> {
            if(couldntLoad){
                binding.searchButton.setEnabled(false);
                friendsVM.setDidntGetFriendList(false);
            }
        });

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ListAdapter(new ArrayList<>(), friendsVM, notificationsVM));

        friendsVM.getListOfPeopleWithThatName().addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<BUuser>>() {
             @Override
             public void onChanged(ObservableList<BUuser> sender) {
                 Log.d("__friends", "Do I make it to here 2?");

             }

             @Override
             public void onItemRangeChanged(ObservableList<BUuser> sender, int positionStart, int itemCount) {
                 Log.d("__friends", "Do I make it to here 4?");
             }

             @Override
             public void onItemRangeInserted(ObservableList<BUuser> sender, int positionStart, int itemCount) {
                 RecyclerView recyclerView = binding.recyclerView;
                 recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                 recyclerView.setAdapter(new ListAdapter(friendsVM.getListOfPeople(), friendsVM, notificationsVM));
                 Log.d("__friends", "Do I make it to here 5?");
             }

             @Override
             public void onItemRangeMoved(ObservableList<BUuser> sender, int fromPosition, int toPosition, int itemCount) {
                 Log.d("__friends", "Do I make it to here 6?");
             }

             @Override
             public void onItemRangeRemoved(ObservableList<BUuser> sender, int positionStart, int itemCount) {
                 Log.d("__friends", "Do I make it to here 7?");
             }
         }
        );

        return binding.getRoot();
    }
}