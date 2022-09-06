package com.pakachu.apaydinfitness.adapters.idman;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pakachu.apaydinfitness.R;
import com.pakachu.apaydinfitness.helpers.CheckHareket;

import java.util.ArrayList;
import java.util.Locale;

public class HareketAdapter extends RecyclerView.Adapter<HareketAdapter.ViewHolder> {

    Activity activity;
    ArrayList<HareketItem> hareketArrayList;


    public HareketAdapter(Activity activity, ArrayList<HareketItem> hareketArrayList) {
        this.activity = activity;
        this.hareketArrayList = hareketArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_hareket, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HareketItem hareketItem = hareketArrayList.get(position);
        holder.btn_hareket.setText(hareketItem.hareketAdi);
        holder.tv_complete.setText(hareketItem.hareketAdi.toUpperCase(Locale.ROOT) + " tamamlandı");
        CheckHareket checkHareket = new CheckHareket(activity);
        holder.btn_hareket.setCompoundDrawables(null, checkHareket.getIcon(hareketItem.hareketAdi,true), null, null);

        ArrayList<DetayItem> detayItemArrayList = new ArrayList<>();
        DetayItem detayItem = new DetayItem();
        detayItem.hareketAdi = hareketItem.hareketAdi;
        detayItemArrayList.add(detayItem);
        DetayAdapter adapterMember = new DetayAdapter(activity, detayItemArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        holder.rv_detaylar.setLayoutManager(linearLayoutManager);

        CountDownTimer countDownTimer = new CountDownTimer(999999999, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                holder.timerActive = true;
                holder.milisec += 1;
                if (holder.milisec == 60) {
                    holder.milisec = 0;
                    holder.sec++;
                }
                if (holder.sec == 60) {
                    holder.sec = 0;
                    holder.min++;
                }
                String minString = "" + holder.min;
                String secString = "" + holder.sec;
                String milisecString = "" + holder.milisec;

                String timeString = minString + ":" + secString + ":" + milisecString;
                holder.tv_sayac.setText(timeString);
                if (holder.min == 60) {
                    cancel();
                    holder.timerActive = false;
                }
            }

            @Override
            public void onFinish() {
                holder.timerActive = false;
            }
        };

        holder.btn_hareket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cl_islev.getVisibility() == View.GONE) {
                    holder.btn_hareket.setText(hareketItem.hareketAdi + hareketItem.hareketDetayi);
                    holder.cl_islev.setVisibility(View.VISIBLE);
                    holder.cl_islev.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.lefttoright));
                } else {
                    holder.btn_hareket.setText(hareketItem.hareketAdi);
                    holder.cl_islev.clearAnimation();
                    holder.cl_islev.setVisibility(View.GONE);
                    SayacDurdur(holder, countDownTimer);
                }

            }
        });

        holder.iv_checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tv_complete.getVisibility() == View.GONE) {
                    holder.btn_hareket.setEnabled(false);
                    holder.tv_complete.setVisibility(View.VISIBLE);
                    holder.tv_complete.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fadein));
                    holder.iv_checked.setImageResource(R.drawable.checked_icon2);
                    holder.btn_hareket.setText(hareketItem.hareketAdi);
                    holder.btn_hareket.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fadeout));
                    holder.iv_info.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fadeout));
                    holder.cl_islev.clearAnimation();
                    holder.cl_islev.setVisibility(View.GONE);
                    holder.iv_info.setTag("0");
                    holder.iv_info.setEnabled(false);
                    holder.rv_detaylar.setAdapter(null);
                    holder.iv_info.setImageDrawable(AppCompatResources.getDrawable(activity, R.drawable.info_icon));
                    SayacDurdur(holder, countDownTimer);
                } else {
                    holder.iv_info.setEnabled(true);
                    holder.btn_hareket.setEnabled(true);
                    holder.tv_complete.clearAnimation();
                    holder.tv_complete.setVisibility(View.GONE);
                    holder.tv_complete.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fadeout));
                    holder.iv_checked.setImageResource(R.drawable.checked_icon);
                    holder.btn_hareket.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fadein));
                    holder.iv_info.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fadein));
                }

            }
        });


        holder.btn_sayacArttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.sayac < 99)
                    holder.sayac++;
                holder.tv_sayac.setText("" + holder.sayac);
            }
        });
        holder.btn_sayacAzalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.sayac > 0)
                    holder.sayac--;
                holder.tv_sayac.setText("" + holder.sayac);
            }
        });


        holder.btn_sureBaslat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.btn_sureBaslat.getTag().equals("0")) {
                    holder.btn_sureBaslat.setTag("1");
                    SayacBaslat(holder, countDownTimer);
                } else {
                    SayacDurdur(holder, countDownTimer);
                }
            }
        });


        holder.iv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.iv_info.getTag().equals("0")) {
                    holder.iv_info.setTag("1");
                    holder.rv_detaylar.setAdapter(adapterMember);
                    holder.iv_info.setImageDrawable(AppCompatResources.getDrawable(activity, R.drawable.info_icon2));
                } else {
                    holder.iv_info.setTag("0");
                    holder.rv_detaylar.setAdapter(null);
                    holder.iv_info.setImageDrawable(AppCompatResources.getDrawable(activity, R.drawable.info_icon));

                }
            }
        });
    }

    void SayacBaslat(ViewHolder holder, CountDownTimer countDownTimer) {
        countDownTimer.start();
        Drawable icon = AppCompatResources.getDrawable(activity, R.drawable.sayac_durdur_icon);
        icon.setBounds(0, 0, 100, 100);
        holder.btn_sureBaslat.setCompoundDrawables(null, icon, null, null);
        holder.btn_sureBaslat.setText("süreyi\ndurdur");
//        holder.tv_sayac.setPadding(50, 0, 0, 0);
//        holder.tv_sayac.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        holder.btn_sayacArttir.setVisibility(View.GONE);
        holder.btn_sayacAzalt.setVisibility(View.GONE);
    }

    void SayacDurdur(ViewHolder holder, CountDownTimer countDownTimer) {
        holder.btn_sureBaslat.setTag("0");
        if (holder.timerActive) {
            countDownTimer.cancel();
            holder.timerActive = false;
        }
//        holder.tv_sayac.setPadding(0, 0, 0, 0);
//        holder.tv_sayac.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        holder.sayac = 0;
        holder.min = 0;
        holder.sec = 0;
        holder.milisec = 0;
        holder.tv_sayac.setText("" + holder.sayac);
        holder.btn_sayacArttir.setVisibility(View.VISIBLE);
        holder.btn_sayacAzalt.setVisibility(View.VISIBLE);
        Drawable icon = AppCompatResources.getDrawable(activity, R.drawable.alarm_icon);
        icon.setBounds(0, 0, 100, 100);
        holder.btn_sureBaslat.setCompoundDrawables(null, icon, null, null);
        holder.btn_sureBaslat.setText("süreyi\nbaşlat");
    }

    @Override
    public int getItemCount() {
        return hareketArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        int min = 0;
        int sec = 0;
        int milisec = 0;
        boolean timerActive = false;
        int sayac = 0;
        ImageView iv_info, iv_checked;
        Button btn_hareket, btn_sayacArttir, btn_sayacAzalt, btn_sureBaslat;
        RecyclerView rv_detaylar;
        ConstraintLayout cl_islev;
        TextView tv_complete, tv_sayac;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_detaylar = itemView.findViewById(R.id.rv_detaylar);
            btn_hareket = itemView.findViewById(R.id.btn_hareket);
            btn_sayacArttir = itemView.findViewById(R.id.btn_sayacArttir);
            btn_sayacAzalt = itemView.findViewById(R.id.btn_sayacAzalt);
            btn_sureBaslat = itemView.findViewById(R.id.btn_sureBaslat);
            cl_islev = itemView.findViewById(R.id.cl_islev);
            iv_info = itemView.findViewById(R.id.iv_info);
            iv_checked = itemView.findViewById(R.id.iv_checked);
            tv_complete = itemView.findViewById(R.id.tv_complete);
            tv_sayac = itemView.findViewById(R.id.tv_sayac);
        }
    }


}