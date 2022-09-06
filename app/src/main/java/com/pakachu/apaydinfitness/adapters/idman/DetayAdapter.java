package com.pakachu.apaydinfitness.adapters.idman;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pakachu.apaydinfitness.R;
import com.pakachu.apaydinfitness.helpers.CheckDetay;

import java.util.ArrayList;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;


public class DetayAdapter extends RecyclerView.Adapter<DetayAdapter.ViewHolder> {

    ArrayList<DetayItem> detayAdapterArrayList;

    private Activity activity;

    public DetayAdapter(Activity activity, ArrayList<DetayItem> detayAdapterArrayList) {
        this.activity = activity;
        this.detayAdapterArrayList = detayAdapterArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detay, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetayItem detayItem = detayAdapterArrayList.get(position);
        CheckDetay checkDetay = new CheckDetay(activity, detayItem.hareketAdi);
        holder.tv_hareketAdi.setText(detayItem.hareketAdi.toUpperCase(Locale.ROOT));
        holder.tv_detay_hakkinda.setText(checkDetay.hakkinda);
        if (checkDetay.gifID != 0)
            holder.gifImageView.setImageResource(checkDetay.gifID);
        if (checkDetay.musclesWorked != null)
            holder.imageView.setImageDrawable(checkDetay.musclesWorked);

        if (checkDetay.videoURL.equals("NO URL"))
            holder.btn_detay_nasil.setVisibility(View.GONE);
        else {
            holder.btn_detay_nasil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openURL = new Intent(Intent.ACTION_VIEW);
                    openURL.setData(Uri.parse(checkDetay.videoURL));
                    activity.startActivity(new Intent(openURL));
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        return detayAdapterArrayList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button btn_detay_nasil;
        TextView tv_hareketAdi, tv_detay_hakkinda;
        GifImageView gifImageView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_detay_nasil = itemView.findViewById(R.id.btn_detay_nasil);
            tv_hareketAdi = itemView.findViewById(R.id.tv_hareketAdi);
            tv_detay_hakkinda = itemView.findViewById(R.id.tv_detay_aciklama);
            gifImageView = itemView.findViewById(R.id.gifImageView);
            imageView = itemView.findViewById(R.id.iv_detay_kaslar);

        }
    }


}