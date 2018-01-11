package com.hanjinliang.dibao.module.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.hanjinliang.dibao.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HanJinLiang on 2018-01-04.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @BindView(R.id.baseToolbar)
    Toolbar baseToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局
        LinearLayout rootView= (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_base,null);
        rootView.addView(LayoutInflater.from(this).inflate(getContentViewId(),null),new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        setContentView(rootView);
        ButterKnife.bind(this);
        //initToolBar
        if(isSupportToolBar()){
            initToolBar();
        }else{
            setSupportActionBar(null);
            baseToolbar.setVisibility(View.GONE);
        }
    }


    /**
     * 初始化 ToolBar
     */
    protected void initToolBar(){
        baseToolbar.setTitle(getTitleContent());
        baseToolbar.setNavigationIcon(R.drawable.picture_back);
        setSupportActionBar(baseToolbar);
        baseToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(isSupportRightMenu()) {
            getMenuInflater().inflate(R.menu.menu_base, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            onRightMenuDone();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    AlertDialog dialog;
    public void showDialog(){
        if(dialog==null) {
            dialog = new AlertDialog.Builder(this).create();
            dialog.setMessage("操作中");
        }
        dialog.show();
    }

    public void dismissDialog(){
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }


    ProgressDialog mProgressDialog;
    public void showProgressDialog(){
        if(mProgressDialog==null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle("发布中");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
            mProgressDialog.setMax(100);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    public void updateProgressDialog(int progress){
        if(mProgressDialog!=null) {
            mProgressDialog.setProgress(progress);
            if(progress==mProgressDialog.getMax()){
                mProgressDialog.dismiss();
            }
        }
    }


    public boolean isSupportToolBar(){
        return true;
    }
    public boolean isSupportRightMenu(){
        return false;
    }
    public String getRightMenuText(){return "完成";};
    public void onRightMenuDone(){};
    public abstract int getContentViewId();
    public abstract String getTitleContent();
}
