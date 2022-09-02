package com.pakachu.apaydinfitness;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pakachu.apaydinfitness.adapters.FoodAdapter;
import com.pakachu.apaydinfitness.adapters.FoodItem;
import com.pakachu.apaydinfitness.adapters.OnizleAdapter;
import com.pakachu.apaydinfitness.adapters.OnizleItem;
import com.pakachu.apaydinfitness.databinding.FragmentDietBinding;
import com.pakachu.apaydinfitness.db.DBDiet;
import com.pakachu.apaydinfitness.db.DBLocal;
import com.pakachu.apaydinfitness.helpers.AddLoader;

import java.util.ArrayList;
import java.util.Locale;


public class DietFragment extends Fragment {

    private FragmentDietBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDietBinding.inflate(inflater, container, false);

        AddLoader addLoader = new AddLoader(getActivity());
        addLoader.RequestInterstatial();

        CalculateReqDailyMacros();
        ListDailyFoods();
        ListFoods();

        binding.radioButton9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String value = binding.editTextTextPersonName31.getText().toString();
                if (isChecked) {
                    binding.textView68.setText("Kaç mililitre?");
                    binding.editTextTextPersonName31.setHint("mililitre");
                    binding.textView69.setText("ml");
                    if (value.equals(""))
                        value = "?";
                    binding.textView71.setText(value + " mililitresinde:");
                } else {
                    binding.textView68.setText("Kaç gram?");
                    binding.editTextTextPersonName31.setHint("gram");
                    binding.textView69.setText("gr");
                    binding.textView71.setText(value + " gramında:");
                }
            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag().equals("0")) {
                    v.setTag("1");
                    binding.button3.setText("Şimdi oluştur");
                    binding.cardView8.setVisibility(View.VISIBLE);
                    binding.cardView8.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce));
                } else {
                    String food_name = "";
                    food_name += binding.editTextTextPersonName33.getText().toString().trim();
                    if (!food_name.equals("")) {
                        boolean isLiquid = false;
                        isLiquid = binding.radioButton9.isChecked();
                        int grams = 0;
                        if (!binding.editTextTextPersonName31.getText().toString().equals(""))
                            grams = Integer.parseInt(binding.editTextTextPersonName31.getText().toString());
                        if (grams > 0) {
                            float protein = 0f;
                            if (!binding.editTextTextPersonName32.getText().toString().equals(""))
                                protein = Float.parseFloat(binding.editTextTextPersonName32.getText().toString());
                            if (protein > 0f) {
                                float carb = 0f;
                                if (!binding.editTextTextPersonName34.getText().toString().equals(""))
                                    carb = Float.parseFloat(binding.editTextTextPersonName34.getText().toString());
                                if (carb > 0f) {
                                    float fat = 0f;
                                    if (!binding.editTextTextPersonName35.getText().toString().equals(""))
                                        fat = Float.parseFloat(binding.editTextTextPersonName35.getText().toString());
                                    if (fat > 0f) {
                                        float toplamKcal = protein * 4 + carb * 4 + fat * 9;
                                        DBDiet dbDiet = new DBDiet(getContext());
                                        Cursor cursor = dbDiet.getDataWithSQL("SELECT food_name FROM food_list WHERE food_name='" + food_name + "'");
                                        if (cursor.getCount() == 0) {
                                            dbDiet.addDataToFoodList(food_name, isLiquid, grams, protein, carb, fat);
                                            v.setTag("0");
                                            binding.button3.setText("Yeni yemek oluştur");
                                            binding.cardView8.clearAnimation();
                                            binding.cardView8.setVisibility(View.GONE);
                                            binding.editTextTextPersonName33.setText("");
                                            binding.editTextTextPersonName31.setText("");
                                            binding.editTextTextPersonName32.setText("");
                                            binding.editTextTextPersonName34.setText("");
                                            binding.editTextTextPersonName35.setText("");
                                            binding.textView80.setText("?");
                                            ListFoods();
                                            new MyCustomDialog(getActivity()).Toast(food_name + " adlı yemek başarıyla eklendi.");
                                        } else
                                            new MyCustomDialog(getActivity()).Toast("Zaten aynı isimde yemek mevcut. Lütfen yemeğin adını değiştirin!");
                                    } else
                                        new MyCustomDialog(getActivity()).Toast("Yağ boş bırakılamaz ve sıfırdan büyük olmalıdır!");
                                } else
                                    new MyCustomDialog(getActivity()).Toast("Karbonhidrat boş bırakılamaz ve sıfırdan büyük olmalıdır!");
                            } else
                                new MyCustomDialog(getActivity()).Toast("Protein boş bırakılamaz ve sıfırdan büyük olmalıdır!");
                        } else
                            new MyCustomDialog(getActivity()).Toast("Gramaj boş bırakılamaz ve sıfırdan büyük olmalıdır!");
                    } else new MyCustomDialog(getActivity()).Toast("Yemek adı boş bırakılamaz!");
                }
            }
        });

        binding.editTextTextPersonName31.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String birim = binding.radioButton9.isChecked() ? "mililitresinde" : "gramında";
                if (!s.toString().equals("")) {
                    binding.textView71.setText(s + " " + birim + ":");
                } else binding.textView71.setText("? " + birim + ":");
            }
        });

        binding.editTextTextPersonName32.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    CalculateNewMealCalorie();
                }
            }
        });

        binding.editTextTextPersonName34.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    CalculateNewMealCalorie();
                }
            }
        });

        binding.editTextTextPersonName35.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    CalculateNewMealCalorie();
                }
            }
        });

        binding.radioButton8.setChecked(true);

        return binding.getRoot();
    }

    public void ListDailyFoods() {
        float protein_taken, carb_taken, fat_taken, calories_taken;
        protein_taken = carb_taken = fat_taken = calories_taken = 0f;
        DBDiet dbDiet = new DBDiet(getContext());
        ArrayList<FoodItem> foodItems = new ArrayList<>();
        Cursor cursor = dbDiet.getDataFromDailyList();
        while (cursor.moveToNext()) {
            int daily_list_id = cursor.getInt(0);
            int daily_list_index = cursor.getInt(1);
            int daily_list_gram = cursor.getInt(2);
            Cursor cursor1 = dbDiet.getFoodInfo(daily_list_index);
            cursor1.moveToFirst();
            boolean is_liquid = cursor1.getInt(2) == 1;
            int food_list_gram = cursor1.getInt(3);
            float food_list_protein = cursor1.getFloat(4);
            float food_list_carb = cursor1.getFloat(5);
            float food_list_fat = cursor1.getFloat(6);
            float protein_takenn = daily_list_gram * food_list_protein / food_list_gram;
            float carb_takenn = daily_list_gram * food_list_carb / food_list_gram;
            float fat_takenn = daily_list_gram * food_list_fat / food_list_gram;
            calories_taken += protein_takenn * 4 + carb_takenn * 4 + fat_takenn * 9;

            protein_taken += protein_takenn;
            carb_taken += carb_takenn;
            fat_taken += fat_takenn;


            FoodItem foodItem = new FoodItem(cursor1.getString(1), daily_list_id, 1, is_liquid, daily_list_gram, protein_takenn, carb_takenn, fat_takenn);
            foodItems.add(foodItem);
        }
        binding.progressBarProtein.setProgress((int) protein_taken);
        binding.progressBarKarb.setProgress((int) carb_taken);
        binding.progressBarYag.setProgress((int) fat_taken);
        binding.tvKal.setText("Alınan kalori: " + (int) calories_taken + " kcal");
        binding.tvPro.setText("Protein: " + (int) protein_taken + " gr");
        binding.tvKarb.setText("Karbonhidrat: " + (int) carb_taken + " gr");
        binding.tvYag.setText("Yağ: " + (int) fat_taken + " gr");

        FoodAdapter myAdapter = new FoodAdapter(getActivity(), foodItems, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rwFood.setLayoutManager(layoutManager);
        binding.rwFood.setAdapter(myAdapter);
        if (cursor.getCount() > 0) {
            binding.textView72.setVisibility(View.GONE);
            binding.button25.setVisibility(View.VISIBLE);
        } else {
            binding.textView72.setVisibility(View.VISIBLE);
            binding.textView72.setText("Liste boş");
            binding.button25.setVisibility(View.GONE);
        }

    }

    public void ListFoods() {
        DBDiet dbDiet = new DBDiet(getContext());
        ArrayList<FoodItem> foodItems = new ArrayList<>();
        Cursor cursor = dbDiet.getDataFromFoodList();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String food_name = cursor.getString(1);
            boolean is_liquid = cursor.getInt(2) == 1;
            int gram = cursor.getInt(3);
            float protein = cursor.getFloat(4);
            float carb = cursor.getFloat(5);
            float fat = cursor.getFloat(6);
            FoodItem foodItem = new FoodItem(food_name, id, 0, is_liquid, gram, protein, carb, fat);
            foodItems.add(foodItem);
        }
        FoodAdapter myAdapter = new FoodAdapter(getActivity(), foodItems, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rwAllFoods.setLayoutManager(layoutManager);
        binding.rwAllFoods.setAdapter(myAdapter);
        int food_list_count = dbDiet.getFoodListCount();
        if (food_list_count != 0)
            binding.textView81.setText("Toplam " + food_list_count + " yemek mevcut." + "\n" + cursor.getCount() + " yemek listeleniyor");
        else binding.textView81.setText("Hiç yemek mevcut değil.\nLütfen yeni yemek oluşturun.");
    }

    private void CalculateNewMealCalorie() {
        String proteinString, carbString, fatString;
        proteinString = binding.editTextTextPersonName32.getText().toString();
        carbString = binding.editTextTextPersonName34.getText().toString();
        fatString = binding.editTextTextPersonName35.getText().toString();
        if (!proteinString.equals(""))
            if (!carbString.equals(""))
                if (!fatString.equals("")) {
                    float protein = Float.parseFloat(proteinString);
                    float carb = Float.parseFloat(carbString);
                    float fat = Float.parseFloat(fatString);
                    float total_kcal = protein * 4 + carb * 4 + fat * 9;
                    binding.textView80.setText("(" + total_kcal + "kcal)");
                }
    }

    private int daily_kcal_need;

    private void CalculateReqDailyMacros() {
        DBLocal dbLocal = new DBLocal(getContext());
        Cursor cursor = dbLocal.getData();
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            boolean ismale = false;
            String sex = "";
            sex = cursor.getString(4);
            if (sex.toUpperCase(Locale.ROOT).equals("ERKEK"))
                ismale = true;
            else ismale = false;
            float yas, boy, kilo;
            yas = boy = kilo = 0f;
            yas = cursor.getFloat(3);
            boy = cursor.getFloat(6);
            kilo = cursor.getFloat(5);
            if (yas != 0f) {
                if (boy != 0f) {
                    if (kilo != 0f) {
                        //Harris-Benedict Equation
                        float kcal_need = 0f;
                        if (ismale)
                            kcal_need = 66.47f + (13.75f * kilo) + (5f * boy) - (6.75f * yas);
                        else
                            kcal_need = 655.09f + (9.56f * kilo) + (1.84f * boy) - (4.67f * yas);
                        float protein_need = kcal_need / 100f * 25f / 4f;
                        float carb_need = kcal_need / 100f * 50f / 4f;
                        float fat_need = kcal_need / 100f * 25f / 9f;
                        binding.progressBarProtein.setMax((int) protein_need);
                        binding.progressBarKarb.setMax((int) carb_need);
                        binding.progressBarYag.setMax((int) fat_need);
                        daily_kcal_need = (int) kcal_need;
//                        new MyCustomDialog(getActivity()).Toast(kcal_need + "\n" + protein_need + "\n" + carb_need + "\n" + fat_need);
                    } else
                        new MyCustomDialog(getActivity()).Toast("Hata! En son tarihli ölçünüzde bulunan kilo bilgisi eksik olduğu için makrolarınız hesaplanamıyor");
                } else
                    new MyCustomDialog(getActivity()).Toast("Hata! En son tarihli ölçünüzde bulunan boy bilgisi eksik olduğu için makrolarınız hesaplanamıyor");
            } else
                new MyCustomDialog(getActivity()).Toast("Hata! En son tarihli ölçünüzde bulunan yaş bilgisi eksik olduğu için makrolarınız hesaplanamıyor");

        } else
            new MyCustomDialog(getActivity()).Toast("Vücudunuza ait herhangi bir bilgi bulamadığımız için ideal makro ihtiyaçlarınızı hesaplayamadık. Lütfen Ölçülerim menüsüne dönüp profil oluşturun veya online profil ekleyin.");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}