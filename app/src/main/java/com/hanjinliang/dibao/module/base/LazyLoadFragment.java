package com.hanjinliang.dibao.module.base;

import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;

/**
 * Created by HanJinLiang on 2018-01-11.
 * 懒加载Fragment
 */
public abstract class LazyLoadFragment<T extends IBasePresenter> extends BaseFragment<T>{
    protected boolean isViewInitiated;//View是否初始化
    protected boolean isVisibleToUser;//用户是否可见
    protected boolean isDataInitiated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public abstract void fetchData();

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        LogUtils.e("prepareFetchData");
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }
}
