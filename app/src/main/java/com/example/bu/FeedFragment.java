package com.example.bu;

import android.os.Bundle;

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

import com.example.bu.BUAdapters.FeedAdapter;
import com.example.bu.BUAdapters.ProfileAdapter;
import com.example.bu.databinding.FragmentFeedBinding;
import com.example.bu.model.Post;
import com.example.bu.viewModel.CurrentUserViewModel;
import com.example.bu.viewModel.FeedViewModel;
import com.example.bu.viewModel.PostViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;


public class FeedFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentFeedBinding binding = FragmentFeedBinding.inflate(inflater, container, false);
        NavController controller = NavHostFragment.findNavController(this);
        FeedViewModel postVM = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(FeedViewModel.class);
        CurrentUserViewModel userVM = new ViewModelProvider(controller.getViewModelStoreOwner(R.id.nav_graph)).get(CurrentUserViewModel.class);

        postVM.getFriendList(userVM.getCurrentUser().getUsername());

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new FeedAdapter());

        postVM.getPosts().addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Post>>() {
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
                recyclerView.setAdapter(new FeedAdapter(sender, getContext()));
            }

            @Override
            public void onItemRangeMoved(ObservableList<Post> sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList<Post> sender, int positionStart, int itemCount) {

            }
        });


        //for ads
        //MobileAds.initialize(getContext());
        //binding.bannerAd.loadAd(new AdRequest.Builder().build());

        return binding.getRoot();
    }
}