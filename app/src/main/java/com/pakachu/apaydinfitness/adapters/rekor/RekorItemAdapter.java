package com.pakachu.apaydinfitness.adapters.rekor;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.pakachu.apaydinfitness.R;

import java.util.ArrayList;


public class RekorItemAdapter extends RecyclerView.Adapter<RekorItemAdapter.ViewHolder> {

    ArrayList<RekorItem> rekorItemArrayList;
    int animationDuration = 1000;
    private Activity activity;

    public RekorItemAdapter(Activity activity, ArrayList<RekorItem> rekorItemArrayList) {
        this.activity = activity;
        this.rekorItemArrayList = rekorItemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rekoritem, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(holder.constraintLayout, "alpha", 0.0f, 1, 1);
        animation.setDuration(animationDuration);
        animation.start();
        animationDuration+=500;

        RekorItem detayItem = rekorItemArrayList.get(position);
        if (detayItem.ismale)
            holder.imageViewAvatar.setImageDrawable(AppCompatResources.getDrawable(activity, R.drawable.male_icon_rounded));
        holder.textViewBaslik.setText(detayItem.baslik);
        holder.textViewYazi.setText(detayItem.yazi);
        int sira = detayItem.siralama;
        holder.textViewSira.setText("" + sira + ".");
        if (sira > 3) {
            holder.imageViewMedal.setVisibility(View.GONE);
            holder.textViewSira.setVisibility(View.VISIBLE);
        }
        if (sira == 2)
            holder.imageViewMedal.setImageDrawable(AppCompatResources.getDrawable(activity, R.drawable.medal_silver));
        else if (sira == 3)
            holder.imageViewMedal.setImageDrawable(AppCompatResources.getDrawable(activity, R.drawable.medal_bronze));
    }

    @Override
    public int getItemCount() {
        return rekorItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMedal;
        ImageView imageViewAvatar;
        TextView textViewBaslik;
        TextView textViewYazi;
        TextView textViewSira;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewMedal = itemView.findViewById(R.id.imageView21);
            imageViewAvatar = itemView.findViewById(R.id.imageView20);
            textViewBaslik = itemView.findViewById(R.id.textView60);
            textViewYazi = itemView.findViewById(R.id.textView61);
            textViewSira = itemView.findViewById(R.id.textView62);
            constraintLayout = itemView.findViewById(R.id.cl_rekor);
        }
    }


}