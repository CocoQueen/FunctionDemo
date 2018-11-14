package com.example.coco.test2.contact;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.Toast;

import com.example.coco.test2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * https://blog.csdn.net/chenliguan/article/details/48316383
 */
public class ContactActivity extends Activity {


    private ListView lvContact;

    /**
     * 联系人组
     */
    private static String[] PHONES_PROJECTION;

    /**
     * 联系人adapter
     */
    private ContactAdapter mContactAdapter;

    /**
     * Curosr
     */
    private Cursor mPhoneCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initView();
    }

    private void initView() {
        lvContact = findViewById(R.id.lv_contact);
        Button button = findViewById(R.id.mBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<PhoneInfo> list = getPhoneNumberFromMobile(ContactActivity.this);
                for (int i = 0; i < list.size(); i++) {
                    String name = list.get(i).getName();
                    String number = list.get(i).getNumber();
                    Toast.makeText(ContactActivity.this, name+number, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    List<PhoneInfo> list;

    public List<PhoneInfo> getPhoneNumberFromMobile(Context context) {
        // TODO Auto-generated constructor stub
        list = new ArrayList<PhoneInfo>();
        Cursor cursor = context.getContentResolver().query(Phone.CONTENT_URI,
                new String[]{"display_name", "sort_key", "contact_id",
                        "data1"}, null, null, null);
//        moveToNext方法返回的是一个boolean类型的数据
        while (cursor.moveToNext()) {
            //读取通讯录的姓名
            String name = cursor.getString(cursor
                    .getColumnIndex(Phone.DISPLAY_NAME));
            //读取通讯录的号码
            String number = cursor.getString(cursor
                    .getColumnIndex(Phone.NUMBER));
            int Id = cursor.getInt(cursor.getColumnIndex(Phone.CONTACT_ID));
            String Sortkey = getSortkey(cursor.getString(1));
            PhoneInfo phoneInfo = new PhoneInfo(name, number, Sortkey, Id);
            list.add(phoneInfo);
        }
        cursor.close();
        return list;
    }

    private static String getSortkey(String sortKeyString) {
        String key = sortKeyString.substring(0, 1).toUpperCase();
        if (key.matches("[A-Z]")) {
            return key;
        } else
            return "#";
    }
}
