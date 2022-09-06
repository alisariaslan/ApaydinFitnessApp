package com.pakachu.apaydinfitness.adapters.diet;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.pakachu.apaydinfitness.R;

import java.util.ArrayList;

public class PastItemAdapter extends RecyclerView.Adapter<PastItemAdapter.ViewHolder> {
    Activity activity;
    ArrayList<PastItemItem> pastItemItems;

    public PastItemAdapter(Activity activity, ArrayList<PastItemItem> pastItemItems) {
        this.activity = activity;
        this.pastItemItems = pastItemItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_past_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PastItemItem pastItemItem = pastItemItems.get(position);
        holder.textView88.setText(pastItemItem.food_name);
        if(pastItemItem.is_liquid)
            holder.imageView27.setImageDrawable(AppCompatResources.getDrawable(activity,R.drawable.liquid_icon));
    }

    @Override
    public int getItemCount() {
        return pastItemItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView27;
        TextView textView88;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView27 = itemView.findViewById(R.id.imageView27);
            textView88 = itemView.findViewById(R.id.textView88);
        }
    }

}
