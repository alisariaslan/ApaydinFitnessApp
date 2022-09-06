package com.pakachu.apaydinfitness;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pakachu.apaydinfitness.adapters.duyuru.DuyuruAdapter;
import com.pakachu.apaydinfitness.adapters.duyuru.DuyuruItem;
import com.pakachu.apaydinfitness.databinding.FragmentDuyurularBinding;
import com.pakachu.apaydinfitness.db.DBLogin;
import com.pakachu.apaydinfitness.db.DBNotif;
import com.pakachu.apaydinfitness.helpers.AddLoader;

import java.util.ArrayList;


public class DuyurularFragment extends Fragment {

    private FragmentDuyurularBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDuyurularBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AddLoader addLoader = new AddLoader(getActivity());
        addLoader.RequestInterstatial();

        Getir();

        binding.floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(DuyurularFragment.this)
                        .navigate(R.id.action_duyurularFragment_to_duyurularAdmin);
            }
        });

        DBLogin dbLogin = new DBLogin(getActivity());
        if (dbLogin.getClearenceLevel() > 99) {
            binding.floatingActionButton3.setVisibility(View.VISIBLE);
        }
    }

    public void Getir() {
        DBNotif dbNotif = new DBNotif(getContext());
        Cursor cursor = dbNotif.getData("SELECT * FROM notif WHERE okundu=0 ORDER BY ID DESC");
        int okunmadiCount = cursor.getCount();
        ArrayList<DuyuruItem> duyuruItemArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String text = cursor.getString(1);
            int id = cursor.getInt(0);
            DuyuruItem duyuruItem = new DuyuruItem(text, id, false);
            duyuruItemArrayList.add(duyuruItem);
        }
        DuyuruAdapter duyuruAdapter = new DuyuruAdapter(getActivity(), getContext(), duyuruItemArrayList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recai.setLayoutManager(layoutManager);
        binding.recai.setAdapter(duyuruAdapter);

        cursor = dbNotif.getData("SELECT * FROM notif WHERE okundu=1 ORDER BY ID DESC");
        int okunduCount = cursor.getCount();

        duyuruItemArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String text = cursor.getString(1);
            int id = cursor.getInt(0);
            DuyuruItem duyuruItem = new DuyuruItem(text, id, true);
            duyuruItemArrayList.add(duyuruItem);
        }
        duyuruAdapter = new DuyuruAdapter(getActivity(), getContext(), duyuruItemArrayList, this);
         layoutManager = new LinearLayoutManager(getContext());
        binding.recigoz.setLayoutManager(layoutManager);
        binding.recigoz.setAdapter(duyuruAdapter);

        Yazdir(okunmadiCount,okunduCount);
    }

    private void Yazdir(int okunmadiCount,int okunduCount) {
        if (okunmadiCount > 0) {
            binding.textView55.setText("Okunmamış Duyurular: " + okunmadiCount);
        } else {
            binding.textView55.setText("Tüm duyurular okundu");
            binding.imageView14.setVisibility(View.VISIBLE);
        }
        if(okunduCount>0)
            binding.textView57.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}