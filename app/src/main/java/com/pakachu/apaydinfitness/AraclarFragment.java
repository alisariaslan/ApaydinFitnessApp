package com.pakachu.apaydinfitness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.pakachu.apaydinfitness.customdialogs.FitMiyimDialog;
import com.pakachu.apaydinfitness.customdialogs.QuickRMDialog;
import com.pakachu.apaydinfitness.databinding.FragmentAraclarBinding;

public class AraclarFragment extends Fragment {

    FragmentAraclarBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAraclarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.imageView29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.rotate));
                QuickRMDialog quickRMDialog=new QuickRMDialog(getActivity());
                quickRMDialog.show(false);
            }
        });
        binding.imageView30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.rotate));
                FitMiyimDialog fitMiyimDialog=new FitMiyimDialog(getActivity());
                fitMiyimDialog.show(false);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}