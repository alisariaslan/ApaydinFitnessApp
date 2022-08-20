package com.pakachu.apaydinfitness;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.pakachu.apaydinfitness.databinding.FragmentAntrenmanAktarBinding;
import com.pakachu.apaydinfitness.db.DBIdman;
import com.pakachu.apaydinfitness.adapters.ItemAdapter;
import com.pakachu.apaydinfitness.adapters.ItemItem;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;


public class AntrenmanAktarFragment extends Fragment {

    private FragmentAntrenmanAktarBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAntrenmanAktarBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AntrenmanlarLoad();
    }

    public void AntrenmanlarLoad() {
        ArrayList<ItemItem> itemItemArrayList = new ArrayList<>();
        DBIdman dbIdman = new DBIdman(getActivity());
        Cursor cursor = dbIdman.getDataFromProgramTable();
        while (cursor.moveToNext()) {
            String programAdi = cursor.getString(1);
            ItemItem itemItem = new ItemItem(programAdi, 4,1,cursor.getPosition());
            itemItemArrayList.add(itemItem);
        }
        ItemAdapter myAdapter = new ItemAdapter(getActivity(), itemItemArrayList);
        myAdapter.antrenmanAktarFragment = this;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyview.setLayoutManager(layoutManager);
        binding.recyview.setAdapter(myAdapter);

    }

    public void Aktar(String tableName, int tableIndex) {

        DBIdman dbIdman = new DBIdman(getContext());
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

        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = null;
        try {
            bitmap = barcodeEncoder.encodeBitmap(qrString, BarcodeFormat.QR_CODE, 400, 400);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        binding.imageView9.setImageBitmap(bitmap);
        Bitmap finalBitmap = bitmap;
        binding.imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                saveImage(finalBitmap,""+tableName+"img");
//                saveTempBitmap(finalBitmap,tableName);
            }
        });
        binding.imageView9.setVisibility(View.VISIBLE);
        binding.imageView9.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.lefttoright));
        binding.textView4.setText(tableName.toUpperCase(Locale.ROOT));

//        new MyCustomDialog(getActivity()).Toast(qrString);
//        Log.e("jsontest",qrString);
    }


//    private void saveImage(Bitmap finalBitmap, String image_name) {
//        String root = Environment.getExternalStorageDirectory().toString();
//        File myDir = new File(root);
//        myDir.mkdirs();
//        String fname = "Image-" + image_name+ ".jpg";
//        File file = new File(myDir, fname);
//        if (file.exists()) file.delete();
//        Log.i("LOAD", root + fname);
//        try {
//            FileOutputStream out = new FileOutputStream(file);
//            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//            out.flush();
//            out.close();
//            new MyCustomDialog(getActivity()).Toast("QR Kod Galeriye Resim Şeklinde Kayıt Edildi");
//        } catch (Exception e) {
//            e.printStackTrace();
//            new MyCustomDialog(getActivity()).Toast("Galeriye Kayıt Esnasında Hata Oluştu!");
//            Log.e("output",""+e);
//        }
//    }

//    public void saveTempBitmap(Bitmap bitmap,String tablename) {
//        if (isExternalStorageWritable()) {
//            saveImage(bitmap,tablename);
//        }else{
//            //prompt the user or do something
//            new MyCustomDialog(getActivity()).Toast("Bu depolama alanına kayıt yapılamıyor!");
//        }
//    }

//    private void saveImage(Bitmap finalBitmap,String tablename) {
//
//        String root = Environment.getExternalStorageDirectory().toString();
//        File myDir = new File(root + "/apaydinfitness");
//        myDir.mkdirs();
//
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String fname = tablename+ timeStamp +".jpg";
//
//        File file = new File(myDir, fname);
//        if (file.exists()) file.delete ();
//        try {
//            FileOutputStream out = new FileOutputStream(file);
//            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            out.flush();
//            out.close();
//            new MyCustomDialog(getActivity()).Toast("QR Kod Sistem Kök Dizinine Resim Şeklinde Kayıt Edildi\n"+root + "/apaydinfitness/"+tablename+".jpg");
//        } catch (Exception e) {
//            e.printStackTrace();
//            new MyCustomDialog(getActivity()).Toast("Sistem Kök Dizinine Kayıt Esnasında Hata Oluştu! Lütfen uygulamanın depolama izinlerini kontrol edin.");
//            Log.e("bitmap",""+e);
//        }
//    }
//
//    /* Checks if external storage is available for read and write */
//    public boolean isExternalStorageWritable() {
//        String state = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            return true;
//        }
//        return false;
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}