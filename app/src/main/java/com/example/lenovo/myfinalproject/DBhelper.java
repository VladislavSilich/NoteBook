package com.example.lenovo.myfinalproject;

/**
 * Created by Lenovo on 25.01.2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBhelper extends SQLiteOpenHelper {
    private static final String TAG = "myLogs";
    // TABLE INFORMATTION
    public static final String TABLE_MEMBER = "member";
    public static final String MEMBER_ID = "_id";
    public static final String MEMBER_NAME = "name";
    public static final String MEMBER_SURNAME = "surname";
    public static final String MEMBER_TEL = "telephone";
    public static final String MEMBER_MAIL = "mail";
    public static final String MEMBER_ADRESS = "adress";


    // DATABASE INFORMATION
    static final String DB_NAME = "MEMBER.DB";
    static final int DB_VERSION = 11;

    // TABLE CREATION STATEMENT
    private static final String CREATE_TABLE = "create table "
            + TABLE_MEMBER + "(" + MEMBER_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MEMBER_NAME + " TEXT NOT NULL, " + MEMBER_SURNAME + " TEXT NOT NULL, "
            + MEMBER_TEL + " TEXT NOT NULL, " + MEMBER_MAIL + " TEXT NOT NULL, " + MEMBER_ADRESS + " TEXT NOT NULL);";

    public DBhelper(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"-----------onCreate");
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        Log.d(TAG,"-----------onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER);
        onCreate(db);
    }
}