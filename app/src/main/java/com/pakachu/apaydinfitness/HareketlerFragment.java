package com.pakachu.apaydinfitness;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewbinding.ViewBinding;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pakachu.apaydinfitness.adapters.hareketler.OnizleHareketAndDetayAdapter;
import com.pakachu.apaydinfitness.adapters.hareketler.OnizleHareketAndDetayItem;
import com.pakachu.apaydinfitness.adapters.idman.ProgramAdapter;
import com.pakachu.apaydinfitness.adapters.idman_olustur.OnizleHareketAdapter;
import com.pakachu.apaydinfitness.adapters.idman_olustur.OnizleHareketItem;
import com.pakachu.apaydinfitness.adapters.idman_olustur.OnizleItem;
import com.pakachu.apaydinfitness.databinding.FragmentHareketlerBinding;
import com.pakachu.apaydinfitness.db.DBIdman;
import com.pakachu.apaydinfitness.helpers.AddLoader;
import com.pakachu.apaydinfitness.helpers.DefaultHareket;

import java.util.ArrayList;

public class HareketlerFragment extends Fragment {

    FragmentHareketlerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentHareketlerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AddLoader addLoader = new AddLoader(getActivity());
        addLoader.RequestInterstatial();

        DefaultHareket defaultHareket = new DefaultHareket(getActivity());
        defaultHareket.hareketlerFragment = this;
        defaultHareket.CheckUP();

        ListHarekets("");

        binding.editTextTextPersonName366.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ListHarekets(s.toString());
            }
        });
    }

    public void ListHarekets(String name) {
        ArrayList<OnizleHareketAndDetayItem> onizleHareketAndDetayItems=new ArrayList<>();
        DBIdman dbIdman=new DBIdman(getContext());
        Cursor cursor= dbIdman.getData("SELECT * FROM hareketler WHERE hareket LIKE '%"+name+"%'");
        while(cursor.moveToNext()) {
            OnizleHareketAndDetayItem onizleHareketAndDetayItem =new OnizleHareketAndDetayItem(cursor.getString(1),cursor.getInt(0));
            onizleHareketAndDetayItems.add(onizleHareketAndDetayItem);
        }
        binding.textView50.setText(cursor.getCount()+" hareket bulundu.");
        OnizleHareketAndDetayAdapter myAdapter = new OnizleHareketAndDetayAdapter(getActivity(), onizleHareketAndDetayItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.reciav.setLayoutManager(layoutManager);
        binding.reciav.setAdapter(myAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}