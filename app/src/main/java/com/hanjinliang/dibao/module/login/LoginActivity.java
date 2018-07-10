package com.hanjinliang.dibao.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SnackbarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.dibao.module.main.MainActivity;
import com.hanjinliang.dibao.R;
import com.hanjinliang.dibao.module.base.BaseActivity;


import butterknife.BindView;
import butterknife.OnClick;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity<ILogin.Presenter> implements ILogin.View {
    @BindView(R.id.login_root)
    RelativeLayout login_root;
    @BindView(R.id.phone)
    EditText et_phone;
    @BindView(R.id.password)
    EditText et_password;

    @Override
    public boolean isSwipeBack() {
        return false;
    }

    @Override
    public boolean isSupportToolBar() {
        return false;
    }

    @Override
    public int attachContentView() {
        return R.layout.activity_login;
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
                    login();
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
    public ILogin.Presenter setPresenter() {
        return new LoginPresenter(this);
    }

    @OnClick({R.id.btn_login,R.id.register})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }

    }

    private void login() {
        String phone=et_phone.getText().toString().trim();
        final String password=et_password.getText().toString().trim();
        if(!RegexUtils.isMobileSimple(phone)){
            ToastUtils.showShort("手机号格式不对");
            return;
        }
        if(TextUtils.isEmpty(password)){
            ToastUtils.showShort("密码不能为空");
            return;
        }
        presenter.login(phone,password);
    }


    @Override
    public void onLogining() {
        showDialog("登录中...");
    }

    @Override
    public void loginSuccess() {
        dismissDialog();
        SnackbarUtils.with(getWindow().getDecorView()).setMessage("登录成功").show();
        launchToMain();
    }
    private void launchToMain(){
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }
    @Override
    public void loginFailed(String errorMessage) {
        dismissDialog();
        SnackbarUtils.with(getWindow().getDecorView()).setMessage(errorMessage).showError();
    }
}

