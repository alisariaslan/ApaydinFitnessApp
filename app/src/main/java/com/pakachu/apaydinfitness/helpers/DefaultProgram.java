package com.pakachu.apaydinfitness.helpers;

import android.app.Activity;
import android.view.View;

import com.pakachu.apaydinfitness.customdialogs.MyCustomDialog;
import com.pakachu.apaydinfitness.db.DBIdman;

public class DefaultProgram {

    private Activity activity;

    public DefaultProgram(Activity activity) {
        this.activity = activity;
    }

    public void CheckUP() {
        DBIdman dbIdman = new DBIdman(activity);
        int count = dbIdman.getDataFromProgramTable().getCount();
        if (count == 0) {
            MyCustomDialog myCustomDialog = new MyCustomDialog(activity);
            myCustomDialog.setButtons("Oluştur", "İptal");
            myCustomDialog.setContent("Görünürde hiç antrenman programınız yok.\n\nVarsayılan antrenman programlarını yüklemek ister misiniz?");
            myCustomDialog.positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myCustomDialog.dissmiss();
                    STANDARTSPLIT();
                    STANDARTFULLBODY();
                    new MyCustomDialog(activity).Toast("Bütün varsayılan programlar eklendi. Lütfen sayfayı kapatıp tekrar açın.");
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

    private void STANDARTSPLIT() {
        DBIdman dbIdman = new DBIdman(activity);
        dbIdman.addDataToProgramTable("standartsplit", "pazartesi\nsalı\nçarşamba\nperşembe\ncuma\ncumartesi\npazar");
        String createsql = "CREATE TABLE table0 " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " column0 TEXT, " +
                " column1 TEXT, " +
                " column2 TEXT, " +
                " column3 TEXT, " +
                " column4 TEXT, " +
                " column5 TEXT, " +
                " column6 TEXT) ";
        dbIdman.executeSQL(createsql);
        String[] insertPazartesi = new String[9];
        insertPazartesi[0] = "INSERT INTO table0 (column0) VALUES ('isinma')";
        insertPazartesi[1] = "INSERT INTO table0 (column0) VALUES ('bench press')";
        insertPazartesi[2] = "INSERT INTO table0 (column0) VALUES ('incline dumbell press')";
        insertPazartesi[3] = "INSERT INTO table0 (column0) VALUES ('chest press')";
        insertPazartesi[4] = "INSERT INTO table0 (column0) VALUES ('cable crossover')";
        insertPazartesi[5] = "INSERT INTO table0 (column0) VALUES ('machine fly')";
        insertPazartesi[6] = "INSERT INTO table0 (column0) VALUES ('bicep curl')";
        insertPazartesi[7] = "INSERT INTO table0 (column0) VALUES ('hammer curl')";
        insertPazartesi[8] = "INSERT INTO table0 (column0) VALUES ('kardiyo')";
        for (String s : insertPazartesi
        ) {
            dbIdman.executeSQL(s);
        }
        String[] insertSALI = new String[9];
        insertSALI[0] = "INSERT INTO table0 (column1) VALUES ('isinma')";
        insertSALI[1] = "INSERT INTO table0 (column1) VALUES ('barfiks')";
        insertSALI[2] = "INSERT INTO table0 (column1) VALUES ('machine row')";
        insertSALI[3] = "INSERT INTO table0 (column1) VALUES ('lat pull')";
        insertSALI[4] = "INSERT INTO table0 (column1) VALUES ('seated cable row')";
        insertSALI[5] = "INSERT INTO table0 (column1) VALUES ('hyperextension')";
        insertSALI[6] = "INSERT INTO table0 (column1) VALUES ('dumbell skullcrusher')";
        insertSALI[7] = "INSERT INTO table0 (column1) VALUES ('close grip bench press')";
        insertSALI[8] = "INSERT INTO table0 (column1) VALUES ('kardiyo')";
        for (String s : insertSALI
        ) {
            dbIdman.executeSQL(s);
        }
        String[] insertCARSAMBA = new String[8];
        insertCARSAMBA[0] = "INSERT INTO table0 (column2) VALUES ('isinma')";
        insertCARSAMBA[1] = "INSERT INTO table0 (column2) VALUES ('lunge')";
        insertCARSAMBA[2] = "INSERT INTO table0 (column2) VALUES ('squat')";
        insertCARSAMBA[3] = "INSERT INTO table0 (column2) VALUES ('leg press')";
        insertCARSAMBA[4] = "INSERT INTO table0 (column2) VALUES ('leg extension')";
        insertCARSAMBA[5] = "INSERT INTO table0 (column2) VALUES ('leg curl')";
        insertCARSAMBA[6] = "INSERT INTO table0 (column2) VALUES ('calf raise')";
        insertCARSAMBA[7] = "INSERT INTO table0 (column2) VALUES ('kardiyo')";
        for (String s : insertCARSAMBA
        ) {
            dbIdman.executeSQL(s);
        }
        String[] insertPERSEMBE = new String[10];
        insertPERSEMBE[0] = "INSERT INTO table0 (column3) VALUES ('isinma')";
        insertPERSEMBE[1] = "INSERT INTO table0 (column3) VALUES ('dips')";
        insertPERSEMBE[2] = "INSERT INTO table0 (column3) VALUES ('dumbell shoulder press')";
        insertPERSEMBE[3] = "INSERT INTO table0 (column3) VALUES ('lateral raise')";
        insertPERSEMBE[4] = "INSERT INTO table0 (column3) VALUES ('front raise')";
        insertPERSEMBE[5] = "INSERT INTO table0 (column3) VALUES ('rear delt fly')";
        insertPERSEMBE[6] = "INSERT INTO table0 (column3) VALUES ('preacher curl')";
        insertPERSEMBE[7] = "INSERT INTO table0 (column3) VALUES ('cable curl')";
        insertPERSEMBE[8] = "INSERT INTO table0 (column3) VALUES ('rope pushdown')";
        insertPERSEMBE[9] = "INSERT INTO table0 (column3) VALUES ('cable pushdown')";
        for (String s : insertPERSEMBE
        ) {
            dbIdman.executeSQL(s);
        }
        dbIdman.executeSQL("INSERT INTO table0 (column4) VALUES ('off')");
        dbIdman.executeSQL("INSERT INTO table0 (column5) VALUES ('off')");
        dbIdman.executeSQL("INSERT INTO table0 (column6) VALUES ('off')");
    }

    private void STANDARTFULLBODY() {
        DBIdman dbIdman = new DBIdman(activity);
        dbIdman.addDataToProgramTable("standartfullbody", "pazartesi\nsalı\nçarşamba\nperşembe\ncuma\ncumartesi\npazar");
        String createsql = "CREATE TABLE table1 " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " column0 TEXT, " +
                " column1 TEXT, " +
                " column2 TEXT, " +
                " column3 TEXT, " +
                " column4 TEXT, " +
                " column5 TEXT, " +
                " column6 TEXT) ";
        dbIdman.executeSQL(createsql);
        String[] insertPazartesi = new String[8];
        insertPazartesi[0] = "INSERT INTO table1 (column0) VALUES ('isinma')";
        insertPazartesi[1] = "INSERT INTO table1 (column0) VALUES ('bench press')";
        insertPazartesi[2] = "INSERT INTO table1 (column0) VALUES ('chest press')";
        insertPazartesi[3] = "INSERT INTO table1 (column0) VALUES ('lat pull')";
        insertPazartesi[4] = "INSERT INTO table1 (column0) VALUES ('seated cable row')";
        insertPazartesi[5] = "INSERT INTO table1 (column0) VALUES ('squat')";
        insertPazartesi[6] = "INSERT INTO table1 (column0) VALUES ('leg press')";
        insertPazartesi[7] = "INSERT INTO table1 (column0) VALUES ('kardiyo')";
        for (String s : insertPazartesi
        ) {
            dbIdman.executeSQL(s);
        }
        String[] insertSALI = new String[8];
        insertSALI[0] = "INSERT INTO table1 (column1) VALUES ('isinma')";
        insertSALI[1] = "INSERT INTO table1 (column1) VALUES ('incline dumbell press')";
        insertSALI[2] = "INSERT INTO table1 (column1) VALUES ('dumbell fly')";
        insertSALI[3] = "INSERT INTO table1 (column1) VALUES ('dumbell row')";
        insertSALI[4] = "INSERT INTO table1 (column1) VALUES ('machine row')";
        insertSALI[5] = "INSERT INTO table1 (column1) VALUES ('lunge')";
        insertSALI[6] = "INSERT INTO table1 (column1) VALUES ('leg press')";
        insertSALI[7] = "INSERT INTO table1 (column1) VALUES ('kardiyo')";
        for (String s : insertSALI
        ) {
            dbIdman.executeSQL(s);
        }
        String[] insertCARSAMBA = new String[8];
        insertCARSAMBA[0] = "INSERT INTO table1 (column2) VALUES ('isinma')";
        insertCARSAMBA[1] = "INSERT INTO table1 (column2) VALUES ('chest press')";
        insertCARSAMBA[2] = "INSERT INTO table1 (column2) VALUES ('dumbell shoulder press')";
        insertCARSAMBA[3] = "INSERT INTO table1 (column2) VALUES ('lat pull')";
        insertCARSAMBA[4] = "INSERT INTO table1 (column2) VALUES ('seated cable row')";
        insertCARSAMBA[5] = "INSERT INTO table1 (column2) VALUES ('hyperextension')";
        insertCARSAMBA[6] = "INSERT INTO table1 (column2) VALUES ('squat')";
        insertCARSAMBA[7] = "INSERT INTO table1 (column2) VALUES ('kardiyo')";
        for (String s : insertCARSAMBA
        ) {
            dbIdman.executeSQL(s);
        }
        String[] insertPERSEMBE = new String[8];
        insertPERSEMBE[0] = "INSERT INTO table1 (column3) VALUES ('isinma')";
        insertPERSEMBE[1] = "INSERT INTO table1 (column3) VALUES ('dumbell shoulder press')";
        insertPERSEMBE[2] = "INSERT INTO table1 (column3) VALUES ('lateral raise')";
        insertPERSEMBE[3] = "INSERT INTO table1 (column3) VALUES ('rear delt fly')";
        insertPERSEMBE[4] = "INSERT INTO table1 (column3) VALUES ('facepull')";
        insertPERSEMBE[5] = "INSERT INTO table1 (column3) VALUES ('leg extension')";
        insertPERSEMBE[6] = "INSERT INTO table1 (column3) VALUES ('leg curl')";
        insertPERSEMBE[7] = "INSERT INTO table1 (column3) VALUES ('kardiyo')";
        for (String s : insertPERSEMBE
        ) {
            dbIdman.executeSQL(s);
        }
        dbIdman.executeSQL("INSERT INTO table1 (column4) VALUES ('off')");
        dbIdman.executeSQL("INSERT INTO table1 (column5) VALUES ('off')");
        dbIdman.executeSQL("INSERT INTO table1 (column6) VALUES ('off')");
    }
}
