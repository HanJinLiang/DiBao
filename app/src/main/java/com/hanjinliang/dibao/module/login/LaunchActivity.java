package com.hanjinliang.dibao.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.dibao.R;
import com.hanjinliang.dibao.module.base.BaseActivity;
import com.hanjinliang.dibao.module.main.MainActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 启动页
 */
public class LaunchActivity extends BaseActivity {
    @BindView(R.id.imageLaunch)
    ImageView imageLaunch;

    @Override
    public boolean isSupportToolBar() {
        return false;
    }

    @Override
    public int attachContentView() {
        return R.layout.activity_launch;
    }

    @Override
    public void initView(View view) {
        //设置导航栏透明
        BarUtils.setStatusBarAlpha(this,0);
        //设置模糊背景
        imageLaunch.setBackgroundDrawable(ImageUtils.bitmap2Drawable(ImageUtils.stackBlur(ImageUtils.getBitmap(R.drawable.login_bg),20)));

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if(BmobUser.getCurrentUser()!=null){
                    launchToActivity(MainActivity.class);
                }else{
                    launchToActivity(LoginActivity.class);
                }
            }
        },1000);
    }
    private void launchToActivity(Class<?> cls){
        startActivity(new Intent(LaunchActivity.this,cls));
        finish();
    }


    @Override
    public String setTitle() {
        return null;
    }

    @Override
    public Object setPresenter() {
        return null;
    }

}

