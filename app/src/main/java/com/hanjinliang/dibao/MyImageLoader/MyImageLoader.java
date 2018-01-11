package com.hanjinliang.dibao.MyImageLoader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hanjinliang.dibao.R;

/**
 * Created by HanJinLiang on 2018-01-11.
 */

public class MyImageLoader implements ImageLoader {
    private static MyImageLoader mMyImageLoader;
    public static MyImageLoader getInstance(){
        if(mMyImageLoader==null){
            mMyImageLoader=new MyImageLoader();
        }
        return mMyImageLoader;
    }
    @Override
    public void showImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.color.color_f6)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }
}
