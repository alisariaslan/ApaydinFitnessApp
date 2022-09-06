package com.pakachu.apaydinfitness.helpers;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

public class CheckHareket {
    Activity activity;
    ImageProcess imageProcess;

    public CheckHareket(Activity activity) {
        this.activity = activity;
        imageProcess = new ImageProcess(activity);
    }

    public Drawable getIcon(String hareketAdi, boolean invert) {
        String assetLocation="";
        switch (hareketAdi) {
            case "isinma": assetLocation="icon/isinma.png"; break;
            case "kardiyo": assetLocation="icon/kardiyo.png"; break;
            case "off": assetLocation="icon/off.png"; break;

            //GÖĞÜS HAREKETLERİ
            case "bench press": assetLocation="icon/benchpress.png"; break;
            case "incline dumbell press": assetLocation="icon/inclinedumbellpress.png"; break;
            case "chest press": assetLocation="icon/chestpress.png"; break;
            case "cable crossover": assetLocation="icon/cablecrossover.png"; break;
            case "machine fly": assetLocation="icon/machinefly.png"; break;
            case "dumbell fly": assetLocation="icon/dumbellfly.png"; break;
            case "incline bench press": assetLocation="icon/inclinebenchpress.png"; break;
            case "decline bench press": assetLocation="icon/declinebenchpress.png"; break;

           //ÖN KOL HAREKETLERİ
            case "bicep curl": assetLocation="icon/bicepcurl.png"; break;
            case "hammer curl": assetLocation="icon/hammercurl.png"; break;
            case "preacher curl": assetLocation="icon/preachercurl.png"; break;
            case "cable curl": assetLocation="icon/cablecurl.png"; break;

            //SIRT HAREKETLERİ
            case "barfiks": assetLocation="icon/barfiks.png"; break;
            case "machine row": assetLocation="icon/machinerow.png"; break;
            case "lat pull": assetLocation="icon/latpull.png"; break;
            case "seated cable row": assetLocation="icon/seatedcablerow.png"; break;
            case "hyperextension": assetLocation="icon/hyperextension.png"; break;
            case "dumbell row": assetLocation="icon/dumbellrow.png"; break;
            case "barbell row": assetLocation="icon/barbellrow.png"; break;
            case "rope pulldown": assetLocation="icon/ropepulldown.png"; break;

            //ARKA KOL HAREKETLERİ
            case "dumbell skullcrusher": assetLocation="icon/dumbellskullcrusher.png"; break;
            case "close grip bench press": assetLocation="icon/closegripbench.png"; break;
            case "rope pushdown": assetLocation="icon/ropepushdown.png"; break;
            case "cable pushdown": assetLocation="icon/cablepushdown.png"; break;
            case "tricep dumbell extension": assetLocation="icon/tricepdumbellextension.png"; break;
            case "dumbell kickback": assetLocation="icon/dumbellkickback.png"; break;
            case "reverse dips": assetLocation="icon/reversedips.png"; break;

            //BACAK HAREKETLERİ
            case "lunge": assetLocation="icon/lunge.png"; break;
            case "squat": assetLocation="icon/squat.png"; break;
            case "leg press": assetLocation="icon/legpress.png"; break;
            case "leg extension": assetLocation="icon/legextension.png"; break;
            case "leg curl": assetLocation="icon/legcurl.png"; break;
            case "calf raise": assetLocation="icon/calfraise.png"; break;

            //OMUZ HAREKETLERİ
            case "dips": assetLocation="icon/dips.png"; break;
            case "dumbell shoulder press": assetLocation="icon/dumbellshoulderpress.png"; break;
            case "lateral raise": assetLocation="icon/lateralraise.png"; break;
            case "front raise": assetLocation="icon/frontraise.png"; break;
            case "rear delt fly": assetLocation="icon/reardeltfly.png"; break;
            case "facepull": assetLocation="icon/ropepushdown.png"; break;

            //KARIN HAREKETLERİ
            case "plank": assetLocation="icon/plank.png"; break;
            case "crunch": assetLocation="icon/crunches.png"; break;
            case "knee to chest": assetLocation="icon/kneetochest.png"; break;
            case "toe tap": assetLocation="icon/toetap.png"; break;
            case "russian twist": assetLocation="icon/russiantwist.png"; break;
        }
        InputStream ims = null;
        try {
            ims = activity.getAssets().open(assetLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ims != null) {
            Drawable img = Drawable.createFromStream(ims, null);
            if (invert)
                img = imageProcess.ColorInvert(img);
            img.setBounds(0, 0, 150, 150);
            return img;

        } else return null;
    }
}
