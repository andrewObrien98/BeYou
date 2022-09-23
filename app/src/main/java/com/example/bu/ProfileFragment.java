package com.example.bu;

import android.os.Bundle;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bu.BUAdapters.NotificationsAdapter;
import com.example.bu.BUAdapters.ProfileAdapter;
import com.example.bu.databinding.FragmentProfileBinding;
import com.example.bu.model.Post;
import com.example.bu.viewModel.CurrentUserViewModel;
import com.example.bu.viewModel.PostViewModel;
import com.example.bu.viewModel.ProfileViewModel;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentProfileBinding binding = FragmentProfileBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);
        ProfileViewModel profileVM = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(ProfileViewModel.class);
        CurrentUserViewModel userVM = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(CurrentUserViewModel.class);

        String username = userVM.getCurrentUser().getUsername();
        String firstName = userVM.getCurrentUser().getFirstName();
        String lastName = userVM.getCurrentUser().getLastName();
        binding.username.setText(username);
        binding.fristname.setText(firstName);
        binding.lastname.setText(lastName);

        profileVM.getPost(username);


        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ProfileAdapter());

        profileVM.getPostList().addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Post>>() {
            @Override
            public void onChanged(ObservableList<Post> sender) {

            }

            @Override
            public void onItemRangeChanged(ObservableList<Post> sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList<Post> sender, int positionStart, int itemCount) {
                RecyclerView recyclerView = binding.recyclerView;
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new ProfileAdapter(sender));
            }

            @Override
            public void onItemRangeMoved(ObservableList<Post> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<Post> sender, int positionStart, int itemCount) {

            }
        });

        return binding.getRoot();
    }
}