package com.example.bu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bu.databinding.FragmentSignUpPart1Binding;

public class SignUpPart1Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSignUpPart1Binding binding = FragmentSignUpPart1Binding.inflate(inflater, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // this is navigation controller and view model
        NavController controller = NavHostFragment.findNavController(this);

        //this is for the message
        binding.textViewCreateAccountUserMessage.setText(getMessage());

        //if the user clicks the next button
        binding.buttonNext1.setOnClickListener((view) -> {
            controller.navigate(R.id.action_signUpPart1Fragment_to_signUpPart2Fragment);
        });

        return binding.getRoot();
    }

    private String getMessage(){
        return "Hello,\n" +
                "Welcome to BU. BU was created to be a place where you " +
                "can just Be You. Our hope as the creators of this social-" +
                "media app is to provide a place where people can post " +
                "about who they are without any filters and without worrying " +
                "about superficial things like how many likes or comments they get." +
                " We hope you find it refreshing and enjoy just Being You.\n" +
                "-Creators";
    }
}