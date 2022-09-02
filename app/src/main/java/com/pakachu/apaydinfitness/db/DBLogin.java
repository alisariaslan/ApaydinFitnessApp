package com.pakachu.apaydinfitness.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBLogin extends SQLiteOpenHelper {

    public static boolean loggedIn=false;

    private static final String TABLENAME = "login";
    private final String COL1 = "id";
    private final String COL2 = "username";
    private final String COL3 = "password";
    private final String COL4 = "remember";
    private final String COL5 = "easylogin";
    private final String COL6 = "clearence";
    private final String COL7 = "logged";

    public DBLogin(Context context) {
        super(context, TABLENAME, null, 3);
    }
    //INTEGER
    //TEXT
    //REAL
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLENAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT," +
                COL3 + " TEXT," +
                COL4 + " INTEGER," +
                COL5 + " INTEGER," +
                COL6 + " INTEGER," +
                COL7 + " INTEGER)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);
    }
    public void addData(String id,String pin,int remember,int easylogin,int clearence,int logged) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, id);
        contentValues.put(COL3, pin);
        contentValues.put(COL4, remember);
        contentValues.put(COL5, easylogin);
        contentValues.put(COL6, clearence);
        contentValues.put(COL7, logged);
        db.insert(TABLENAME, null, contentValues);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLENAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public int getClearenceLevel() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLENAME + " WHERE logged=1";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        return data.getInt(5);
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