package com.pakachu.apaydinfitness.adapters.idman;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.pakachu.apaydinfitness.db.DBIdman;
import com.pakachu.apaydinfitness.R;

import java.util.ArrayList;

public class GunAdapter extends RecyclerView.Adapter<GunAdapter.ViewHolder> {

    Activity activity;
    ArrayList<GunItem> gunItemArrayList;
    boolean autoLoad;

    public GunAdapter(Activity activity, ArrayList<GunItem> gunItemArrayList, boolean autoLoad) {
        this.activity = activity;
        this.gunItemArrayList = gunItemArrayList;
        this.autoLoad = autoLoad;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_gun, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GunItem gunItem = gunItemArrayList.get(position);
        holder.btn_gun.setText(gunItem.gunAdi);
        DBIdman dbIdman = new DBIdman(activity);
        Cursor cursor = dbIdman.getData("SELECT column" + gunItem.gunIndex + " FROM table" + gunItem.tableIndex);
        boolean hasData = false;
        while (cursor.moveToNext()) {
            if (cursor.getString(0) != null)
                hasData = true;
        }
        Drawable img1, img2, img3;
        img1 = ResourcesCompat.getDrawable(activity.getResources(), R.drawable.dropdown_icon, null);
        img2 = ResourcesCompat.getDrawable(activity.getResources(), R.drawable.dropdown_up_icon, null);
        img3 = ResourcesCompat.getDrawable(activity.getResources(), R.drawable.empty_icon, null);
        img1.setBounds(0, 0, 50, 50);
        img2.setBounds(0, 0, 50, 50);
        img3.setBounds(0, 0, 50, 50);
        if (hasData)
            holder.btn_gun.setCompoundDrawables(null, null, img1, null);
        else holder.btn_gun.setCompoundDrawables(null, null, img3, null);
        if (autoLoad) {
            Load(cursor, holder);
            if(hasData)
                holder.btn_gun.setCompoundDrawables(null, null, img2, null);
        }
        else {
            if (hasData) {
                holder.btn_gun.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.btn_gun.getTag().equals("0")) {
                            Load(cursor, holder);
                            holder.btn_gun.setCompoundDrawables(null, null, img2, null);
                        } else {
                            Unload(holder);
                            holder.btn_gun.setCompoundDrawables(null, null, img1, null);
                        }
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return gunItemArrayList.size();
    }

    void Load(Cursor cursor, ViewHolder holder) {
        holder.btn_gun.setTag("1");
        ArrayList<HareketItem> hareketItemArrayList = new ArrayList<>();
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            if (cursor.getString(0) != null) {
                String hareket = cursor.getString(0);
                String hareketAdi = hareket;
                String detay = "";
                if (hareket.contains("\n")) {
                    hareketAdi = hareket.substring(0, hareket.indexOf("\n"));
                    detay = hareket.substring(hareket.indexOf("\n"));
                }
                HareketItem hareketItem = new HareketItem();
                hareketItem.hareketAdi = hareketAdi;
                hareketItem.hareketDetayi = detay;
                hareketItemArrayList.add(hareketItem);
            }
        }
        HareketAdapter adapterMember = new HareketAdapter(activity, hareketItemArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        holder.rv_hareketler.setLayoutManager(linearLayoutManager);
        holder.rv_hareketler.setAdapter(adapterMember);
    }

    void Unload(ViewHolder holder) {
        holder.rv_hareketler.setAdapter(null);
        holder.btn_gun.setTag("0");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button btn_gun;
        RecyclerView rv_hareketler;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            btn_gun = itemView.findViewById(R.id.btn_gun);
            rv_hareketler = itemView.findViewById(R.id.rv_hareketler);

        }
    }


}
