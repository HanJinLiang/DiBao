package com.hanjinliang.dibao.module.post.beans;

import com.hanjinliang.dibao.module.base.BaseBeans;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by HanJinLiang on 2018-01-08.
 * 照片
 */
public class DiBaoFile extends BaseBeans {
    String fileDescribe;//照片的描述
    BmobFile file;//文件
    String fileType;//文件类型 TYPE_PIC 照片 TYPE_VIDEO 视屏
    DiBaoPost post;//关联的post

    public DiBaoFile(String fileDescribe, BmobFile file, String fileType) {
        this.fileDescribe = fileDescribe;
        this.file = file;
        this.fileType = fileType;
    }

    public DiBaoFile(String fileDescribe, BmobFile file, String fileType, DiBaoPost post) {
        this.fileDescribe = fileDescribe;
        this.file = file;
        this.fileType = fileType;
        this.post = post;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public DiBaoPost getPost() {
        return post;
    }

    public void setPost(DiBaoPost post) {
        this.post = post;
    }

    public DiBaoFile() {
    }

    public String getFileDescribe() {
        return fileDescribe;
    }

    public void setFileDescribe(String fileDescribe) {
        this.fileDescribe = fileDescribe;
    }

    public BmobFile getFile() {
        return file;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "DiBaoFile{" +
                "fileDescribe='" + fileDescribe + '\'' +
                ", file=" + file +
                ", fileType='" + fileType + '\'' +
                ", post=" + post +
                '}';
    }
}
