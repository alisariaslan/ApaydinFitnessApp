package com.pakachu.apaydinfitness.adapters.idman;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.pakachu.apaydinfitness.customdialogs.QRDialog;
import com.pakachu.apaydinfitness.db.DBIdman;
import com.pakachu.apaydinfitness.helpers.ImageProcess;
import com.pakachu.apaydinfitness.R;
import com.pakachu.apaydinfitness.helpers.StringCompressor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {


    Activity activity;
    ArrayList<ProgramItem> programItemArrayList;
    boolean autoLoad;

    public ProgramAdapter(Activity activity, ArrayList<ProgramItem> programItemArrayList, boolean autoLoad) {
        this.activity = activity;
        this.programItemArrayList = programItemArrayList;
        this.autoLoad = autoLoad;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_program, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProgramItem programItem = programItemArrayList.get(position);
        holder.btn_program.setText(programItem.programAdi);
        DBIdman dbIdman = new DBIdman(activity);
        Cursor isfullbodyCursor = dbIdman.getData("SELECT * FROM table" + programItem.pos);
        Cursor loadCursor = dbIdman.getDataFromProgramTable();

        ImageProcess imageProcess = new ImageProcess(activity);
        Drawable icon;
        if (isFullBody(isfullbodyCursor)) {
            icon = imageProcess.GetDrawableFromAssets("icon/fullbody.png", true);
        } else {
            icon = imageProcess.GetDrawableFromAssets("icon/split.png", true);
        }
        icon.setBounds(0, 0, 150, 150);
        holder.btn_program.setCompoundDrawables(icon, null, null, null);

        if (autoLoad)
            Load(loadCursor, programItem, holder);
        else
            holder.btn_program.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.btn_program.getTag().equals("0")) {
                        Load(loadCursor, programItem, holder);
                    } else UnLoad(holder);
                }
            });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String tableName = programItem.programAdi;
                int tableIndex = programItem.pos;

                DBIdman dbIdman = new DBIdman(activity);
                Cursor cursor = dbIdman.getDataFromProgramTable();

                cursor.moveToFirst();
                cursor.move(tableIndex);
                String[] gunler = cursor.getString(2).split("\n");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("Tablo", tableName);
                    JSONObject jsonObject2 = new JSONObject();
                    int i = 0;
                    for (String string : gunler
                    ) {
                        JSONArray jsonArray = new JSONArray();
                        Cursor cursor1 = dbIdman.getData("SELECT column" + i + " FROM table" + tableIndex);
                        while (cursor1.moveToNext()) {
                            if (cursor1.getString(0) != null)
                                jsonArray.put(cursor1.getString(0));
                        }
                        jsonObject2.put(string, jsonArray);
                        i++;
                    }
                    jsonObject.put("Günler", jsonObject2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String qrString = jsonObject.toString();
                Log.e("qr", "DEFAULT:(" + qrString.length() + ")" + qrString);

                //COMPRESS
                StringCompressor stringCompressor = new StringCompressor(activity);
                try {
                    qrString = stringCompressor.compressAndReturnB64(qrString);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("qr", "BASE64 COMPRESS ERROR!!!");
                }
                Log.e("qr", "COMPRESSED:(" + qrString.length() + ")" + qrString);

                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = null;
                try {
                    bitmap = barcodeEncoder.encodeBitmap(qrString, BarcodeFormat.QR_CODE, 400, 400);
                } catch (
                        WriterException e) {
                    e.printStackTrace();
                }

                QRDialog qrDialog = new QRDialog(activity);
                qrDialog.setCaption(tableName);
                qrDialog.setImage(bitmap);
                qrDialog.iv_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qrDialog.dissmiss();
                    }
                });
                qrDialog.show(false);
            }
        });
    }

    public void Load(Cursor cursor, ProgramItem programItem, ViewHolder holder) {
        holder.btn_program.setTag("1");

        ArrayList<GunItem> gunItemArrayList = new ArrayList<>();
        if (cursor.getCount() > programItem.pos) {
            cursor.moveToFirst();
            cursor.move(programItem.pos);
            String[] gunler = cursor.getString(2).split("\n");
            int i = 0;
            for (String gun : gunler
            ) {
                GunItem gunItem = new GunItem(gun, i, programItem.pos);
                gunItemArrayList.add(gunItem);
                i++;
            }
        }
        GunAdapter adapterMember = new GunAdapter(activity, gunItemArrayList, autoLoad);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        holder.rv_gunler.setLayoutManager(linearLayoutManager);
        holder.rv_gunler.setAdapter(adapterMember);

        if (programItem.imageId != 0) {
            Drawable img = ResourcesCompat.getDrawable(activity.getResources(), programItem.imageId, null);
            img.setBounds(0, 0, 100, 100);
            holder.btn_program.setCompoundDrawables(null, null, img, null);
        }

    }

    public void UnLoad(ViewHolder holder) {
        holder.rv_gunler.setAdapter(null);
        holder.btn_program.setTag("0");
    }

    public boolean isFullBody(Cursor cursor) {
        int[] columnFilled = new int[cursor.getColumnCount() - 1];
        for (int i = 0; i < cursor.getColumnCount() - 1; i++) {
            int columnValuesCount = 0;
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                if (cursor.getString(i + 1) != null) {
                    if (!cursor.getString(i + 1).equals("off"))
                        columnValuesCount++;
                }
            }
            if (columnValuesCount > 0)
                columnFilled[i] = columnValuesCount;
        }
        int filledColumnCount = 0;
        int emptyColumnCount = 0;
        for (int value : columnFilled
        ) {
            if (value > 0)
                filledColumnCount++;
            else emptyColumnCount++;
        }
        boolean isFullBodyProgram = false;
//        float ratio = (float) columnFilled.length / (float) filledColumnCount;
//        if (ratio > filledColumnCount )
//            Toast.makeText(activity, "This is a full body program", Toast.LENGTH_SHORT).show();
        if (emptyColumnCount >= 3)
            isFullBodyProgram = true;
        return isFullBodyProgram;
    }

    @Override
    public int getItemCount() {
        return programItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btn_program;
        RecyclerView rv_gunler;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_program = itemView.findViewById(R.id.btn_program);
            rv_gunler = itemView.findViewById(R.id.rv_gunler);
            imageView = itemView.findViewById(R.id.imageView13);
        }
    }

}
