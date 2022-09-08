package com.pakachu.apaydinfitness.adapters.diet;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pakachu.apaydinfitness.DietFragment;
import com.pakachu.apaydinfitness.customdialogs.MyCustomDialog;
import com.pakachu.apaydinfitness.R;
import com.pakachu.apaydinfitness.db.DBDiet;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    Activity activity;
    ArrayList<FoodItem> foodItems;
    DietFragment dietFragment;

    public FoodAdapter(Activity activity, ArrayList<FoodItem> foodItems, DietFragment dietFragment) {
        this.activity = activity;
        this.foodItems = foodItems;
        this.dietFragment = dietFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodItem foodItem = foodItems.get(position);
        if (foodItem.isliquid)
            holder.imageView.setImageDrawable(AppCompatResources.getDrawable(activity, R.drawable.liquid_icon));
        if (foodItem.status == 0) {
            String birim = (foodItem.isliquid) ? "gramında:" : "mililitresinde:";
            float kcal = foodItem.protein * 4 + foodItem.carb * 4 + foodItem.fat * 9;
            holder.textView84.setText(foodItem.gram + " " + birim + "\n" + foodItem.protein + " gr protein,\n" + foodItem.carb + " gr karbonhidrat,\n" + foodItem.fat + " gr yağ,\ntoplam " + kcal + "kcal.");
            holder.textView.setText(foodItem.foodName);
            holder.seekBar.setMax(foodItem.gram);
            holder.seekBar.setProgress(foodItem.gram);
            holder.imageView24.setVisibility(View.GONE);
            holder.imageView23.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.rotate));
                    holder.cardView.setVisibility(View.VISIBLE);
                    holder.cardView.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fall));
                }
            });
            holder.eklebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.seekBar.getProgress() != 0) {
                        holder.cardView.setVisibility(View.GONE);
                        DBDiet dbDiet = new DBDiet(activity);
                        dbDiet.addDataToDailyList(foodItem.id, holder.seekBar.getProgress());
                        dietFragment.ListDailyFoods();
                        new MyCustomDialog(activity).Toast(foodItem.foodName + " bugün yediklerime eklendi.");
                    } else new MyCustomDialog(activity).Toast("Hata! Değer 0 olamaz!");
                }
            });
            holder.iptalbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.cardView.clearAnimation();
                    holder.cardView.setVisibility(View.GONE);
                }
            });
            holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    String birim = (foodItem.isliquid) ? "ml içtim" : "gr yedim";
                    holder.textView82.setText(progress + "" + birim);
                    birim = (foodItem.isliquid) ? "gramında" : "mililitresinde";
                    int gram = progress;
                    float protein = gram * foodItem.protein / foodItem.gram;
                    float carb = gram * foodItem.carb / foodItem.gram;
                    float fat = gram * foodItem.fat / foodItem.gram;
                    float kcal = protein * 4 + carb * 4 + fat * 9;
                    holder.textView85.setText(gram + " " + birim + ":\n" + protein + " gr protein,\n" + carb + " gr karbonhidrat,\n" + fat + " gr yağ,\ntoplam " + kcal + "kcal.");
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            holder.imageView26.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.rotate));
                    MyCustomDialog myCustomDialog = new MyCustomDialog(activity);
                    myCustomDialog.setCaption("Emin misiniz?");
                    myCustomDialog.setContent(foodItem.foodName + " adlı yemek kalıcı olarak silinecektir!");
                    myCustomDialog.setButtons("Sil", "İptal");
                    myCustomDialog.positive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myCustomDialog.dissmiss();
                            DBDiet dbDiet = new DBDiet(activity);
                            dbDiet.executeSQL("DELETE FROM food_list WHERE id=" + foodItem.id);
                            dbDiet.executeSQL("DELETE FROM daily_list WHERE food_index=" + foodItem.id);
                            dietFragment.ListFoods(100);
                            dietFragment.ListDailyFoods();
                            new MyCustomDialog(activity).Toast(foodItem.foodName + " başarıyla silindi.");
                        }
                    });
                    myCustomDialog.negative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myCustomDialog.dissmiss();
                        }
                    });
                    myCustomDialog.show(false);
                }
            });

        } else {
            String birim = foodItem.isliquid ? "ml" : "gr";
//            holder.textView.setText("("+foodItem.gram+birim+") "+foodItem.foodName+"\n"+foodItem.protein+" gr protein,\n"+foodItem.carb+" gr karbonhidrat,\n"+foodItem.fat+" gr yağ");
            float kcal = foodItem.protein * 4 + foodItem.carb *4 +foodItem.fat*9;
            holder.textView.setText("(" + foodItem.gram + birim + ") " + foodItem.foodName+" ("+kcal+"kcal)");
            holder.imageView23.setVisibility(View.GONE);
            holder.imageView26.setVisibility(View.GONE);
            holder.imageView24.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBDiet dbDiet = new DBDiet(activity);
                    dbDiet.executeSQL("DELETE FROM daily_list WHERE id=" + foodItem.id);
                    dietFragment.ListDailyFoods();
                    new MyCustomDialog(activity).Toast("(" + foodItem.gram + "gr) " + foodItem.foodName + " bugün yediklerimden çıkartıldı.");
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, textView82, textView84, textView85;
        ImageView imageView, imageView23, imageView24, imageView26;
        CardView cardView;
        Button eklebtn, iptalbtn;
        SeekBar seekBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView83);
            textView82 = itemView.findViewById(R.id.textView82);
            textView84 = itemView.findViewById(R.id.textView84);
            textView85 = itemView.findViewById(R.id.textView85);
            imageView = itemView.findViewById(R.id.imageView25);
            imageView23 = itemView.findViewById(R.id.imageView23); //add
            imageView24 = itemView.findViewById(R.id.imageView24); //minus
            imageView26 = itemView.findViewById(R.id.imageView26); //delete
            cardView = itemView.findViewById(R.id.cw_nekadaryedim);
            eklebtn = itemView.findViewById(R.id.button34);
            iptalbtn = itemView.findViewById(R.id.button35);
            seekBar = itemView.findViewById(R.id.seekBar);
        }
    }

}
