package com.hanjinliang.dibao.module.post;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.dibao.module.post.beans.DiBaoPost;
import com.hanjinliang.dibao.module.post.beans.DiBaoPostModel;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/12/17.
 */

public class PostPresenter implements IPost.Presenter {

    private static final String TAG = "PostPresenter";
    private IPost.View view;
    private int mPageIndex=1;
    private List<DiBaoPost> mDiBaoPosts=new ArrayList<>();
    public PostPresenter(IPost.View view) {
        this.view = view;
    }

    @Override
    public void doRefresh() {
        doLoadData();
    }

    @Override
    public void doLoadData() {
        DiBaoPostModel.loadPost(10, 1, new DiBaoPostModel.ModelLoadCallback() {
            @Override
            public void loadSuccess(List<DiBaoPost> list) {
                if(list==null||list.size()==0){//没有更多数据
                    view.onShowDataEmpty();
                }
                view.onHideLoading();
                mPageIndex=2;
                mDiBaoPosts.clear();
                mDiBaoPosts.addAll(list);
                view.onSetAdapter(mDiBaoPosts);
            }

            @Override
            public void error(String errorMessage) {
                view.onShowNetError();
            }
        });
    }

    @Override
    public void doLoadMoreData() {
        DiBaoPostModel.loadPost(10, ++mPageIndex, new DiBaoPostModel.ModelLoadCallback() {
            @Override
            public void loadSuccess(List<DiBaoPost> list) {
                if(list==null||list.size()==0){//没有更多数据
                    view.onShowNoMore();
                }else {
                    view.onHideLoadingMore();
                    mDiBaoPosts.addAll(list);
                    view.onSetAdapter(mDiBaoPosts);
                }
            }

            @Override
            public void error(String errorMessage) {
                mPageIndex--;
                view.onShowNetError();
            }
        });
    }

}
