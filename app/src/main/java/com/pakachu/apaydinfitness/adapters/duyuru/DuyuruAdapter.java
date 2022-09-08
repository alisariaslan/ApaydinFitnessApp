package com.pakachu.apaydinfitness.adapters.duyuru;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.pakachu.apaydinfitness.DuyurularFragment;
import com.pakachu.apaydinfitness.customdialogs.MyCustomDialog;
import com.pakachu.apaydinfitness.R;
import com.pakachu.apaydinfitness.db.DBNotif;

import java.util.ArrayList;

public class DuyuruAdapter extends RecyclerView.Adapter<DuyuruAdapter.ViewHolder> {

    Context context;
    Activity activity;
    ArrayList<DuyuruItem> duyuruItemArrayList;
    DuyurularFragment duyurularFragment;

    public DuyuruAdapter(Activity activity, Context context, ArrayList<DuyuruItem> duyuruItemArrayList, DuyurularFragment duyurularFragment) {
        this.activity = activity;
        this.duyuruItemArrayList = duyuruItemArrayList;
        this.context = context;
        this.duyurularFragment = duyurularFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_duyuru, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DuyuruItem duyuruItem = duyuruItemArrayList.get(position);
        String text= duyuruItem.text;
        int id = duyuruItem.id;
        boolean okundu = duyuruItem.okunmus;
        if(okundu)
            holder.button.setText("Kopyala");
        holder.textView.setText(text);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.button.setBackgroundColor(Color.WHITE);
                if(okundu) {
                    holder.button.setText("Kopyalandı");
                    ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("duyuruString", text);
                    clipboard.setPrimaryClip(clip);
                } else {
                    holder.button.setText("Okundu");
                    DBNotif dbNotif=new DBNotif(context);
                    dbNotif.executeSQL("UPDATE notif SET okundu=1 WHERE id="+id);
                    new MyCustomDialog(activity).Toast("Bildirim okundu olarak işaretlendi");
                    holder.cardView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.torightfast));
                    new CountDownTimer(1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            duyurularFragment.Getir();
                        }
                    }.start();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return duyuruItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        Button button;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView5);
            button = itemView.findViewById(R.id.button12);
            cardView = itemView.findViewById(R.id.cardView35);
        }
    }

}
