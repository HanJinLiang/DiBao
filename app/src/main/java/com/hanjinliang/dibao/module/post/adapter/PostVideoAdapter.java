package com.hanjinliang.dibao.module.post.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hanjinliang.dibao.tools.image.MyImageLoader;
import com.hanjinliang.dibao.R;
import com.hanjinliang.dibao.module.post.beans.DiBaoFile;

import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by HanJinLiang on 2018-01-10.
 */
public class PostVideoAdapter extends RecyclerView.Adapter<PostVideoAdapter.MyViewHolder> {
    List<DiBaoFile> mFiles;
    public PostVideoAdapter(List<DiBaoFile> files){
        mFiles=files;
    }
    @Override
    public PostVideoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(PostVideoAdapter.MyViewHolder holder, int position) {
        holder.mJZVideoPlayerStandard.setUp( mFiles.get(position).getFile().getUrl()
                , JZVideoPlayerStandard.SCREEN_WINDOW_LIST, mFiles.get(position).getFileDescribe());
        holder.mJZVideoPlayerStandard.setVideoImageDisplayType(JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP);//设置容器内播放器高
        MyImageLoader.getInstance().showImage(holder.mJZVideoPlayerStandard.getContext(),mFiles.get(position).getFile().getUrl(),holder.mJZVideoPlayerStandard.thumbImageView);
    }

    @Override
    public int getItemCount() {
        return mFiles==null?0:mFiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public JZVideoPlayerStandard mJZVideoPlayerStandard;
        public MyViewHolder(View itemView) {
            super(itemView);
            mJZVideoPlayerStandard=itemView.findViewById(R.id.JZVideoPlayerStandard);
        }
    }
}
