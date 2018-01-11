package com.hanjinliang.dibao.module.post.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hanjinliang.dibao.MyImageLoader.MyImageLoader;
import com.hanjinliang.dibao.R;
import com.hanjinliang.dibao.module.post.beans.DiBaoFile;

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
        View imageView =LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pic, parent, false);
        MyViewHolder myViewHolder=new MyViewHolder(imageView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(PostPicAdapter.MyViewHolder holder, int position) {
        MyImageLoader.getInstance().showImage(holder.mImageView.getContext(),mFiles.get(position).getFile().getUrl(),holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mFiles==null?0:mFiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public  ImageView mImageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.imageView);
        }
    }
}
