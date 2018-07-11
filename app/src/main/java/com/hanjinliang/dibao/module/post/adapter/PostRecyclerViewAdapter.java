package com.hanjinliang.dibao.module.post.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.hanjinliang.dibao.R;
import com.hanjinliang.dibao.module.post.PostType;
import com.hanjinliang.dibao.module.post.beans.DiBaoFile;
import com.hanjinliang.dibao.module.post.beans.DiBaoPost;
import com.hanjinliang.dibao.module.post.ui.AddPicActivity;


import java.util.ArrayList;
import java.util.List;

/**
 */
public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder> {

    private  ArrayList<DiBaoPost> mDiBaoPosts;
    private Context mContext;
    public PostRecyclerViewAdapter(Context context,ArrayList<DiBaoPost> diBaoPosts) {
        mContext=context;
        mDiBaoPosts = diBaoPosts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.picRecyclerView.setLayoutManager(new GridLayoutManager(mContext,getColumnsCount(mDiBaoPosts.get(position).getPostType(),mDiBaoPosts.get(position).getDiBaoFiles())));
        if(mDiBaoPosts.get(position).getPostType().equals(PostType.IMAGE)){
            holder.picRecyclerView.setAdapter(new PostPicAdapter(mDiBaoPosts.get(position).getDiBaoFiles()));
        }else{
            holder.picRecyclerView.setAdapter(new PostVideoAdapter(mDiBaoPosts.get(position).getDiBaoFiles()));
        }
        holder.postContent.setText(mDiBaoPosts.get(position).getContent());
        holder.userName.setText(mDiBaoPosts.get(position).getUser().getUsername());
        holder.time.setText(mDiBaoPosts.get(position).getCreatedAt());
    }

    private int getColumnsCount(String postType,List<DiBaoFile> diBaoFiles) {
        if(postType.equals(PostType.VIDEO)||diBaoFiles==null){
            return 2;//视频只显示一个
        }
        int columns=3;
        switch (diBaoFiles.size()){
            case 0:
            case 1:
                columns=1;
                break;
            case 2:
            case 3:
            case 4:
                columns=2;
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                columns=3;
                break;
        }
        return columns;
    }

    @Override
    public int getItemCount() {
        return mDiBaoPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final RecyclerView picRecyclerView;
        public final TextView postContent;
        public final TextView userName;
        public final TextView time;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            picRecyclerView = (RecyclerView) view.findViewById(R.id.picRecyclerView);
            postContent = (TextView) view.findViewById(R.id.postContent);
            userName = (TextView) view.findViewById(R.id.userName);
            time = (TextView) view.findViewById(R.id.time);
        }

    }
}
