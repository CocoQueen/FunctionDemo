package com.example.coco.test2.autotext;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FilterQueryProvider;
import android.widget.TextView;

import com.example.coco.test2.R;

/**
 * https://blog.csdn.net/yjp19871013/article/details/62049125
 *
 * 仿百度搜索提示自动补全
 */

public class AutoSearchActivity extends AppCompatActivity {
    //一定要使用一个成员变量，不然DbHelper内存回收会导致DB无法使用
    private DbHelper mDbHelper;
    private SQLiteDatabase mDb;
    private AutoCompleteTextView mAutoCompleteTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_search);
        mAutoCompleteTextView = findViewById(R.id.auto_complete_text_view);

        //实际上设置的是弹出的列表的OnItemClickListener
        //另外千万注意，不要使用OnItemSelectedListener
        //看源码中该监听器虽然可以设置，但是没有用到
        mAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //这里设置文本值，否则默认显示赋值的文本是Cursor的toString()
                TextView textView = (TextView) view;
                mAutoCompleteTextView.setText(textView.getText());
            }
        });

        AutoCompleteAdapter adapter = new AutoCompleteAdapter(this);

        //这里设置FilterQueryProvider可以自定义自己的Cursor创建逻辑
        //而不是使用默认设置给Adapter的Cursor，实现动态处理
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                if (constraint == null || constraint.length() == 0) {
                    return null;
                }

                //模糊查找，千万加入_id列，否则无法正常使用
                return mDb.rawQuery("SELECT _id," + DbHelper.COL_SCHOOL_NAME
                        + " FROM " + DbHelper.SCHOOLS_TABLE_NAME
                        + " WHERE " + DbHelper.COL_SCHOOL_NAME
                        + " LIKE \'%" + constraint.toString() +"%\'", null);
            }
        });
        mAutoCompleteTextView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDbHelper = new DbHelper(this);

        //使用getWritableDatabase，如果表不存在会自动创建
        mDb = mDbHelper.getWritableDatabase();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //资源回收
        mDb.close();
        mDbHelper.close();
    }
}
