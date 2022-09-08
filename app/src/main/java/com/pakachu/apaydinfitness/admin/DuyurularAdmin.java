package com.pakachu.apaydinfitness.admin;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.pakachu.apaydinfitness.customdialogs.MyCustomDialog;
import com.pakachu.apaydinfitness.R;
import com.pakachu.apaydinfitness.databinding.FragmentDuyurularAdminBinding;
import com.pakachu.apaydinfitness.db.DBNotif;
import com.pakachu.apaydinfitness.helpers.JSONWorkbench;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class DuyurularAdmin extends Fragment {

    private FragmentDuyurularAdminBinding binding;

    private boolean timer;
    private CountDownTimer countDownTimer;

    private boolean lastNotifTimer;
    private CountDownTimer lastNotifDownTimer;

    private boolean arindirTimer;
    private CountDownTimer arindirDownTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDuyurularAdminBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        CheckLastNotif();

        binding.button30.setOnClickListener(new View.OnClickListener() { //TEMİZLE
            @Override
            public void onClick(View v) {
                binding.editTextTextPersonName29.setText("");
                binding.editTextTextPersonName29.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
                binding.editTextTextPersonName29.requestFocus();
                new MyCustomDialog(getActivity()).Toast("Temizlendi");
            }
        });

        binding.button27.setOnClickListener(new View.OnClickListener() { //KOPYALA
            @Override
            public void onClick(View v) {
                String string = binding.editTextTextPersonName29.getText().toString().trim();
                if(!string.equals(""))
                {
                    ClipData clip = ClipData.newPlainText("duyuruString", string);
                    clipboard.setPrimaryClip(clip);
                    new MyCustomDialog(getActivity()).Toast("Kopyalama başarılı");
                } else
                    new MyCustomDialog(getActivity()).Toast("Boş alan kopyalanamaz!");
            }
        });

        binding.button28.setOnClickListener(new View.OnClickListener() { //YAPIŞTIR
            @Override
            public void onClick(View v) {
                ClipData clipData = clipboard.getPrimaryClip();
                ClipData.Item item = clipData.getItemAt(0);
                String string = item.getText().toString();
                binding.editTextTextPersonName29.setText(string);
                binding.editTextTextPersonName29.requestFocus();
            }
        });

        binding.button12.setOnClickListener(new View.OnClickListener() { //YAYINLA
            @Override
            public void onClick(View v) {
                Calendar dateCal = Calendar.getInstance();
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                String date = formatter.format(dateCal.getTime());
                String firstString = binding.editTextTextPersonName29.getText().toString().trim();
                if (!firstString.equals("")) {
                    binding.editTextTextPersonName29.setEnabled(false);
                    firstString += " (" + date + ")";
                    binding.progressBar5.setVisibility(View.VISIBLE);
                    JSONWorkbench jsonWorkbench = new JSONWorkbench(getContext());
                    jsonWorkbench.ADDNOTIF(firstString);
                    countDownTimer = new CountDownTimer(10000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            timer = true;
                            if (jsonWorkbench.finishStatus) {
                                timer = false;
                                cancel();
                                binding.editTextTextPersonName29.setEnabled(true);
                                binding.progressBar5.setVisibility(View.GONE);
                                binding.editTextTextPersonName29.setText("");
                                CheckLastNotif();
                            }
                        }

                        @Override
                        public void onFinish() {
                            timer = false;
                            binding.progressBar5.setVisibility(View.GONE);
                            new MyCustomDialog(getActivity()).Toast("Zaman aşımı");
                            binding.editTextTextPersonName29.setEnabled(true);
                        }
                    }.start();
                } else {
                    new MyCustomDialog(getActivity()).Toast("Duyuru yazısı boş olamaz!");
                }

            }
        });

        binding.button19.setOnClickListener(new View.OnClickListener() { //arindir
            @Override
            public void onClick(View v) {
                JSONWorkbench jsonWorkbench = new JSONWorkbench(getContext());
                jsonWorkbench.SET("DELETE FROM duyurular");
                DBNotif dbNotif = new DBNotif(getContext());
                dbNotif.deleteData();
                binding.progressBar7.setVisibility(View.VISIBLE);
                binding.button19.setVisibility(View.GONE);
                arindirDownTimer = new CountDownTimer(10000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if (jsonWorkbench.finishStatus) {
                            cancel();
                            onFinish();
                            new MyCustomDialog(getActivity()).Toast("Arındırma başarılı");
                            CheckLastNotif();
                        }
                    }

                    @Override
                    public void onFinish() {
                        binding.progressBar7.setVisibility(View.GONE);
                        binding.button19.setVisibility(View.VISIBLE);
                    }
                }.start();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (timer)
            countDownTimer.cancel();
        if (lastNotifTimer)
            lastNotifDownTimer.cancel();
        if (arindirTimer)
            arindirDownTimer.cancel();
    }

    private void CheckLastNotif() {
        JSONWorkbench jsonWorkbench = new JSONWorkbench(getContext());
        ArrayList<ArrayList> arrayListArrayList = jsonWorkbench.GET("SELECT * FROM duyurular ORDER BY ID DESC LIMIT 1");
        binding.progressBar6.setVisibility(View.VISIBLE);
        lastNotifDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                lastNotifTimer = true;
                if (jsonWorkbench.finishStatus) {
                    cancel();
                    onFinish();
                    if (arrayListArrayList.size() > 0) {
                        ArrayList<String> arrayList = arrayListArrayList.get(0);
                        binding.textView54.setText(arrayList.get(1));
                    } else {
                        binding.textView54.setText("Herhangi bir duyuru mevcut değil.");
                    }

                }
            }

            @Override
            public void onFinish() {
                binding.progressBar6.setVisibility(View.GONE);
                binding.textView54.setText("Bağlantı hatası!");
                lastNotifTimer = false;
            }
        }.start();

    }

}