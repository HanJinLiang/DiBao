package com.hanjinliang.dibao.module.post.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.hanjinliang.dibao.R;
import com.hanjinliang.dibao.module.base.BaseActivity;

import com.luck.picture.lib.photoview.PhotoView;

import com.luck.picture.lib.widget.PreviewViewPager;
import com.luck.picture.lib.widget.longimage.ImageSource;
import com.luck.picture.lib.widget.longimage.ImageViewState;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 浏览大图
 */
public class PicturePreviewActivity extends BaseActivity{
    public static final String PIC_PATHS="PIC_PATHS";
    public static final String PIC_POSITION="PIC_POSITION";

    @BindView(R.id.preview_pager)
    PreviewViewPager viewPager;
    private int position;
    private List<String> mImagePaths = new ArrayList<>();

    /**
     * 开始浏览图片
     * @param context
     * @param imagePaths
     * @param position
     */
    public static void previewPicture(Context context,ArrayList<String> imagePaths, int position){
        Intent intent=new Intent(context,PicturePreviewActivity.class);
        intent.putExtra(PIC_POSITION,position);
        intent.putStringArrayListExtra(PIC_PATHS,imagePaths);
        context.startActivity(intent);
    }
    @Override
    public int attachContentView() {
        return R.layout.activity_picture_preview;
    }

    @Override
    public boolean isSwipeBack() {
        return false;
    }

    @Override
    public void initView(View view) {
        position = getIntent().getIntExtra(PIC_POSITION, 0);
        mImagePaths = getIntent().getStringArrayListExtra(PIC_PATHS);
        setTitle(position + 1 + "/" + mImagePaths.size());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int i) {
                position = i;
                setTitle(position + 1 + "/" + mImagePaths.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        viewPager.setAdapter(new SimpleFragmentAdapter());
        viewPager.setCurrentItem(position,true);
    }

    @Override
    public String setTitle() {
        return "";
    }



    @Override
    public Object setPresenter() {
        return null;
    }

    public class SimpleFragmentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImagePaths.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            (container).removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View contentView = LayoutInflater.from(PicturePreviewActivity.this).inflate(R.layout.item_picture_preview, container, false);
            // 常规图控件
            final PhotoView imageView = (PhotoView) contentView.findViewById(R.id.preview_image);
            // 长图控件
            final SubsamplingScaleImageView longImg = (SubsamplingScaleImageView) contentView.findViewById(R.id.longImg);
            final String path = mImagePaths.get(position);
            RequestOptions options = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(PicturePreviewActivity.this)
                    .asBitmap()
                    .load(path)
                    .apply(options)
                    .into(new SimpleTarget<Bitmap>(480, 800) {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            if (isLongImg(resource)) {
                                imageView.setVisibility(View.GONE);
                                longImg.setVisibility(View.VISIBLE);
                                displayLongPic(resource, longImg);
                            } else {
                                imageView.setVisibility(View.VISIBLE);
                                longImg.setVisibility(View.GONE);
                                imageView.setImageBitmap(resource);
                            }
                        }
                    });

            (container).addView(contentView, 0);
            //单击结束页面
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            longImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            return contentView;
        }
    }

    private boolean isLongImg(Bitmap bitmap) {
        if(null != bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int h = width * 3;
            return height > h;
        } else {
            return false;
        }
    }

    /**
     * 加载长图
     *
     * @param bmp
     * @param longImg
     */
    private void displayLongPic(Bitmap bmp, SubsamplingScaleImageView longImg) {
        longImg.setQuickScaleEnabled(true);
        longImg.setZoomEnabled(true);
        longImg.setPanEnabled(true);
        longImg.setDoubleTapZoomDuration(100);
        longImg.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CENTER_CROP);
        longImg.setDoubleTapZoomDpi(SubsamplingScaleImageView.ZOOM_FOCUS_CENTER);
        longImg.setImage(ImageSource.cachedBitmap(bmp), new ImageViewState(0, new PointF(0, 0), 0));
    }

}
