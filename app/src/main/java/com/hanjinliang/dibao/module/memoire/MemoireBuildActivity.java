package com.hanjinliang.dibao.module.memoire;

import android.view.View;

import com.hanjinliang.dibao.R;
import com.hanjinliang.dibao.module.base.BaseActivity;

/**
 * 新增管理员
 */
public class MemoireBuildActivity extends BaseActivity {


    @Override
    public int attachContentView() {
        return R.layout.activity_memoire_build;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public String setTitle() {
        return "新增备忘录";
    }

    @Override
    public Object setPresenter() {
        return null;
    }
}
