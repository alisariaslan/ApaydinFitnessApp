package com.pakachu.apaydinfitness.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBJeton extends SQLiteOpenHelper {

    public static boolean loggedIn = false;

    private static final String TABLENAME = "jeton";
    private final String COL1 = "id";
    private final String COL2 = "coin";
    private final String COL3 = "ap";

    public DBJeton(Context context) {
        super(context, TABLENAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLENAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," + COL2 + " INTEGER, " + COL3 + " INTEGER)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLENAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void addData(int coin, int ap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, coin);
        contentValues.put(COL3, ap);
        db.insert(TABLENAME, null, contentValues);
    }

    public int getCoin() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT coin FROM " + TABLENAME;
        Cursor data = db.rawQuery(query, null);
        int coin = 0;
        if(data.getCount()>0) {
            data.moveToFirst();
            coin = data.getInt(0);
        }
        return coin;
    }

    public int getAP() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT ap FROM " + TABLENAME;
        Cursor data = db.rawQuery(query, null);
        int ap = 0;
        if(data.getCount()>0) {
            data.moveToFirst();
            ap = data.getInt(0);
        }
        return ap;
    }

    public void setCoin(int coin) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+TABLENAME+" SET coin="+coin);
        db.close();
    }

    public void setAP(int ap) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+TABLENAME+" SET ap="+ap);
        db.close();
    }


    public void deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLENAME);
        db.close();
    }

}