package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.PropertyResourceBundle;
import java.util.concurrent.ExecutionException;

import javax.security.auth.PrivateCredentialPermission;

public class SqliteAdapter {

    SqliteHelper helper;

    public SqliteAdapter(Context context) {
        helper = new SqliteHelper(context);

    }

    static class SqliteHelper extends SQLiteOpenHelper {
        private Context context;
        private static final String DATABASE_NAME = "database";
        private static final String TABLE_NAME = "database";
        private static final int DATABASE_VERSION = 1;
        private static final String UID = "_id";
        private static final String FNAME = "first";
        private static final String LNAME = "last";

        private final String CREATE_TABLE = String.format(" CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT ,%s VARCHAR(255));", TABLE_NAME, UID, FNAME);

        private final String DROP_TABLE = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);

        public SqliteHelper(Context context) {
            super(context, TABLE_NAME, null, DATABASE_VERSION);
            this.context = context;
            Toast.makeText(context, "constructor called", Toast.LENGTH_LONG).show();
            Log.i("SQLite Helper", "on create called");

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
                Toast.makeText(context, "on create called", Toast.LENGTH_LONG).show();

            } catch (SQLException e) {
                Toast.makeText(context, "on create error", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
                Toast.makeText(context, "on upgrade called", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(context, "on upgrade error", Toast.LENGTH_LONG).show();
            }
        }
    }

    public long insert(String first, String last) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SqliteHelper.FNAME, first);
        contentValues.put(SqliteHelper.LNAME, last);
        long id = db.insert(SqliteHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public String getalldata() {
        SQLiteDatabase dp = helper.getWritableDatabase();
        String[] colums = {SqliteHelper.UID, SqliteHelper.FNAME, SqliteHelper.LNAME};
        Cursor cursor = dp.query(SqliteHelper.TABLE_NAME, colums, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int cid = cursor.getInt(0);
            String fname = cursor.getString(1);
            String lname = cursor.getString(2);
            buffer.append(cid + " " + fname + " " + lname + "\n");
        }
        return buffer.toString();
    }

    public String search(String name) {
        SQLiteDatabase dp = helper.getWritableDatabase();
        String[] colums = {SqliteHelper.FNAME, SqliteHelper.LNAME};
        Cursor cursor = dp.query(SqliteHelper.TABLE_NAME, colums, SqliteHelper.FNAME + " = '" + name + "' ", null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int index1 = cursor.getColumnIndex(SqliteHelper.FNAME);
            int index2 = cursor.getColumnIndex(SqliteHelper.LNAME);
            String fname = cursor.getString(index1);
            String lname = cursor.getString(index2);
            buffer.append(fname + " " + lname + "\n");
        }
        return buffer.toString();
    }
//---------------------- another way to search that return id --------------------------------------
//    public String search(String fname, String lname) {
//        SQLiteDatabase dp = helper.getWritableDatabase();
//        String[] colums = {SqliteHelper.UID};
//        String[] selectionargs = {fname,lname};
//        Cursor cursor = dp.query(SqliteHelper.TABLE_NAME, colums, SqliteHelper.FNAME + " =? AND" + SqliteHelper.LNAME + "=?",
//                selectionargs,null, null, null, null);
//
//        StringBuffer buffer = new StringBuffer();
//        while (cursor.moveToNext()) {
//            int index0=cursor.getColumnIndex(SqliteHelper.UID);
//            int personid = cursor.getInt(index0);
//            buffer.append(personid + "\n");
//        }
//        return buffer.toString();
//    }

    public int updatename(String oldfirst , String newfirst){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SqliteHelper.FNAME,newfirst);
        //contentValues.put(SqliteHelper.LNAME,newlast);
        String[]args = {oldfirst,};
        int count = db.update(SqliteHelper.TABLE_NAME,contentValues,SqliteHelper.FNAME + " =? ",args);
        return count;

    }
    public int deleterow(String name){
        SQLiteDatabase db = helper.getWritableDatabase();
        String []args={name};
        int count = db.delete(SqliteHelper.TABLE_NAME,SqliteHelper.FNAME+"=?",args);
        return count;

    }

}
