package com.example.coco.test2.changefragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.coco.test2.R;
import com.example.coco.test2.changefragment.HomeFragment;
import com.example.coco.test2.changefragment.MineFragment;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private long mExitTime;
    private HomeFragment home;
    private MineFragment mine;
    private LinearLayout mLin_home,mLin_mine;
    private ImageView mImg_home,mImg_mine;
    private FragmentManager manager;
    private String first;
    private ImageView mImg_FaBu;
    private String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        manager=getSupportFragmentManager();
        initView();
    }
    private void initView() {
        mLin_home=(LinearLayout) findViewById(R.id.mLin_home);
        mLin_mine=(LinearLayout) findViewById(R.id.mLin_mine);
        mImg_home=(ImageView) findViewById(R.id.mImg_home);
        mImg_mine=(ImageView) findViewById(R.id.mImg_mine);
        mImg_FaBu=(ImageView) findViewById(R.id.mImg_FaBu);
        mLin_home.setOnClickListener(this);
        mLin_mine.setOnClickListener(this);
        mImg_FaBu.setOnClickListener(this);

        //默认首页
        FragmentTransaction ft=manager.beginTransaction();
        home=new HomeFragment();
        ft.add(R.id.mFrag_group, home);
        mImg_home.setImageResource(R.mipmap.ic_launcher_round);
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        final FragmentTransaction ft=manager.beginTransaction();
        clear();
        hideFragment();
        switch (id) {
            case R.id.mLin_home:
                mImg_home.setImageResource(R.mipmap.ic_launcher_round);
                ft.show(home);
                ft.commit();
                break;
            case R.id.mLin_mine:
                    mImg_mine.setImageResource(R.mipmap.ic_launcher_round);
                    if (mine==null) {
                        mine=new MineFragment();
                        ft.add(R.id.mFrag_group, mine);
                    }else {
                        ft.show(mine);
                    }
                    ft.commit();
                break;
            case R.id.mImg_FaBu:

                break;
        }
    }

    /*
     * 将所有图片还原的方法
     * */
    private void clear(){
        mImg_home.setImageResource(R.mipmap.ic_launcher);
        mImg_mine.setImageResource(R.mipmap.ic_launcher);
    }

    /*
     * 隐藏碎片的方法
     * */
    private void hideFragment(){
        FragmentTransaction ft=manager.beginTransaction();
        if (home!=null) {
            ft.hide(home);
        }if (mine!=null) {
            ft.hide(mine);
        }
        ft.commit();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
