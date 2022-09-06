package com.pakachu.apaydinfitness.adapters.diet;

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
import com.pakachu.apaydinfitness.db.DBDiet;

import java.util.ArrayList;

public class PastAdapter extends RecyclerView.Adapter<PastAdapter.ViewHolder> {
    Activity activity;
    ArrayList<PastItem> pastItems;

    public PastAdapter(Activity activity, ArrayList<PastItem> pastItems) {
        this.activity = activity;
        this.pastItems = pastItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_past, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PastItem pastItem = pastItems.get(position);
        holder.textView86.setText(pastItem.date+" ("+pastItem.calorie_taken+"kcal)");
        String[] food_indexes = pastItem.indexes.split(",");
        ArrayList<PastItemItem> pastItemItems = new ArrayList<>();
        for (String s: food_indexes
             ) {
            int index = Integer.parseInt(s);
            DBDiet dbDiet=new DBDiet(activity);
            Cursor c = dbDiet.getDataWithSQL("SELECT food_name,is_liquid FROM food_list WHERE id="+index);
            if(c.moveToFirst())
            {
                boolean is_liquid = c.getInt(1) == 1;
                PastItemItem pastItemItem = new PastItemItem(c.getString(0),is_liquid);
                pastItemItems.add(pastItemItem);
            } else {
                PastItemItem pastItemItem = new PastItemItem("Silinmiş",false);
                pastItemItems.add(pastItemItem);
            }
        }
        PastItemAdapter adapterMember = new PastItemAdapter(activity, pastItemItems);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(adapterMember);
    }

    @Override
    public int getItemCount() {
        return pastItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView86;
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView86 = itemView.findViewById(R.id.textView86);
            recyclerView =  itemView.findViewById(R.id.recai_view);
        }
    }

}
