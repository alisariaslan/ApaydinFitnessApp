package com.pakachu.apaydinfitness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pakachu.apaydinfitness.customdialogs.MyCustomDialog;
import com.pakachu.apaydinfitness.databinding.FragmentFitopediaBinding;


public class FitopediaFragment extends Fragment {

    public static int aktif_sayfa = 0;

    private FragmentFitopediaBinding binding;
    private int max_sayfa = 0;
    private String[] strings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFitopediaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        strings = getActivity().getResources().getStringArray(R.array.fitopedia);
        max_sayfa = strings.length;
        binding.button422.setText("" + max_sayfa);

        Load();

        binding.button38.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aktif_sayfa > 0) {
                    aktif_sayfa = 0;
                    Load();
                } else new MyCustomDialog(getActivity()).Toast("Zaten ilk sayfadasınız.");
            }
        });

        binding.button40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aktif_sayfa > 0) {
                    aktif_sayfa -= 1;
                    Load();
                } else new MyCustomDialog(getActivity()).Toast("Zaten ilk sayfadasınız.");
            }
        });

        binding.button41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aktif_sayfa < max_sayfa - 1) {
                    aktif_sayfa += 1;
                    Load();
                } else new MyCustomDialog(getActivity()).Toast("Son sayfaya ulaştınız.");
            }
        });

        binding.button422.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aktif_sayfa != max_sayfa - 1) {
                    aktif_sayfa = max_sayfa - 1;
                    Load();
                } else new MyCustomDialog(getActivity()).Toast("Son sayfaya ulaştınız.");
            }
        });

        binding.textView96.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightning();
            }
        });
    }

    private CharSequence makale;
    private int myindex = 0;
    private int mylenght = 0;

    private void Load() {
        SetAktifSayfa();
        makale = strings[aktif_sayfa];
        mylenght = makale.length();
        myindex = 0;
        handler.removeCallbacks(characteradder);
        handler.postDelayed(characteradder, 0);
    }

    private void lightning() {
        handler.removeCallbacks(characteradder);
        binding.textView96.setText(makale);
    }

    private final Handler handler = new Handler();

    private final Runnable characteradder = new Runnable() {
        @Override
        public void run() {
            Log.e("makale", "running...");
            binding.textView96.setText(makale.subSequence(0, myindex++));
            if (myindex < mylenght)
                handler.postDelayed(characteradder, 50);
        }
    };

    private void SetAktifSayfa() {
        binding.textView100.setText("Sayfa: " + (aktif_sayfa + 1));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        handler.removeCallbacks(characteradder);
    }
}