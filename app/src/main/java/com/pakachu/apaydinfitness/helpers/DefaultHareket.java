package com.pakachu.apaydinfitness.helpers;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.pakachu.apaydinfitness.AntrenmanOlusturFragment;
import com.pakachu.apaydinfitness.HareketlerFragment;
import com.pakachu.apaydinfitness.customdialogs.MyCustomDialog;
import com.pakachu.apaydinfitness.db.DBIdman;

import java.util.ArrayList;

public class DefaultHareket {
    private Activity activity;
    private DBIdman dbIdman;

    private ArrayList<String> hareketler;
    private ArrayList<String> bolgeleri;

    public HareketlerFragment hareketlerFragment;
    public AntrenmanOlusturFragment antrenmanOlusturFragment;

    String hareket="",bolgesi="";
    int i = 0;

    public DefaultHareket(Activity activity) {
        this.activity = activity;
        dbIdman = new DBIdman(activity);
    }

    public void CheckDown() {
        Log.e("hareketler",""+hareketler.size());
        Log.e("hareketler",""+bolgeleri.size());

        boolean neverAdded = false;
        for ( i = 0; i < hareketler.size(); i++) {
             hareket = hareketler.get(i);
             bolgesi = bolgeleri.get(i);
            neverAdded = dbIdman.CheckData(hareket);
            Log.e("never",""+neverAdded);
            if(neverAdded)
                break;
        }

        if(neverAdded)
        {
            MyCustomDialog myCustomDialog=new MyCustomDialog(activity);
            myCustomDialog.setButtons("Ekle","Atla","İptal");
            myCustomDialog.setContent(bolgesi+" bölgesine ait \""+hareket+"\" hareketi listenizde bulunmamaktadır. Listenize eklemek ister misiniz?");
            myCustomDialog.positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myCustomDialog.dissmiss();
                    dbIdman.addDataToHareketTable(hareket,bolgesi);
                    if(hareketlerFragment!=null)
                        hareketlerFragment.ListHarekets("");
                    if(antrenmanOlusturFragment!=null)
                        antrenmanOlusturFragment.HareketDuzenle_SetTumHareketlerSpinner();
                    CheckDown();
                }
            });
            myCustomDialog.negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myCustomDialog.dissmiss();
                    hareketler.remove(i);
                    bolgeleri.remove(i);
                    CheckDown();
                }
            });
            myCustomDialog.neutral.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myCustomDialog.dissmiss();
                }
            });
            myCustomDialog.show(false);
        }
    }

    public void CheckUP() {
        hareketler = new ArrayList<>();
        bolgeleri = new ArrayList<>();

        //GÖĞÜS
        hareketler.add("bench press");
        bolgeleri.add("Göğüs");
        hareketler.add("incline dumbell press");
        bolgeleri.add("Göğüs");
        hareketler.add("chest press");
        bolgeleri.add("Göğüs");
        hareketler.add("cable crossover");
        bolgeleri.add("Göğüs");
        hareketler.add("machine fly");
        bolgeleri.add("Göğüs");
        hareketler.add("dumbell fly");
        bolgeleri.add("Göğüs");
        hareketler.add("incline bench press");
        bolgeleri.add("Göğüs");
        hareketler.add("decline bench press");
        bolgeleri.add("Göğüs");

        //SIRT
        hareketler.add("barfiks");
        bolgeleri.add("Sırt");
        hareketler.add("machine row");
        bolgeleri.add("Sırt");
        hareketler.add("dumbell row");
        bolgeleri.add("Sırt");
        hareketler.add("lat pull");
        bolgeleri.add("Sırt");
        hareketler.add("seated cable row");
        bolgeleri.add("Sırt");
        hareketler.add("hyperextension");
        bolgeleri.add("Sırt");
        hareketler.add("barbell row");
        bolgeleri.add("Sırt");
        hareketler.add("rope pulldown");
        bolgeleri.add("Sırt");

        //ÖN KOL
        hareketler.add("bicep curl");
        bolgeleri.add("Ön Kol");
        hareketler.add("hammer curl");
        bolgeleri.add("Ön Kol");
        hareketler.add("preacher curl");
        bolgeleri.add("Ön Kol");
        hareketler.add("cable curl");
        bolgeleri.add("Ön Kol");

        //ARKA KOL
        hareketler.add("dumbell skullcrusher");
        bolgeleri.add("Arka Kol");
        hareketler.add("close grip bench press");
        bolgeleri.add("Arka Kol");
        hareketler.add("rope pushdown");
        bolgeleri.add("Arka Kol");
        hareketler.add("cable pushdown");
        bolgeleri.add("Arka Kol");
        hareketler.add("tricep dumbell extension");
        bolgeleri.add("Arka Kol");
        hareketler.add("dumbell kickback");
        bolgeleri.add("Arka Kol");
        hareketler.add("reverse dips");
        bolgeleri.add("Arka Kol");

        //BACAK
        hareketler.add("lunge");
        bolgeleri.add( "Bacak");
        hareketler.add( "squat");
        bolgeleri.add( "Bacak");
        hareketler.add("leg press");
        bolgeleri.add( "Bacak");
        hareketler.add( "leg extension");
        bolgeleri.add("Bacak");
        hareketler.add( "leg curl");
        bolgeleri.add( "Bacak");
        hareketler.add( "calf raise");
        bolgeleri.add( "Bacak");

        //OMUZ
        hareketler.add(  "dips");
        bolgeleri.add("Omuz");
        hareketler.add( "facepull");
        bolgeleri.add("Omuz");
        hareketler.add( "dumbell shoulder press");
        bolgeleri.add( "Omuz");
        hareketler.add(  "lateral raise");
        bolgeleri.add("Omuz");
        hareketler.add(  "front raise");
        bolgeleri.add( "Omuz");
        hareketler.add(  "rear delt fly");
        bolgeleri.add("Omuz");

        //KARIN
        hareketler.add( "plank");
        bolgeleri.add("Omuz");
        hareketler.add(  "crunch");
        bolgeleri.add("Omuz");
        hareketler.add( "knee to chest");
        bolgeleri.add( "Omuz");
        hareketler.add( "toe tap");
        bolgeleri.add("Omuz");
        hareketler.add(  "russian twist");
        bolgeleri.add( "Omuz");

        //DİĞER
        hareketler.add( "isinma");
        bolgeleri.add("Omuz");
        hareketler.add( "kardiyo");
        bolgeleri.add("Omuz");
        hareketler.add( "off");
        bolgeleri.add("Omuz");

        CheckDown();
    }

}
