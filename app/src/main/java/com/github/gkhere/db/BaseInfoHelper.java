package com.github.gkhere.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Meiji on 2016/10/23.
 */

public class BaseInfoHelper extends SQLiteOpenHelper {

    public BaseInfoHelper(Context context) {
        super(context, "baseinfo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table baseinfo " +
                " (id integer primary key autoincrement," +
                " key varchar(255) ," +
                " value varchar(255)) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
