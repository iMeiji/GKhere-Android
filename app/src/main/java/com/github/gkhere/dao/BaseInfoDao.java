package com.github.gkhere.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.gkhere.db.BaseInfoHelper;


/**
 * Created by Meiji on 2016/10/23.
 */

public class BaseInfoDao {

    private Context mContext;

    public BaseInfoDao(Context mContext) {
        this.mContext = mContext;
    }

    public boolean add(String key, String value) {
        BaseInfoHelper helper = new BaseInfoHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("key", key);
        values.put("value", value);
        long id = db.insert("baseinfo", null, values);
        return id != -1;
    }

    public String query(String key) {
        BaseInfoHelper helper = new BaseInfoHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query("baseinfo", new String[]{"value"}, "key=?", new String[]{key}, null, null, null);
        String value = null;
        if (cursor.moveToNext()) {
            value = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return value;
    }


}
