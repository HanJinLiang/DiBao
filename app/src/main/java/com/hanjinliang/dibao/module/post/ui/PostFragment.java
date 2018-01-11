package com.hanjinliang.dibao.module.post.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.classic.common.MultipleStatusView;
import com.hanjinliang.dibao.R;
import com.hanjinliang.dibao.module.base.LazyLoadFragment;
import com.hanjinliang.dibao.module.post.adapter.PostRecyclerViewAdapter;
import com.hanjinliang.dibao.module.post.beans.DiBaoFile;
import com.hanjinliang.dibao.module.post.beans.DiBaoPost;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HanJinLiang on 2018-01-09.
 */

public class PostFragment extends LazyLoadFragment {
    private String mType;

    public static PostFragment newInstance(String mType) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString("mType",mType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void fetchData() {
        refreshLayout.autoRefresh(100);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args=getArguments();
        if(args!=null){
            mType=args.getString("mType");
        }
    }
    int count=0;
    int page=1;
    private void loadData(final boolean isAdd) {
        LogUtils.e("loadData");
        BmobQuery<DiBaoPost> query = new BmobQuery<DiBaoPost>();
        query.setLimit(10);//每页条数
        query.setSkip(isAdd?(10*(page-1)):0);//跳过多少页
        query.order("-createdAt");
        query.addWhereEqualTo("postType",mType);
        query.findObjects(new FindListener<DiBaoPost>() {
            @Override
            public void done(final List<DiBaoPost> postlist, BmobException e) {
                LogUtils.e(postlist.toString());
                page++;
                count=0;
                if(postlist.size()==0){//没有数据
                    if (!isAdd) {//刷新
                        refreshLayout.finishRefresh();//
                        page = 2;
                        mDiBaoPosts.clear();
                    } else {
                        refreshLayout.finishLoadmore(true);
                    }
                }else {
                    for (int i = 0; i < postlist.size(); i++) {
                        final DiBaoPost diBaoPost = postlist.get(i);
                        BmobQuery<DiBaoFile> queryFile = new BmobQuery<DiBaoFile>();
                        queryFile.addWhereEqualTo("post", new BmobPointer(diBaoPost));
                        queryFile.findObjects(new FindListener<DiBaoFile>() {
                            @Override
                            public void done(List<DiBaoFile> list, BmobException e) {
                                diBaoPost.setDiBaoFiles(list);
                                count++;
                                if (count == postlist.size()) {
                                    if (!isAdd) {//刷新
                                        refreshLayout.finishRefresh();//
                                        page = 2;
                                        mDiBaoPosts.clear();
                                    } else {
                                        refreshLayout.finishLoadmore(true);
                                    }
                                    LogUtils.e(postlist);
                                    mDiBaoPosts.addAll(postlist);
                                    LogUtils.e("notifyDataSetChanged");
                                    mPostRecyclerViewAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                }

            }
        });


    }
    PostRecyclerViewAdapter mPostRecyclerViewAdapter;
    ArrayList<DiBaoPost> mDiBaoPosts=new ArrayList<>();
    RefreshLayout refreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        refreshLayout= (RefreshLayout) view.findViewById(R.id.refreshLayout);
        final MultipleStatusView multipleStatusView = (MultipleStatusView) view.findViewById(R.id.multiple_status_view);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadData(false);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loadData(true);
            }
        });
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPostRecyclerViewAdapter=new PostRecyclerViewAdapter(getActivity(),mType,mDiBaoPosts);
        recyclerView.setAdapter(mPostRecyclerViewAdapter);
        return view;
    }
}
