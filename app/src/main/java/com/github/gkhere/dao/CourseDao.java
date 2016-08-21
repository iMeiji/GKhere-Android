package com.github.gkhere.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.gkhere.bean.CourseBean;
import com.github.gkhere.db.CourseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/8/16.
 */
public class CourseDao {

    private Context mContext;

    public CourseDao(Context mContext) {
        this.mContext = mContext;
    }

    public boolean add(String name, String time, String
            timedetail, String teacher, String location,String info) {
        CourseHelper helper = new CourseHelper(mContext, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("time", time);
        values.put("timedetail", timedetail);
        values.put("teacher", teacher);
        values.put("location", location);
        values.put("info", info);
        long id = db.insert("course", null, values);
        db.close();
        return id != -1;
    }

    public List<CourseBean> query(String time) {
        CourseHelper helper = new CourseHelper(mContext, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("course", null, "time=?", new String[]{time},
                null, null, null);
        List<CourseBean> dayCourseList = new ArrayList<>();
        while (cursor.moveToNext()) {
            CourseBean bean = new CourseBean();
            bean.setCourseName(cursor.getString(1));
            bean.setCourseTime(cursor.getString(2));
            bean.setCourseTimeDetail(cursor.getString(3));
            bean.setCourseTeacher(cursor.getString(4));
            bean.setCourseLocation(cursor.getString(5));
            bean.setCourseInfo(cursor.getString(6));
            dayCourseList.add(bean);
        }
        cursor.close();
        db.close();
        return dayCourseList;
    }

    public List<CourseBean> queryAll() {
        CourseHelper helper = new CourseHelper(mContext, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("course", null, null, null, null, null, "time asc");
        List<CourseBean> dayCourseList = new ArrayList<>();
        while (cursor.moveToNext()) {
            CourseBean bean = new CourseBean();
            bean.setCourseName(cursor.getString(1));
            bean.setCourseTime(cursor.getString(2));
            bean.setCourseTimeDetail(cursor.getString(3));
            bean.setCourseTeacher(cursor.getString(4));
            bean.setCourseLocation(cursor.getString(5));
            bean.setCourseInfo(cursor.getString(6));
            dayCourseList.add(bean);
        }
        cursor.close();
        db.close();
        return dayCourseList;
    }

    public boolean deleteAll() {
        CourseHelper helper = new CourseHelper(mContext, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        int delete = db.delete("course", null, null);
        db.close();
        return delete != 0;
    }
}
