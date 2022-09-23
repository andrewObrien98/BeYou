package com.example.bu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bu.databinding.FragmentSignUpPart2Binding;
import com.example.bu.viewModel.LoginViewModel;

public class SignUpPart2Fragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSignUpPart2Binding binding = FragmentSignUpPart2Binding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // this is navigation controller and view model
        NavController controller = NavHostFragment.findNavController(this);
        LoginViewModel viewModel = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(LoginViewModel.class);
        binding.setViewModel(viewModel);

        //if the click the next button
        binding.buttonNext2.setOnClickListener((view) -> {
            binding.buttonNext2.setEnabled(false);
            viewModel.signUpUser(binding.editTextTextEmailAddress2.getText().toString(), binding.editTextTextPassword2.getText().toString());
        });

        //if they enter a good email and password for their user
        viewModel.getLogin().observe(getViewLifecycleOwner(), goodUser -> {
            binding.buttonNext2.setEnabled(true);
            if(goodUser){
                controller.navigate(R.id.action_signUpPart2Fragment_to_signUpPart3Fragment);
                viewModel.setLogin(false);
            }
        });

        //if the enter a bad email or password for their user
        viewModel.getInvalidEmailOrPassword().observe(getViewLifecycleOwner(), invalid -> {
            binding.buttonNext2.setEnabled(true);
            if(invalid){
                viewModel.setInvalidEmailOrPassword(false);
            }
        });

        return binding.getRoot();
    }
}