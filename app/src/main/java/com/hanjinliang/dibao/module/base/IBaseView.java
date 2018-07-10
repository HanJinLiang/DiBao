package com.hanjinliang.dibao.module.base;


import android.view.View;

/**
 * Created by Meiji on 2017/5/7.
 */
public interface IBaseView<T> {
    /**
     * 关联布局
     * @return
     */
    int attachContentView();

    /**
     * 初始化布局
     * @param view
     */
    void initView(View view);

    /**
     * 设置标题
     * @return
     */
    String setTitle();

    /**
     * 设置 presenter
     */
    T setPresenter();

}
