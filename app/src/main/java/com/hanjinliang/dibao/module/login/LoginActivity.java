package com.hanjinliang.dibao.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.dibao.module.main.MainActivity;
import com.hanjinliang.dibao.R;
import com.hanjinliang.dibao.module.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_root)
    RelativeLayout login_root;
    @BindView(R.id.phone)
    EditText et_phone;
    @BindView(R.id.password)
    EditText et_password;

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
        if(BmobUser.getCurrentUser()!=null){
            launchToMain();
        }

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

    private void launchToMain(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public boolean isSupportToolBar() {
        return false;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public String getTitleContent() {
        return null;
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

        BmobQuery<BmobUser> query=new BmobQuery<>();
        query.addWhereEqualTo("mobilePhoneNumber",phone);
        query.addWhereEqualTo("password",password);
        query.findObjects(new FindListener<BmobUser>() {
            @Override
            public void done(List<BmobUser> list, BmobException e) {
                if(e==null){
                    if(list!=null&&list.size()>0){
                        BmobUser bmobUser=list.get(0);
                        bmobUser.setPassword(password);
                        bmobUser.login(new SaveListener<Object>() {
                            @Override
                            public void done(Object o, BmobException e) {
                                if(e==null){
                                    ToastUtils.showShort("登录成功");
                                    launchToMain();
                                }else{
                                    ToastUtils.showShort(e.getMessage());
                                    LogUtils.e();
                                }
                            }
                        });
                    }else{
                        ToastUtils.showShort("没有找到对应用户");
                    }
                }else{
                    ToastUtils.showShort(e.getMessage());
                }

            }
        });
    }


}

