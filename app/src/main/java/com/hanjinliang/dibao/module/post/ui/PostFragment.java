package com.hanjinliang.dibao.module.post.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.dibao.module.base.BaseListFragment;
import com.hanjinliang.dibao.module.post.IPost;
import com.hanjinliang.dibao.module.post.PostPresenter;
import com.hanjinliang.dibao.module.post.adapter.PostRecyclerViewAdapter;
import com.hanjinliang.dibao.module.post.beans.DiBaoPost;
import com.scwang.smartrefresh.layout.api.RefreshLayout;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by HanJinLiang on 2018-01-09.
 */

public class PostFragment extends BaseListFragment<IPost.Presenter> implements IPost.View {
    PostRecyclerViewAdapter mPostRecyclerViewAdapter;
    ArrayList<DiBaoPost> mDiBaoPosts=new ArrayList<>();
    public static PostFragment newInstance() {
        PostFragment fragment = new PostFragment();
        return fragment;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPostRecyclerViewAdapter=new PostRecyclerViewAdapter(getActivity(),mDiBaoPosts);
        mRecyclerView.setAdapter(mPostRecyclerViewAdapter);
    }



    @Override
    public IPost.Presenter setPresenter() {
        return  new PostPresenter(this);
    }

    @Override
    protected void initData()  {

    }

    @Override
    public void onSetAdapter(List<?> list) {
        mDiBaoPosts.clear();
        mDiBaoPosts.addAll((Collection<? extends DiBaoPost>) list);
        mPostRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        presenter.doLoadMoreData();
    }
}
