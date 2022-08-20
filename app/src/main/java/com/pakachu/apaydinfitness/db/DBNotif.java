package com.pakachu.apaydinfitness.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBNotif extends SQLiteOpenHelper {

    public static boolean loggedIn = false;

    private static final String TABLENAME = "notif";
    private final String COL1 = "id";
    private final String COL2 = "text";
    private final String COL3 = "okundu";

    public DBNotif(Context context) {
        super(context, TABLENAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLENAME + " (ID INTEGER, text TEXT, okundu INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);
    }

    public void addData(int id, String text,int okundu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, id);
        contentValues.put(COL2, text);
        contentValues.put(COL3, okundu);
        db.insert(TABLENAME, null, contentValues);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLENAME;
        Cursor data = db.rawQuery(query, null);
        return data;
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

    public void deleteData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLENAME);
        db.close();
    }

    public int getUnreadSize()
    {
        int size = 0;
        size = getData("SELECT * FROM notif WHERE okundu=0 ORDER BY ID DESC").getCount();
        return size;
    }


}