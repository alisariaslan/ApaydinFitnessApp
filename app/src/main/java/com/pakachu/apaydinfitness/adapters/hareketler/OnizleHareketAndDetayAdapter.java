package com.pakachu.apaydinfitness.adapters.hareketler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pakachu.apaydinfitness.R;
import com.pakachu.apaydinfitness.adapters.idman.DetayItem;
import com.pakachu.apaydinfitness.helpers.CheckDetay;
import com.pakachu.apaydinfitness.helpers.CheckHareket;

import java.util.ArrayList;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class OnizleHareketAndDetayAdapter extends RecyclerView.Adapter<OnizleHareketAndDetayAdapter.ViewHolder> {
    Activity activity;
    ArrayList<OnizleHareketAndDetayItem> onizleHareketItemArrayList;

    public OnizleHareketAndDetayAdapter(Activity activity, ArrayList<OnizleHareketAndDetayItem> onizleHareketItemArrayList) {
        this.activity = activity;
        this.onizleHareketItemArrayList = onizleHareketItemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_onizle_hareket_and_detay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OnizleHareketAndDetayItem onizleHareketAndDetayItem = onizleHareketItemArrayList.get(position);

        CheckHareket checkHareket = new CheckHareket(activity);

        CheckDetay checkDetay = new CheckDetay(activity, onizleHareketAndDetayItem.hareketAdi);

        holder.imageView15.setImageDrawable(checkHareket.getIcon(onizleHareketAndDetayItem.hareketAdi, true));
        holder.textView59.setText(onizleHareketAndDetayItem.hareketAdi.toUpperCase(Locale.ROOT));

        holder.textView87.setText(checkDetay.hakkinda);

        holder.gifImageView2.setImageResource(checkDetay.gifID);
//        if (checkDetay.musclesWorked != null)
        holder.imageView28.setImageDrawable(checkDetay.musclesWorked);

        holder.imageView30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.linearLayout.getVisibility() == View.GONE) {
                    v.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.rotate));
                    holder.imageView30.setImageDrawable(AppCompatResources.getDrawable(activity, R.drawable.info_icon2));
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    holder.linearLayout.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.bounce));
                    holder.cardView.setContentPadding(0, 0, 0, 0);
                } else {
                    holder.imageView30.setImageDrawable(AppCompatResources.getDrawable(activity, R.drawable.info_icon));
                    holder.linearLayout.setVisibility(View.GONE);
                    holder.linearLayout.clearAnimation();
                    holder.cardView.setContentPadding(0, 0, 0, 20);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return onizleHareketItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView59, textView87;
        ImageView imageView30, imageView15, imageView28;
        LinearLayout linearLayout;
        CardView cardView;
        GifImageView gifImageView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView59 = itemView.findViewById(R.id.textView59);
            textView87 = itemView.findViewById(R.id.textView87);
            imageView30 = itemView.findViewById(R.id.imageView30);
            imageView15 = itemView.findViewById(R.id.imageView15);
            imageView28 = itemView.findViewById(R.id.imageView28);
            gifImageView2 = itemView.findViewById(R.id.gifImageView2);
            linearLayout = itemView.findViewById(R.id.consina);
            cardView = itemView.findViewById(R.id.cardo);
        }
    }

}
