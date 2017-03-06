package com.example.lenovo.myfinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLController {

    private static final String TAG = "myLogs";
    private DBhelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;
    String selection = null;
    public SQLController(Context c) {
        ourcontext = c;
    }

    public SQLController open() throws SQLException {
        dbhelper = new DBhelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbhelper.close();
    }

    public void insertData(String name,String surname,String tel,String mail,String adress) {
        Log.d(TAG,"--------insert-----");
        ContentValues cv = new ContentValues();
        cv.put(DBhelper.MEMBER_NAME, name);
        cv.put(DBhelper.MEMBER_SURNAME,surname);
        cv.put(DBhelper.MEMBER_TEL,tel);
        cv.put(DBhelper.MEMBER_MAIL,mail);
        cv.put(DBhelper.MEMBER_ADRESS,adress);

        database.insert(DBhelper.TABLE_MEMBER, null, cv);
    }

    public Cursor readData() {
        String[] allColumns = new String[] { DBhelper.MEMBER_ID,
                DBhelper.MEMBER_NAME,DBhelper.MEMBER_SURNAME };
        Cursor c = database.query(DBhelper.TABLE_MEMBER, allColumns, null,
                null, null, null, "name ASC");
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor readDataSearch(String name){

        String[]allColumns = new String[]{DBhelper.MEMBER_ID,DBhelper.MEMBER_NAME,DBhelper.MEMBER_SURNAME};
        //selection = "instr(name,?)";
        selection = "instr(LOWER(name),LOWER(?)) OR instr(LOWER(surname),LOWER(?))";
        Cursor cursor = database.query(DBhelper.TABLE_MEMBER,allColumns,selection,new String[]{name,name},null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int updateData(long memberID, String memberName,String memberSurname,String memberTel,String mail,String adress) {
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(DBhelper.MEMBER_NAME, memberName);
        cvUpdate.put(DBhelper.MEMBER_SURNAME,memberSurname);
        cvUpdate.put(DBhelper.MEMBER_TEL,memberTel);
        cvUpdate.put(DBhelper.MEMBER_MAIL,mail);
        cvUpdate.put(DBhelper.MEMBER_ADRESS,adress);
        return  database.update(DBhelper.TABLE_MEMBER, cvUpdate,
                DBhelper.MEMBER_ID + " = " + memberID, null);
    }

    public void deleteData(long memberID) {
        database.delete(DBhelper.TABLE_MEMBER, DBhelper.MEMBER_ID + "="
                + memberID, null);
    }

}// outer class end
