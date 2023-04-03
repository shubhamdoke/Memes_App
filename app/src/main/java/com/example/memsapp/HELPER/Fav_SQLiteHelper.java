package com.example.memsapp.HELPER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.memsapp.MODEL.Fav_Model;

import java.util.ArrayList;

public class Fav_SQLiteHelper extends SQLiteOpenHelper {

    //Database Variable
    private static final String DB_NAME = "Memes";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Fav_memes";


    //Employee Variable
    private static final String meme_id = "id";
    private static final String joke = "joke";
    private static final String ans = "ans";


    public Fav_SQLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " (" + meme_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + joke + " TEXT,"
                + ans + " TEXT)";

        db.execSQL(query);

    }

    public void add(String joke1, String ans1) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(joke, joke1);
        values.put(ans, ans1);


        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public ArrayList<Fav_Model> readMemes() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<Fav_Model> memeList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                memeList.add(new Fav_Model(cursor.getString(1),
                        cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return memeList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}