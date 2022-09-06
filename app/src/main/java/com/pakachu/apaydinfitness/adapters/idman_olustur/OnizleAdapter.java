package com.pakachu.apaydinfitness.adapters.idman_olustur;

import android.app.Activity;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pakachu.apaydinfitness.R;
import com.pakachu.apaydinfitness.db.DBIdman;

import java.util.ArrayList;

public class OnizleAdapter extends RecyclerView.Adapter<OnizleAdapter.ViewHolder> {


    Activity activity;
    ArrayList<OnizleItem> onizleItemArrayList;


    public OnizleAdapter(Activity activity, ArrayList<OnizleItem> onizleItemArrayList) {
        this.activity = activity;
        this.onizleItemArrayList = onizleItemArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_onizle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OnizleItem onizleItem = onizleItemArrayList.get(position);

        holder.tv_baslik.setText(onizleItem.gunAdi);
        DBIdman dbIdman = new DBIdman(activity);
        Cursor cursor = dbIdman.getData("SELECT * FROM table" + onizleItem.tablePos);

        ArrayList<OnizleHareketItem> onizleHareketItemArrayList = new ArrayList<>();
        int gunIndex = onizleItem.gunIndex;

        int i = 0;
        while (cursor.moveToNext()) {
            if (cursor.getString(gunIndex) != null) {
                OnizleHareketItem onizleHareketItem = new OnizleHareketItem(cursor.getString(gunIndex), i);
                onizleHareketItemArrayList.add(onizleHareketItem);
                i++;
            }
        }
        OnizleHareketAdapter adapterMember = new OnizleHareketAdapter(activity, onizleHareketItemArrayList);
        holder.rv_harekets.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        holder.rv_harekets.setAdapter(adapterMember);

    }

    @Override
    public int getItemCount() {
        return onizleItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_baslik;
        RecyclerView rv_harekets;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_baslik = itemView.findViewById(R.id.textView58);
            rv_harekets = itemView.findViewById(R.id.rv_harekets);
        }
    }

}
