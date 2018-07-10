package com.hanjinliang.dibao.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
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

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 注册
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.login_root)
    RelativeLayout login_root;
    @BindView(R.id.phone)
    EditText et_phone;
    @BindView(R.id.password)
    EditText et_password;
    @BindView(R.id.userName)
    EditText et_userName;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;


    private void launchToMain(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public boolean isSupportToolBar() {
        return false;
    }


    @OnClick(R.id.btn_register)
    public void onClick(View view){
        register();
    }

    private void register() {
        String phone=et_phone.getText().toString().trim();
        String userName=et_userName.getText().toString().trim();
        String password=et_password.getText().toString().trim();
        if(!RegexUtils.isMobileSimple(phone)){
            ToastUtils.showShort("手机号格式不对");
            return;
        }
        if(TextUtils.isEmpty(userName)){
            ToastUtils.showShort("用户名不能为空");
            return;
        }
        if(TextUtils.isEmpty(password)){
            ToastUtils.showShort("密码不能为空");
            return;
        }
        BmobUser bu = new BmobUser();
        bu.setMobilePhoneNumber(phone);
        bu.setUsername(userName);
        bu.setPassword(password);
        //注意：不能用save方法进行注册
        bu.signUp(new SaveListener<Object>() {
            @Override
            public void done(Object o, BmobException e) {
                if(e==null){
                    ToastUtils.showShort("注册成功");
                    launchToMain();
                }else{
                    LogUtils.e(e.getMessage());
                }
            }
        });
    }


    @Override
    public int attachContentView() {
        return  R.layout.activity_login;
    }

    @Override
    public void initView(View view) {
        //设置导航栏透明
        BarUtils.setStatusBarAlpha(this,0);

        //设置模糊背景
        login_root.setBackgroundDrawable(ImageUtils.bitmap2Drawable(ImageUtils.stackBlur(ImageUtils.getBitmap(R.drawable.login_bg),20)));
        et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    register();
                    return true;
                }
                return false;
            }
        });
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

