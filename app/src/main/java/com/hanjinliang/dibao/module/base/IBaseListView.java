package com.hanjinliang.dibao.module.base;

import java.util.List;

/**
 * Created by Meiji on 2017/7/5.
 */

public interface IBaseListView<T> extends IBaseView<T> {

    /**
     * 显示加载动画
     */
    void onShowLoading();

    /**
     * 隐藏加载
     */
    void onHideLoading();

    /**
     * 显示网络错误
     */
    void onShowNetError();


    /**
     * 设置适配器
     */
    void onSetAdapter(List<?> list);

    /**
     * 加载完毕--没有更多数据
     */
    void onShowNoMore();

    /**
     * 加载完毕
     */
    void onHideLoadingMore();

    /**
     * 空数据
     */
    void onShowDataEmpty();
}
