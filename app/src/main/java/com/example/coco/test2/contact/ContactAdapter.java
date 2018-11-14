package com.example.coco.test2.contact;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coco.test2.R;

import java.io.InputStream;

/**
 * Created by Coco on 2018/11/13
 */
public class ContactAdapter extends CursorAdapter {
    private Context mContext;

    /**
     * 联系人显示名称
     */
    private static final int PHONES_DISPLAY_NAME_INDEX = 1;

    /**
     * 电话号码
     **/
    private static final int PHONES_NUMBER_INDEX = 2;

    /**
     * 头像ID
     */
    private static final int PHONES_PHOTO_ID_INDEX = 3;

    /**
     * 联系人的ID
     */
    private static final int PHONES_CONTACT_ID_INDEX = 4;


    public ContactAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        this.mContext = context;
    }

    @Override
    public int getCount() {

        if (mDataValid && mCursor != null) {
            return mCursor.getCount();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {

        if (mDataValid && mCursor != null) {
            mCursor.moveToPosition(position);
            return mCursor;
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {

        if (mDataValid && mCursor != null) {
            if (mCursor.moveToPosition(position)) {
                return mCursor.getLong(mRowIDColumn);
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        if (convertView == null) {
            view = newView(mContext, mCursor, parent);
        } else {
            view = convertView;
        }
        return view;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        ViewHolder viewholder = null;
        View view = LayoutInflater.from(mContext).inflate( R.layout.item_contact, null);
        viewholder = new ViewHolder(view);
        view.setTag(viewholder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        /**
         * 获取当前position
         * final int position=cursor.getPosition();
         */

        ViewHolder viewholder = (ViewHolder) view.getTag();

        /**
         * 得到联系人名称
         */
        viewholder.name.setText(cursor.getString(PHONES_DISPLAY_NAME_INDEX) + "");

        /**
         * 得到手机号码
         */
        viewholder.number.setText(cursor.getString(PHONES_NUMBER_INDEX) + "");

        /**
         * 得到联系人头像
         */
        // 得到联系人ID
        Long contactid = cursor
                .getLong(PHONES_CONTACT_ID_INDEX);
        // 得到联系人头像ID
        Long photoid = cursor.getLong(PHONES_PHOTO_ID_INDEX);
        // 得到联系人头像Bitamp
        Bitmap contactPhoto = null;
        // photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
        if (photoid > 0) {
            Uri uri = ContentUris.withAppendedId(
                    ContactsContract.Contacts.CONTENT_URI,
                    contactid);
            InputStream input = ContactsContract.Contacts
                    .openContactPhotoInputStream(mContext.getContentResolver(), uri);
            contactPhoto = BitmapFactory.decodeStream(input);
        } else {
            contactPhoto = BitmapFactory.decodeResource(
                    mContext.getResources(), R.mipmap.ic_launcher);
        }

        viewholder.ivHead.setImageBitmap(contactPhoto);
    }



    class ViewHolder {
        public View rootView;
        public ImageView ivHead;
        public TextView name;
        public TextView number;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ivHead = rootView.findViewById(R.id.iv_head);
            this.name = rootView.findViewById(R.id.name);
            this.number = rootView.findViewById(R.id.number);
        }

    }
}
