package com.pakachu.apaydinfitness.games.xox;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.pakachu.apaydinfitness.R;
import com.pakachu.apaydinfitness.databinding.ActivityXoxBinding;
import com.pakachu.apaydinfitness.db.DBJeton;

import java.util.ArrayList;
import java.util.Random;

public class XoX extends AppCompatActivity {

    private ActivityXoxBinding binding;
    private ArrayList<Button> buttonArrayList;

    private char p1, p2;
    private boolean ready = false;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityXoxBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        init();

        int rnd = random.nextInt(2);
        if (rnd == 1) {
            p1 = 'X';
            p2 = 'O';
        } else {
            p2 = 'X';
            p1 = 'O';
        }

        binding.clButtons.startAnimation(AnimationUtils.loadAnimation(this, R.anim.comefromleft));
        new CountDownTimer(3000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                btn_fadein();
                say("Haha! Yenilmeye hazır ol! Ben Bowser. Haha!", 5);
                new CountDownTimer(5000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
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

        binding.btnCikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ready = false;
                binding.tvNote.setText("Çıkış yapılıyor...");
                binding.btnCikis.setVisibility(View.GONE);
                binding.btnTekrarDene.setVisibility(View.GONE);
                say("Demek kaçıyorsun! HAHA!", 3);
                new CountDownTimer(3000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        finish();
                    }
                }.start();
            }
        });

        binding.btnTekrarDene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ready = false;
                binding.tvNote.setText("Yeniden başlatılıyor...");
                binding.btnCikis.setVisibility(View.GONE);
                binding.btnTekrarDene.setVisibility(View.GONE);
                RestartActivity(3);
            }
        });

        int minusOne = -1;
//        Sec(1+minusOne);
//        Sec(5+minusOne);

//        Sec(1+minusOne, p1);
//        Sec(2+minusOne, p1);
//        Sec(4+minusOne, p1);

    }

    private void Ready() {
        binding.tvNote.setText("Sıra sen de !\n" + p1 + " sensin");
        ready = true;
    }

    private void NoReady() {
        binding.tvNote.setText("Sıra Bowser da !\n" + p2 + " o");
        ready = false;
    }

    private void randomAt() {
        say("Şimdi ağlayacaksın!", 1);
        new CountDownTimer(random.nextInt(5) * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
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
        secilenbtn.startAnimation(AnimationUtils.loadAnimation(XoX.this, R.anim.fall));
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

        DBJeton dbJeton = new DBJeton(this);
        int coin = dbJeton.getCoin();
        if (won == 0) {
            if (!isplayer)
                Ready();
            else
                randomAt();
        } else if (won == 1) {
            if (!isplayer) {
                binding.tvNote.setText("Bowser Kazandı! 10 Coin kaybettiniz");
                binding.givBowser.setImageResource(R.drawable.bowser_win);
                say("Haha! Hadi ağla bakalım!! Haha!", 5);
                binding.btnTekrarDene.setVisibility(View.VISIBLE);
                coin-=10;
            } else {
                binding.tvNote.setText("Tebrikler. Kazandınız! 100 Coin cüzdanınıza eklendi");
                binding.givBowser.setImageResource(R.drawable.bowser_fail);
                say("Ah, hayır olamazzz!!!", 5);
                coin+=100;
            }
        } else {
            binding.tvNote.setText("Berabere! İki tarafta eşit.");
            binding.givBowser.setImageResource(R.drawable.bowser_equal);
            say("Bugün şanslı günündesin!", 5);
            binding.btnTekrarDene.setVisibility(View.VISIBLE);
        }
        dbJeton.setCoin(coin);
    }

    private void RestartActivity(int duration) {
        new CountDownTimer(duration * 1000L, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                finish();
                startActivity(new Intent(XoX.this, XoX.class));
            }
        }.start();
    }

    private CountDownTimer countDownTimerSay;
    private CountDownTimer countDownTimerSaySub;
    private boolean sayBoolean = false;
    private boolean sayBooleanSub = false;

    private void say(String text, int duration) {
        if (sayBoolean) {
            countDownTimerSay.cancel();
            sayBoolean = false;
        }
        if (sayBooleanSub) {
            countDownTimerSaySub.cancel();
            sayBooleanSub = false;
        }
        binding.tvBowser.clearAnimation();
        binding.tvBowser.setVisibility(View.GONE);

        binding.tvBowser.setText(text);
        binding.tvBowser.setVisibility(View.VISIBLE);
        binding.tvBowser.startAnimation(AnimationUtils.loadAnimation(XoX.this, R.anim.bounce));

        countDownTimerSay = new CountDownTimer(duration * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                sayBoolean = true;
            }

            @Override
            public void onFinish() {
                sayBoolean = false;
                binding.tvBowser.clearAnimation();
                binding.tvBowser.startAnimation(AnimationUtils.loadAnimation(XoX.this, R.anim.fadeout));
                countDownTimerSaySub = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        sayBooleanSub = true;
                    }

                    @Override
                    public void onFinish() {
                        binding.tvBowser.clearAnimation();
                        binding.tvBowser.setVisibility(View.GONE);
                        sayBooleanSub = false;
                    }
                }.start();
            }
        }.start();
    }

    private void btn_fadein() {
        int delay = 1000;
        for (Button btn : buttonArrayList
        ) {
            btn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein));
            btn.setVisibility(View.VISIBLE);
            ObjectAnimator animation = ObjectAnimator.ofFloat(btn, "alpha", 0.0f, 1, 1);
            animation.setDuration(delay);
            animation.start();
            delay += 500;
        }
    }

    private void init() {
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
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ready) {
                        NoReady();
                        btn.setEnabled(false);
                        btn.setText("" + p1);
                        btn.startAnimation(AnimationUtils.loadAnimation(XoX.this, R.anim.rotate));

                        kontrol(true, p1);
                    }
                }
            });
        }
    }
}