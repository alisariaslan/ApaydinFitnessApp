package com.pakachu.apaydinfitness;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pakachu.apaydinfitness.databinding.FragmentGamesBinding;


public class GamesFragment extends Fragment {

    private FragmentGamesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGamesBinding.inflate(inflater, container, false);

        binding.button23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(GamesFragment.this)
                        .navigate(R.id.action_gamesFragment_to_XOXFragment);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}