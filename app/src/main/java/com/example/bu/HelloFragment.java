package com.example.bu;

import static androidx.databinding.library.baseAdapters.BR.viewModel;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bu.databinding.FragmentHelloBinding;
import com.example.bu.viewModel.CurrentUserViewModel;
import com.example.bu.viewModel.FriendsViewModel;
import com.example.bu.viewModel.LoginViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.atomic.AtomicReference;


public class HelloFragment extends Fragment {
    private boolean LoadFriendList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentHelloBinding binding = FragmentHelloBinding.inflate(inflater, container, false);

        ///////////// this is for the controller ////////////////////////////////////////////////////////////
        NavController controller = NavHostFragment.findNavController(this);
        LoginViewModel viewModel = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(LoginViewModel.class);
        CurrentUserViewModel userViewModel = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(CurrentUserViewModel.class);
        FriendsViewModel friendsViewModel = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(FriendsViewModel.class);

        //load the current friend list
        friendsViewModel.resetListOfFriendRequestsMade();

        viewModel.getSignout().observe(getViewLifecycleOwner(), signedOut -> {
            if(signedOut){
                controller.navigate(R.id.action_helloFragment_to_login);
                viewModel.setSignout(false);
            }
        });
        ///////////////// this is for the bottom navigation bar //////////////////////////////////
        BottomNavigationView bottomNav = binding.bottomNav;
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.nav_host_fragment_container_2, new FeedFragment(), null)
                .commit();
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            if(item.getItemId() == R.id.feedFragment){

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.nav_host_fragment_container_2, new FeedFragment(), null)
                        .commit();
            }
            else if(item.getItemId() == R.id.profileFragment){
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.nav_host_fragment_container_2, new ProfileFragment(), null)
                        .commit();
            }
            else if(item.getItemId() == R.id.friendFragment){
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.nav_host_fragment_container_2, new FriendFragment(), null)
                        .commit();
            }
            else if(item.getItemId() == R.id.createPostFragment){
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.nav_host_fragment_container_2, new CreatePostFragment(), null)
                        .commit();
            }
            return true;
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                binding.bottomNav.setSelectedItemId(R.id.feedFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);

/////////////////////////////////// this is for the side bar ///////////////////////////////

        binding.topAppBar.setNavigationOnClickListener(something -> {
                binding.drawerLayout.open();
                TextView nameText = getActivity().findViewById(R.id.textViewHeaderName);
                nameText.setText(userViewModel.getCurrentUser().getFirstName());
                TextView usernameText = getActivity().findViewById(R.id.textViewHeaderUserName);
                usernameText.setText(userViewModel.getCurrentUser().getUsername());
        });
        binding.navigationView.setNavigationItemSelectedListener(menuItem -> {
            menuItem.setChecked(true);
            if(menuItem.getItemId() == R.id.profile){
                binding.bottomNav.setSelectedItemId(R.id.profileFragment);
            }
            if(menuItem.getItemId() == R.id.logout){
                viewModel.logOut();
            }
            if(menuItem.getItemId() == R.id.notifications){
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.nav_host_fragment_container_2, new NotificationsFragment(), null)
                        .commit();
                //binding.bottomNav.setSelectedItemId(R.id.feedFragment);
            }
            binding.drawerLayout.close();
            return true;
        });
        return binding.getRoot();
    }

}