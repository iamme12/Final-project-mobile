package org.daou.personaldashboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DashboardSQLiteOpenHelperr  extends SQLiteOpenHelper {
    public static final String DB_NAME = "SelfHelp";
    public static final int DB_VERSION = 1;
    int i=0;

    public DashboardSQLiteOpenHelperr(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USERS (" +
                "_un STRING PRIMARY KEY AUTOINCREMENT," +
                "UN TEXT," +
                "Email TEXT," +
                "Bank   TEXT," +
                "PASSWORD TEXT);");

        db.execSQL("CREATE TABLE NOTES (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NOTES TEXT);");

    }


    public void insertNotes(String notes) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("COMMENT", notes);
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert("POSTS", null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public Boolean insertData(String username , String email, String bank, String pass) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UN", username );
        contentValues.put("Email", email );
        contentValues.put("BANK", bank );
        contentValues.put("PASSWORD", pass);
        long result=db.insert("USERS", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from USERS where UN = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }


    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from USERS where ID = ? and PASSWORD = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }


    public StringBuilder viewAll(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from NOTES", null);
        StringBuilder buffer=new StringBuilder();
        while(res.moveToNext()){
            i++;
            buffer.append("Your Notes:\n" + res.getString(1) + "\n");
        }
        return buffer;
    }

}
