package com.example.test4;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, "test_4.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSql = "create table contactInfo(_id integer primary key autoincrement,name " +
                "varchar, tel varchar)";
        db.execSQL(createSql);
        Log.d("TAG: ", "数据库创建成功");
        db.execSQL("insert into contactInfo(name,tel) values('张三','13255698875')");
        db.execSQL("insert into contactInfo(name,tel) values('李四','11325564589')");
        db.execSQL("insert into contactInfo(name,tel) values('王五','15633253145')");
        Log.d("TAG: ", "数据初始化成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void getContactUsers(Context context){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String selectSql = "select * from contactInfo";
        Cursor cursor = database.rawQuery(selectSql, null);
        while (cursor.moveToNext()) {
            Log.d("id=", cursor.getColumnName(0));
            Log.d("name=", cursor.getColumnName(1));
            Log.d("tel=", cursor.getColumnName(2));
        }
        cursor.close();
    }
}
