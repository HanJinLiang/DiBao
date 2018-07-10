package com.hanjinliang.dibao.module.login;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by HanJinLiang on 2018-01-18.
 */

public class LoginPresenter implements ILogin.Presenter {
    ILogin.View view;
    public LoginPresenter(ILogin.View view){
        this.view=view;
    }

    @Override
    public void login(String phone,final String password) {
        view.onLogining();
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
                                    view.loginSuccess();
                                }else{
                                    view.loginFailed(e.getMessage());
                                }
                            }
                        });
                    }else{
                        view.loginFailed("没有找到对应用户");
                    }
                }else{
                    view.loginFailed(e.getMessage());
                }

            }
        });
    }

    @Override
    public void doRefresh() {

    }
}
