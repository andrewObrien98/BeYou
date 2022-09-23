package com.example.bu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bu.BUAdapters.ListAdapter;
import com.example.bu.BUAdapters.NotificationsAdapter;
import com.example.bu.databinding.FragmentNotificationsBinding;
import com.example.bu.viewModel.CurrentUserViewModel;
import com.example.bu.viewModel.NotificationsViewModel;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentNotificationsBinding binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);
        NotificationsViewModel notificationsVM = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(NotificationsViewModel.class);
        CurrentUserViewModel userVM = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(CurrentUserViewModel.class);

        notificationsVM.loadCurrentUserFriends(userVM.getCurrentUser().getUsername());

        notificationsVM.getLoadedFriendList().observe(getViewLifecycleOwner(), loadedFriends -> {
            if(loadedFriends){
                notificationsVM.loadUsersNotifications();
                notificationsVM.setLoadedFriendList(false);
            }
        });

        notificationsVM.getGetUsersNotifications().observe(getViewLifecycleOwner(), loadedNotifications -> {
            if(loadedNotifications){
                notificationsVM.loadNotificationsList();
                notificationsVM.setGetUsersNotifications(false);
            }
        });

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new NotificationsAdapter(new ArrayList<>(), notificationsVM));

        notificationsVM.getListOfFriendRequests().addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<String>>() {
            @Override
            public void onChanged(ObservableList<String> sender) {

            }

            @Override
            public void onItemRangeChanged(ObservableList<String> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList<String> sender, int positionStart, int itemCount) {
                ArrayList<String> listOfNotifications = new ArrayList<>(sender);
                RecyclerView recyclerView = binding.recyclerView;
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new NotificationsAdapter(listOfNotifications, notificationsVM));
            }

            @Override
            public void onItemRangeMoved(ObservableList<String> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<String> sender, int positionStart, int itemCount) {

            }
        });

        return binding.getRoot();
    }
}
