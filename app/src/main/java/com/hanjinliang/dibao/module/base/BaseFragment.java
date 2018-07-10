package com.hanjinliang.dibao.module.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by HanJinLiang on 2018-01-12.
 */
public abstract class BaseFragment<T extends IBasePresenter> extends Fragment implements IBaseView<T> {

    protected T presenter;

    /**
     * 初始化数据
     */
    protected abstract void initData() throws NullPointerException;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        presenter=setPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(attachContentView(), container, false);
        ButterKnife.bind(this,view);
        initView(view);
        return view;
    }
    @Override
    public String setTitle() {
        return null;
    }
}
