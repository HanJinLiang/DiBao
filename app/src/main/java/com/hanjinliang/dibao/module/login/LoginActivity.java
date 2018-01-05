package com.hanjinliang.dibao.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.hanjinliang.dibao.module.main.MainActivity;
import com.hanjinliang.dibao.R;
import com.hanjinliang.dibao.module.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_root)
    LinearLayout login_root;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置导航栏透明
        BarUtils.setStatusBarAlpha(this,0);

        //设置模糊背景
        login_root.setBackgroundDrawable(ImageUtils.bitmap2Drawable(ImageUtils.stackBlur(ImageUtils.getBitmap(R.drawable.login_bg),20)));
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.btn_login)
    public void onClick(View view){
        startActivity(new Intent(this,MainActivity.class));
        finish();

//        BmobUser bu = new BmobUser();
//        bu.setUsername("sendi");
//        bu.setPassword("123456");
//        bu.setEmail("sendi@163.com");
//        //注意：不能用save方法进行注册
//        bu.signUp(new SaveListener<Object>() {
//            @Override
//            public void done(Object o, BmobException e) {
//
//            }
//        });
    }


}

