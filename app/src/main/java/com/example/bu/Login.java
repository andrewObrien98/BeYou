package com.example.bu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bu.databinding.FragmentLoginBinding;
import com.example.bu.viewModel.CreateUserViewModel;
import com.example.bu.viewModel.CurrentUserViewModel;
import com.example.bu.viewModel.LoginViewModel;

public class Login extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentLoginBinding binding = FragmentLoginBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // this is navigation controller and view model
        NavController controller = NavHostFragment.findNavController(this);
        LoginViewModel viewModel = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(LoginViewModel.class);
        CurrentUserViewModel userViewModel = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(CurrentUserViewModel.class);


        binding.setViewModel(viewModel);

        //we should check to make sure that they weren't already logged in
        viewModel.alreadySignedIn();

        //this is if they already have an account
        binding.buttonLogin.setOnClickListener((view) -> {
            binding.buttonLogin.setEnabled(false);
            viewModel.login(binding.editTextTextEmailAddress.getText().toString(), binding.editTextTextPassword.getText().toString());
        });

        //this means that it was successful
        viewModel.getLogin().observe(getViewLifecycleOwner(), goodUser -> {
            binding.buttonLogin.setEnabled(true);
            if(goodUser){
                userViewModel.setCurrentUser(viewModel.getCurrentUser());
                controller.navigate(R.id.action_login_to_helloFragment);
                viewModel.setLogin(false);
            }
        });

        //this means that it is a bad account
        viewModel.getInvalidEmailOrPassword().observe(getViewLifecycleOwner(), invalid -> {
            binding.buttonLogin.setEnabled(true);
            if(invalid){
                viewModel.setInvalidEmailOrPassword(false);
                Log.d("___TOME", "Do I get here");
            }
        });

        //this is if they dont have an account
        binding.buttonGoToCreateAccount.setOnClickListener((view) -> {
            controller.navigate(R.id.action_login_to_signUpPart1Fragment);
        });

        return binding.getRoot();
    }
}