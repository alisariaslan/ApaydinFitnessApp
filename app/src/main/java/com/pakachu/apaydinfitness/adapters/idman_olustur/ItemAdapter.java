package com.pakachu.apaydinfitness.adapters.idman_olustur;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.pakachu.apaydinfitness.AntrenmanOlusturFragment;
import com.pakachu.apaydinfitness.helpers.CheckHareket;
import com.pakachu.apaydinfitness.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    Activity activity;
    ArrayList<ItemItem> itemItemArrayList;
    public AntrenmanOlusturFragment antrenmanOlusturFragment;

    public ItemAdapter(Activity activity, ArrayList<ItemItem> itemItemArrayList) {
        this.activity = activity;
        this.itemItemArrayList = itemItemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CheckHareket checkHareket = new CheckHareket(activity);
        ItemItem itemItem = itemItemArrayList.get(holder.getAdapterPosition());
        holder.button.setText(itemItem.text);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (itemItem.switchInt) {
                    case 0:
                        antrenmanOlusturFragment.GunCikart(holder.getAdapterPosition());
                        break;
                    case 1:
                        if(!antrenmanOlusturFragment.inwork)
                        {
                            antrenmanOlusturFragment.inwork=true;
                            holder.adana.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.torightfast));
                            antrenmanOlusturFragment.countDownTimer = new CountDownTimer(1000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    antrenmanOlusturFragment.timer=true;
                                }

                                @Override
                                public void onFinish() {
                                    antrenmanOlusturFragment.HareketCikart(holder.getAdapterPosition());
                                    antrenmanOlusturFragment.timer=false;
                                }
                            }.start();
                        }
                        break;
                    case 2:
                        antrenmanOlusturFragment.HareketEkle(holder.getAdapterPosition());
                        break;
                    case 3:
                        antrenmanOlusturFragment.TumHareketler_HareketSil(holder.getAdapterPosition());
                        break;
                    case 4:
//                        antrenmanAktarFragment.Aktar(itemItem.text, itemItem.mainIndex);
                        break;
                    default:
                        Toast.makeText(activity, "switchInt değişkeni atanmamış!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        String hareket = "" + itemItem.text;
        String hareketAdi = "" + hareket;
        String detay = "";
        if (hareket.contains("\n")) {
            hareketAdi = hareket.substring(0, hareket.indexOf("\n"));
            detay = hareket.substring(hareket.indexOf("\n"));
        }
        if (itemItem.switchInt > 0) {
            holder.button.setCompoundDrawables(checkHareket.getIcon(hareketAdi, true), null, holder.button.getCompoundDrawables()[2], null);
        }
        if (itemItem.switchInt == 1) { //PROGRAMDAKİ HAREKETLER
            if (itemItemArrayList.size() > 1) {
                holder.constraintLayout.setVisibility(View.VISIBLE);
                if (itemItem.index == 0)
                    holder.cardViewUp.setVisibility(View.GONE);
                else if (itemItem.index == itemItemArrayList.size() - 1)
                    holder.cardViewDown.setVisibility(View.GONE);
            }

            Log.e("checklog", "index: " + itemItem.index + " size: " + (itemItemArrayList.size() - 1));
        }
        if (itemItem.switchInt == 2) { //EKLENECEK HAREKETLER
            Drawable img = AppCompatResources.getDrawable(activity, android.R.drawable.ic_input_add);
            img.setBounds(0, 0, 75, 75);
            holder.button.setCompoundDrawables(holder.button.getCompoundDrawables()[0], null, img, null);
        }
        if (itemItem.switchInt == 4)
            holder.button.setCompoundDrawables(null, null, null, null);

        holder.cardViewUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!antrenmanOlusturFragment.inwork)
               {
                   antrenmanOlusturFragment.inwork=true;
                   ItemItem upperItem = itemItemArrayList.get(holder.getAdapterPosition() - 1);
                   upperItem.constraintLayout.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.todown));
                   holder.adana.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.toup));
                   antrenmanOlusturFragment.countDownTimer =new CountDownTimer(300, 1000) {
                       @Override
                       public void onTick(long millisUntilFinished) {
                           antrenmanOlusturFragment.timer=true;
                       }

                       @Override
                       public void onFinish() {
                           antrenmanOlusturFragment.HareketUP(holder.getAdapterPosition());
                           antrenmanOlusturFragment.timer=false;
                       }
                   }.start();
               }
            }
        });

        holder.cardViewDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!antrenmanOlusturFragment.inwork)
                {
                    antrenmanOlusturFragment.inwork=true;
                    ItemItem downItem = itemItemArrayList.get(holder.getAdapterPosition() + 1);
                    downItem.constraintLayout.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.toup));
                    holder.adana.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.todown));
                    antrenmanOlusturFragment.countDownTimer = new CountDownTimer(300, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            antrenmanOlusturFragment.timer=true;
                        }

                        @Override
                        public void onFinish() {
                            antrenmanOlusturFragment.HareketDOWN(holder.getAdapterPosition());
                            antrenmanOlusturFragment.timer=false;
                        }
                    }.start();
                }
            }
        });

        itemItem.constraintLayout = holder.adana;
    }

    @Override
    public int getItemCount() {
        return itemItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button button;
        ConstraintLayout constraintLayout;
        ConstraintLayout adana;
        CardView cardViewUp;
        CardView cardViewDown;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button16);
            constraintLayout = itemView.findViewById(R.id.constraintLayout5);
            adana = itemView.findViewById(R.id.cl_adana);
            cardViewUp = itemView.findViewById(R.id.cardView7);
            cardViewDown = itemView.findViewById(R.id.cardView6);
        }
    }


}
