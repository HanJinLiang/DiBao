package com.hanjinliang.dibao.module.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.classic.common.MultipleStatusView;
import com.hanjinliang.dibao.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;


import butterknife.BindView;

public abstract class BaseListFragment<T extends IBasePresenter> extends LazyLoadFragment<T> implements IBaseListView<T>, OnRefreshLoadmoreListener {

    @BindView(R.id.refreshLayout)
    public SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recyclerView)
    public RecyclerView mRecyclerView;
    @BindView(R.id.multiple_status_view)
    MultipleStatusView mMultipleStatusView;

    public static final String TAG = "BaseListFragment";

    @Override
    public int attachContentView() {
        return R.layout.fragment_list;
    }


    @Override
    public void initView(View view) {
        mSmartRefreshLayout.setOnRefreshLoadmoreListener(this);
        mMultipleStatusView.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowLoading();
            }
        });
    }

    @Override
    public void onShowLoading() {
        mMultipleStatusView.showContent();
        mSmartRefreshLayout.autoRefresh();
    }

    @Override
    public void onHideLoading() {
        mSmartRefreshLayout.finishRefresh();
    }

    @Override
    public void fetchData() {
        mSmartRefreshLayout.autoRefresh(100);
    }

    @Override
    public void onShowNetError() {
        if(mSmartRefreshLayout.isRefreshing()) {
            mSmartRefreshLayout.finishRefresh(false);
        }
        if(mSmartRefreshLayout.isLoading()) {
            mSmartRefreshLayout.finishLoadmore(false);
        }
        mMultipleStatusView.showNoNetwork();
    }

    @Override
    public void onShowNoMore() {
        mMultipleStatusView.showContent();
        ToastUtils.showShort("没有更多数据");
        mSmartRefreshLayout.finishLoadmoreWithNoMoreData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {

    }


    @Override
    public void onHideLoadingMore() {
        mSmartRefreshLayout.finishLoadmore(true);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mMultipleStatusView.showContent();
        presenter.doRefresh();
    }

    @Override
    public void onShowDataEmpty() {
        mMultipleStatusView.showEmpty();
    }
}