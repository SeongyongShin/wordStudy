package com.example.wordstudy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "WordData";

    private static final String TABLE_WORD = "Word";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_MEAN = "mean";
    private static final String COUNT = "count";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //onCreate(this.getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("DB 생성 중");
        String CREATE_TABLE_WORD =
                "CREATE TABLE " + TABLE_WORD + "(" +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        KEY_NAME + " VARCHAR(50) NOT NULL," +
                        KEY_MEAN + " VARCHAR(100) NOT NULL," +
                        COUNT +
                        ");";
        try{
            db.execSQL(CREATE_TABLE_WORD);
        }catch (Exception e){
            System.out.println("테이블 생성 실패");
            e.printStackTrace();
        }finally {
            System.out.println("테이블 생성 완료");
        }
    }
    public void dropTable(SQLiteDatabase db){
        String DROP_TABLE_WORD = "DROP TABLE IF EXISTS " + TABLE_WORD;
        try {
            db.execSQL(DROP_TABLE_WORD);
        }catch (Exception e){
            System.out.println("테이블 삭제 실패");
            e.printStackTrace();
        }finally {
            System.out.println("테이블 삭제 완료");
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE_WORD = "DROP TABLE IF EXISTS " + TABLE_WORD;
        db.execSQL(DROP_TABLE_WORD);

        onCreate(db);
    }

    public void add(SQLiteDatabase db, ArrayList<WordData> list) {

        ContentValues values = new ContentValues();
        for(int i=0;i<list.size();i++){
            values.put(KEY_NAME,list.get(i).getName());
            values.put(KEY_MEAN,list.get(i).getMean());
            db.insert(TABLE_WORD, null, values);
        }
        db.close();
    }

    public ArrayList<WordData> getAll(SQLiteDatabase db) {
        ArrayList<WordData> wordList = new ArrayList<>();

        String SELECT_ALL = "SELECT * FROM " + TABLE_WORD;

        Cursor cursor = db.rawQuery(SELECT_ALL, null);

        if (cursor.moveToFirst()) {
            do {
                WordData wordData = new WordData();
                wordData.setId(Integer.parseInt(cursor.getString(0)));
                wordData.setName(cursor.getString(1));
                wordData.setMean(cursor.getString(2));
                wordList.add(wordData);
            } while (cursor.moveToNext());
        }

        return wordList;
    }

}
