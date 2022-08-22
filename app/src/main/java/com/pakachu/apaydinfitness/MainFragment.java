package com.pakachu.apaydinfitness;

import android.animation.ObjectAnimator;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.pakachu.apaydinfitness.databinding.FragmentMainBinding;
import com.pakachu.apaydinfitness.db.DBNotif;

import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private ObjectAnimator animation;
    private ArrayList<Button> buttonArrayList;
    private CountDownTimer countDownTimer;
    private boolean timer;

    private void Locked() {
        for (Button button : buttonArrayList
        ) {
            button.setEnabled(false);
        }
    }

    private void Unlocked() {
        for (Button button : buttonArrayList
        ) {
            button.setEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);

        buttonArrayList = new ArrayList<>();

        buttonArrayList.add(binding.btnOlculerim);
        buttonArrayList.add(binding.btnGames);
        buttonArrayList.add(binding.btnAntrenman);
        buttonArrayList.add(binding.btnDiyet);
        buttonArrayList.add(binding.btnArena);
        buttonArrayList.add(binding.btnAraclar);
        buttonArrayList.add(binding.btnFitopedia);
        buttonArrayList.add(binding.btnIletisim);
        buttonArrayList.add(binding.btnTakviyeler);
        buttonArrayList.add(binding.btnDuyurular);
        buttonArrayList.add(binding.btnPt);
        buttonArrayList.add(binding.btnKapanis);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnOlculerim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locked();
                animation = ObjectAnimator.ofFloat(binding.btnOlculerim, "rotationY", 0.0f, 360);
                animation.setDuration(1000);
                animation.start();
                countDownTimer = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer = true;
                    }

                    @Override
                    public void onFinish() {
                        NavHostFragment.findNavController(MainFragment.this)
                                .navigate(R.id.action_mainFragment_to_olculerimFragment);
                    }
                }.start();
            }
        });


        binding.btnAntrenman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locked();
                animation = ObjectAnimator.ofFloat(binding.btnAntrenman, "rotationY", 0.0f, 360);
                animation.setDuration(1000);
                animation.start();
                countDownTimer = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer = true;
                    }

                    @Override
                    public void onFinish() {
                        NavHostFragment.findNavController(MainFragment.this)
                                .navigate(R.id.action_mainFragment_to_antrenmanFragment);
                    }
                }.start();
            }
        });

        binding.btnDuyurular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locked();
                animation = ObjectAnimator.ofFloat(binding.btnDuyurular, "rotationY", 0.0f, 360);
                animation.setDuration(1000);
                animation.start();
                countDownTimer = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer = true;
                    }

                    @Override
                    public void onFinish() {
                        NavHostFragment.findNavController(MainFragment.this)
                                .navigate(R.id.action_mainFragment_to_duyurularFragment);
                    }
                }.start();
            }
        });

        binding.btnIletisim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locked();
                animation = ObjectAnimator.ofFloat(binding.btnIletisim, "rotationY", 0.0f, 360);
                animation.setDuration(1000);
                animation.start();
                countDownTimer = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer = true;
                    }

                    @Override
                    public void onFinish() {
                        NavHostFragment.findNavController(MainFragment.this)
                                .navigate(R.id.action_mainFragment_to_iletisimFragment);
                    }
                }.start();
            }
        });

        binding.btnGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locked();
                animation = ObjectAnimator.ofFloat(binding.btnGames, "rotationY", 0.0f, 360);
                animation.setDuration(1000);
                animation.start();
                countDownTimer = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer = true;
                    }

                    @Override
                    public void onFinish() {
                        new MyCustomDialog(getActivity()).Toast("Bu bölüm henüz aktif değil");
                        binding.btnGames.clearAnimation();

                        Unlocked();
                    }
                }.start();
            }
        });

        binding.btnDiyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locked();
                animation = ObjectAnimator.ofFloat(binding.btnDiyet, "rotationY", 0.0f, 360);
                animation.setDuration(1000);
                animation.start();
                countDownTimer = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer = true;
                    }

                    @Override
                    public void onFinish() {
                        new MyCustomDialog(getActivity()).Toast("Bu bölüm henüz aktif değil");
                        Unlocked();
                    }
                }.start();
            }
        });

        binding.btnArena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locked();
                animation = ObjectAnimator.ofFloat(binding.btnArena, "rotationY", 0.0f, 360);
                animation.setDuration(1000);
                animation.start();
                countDownTimer = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer = true;
                    }

                    @Override
                    public void onFinish() {
                        NavHostFragment.findNavController(MainFragment.this)
                                .navigate(R.id.action_mainFragment_to_arenaFragment);
                    }
                }.start();
            }
        });

        binding.btnAraclar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locked();
                animation = ObjectAnimator.ofFloat(binding.btnAraclar, "rotationY", 0.0f, 360);
                animation.setDuration(1000);
                animation.start();
                countDownTimer = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer = true;
                    }

                    @Override
                    public void onFinish() {
                        new MyCustomDialog(getActivity()).Toast("Bu bölüm henüz aktif değil");
                        Unlocked();
                    }
                }.start();
            }
        });

        binding.btnFitopedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locked();
                animation = ObjectAnimator.ofFloat(binding.btnFitopedia, "rotationY", 0.0f, 360);
                animation.setDuration(1000);
                animation.start();
                countDownTimer = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer = true;
                    }

                    @Override
                    public void onFinish() {
                        new MyCustomDialog(getActivity()).Toast("Bu bölüm henüz aktif değil");
                        Unlocked();
                    }
                }.start();
            }
        });

        binding.btnTakviyeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locked();
                animation = ObjectAnimator.ofFloat(binding.btnTakviyeler, "rotationY", 0.0f, 360);
                animation.setDuration(1000);
                animation.start();
                countDownTimer = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer = true;
                    }

                    @Override
                    public void onFinish() {
                        new MyCustomDialog(getActivity()).Toast("Bu bölüm henüz aktif değil");
                        Unlocked();
                    }
                }.start();
            }
        });

        binding.btnPt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locked();
                animation = ObjectAnimator.ofFloat(binding.btnPt, "rotationY", 0.0f, 360);
                animation.setDuration(1000);
                animation.start();
                countDownTimer = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer = true;
                    }

                    @Override
                    public void onFinish() {
                        new MyCustomDialog(getActivity()).Toast("Bu bölüm henüz aktif değil");
                        Unlocked();
                    }
                }.start();
            }
        });

        binding.btnKapanis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Locked();
                animation = ObjectAnimator.ofFloat(binding.btnKapanis, "rotationY", 0.0f, 360);
                animation.setDuration(1000);
                animation.start();
                countDownTimer = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer = true;
                    }

                    @Override
                    public void onFinish() {
                        NavHostFragment.findNavController(MainFragment.this)
                                .navigate(R.id.action_mainFragment_to_saatlerFragment);
                    }
                }.start();
            }
        });

        DBNotif dbNotif = new DBNotif(getContext());
        int size = dbNotif.getUnreadSize();
        if (size > 0) {
            binding.btnDuyurular.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.blink_anim));
            binding.btnDuyurular.setText("duyurular (" + size + ")");
        }

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.textView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.comefromup));
            binding.imageView2.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.comefromdown));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (timer)
            countDownTimer.cancel();
    }

}