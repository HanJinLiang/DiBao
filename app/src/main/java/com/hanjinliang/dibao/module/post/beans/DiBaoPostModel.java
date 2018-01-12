package com.hanjinliang.dibao.module.post.beans;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.dibao.module.base.BaseBeans;

import java.lang.reflect.Type;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HanJinLiang on 2018-01-09.
 * 帖子Model
 */
public class DiBaoPostModel extends BaseBeans {
    /**
     *
     * @param pageSize
     * @param pageIndex  从1开始
     * @param modelLoadCallback
     */
     public static void loadPost(int pageSize, int pageIndex,String mType,final ModelLoadCallback modelLoadCallback){
         BmobQuery<DiBaoPost> query = new BmobQuery<DiBaoPost>();
         query.setLimit(pageSize);//每页条数
         query.setSkip(pageSize*(pageIndex-1));//跳过多少页
         query.order("-createdAt");
         query.addWhereEqualTo("postType",mType);
         query.findObjects(new FindListener<DiBaoPost>() {
             int count=0;
             @Override
             public void done(final List<DiBaoPost> postlist, BmobException e) {
                 if(e==null) {
                     LogUtils.e(postlist.toString());
                     if (postlist.size() == 0) {//没有数据
                         modelLoadCallback.loadSuccess(postlist);//空数据
                     } else {
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
                                         modelLoadCallback.loadSuccess(postlist);//帖子 带有图片
                                     }
                                 }
                             });
                         }
                     }

                 }else{
                     modelLoadCallback.error(e.getMessage());
                 }
             }
         });
     }

     public interface ModelLoadCallback{
         public void loadSuccess(List<DiBaoPost> list);
         public void error(String errorMessage);
     }
}
