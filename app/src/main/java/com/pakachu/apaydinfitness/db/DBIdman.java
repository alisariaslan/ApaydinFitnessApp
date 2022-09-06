package com.pakachu.apaydinfitness.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBIdman extends SQLiteOpenHelper {

    private static final String TABLE1 = "programlarim";
    private final String TABLE1_COL1 = "id";
    private final String TABLE1_COL2 = "ad";
    private final String TABLE1_COL3 = "gunler";

    private static final String TABLE2 = "hareketler";
    private final String TABLE2_COL1 = "id";
    private final String TABLE2_COL2 = "hareket";
    private final String TABLE2_COL3 = "bolgesi";


    public DBIdman(Context context) {
        super(context, TABLE1, null, 1);
    }

    //INTEGER
    //TEXT
    //REAL
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable1 = "CREATE TABLE " + TABLE1 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE1_COL2 + " TEXT," +
                TABLE1_COL3 + " TEXT)";
        db.execSQL(createTable1);

        String createTable2 = "CREATE TABLE " + TABLE2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE2_COL2 + " TEXT," +
                TABLE2_COL3 + " TEXT)";
        db.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1);
        onCreate(db);
    }

    public void addDataToProgramTable(String programAdi, String gunler) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE1_COL2, programAdi);
        contentValues.put(TABLE1_COL3, gunler);
        db.insert(TABLE1, null, contentValues);
    }

    public Cursor getDataFromProgramTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE1;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteDataFromProgramTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE1);
        db.close();
    }


    public void addDataToHareketTable(String hareketAdi, String bolgesi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TABLE2_COL2, hareketAdi);
        contentValues.put(TABLE2_COL3, bolgesi);
        db.insert(TABLE2, null, contentValues);
    }

    public boolean CheckData(String hareketAdi) {
        Cursor cursor = getData("SELECT hareket FROM hareketler WHERE hareket ='"+hareketAdi+"'");
        if(cursor.moveToFirst()) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getDataFromHareketTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE2;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

//
//    public void addDetay(String detayAdi) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(TABLE3_COL2, detayAdi);
//        db.insert(TABLE3, null, contentValues);
//    }


    //
//    public int getClearenceLevel() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "SELECT * FROM " + TABLENAME;
//        Cursor data = db.rawQuery(query, null);
//        data.moveToFirst();
//        return data.getInt(5);
//    }
//
    public Cursor getData(String sql) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(sql, null);
        return data;
    }

    //
    public void executeSQL(String sql) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

    public ArrayList<String> getTableNamesArray() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> tables = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        while (cursor.moveToNext()) {
            String tableName = cursor.getString(0);
            if (tableName.equals("android_metadata") ||
                    tableName.equals("sqlite_sequence") ||
                    tableName.equals(TABLE1) ||
                    tableName.equals(TABLE2)
            ) {
            } else {
                tables.add(tableName);
            }
        }
        return tables;
    }


}