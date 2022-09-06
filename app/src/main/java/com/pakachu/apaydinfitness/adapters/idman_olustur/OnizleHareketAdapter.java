package com.pakachu.apaydinfitness.adapters.idman_olustur;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pakachu.apaydinfitness.R;
import com.pakachu.apaydinfitness.helpers.CheckHareket;

import java.util.ArrayList;

public class OnizleHareketAdapter extends RecyclerView.Adapter<OnizleHareketAdapter.ViewHolder> {


    Activity activity;
    ArrayList<OnizleHareketItem> onizleHareketItemArrayList;


    public OnizleHareketAdapter(Activity activity, ArrayList<OnizleHareketItem> onizleHareketItemArrayList) {
        this.activity = activity;
        this.onizleHareketItemArrayList = onizleHareketItemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_onizle_hareket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OnizleHareketItem onizleHareketItem = onizleHareketItemArrayList.get(position);

        CheckHareket checkHareket = new CheckHareket(activity);

        String hareket = onizleHareketItem.hareketAdi;
        String hareketAdi = hareket;
        String detay = "";
        if (hareket.contains("\n")) {
            hareketAdi = hareket.substring(0, hareket.indexOf("\n"));
            detay = hareket.substring(hareket.indexOf("\n"));
        }

        holder.textView59.setText("" + (onizleHareketItem.index + 1) + "." + " " + hareketAdi);
        holder.imageView15.setImageDrawable(checkHareket.getIcon(hareketAdi, true));
    }

    @Override
    public int getItemCount() {
        return onizleHareketItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView59;
        ImageView imageView15;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView59 = itemView.findViewById(R.id.textView59);
            imageView15 = itemView.findViewById(R.id.imageView15);
        }
    }

}
