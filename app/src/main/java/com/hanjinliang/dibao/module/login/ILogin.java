package com.hanjinliang.dibao.module.login;

import com.hanjinliang.dibao.module.base.IBasePresenter;
import com.hanjinliang.dibao.module.base.IBaseView;

/**
 * Created by HanJinLiang on 2018-01-18.
 */

public interface ILogin {
    interface View extends IBaseView<Presenter>{
       void onLogining();

       void loginSuccess();

       void loginFailed(String errorMessage);
    }

    interface Presenter extends IBasePresenter {
        /**
         * 登录
         * @param phone
         * @param password
         */
        void login(String phone,String password);
    }
}
