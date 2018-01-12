package com.hanjinliang.dibao.module.post.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hanjinliang.dibao.MyImageLoader.MyImageLoader;
import com.hanjinliang.dibao.R;
import com.hanjinliang.dibao.module.post.beans.DiBaoFile;
import com.hanjinliang.dibao.module.post.ui.PicturePreviewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanJinLiang on 2018-01-10.
 */
public class PostPicAdapter extends RecyclerView.Adapter<PostPicAdapter.MyViewHolder> {
    List<DiBaoFile> mFiles;
    ArrayList<String> picPaths=new ArrayList<>();
    public PostPicAdapter(List<DiBaoFile> files){
        mFiles=files;
        picPaths.clear();
        for(DiBaoFile diBaoFile:mFiles){
            picPaths.add(diBaoFile.getFile().getUrl());
        }
    }
    @Override
    public PostPicAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View imageView =LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pic, parent, false);
        MyViewHolder myViewHolder=new MyViewHolder(imageView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final PostPicAdapter.MyViewHolder holder,final int position) {
        MyImageLoader.getInstance().showImage(holder.mImageView.getContext(),mFiles.get(position).getFile().getUrl(),holder.mImageView);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicturePreviewActivity.previewPicture(holder.mImageView.getContext(),picPaths,position);
            }
        });
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
