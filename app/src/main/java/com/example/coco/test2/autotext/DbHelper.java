package com.example.coco.test2.autotext;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Coco on 2018/11/13
 */
public class DbHelper extends SQLiteOpenHelper {

    //大学表
    public static final String SCHOOLS_TABLE_NAME = "table_schools";
    public static final String COL_SCHOOL_NAME = "school_name";

    private static final String DB_NAME = "auto_complete_text_view_app_db";
    private static final int DB_VERSION = 1;

    private static final String STRING_CREATE_SCHOOL_TABLE =
            "CREATE TABLE " + SCHOOLS_TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_SCHOOL_NAME + " TEXT);";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STRING_CREATE_SCHOOL_TABLE);

        //伪造一些数据
        ContentValues cv = new ContentValues(1);
        cv.put(COL_SCHOOL_NAME, "大连理工大学");
        db.insert(SCHOOLS_TABLE_NAME, COL_SCHOOL_NAME, cv);

        cv.put(COL_SCHOOL_NAME, "太原理工大学");
        db.insert(SCHOOLS_TABLE_NAME, COL_SCHOOL_NAME, cv);

        cv.put(COL_SCHOOL_NAME, "天津理工大学");
        db.insert(SCHOOLS_TABLE_NAME, COL_SCHOOL_NAME, cv);

        cv.put(COL_SCHOOL_NAME, "河北工业大学");
        db.insert(SCHOOLS_TABLE_NAME, COL_SCHOOL_NAME, cv);

        cv.put(COL_SCHOOL_NAME, "天津大学");
        db.insert(SCHOOLS_TABLE_NAME, COL_SCHOOL_NAME, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STRING_CREATE_SCHOOL_TABLE);
    }

}
