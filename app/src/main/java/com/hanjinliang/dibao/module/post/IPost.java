package com.hanjinliang.dibao.module.post;

import com.hanjinliang.dibao.module.base.IBaseListView;
import com.hanjinliang.dibao.module.base.IBasePresenter;
import com.hanjinliang.dibao.module.post.beans.DiBaoPost;

import java.util.List;

/**
 * Created by HanJinLiang on 2018-01-12.
 */
public interface IPost {
    interface View extends IBaseListView<Presenter> {


    }

    interface Presenter extends IBasePresenter {

        /**
         * 请求数据
         */
        void doLoadData();

        /**
         * 再起请求数据
         */
        void doLoadMoreData();
    }
}
