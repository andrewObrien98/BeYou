package com.example.bu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bu.databinding.FragmentCreatePostBinding;
import com.example.bu.viewModel.CurrentUserViewModel;
import com.example.bu.viewModel.LoginViewModel;
import com.example.bu.viewModel.PostViewModel;

public class CreatePostFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentCreatePostBinding binding = FragmentCreatePostBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);
        PostViewModel postVM = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(PostViewModel.class);
        CurrentUserViewModel userVM = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(CurrentUserViewModel.class);


        binding.buttonPost.setOnClickListener((view) -> {
            postVM.createPost(userVM.getCurrentUser().getUsername(), binding.editTextPostMessage.getText().toString());
            binding.buttonPost.setEnabled(false);
            binding.buttonPost.setText("Saving...");
        });


        postVM.getPostCreated().observe(getViewLifecycleOwner(), posted -> {
            if(posted){
                postVM.setPostCreated(false);
                binding.buttonPost.setText("Post");
                binding.buttonPost.setEnabled(true);
                binding.editTextPostMessage.setText("");
            }
        });
        return binding.getRoot();
    }

    //orderBy("date")
}