package com.example.test4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DAO {


    public void insertContactInfo(Context context, ContactUser contactUser) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",contactUser.getName());
        values.put("tel",contactUser.getTel());
        db.insert("contactInfo", null, values);
        Log.d("TAG: ", contactUser.toString() + "数据插入成功");
    }

    public void deleteContactUser(Context context, Integer id) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "delete from contactInfo where _id=" + id;
        db.execSQL(sql);
        Log.d("TAG: ", id + "删除成功");
    }

    public void updateContactUser(Context context, ContactUser contactUser) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql =
                "update contactInfo set name='"+contactUser.getName()+"',tel='"+contactUser.getTel()+"' where _id="+contactUser.get_id();
        db.execSQL(sql);
        Log.d("TAG: ", contactUser.toString() + "更新成功");
    }

    public List<ContactUser> getContactUsers(Context context) {
        List<ContactUser> userList = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from contactInfo";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            ContactUser contactUser = new ContactUser(cursor.getString(1),
                    cursor.getString(2));
//            contactUser.set_id(Integer.parseInt(cursor.getColumnName(0)));
//            contactUser.setName(cursor.getColumnName(1));
//            contactUser.setTel(cursor.getColumnName(2));
            userList.add(contactUser);
        }
        cursor.close();
        Log.d("TAG: ", "数据查询成功-"+userList.toString());
        return userList;
    }

    public Integer getId(Context context, ContactUser contactUser) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select _id from contactInfo where name='"+contactUser.getName()+"' and " +
                "tel='"+contactUser.getTel()+"'";
        Cursor cursor = db.rawQuery(sql, null);
        int id = 0;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }
}
