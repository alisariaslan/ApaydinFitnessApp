package com.pakachu.apaydinfitness;

import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.pakachu.apaydinfitness.adapters.ProgramAdapter;
import com.pakachu.apaydinfitness.adapters.ProgramItem;
import com.pakachu.apaydinfitness.databinding.FragmentAntrenmanBinding;
import com.pakachu.apaydinfitness.databinding.FragmentMainBinding;
import com.pakachu.apaydinfitness.db.DBIdman;
import com.pakachu.apaydinfitness.helpers.AddLoader;
import com.pakachu.apaydinfitness.helpers.DefaultProgram;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class AntrenmanFragment extends Fragment {

    private FragmentAntrenmanBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAntrenmanBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    private void QRileOlustur(String resultString) {
        JSONObject resultObject = null;
        try {
            resultObject = new JSONObject(resultString);
            String tabloAdi = null;
            JSONObject gunlerObject = new JSONObject();
            Iterator<String> iter = resultObject.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                if (key.equals("Tablo")) {
                    tabloAdi = (String) resultObject.get(key);
                }
                if (key.equals("Günler")) {
                    gunlerObject = (JSONObject) resultObject.get(key);
                }
            }
            DBIdman dbIdman = new DBIdman(getActivity());
            ArrayList<String> tableNamesArray = dbIdman.getTableNamesArray();
            String newTableName = "table" + tableNamesArray.size();
            ArrayList<String> insertArrayList = new ArrayList<>();
            String gunler = "";
            int size = 0;
            Iterator<String> iter2 = gunlerObject.keys();
            while (iter2.hasNext()) {
                String key = iter2.next();
                JSONArray jsonArray = (JSONArray) gunlerObject.get(key);
                gunler += key + "\n";
                for (int i = 0; i < jsonArray.length(); i++) {
                    String insertString = "INSERT INTO " + newTableName + " (column" + size + ") VALUES ('" + jsonArray.getString(i) + "')";
                    insertArrayList.add(insertString);
                }
                size++;
            }
            gunler = gunler.trim();
            boolean tableHasCreatedBefore = false;
            Cursor cursor = dbIdman.getDataFromProgramTable();
            while (cursor.moveToNext()) {
                if (tabloAdi.equals(cursor.getString(1))) {
                    tableHasCreatedBefore = true;
                    break;
                }
            }
            if (tableHasCreatedBefore)
                tabloAdi += " yeni";
            dbIdman.addDataToProgramTable(tabloAdi + "", gunler);
            String createSQL = "CREATE TABLE " + newTableName +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "";
            for (int i = 0; i < size; i++) {
                createSQL += ("column" + i) + " TEXT,";
            }
            createSQL = createSQL.substring(0, createSQL.length() - 1);
            createSQL += ")";
            dbIdman.executeSQL(createSQL);
            for (String insertSQL : insertArrayList
            ) {
                dbIdman.executeSQL(insertSQL);
            }
            new MyCustomDialog(getActivity()).Toast(tabloAdi + " programı başarıyla oluşturuldu");
        } catch (JSONException e) {
            e.printStackTrace();
            new MyCustomDialog(getActivity()).Toast("JSON hata!");
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(AntrenmanFragment.this).navigate(R.id.antrenmanOlusturFragment);
            }
        });

        binding.button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCustomDialog myCustomDialog = new MyCustomDialog(getActivity());
                myCustomDialog.setCaption("Seçiniz");
                myCustomDialog.setContent("Lütfen seçim yapın");
                myCustomDialog.setButtons("QR ile Kendime Aktar", "QR ile Başkasına Aktar");
                myCustomDialog.positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myCustomDialog.dissmiss();
                        ScanOptions scanOptions = new ScanOptions();
                        scanOptions.setTimeout(15000);
                        scanOptions.setBeepEnabled(false);
                        scanOptions.setPrompt("LÜTFEN TARAYICIYI SİZE GÖSTERİLEN QR KODA DOĞRU TUTUN");
                        barcodeLauncher.launch(scanOptions);
                    }
                });
                myCustomDialog.negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myCustomDialog.dissmiss();
                        NavHostFragment.findNavController(AntrenmanFragment.this).navigate(R.id.antrenmanAktarFragment);
                    }
                });
                myCustomDialog.show(false);
            }
        });

        binding.cardlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.cardlike.setEnabled(false);
                binding.imageView13.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.zoomin));
                binding.textView50.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.toleft));
                countDownTimer.start();
            }
        });
    }

    private boolean timer;
    private final CountDownTimer countDownTimer=new CountDownTimer(3000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timer=true;
        }

        @Override
        public void onFinish() {
            NavHostFragment.findNavController(AntrenmanFragment.this).navigate(R.id.action_antrenmanFragment_to_antrenmanTamEkran);
            timer=false;
        }
    };

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    new MyCustomDialog(getActivity()).Toast("İptal Edildi");
                } else {
                    String resultString = result.getContents();
                    QRileOlustur(resultString);
                }
            });

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if(timer)
            countDownTimer.cancel();
    }
}