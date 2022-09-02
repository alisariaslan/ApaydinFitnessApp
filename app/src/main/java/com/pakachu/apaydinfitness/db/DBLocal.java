package com.pakachu.apaydinfitness.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBLocal extends SQLiteOpenHelper {

    private static final String TABLENAME = "uyeler";
    private final String COL1 = "id";
    private final String COL2 = "tarih";
    private final String COL3 = "ad";
    private final String COL4 = "yas";
    private final String COL5 = "cinsiyet";
    private final String COL6 = "kilo";
    private final String COL7 = "boy";
    private final String COL8 = "boyun";
    private final String COL9 = "onkol";
    private final String COL10 = "kol";
    private final String COL11 = "bicep";
    private final String COL12 = "omuz";
    private final String COL13 = "gogus";
    private final String COL14 = "karin";
    private final String COL15 = "kalca";
    private final String COL16 = "bacak";
    private final String COL17 = "calf";
    private final String COL18 = "antrenor";

    public DBLocal(Context context) {
        super(context, TABLENAME, null, 1);
    }
    //INTEGER
    //TEXT
    //REAL
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable1 = "CREATE TABLE " + TABLENAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT," +
                COL3 + " TEXT," +
                COL4 + " INTEGER," +
                COL5 + " TEXT," +
                COL6 + " REAL," +
                COL7 + " REAL," +
                COL8 + " REAL," +
                COL9 + " REAL," +
                COL10 + " REAL," +
                COL11 + " REAL," +
                COL12 + " REAL," +
                COL13 + " REAL," +
                COL14 + " REAL," +
                COL15 + " REAL," +
                COL16 + " REAL," +
                COL17 + " REAL," +
                COL18 + " TEXT)";
        db.execSQL(createTable1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);
    }
    public void addData(String tarih, String ad, int yas, String cinsiyet, float kilo, float boy) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, tarih);
        contentValues.put(COL3, ad);
        contentValues.put(COL4, yas);
        contentValues.put(COL5, cinsiyet);
        contentValues.put(COL6, kilo);
        contentValues.put(COL7, boy);
        db.insert(TABLENAME, null, contentValues);
    }
    public void addData(String tarih, String ad, int yas, String cinsiyet, float kilo, float boy, float boyun,
                        float onkol, float kol, float bicep, float omuz, float gogus, float karin, float kalca,
                        float bacak, float calf, String antrenor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, tarih);
        contentValues.put(COL3, ad);
        contentValues.put(COL4, yas);
        contentValues.put(COL5, cinsiyet);
        contentValues.put(COL6, kilo);
        contentValues.put(COL7, boy);
        contentValues.put(COL8, boyun);
        contentValues.put(COL9, onkol);
        contentValues.put(COL10, kol);
        contentValues.put(COL11, bicep);
        contentValues.put(COL12, omuz);
        contentValues.put(COL13, gogus);
        contentValues.put(COL14, karin);
        contentValues.put(COL15, kalca);
        contentValues.put(COL16, bacak);
        contentValues.put(COL17, calf);
        contentValues.put(COL18, antrenor);
        db.insert(TABLENAME, null, contentValues);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLENAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public String getName() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLENAME;
        Cursor data = db.rawQuery(query, null);
        if(data.getCount()>0) {
            data.moveToFirst();
            return data.getString(2);
        } else
            return "";
    }

    public Cursor getData(String sql) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(sql, null);
        return data;
    }

    public void executeSQL(String sql) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    public void deleteData(String tablename) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+tablename);
        db.close();
    }


}