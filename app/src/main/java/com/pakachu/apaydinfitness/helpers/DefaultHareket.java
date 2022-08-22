package com.pakachu.apaydinfitness.helpers;

import android.app.Activity;

import com.pakachu.apaydinfitness.db.DBIdman;

public class DefaultHareket {
    private Activity activity;
    private DBIdman dbIdman;

    public DefaultHareket(Activity activity) {
        this.activity = activity;
        dbIdman = new DBIdman(activity);
    }

    public void Gogus() {
        dbIdman.addDataToHareketTable("bench press", "Göğüs");
        dbIdman.addDataToHareketTable("incline dumbell press", "Göğüs");
        dbIdman.addDataToHareketTable("chest press", "Göğüs");
        dbIdman.addDataToHareketTable("cable crossover", "Göğüs");
        dbIdman.addDataToHareketTable("machine fly", "Göğüs");
        dbIdman.addDataToHareketTable("dumbell fly", "Göğüs");
        dbIdman.addDataToHareketTable("incline bench press", "Göğüs");
        dbIdman.addDataToHareketTable("decline bench press", "Göğüs");
    }

    public void Sırt() {
        dbIdman.addDataToHareketTable("barfiks", "Sırt");
        dbIdman.addDataToHareketTable("machine row", "Sırt");
        dbIdman.addDataToHareketTable("dumbell row", "Sırt");
        dbIdman.addDataToHareketTable("lat pull", "Sırt");
        dbIdman.addDataToHareketTable("seated cable row", "Sırt");
        dbIdman.addDataToHareketTable("hyperextension", "Sırt");
        dbIdman.addDataToHareketTable("barbell row", "Sırt");
        dbIdman.addDataToHareketTable("rope pulldown", "Sırt");
    }

    public void OnKol() {
        dbIdman.addDataToHareketTable("bicep curl", "Ön Kol");
        dbIdman.addDataToHareketTable("hammer curl", "Ön Kol");
        dbIdman.addDataToHareketTable("preacher curl", "Ön Kol");
        dbIdman.addDataToHareketTable("cable curl", "Ön Kol");
    }

    public void ArkaKol() {
        dbIdman.addDataToHareketTable("dumbell skullcrusher", "Arka Kol");
        dbIdman.addDataToHareketTable("close grip bench press", "Arka Kol");
        dbIdman.addDataToHareketTable("rope pushdown", "Arka Kol");
        dbIdman.addDataToHareketTable("cable pushdown", "Arka Kol");
        dbIdman.addDataToHareketTable("tricep dumbell extension", "Arka Kol");
        dbIdman.addDataToHareketTable("dumbell kickback", "Arka Kol");
        dbIdman.addDataToHareketTable("reverse dips", "Arka Kol");
    }

    public void Bacak() {
        dbIdman.addDataToHareketTable("lunge", "Bacak");
        dbIdman.addDataToHareketTable("squat", "Bacak");
        dbIdman.addDataToHareketTable("leg press", "Bacak");
        dbIdman.addDataToHareketTable("leg extension", "Bacak");
        dbIdman.addDataToHareketTable("leg curl", "Bacak");
        dbIdman.addDataToHareketTable("calf raise", "Bacak");
    }

    public void Omuz() {
        dbIdman.addDataToHareketTable("dips", "Omuz");
        dbIdman.addDataToHareketTable("facepull", "Omuz");
        dbIdman.addDataToHareketTable("dumbell shoulder press", "Omuz");
        dbIdman.addDataToHareketTable("lateral raise", "Omuz");
        dbIdman.addDataToHareketTable("front raise", "Omuz");
        dbIdman.addDataToHareketTable("rear delt fly", "Omuz");
    }

    public void Karin() {
        dbIdman.addDataToHareketTable("plank", "Karın");
        dbIdman.addDataToHareketTable("crunch", "Karın");
        dbIdman.addDataToHareketTable("knee to chest", "Karın");
        dbIdman.addDataToHareketTable("toe tap", "Karın");
        dbIdman.addDataToHareketTable("russian twist", "Karın");
    }

    public void Diger() {
        dbIdman.addDataToHareketTable("ısınma", "Diğer");
        dbIdman.addDataToHareketTable("kardiyo", "Diğer");
        dbIdman.addDataToHareketTable("off", "Diğer");
    }
}
