package com.example.cryptocurrencyrates;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//тут мы работаем с БД
public class MyDBHelper extends SQLiteOpenHelper {
    //присваиваем БД имя - константу
    public static final String DB_NAME = "db_user";
    //присваиваем БД версию
    public static final int DB_VERSION = 1;
    //table
    public static final String TABLE_NAME = "rates";
    public static final String KEY_ID = "key_id";
    public static final String PAIR = "pair";
    public static final String HIGH = "high";
    public static final String LOW = "low";
    public static final String VOLUME = "volume";
    public static final String LAST = "last";

    //create table key_id
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PAIR + " text, " +
            HIGH + " real, " +
            LOW + " real, " +
            VOLUME + " real, " +
            LAST + " real);";

    //конструктор класса, отвечающего за соединение и работу с БД
    public MyDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //при создании БД
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    //при обновлении версии БД
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
