package com.example.bu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bu.databinding.FragmentSignUpPart3Binding;
import com.example.bu.viewModel.CreateUserViewModel;
import com.example.bu.viewModel.CurrentUserViewModel;
import com.example.bu.viewModel.LoginViewModel;


public class SignUpPart3Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSignUpPart3Binding binding = FragmentSignUpPart3Binding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // this is navigation controller and view model
        NavController controller = NavHostFragment.findNavController(this);
        CreateUserViewModel viewModel = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(CreateUserViewModel.class);
        CurrentUserViewModel userViewModel = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(CurrentUserViewModel.class);
        binding.setViewModel(viewModel);

        //this is for when the user clicks create
        binding.buttonCreateUser.setOnClickListener((view) -> {
            binding.buttonCreateUser.setEnabled(false);
            viewModel.createUser(
                    binding.exitTextFirstName.getText().toString(),
                    binding.editTextLastName.getText().toString(),
                    binding.editTextUsername.getText().toString(),
                    binding.editTextDateOfBirth.getText().toString()
            );
        });

        viewModel.getUserCreated().observe(getViewLifecycleOwner(), createdUser -> {
            binding.buttonCreateUser.setEnabled(true);
            if(createdUser){
                userViewModel.setCurrentUser(viewModel.getCurrentUser());
                controller.navigate(R.id.action_signUpPart3Fragment_to_helloFragment);
                viewModel.setUserCreated(false);
            }
        });

        viewModel.getInvalidFields().observe(getViewLifecycleOwner(), invalid -> {
            binding.buttonCreateUser.setEnabled(true);
            if(invalid){
                viewModel.setInvalidFields(false);
            }
        });
        return binding.getRoot();
    }
}