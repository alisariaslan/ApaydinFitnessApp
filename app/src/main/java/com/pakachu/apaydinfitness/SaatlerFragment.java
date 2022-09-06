package com.pakachu.apaydinfitness;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.pakachu.apaydinfitness.databinding.FragmentSaatlerBinding;
import com.pakachu.apaydinfitness.helpers.AddLoader;

import java.util.Timer;
import java.util.TimerTask;


public class SaatlerFragment extends Fragment {

    FragmentSaatlerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSaatlerBinding.inflate(inflater, container, false);

        AddLoader addLoader = new AddLoader(getActivity());
        addLoader.RequestInterstatial();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.imageView11.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.toright));
        binding.imageView12.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.toleft));
        countDownTimer.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (timer)
            countDownTimer.cancel();
    }

    private boolean timer = false;

    private CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timer = true;
        }

        @Override
        public void onFinish() {
            timer = false;
            binding.imageView11.clearAnimation();
            binding.imageView11.setVisibility(View.GONE);
            binding.imageView12.clearAnimation();
            binding.imageView12.setVisibility(View.GONE);
        }
    };
}