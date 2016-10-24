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
                "course varchar(100)," +
                "day varchar(50)," +
                "timeinfo varchar(200)," +
                "week varchar(50)," +
                "teacher varchar(200)," +
                "location varchar(200)," +
                "extra varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
