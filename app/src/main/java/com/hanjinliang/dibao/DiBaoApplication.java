package com.hanjinliang.dibao;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

import cn.bmob.v3.Bmob;

/**
 * Created by HanJinLiang on 2018-01-04.
 */

public class DiBaoApplication extends Application {
    public static Application mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication=this;

        //第一：默认初始化
        Bmob.initialize(this, "1ff8acc277e93b8575f803213e6cb0d6");

        // init it in the function of onCreate in ur Application
        Utils.init(mApplication);
    }
}
