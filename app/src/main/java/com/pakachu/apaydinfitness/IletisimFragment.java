package com.pakachu.apaydinfitness;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.pakachu.apaydinfitness.databinding.FragmentIletisimBinding;
import com.pakachu.apaydinfitness.helpers.AddLoader;

public class IletisimFragment extends Fragment {

    FragmentIletisimBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentIletisimBinding.inflate(inflater, container, false);

        binding.imageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate));
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/apaydin_fitness/?hl=tr")));
            }
        });

        binding.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate));
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("tel:05325768239")));
            }
        });

        binding.button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:40.775523901392845, 29.811244481385344")));
            }
        });

        binding.button18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:40.776094545386485, 29.799866196726022")));

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AddLoader addLoader = new AddLoader(getActivity());
        addLoader.RequestInterstatial();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}