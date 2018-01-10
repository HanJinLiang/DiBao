package com.hanjinliang.dibao.module.picture.beans;

import com.hanjinliang.dibao.module.base.BaseBeans;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by HanJinLiang on 2018-01-09.
 * 帖子
 */
public class DiBaoPost extends BaseBeans {
    String content;
    String postType;//帖子类型 TYPE_PIC 照片 TYPE_VIDEO 视屏
    BmobUser user;

    List<DiBaoFile> mDiBaoFiles;
    public DiBaoPost(String content, String postType, BmobUser user) {
        this.content = content;
        this.postType = postType;
        this.user = user;
    }

    public DiBaoPost() {
    }

    public List<DiBaoFile> getDiBaoFiles() {
        return mDiBaoFiles;
    }

    public void setDiBaoFiles(List<DiBaoFile> diBaoFiles) {
        mDiBaoFiles = diBaoFiles;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public BmobUser getUser() {
        return user;
    }

    public void setUser(BmobUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "DiBaoPost{" +
                "content='" + content + '\'' +
                ", postType='" + postType + '\'' +
                ", user=" + user +
                ", mDiBaoFiles=" + mDiBaoFiles +
                '}';
    }
}
