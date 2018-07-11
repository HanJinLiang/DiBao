package com.hanjinliang.dibao.module.post;

import android.support.annotation.StringDef;

import static com.hanjinliang.dibao.module.post.PostType.IMAGE;
import static com.hanjinliang.dibao.module.post.PostType.VIDEO;

/**
 * Created by HanJinLiang on 2018-07-11.
 * 帖子类型
 */
@StringDef({VIDEO, IMAGE})
public @interface PostType {
     String VIDEO="TYPE_VIDEO";
     String IMAGE="TYPE_PIC";
}
