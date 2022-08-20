package com.pakachu.apaydinfitness.helpers;

import android.content.Context;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.pakachu.apaydinfitness.R;
import com.pakachu.apaydinfitness.db.DBNotif;

import java.util.ArrayList;

public class CheckNotif {

    private Context context;
    private JSONWorkbench jsonWorkbench;
    private ArrayList<ArrayList> arrayListArrayList;
    private DBNotif dbNotif;

    public CheckNotif(Context context) {
        this.context = context;
        this.jsonWorkbench = new JSONWorkbench(context);
        this.arrayListArrayList = new ArrayList<>();
        this.dbNotif = new DBNotif(context);
    }

    public void Check() {
        arrayListArrayList = jsonWorkbench.GET("SELECT * FROM duyurular ORDER BY ID DESC LIMIT 10");
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (jsonWorkbench.finishStatus) {
                    cancel();
                    Compare();
                }
            }

            @Override
            public void onFinish() {
                Log.e("notifTimer", "Zaman Aşımı");
            }
        }.start();
    }

    private void Compare() {
        String lastNotifText = "";
        int i = 0;
        i = arrayListArrayList.size() - 1;
        while (i >= 0) {
            ArrayList<String> arrayList = arrayListArrayList.get(i);

            int id = Integer.parseInt(arrayList.get(0));
            String text = arrayList.get(1);
            boolean var = false;
            int cursorID;
            Cursor cursor = dbNotif.getData();
            while (cursor.moveToNext()) {
                cursorID = cursor.getInt(0);
                if (id == cursorID)
                    var = true;
            }
            if (!var) {
                dbNotif.addData(id, text, 0);
                lastNotifText = text;
            }

            i--;
        }

        if (!lastNotifText.equals(""))
            Notify(lastNotifText);
    }

    public void Notify(String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "my_notif")
                .setSmallIcon(R.drawable.apaydin_fitness_icon)
                .setContentTitle("Apaydın Fitness Center")
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, builder.build());
    }
}
