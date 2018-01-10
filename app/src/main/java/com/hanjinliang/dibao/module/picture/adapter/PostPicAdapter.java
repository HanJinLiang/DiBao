package com.hanjinliang.dibao.module.picture.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.hanjinliang.dibao.module.picture.beans.DiBaoFile;

import java.util.List;

/**
 * Created by HanJinLiang on 2018-01-10.
 */

public class PostPicAdapter extends RecyclerView.Adapter<PostPicAdapter.MyViewHolder> {
    List<DiBaoFile> mFiles;
    public PostPicAdapter(List<DiBaoFile> files){
        mFiles=files;
    }
    @Override
    public PostPicAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView imageView=new ImageView(parent.getContext());
//        RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.setMargins(SizeUtils.dp2px(5),SizeUtils.dp2px(5),SizeUtils.dp2px(5),SizeUtils.dp2px(5));
//        imageView.setLayoutParams(params);
        MyViewHolder myViewHolder=new MyViewHolder(imageView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(PostPicAdapter.MyViewHolder holder, int position) {
        ImageView imageView=(ImageView)holder.itemView;
        Glide.with(imageView.getContext()).load(mFiles.get(position).getFile().getUrl()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return mFiles==null?0:mFiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
