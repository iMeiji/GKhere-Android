package com.github.gkhere.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Meiji on 2016/8/16.
 */
public class CourseHelper extends SQLiteOpenHelper {

    public CourseHelper(Context context, int version) {
        super(context, "course.db", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table course" +
                "(id integer primary key autoincrement," +
                "name varchar(100)," +
                "time varchar(50)," +
                "timedetail varchar(200)," +
                "teacher varchar(50)," +
                "location varchar(200)," +
                "info varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
