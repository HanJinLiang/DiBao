package com.hanjinliang.dibao.module.post.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hanjinliang.dibao.R;
import com.hanjinliang.dibao.module.base.BaseActivity;
import com.hanjinliang.dibao.module.post.adapter.FullyGridLayoutManager;
import com.hanjinliang.dibao.module.post.adapter.GridImageAdapter;
import com.hanjinliang.dibao.module.post.beans.DiBaoFile;
import com.hanjinliang.dibao.module.post.beans.DiBaoPost;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;


/**
 * Created by HanJinLiang on 2018-01-08.
 */

public class AddPicActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.et_content)
    EditText et_content;

    public static final String TYPE_PIC="TYPE_PIC";
    public static final String TYPE_VIDEO="TYPE_VIDEO";

    private String mType=TYPE_PIC;//类型  默认为照片

    private GridImageAdapter adapter;

    private int maxSelectNum = 9;
    private List<LocalMedia> selectList = new ArrayList<>();
    public static void launchActivity(Context context, String TYPE){
        Intent intent=new Intent(context,AddPicActivity.class);
        intent.putExtra("TYPE",TYPE);
        context.startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_pic_add;
    }

    @Override
    public String getTitleContent() {
        return "添加";
    }

    @Override
    public boolean isSupportRightMenu() {
        return true;
    }

    @Override
    public void onRightMenuDone() {
        final DiBaoPost diBaoPost=new DiBaoPost(et_content.getText().toString(),mType, BmobUser.getCurrentUser());
        diBaoPost.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                LogUtils.e(s);
                postFiles(diBaoPost);
            }
        });
    }

    private void postFiles(final DiBaoPost diBaoPost) {
        final String[] filePaths=new String[selectList.size()];
        for(int i=0;i<filePaths.length;i++){
            filePaths[i]=selectList.get(i).getPath();
            LogUtils.e(FileUtils.getFileSize(selectList.get(i).getPath()));
        }
        showProgressDialog();
        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> urls) {
                //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                //2、urls-上传文件的完整url地址
                if(urls.size()==filePaths.length){//如果数量相等，则代表文件全部上传完成
                    //do something
                   LogUtils.e("文件上传成功");
                    final List<BmobObject> files=new ArrayList<>();
                    for(int i=0;i<list.size();i++){
                        final DiBaoFile diBaoFile=new DiBaoFile("",list.get(i),mType);
                        diBaoFile.setPost(diBaoPost);
                        files.add(diBaoFile);
                    }
                    insertBatch(files);
                }
            }

            @Override
            public void onProgress(int i, int i1, int i2, int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
                LogUtils.e(totalPercent);
                updateProgressDialog(totalPercent);
            }

            @Override
            public void onError(int i, String s) {

            }
        });


    }

    /**
     * 批量插入
     * @param files
     */
    public void insertBatch(final List<BmobObject> files){
        //第二种方式：v3.5.0开始提供
        new BmobBatch().insertBatch(files).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if(e==null){
                    for(int i=0;i<o.size();i++){
                        BatchResult result = o.get(i);
                        BmobException ex =result.getError();
                        if(ex==null){
                            LogUtils.e("第"+i+"个数据批量添加成功："+result.getCreatedAt()+","+result.getObjectId()+","+result.getUpdatedAt());
                        }else{
                            LogUtils.e("第"+i+"个数据批量添加失败："+ex.getMessage()+","+ex.getErrorCode());
                        }
                    }
                    ToastUtils.showShort("发布成功");
                    finish();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType=getIntent().getStringExtra("TYPE");
        select();
        initView();
    }

    private void initView() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(AddPicActivity.this, 4, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(AddPicActivity.this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(AddPicActivity.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(AddPicActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(AddPicActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            select();
        }

    };


    private void select() {
        int mimeType=PictureMimeType.ofImage();
        if(mType.equals(TYPE_PIC)){
            mimeType=PictureMimeType.ofImage();
        }else if(mType.equals(TYPE_VIDEO)){
            mimeType=PictureMimeType.ofVideo();
        }
        PictureSelector.create(AddPicActivity.this)
                .openGallery(mimeType)//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_white_style)
                .compress(true)
                .videoQuality(0)// 视频录制质量 0 or 1
                .selectionMedia(selectList)// 是否传入已选图片
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                    }
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
