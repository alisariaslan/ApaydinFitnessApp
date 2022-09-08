package com.pakachu.apaydinfitness;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CalendarView;
import android.widget.CompoundButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pakachu.apaydinfitness.adapters.diet.FoodAdapter;
import com.pakachu.apaydinfitness.adapters.diet.FoodItem;
import com.pakachu.apaydinfitness.adapters.diet.PastAdapter;
import com.pakachu.apaydinfitness.adapters.diet.PastItem;
import com.pakachu.apaydinfitness.customdialogs.MyCustomDialog;
import com.pakachu.apaydinfitness.databinding.FragmentDietBinding;
import com.pakachu.apaydinfitness.db.DBDiet;
import com.pakachu.apaydinfitness.db.DBLocal;
import com.pakachu.apaydinfitness.helpers.AddLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class DietFragment extends Fragment {

    private FragmentDietBinding binding;
    private int daily_kcal_need, daily_p_need, daily_c_need, daily_f_need;
    private int kcal_taken, p_taken, c_taken, f_taken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDietBinding.inflate(inflater, container, false);

        AddLoader addLoader = new AddLoader(getActivity());
        addLoader.RequestInterstatial();

        CalculateReqDailyMacros();
        ListDailyFoods();
        ListFoods(100);
        ListPass();

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
                                            ListFoods(100);
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

        binding.button25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kcal_taken > 0) {
                    DBDiet dbDiet = new DBDiet(getActivity());
                    Cursor consumed_foods = dbDiet.getDataFromDailyList();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    CalendarView calendarView = new CalendarView(getActivity());
                    String foods = "";
                    while (consumed_foods.moveToNext()) {
                        foods += consumed_foods.getInt(1) + ",";
                    }
                    foods = foods.substring(0, foods.length() - 1);
                    String info = info();
                    dbDiet.addDataToPassList(foods, kcal_taken, sdf.format(new Date(calendarView.getDate())));
                    dbDiet.executeSQL("DELETE FROM daily_list");
                    ListDailyFoods();
                    ListPass();
                    new MyCustomDialog(getActivity()).Toast("Yedikleriniz geçmişe eklendi.\n\n" + info);
                } else
                    new MyCustomDialog(getActivity()).Toast("Bugün yeterince beslenmediğiniz için geçmişe eklenemez!");
            }
        });

        binding.button37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBDiet dbDiet = new DBDiet(getActivity());
                dbDiet.executeSQL("DELETE FROM pass_list");
                ListPass();
                new MyCustomDialog(getActivity()).Toast("Geçmiş başaryıla temizlendi.");
            }
        });

        binding.editTextTextPersonName30.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ListByFoodName(s.toString());
            }
        });

        binding.radioButton8.setChecked(true);

        return binding.getRoot();
    }

    private String info() {
        String infoString = "KONTROL\n";
        if (kcal_taken < daily_kcal_need / 1.2f)
            infoString += "Öyle görünüyor ki bugün yeterince kalori almamışsınız. Bu şekilde devam ederseniz kilo verebilirsiniz.\n";
        else if (kcal_taken > daily_kcal_need * 1.2f)
            infoString += "Öyle görünüyor ki bugün ihtiyacınızdan fazla kalori almışsınız. Bu şekilde devam ederseniz kilo alabilirsiniz.\n";
        else
            infoString += "Öyle görünüyor ki bugün ihtiyacınıza yaklaşık miktarda kalori almışsınız. Bu şekilde devam ederseniz kilonuzu koruyabilirsiniz.\n";

        return infoString;
    }

    private void ListPass() {
        ArrayList<PastItem> pastItems = new ArrayList<>();

        DBDiet dbDiet = new DBDiet(getActivity());
        Cursor passlist = dbDiet.getDataFromPassList();
        while (passlist.moveToNext()) {
            PastItem pastItem = new PastItem(passlist.getString(1), passlist.getInt(2), passlist.getString(3));
            pastItems.add(pastItem);
        }
        PastAdapter adapterMember = new PastAdapter(getActivity(), pastItems);
        binding.recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerView3.setAdapter(adapterMember);
        if (passlist.getCount() > 0) {
            binding.textView89.setVisibility(View.GONE);
            binding.button37.setVisibility(View.VISIBLE);
        } else {
            binding.textView89.setVisibility(View.VISIBLE);
            binding.button37.setVisibility(View.GONE);
        }
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
        kcal_taken = (int) calories_taken;
        p_taken = (int) protein_taken;
        c_taken = (int) carb_taken;
        f_taken = (int) fat_taken;
        binding.progressBarProtein.setProgress(p_taken);
        binding.progressBarKarb.setProgress(c_taken);
        binding.progressBarYag.setProgress(f_taken);

        binding.tvKal.setText("Alınan kalori: " + (int) kcal_taken + " / " + daily_kcal_need + " kcal");
        binding.tvPro.setText("Protein: " + (int) p_taken + " / " + daily_p_need + " gr");
        binding.tvKarb.setText("Karbonhidrat: " + (int) c_taken + " / " + daily_c_need + " gr");
        binding.tvYag.setText("Yağ: " + (int) f_taken + " / " + daily_f_need + " gr");

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

    private void ListByFoodName(String foodname) {
        DBDiet dbDiet = new DBDiet(getContext());
        ArrayList<FoodItem> foodItems = new ArrayList<>();
        Cursor cursor = dbDiet.getDataWithSQL("SELECT * FROM food_list WHERE food_name LIKE '"+foodname+"%'");
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
    }

    public void ListFoods(int limit) {
        DBDiet dbDiet = new DBDiet(getContext());
        ArrayList<FoodItem> foodItems = new ArrayList<>();
        Cursor cursor = dbDiet.getDataWithSQL("SELECT * FROM food_list LIMIT "+limit);
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

                        daily_kcal_need = (int) kcal_need;
                        daily_p_need = (int) protein_need;
                        daily_c_need = (int) carb_need;
                        daily_f_need = (int) fat_need;

                        binding.progressBarProtein.setMax(daily_p_need);
                        binding.progressBarKarb.setMax(daily_c_need);
                        binding.progressBarYag.setMax(daily_f_need);

                        Log.e("diet", "" + kcal_need + "\n" + protein_need + "\n" + carb_need + "\n" + fat_need);
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