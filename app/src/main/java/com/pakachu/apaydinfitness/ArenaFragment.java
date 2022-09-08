package com.pakachu.apaydinfitness;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.pakachu.apaydinfitness.adapters.rekor.RekorItem;
import com.pakachu.apaydinfitness.adapters.rekor.RekorItemAdapter;
import com.pakachu.apaydinfitness.customdialogs.MyCustomDialog;
import com.pakachu.apaydinfitness.databinding.FragmentArenaBinding;
import com.pakachu.apaydinfitness.helpers.AddLoader;
import com.pakachu.apaydinfitness.helpers.JSONWorkbench;

import java.util.ArrayList;
import java.util.Locale;

public class ArenaFragment extends Fragment {

    private FragmentArenaBinding binding;

    private CountDownTimer countDownTimerKol;
    private boolean booleanKol = false;

    private CountDownTimer countDownTimerOmuz;
    private boolean booleanOmuz = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentArenaBinding.inflate(inflater, container, false);

        AddLoader addLoader = new AddLoader(getActivity());
        addLoader.RequestInterstatial();

        binding.btn1Kolgetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressBar1.setVisibility(View.VISIBLE);
                binding.btn1Kolgetir.setVisibility(View.GONE);
                JSONWorkbench jsonWorkbench=new JSONWorkbench(getContext());
                ArrayList<ArrayList> arrayListArrayList= jsonWorkbench.GET("SELECT *, MAX(bicep) FROM uyeler GROUP BY ad ORDER BY MAX(bicep) DESC LIMIT 10");
                countDownTimerKol = new CountDownTimer(10000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        booleanKol = true;
                        if(jsonWorkbench.finishStatus)
                        {
                            cancel();
                            binding.progressBar1.setVisibility(View.GONE);
                            booleanKol = false;
                            binding.textView1.setText("Kol\nİlk 10:");
                            if(arrayListArrayList.size()>0)
                            {
                                ArrayList<RekorItem> rekorItemArrayList = new ArrayList<>();
                                int siralama = 0;
                                for (ArrayList<String> arrayList: arrayListArrayList
                                     ) {
                                    siralama++;
                                    String ad = arrayList.get(2);
                                    String olcu = arrayList.get(arrayList.size() - 1) + " cm";
                                    boolean isMale;
                                    String sex = arrayList.get(4);
                                    if(sex.toUpperCase(Locale.ROOT).equals("ERKEK"))
                                        isMale=true;
                                    else
                                        isMale=false;
                                    RekorItem rekorItem=new RekorItem(ad,olcu,siralama,isMale);
                                    rekorItemArrayList.add(rekorItem);
                                }
                                RekorItemAdapter myAdapter = new RekorItemAdapter(getActivity(), rekorItemArrayList);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                                binding.recyclerView.setLayoutManager(layoutManager);
                                binding.recyclerView.setAdapter(myAdapter);
                            } else {
                                new MyCustomDialog(getActivity()).Toast("Hata! Sistemde kayıtlı üye bulunmamaktadır");
                                binding.textView1.setText("Kol\nMevcut üye bulunmamakta!:");
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        booleanKol = false;
                        binding.progressBar1.setVisibility(View.GONE);
                        binding.btn1Kolgetir.setVisibility(View.VISIBLE);
                        new MyCustomDialog(getActivity()).Toast("Hata! Zaman Aşımı");
                    }
                }.start();
            }
        });

        binding.btn2Omuzgetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressBar2.setVisibility(View.VISIBLE);
                binding.btn2Omuzgetir.setVisibility(View.GONE);
                JSONWorkbench jsonWorkbench=new JSONWorkbench(getContext());
                ArrayList<ArrayList> arrayListArrayList= jsonWorkbench.GET("SELECT *, MAX(omuz) FROM uyeler GROUP BY ad ORDER BY MAX(omuz) DESC LIMIT 10");
                countDownTimerOmuz = new CountDownTimer(10000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        booleanOmuz = true;
                        if(jsonWorkbench.finishStatus)
                        {
                            cancel();
                            binding.progressBar2.setVisibility(View.GONE);
                            booleanOmuz = false;
                            binding.textView2.setText("Omuz\nİlk 10:");
                            if(arrayListArrayList.size()>0)
                            {
                                ArrayList<RekorItem> rekorItemArrayList = new ArrayList<>();
                                int siralama = 0;
                                for (ArrayList<String> arrayList: arrayListArrayList
                                ) {
                                    siralama++;
                                    String ad = arrayList.get(2);
                                    String olcu = arrayList.get(arrayList.size() - 1) + " cm";
                                    boolean isMale;
                                    String sex = arrayList.get(4);
                                    if(sex.toUpperCase(Locale.ROOT).equals("ERKEK"))
                                        isMale=true;
                                    else
                                        isMale=false;
                                    RekorItem rekorItem=new RekorItem(ad,olcu,siralama,isMale);
                                    rekorItemArrayList.add(rekorItem);
                                }
                                RekorItemAdapter myAdapter = new RekorItemAdapter(getActivity(), rekorItemArrayList);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                                binding.recyclerView2.setLayoutManager(layoutManager);
                                binding.recyclerView2.setAdapter(myAdapter);
                            } else {
                                new MyCustomDialog(getActivity()).Toast("Hata! Sistemde kayıtlı üye bulunmamaktadır");
                                binding.textView1.setText("Kol\nMevcut üye bulunmamakta!:");
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        booleanOmuz = false;
                        binding.progressBar2.setVisibility(View.GONE);
                        binding.btn2Omuzgetir.setVisibility(View.VISIBLE);
                        new MyCustomDialog(getActivity()).Toast("Hata! Zaman Aşımı");
                    }
                }.start();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.imageView19.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.fadein));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
        if(booleanKol)
            countDownTimerKol.cancel();
        if(booleanOmuz)
            countDownTimerOmuz.cancel();
    }
}