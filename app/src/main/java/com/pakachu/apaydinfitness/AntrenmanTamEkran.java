package com.pakachu.apaydinfitness;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pakachu.apaydinfitness.adapters.ProgramAdapter;
import com.pakachu.apaydinfitness.adapters.ProgramItem;
import com.pakachu.apaydinfitness.databinding.FragmentAntrenmanTamEkranBinding;
import com.pakachu.apaydinfitness.db.DBIdman;
import com.pakachu.apaydinfitness.helpers.AddLoader;
import com.pakachu.apaydinfitness.helpers.DefaultProgram;

import java.util.ArrayList;


public class AntrenmanTamEkran extends Fragment {

    private FragmentAntrenmanTamEkranBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAntrenmanTamEkranBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AntrenmanlarLoad();

        AddLoader addLoader = new AddLoader(getActivity());
        addLoader.RequestInterstatial();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    public void AntrenmanlarLoad() {
        ArrayList<ProgramItem> programItemArrayList = new ArrayList<>();
        DBIdman dbIdman = new DBIdman(getActivity());
        Cursor cursor = dbIdman.getDataFromProgramTable();
        while (cursor.moveToNext()) {
            String programAdi = cursor.getString(1);
            ProgramItem programItem = new ProgramItem(programAdi, cursor.getPosition());
            programItemArrayList.add(programItem);
        }
        ProgramAdapter myAdapter;
        myAdapter = new ProgramAdapter(getActivity(), programItemArrayList, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyview.setLayoutManager(layoutManager);
        binding.recyview.setAdapter(myAdapter);
        if(programItemArrayList.size()==0) {
            MyCustomDialog myCustomDialog=new MyCustomDialog(getActivity());
            myCustomDialog.setButtons("Oluştur","İptal");
            myCustomDialog.setContent("Görünürde hiç antrenman programınız yok.\n\nVarsayılan antrenman programlarını yüklemek ister misiniz?");
            myCustomDialog.positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myCustomDialog.dissmiss();
                    DefaultProgram defaultProgram=new DefaultProgram();
                    defaultProgram.STANDARTSPLIT(getActivity());
                    defaultProgram.STANDARTFULLBODY(getActivity());
                    AntrenmanlarLoad();
                }
            });
            myCustomDialog.negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myCustomDialog.dissmiss();
                }
            });
            myCustomDialog.show(true);
        }

    }

}