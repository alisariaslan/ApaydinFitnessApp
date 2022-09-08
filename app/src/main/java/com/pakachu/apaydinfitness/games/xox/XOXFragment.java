package com.pakachu.apaydinfitness.games.xox;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.pakachu.apaydinfitness.R;
import com.pakachu.apaydinfitness.databinding.FragmentXOXBinding;
import com.pakachu.apaydinfitness.helpers.AddLoader;

import java.util.ArrayList;
import java.util.Random;

public class XOXFragment extends Fragment {

    private FragmentXOXBinding binding;

    private ArrayList<Button> buttonArrayList;
    private char p1, p2;
    private boolean ready = false;
    private Random random;

    private CountDownTimer countDownTimerStartReady;
    private boolean timerStartReady = false;

    private CountDownTimer countDownTimerStart;
    private boolean timerStart = false;

    private CountDownTimer countDownTimerThink;
    private boolean timerThink = false;

    private CountDownTimer countDownTimerSay;
    private boolean timerSay = false;

    private CountDownTimer countDownTimerSaySub;
    private boolean timerSaySub = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentXOXBinding.inflate(inflater, container, false);

        binding.btnCikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckTimers();
                requireActivity().onBackPressed();
            }
        });

        binding.btnTekrarDene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ready = false;
                v.setVisibility(View.GONE);
                Start();
            }
        });

        Start();

        return binding.getRoot();
    }

    private void Start() {
        CheckTimers();

        AddLoader addLoader = new AddLoader(getActivity());
        addLoader.RequestInterstatial();

        init();

        int rnd = random.nextInt(2);
        if (rnd == 1) {
            p1 = 'X';
            p2 = 'O';
        } else {
            p2 = 'X';
            p1 = 'O';
        }

        binding.clButtons.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.comefromleft));
        binding.givBowser.setImageResource(R.drawable.bowser);
        binding.tvNote.setText("Sıranın kimde olduğu şimdi belirlenecek!");

        countDownTimerStartReady = new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerStartReady = true;
            }

            @Override
            public void onFinish() {
                timerStartReady = false;
                btn_fadein();
                say("Haha! Yenilmeye hazır ol! Ben Bowser. Haha!", 5);
                countDownTimerStart = new CountDownTimer(5000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timerStart = true;
                    }

                    @Override
                    public void onFinish() {
                        timerStart = false;
                        int rnd = random.nextInt(2);
                        if (rnd == 0) {
                            NoReady();
                            randomAt();
                        } else Ready();
                        binding.btnCikis.setVisibility(View.VISIBLE);
                    }
                }.start();
            }
        }.start();
    }

    private void Ready() {
        binding.tvNote.setText("Sıra sen de !\n" + p1 + " sensin");
        ready = true;
    }

    private void NoReady() {
        binding.tvNote.setText("Sıra Bowser da !\n" + p2 + " o");
        ready = false;
    }

    private void say(String text, int duration) {
        if (timerSay) {
            countDownTimerSay.cancel();
            timerSay = false;
        }
        if (timerSaySub) {
            countDownTimerSaySub.cancel();
            timerSaySub = false;
        }
        binding.tvBowser.clearAnimation();
        binding.tvBowser.setVisibility(View.GONE);

        binding.tvBowser.setText(text);
        binding.tvBowser.setVisibility(View.VISIBLE);
        binding.tvBowser.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));

        countDownTimerSay = new CountDownTimer(duration * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerSay = true;
            }

            @Override
            public void onFinish() {
                timerSay = false;
                binding.tvBowser.clearAnimation();
                binding.tvBowser.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fadeout));
                countDownTimerSaySub = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timerSaySub = true;
                    }

                    @Override
                    public void onFinish() {
                        binding.tvBowser.clearAnimation();
                        binding.tvBowser.setVisibility(View.GONE);
                        timerSaySub = false;
                    }
                }.start();
            }
        }.start();
    }

    private void randomAt() {
        say("Şimdi ağlayacaksın!", 1);
        countDownTimerThink = new CountDownTimer(random.nextInt(5) * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerThink = true;
            }

            @Override
            public void onFinish() {
                timerThink = false;
                StringBuilder currentPattern = new StringBuilder();
                for (Button btn : buttonArrayList)
                    currentPattern.append(btn.getText());
                Patterns pattern = new Patterns();

                int secilenIndex = pattern.ai_attack(currentPattern.toString(), p2); //ATAK
                if (secilenIndex < 0)
                    secilenIndex = pattern.ai_attack(currentPattern.toString(), p1); //DEFANS

                if (secilenIndex > -1) {
                    Log.e("game", "AI buldu: " + secilenIndex);
                    Sec(secilenIndex, p2);
                    kontrol(false, p2);
                } else {
                    int size = 0;
                    int size2 = 0;
                    int[] allindex = new int[buttonArrayList.size()];

//                Log.e("game", "ARRAYLST " + LocalDateTime.now());
                    for (Button btn : buttonArrayList
                    ) {
                        if (btn.isEnabled()) {
                            allindex[size] = size;
                            size2++;
                        } else allindex[size] = -1;
//            Log.e("game","btn"+(size+1)+": "+allindex[size]);
                        size++;
                    }
                    if (size2 != 0) {
                        int[] avabileIndexes = new int[size2];
                        size = 0;
                        for (int i : allindex
                        ) {
                            if (i >= 0) {
                                avabileIndexes[size] = i;
//                            Log.e("game", "btn" + (size + 1) + ": " + avabileIndexes[size]);
                                size++;
                            }
                        }

                        int rnd = random.nextInt(avabileIndexes.length);
//                    Log.e("game", "rnd: " + rnd);
                        int secilen = avabileIndexes[rnd];
//                    Log.e("game", "secilen: " + secilen);

                        Sec(secilen, p2);
                    }
                    kontrol(false, p2);
                }


            }
        }.start();
    }

    private void Sec(int index, char p) {
        Button secilenbtn = buttonArrayList.get(index);
        secilenbtn.setText("" + p);
        secilenbtn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fall));
        secilenbtn.setEnabled(false);
        say("Hadi annene git ve ağla! Haha!", 1);
    }

    private void kontrol(boolean isplayer, char p) {
        StringBuilder currentPattern = new StringBuilder();
        for (Button btn : buttonArrayList)
            currentPattern.append(btn.getText());

        Log.e("game", "PATTERN \n" + currentPattern);

        Patterns pattern = new Patterns();
        int won = pattern.contains(currentPattern.toString(), p);
        Log.e("game", "IsWon? " + won);


        if (won == 0) {
            if (!isplayer)
                Ready();
            else
                randomAt();
        } else if (won == 1) {
            if (!isplayer) {
                binding.tvNote.setText("Maalesef kaybettiniz. Bowser oyunu kazandı!");
                binding.givBowser.setImageResource(R.drawable.bowser_win);
                say("Haha! Hadi ağla bakalım!! Haha!", 5);
                binding.btnTekrarDene.setVisibility(View.VISIBLE);

            } else {
                binding.tvNote.setText("Tebrikler. Kazandınız!");
                binding.givBowser.setImageResource(R.drawable.bowser_fail);
                say("Ah, hayır olamazzz!!!", 5);
                binding.btnTekrarDene.setVisibility(View.VISIBLE);
            }
        } else {
            binding.tvNote.setText("Berabere. İki tarafta eşit!");
            binding.givBowser.setImageResource(R.drawable.bowser_equal);
            say("Bugün şanslı günündesin!", 5);
            binding.btnTekrarDene.setVisibility(View.VISIBLE);
        }

    }

    private void btn_fadein() {
        int delay = 1000;
        for (Button btn : buttonArrayList
        ) {
            btn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fadein));
            btn.setVisibility(View.VISIBLE);
            ObjectAnimator animation = ObjectAnimator.ofFloat(btn, "alpha", 0.0f, 1, 1);
            animation.setDuration(delay);
            animation.start();
            delay += 500;
        }
    }

    private void init() {
        binding.btnCikis.setVisibility(View.GONE);

        CheckTimers();

        timerStart = timerThink = timerSay = timerSaySub = false;

        random = new Random();
        buttonArrayList = new ArrayList<>();

        buttonArrayList.add(binding.btn1);
        buttonArrayList.add(binding.btn2);
        buttonArrayList.add(binding.btn3);
        buttonArrayList.add(binding.btn4);
        buttonArrayList.add(binding.btn5);
        buttonArrayList.add(binding.btn6);
        buttonArrayList.add(binding.btn7);
        buttonArrayList.add(binding.btn8);
        buttonArrayList.add(binding.btn9);

        for (Button btn : buttonArrayList
        ) {
            btn.setText(".");
            btn.setEnabled(true);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ready) {
                        NoReady();
                        btn.setEnabled(false);
                        btn.setText("" + p1);
                        btn.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rotate));

                        kontrol(true, p1);
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        CheckTimers();
    }

    private void CheckTimers() {
        if (timerStart) {
            countDownTimerStart.cancel();
            timerStart = false;
        }

        if (timerStartReady) {
            countDownTimerStartReady.cancel();
            timerStartReady = false;
        }

        if (timerThink) {
            countDownTimerThink.cancel();
            timerThink = false;
        }

        if (timerSay) {
            countDownTimerSay.cancel();
            timerSay = false;
        }

        if (timerSaySub) {
            countDownTimerSaySub.cancel();
            timerSaySub = false;
        }

    }
}