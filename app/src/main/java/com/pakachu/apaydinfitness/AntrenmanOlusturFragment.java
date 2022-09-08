package com.pakachu.apaydinfitness;

import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.pakachu.apaydinfitness.adapters.idman_olustur.OnizleAdapter;
import com.pakachu.apaydinfitness.adapters.idman_olustur.OnizleItem;
import com.pakachu.apaydinfitness.customdialogs.MyCustomDialog;
import com.pakachu.apaydinfitness.databinding.FragmentAntrenmanOlusturBinding;
import com.pakachu.apaydinfitness.db.DBIdman;
import com.pakachu.apaydinfitness.helpers.DefaultHareket;
import com.pakachu.apaydinfitness.adapters.idman_olustur.ItemAdapter;
import com.pakachu.apaydinfitness.adapters.idman_olustur.ItemItem;
import com.pakachu.apaydinfitness.helpers.DefaultProgram;

import java.util.ArrayList;
import java.util.Locale;


public class AntrenmanOlusturFragment extends Fragment {

    public boolean inwork = false;

    private FragmentAntrenmanOlusturBinding binding;

    private DBIdman dbIdman;
    private ArrayList<ItemItem> gunItemArrayList;
    private ArrayList<ItemItem> hareketItemArrayList;
    private ArrayList<ItemItem> hareketDuzenle_TumHareketItemArrayList;
    private ArrayList<ItemItem> tumHareketlerItemArrayList;

    public CountDownTimer countDownTimer;
    public boolean timer = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAntrenmanOlusturBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void GunCikart(int pos) {
        gunItemArrayList.remove(pos);
        ItemAdapter myAdapter = new ItemAdapter(getActivity(), gunItemArrayList);
        myAdapter.antrenmanOlusturFragment = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.reciView.setLayoutManager(layoutManager);
        binding.reciView.setAdapter(myAdapter);
        binding.textView17.setText(myAdapter.getItemCount() + " Gün Mevcut");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbIdman = new DBIdman(getActivity());

        SetSpinner();
        TumHareketler_SetSpinner();
        InitSpinners();
        InitViewButtons();

        DefaultProgram defaultProgram=new DefaultProgram(getActivity());
        defaultProgram.CheckUP();

        DefaultHareket defaultHareket=new DefaultHareket(getActivity());
        defaultHareket.antrenmanOlusturFragment = this;
        defaultHareket.CheckUP();

        gunItemArrayList = new ArrayList<>();

        binding.btnProgramOlustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String programAdiString = binding.editTextTextPersonName4.getText().toString().trim();
                if (!programAdiString.equals("")) {
                    if (gunItemArrayList.size() > 0) {
                        String gunler = "";

                        for (ItemItem gunItem : gunItemArrayList
                        ) {
                            gunler += gunItem.text + "\n";

                        }
                        gunler = gunler.trim();
                        ArrayList<String> tableNamesArray = dbIdman.getTableNamesArray();
                        boolean tableHasCreatedBefore = false;
                        Cursor cursor = dbIdman.getDataFromProgramTable();
                        while (cursor.moveToNext()) {
                            if (programAdiString.equals(cursor.getString(1))) {
                                tableHasCreatedBefore = true;
                                break;
                            }
                        }
                        if (!tableHasCreatedBefore) {
                            dbIdman.addDataToProgramTable(programAdiString, gunler);
                            String newTableName = "table" + tableNamesArray.size();
                            String createSQL = "CREATE TABLE " + newTableName +
                                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," + "";
                            for (int i = 0; i < gunItemArrayList.size(); i++) {
                                createSQL += ("column" + i) + " TEXT,";
                            }
                            createSQL = createSQL.substring(0, createSQL.length() - 1);
                            createSQL += ")";

                            dbIdman.executeSQL(createSQL);

                            new MyCustomDialog(getActivity()).Toast(programAdiString + " programı başarıyla oluşturuldu");
                            binding.editTextTextPersonName4.setText(null);
                            binding.editTextTextPersonName5.setText(null);

                            gunItemArrayList.clear();
                            ItemAdapter myAdapter = new ItemAdapter(getActivity(), gunItemArrayList);
                            myAdapter.antrenmanOlusturFragment = AntrenmanOlusturFragment.this;
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            binding.reciView.setLayoutManager(layoutManager);
                            binding.reciView.setAdapter(myAdapter);
                            binding.textView17.setText("Günler Boş");
                            SetSpinner();
                        } else
                            new MyCustomDialog(getActivity()).Toast("Bu program adı zaten kullanılmış!");


                    } else
                        new MyCustomDialog(getActivity()).Toast("Programa en az bir gün eklenmiş olması gerekiyor!");

                } else
                    new MyCustomDialog(getActivity()).Toast("Program Adı boş olamaz!");
            }
        });


        binding.btnProgramGunEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gunString = binding.editTextTextPersonName5.getText().toString().trim();
                if (!gunString.equals("")) {
                    ItemItem programItem = new ItemItem(gunString, 0, 1, 0);
                    gunItemArrayList.add(programItem);
                    ItemAdapter myAdapter = new ItemAdapter(getActivity(), gunItemArrayList);
                    myAdapter.antrenmanOlusturFragment = AntrenmanOlusturFragment.this;
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    binding.reciView.setLayoutManager(layoutManager);
                    binding.reciView.setAdapter(myAdapter);
                    binding.textView17.setText(myAdapter.getItemCount() + " Gün Mevcut");
                    binding.editTextTextPersonName5.setText("");
                }

            }
        });

        binding.button31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hareketString = binding.editTextTextPersonName222.getText().toString().trim();
                String bolgesiString = binding.editTextTextPersonName6.getText().toString().trim();
                if (hareketString.equals("") | bolgesiString.equals("")) {
                    new MyCustomDialog(getActivity()).Toast("Hareket Adı veya Bölgesi boş olamaz!");
                } else {
                    DBIdman dbIdman = new DBIdman(getActivity());
                    dbIdman.addDataToHareketTable(hareketString, bolgesiString);
//                    binding.editTextTextPersonName6.setText("");
                    binding.editTextTextPersonName222.setText("");
                    Cursor cursor = dbIdman.getData("SELECT * FROM hareketler WHERE bolgesi='" + bolgesiString + "'");
                    if (cursor.getCount() > 1)
                        TumHareketler_SetListView(binding.spinner6.getSelectedItemPosition());
                    else
                        TumHareketler_SetSpinner();

                }
            }
        });

        binding.imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imageView8.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate));
                if (binding.spinner6.getCount() > 0) {
                    String info = "";
                    String bolge = binding.spinner6.getSelectedItem().toString();
                    info += bolge + " adlı bölge silinecektir.\n\nİçerisinde bulunan toplam " + getBolgeyeOzelHareketlerCount(bolge) + " adet hareket de silinecektir!";

                    MyCustomDialog myCustomDialog = new MyCustomDialog(getActivity());
                    myCustomDialog.setButtons("Sil", "İptal");
                    myCustomDialog.setContent(info);
                    myCustomDialog.positive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dbIdman.executeSQL("DELETE FROM hareketler WHERE bolgesi='" + bolge + "'");
                            new MyCustomDialog(getActivity()).Toast(bolge + " silindi.");
                            binding.reciview4.setAdapter(null);
                            binding.reciview3.setAdapter(null);
                            TumHareketler_SetSpinner();
                            myCustomDialog.dissmiss();
                        }
                    });
                    myCustomDialog.negative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myCustomDialog.dissmiss();
                        }
                    });
                    myCustomDialog.show(false);

                } else
                    new MyCustomDialog(getActivity()).Toast("Silinebilecek bölge mevcut değil!");

            }
        });


        binding.imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.imageView7.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate));
                if (binding.spinner5.getCount() > 0) {
                    String info = "";
                    int tableIndex = binding.spinner5.getSelectedItemPosition();
                    String tableName = binding.spinner5.getItemAtPosition(tableIndex).toString();
                    info += tableName + " adlı program silinecektir!\nEmin misiniz?";

                    MyCustomDialog myCustomDialog = new MyCustomDialog(getActivity());
                    myCustomDialog.setButtons("Sil", "İptal");
                    myCustomDialog.setContent(info);
                    myCustomDialog.positive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dbIdman.executeSQL("DELETE FROM programlarim WHERE ad='" + tableName + "'");
                            dbIdman.executeSQL("DROP TABLE table" + tableIndex);
                            ArrayList<String> arrayList = dbIdman.getTableNamesArray();
                            for (int i = 0; i < arrayList.size(); i++) {
                                String tableName = arrayList.get(i);
                                String newTableName = "table" + i;
                                if (!tableName.equals(newTableName))
                                    dbIdman.executeSQL("ALTER TABLE " + tableName + " RENAME TO " + newTableName);
                            }
                            SetSpinner();
                            new MyCustomDialog(getActivity()).Toast(tableName + " başarıyla silindi");
                            myCustomDialog.dissmiss();
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

            }
        });
    }

    private void Onizle(int pos, boolean reset) {
        Log.e("looptest", "true");
        ArrayList<OnizleItem> onizleItemArrayList = new ArrayList<>();
        Cursor cursor = dbIdman.getDataFromProgramTable();
        if (cursor.getCount() > pos) {
            binding.textView36.setText(binding.spinner5.getItemAtPosition(pos).toString());
            cursor.moveToFirst();
            cursor.move(pos);

            String programAdi = cursor.getString(1);
            binding.textView23.setText(programAdi);

            String gunler[] = cursor.getString(2).split("\n");
            int index = 1;
            for (String gun : gunler
            ) {
                OnizleItem onizleItem = new OnizleItem(gun, pos, index);
                onizleItemArrayList.add(onizleItem);
                index++;
            }
        }

        OnizleAdapter myAdapter = new OnizleAdapter(getActivity(), onizleItemArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyview.setLayoutManager(layoutManager);
        binding.recyview.setAdapter(myAdapter);
        if (reset)
            HareketDuzenle_SetSpinner(pos);
    }

    private void SetSpinner() {
        Cursor cursor = dbIdman.getDataFromProgramTable();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        while (cursor.moveToNext()) {
            String programAdi = cursor.getString(1);
            spinnerAdapter.add(programAdi);
        }
        binding.spinner5.setAdapter(spinnerAdapter);
        String text = "";
        if (spinnerAdapter.getCount() > 0) {
            binding.textView16.setText(spinnerAdapter.getCount() + " adet program mevcut");

        } else {
            binding.textView16.setText("Herhangi bir program mevcut değil! Lütfen yeni program oluşturun");
            binding.recyview.setAdapter(null);
            binding.textView23.setText("Program Mevcut Değil");
            binding.spinner7.setAdapter(new ArrayAdapter<String>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item));
            binding.textView37.setText("");
            binding.reciview2.setAdapter(null);

        }
        binding.textView36.setText("");
    }

    private void HareketDuzenle_SetSpinner(int pos) {
        Cursor cursor = dbIdman.getData("SELECT * FROM programlarim");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        cursor.moveToFirst();
        cursor.move(pos);
        String[] gunler = cursor.getString(2).split("\n");
        for (String gun : gunler
        ) {
            spinnerAdapter.add(gun);
        }
        binding.spinner7.setAdapter(spinnerAdapter);
        binding.textView37.setText("");

    }

    private void HareketDuzenle_SetListView(int pos) {
        int tabloNo = binding.spinner5.getSelectedItemPosition();
        Cursor cursor = dbIdman.getData("SELECT * FROM table" + tabloNo);
        hareketItemArrayList = new ArrayList<>();
        int index = 0;
        int mainIndex = 1;

        while (cursor.moveToNext()) {
            dbIdman.executeSQL("UPDATE table" + tabloNo + " SET id=" + mainIndex + " WHERE id=" + cursor.getInt(0));
            if (cursor.getString(pos + 1) != null) {
                String string = cursor.getString(pos + 1);
                ItemItem itemItem = new ItemItem(string, 1, index, mainIndex);
                hareketItemArrayList.add(itemItem);
                index++;
            }
            mainIndex++;
        }

        ItemAdapter myAdapter = new ItemAdapter(getActivity(), hareketItemArrayList);
        myAdapter.antrenmanOlusturFragment = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.reciview2.setLayoutManager(layoutManager);
        binding.reciview2.setAdapter(myAdapter);
        binding.textView37.setText(binding.spinner7.getItemAtPosition(pos).toString());
        Onizle(binding.spinner5.getSelectedItemPosition(), false);
    }

    public void HareketEkle(int pos) {
        String hareketString = hareketDuzenle_TumHareketItemArrayList.get(pos).text;
        String birlesikString = hareketString;
        String detayString = binding.editTextTextPersonName9.getText().toString().trim();
        if (!detayString.equals(""))
            birlesikString += "\n" + detayString;
        if (binding.spinner7.getCount() > 0) {
            int tabloNo = binding.spinner5.getSelectedItemPosition();
            int gunNo = binding.spinner7.getSelectedItemPosition();
            dbIdman.executeSQL("INSERT INTO table" + tabloNo + " (column" + gunNo + ") VALUES ('" + birlesikString + "')");
            HareketDuzenle_SetListView(binding.spinner7.getSelectedItemPosition());
            Onizle(binding.spinner5.getSelectedItemPosition(), false);
        } else
            new MyCustomDialog(getActivity()).Toast("Hareket aktarmak için gün seçmeniz gerekiyor!");

    }

    public void HareketCikart(int pos) {
        String secilenHareket = hareketItemArrayList.get(pos).text;
        hareketItemArrayList.remove(pos);
        int tabloNo = binding.spinner5.getSelectedItemPosition();
        int columnNo = binding.spinner7.getSelectedItemPosition();
        dbIdman.executeSQL("DELETE FROM table" + tabloNo + " WHERE column" + columnNo + "='" + secilenHareket + "';");
        HareketDuzenle_SetListView(binding.spinner7.getSelectedItemPosition());
        inwork = false;
    }

    public void HareketUP(int pos) {
        ItemItem ustItem = new ItemItem(hareketItemArrayList.get(pos - 1).text, hareketItemArrayList.get(pos - 1).switchInt, hareketItemArrayList.get(pos - 1).index, hareketItemArrayList.get(pos - 1).mainIndex);
        ItemItem buItem = new ItemItem(hareketItemArrayList.get(pos).text, hareketItemArrayList.get(pos).switchInt, hareketItemArrayList.get(pos).index, hareketItemArrayList.get(pos).mainIndex);

        int tabloNo = binding.spinner5.getSelectedItemPosition();
        int columnNo = binding.spinner7.getSelectedItemPosition();

        String secilenHareket = buItem.text;
        int secilenindex = buItem.mainIndex;

        String biUstHareket = ustItem.text;
        int biUstIndex = ustItem.mainIndex;

        dbIdman.executeSQL("UPDATE table" + tabloNo + " SET column" + columnNo + "='" + secilenHareket + "' WHERE id=" + biUstIndex);
        dbIdman.executeSQL("UPDATE table" + tabloNo + " SET column" + columnNo + "='" + biUstHareket + "' WHERE id=" + secilenindex);
        HareketDuzenle_SetListView(binding.spinner7.getSelectedItemPosition());
        inwork = false;
    }

    public void HareketDOWN(int pos) {
        ItemItem altItem = new ItemItem(hareketItemArrayList.get(pos + 1).text, hareketItemArrayList.get(pos + 1).switchInt, hareketItemArrayList.get(pos + 1).index, hareketItemArrayList.get(pos + 1).mainIndex);
        ItemItem buItem = new ItemItem(hareketItemArrayList.get(pos).text, hareketItemArrayList.get(pos).switchInt, hareketItemArrayList.get(pos).index, hareketItemArrayList.get(pos).mainIndex);

        int tabloNo = binding.spinner5.getSelectedItemPosition();
        int columnNo = binding.spinner7.getSelectedItemPosition();

        String secilenHareket = buItem.text;
        int secilenindex = buItem.mainIndex;

        String biAltHareket = altItem.text;
        int biAltIndex = altItem.mainIndex;

        dbIdman.executeSQL("UPDATE table" + tabloNo + " SET column" + columnNo + "='" + secilenHareket + "' WHERE id=" + biAltIndex);
        dbIdman.executeSQL("UPDATE table" + tabloNo + " SET column" + columnNo + "='" + biAltHareket + "' WHERE id=" + secilenindex);
        HareketDuzenle_SetListView(binding.spinner7.getSelectedItemPosition());
        inwork = false;
    }

    public void HareketDuzenle_SetTumHareketlerSpinner() {
        Cursor cursor = dbIdman.getData("SELECT DISTINCT bolgesi FROM hareketler");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        while (cursor.moveToNext()) {
            String bolge = cursor.getString(0);
            spinnerAdapter.add(bolge);
        }
        binding.spinner8.setAdapter(spinnerAdapter); //SPINNER 8 AUTO
        binding.textView38.setText("");
    }

    private void HareketDuzenle_SetTumHareketlerListView(int pos) {
        hareketDuzenle_TumHareketItemArrayList = new ArrayList<>();
        String bolgesi = binding.spinner8.getItemAtPosition(pos).toString();
        binding.textView38.setText(bolgesi);
        Cursor cursor = dbIdman.getData("SELECT hareket FROM hareketler WHERE bolgesi='" + bolgesi + "'");
        while (cursor.moveToNext()) {
            String hareket = cursor.getString(0);
            ItemItem itemItem = new ItemItem(hareket, 2, 1, cursor.getPosition());
            hareketDuzenle_TumHareketItemArrayList.add(itemItem);
        }
        ItemAdapter myAdapter = new ItemAdapter(getActivity(), hareketDuzenle_TumHareketItemArrayList);
        myAdapter.antrenmanOlusturFragment = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.reciview3.setLayoutManager(layoutManager);
        binding.reciview3.setAdapter(myAdapter);
    }

    private int getBolgeyeOzelHareketlerCount(String bolge) {
        return dbIdman.getData("SELECT * FROM hareketler WHERE bolgesi='" + bolge + "'").getCount();
    }

    private void TumHareketler_SetSpinner() {
        Cursor cursor = dbIdman.getData("SELECT DISTINCT bolgesi FROM hareketler");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        while (cursor.moveToNext()) {
            String bolge = cursor.getString(0);
            spinnerAdapter.add(bolge);
        }
        binding.spinner6.setAdapter(spinnerAdapter);
        binding.editTextTextPersonName6.setText("");
        binding.textView40.setText("");
        HareketDuzenle_SetTumHareketlerSpinner();
    }

    private void TumHareketler_SetListView(int pos) {
        tumHareketlerItemArrayList = new ArrayList<>();
        String bolgesi = binding.spinner6.getItemAtPosition(pos).toString();
        binding.textView40.setText(bolgesi);
        binding.editTextTextPersonName6.setText(bolgesi);
        Cursor cursor = dbIdman.getData("SELECT hareket FROM hareketler WHERE bolgesi='" + bolgesi + "'");
        while (cursor.moveToNext()) {
            String hareket = cursor.getString(0);
            ItemItem itemItem = new ItemItem(hareket, 3, 1, cursor.getPosition());
            tumHareketlerItemArrayList.add(itemItem);
        }
        ItemAdapter myAdapter = new ItemAdapter(getActivity(), tumHareketlerItemArrayList);
        myAdapter.antrenmanOlusturFragment = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.reciview4.setLayoutManager(layoutManager);
        binding.reciview4.setAdapter(myAdapter);
        HareketDuzenle_SetTumHareketlerListView(binding.spinner8.getSelectedItemPosition());
        binding.textView22.setText(cursor.getCount()+" Adet Toplam Hareket Mevcut");
    }

    public void TumHareketler_HareketSil(int pos) {
        String selectedHareketString = tumHareketlerItemArrayList.get(pos).text;
        String selectedBolgeString = binding.spinner6.getSelectedItem().toString();

        MyCustomDialog myCustomDialog = new MyCustomDialog(getActivity());
        myCustomDialog.setButtons("Sil", "İptal");
        myCustomDialog.setContent(selectedHareketString.toUpperCase(Locale.ROOT) + " kalıcı olarak silinecektir!\nOnaylıyor musunuz?");
        myCustomDialog.positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbIdman.executeSQL("DELETE FROM hareketler WHERE hareket='" + selectedHareketString + "' AND bolgesi='" + selectedBolgeString + "'");
                TumHareketler_SetListView(binding.spinner6.getSelectedItemPosition());
                HareketDuzenle_SetTumHareketlerListView(binding.spinner8.getSelectedItemPosition());
                binding.textView22.setText(dbIdman.getDataFromHareketTable().getCount() + " Adet Toplam Hareket Mevcut");
                myCustomDialog.dissmiss();
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

    private void InitSpinners() {
        binding.spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Onizle(position, true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.spinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HareketDuzenle_SetListView(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TumHareketler_SetListView(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        binding.spinner8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HareketDuzenle_SetTumHareketlerListView(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void InitViewButtons() {
        binding.button21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.cardView34.getVisibility() == View.VISIBLE) {
                    binding.cardView34.clearAnimation();
                    binding.cardView34.setVisibility(View.GONE);
                    binding.button21.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255, 255, 235, 59)));

                } else {
                    binding.cardView34.setVisibility(View.VISIBLE);
                    binding.button21.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255, 255, 249, 197)));
                    binding.cardView34.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.bounce));

                }
            }
        });

        binding.button22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.cardView33.getVisibility() == View.VISIBLE) {
                    binding.cardView33.clearAnimation();
                    binding.cardView33.setVisibility(View.GONE);
                    binding.button22.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255, 255, 235, 59)));

                } else {
                    binding.cardView33.setVisibility(View.VISIBLE);
                    binding.button22.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255, 255, 249, 197)));
                    binding.cardView33.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.bounce));

                }
            }
        });

        binding.button23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.cardView32.getVisibility() == View.VISIBLE) {
                    binding.cardView32.clearAnimation();
                    binding.cardView32.setVisibility(View.GONE);
                    binding.button23.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255, 255, 235, 59)));
                } else {
                    binding.cardView32.setVisibility(View.VISIBLE);
                    binding.button23.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255, 255, 249, 197)));
                    binding.cardView32.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.bounce));

                }
            }
        });

        binding.button36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.button22.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255, 255, 235, 59)));
                binding.cardView33.clearAnimation();
                binding.cardView33.setVisibility(View.GONE);
            }
        });
        binding.button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.button21.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255, 255, 235, 59)));
                binding.cardView34.clearAnimation();
                binding.cardView34.setVisibility(View.GONE);
            }
        });

        binding.button29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.button23.setBackgroundTintList(ColorStateList.valueOf(Color.argb(255, 255, 235, 59)));
                binding.cardView32.clearAnimation();
                binding.cardView32.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

        if (timer)
            countDownTimer.cancel();
    }
}