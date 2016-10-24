package com.github.gkhere.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.gkhere.bean.CourseBean;
import com.github.gkhere.db.CourseHelper;

import java.util.ArrayList;
import java.util.List;

import static com.github.gkhere.bean.CourseBean.COURSEBEAN_course;
import static com.github.gkhere.bean.CourseBean.COURSEBEAN_day;
import static com.github.gkhere.bean.CourseBean.COURSEBEAN_extra;
import static com.github.gkhere.bean.CourseBean.COURSEBEAN_location;
import static com.github.gkhere.bean.CourseBean.COURSEBEAN_teacher;
import static com.github.gkhere.bean.CourseBean.COURSEBEAN_timeinfo;
import static com.github.gkhere.bean.CourseBean.COURSEBEAN_week;

/**
 * Created by Meiji on 2016/8/16.
 */
public class CourseDao {

    private Context mContext;

    public CourseDao(Context mContext) {
        this.mContext = mContext;
    }

    public boolean add(String course,
                       String day,
                       String timeinfo,
                       String week,
                       String teacher,
                       String location,
                       String extra) {
        CourseHelper helper = new CourseHelper(mContext, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COURSEBEAN_course, course);
        values.put(COURSEBEAN_day, day);
        values.put(COURSEBEAN_timeinfo, timeinfo);
        values.put(COURSEBEAN_week, week);
        values.put(COURSEBEAN_teacher, teacher);
        values.put(COURSEBEAN_location, location);
        values.put(COURSEBEAN_extra, extra);
        long id = db.insert("course", null, values);
        db.close();
        return id != -1;
    }

    /**
     * 按星期查询
     *
     * @param day
     * @return
     */
    public List<CourseBean> query(String day) {
        CourseHelper helper = new CourseHelper(mContext, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("course", null, "day=?", new String[]{day},
                null, null, null);
        List<CourseBean> dayCourseList = new ArrayList<>();
        while (cursor.moveToNext()) {
            CourseBean bean = new CourseBean();
            bean.setCourse(cursor.getString(1));
            bean.setDay(cursor.getString(2));
            bean.setTimeinfo(cursor.getString(3));
            bean.setWeek(cursor.getString(4));
            bean.setTeacher(cursor.getString(5));
            bean.setLocation(cursor.getString(6));
            bean.setExtra(cursor.getString(7));
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
            bean.setCourse(cursor.getString(1));
            bean.setDay(cursor.getString(2));
            bean.setTimeinfo(cursor.getString(3));
            bean.setWeek(cursor.getString(4));
            bean.setTeacher(cursor.getString(5));
            bean.setLocation(cursor.getString(6));
            bean.setExtra(cursor.getString(7));
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
