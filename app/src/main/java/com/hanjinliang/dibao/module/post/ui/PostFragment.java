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
    private String mType;
    PostRecyclerViewAdapter mPostRecyclerViewAdapter;
    ArrayList<DiBaoPost> mDiBaoPosts=new ArrayList<>();
    public static PostFragment newInstance(String mType) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString("mType",mType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPostRecyclerViewAdapter=new PostRecyclerViewAdapter(getActivity(),mType,mDiBaoPosts);
        mRecyclerView.setAdapter(mPostRecyclerViewAdapter);
    }

    @Override
    protected void initData()  {
        Bundle args=getArguments();
        LogUtils.e("initData--"+args.getString("mType"));
        if(args!=null){
            mType=args.getString("mType");
        }
    }



    @Override
    public void setPresenter(IPost.Presenter presenter) {
        if (null == presenter) {
            this.presenter = new PostPresenter(this,mType);
        }
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
