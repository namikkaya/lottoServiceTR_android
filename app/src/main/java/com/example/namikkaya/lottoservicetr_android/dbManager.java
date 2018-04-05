package com.example.namikkaya.lottoservicetr_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class dbManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "piyango";
    private static final String TABLE_NAME = "myLuckyNumbers";
    private static String ID = "id";
    private static String NUMBERS = "numbers";

    public dbManager(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NUMBERS + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
        System.out.println("Database => DB OLUŞTURULDU.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }


    /**
     * Satırı siler
     * @param id silinecek grubun id si
     */
    public void deleteLuckyNumbers(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + "=?",
                new String[]{String.valueOf(id)}
        );
    }

    /**
     * Satır ekler
     * @param numbers şanslı sayılar string olarak alır ve yazar.
     */
    public void insertLuckyNumber(String numbers){
        SQLiteDatabase dbIN = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NUMBERS, numbers);
        dbIN.insert(TABLE_NAME, null, values);
        dbIN.close();
    }

    /**
     * Eklenmiş bütün satırları döndürür.
     * @return
     */
    public  ArrayList<HashMap<String, String>> getAllLuckyNumbers(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> numberList = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for(int i=0; i<cursor.getColumnCount();i++)
                {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }

                numberList.add(map);
            } while (cursor.moveToNext());
        }
        db.close();
        return numberList;
    }

    /**
     * uzunluğu döndürür
     * @return
     */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        return rowCount;
    }

}
