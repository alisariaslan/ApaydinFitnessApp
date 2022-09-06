package com.pakachu.apaydinfitness.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBDiet extends SQLiteOpenHelper {

    private static final String TABLENAME = "food_list";
    private final String COL2 = "food_name";
    private final String COL3 = "is_liquid";
    private final String COL4 = "gram";
    private final String COL5 = "protein";
    private final String COL6 = "carb";
    private final String COL7 = "fat";

    private final String TABLENAME2 = "daily_list";
    private final String COL22 = "food_index";
    private final String COL33 = "food_gram";

    private final String TABLENAME3 = "pass_list";
    private final String COL222 = "food_indexes";
    private final String COL333 = "taken_calorie";
    private final String COL444 = "date";

    public DBDiet(Context context) {
        super(context, TABLENAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLENAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT," +
                COL3 + " INTEGER," +
                COL4 + " INTEGER," +
                COL5 + " FLOAT," +
                COL6 + " FLOAT," +
                COL7 + " FLOAT)";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " + TABLENAME2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL22 + " INTEGER," +
                COL33 + " INTEGER)";
        db.execSQL(createTable);

        createTable = "CREATE TABLE " + TABLENAME3 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL222 + " TEXT," +
                COL333 + " INTEGER," +
                COL444 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(db);
    }

    public void addDataToFoodList(String foodName, boolean is_liquid, int grams, float protein, float carb, float fat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, foodName);
        contentValues.put(COL3, is_liquid);
        contentValues.put(COL4, grams);
        contentValues.put(COL5, protein);
        contentValues.put(COL6, carb);
        contentValues.put(COL7, fat);
        db.insert(TABLENAME, null, contentValues);
    }

    public void addDataToDailyList(int foodIndex, int gram) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL22, foodIndex);
        contentValues.put(COL33, gram);
        db.insert(TABLENAME2, null, contentValues);
    }

    public void addDataToPassList(String foodIndexes, int requiredCalorie, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL222, foodIndexes);
        contentValues.put(COL333, requiredCalorie);
        contentValues.put(COL444, date);
        db.insert(TABLENAME3, null, contentValues);
    }

    public Cursor getDataFromFoodList() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLENAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public int getFoodListCount() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLENAME;
        Cursor data = db.rawQuery(query, null);
        return data.getCount();
    }

    public Cursor getDataFromDailyList() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLENAME2;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getFoodInfo(int index) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLENAME + " WHERE id="+index;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDataFromPassList() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLENAME3;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDataWithSQL(String sql) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(sql, null);
        return data;
    }

    public void executeSQL(String sql) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

}