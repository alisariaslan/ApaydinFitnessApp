package com.pakachu.apaydinfitness;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pakachu.apaydinfitness.databinding.FragmentGamesBinding;
import com.pakachu.apaydinfitness.db.DBJeton;
import com.pakachu.apaydinfitness.games.xox.XoX;
import com.pakachu.apaydinfitness.helpers.AddLoader;


public class GamesFragment extends Fragment {

    private FragmentGamesBinding binding;
    private int coin;
    private int ap;

    private CountDownTimer countDownTimer;
    private boolean timer = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGamesBinding.inflate(inflater, container, false);

        binding.button23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), XoX.class);
                startActivity(intent);
            }
        });

        DBJeton dbJeton = new DBJeton(getContext());
//        dbJeton.deleteData();
        if (dbJeton.getData().getCount() == 0)
            dbJeton.addData(0, 0);
         coin = dbJeton.getCoin();
         ap = dbJeton.getAP();

        ReText();

        binding.btnCoinToAp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                binding.gifCoin.setVisibility(View.VISIBLE);
                countDownTimer = new CountDownTimer(3000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer=true;
                    }

                    @Override
                    public void onFinish() {
                        timer=false;
                        binding.gifCoin.setVisibility(View.GONE);
                        ap += coin / 1000;
                        coin = coin % 1000;
                        dbJeton.setAP(ap);
                        dbJeton.setCoin(coin);
                        v.setVisibility(View.VISIBLE);
                        ReText();
                    }
                }.start();
            }
        });

        return binding.getRoot();
    }

    private void ReText() {
        binding.tvCoin.setText("Coin: " + coin + " coin");
        binding.tvAp.setText("AP: " + ap + " ap");

        if (coin > 1000) {
            binding.btnCoinToAp.setVisibility(View.VISIBLE);
        } else     binding.btnCoinToAp.setVisibility(View.GONE);

        if (coin <= 0)
            binding.tvIshavecoin.setVisibility(View.VISIBLE);
        else  binding.tvIshavecoin.setVisibility(View.GONE);
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

        if(timer)
            countDownTimer.cancel();
    }
}